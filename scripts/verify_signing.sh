#!/usr/bin/env bash
# Verify the release signing setup of template-compose.
# The script builds in a temporary copy of the tracked and unignored files.
# It never touches the working tree.
#
# Usage: scripts/verify_signing.sh

set -euo pipefail

REPO_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SDK_DIR="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}}"
WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT

STORE_PASSWORD="verify-store-pass"
KEY_PASSWORD="verify-key-pass"
KEY_ALIAS="verify-key"
KEY_CN="verify-signing-check"
FAILURES=0

pass() { echo "PASS: $1"; }
fail() { echo "FAIL: $1"; FAILURES=$((FAILURES + 1)); }

fresh_copy() {
    rm -rf "$WORK_DIR/project"
    mkdir -p "$WORK_DIR/project"
    (cd "$REPO_ROOT" && git ls-files --cached --others --exclude-standard -z -- template-compose) |
        rsync -a --from0 --files-from=- "$REPO_ROOT/" "$WORK_DIR/project/"
}

gradle() {
    (cd "$WORK_DIR/project/template-compose" && ANDROID_HOME="$SDK_DIR" ./gradlew --console=plain \
        ${JAVA_HOME:+-Dorg.gradle.java.home="$JAVA_HOME"} "$@")
}

signer_cn() {
    local apksigner
    apksigner="$(ls -d "$SDK_DIR"/build-tools/*/apksigner | sort -V | tail -1)"
    "$apksigner" verify --print-certs "$1" | grep -m1 "certificate DN" || true
}

# 1. A debug build needs no signing file.
fresh_copy
if gradle assembleStagingDebug >"$WORK_DIR/debug.log" 2>&1; then
    pass "debug build without signing.properties"
else
    fail "debug build without signing.properties"; tail -20 "$WORK_DIR/debug.log"
fi

# 2. A release build without the file fails with one clear message.
if gradle assembleStagingRelease >"$WORK_DIR/release_missing.log" 2>&1; then
    fail "release build without signing.properties must fail"
elif grep -q "signing.properties" "$WORK_DIR/release_missing.log" &&
    grep -q "release.keystore" "$WORK_DIR/release_missing.log"; then
    pass "release build without signing.properties fails with a message"
else
    fail "release failure message misses signing.properties or release.keystore"
    tail -20 "$WORK_DIR/release_missing.log"
fi

# 3. A release build with a throwaway keystore and file signs with that key.
keytool -genkeypair -storetype JKS -keystore "$WORK_DIR/project/template-compose/config/release.keystore" \
    -storepass "$STORE_PASSWORD" -keypass "$KEY_PASSWORD" -alias "$KEY_ALIAS" \
    -dname "CN=$KEY_CN" -keyalg RSA -keysize 2048 -validity 2 >/dev/null 2>&1
cat >"$WORK_DIR/project/template-compose/signing.properties" <<EOF
KEY_ALIAS=$KEY_ALIAS
KEYSTORE_PASSWORD=$STORE_PASSWORD
KEY_PASSWORD=$KEY_PASSWORD
EOF
for task in assembleStagingRelease assembleStagingPreRelease; do
    if gradle "$task" >"$WORK_DIR/$task.log" 2>&1; then
        variant="${task#assembleStaging}"
        apk="$(find "$WORK_DIR/project/template-compose/app/build/outputs/apk" -name '*.apk' -ipath "*staging*${variant}*" | head -1)"
        if [[ -n "$apk" ]] && signer_cn "$apk" | grep -q "$KEY_CN"; then
            pass "$task signs with the release key"
        else
            fail "$task APK is not signed with the release key"
        fi
    else
        fail "$task with signing.properties and keystore"; tail -20 "$WORK_DIR/$task.log"
    fi
done

# 4. A blank key fails and the message names the key.
sed -i.bak 's/^KEY_PASSWORD=.*/KEY_PASSWORD=/' "$WORK_DIR/project/template-compose/signing.properties"
if gradle assembleStagingRelease >"$WORK_DIR/release_blank.log" 2>&1; then
    fail "release build with a blank KEY_PASSWORD must fail"
elif grep -q "KEY_PASSWORD" "$WORK_DIR/release_blank.log"; then
    pass "release build with a blank KEY_PASSWORD fails and names the key"
else
    fail "blank key message misses KEY_PASSWORD"; tail -20 "$WORK_DIR/release_blank.log"
fi

exit "$FAILURES"

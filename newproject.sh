#!/bin/bash
set -e

# Script inspired by https://gist.github.com/szeidner/613fe4652fc86f083cefa21879d5522b

PROGNAME=$(basename $0)
WORKING_DIR=$(cd -P -- "$(dirname -- "$0")" && pwd -P)

die() {
    echo "$PROGNAME: $*" >&2
    exit 1
}

usage() {
    if [ "$*" != "" ] ; then
        echo "Error: $*"
    fi

    cat << EOF
Usage: $PROGNAME --template [TEMPLATE] --package-name [PACKAGE_NAME] --app-name [APP_NAME]
Set up an Android app and package name.
Options:
-h, --help                         display this usage message and exit
-t, --template [TEMPLATE]          Select template: "rx" - RxJavaTemplate or "crt" - CoroutineTemplate (i.e. rx)
-p, --package-name [PACKAGE_NAME]  new package name (i.e. com.example.package)
-n, --app-name [APP_NAME]          new app name (i.e. MyApp, "My App")
EOF

    exit 1
}

template=""
packagename=""
appname=""
while [ $# -gt 0 ] ; do
    case "$1" in
    -h|--help)
        usage
        ;;
    -t|--template)
        template="$2"
        shift
        ;;
    -p|--package-name)
        packagename="$2"
        shift
        ;;
    -n|--app-name)
        appname="$2"
        shift
        ;;
    -*)
        usage "Unknown option '$1'"
        ;;
    *)
        usage "Too many arguments"
      ;;
    esac
    shift
done

OLD_APPNAME=""
OLD_NAME=""
OLD_PACKAGE=""

# Path segments
FIRST_PACKAGE_SEGMENT="co"
SECOND_PACKAGE_SEGMENT="nimblehq"
THIRD_PACKAGE_SEGMENT=""
# Select template
case "$template" in
    "crt")
        OLD_APPNAME="Coroutine Template"
        OLD_NAME="CoroutineTemplate"
        OLD_PACKAGE="co.nimblehq.coroutine"
        THIRD_PACKAGE_SEGMENT="coroutine"
        ;;
    "rx")
        OLD_APPNAME="RxJava Template"
        OLD_NAME="RxJavaTemplate[DEPRECATED]"
        OLD_PACKAGE="co.nimblehq.rxjava"
        THIRD_PACKAGE_SEGMENT="rxjava"
        ;;
    -*)
        usage "Unknown template '$template'. Please use 'rx' for RxJavaTemplate or 'crt' for CoroutineTemplate"
        ;;
esac

if [ -z "$packagename" ] ; then
    usage "No new package provided"
fi

if [ -z "$appname" ] ; then
    usage "No new app name provided"
fi

# Enforce package name
regex='^[a-z][a-z0-9_]*(\.[a-z0-9_]+)+[0-9a-z_]$'
if ! [[ "$packagename" =~ $regex ]]; then
    die "Invalid Package Name: $packagename (needs to follow standard pattern {com.example.package})"
fi

echo "=> 🐢 Staring init $appname with $OLD_APPNAME..."

# Trim spaces in APP_NAME
NAME_NO_SPACES=$(echo "$appname" | sed "s/ //g")

# Copy main folder
cp -R $OLD_NAME $NAME_NO_SPACES

# Clean the old build
./$NAME_NO_SPACES/gradlew -p ./$NAME_NO_SPACES clean
# Get rid of idea settings
rm -rf $NAME_NO_SPACES/.idea
# Get rid of gradle cache
rm -rf $NAME_NO_SPACES/.gradle
# Get rid of the git history
rm -rf $NAME_NO_SPACES/.git

# Rename folder structure
renameFolderStructure() {
  DIR=""
  if [ "$*" != "" ] ; then
      DIR="$*"
  fi
  ORIG_DIR=$DIR

  mv $NAME_NO_SPACES/$DIR/$FIRST_PACKAGE_SEGMENT/$SECOND_PACKAGE_SEGMENT/$THIRD_PACKAGE_SEGMENT $NAME_NO_SPACES/$DIR/
  rm -rf $NAME_NO_SPACES/$DIR/$FIRST_PACKAGE_SEGMENT/$SECOND_PACKAGE_SEGMENT
  rm -rf $NAME_NO_SPACES/$DIR/$FIRST_PACKAGE_SEGMENT
  cd $NAME_NO_SPACES/$DIR
  IFS='.' read -ra packages <<< "$packagename"
  for i in "${packages[@]}"; do
      DIR="$DIR/$i"
      mkdir $i
      cd $i
  done
  mv $WORKING_DIR/$NAME_NO_SPACES/$ORIG_DIR/$THIRD_PACKAGE_SEGMENT/* ./
  rmdir $WORKING_DIR/$NAME_NO_SPACES/$ORIG_DIR/$THIRD_PACKAGE_SEGMENT
  cd $WORKING_DIR
  echo $DIR
}

# Rename files structure
echo "=> 🔎 Replacing files structure..."

# Rename project folder structure
APP_PACKAGE_DIR="app/src/main/java"
APP_PACKAGE_DIR=$( renameFolderStructure $APP_PACKAGE_DIR )

DATA_PACKAGE_DIR="data/src/main/java"
DATA_PACKAGE_DIR=$( renameFolderStructure $DATA_PACKAGE_DIR )

DOMAIN_PACKAGE_DIR="domain/src/main/java"
DOMAIN_PACKAGE_DIR=$( renameFolderStructure $DOMAIN_PACKAGE_DIR )

# Rename android test folder structure
APP_ANDROIDTEST_DIR="app/src/androidTest/java"
if [ -d APP_ANDROIDTEST_DIR ]
then
    APP_ANDROIDTEST_DIR=$( renameFolderStructure $APP_ANDROIDTEST_DIR )
fi

DATA_ANDROIDTEST_DIR="data/src/androidTest/java"
if [ -d DATA_ANDROIDTEST_DIR ]
then
    DATA_ANDROIDTEST_DIR=$( renameFolderStructure $DATA_ANDROIDTEST_DIR )
fi

DOMAIN_ANDROIDTEST_DIR="domain/src/androidTest/java"
if [ -d DOMAIN_ANDROIDTEST_DIR ]
then
    DOMAIN_ANDROIDTEST_DIR=$( renameFolderStructure $DOMAIN_ANDROIDTEST_DIR )
fi

# Rename test folder structure
APP_TEST_DIR="app/src/test/java"
if [ -d APP_TEST_DIR ]
then
    APP_TEST_DIR=$( renameFolderStructure $APP_TEST_DIR )
fi

DATA_TEST_DIR="data/src/test/java"
if [ -d DATA_TEST_DIR ]
then
    DATA_TEST_DIR=$( renameFolderStructure $DATA_TEST_DIR )
fi

DOMAIN_TEST_DIR="domain/src/test/java"
if [ -d DOMAIN_TEST_DIR ]
then
    DOMAIN_TEST_DIR=$( renameFolderStructure $DOMAIN_TEST_DIR )
fi

# Rename common-rx module on RxTemplate
if [ $template = "rx" ]
then
  # Rename package folder
  COMMON_RX_PACKAGE_DIR="common-rx/src/main/java"
  COMMON_RX_PACKAGE_DIR=$( renameFolderStructure $COMMON_RX_PACKAGE_DIR )

  # Rename androidTest folder
  COMMON_RX_ANDROIDTEST_DIR="common-rx/src/androidTest/java"
  if [ -d COMMON_RX_ANDROIDTEST_DIR ]
  then
      COMMON_RX_ANDROIDTEST_DIR=$( renameFolderStructure $COMMON_RX_ANDROIDTEST_DIR )
  fi

  # Rename test folder
  COMMON_RX_TEST_DIR="common-rx/src/test/java"
  if [ -d COMMON_RX_TEST_DIR ]
  then
      COMMON_RX_TEST_DIR=$( renameFolderStructure $COMMON_RX_TEST_DIR )
  fi
fi

echo "✅  Completed"

# Search and replace in files
echo "=> 🔎 Replacing package and package name within files..."
PACKAGE_NAME_ESCAPED="${packagename//./\.}"
OLD_PACKAGE_NAME_ESCAPED="${OLD_PACKAGE//./\.}"
if [[ "$OSTYPE" == "darwin"* ]] # Mac OSX
then
  LC_ALL=C find $WORKING_DIR/$NAME_NO_SPACES -type f -exec sed -i "" -e "s/$OLD_PACKAGE_NAME_ESCAPED/$PACKAGE_NAME_ESCAPED/g" {} +
  LC_ALL=C find $WORKING_DIR/$NAME_NO_SPACES -type f -exec sed -i "" -e "s/$OLD_NAME/$NAME_NO_SPACES/g" {} +
else
  LC_ALL=C find $WORKING_DIR/$NAME_NO_SPACES -type f -exec sed -i -e "s/$OLD_PACKAGE_NAME_ESCAPED/$PACKAGE_NAME_ESCAPED/g" {} +
  LC_ALL=C find $WORKING_DIR/$NAME_NO_SPACES -type f -exec sed -i -e "s/$OLD_NAME/$NAME_NO_SPACES/g" {} +
fi
echo "✅  Completed"

# Search and replace files <...>
echo "=> 🔎 Replacing app name in strings.xml..."
if [[ "$OSTYPE" == "darwin"* ]] # Mac OSX
then
  sed -i "" -e "s/$OLD_APPNAME/$appname/" "$WORKING_DIR/$NAME_NO_SPACES/app/src/main/res/values/strings.xml"
  sed -i "" -e "s/$OLD_APPNAME/$appname/" "$WORKING_DIR/$NAME_NO_SPACES/app/src/staging/res/values/strings.xml"
else
  sed -i -e "s/$OLD_APPNAME/$appname/" "$WORKING_DIR/$NAME_NO_SPACES/app/src/main/res/values/strings.xml"
  sed -i -e "s/$OLD_APPNAME/$appname/" "$WORKING_DIR/$NAME_NO_SPACES/app/src/staging/res/values/strings.xml"
fi
echo "✅  Completed"

echo "=> 🔎 Replacing Application class..."
OLD_APPLICATION_CLASS_PATH=$(find $WORKING_DIR/$NAME_NO_SPACES -type f -iname "*Application.*";)
APPLICATION_CLASS_PATH="${OLD_APPLICATION_CLASS_PATH/$OLD_NAME/$NAME_NO_SPACES}"
mv $OLD_APPLICATION_CLASS_PATH $APPLICATION_CLASS_PATH
echo "✅  Completed"

echo "=> 🛠️ Building generated project..."
./$NAME_NO_SPACES/gradlew -p ./$NAME_NO_SPACES assembleDebug
echo "✅  Build success"

echo "=> 🚓 Executing all unit tests..."
./$NAME_NO_SPACES/gradlew -p ./$NAME_NO_SPACES testStagingDebugUnitTest
echo "✅  All unit tests passed"

# Done!
echo "=> 🚀 Done! The project is ready for development 🙌"

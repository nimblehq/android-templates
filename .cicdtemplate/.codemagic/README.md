# Setup

Setting up the [Codemagic](https://codemagic.io/start/) to the project starts from adding project to
the Codemagic console.
After that, you can follow the instruction below to setup the environment variables and deployment.

# Environment Variables

You can configure the environment variables for Codemagic by accessing the `Environment Variables`
tab under `App Settings`.
For detailed instructions on how to configure these variables, please refer
to [this document](https://docs.codemagic.io/yaml-basic-configuration/configuring-environment-variables/#configuring-environment-variables)
.

# Deploy to Firebase App Distribution (FAD)

1. Set up the Firebase SDK in your app by following
   the [Get started guide for Android](https://firebase.google.com/docs/android/setup).
2. Create service account for Codemagic by following
   the [Firebase App Distribution with codemagic.yaml](https://docs.codemagic.io/yaml-publishing/firebase-app-distribution/)
   at `Authenticating via service account` which is recommended.
3. Update the script on `app_id` and tester `groups` in `codemagic.yaml` file:

   ```yaml
     publishing:
     firebase:
        firebase_service_account: $FIREBASE_SERVICE_ACCOUNT
        android:
           app_id: x:xxxxxxxxxxxx:android:xxxxxxxxxxxxxxxxxxxxxx
           groups:
              - androidTesters
              - ...
           artifact_type: 'apk' # Replace with 'aab' to only publish the Android app bundle
   ```

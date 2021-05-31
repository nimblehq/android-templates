# Setup

## App Environment Variables

1. Navigate to `Env Vars` tab on Bitrise
2. Update variables:
  - `PROJECT_LOCATION` = `.` // Root folder
  - `MODULE` = `app` // Main project's module
  - `VARIANT` = `debug` // Default run with debug
  - `TZ` = `Asia/Bangkok` // Config timezone for host machine
  - `PACKAGE_NAME` = <Your app package id> // Package name will be used for deploy to Google Play Store

## Deployment

### Code Signing

1. Navigate to `Code Signing` tab on Bitrise.
2. Upload android `keystore` file.
3. Upload `signing.properties` file and enter `SIGNING_PROPERTIES` to the `File Storage ID` text box.

> Please notice that you can add specific variables on each Workflow

### Deploy to Bitrise

Uploading APKs file to Bitrise already supported on `deploy-to-bitrise-io` step. Please take a look on `bitrise.yml` for further details.

### Deploy to Firebase App Distribution (FAD)

This template does not include steps to publishing the APKs to FAD.
You can follow this [article](https://medium.com/@arekk/bitrise-firebase-app-distribution-step-9f9eb558fb89) for the guideline. Or using [fastlane](https://docs.fastlane.tools/getting-started/android/setup/) to setup deployment step.

### Deploying app to Google Play Store

1. Navigate to `Env Vars` tab on Bitrise
2. Upload the google service account credential file `api-xxx-xxx.json` file and enter `SERVICE_ACCOUNT_JSON_KEY` to the `File Storage ID` text box.
3. By default, the release bundle will be uploaded to `alpha` test track.

> Steps how to get Google API access json file from [Bitrise](https://devcenter.bitrise.io/deploy/android-deploy/deploying-android-apps/)

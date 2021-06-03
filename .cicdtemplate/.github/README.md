# Setup

The `.github` contains all the required setup for Github Action to trigger. Simply place this `.github` directory at your root directory, continue with the following instruction to update your environment variables (Secrets) to have it up and running correctly.

# Environment Variables (Secret)
Here are the current Secrets we need to add to the Project Settings - Secret:

- `FIREBASE_APP_ID_STAGING` - your application id on Firebase.
- `FIREBASE_TOKEN` - your Firebase access/refresh token. Follow this [Guideline](https://firebase.google.com/docs/cli#cli-ci-systems) to get one for your project.
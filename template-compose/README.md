# Android Templates

- Our optimized Android templates used in our android projects

## Setup

- Clone the project
- Run the project with Android Studio

## Linter and static code analysis

- Detekt:

```
$ ./gradlew detekt
```

Report is located at: `./build/reports/detekt`

- Ktlint:

```
$ ./gradlew ktlintCheck
```

Report is located at: `./build/reports/ktlint`

To automatically fix formatting issues:

```
$ ./gradlew ktlintFormat
```

**Note:** `ktlintFormat` runs automatically before each build to ensure code is properly formatted.

## Testing

- Run unit testing:

```
$ ./gradlew app:testStagingDebugUnitTest
$ ./gradlew data:testDebugUnitTest
$ ./gradlew domain:test
```

- Run unit testing with coverage:

```
$ ./gradlew koverHtmlReport
```

Report is located at: `app/build/reports/kover/`

## Build and deploy

For `release` builds, we need to provide release keystore and signing properties:

- Put the `release.keystore` file at root `config` folder.
- Put keystore signing properties in `signing.properties`

# Android Templates

- Our optimized Android templates used in our android projects

## Setup

- Clone the project
- Run the project with Android Studio

## Linter and static code analysis

- Lint:

```
$ ./gradlew lint
```

Report is located at: `./app/build/reports/lint/`

- Detekt

```
$ ./gradlew detekt
```

Report is located at: `./build/reports/detekt`

## Testing

- Run unit testing:

```
$ ./gradlew app:testStagingDebugUnitTest
$ ./gradlew data:testDebugUnitTest
$ ./gradlew domain:test
```

- Run unit testing with coverage:

```
$ ./gradlew koverHtmlReportCustom
```

Report is located at: `app/build/reports/kover/`

## Build and deploy

A `release` build needs a release keystore and signing properties:

- Put the `release.keystore` file in the root `config` folder.
- Copy `signing.properties.sample` to `signing.properties`. Fill in the real values.
- On CI, write both `signing.properties` and `config/release.keystore` before a `release` build.

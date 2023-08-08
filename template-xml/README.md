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
$ ./gradlew koverMergedHtmlReport
```

Report is located at: `.app/build/reports/kover/`

## Build and deploy

For `release` builds, we need to provide release keystore and signing properties:

- Put the `release.keystore` file at root `config` folder.
- Put keystore signing properties in `signing.properties`

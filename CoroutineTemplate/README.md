# Android Templates: Coroutine
- Our optimized Android templates used in our android projects

### Setup
- Clone the project
- Run the project with Android Studio

### Linter and static code analysis

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

### Testing

- Run unit testing:

```
$ ./gradlew test
```

- Run unit testing with coverage:

```
$ ./gradlew jacocoTestReport
```

Report is located at: `./app/build/reports/jacoco/`

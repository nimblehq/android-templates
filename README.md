![Build Status](https://www.bitrise.io/app/4cbfc6308f5c80e5/status.svg?token=FXilfAPD1nT8M1QzlfBNqA&branch=kotlin)
# Android Templates: Kotlin
- Our optimized Android templates used in our kotlin android projects

### Setup
- Clone the project
- Checkout our main development branch `kotlin`
- Run the project with Android Studio


### Linter and static code analysis

1. Check Style:

```
$ ./gradlew checkStyle
```

Report is located at: `./app/build/reports/checkstyle/`

2. Detekt

```
$ ./gradlew detekt
```

Report is located at: `./build/reports/detekt.html`

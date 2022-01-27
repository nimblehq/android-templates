Welcome to the android-templates wiki!

This repository generates a new project based on our preferences, by running a simple script. 

Ex: `./newproject.sh -t crt -p co.myproject.example -n "MyProjectExampleCoroutine"`

This script must include all essentials by default, while optional features can be appended with flags. To prevent this repository from becoming a dumping playground, features can be rejected too.

Planning to contribute? Let's have a look at the criteria:

How do we decide if a feature is **essential**? 👍

- Our team requires it to maintain code quality.
- Our team requires it to maintain a good developer experience.
- Our team requires it to increase the productivity of the team.
- It is always used or configured in the projects we've worked on.
- It is recommended to use or configure based on Google's standards.

How do we decide if a feature is **optional**? 🚩

- It is regularly used or configured in projects we've worked on.
- It is regularly used or configured as an alternative to the default choice.

How do we decide if a feature is **rejected**? 👎

- It is barely used or configured in projects we've worked on.
- It is barely used or configured as an alternative to the default choice.

Let's have a look at some examples 🔎

**Essential**:
- [Detekt](https://github.com/detekt/detekt): Code smell analysis is important to maintain code quality.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): Dependency injection provides a good developer experience.
- [Firebase App Distribution](https://firebase.google.com/docs/app-distribution): Continuous delivery increases the productivity of the team.
- [Timber](https://github.com/JakeWharton/timber): A logging library which is always used in all the projects we've worked on.
- [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started): A suite of libraries for in-app navigation which is recommended by Google.

**Optional**:
- [Lottie](https://github.com/airbnb/lottie-android): Loading animations with a JSON file is regularly used, but not every project needs it.
- [Bitrise](https://www.bitrise.io/): A CI/CD provider which is regularly used, but [GitHub Actions](https://github.com/features/actions) is our default choice.

**Rejected**:
- [Skeleton Layout](https://github.com/Faltenreich/SkeletonLayout): A progress indicator with visual feedback, it has a specific use case that is barely used.
- [Mockito](https://github.com/mockito/mockito): A mocking framework which is barely used, because [Mockk](https://mockk.io/) is our default choice.

Keep in mind, the features are based on _our team's_ requirements. In case the client has different requirements or requests, we can consider adding them as optional features if they occur regularly.

Know that the above examples are not definitive as new and existing libraries keep on emerging and evolving.

Still unsure where your future contribution belongs? Let's discuss! 🚀
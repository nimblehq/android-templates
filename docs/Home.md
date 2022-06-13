Welcome to the android-templates wiki!

This repository generates a new project based on our preferences, by running a simple script. 

Example: `kscript new_project.kts package-name=co.myproject.example app-name="My Project"`

This script must include all essentials by default, while optional features can be appended with flags. To prevent this repository from becoming a dumping playground, features can be rejected too.

Planning to contribute? Let's have a look at the criteria:

How do we decide if a feature is **essential**? 👍

- It is always implemented in the projects we've worked on.
- It allows developers to avoid boilerplate setup, maintain the code quality and have good experience for themselves.
- It is a Google recommended component.

How do we decide if a feature is **optional**? 🚩

- It is regularly implemented in the project's we've worked on.
- It is an alternative to a newer dominant option.

How do we decide if a feature is **rejected**? 👎

- It is barely implemented in the project's we've worked on due to being a specific project requirement.
- It has been replaced completely by a newer dominant option.

Let's have a look at some examples 🔎

**Essential**:
- [Timber](https://github.com/JakeWharton/timber): A logging library which is always used in all the projects we've worked on.
- [Firebase App Distribution](https://firebase.google.com/docs/app-distribution): Continuous delivery always requires a boilerplate setup.
- [Detekt](https://github.com/detekt/detekt): Code smell analysis is important to maintain code quality.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): Dependency injection provides a good developer experience.
- [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started): A suite of libraries for in-app navigation which is recommended by Google.

**Optional**:
- [RxPermissions](https://github.com/tbruyelle/RxPermissions): Permissions powered by RxJava are regularly used, but not every project needs it.
- [Bitrise](https://www.bitrise.io/): A CI/CD provider which is regularly used, but [GitHub Actions](https://github.com/features/actions) is our default choice.

**Rejected**:
- [Skeleton Layout](https://github.com/Faltenreich/SkeletonLayout): A progress indicator with visual feedback, it has a specific use case that is barely used.
- [Mockito](https://github.com/mockito/mockito): A mocking framework which is barely used, because [Mockk](https://mockk.io/) is our default choice.

Keep in mind, the features are based on _our team's_ requirements. In case the client has different requirements or requests, we can consider adding them as optional features if they occur regularly.

Please note that the above examples are not definitive as new and existing libraries keep on emerging and evolving.

How do we vote on an issue? 🗳

- If we agree with the RFC, we must react with 👍.
- If we disagree with the RFC, we must react with 👎 and leave a comment explaining why.
- It is the responsibility of the RFC creator to label their proposed change as **essential** or **optional**.

Still unsure where your future contribution belongs? Let's discuss! 🚀

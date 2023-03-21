Welcome to the android-templates wiki!

This repository generates a new project based on our preferences, by running a simple script. 

Example: `kscript new_project.kts package-name=co.myxmlproject.example app-name="My XML Project" template=xml`

Planning to contribute? Let's have a look at the criteria:

How do we decide if a feature is **approved**? 👍

- It is always implemented in the projects we've worked on.
- It allows developers to avoid boilerplate setup, maintain the code quality and have good experience for themselves.
- It is a Google-recommended component.

How do we decide if a feature is **rejected**? 👎

- It is barely implemented in the project's we've worked on due to being a specific project requirement.
- It has been replaced completely by a newer dominant option.

Let's have a look at some examples 🔎

**Approved**:
- [Timber](https://github.com/JakeWharton/timber): A logging library which is always used in all the projects we've worked on.
- [Firebase App Distribution](https://firebase.google.com/docs/app-distribution): Continuous delivery always requires a boilerplate setup.
- [Detekt](https://github.com/detekt/detekt): Code smell analysis is important to maintain code quality.
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android): Dependency injection provides a good developer experience.
- [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started): A suite of libraries for in-app navigation which is recommended by Google.

**Rejected**:
- [Skeleton Layout](https://github.com/Faltenreich/SkeletonLayout): A progress indicator with visual feedback, it has a specific use case that is barely used.
- [Mockito](https://github.com/mockito/mockito): A mocking framework which is barely used, because [Mockk](https://mockk.io/) is our default choice.

Keep in mind, the features are based on _our team's_ requirements. In case the client has different requirements or requests, we can consider adding them as features if they occur regularly.

Please note that the above examples are not definitive as new and existing libraries keep on emerging and evolving.

Before an issue can be worked on, it should go through our voting process. 

1. Create an issue 💭 

- For features and chores, we should create an RFC with GitHub Discussions.
- For bugs, we should create an issue directly.

2. Vote on an issue? 🗳

- If we agree with one of the poll options, we should vote for that option.
- If we do not agree with any of the options, we should leave a comment explaining why. 
- If there are differing opinions, then the repository maintainer must resolve it.
- To allow sufficient time for everyone to participate, at least one week is allowed for voting.

3. Conclusion for approved/rejected issues? 🕐 

- A conclusion is reached if at least half of the team members cast their vote.
- If the majority vote to work on the issue, then the discussion is locked and an issue is created.
- If the majority vote to reject the issue, then the discussion is locked and no issue is created.
- If the vote is tied, then the repository maintainer must resolve it.

Still unsure where your future contribution belongs? Let's discuss! 🚀

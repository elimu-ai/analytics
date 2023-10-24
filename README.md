# elimu.ai Analytics 📊

Android application which collects, provides and uploads learning event data.

## Software Architecture

[
  <img width="320" alt="Software Architecture" src="https://user-images.githubusercontent.com/15718174/83595568-fb6a1e00-a594-11ea-990a-10c0bd62ed11.png">
](https://github.com/elimu-ai/wiki/blob/main/SOFTWARE_ARCHITECTURE.md)

## Learning Events

The various types of _learning events_ are handled by the Android receivers at [app/src/main/java/ai/elimu/analytics/receiver](app/src/main/java/ai/elimu/analytics/receiver).

A _learning event_ is an event without any corresponding testing of the student's mastery of a concept. For example; A student presses a word in a storybook and listens to its pronunciation.

  * `StoryBookLearningEvent`
  * `WordLearningEvent`
  * `LetterLearningEvent`

## Assessment Events

The various types of _assessment events_ are handled by the Android receivers at [app/src/main/java/ai/elimu/analytics/receiver](app/src/main/java/ai/elimu/analytics/receiver).

An _assessment event_ is an event that involves testing of whether or not the student is able to master a concept. For example; A word is sounded out, and the student is asked select the corresponding written text amongst a list of alternatives (multiple choice).

  * `WordAssessmentEvent`
  * `LetterAssessmentEvent`

## Development 👩🏽‍💻

Compile APK:

```
./gradlew clean build
```

Install APK:

```
adb install app/build/outputs/apk/debug/ai.elimu.analytics-<versionCode>-debug.apk
```

### Utils Library 📦

A [`utils`](utils) library (`.aar`) makes it easier for other Android apps to report learning/assessment events.

  * [`LearningEventUtil`](https://github.com/elimu-ai/analytics/blob/main/utils/src/main/java/ai/elimu/analytics/utils/LearningEventUtil.java)
  * [`AssessmentEventUtil`](https://github.com/elimu-ai/analytics/blob/main/utils/src/main/java/ai/elimu/analytics/utils/AssessmentEventUtil.java)

See https://jitpack.io/#elimu-ai/analytics/ for the latest version.

#### Utils Usage Sample

To use the `utils` library in another Android app, add the dependency in `app/build.gradle`:

```java
implementation 'com.github.elimu-ai:analytics:<version>@aar'
```

For an example of an app that is reporting learning events, see https://github.com/elimu-ai/vitabu:

  * https://github.com/elimu-ai/vitabu/blob/main/app/build.gradle#L51
  * https://github.com/elimu-ai/vitabu/blob/main/app/src/main/java/ai/elimu/vitabu/ui/storybook/ChapterFragment.java#L150


---

elimu.ai - Free personalized learning for every child on Earth 🌍🌏🌎

[Website 🌐](https://elimu.ai) | [Wiki 📃](https://github.com/elimu-ai/wiki#readme) | [Projects 👩🏽‍💻](https://github.com/elimu-ai/wiki/projects) | [Milestones 🎯](https://github.com/elimu-ai/wiki/milestones) | [Community 👋🏽](https://github.com/elimu-ai/wiki#open-source-community)

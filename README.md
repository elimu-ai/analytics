[![](https://jitpack.io/v/ai.elimu/analytics.svg)](https://jitpack.io/#ai.elimu/analytics)

# elimu.ai Analytics 📊

Android application which collects, provides and uploads learning event data.

## Software Architecture

[
  <img width="320" alt="Software Architecture" src="https://user-images.githubusercontent.com/15718174/83595568-fb6a1e00-a594-11ea-990a-10c0bd62ed11.png">
](https://github.com/elimu-ai/wiki/blob/main/SOFTWARE_ARCHITECTURE.md)

## Learning Events

The various types of _learning events_ are handled by the Android receivers at [`app/src/main/java/ai/elimu/analytics/receiver`](app/src/main/java/ai/elimu/analytics/receiver).

A _learning event_ is an event without any corresponding testing of the student's mastery of a concept. For example; A student presses a word in a storybook and listens to its pronunciation.

  * `StoryBookLearningEvent`
  * `WordLearningEvent`
  * `LetterSoundLearningEvent` (letter-sound correspondence)

## Assessment Events

The various types of _assessment events_ are handled by the Android receivers at [`app/src/main/java/ai/elimu/analytics/receiver`](app/src/main/java/ai/elimu/analytics/receiver).

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
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Utils Library 📦

A [`utils`](utils) library (`.aar`) makes it easier for other Android apps to report learning/assessment events.

* [`LearningEventUtil`](https://github.com/elimu-ai/analytics/blob/main/utils/src/main/java/ai/elimu/analytics/utils/LearningEventUtil.kt)
* [`AssessmentEventUtil`](https://github.com/elimu-ai/analytics/blob/main/utils/src/main/java/ai/elimu/analytics/utils/AssessmentEventUtil.kt)

See https://jitpack.io/#ai.elimu/analytics/ for the latest version.

<a name="utils-snapshot"></a>
### How to Test `-SNAPSHOT` Versions of the Utils Library

1. Publish the library to your local Maven repository:
    ```sh
    ./gradlew clean assemble utils:publishToMavenLocal
    ```
2. In the app that will be testing the `-SNAPSHOT` version of the library, add `mavenLocal()`:
    ```diff
    allprojects {
        repositories {
            google()
            mavenCentral()
            maven {
                url "https://jitpack.io"
            }
    +       mavenLocal()
        }
    }
    ```
3. Then change to your `-SNAPSHOT` version of the library:
    ```diff
    [versions]
    elimuModel = "model-2.0.101"
    -elimuAnalytics = "3.1.33"
    +elimuAnalytics = "3.1.34-SNAPSHOT"
    ```

#### Utils Usage Sample

> [!NOTE]
> To use the `utils` library in another Android app, add the dependency in `app/build.gradle`:

```java
implementation 'ai.elimu:analytics:<version>@aar'
```

For an example of an app that is reporting learning events, see https://github.com/elimu-ai/vitabu:

  * https://github.com/elimu-ai/vitabu/blob/main/app/build.gradle#L51
  * https://github.com/elimu-ai/vitabu/blob/main/app/src/main/java/ai/elimu/vitabu/ui/storybook/ChapterFragment.java#L150

### Database Migration 🔀

> [!IMPORTANT]
> When adding a new database `@Entity` (or modifying an existing one), you need to prepare a database 
migration (SQL script) in 
[`app/src/main/java/ai/elimu/analytics/db/RoomDb.java`](app/src/main/java/ai/elimu/analytics/db/RoomDb.java).

Follow these steps:

1. Add the new/modified `@Entity` to [`app/src/main/java/ai/elimu/analytics/entity/`](app/src/main/java/ai/elimu/analytics/entity/)
1. Include the entity in the `entities` section of the `@Database` in [`app/src/main/java/ai/elimu/analytics/db/RoomDb.java`](app/src/main/java/ai/elimu/analytics/db/RoomDb.java)
1. Bump the `@Database` version in [`app/src/main/java/ai/elimu/analytics/db/RoomDb.java`](app/src/main/java/ai/elimu/analytics/db/RoomDb.java)
1. Build the code with `./gradlew clean build`
1. Open the new database schema generated at `app/schemas/ai.elimu.analytics.db.RoomDb/<version>.json`
   - Under `entities`, find the matching `tableName` and copy its SQL script from the `createSql` property.
1. Open `RoomDb.java` and add a new method for the latest migration
   - Paste the SQL script from the above JSON schema, and replace `${TABLE_NAME}` with the name of the table you created/modified.
   - Include the migration in the `getDatabase` method in `RoomDb.java`.
1. To run the database migration, launch the application on your device.
1. To verify that your database migration ran successfully, look at the Logcat output and 
ensure that there are no RoomDb errors:
   ```
   2023-10-25 15:40:55.640 15303-914   RoomDb                  ai.elimu.analytics.debug             I  migrate (5 --> 6)
   2023-10-25 15:40:55.641 15303-914   RoomDb                  ai.elimu.analytics.debug             I  sql: CREATE TABLE IF NOT EXISTS 
   `LetterSoundCorrespondenceLearningEvent` (`letterSoundCorrespondenceLearningEventId` INTEGER, `androidId` TEXT NOT NULL, `packageName` TEXT NOT NULL, `time` 
   INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT)
   ```

> [!TIP]
> You can also use Android Studio's _Database Inspector_ to verify that the database 
migration succeeded:

![Database Inspector](https://github.com/elimu-ai/analytics/assets/1451036/64eb7aa2-40a3-4347-91b8-971b1f833241)

### Gradle Upgrade

```
./gradlew wrapper --gradle-version x.x.x
```

### Release 📦

To perform a release, follow these steps:

1. Merge your PR into the `main` branch
1. Wait for the ["Gradle Release"](https://github.com/elimu-ai/analytics/actions/workflows/gradle-release.yml) workflow to complete
1. Ensure that the new release version appears at https://github.com/elimu-ai/analytics/releases

> [!IMPORTANT]
> After you publish a new release, remember to also bump the version in all Android app repos that depend on the `utils` library:
> * https://github.com/elimu-ai/content-provider/blob/main/gradle/libs.versions.toml
> * https://github.com/elimu-ai/kukariri/blob/main/gradle/libs.versions.toml
> * https://github.com/elimu-ai/herufi/blob/main/gradle/libs.versions.toml
> * https://github.com/elimu-ai/vitabu/blob/main/gradle/libs.versions.toml
> * https://github.com/elimu-ai/filamu/blob/main/gradle/libs.versions.toml

---

<p align="center">
  <img src="https://github.com/elimu-ai/webapp/blob/main/src/main/webapp/static/img/logo-text-256x78.png" />
</p>
<p align="center">
  elimu.ai - Free open-source learning software for out-of-school children ✨🚀
</p>
<p align="center">
  <a href="https://elimu.ai">Website 🌐</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/elimu-ai/wiki#readme">Wiki 📃</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/orgs/elimu-ai/projects?query=is%3Aopen">Projects 👩🏽‍💻</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/elimu-ai/wiki/milestones">Milestones 🎯</a>
  &nbsp;•&nbsp;
  <a href="https://github.com/elimu-ai/wiki#open-source-community">Community 👋🏽</a>
  &nbsp;•&nbsp;
  <a href="https://www.drips.network/app/drip-lists/41305178594442616889778610143373288091511468151140966646158126636698">Support 💜</a>
</p>

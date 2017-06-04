## Gradle Import

To import the library, you first need to add our repository in app/build.gradle:

```
repositories {
    mavenLocal()
    maven {
        url "http://archiva.educativo.eu:8081/repository/internal/"
    }
}
```

Then, add the following dependencies:

```
dependencies {
   ...

   compile 'org.literacyapp.analytics:eventtracker:1.0.2'
}
```

## Report Learning Activity

To report learning activity, use one of the static methods provided by the EventTracker class:
EventTracker.reportLetterLearningEvent(getApplicationContext(), “a”);

Note that for the analytics data to be stored on the SD card, the Analytics application must be installed on the tablet. The log files will then appear under /sdcard/.literacyapp-analytics/events

### Sample Application

To see a sample of how to use the analytics library, go to https://github.com/literacyapp-org/literacyapp-android
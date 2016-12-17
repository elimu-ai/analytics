# literacyapp-analytics

## Initiate JobService

To initiate the ScreenshotJobService, use the following command:

`am startservice org.literacyapp.analytics/.service.ScreenshotJobService`

## Report usage activity

To notify this application about usage activity in another application, there are 2 different options:

1. Broadcast data using Android code
2. Broadcast data using shell command

### Android code example:

    Intent intent = new Intent();
    intent.setPackage("org.literacyapp.analytics");
    intent.setAction("literacyapp.intent.action.USAGE_EVENT");
    intent.putExtra("packageName", "org.package.name");
    intent.putExtra("literacySkill", "PHONEMIC_AWARENESS");
    intent.putExtra("letter", "a");
    sendBroadcast(intent);

### Shell command example:

    am broadcast
        -a literacyapp.intent.action.USAGE_EVENT
        -e packageName org.package.name
        -e literacySkill PHONEMIC_AWARENESS
        -e letter a

## Parameter names and values

### "packageName"

Set to the package name of the Android application performing the broadcast intent.

### "literacySkill"

For the `literacySkill` parameter, the following values are used for representing Early Grade Reading Assessment (EGRA) skills: https://github.com/literacyapp-org/literacyapp-model/blob/master/src/main/java/org/literacyapp/model/enums/content/LiteracySkill.java

### "numeracySkill"

For the `numeracySkill` parameter, the following values are used for representing the Early Grade Mathematics Assessment (EGMA): https://github.com/literacyapp-org/literacyapp-model/blob/master/src/main/java/org/literacyapp/model/enums/content/NumeracySkill.java

### "letter"

### "number"

### "word"
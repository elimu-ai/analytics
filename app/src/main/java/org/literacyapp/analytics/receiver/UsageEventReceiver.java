package org.literacyapp.analytics.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import org.literacyapp.model.enums.content.LiteracySkill;
import org.literacyapp.model.enums.content.NumeracySkill;

public class UsageEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(getClass().getName(), "onReceive");

        String packageName = intent.getStringExtra("packageName");
        Log.i(getClass().getName(), "packageName: " + packageName);

        String literacySkillExtra = intent.getStringExtra("literacySkill");
        Log.i(getClass().getName(), "literacySkillExtra: " + literacySkillExtra);
        if (!TextUtils.isEmpty(literacySkillExtra)) {
            LiteracySkill literacySkill = LiteracySkill.valueOf(literacySkillExtra);
            Log.i(getClass().getName(), "literacySkill: " + literacySkill);
        }

        String numeracySkillExtra = intent.getStringExtra("numeracySkill");
        Log.i(getClass().getName(), "numeracySkillExtra: " + numeracySkillExtra);
        if (!TextUtils.isEmpty(numeracySkillExtra)) {
            NumeracySkill numeracySkill = NumeracySkill.valueOf(numeracySkillExtra);
            Log.i(getClass().getName(), "numeracySkill: " + numeracySkill);
        }

        // TODO: add content, task type, task result, duration, etc.

        // TODO: store in database
    }
}

package ai.elimu.analytics.utils.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class ErrorResultReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(this::class.simpleName, "onReceive")

        val results: Bundle = getResultExtras(true)
        val errorMessage: String? = results.getString("errorMessage")
        errorMessage?.let {
            Log.e(this::class.simpleName, "errorMessage: ${errorMessage}")
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}

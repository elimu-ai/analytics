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
        val errorClassName: String? = results.getString("errorClassName")
        errorClassName?.let {
            Log.e(this::class.simpleName, "errorClassName: ${errorClassName}")
            Toast.makeText(context, "Error: ${errorClassName}", Toast.LENGTH_SHORT).show()
        }
    }
}
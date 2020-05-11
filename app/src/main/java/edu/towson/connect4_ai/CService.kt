package edu.towson.connect4_ai

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CService : JobService(), CoroutineScope{

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartJob(p0: JobParameters?): Boolean {

        val notif = createNotification()
        NotificationManagerCompat.from(this@CService).notify(NOTIF_ID, notif)

        MessageQueue.Channel.postValue("Connect 4 is fun !")

        //sendBroadcast(Intent(MyReceiver.IMAGE_ACTION))

        return true
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "C4Channel"
            val descriptionTxt = "Notification channel for prompting users to play."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionTxt
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun createNotification(): Notification {
        val intent = Intent(this, MainMenuActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText("Come play with us !")
            .setContentTitle("We miss you !")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    companion object {
        val TAG = CService::class.java.simpleName
        val CHANNEL_ID = "C4Channel"
        val NOTIF_ID = 1
    }
}
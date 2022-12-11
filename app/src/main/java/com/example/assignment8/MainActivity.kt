package com.example.assignment8

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    private lateinit var  button: Button
    private lateinit var  button2: Button

    companion object {
        private  val CHANNEL_ID = "MY_CHANNEL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = intent.extras?.getString("name", "")

        Log.d("TAG", "onCreate: $name")

        button = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)

        button2.setOnClickListener{

        }

        this.createNotificationChannel()


        // Create an Intent for the activity you want to start
        val resultIntent = Intent(this, MainActivity::class.java)
        intent.putExtra("NAME", "dfdf")

        // Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }


        val snoozeIntent = Intent(this, MainActivity::class.java)




        val snoozePendingIntent: PendingIntent =
            taskStackBuilder.create(this).run {
                // Add the intent, which inflates the back stack
                addNextIntentWithParentStack(snoozePendingIntent)
                // Get the PendingIntent containing the entire back stack
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

            }


        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
            .setContentTitle("Title")
            .setContentText("Message")
            .setContentIntent(resultPendingIntent)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background)))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.ic_launcher_background, "click me",
                snoozePendingIntent)

        button.setOnClickListener{

            val nbc = NotificationManagerCompat.from(this)
            nbc.notify(1, notification.build())

        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "NAME"
            val descriptionText = "Desc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = descriptionText

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
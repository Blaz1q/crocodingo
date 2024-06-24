package czerwone.krokodyle.czerwone_krokodyle;

import static android.content.Context.MODE_PRIVATE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.czerwone_krokodyle.R;

import java.util.Random;

public class NotificationReceiver extends BroadcastReceiver {
    public static final String SHARED_PREFS = "sharedPrefs";
    String[] Naglowki = {
            "Croco czeka na Ciebie!",
            "Nie opuszczaj Croco!",
            "Matematyczna przygoda czeka!",
            "Croco tęskni za Tobą!",
            "Pora na zabawę z matematyką!"
    };
    String[] Tekst = {"Pora wrócić i odkryć nowe wyzwania matematyczne razem z Croco! Sprawdź, co nowego czeka na Ciebie!",
            "Croco czuje się samotne bez Twojej obecności. Wróć do aplikacji i kontynuuj swoją matematyczną podróż!",
            "Croco zaprasza Cię na kolejną ekscytującą podróż w świat matematyki. Czekają na Ciebie fascynujące zadania i nagrody!",
            "Twoja ulubiona aplikacja matematyczna, Croco, czeka na Twój powrót. Niech Twoja przygoda z nauką matematyki trwa dalej!",
            "Nie pozwól, by matematyka była nudna! Wróć do Croco i przekonaj się, jak zabawna może być nauka!"
    };
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!shouldSendNotification(context)) {
            return; // Skip sending notification if disabled
        }
        NotificationManager mNotificationManager;
        String channelId = "CrocoChannel#1";
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/" + R.raw.hapaba2);
        Random random = new Random();
        int losuj = random.nextInt(5);

        // Create NotificationCompat.Builder
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(Naglowki[losuj])
                .setContentText(Tekst[losuj])
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(soundUri)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(Tekst[losuj])
                        .setBigContentTitle(Naglowki[losuj]));

        // Create an Intent for the notification tap action
        Intent ii = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, PendingIntent.FLAG_IMMUTABLE);
        mBuilder.setContentIntent(pendingIntent);

        // Get the Notification Manager
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // For Android O and above, create a Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "CrocoChannel#1",
                    NotificationManager.IMPORTANCE_DEFAULT);

            // Set the custom sound for the channel
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            channel.setSound(soundUri, audioAttributes);

            // Create the notification channel
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        // Show the notification
        if (mNotificationManager != null) {
            mNotificationManager.notify(0, mBuilder.build());
        }
    }
    private boolean shouldSendNotification(Context context) {
        // Retrieve the boolean value from preferences or database
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean("notification_enabled", true); // Default to true if not found
    }
}
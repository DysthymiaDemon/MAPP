package mapp.com.sg.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // id for Notification Channel
    public static final String NOTIFICATION_CHANNEL_ID = "10001";


    private BroadcastReceiver the_receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, "SMS Message Received!", duration);
            toast.show();
        }
    };

    private IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /****** Add the following Code ******/
        final Button notify = (Button) findViewById(R.id.notify);

        notify.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(),NOTIFICATION_CHANNEL_ID);
                builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
                Intent intent = new Intent(getBaseContext(), NotifyMessage.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent,0);
                builder.setContentIntent(pendingIntent);
                builder.setSmallIcon(R.drawable.stat_notify_chat);
                builder.setContentTitle("Notifications Title");
                builder.setContentText("Your notification content here.");
                //  builder.setSubText("Tap to view the website.");

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);

                    builder.setChannelId(NOTIFICATION_CHANNEL_ID);
                    notificationManager.createNotificationChannel(notificationChannel);
                }
                // Will display the notification in the notification bar
                notificationManager.notify(1, builder.build());
            }


        });
        /**** End of adding Code ****/

    }

    @Override
    protected void onResume(){

        // Register receiver if activity is in front
        this.registerReceiver(the_receiver, filter);
        super.onResume();
    }

    @Override
    protected void onPause(){

        // Unregister receiver if activity is not in front
        this.unregisterReceiver(the_receiver);
        super.onPause();


    }

}

package com.example.customnavigationdrawer;

import static com.example.customnavigationdrawer.App.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class ExampleService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

//        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
//        //broadcastIntent.putExtra("toastMessage", "123");
//        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
//                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        String thong_diep = "khẩu trang, khử khuẩn, khoảng cách, không tụ tập và khai báo y tế.";
        String tieu_de = "Tuân thủ tốt 5K vì bạn và mọi người xung quanh.";
        Intent scanIntent = new Intent(this, ScanActivity.class);
        scanIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent scanPendingIntent = PendingIntent.getActivity(this, 0, scanIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(tieu_de).setStyle(new NotificationCompat.BigTextStyle().bigText(tieu_de))
                .setContentText(thong_diep).setStyle(new NotificationCompat.BigTextStyle().bigText(thong_diep))
                .setSmallIcon(R.drawable.ic_action_on)
                .setContentIntent(pendingIntent).setColor(ContextCompat.getColor(this, R.color.purple_700))
                .addAction(R.drawable.ic_home, "Tìm người xung quanh", scanPendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    private void convert(){
        Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

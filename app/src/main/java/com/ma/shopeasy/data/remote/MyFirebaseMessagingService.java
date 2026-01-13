package com.ma.shopeasy.data.remote;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ma.shopeasy.R;
import com.ma.shopeasy.ui.MainActivity;

/**
 * Firebase Cloud Messaging Service
 * ✅ Security: Proper error handling, secure notification handling
 * ✅ Fixed: Unique notification IDs, proper PendingIntent flags
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FCM";
    private static final String CHANNEL_ID = "shopeasy_notifications";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        try {
            if (remoteMessage.getNotification() != null) {
                RemoteMessage.Notification notification = remoteMessage.getNotification();
                String title = notification.getTitle();
                String body = notification.getBody();

                // ✅ Validate notification content
                if (title != null && !title.isEmpty() && body != null && !body.isEmpty()) {
                    sendNotification(title, body);
                } else {
                    Log.w(TAG, "Invalid notification content");
                }
            } else if (!remoteMessage.getData().isEmpty()) {
                // Handle data-only messages
                Log.d(TAG, "Received data-only message: " + remoteMessage.getData());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing FCM message", e);
        }
    }

    /**
     * Send notification with unique ID and proper flags
     * ✅ Security: Unique notification ID, immutable PendingIntent
     */
    private void sendNotification(String title, String messageBody) {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            // ✅ Use unique ID for each notification (prevents overwriting)
            int uniqueNotificationId = (int) System.currentTimeMillis();

            // ✅ Use UPDATE_CURRENT with IMMUTABLE flags for security
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    uniqueNotificationId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            createNotificationChannel();

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.notify(uniqueNotificationId, notificationBuilder.build());
                Log.d(TAG, "Notification sent with ID: " + uniqueNotificationId);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to send notification", e);
        }
    }

    /**
     * Create notification channel for Android 8+
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "ShopEasy Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("ShopEasy order and promotional notifications");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * Called when new FCM token is generated
     * Should send token to backend for target messaging
     */
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "New FCM Token: " + token);
        // TODO: Send token to backend/Firestore for device registration
        // This allows sending targeted messages to specific devices
    }
}

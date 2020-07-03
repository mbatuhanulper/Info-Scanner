package com.dtsetr.scannerinfo;

import android.content.Intent;
import android.util.Log;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.huawei.hms.push.SendException;

import java.util.Arrays;

public class MyPushService extends HmsMessageService {
    private static final String TAG = "PushDemoLog";
//    private final static String CODELABS_ACTION = "com.dtsetr.scannerinfo.action";
    private final static String CODELABS_ACTION = "com.huawei.codelabpush.action";
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.i(TAG, "receive token:" + token);
    }
    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.i(TAG, "receive token: onMessageRecieved");
        if (message == null) {
            Log.e(TAG, "Received message entity is null!");
            return;
        }
        // getCollapseKey() Obtains the classification identifier (collapse key) of a message.
        // getData() Obtains valid content data of a message.
        // getMessageId() Obtains the ID of a message.
        // getMessageType() Obtains the type of a message.
        // getNotification() Obtains the notification data instance from a message.
        // getOriginalUrgency() Obtains the original priority of a message.
        // getSentTime() Obtains the time when a message is sent from the server.
        // getTo() Obtains the recipient of a message.

        Log.i(TAG, "getCollapseKey: " + message.getCollapseKey()
                + "\n getData: " + message.getData()
                + "\n getFrom: " + message.getFrom()
                + "\n getTo: " + message.getTo()
                + "\n getMessageId: " + message.getMessageId()
                + "\n getOriginalUrgency: " + message.getOriginalUrgency()
                + "\n getUrgency: " + message.getUrgency()
                + "\n getSendTime: " + message.getSentTime()
                + "\n getMessageType: " + message.getMessageType()
                + "\n getTtl: " + message.getTtl());

        // getBody() Obtains the displayed content of a message
        // getTitle() Obtains the title of a message
        // getTitleLocalizationKey() Obtains the key of the displayed title of a notification message
        // getTitleLocalizationArgs() Obtains variable parameters of the displayed title of a message
        // getBodyLocalizationKey() Obtains the key of the displayed content of a message
        // getBodyLocalizationArgs() Obtains variable parameters of the displayed content of a message
        // getIcon() Obtains icons from a message
        // getSound() Obtains the sound from a message
        // getTag() Obtains the tag from a message for message overwriting
        // getColor() Obtains the colors of icons in a message
        // getClickAction() Obtains actions triggered by message tapping
        // getChannelId() Obtains IDs of channels that support the display of messages
        // getImageUrl() Obtains the image URL from a message
        // getLink() Obtains the URL to be accessed from a message
        // getNotifyId() Obtains the unique ID of a message

        RemoteMessage.Notification notification = message.getNotification();
        if (notification != null) {
            Log.i(TAG, "\n getImageUrl: " + notification.getImageUrl()
                    + "\n getTitle: " + notification.getTitle()
                    + "\n getTitleLocalizationKey: " + notification.getTitleLocalizationKey()
                    + "\n getTitleLocalizationArgs: " + Arrays.toString(notification.getTitleLocalizationArgs())
                    + "\n getBody: " + notification.getBody()
                    + "\n getBodyLocalizationKey: " + notification.getBodyLocalizationKey()
                    + "\n getBodyLocalizationArgs: " + Arrays.toString(notification.getBodyLocalizationArgs())
                    + "\n getIcon: " + notification.getIcon()
                    + "\n getSound: " + notification.getSound()
                    + "\n getTag: " + notification.getTag()
                    + "\n getColor: " + notification.getColor()
                    + "\n getClickAction: " + notification.getClickAction()
                    + "\n getChannelId: " + notification.getChannelId()
                    + "\n getLink: " + notification.getLink()
                    + "\n getNotifyId: " + notification.getNotifyId());
        }

        Intent intent = new Intent();
        intent.setAction(CODELABS_ACTION);
        intent.putExtra("method", "onMessageReceived");
        intent.putExtra("msg", "onMessageReceived called, message id:" + message.getMessageId() + ", payload data:"
                + message.getData());

        sendBroadcast(intent);

        Boolean judgeWhetherIn10s = false;

        // If the messages are not processed in 10 seconds, the app needs to use WorkManager for processing.
        if (judgeWhetherIn10s) {
            startWorkManagerJob(message);
        } else {
            // Process message within 10s
            processWithin10s(message);
        }
    }

    private void startWorkManagerJob(RemoteMessage message) {
        Log.d(TAG, "Start new Job processing.");
    }
    private void processWithin10s(RemoteMessage message) {
        Log.d(TAG, "Processing now.");
    }



    @Override
    public void onMessageSent(String msgId) {
        Log.i(TAG, "onMessageSent called, Message id:" + msgId);
        Intent intent = new Intent();
        intent.setAction(CODELABS_ACTION);
        intent.putExtra("method", "onMessageSent");
        intent.putExtra("msg", "onMessageSent called, Message id:" + msgId);

        sendBroadcast(intent);
    }

    @Override
    public void onSendError(String msgId, Exception exception) {
        Log.i(TAG, "onSendError called, message id:" + msgId + ", ErrCode:"
                + ((SendException) exception).getErrorCode() + ", description:" + exception.getMessage());

        Intent intent = new Intent();
        intent.setAction(CODELABS_ACTION);
        intent.putExtra("method", "onSendError");
        intent.putExtra("msg", "onSendError called, message id:" + msgId + ", ErrCode:"
                + ((SendException) exception).getErrorCode() + ", description:" + exception.getMessage());

        sendBroadcast(intent);
    }
}

package com.umbrella.insurance.androidMessageForwarder;

import static com.umbrella.insurance.androidMessageForwarder.logger.RemoteLogger.callRemoteLogger;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.google.common.util.concurrent.ListenableFuture;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.umbrella.insurance.androidMessageForwarder.logger.LoggingMessage;

public class MainActivity extends Activity implements MessageListenerInterface {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1;
    private static final int MY_PERMISSIONS_REQUEST_SCHEDULE_EXACT_ALARM = 2;
    private static final int MY_PERMISSIONS_REQUEST_USE_EXACT_ALARM = 3;
    static Button sendBtn;
    static Button reconnectWSBtn;
    static Button disconnectWSBtn;
    static EditText txtPhoneNo;
    static EditText txtMessage;

    static EditText domain;
    static EditText port;
    static String phoneNo;
    static String message;
    static String twoFactorPhoneConnectionPassword = "324gsgdfs44tgsdfgsdfg44fsdg4gsdbcxvbxcv";

    public static WebSocketHandler webSocketHandler;
    public static MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtPhoneNo = (EditText) findViewById(R.id.phoneNumber);
        txtMessage = (EditText) findViewById(R.id.message);

        sendBtn = (Button) findViewById(R.id.btnSendSMS);

        domain = (EditText) findViewById(R.id.domain);
        port = (EditText) findViewById(R.id.port);

        reconnectWSBtn = (Button) findViewById(R.id.btnReconnectWS);
        disconnectWSBtn = (Button) findViewById(R.id.btnDisconnectWS);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });
        reconnectWSBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                connectToWS();
            }
        });
        disconnectWSBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                disconnectToWS();
            }
        });
        MessageBroadcastReceiver.bindListener(this);
        //connectToWS();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_EXACT_ALARM)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.USE_EXACT_ALARM)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.USE_EXACT_ALARM},
                        MY_PERMISSIONS_REQUEST_USE_EXACT_ALARM);
            }
        }

        //ListenableFuture <List<WorkInfo>> workInfo = WorkManager
        //        .getInstance(this).getWorkInfosByTag("com.umbrella.insurance.androidMessageForwarder.Scheduler");
        //WorkManager.getInstance(this).cancelAllWorkByTag("com.umbrella.insurance.androidMessageForwarder.Scheduler");

        WorkRequest schedulerWorkRequest =
                new OneTimeWorkRequest.Builder(Scheduler.class)
                        .build();
        WorkRequest schedulerWorkRequests =
                new PeriodicWorkRequest.Builder(Scheduler.class,
                        15, TimeUnit.MINUTES,
                        15, TimeUnit.MINUTES)
                        .build();
        WorkManager.getInstance(this).enqueue(schedulerWorkRequests);


    }

    public void disconnectToWS() {
        try {
            disconnectWSBtn.setEnabled(false);
            if (webSocketHandler != null &&
                    webSocketHandler.isOpen() &&
                    !webSocketHandler.isClosed() &&
                    !webSocketHandler.isClosing() &&
                    !webSocketHandler.isFlushAndClose()) {
                webSocketHandler.close();
            }
            reconnectWSBtn.setEnabled(true);
            txtMessage.setText("disconnected");
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);

        } catch (Exception e) {
            disconnectWSBtn.setEnabled(true);
            reconnectWSBtn.setEnabled(false);
        }


    }
    public static void connectToWS() {
        try {
            phoneNo = txtPhoneNo.getText().toString();
            message = txtMessage.getText().toString();
            reconnectWSBtn.setEnabled(true);
            String domainString = domain.getText().toString();
            String portString = port.getText().toString();
            //String domain = "192.168.2.100";
            //String port = "8081";
            String protocol = "ws";
            if(domain != null &&
                    !(domainString.contains("localhost") || domainString.contains("192.168"))) {
                        protocol = "wss";
            }
            String path = "phone";
            URI url = new URI(protocol + "://" + domainString + ":" + portString + "/" + path + "/"
                    + twoFactorPhoneConnectionPassword + "?env=TEST");
            webSocketHandler = new WebSocketHandler(url, new Handler());
            webSocketHandler.addHeader("User-Agent", "android-message-forwarder");
            webSocketHandler.connect();
            //webSocketHandler.send("ping");
            //txtPhoneNo = txtPhoneNo;
            //txtMessage = txtMessage;
            reconnectWSBtn = reconnectWSBtn;
            txtMessage.setText("connected");
            reconnectWSBtn.setEnabled(false);
            disconnectWSBtn.setEnabled(true);
        } catch (Exception e) {
            LoggingMessage loggingMessage = new LoggingMessage();
            loggingMessage.setAppName("android-message-forwarder");
            loggingMessage.setLogLevel("ERROR");
            loggingMessage.setCallingMethod("connectToWS");
            loggingMessage.setCallingLoggerName("MainActivity.java");
            loggingMessage.setLoggingPayload(e.getMessage());
            callRemoteLogger(loggingMessage);
            txtMessage.setText(e.getMessage());
            reconnectWSBtn.setEnabled(true);
        }
    }

    protected void sendSMSMessage() {
        phoneNo = txtPhoneNo.getText().toString();
        message = txtMessage.getText().toString();

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        } else {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "SMS Receive Permission success.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS Receive permission failed, please try again.", Toast.LENGTH_LONG).show();
                }
                break;
            case MY_PERMISSIONS_REQUEST_SCHEDULE_EXACT_ALARM:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(), "Exact Alarm Scheduled",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Schedule Exact Alarm failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            case MY_PERMISSIONS_REQUEST_USE_EXACT_ALARM:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(), "Use Alarm Scheduled",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Use Exact Alarm failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
        }
    }

    @Override
    public void messageReceived(String message) {
        // setting message in our text view on below line.
        //txtMessage.setHint(message);
        //System.out.println(message);
        txtMessage.setText(message);
    }
}
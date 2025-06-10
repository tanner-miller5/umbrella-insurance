package com.umbrella.insurance.androidMessageForwarder;

import static com.umbrella.insurance.androidMessageForwarder.MainActivity.connectToWS;
import static com.umbrella.insurance.androidMessageForwarder.MainActivity.reconnectWSBtn;
import static com.umbrella.insurance.androidMessageForwarder.MainActivity.txtMessage;
import static com.umbrella.insurance.androidMessageForwarder.MainActivity.txtPhoneNo;
import static com.umbrella.insurance.androidMessageForwarder.MainActivity.webSocketHandler;
import static com.umbrella.insurance.androidMessageForwarder.logger.RemoteLogger.callRemoteLogger;

import android.os.Handler;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

import com.umbrella.insurance.androidMessageForwarder.logger.LoggingMessage;


class WebSocketHandler extends WebSocketClient {
    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    private final Handler handler;

    public WebSocketHandler(URI serverUri, Handler handler) {

        super(serverUri);
        this.handler = handler;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        String phoneNumber = "";
        String msg = "initial connect";
        send(phoneNumber + ":" + msg);
    }

    @Override
    public void onMessage(String message) {
        try {
            String[] messageSplit = message.split(":");

            if (messageSplit.length == 2) {
                String phoneNumber = messageSplit[0];
                String msg = messageSplit[1];
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
                // Post the UI updating code to our Handler
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        txtPhoneNo.setText(phoneNumber);
                        txtMessage.setText(msg);
                    }
                });
            } else {
                throw new Exception("invalid message:" + message);
            }
        } catch (Exception e) {
            LoggingMessage loggingMessage = new LoggingMessage();
            loggingMessage.setAppName("android-message-forwarder");
            loggingMessage.setLogLevel("ERROR");
            loggingMessage.setCallingMethod("onMessage");
            loggingMessage.setCallingLoggerName("WebSocketHandler.java");
            loggingMessage.setLoggingPayload(e.getMessage());
            callRemoteLogger(loggingMessage);
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        LoggingMessage loggingMessage = new LoggingMessage();
        loggingMessage.setAppName("android-message-forwarder");
        loggingMessage.setLogLevel("ERROR");
        loggingMessage.setLoggingPayload("websocket close code: " + code + ", reason: " + reason);
        callRemoteLogger(loggingMessage);
        handler.post(new Runnable() {
            @Override
            public void run() {
                txtPhoneNo.setText("");
                txtMessage.setText(code + ":" + reason);
                reconnectWSBtn.setEnabled(true);
                connectToWS();
            }
        });
    }

    @Override
    public void onError(Exception ex) {
        LoggingMessage loggingMessage = new LoggingMessage();
        loggingMessage.setAppName("android-message-forwarder");
        loggingMessage.setLogLevel("ERROR");
        loggingMessage.setCallingMethod("onError");
        loggingMessage.setCallingLoggerName("WebSocketHandler.java");
        loggingMessage.setLoggingPayload(ex.getMessage());
        callRemoteLogger(loggingMessage);
        handler.post(new Runnable() {
            @Override
            public void run() {
                txtPhoneNo.setText("");
                txtMessage.setText(ex.getMessage());
                reconnectWSBtn.setEnabled(true);
                connectToWS();
            }
        });
    }
}
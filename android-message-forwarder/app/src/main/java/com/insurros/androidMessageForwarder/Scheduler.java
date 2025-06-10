package com.umbrella.insurance.androidMessageForwarder;

import static com.umbrella.insurance.androidMessageForwarder.MainActivity.connectToWS;
import static com.umbrella.insurance.androidMessageForwarder.MainActivity.mainActivity;
import static com.umbrella.insurance.androidMessageForwarder.MainActivity.webSocketHandler;
import static com.umbrella.insurance.androidMessageForwarder.logger.RemoteLogger.callRemoteLogger;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.umbrella.insurance.androidMessageForwarder.logger.LoggingMessage;
import com.umbrella.insurance.androidMessageForwarder.logger.RemoteLogger;

public class Scheduler extends Worker {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);

    public Scheduler(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {

        // Do the work here--in this case, upload the images.
        //uploadImages();
        System.out.println("Scheduler running!");
        if(webSocketHandler == null || webSocketHandler.isClosed() || webSocketHandler.isClosing() ||
                webSocketHandler.isFlushAndClose()) {
            mainActivity.runOnUiThread(()->{
                connectToWS();
            });
        }
        String status = String.valueOf(webSocketHandler != null ? webSocketHandler.isOpen() : false);
        LoggingMessage loggingMessage = new LoggingMessage();
        loggingMessage.setAppName("android-message-forwarder");
        loggingMessage.setLogLevel("INFO");
        loggingMessage.setLoggingPayload("web socket isOpen: " + status);
        callRemoteLogger(loggingMessage);
        System.out.println("Scheduler Complete!");
        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}

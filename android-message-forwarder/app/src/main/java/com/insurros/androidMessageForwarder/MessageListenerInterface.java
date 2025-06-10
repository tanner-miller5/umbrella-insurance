package com.umbrella.insurance.androidMessageForwarder;

public interface MessageListenerInterface {
    // creating an interface method for messages received.
    void messageReceived(String message);
}

package com.umbrella.insurance.endpoints.ws;

import com.umbrella.insurance.core.models.communications.sentTexts.v1.db.jpa.SentTextService;
import com.umbrella.insurance.core.models.entities.SentText;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.entities.VerificationCode;
import com.umbrella.insurance.core.models.entities.VerificationMethod;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa.VerificationCodeService;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.VerificationMethodEnum;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa.VerificationMethodService;
import com.umbrella.insurance.util.Verification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.umbrella.insurance.core.constants.TimeConstants.UTC;
import static com.umbrella.insurance.core.utils.Security.PHONE_WS_CONNECTION_PASSWORD;
import static com.umbrella.insurance.core.utils.Security.WS_PHONE_ENDPOINT;

public class SocketTextHandler extends TextWebSocketHandler {
    public static final String SENDER_PHONE_NUMBER = "";
    private static final Logger logger = LoggerFactory.getLogger(SocketTextHandler.class);
    public static WebSocketSession phoneSession;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        logger.info("Websocket message received message:{}, remoteAddress:{}, path:{}",
                message, session.getRemoteAddress(), session.getUri().getPath());
        if (!session.isOpen()) {
            return;
        }
        if (session.getUri().getPath().endsWith(WS_PHONE_ENDPOINT + PHONE_WS_CONNECTION_PASSWORD)) {
            phoneSession = session;
        } else {
            String payload = message.getPayload();
            System.out.println("Received: " +payload);
            session.sendMessage(new TextMessage(payload));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                logger.error("Logging message: {}", e.getMessage());
            }
            //session.sendMessage(new TextMessage("Sending2: " + payload));
        }
    }

    public boolean sendVerificationText(String emailAddress,
                                        UserService userService,
                                        VerificationMethodService verificationMethodService,
                                        SentTextService sentTextService,
                                        VerificationCodeService verificationCodeService) {
        boolean success = false;
        try {

            //ReadJson readJson = new <SentTextRecord>ReadJson();
            //SentTextRecord e = new SentTextRecord();
            //SentTextRecord sentEmail = (SentTextRecord) readJson.readJsonToObj(WELCOME_EMAIL_PATH, e);

            User user = userService.getUserByEmailAddress(emailAddress).get();

            SentText sentText = new SentText();
            sentText.setRecipientPhoneNumber(user.getPhoneNumber());
            sentText.setSenderPhoneNumber(SENDER_PHONE_NUMBER);
            sentText.setSentDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));

            String verificationCodeString = Verification.generateVerificationCodeString();
            VerificationCode verificationCode = new VerificationCode();
            verificationCode.setVerificationCode(verificationCodeString);
            verificationCode.setIsVerified(false);



            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(UTC));
            ZonedDateTime nowPlus10Minutes = now.plusMinutes(10);
            ZonedDateTime nowPlus30Minutes = now.plusMinutes(30);
            Timestamp expirationDateTime = Timestamp.valueOf(nowPlus10Minutes.toLocalDateTime());
            verificationCode.setExpirationDateTime(expirationDateTime);

            VerificationMethod verificationMethod = verificationMethodService
                    .getVerificationMethodByVerificationMethodName(
                            VerificationMethodEnum.text.toString()).get();
            verificationCode.setVerificationMethod(verificationMethod);
            verificationCode.setIsVerified(false);
            verificationCode.setUser(user);
            verificationCode.setCurrentAttempt(0L);
            verificationCode.setMinutesToVerify(10L);
            verificationCode.setMaxAttempts(10L);
            String message = "Umbrella Insurance verification code " + verificationCodeString;
            sentText.setTextMessage(message);
            sentText = sentTextService.saveSentText(sentText);
            verificationCode = verificationCodeService.saveVerificationCode(
                    verificationCode);

            phoneSession.sendMessage(new TextMessage(user.getPhoneNumber() + ":" + message));
            success = true;
        } catch (Exception e) {
            logger.error("Logging message: {}", e.getMessage());
        }
        return success;
    }

    public void sendWelcomeText(String emailAddress,
                                UserService userService,
                                SentTextService sentTextService) {
        try {

            //ReadJson readJson = new <SentTextRecord>ReadJson();
            //SentTextRecord e = new SentTextRecord();
            //SentTextRecord sentEmail = (SentTextRecord) readJson.readJsonToObj(WELCOME_EMAIL_PATH, e);

            User user = userService.getUserByEmailAddress(emailAddress).get();

            SentText sentText = new SentText();
            sentText.setRecipientPhoneNumber(user.getPhoneNumber());
            sentText.setSenderPhoneNumber(SENDER_PHONE_NUMBER);
            sentText.setSentDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));

            String message = "Welcome to Umbrella Insurance!";
            sentText.setTextMessage(message);
            sentText = sentTextService.saveSentText(sentText);

            phoneSession.sendMessage(new TextMessage(user.getPhoneNumber() + ":" + message));

        } catch (Exception e) {
            logger.error("Logging message: {}", e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.error("Logging message: closeStatusCode: {}, closeStatusReason: {}", status.getCode(), status.getReason());
        super.afterConnectionClosed(session, status);
    }

}

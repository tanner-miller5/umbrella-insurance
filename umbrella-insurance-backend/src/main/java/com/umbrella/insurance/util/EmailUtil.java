/*
 * Copyright (c) 2022  Umbrella Insurance
 */

package com.umbrella.insurance.util;

import com.umbrella.insurance.core.models.communications.sentEmails.v1.db.jpa.SentEmailService;
import com.umbrella.insurance.core.models.entities.SentEmail;
import com.umbrella.insurance.core.models.entities.User;
import com.umbrella.insurance.core.models.entities.VerificationCode;
import com.umbrella.insurance.core.models.entities.VerificationMethod;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa.VerificationCodeService;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.VerificationMethodEnum;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa.VerificationMethodService;
import com.umbrella.insurance.core.utils.ReadJson;
import com.umbrella.insurance.endpoints.rest.users.v1.responses.SendEmailVerificationResponse;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static com.umbrella.insurance.core.constants.BotEmailConstants.*;
import static com.umbrella.insurance.core.constants.TemplateReplace.NEW_PASSWORD;
import static com.umbrella.insurance.core.constants.TemplateReplace.VERIFICATION_CODE_REPLACE;
import static com.umbrella.insurance.core.constants.TimeConstants.DATE_TIME_FORMAT;
import static com.umbrella.insurance.core.constants.TimeConstants.UTC;

/**
 * <p>EmailUtil class.</p>
 *
 * @author Marcus Miller
 * @version $Id: $Id
 * @since 0.0.19
 */
public class EmailUtil {
    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
    public static final String EMAIL_PATH = "email/emailVerification.json";
    public static final String WELCOME_EMAIL_PATH = "email/welcomeEmail.json";
    public static final String NEW_PASSWORD_EMAIL_PATH = "email/newPasswordEmail.json";

    public SendEmailVerificationResponse sendWelcomeEmail(String emailAddress,
                                                          UserService userService, SentEmailService sentEmailService) {
        try {
            Properties mailServerProperties = EmailUtil.getMailServerProperties();
            Session getMailSession;
            getMailSession = Session.getDefaultInstance(mailServerProperties, null);

            ReadJson readJson = new <SentEmail>ReadJson();
            SentEmail e = new SentEmail();
            SentEmail sentEmail = (SentEmail) readJson.readJsonToObj(WELCOME_EMAIL_PATH, e);

            MimeMessage generateMailMessage;
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailAddress));
            generateMailMessage.setSubject(sentEmail.getEmailSubject());
            generateMailMessage.setHeader(FEEDBACK_ID, EMAIL_FROM);
            sentEmail.setRecipientEmailAddress(emailAddress);
            sentEmail.setSenderEmailAddress(EMAIL_FROM);


            User user = userService.getUserByEmailAddress(emailAddress).get();
            //fixme need to add unsubscribe link and physical address
            String emailBody = sentEmail.getEmailBody();
            sentEmail.setEmailBody(emailBody);
            generateMailMessage.setContent(emailBody, sentEmail.getContentType());
            Transport transport = getMailSession.getTransport(SMTP);
            //

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect(SMTP_GMAIL_COM, EMAIL_FROM, EMAIL_FROM_PASSWORD);
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();

            sentEmail.setSentDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            SendEmailVerificationResponse sendEmailVerificationResponse = new SendEmailVerificationResponse();
            sendEmailVerificationResponse.setIsSuccess(true);

            sentEmail = sentEmailService.saveSentEmail(sentEmail);

            return sendEmailVerificationResponse;
        } catch (Exception e) {
            logger.error(e.getMessage());
            SendEmailVerificationResponse sendEmailVerificationResponse = new SendEmailVerificationResponse();
            sendEmailVerificationResponse.setIsSuccess(false);
            return sendEmailVerificationResponse;
        }
    }

    public boolean sendVerificationEmail(String emailAddress,
                                                Connection connection,
                                         VerificationMethodService verificationMethodService,
                                         UserService userService,
                                         SentEmailService sentEmailService,
                                         VerificationCodeService verificationCodeService) throws SQLException {
        Savepoint savepoint = null;
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();
            Properties mailServerProperties = EmailUtil.getMailServerProperties();
            Session getMailSession;
            getMailSession = Session.getDefaultInstance(mailServerProperties, null);

            ReadJson readJson = new <SentEmail>ReadJson();
            SentEmail e = new SentEmail();
            SentEmail sentEmail = (SentEmail) readJson.readJsonToObj(EMAIL_PATH, e);

            MimeMessage generateMailMessage;
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailAddress));
            generateMailMessage.setSubject(sentEmail.getEmailSubject());
            generateMailMessage.setHeader(FEEDBACK_ID, EMAIL_FROM);
            sentEmail.setRecipientEmailAddress(emailAddress);
            sentEmail.setSenderEmailAddress(EMAIL_FROM);

            String verificationCodeString = Verification.generateVerificationCodeString();
            VerificationCode verificationCode = new VerificationCode();
            verificationCode.setVerificationCode(verificationCodeString);
            VerificationMethod verificationMethod = verificationMethodService
                    .getVerificationMethodByVerificationMethodName(
                            VerificationMethodEnum.email.toString()).get();
            verificationCode.setVerificationMethod(verificationMethod);
            verificationCode.setIsVerified(false);
            User user = userService.getUserByEmailAddress(emailAddress).get();
            verificationCode.setUser(user);
            verificationCode.setCurrentAttempt(0L);
            //fixme need to add unsubscribe link and physical address
            String emailBody = sentEmail.getEmailBody().replace(VERIFICATION_CODE_REPLACE,
                    verificationCodeString);
            sentEmail.setEmailBody(emailBody);
            generateMailMessage.setContent(emailBody, sentEmail.getContentType());
            Transport transport = getMailSession.getTransport(SMTP);
            //

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect(SMTP_GMAIL_COM, EMAIL_FROM, EMAIL_FROM_PASSWORD);
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(UTC));
            sentEmail.setSentDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            ZonedDateTime nowPlus10Minutes = now.plusMinutes(10);
            ZonedDateTime nowPlus30Minutes = now.plusMinutes(30);
            Timestamp expirationDateTime = Timestamp.valueOf(nowPlus10Minutes.toLocalDateTime());
            verificationCode.setExpirationDateTime(expirationDateTime);
            verificationCode.setMinutesToVerify(10L);
            verificationCode.setMaxAttempts(10L);
            com.umbrella.insurance.core.models.entities.Session session = new com.umbrella.insurance.core.models.entities.Session();
            Timestamp endDateTime = Timestamp.valueOf(nowPlus30Minutes.toLocalDateTime());
            session.setEndDateTime(endDateTime);
            Timestamp startDateTime = Timestamp.valueOf(now.toLocalDateTime());
            session.setStartDateTime(startDateTime);
            session.setMinutesToExpire(30L);

            sentEmail = sentEmailService.saveSentEmail(sentEmail);
            verificationCode = verificationCodeService.saveVerificationCode(
                    verificationCode);
            connection.commit();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (connection != null) {
                connection.rollback(savepoint);
            }
            return false;
        }
    }

    public boolean sendResetPasswordEmail(String emailAddress, String newPassword,
                                                 SentEmailService sentEmailService) throws SQLException {
        try {
            Properties mailServerProperties = EmailUtil.getMailServerProperties();
            Session getMailSession;
            getMailSession = Session.getDefaultInstance(mailServerProperties, null);

            ReadJson readJson = new <SentEmail>ReadJson();
            SentEmail e = new SentEmail();
            SentEmail sentEmail = (SentEmail) readJson.readJsonToObj(NEW_PASSWORD_EMAIL_PATH, e);

            MimeMessage generateMailMessage;
            generateMailMessage = new MimeMessage(getMailSession);
            generateMailMessage.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailAddress));
            generateMailMessage.setSubject(sentEmail.getEmailSubject());
            generateMailMessage.setHeader(FEEDBACK_ID, EMAIL_FROM);
            sentEmail.setRecipientEmailAddress(emailAddress);
            sentEmail.setSenderEmailAddress(EMAIL_FROM);

            //fixme need to add unsubscribe link and physical address
            String emailBody = sentEmail.getEmailBody().replace(NEW_PASSWORD,
                    newPassword);
            sentEmail.setEmailBody(emailBody);
            generateMailMessage.setContent(emailBody, sentEmail.getContentType());
            Transport transport = getMailSession.getTransport(SMTP);
            //

            // Enter your correct gmail UserID and Password
            // if you have 2FA enabled then provide App Specific Password
            transport.connect(SMTP_GMAIL_COM, EMAIL_FROM, EMAIL_FROM_PASSWORD);
            transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
            transport.close();
            sentEmail.setSentDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));


            sentEmail = sentEmailService.saveSentEmail(sentEmail);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * <p>getMailServerProperties.</p>
     *
     * @return a {@link java.util.Properties} object
     */
    public static Properties getMailServerProperties() {
        Properties mailServerProperties;
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.starttls.required", "true");
        return mailServerProperties;
    }
}

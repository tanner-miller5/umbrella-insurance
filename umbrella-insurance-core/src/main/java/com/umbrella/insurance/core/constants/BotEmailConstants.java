package com.umbrella.insurance.core.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotEmailConstants {
    private static final Logger logger = LoggerFactory.getLogger(BotEmailConstants.class);

    /**
     * The email password of the email bot. Constant <code>EMAIL_PASSWORD="emailPassword"</code>
     */
    public static final String EMAIL_PASSWORD = "emailPassword";
    /**
     * The header name used for Google gmail. Constant <code>FEEDBACK_ID="Review-ID"</code>
     */
    public static final String FEEDBACK_ID = "Review-ID";
    /**
     * Gmail smtp server address. Constant <code>SMTP_GMAIL_COM="smtp.gmail.com"</code>
     */
    public static final String SMTP_GMAIL_COM = "smtp.gmail.com";
    /**
     * smtp protocol. Constant <code>SMTP="smtp"</code>
     */
    public static final String SMTP = "smtp";

    /**
     * Email address of email bot. Constant <code>EMAIL_FROM=""</code>
     */
    public static String EMAIL_FROM;
    /**
     * Password of email used to send emails. Constant <code>EMAIL_FROM_PASSWORD=""</code>
     */
    public static String EMAIL_FROM_PASSWORD;

    static {
        EMAIL_FROM = System.getenv().get("email");
        EMAIL_FROM_PASSWORD = System.getenv().get(EMAIL_PASSWORD);
    }
}

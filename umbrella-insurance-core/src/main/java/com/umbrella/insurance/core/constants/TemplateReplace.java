/*
 * Copyright (c) 2022-2023  Umbrella Insurance Inc.
 */

package com.umbrella.insurance.core.constants;

/**
 * <p>TemplateReplace class.</p>
 *
 * @author Marcus Miller
 * @version $Id: $Id
 * @since 1.47
 */
public class TemplateReplace {

    /**
     * Used to replace the verification code in the email template files. Constant
     * <code>VERIFICATION_CODE_REPLACE="VERIFICATION_CODE"</code>
     */
    public static final String VERIFICATION_CODE_REPLACE = "VERIFICATION_CODE";
    public static final String NEW_PASSWORD = "NEW_PASSWORD";

    /**
     * The value to be replaced in the user's emailtomms db column. emailtomms column example
     * number@mms.att.net. Constant
     * <code>NUMBER="number"</code>
     */
    public static final String PHONE_NUMBER = "number";

}

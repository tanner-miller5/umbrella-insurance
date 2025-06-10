package com.umbrella.insurance.endpoints.rest.users.v1.responses;

import com.umbrella.insurance.core.models.entities.ApplicationRole;
import com.umbrella.insurance.core.models.entities.Cart;
import com.umbrella.insurance.core.models.entities.User;
import lombok.Data;

@Data
public class SignInResponse {
    public String username;
    public String emailAddress;
    public String phoneNumber;
    public String firstName;
    public String lastName;
    public Boolean consentedToEmails;
    public Boolean consentedToTexts;
    public String twoFactorAuthType;
    public String userAgent;
    public String dateOfBirth;
    public Double escrowPlayAmount;
    public Double escrowRealAmount;
    public Double playAccountValue;
    public Double realAccountValue;
    public String sessionCode;
    private Boolean isEmailAddressVerified;
    private Boolean isPhoneNumberVerified;
    private Boolean isAuthAppVerified;
    private String authAppDataUrl;
    private ApplicationRole[] applicationRoles;
    private String endDateTime;
    private User user;
    private String backendAppVersion;
    private Cart cart;
}

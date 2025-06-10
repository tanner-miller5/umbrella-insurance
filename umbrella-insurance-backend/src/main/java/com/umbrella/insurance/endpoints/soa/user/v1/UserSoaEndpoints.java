package com.umbrella.insurance.endpoints.soa.user.v1;

import com.umbrella.insurance.core.constants.AccessEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.ApplicationRoleEnum;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.carts.v1.CartStatusEnum;
import com.umbrella.insurance.core.models.carts.v1.db.jpa.CartService;
import com.umbrella.insurance.core.models.communications.sentEmails.v1.db.jpa.SentEmailService;
import com.umbrella.insurance.core.models.communications.sentTexts.v1.db.jpa.SentTextService;
import com.umbrella.insurance.core.models.databases.v1.Database;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.environments.EnvironmentEnum;
import com.umbrella.insurance.core.models.exceptions.ConflictException;
import com.umbrella.insurance.core.models.exceptions.NotFoundException;
import com.umbrella.insurance.core.models.exceptions.NotImplementedException;
import com.umbrella.insurance.core.models.people.v1.db.jpa.PersonService;
import com.umbrella.insurance.core.models.reviews.v1.db.jpa.ReviewService;
import com.umbrella.insurance.core.models.units.v1.UnitEnum;
import com.umbrella.insurance.core.models.units.v1.db.jpa.UnitService;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.AccountBalanceTypeEnum;
import com.umbrella.insurance.core.models.users.accountBalances.accountBalanceTypes.v1.db.jpa.AccountBalanceTypeService;
import com.umbrella.insurance.core.models.users.accountBalances.v1.db.jpa.AccountBalanceService;
import com.umbrella.insurance.core.models.users.passwords.v1.db.jpa.PasswordService;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.totps.v1.db.jpa.TotpService;
import com.umbrella.insurance.core.models.users.userAgreements.v1.UserAgreementEnum;
import com.umbrella.insurance.core.models.users.userAgreements.v1.db.jpa.UserAgreementService;
import com.umbrella.insurance.core.models.users.v4.db.UserPrivilege;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.umbrella.insurance.core.models.users.verificationCodes.v1.db.jpa.VerificationCodeService;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.VerificationMethodEnum;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.jpa.VerificationMethodService;
import com.umbrella.insurance.core.utils.Security;
import com.umbrella.insurance.core.utils.TOTPUtils;
import com.umbrella.insurance.endpoints.rest.users.v1.requests.*;
import com.umbrella.insurance.endpoints.rest.users.v1.responses.*;
import com.umbrella.insurance.endpoints.ws.SocketTextHandler;
import com.umbrella.insurance.util.EmailUtil;
import jakarta.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.umbrella.insurance.core.models.users.v4.db.UserPrivilege.*;
import static com.umbrella.insurance.core.utils.Security.*;
import static com.umbrella.insurance.core.utils.TOTPUtils.createSeedUrl;
import static com.umbrella.insurance.core.utils.TOTPUtils.verifyOTP;
import static org.postgresql.util.PSQLState.UNIQUE_VIOLATION;

@Controller
@RequestMapping(UserPrivilege.SOA_PATH)
public class UserSoaEndpoints {
    private static final Logger logger = LoggerFactory.getLogger(UserSoaEndpoints.class);

    @Value("${info.app.version}")
    private String applicationVersion;

    @Autowired
    private UserService userService;

    @Autowired
    private PersonService personService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private VerificationMethodService verificationMethodService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private TotpService totpService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private UserAgreementService userAgreementService;

    @Autowired
    private AccountBalanceTypeService accountBalanceTypeService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private AccountBalanceService accountBalanceService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private SentEmailService sentEmailService;

    @Autowired
    private SentTextService sentTextService;

    private EmailUtil emailUtil = new EmailUtil();
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CartService cartService;

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = SIGN_OUT_PATH)
    @ResponseStatus(HttpStatus.OK)
    SignOutResponse signOut(
            @RequestParam String env,
            @RequestBody SignOutRequest signOutRequest,
            @RequestAttribute BigInteger currentRequestNumber,
            @RequestAttribute Device device,
            @RequestAttribute String access,
            @RequestAttribute String username,
            ServletRequest request) throws Exception {
        Connection connection = null;
        SignOutResponse signOutResponse = new SignOutResponse();
        try {
            request.setAttribute("requestBody", signOutRequest);
            if(!(access.equals(AccessEnum.USER.name()) ||
                    access.equals(AccessEnum.ADMIN.name()))) {
                throw new Exception("Incorrect access");
            } else if (access.equals(AccessEnum.USER.name())) {
                if(!username.equals(signOutRequest.getUsername())) {
                    throw new Exception("Incorrect access");
                }
            }
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            Optional<User> user = userService.getUserByUsername(
                    signOutRequest.getUsername());
            if(user.isEmpty()){
                throw new NotFoundException("Unable to read user");
            }
            Optional<Session> session = sessionService.getSessionByUserId(user.get().getId());
            if(session.isEmpty()) {
                throw new NotFoundException("Unable to read session");
            }
            session.get().setDidSignOut(true);

            sessionService.updateSession(session.get());
            signOutResponse.setIsSuccess(true);
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to sign out");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", signOutResponse);
        return signOutResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = SIGN_IN_PATH)
    @ResponseStatus(HttpStatus.OK)
    SignInResponse signIn(
            @RequestParam String env,
            @RequestBody SignInRequest signInRequest,
            @RequestAttribute String userAgent,
            @RequestAttribute BigInteger currentRequestNumber,
            @RequestAttribute Device device,
            ServletRequest request) throws Exception {
        Connection connection = null;
        SignInResponse signInResponse = new SignInResponse();
        try {
            request.setAttribute("requestBody", signInRequest);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            Optional<User> user = userService.getUserByUsername(
                    signInRequest.getUsername());
            if(user.isEmpty()){
                throw new NotFoundException("Unable to read user");
            }
            Optional<Person> person = personService.getPersonById(
                    user.get().getPerson().getId());
            if(person.isEmpty()){
                throw new NotFoundException("Unable to read person");
            }
            Optional<Password> password = passwordService.getPasswordByUserId(user.get().getId());
            if(password.isEmpty()){
                throw new NotFoundException("Unable to read password");
            }
            boolean isCorrectPassword = checkPassword(signInRequest.getPassword(),
                    password.get().getSalt(), password.get().getHashedPassword());
            if(!isCorrectPassword) {
                throw new Exception("Incorrect Password");
            } else {
                Optional<VerificationMethod> emailVerificationMethod = verificationMethodService.getVerificationMethodByVerificationMethodName(
                        VerificationMethodEnum.email.name());
                if(emailVerificationMethod.isEmpty()){
                    throw new NotFoundException("Unable to read email verification method");
                }
                Optional<VerificationMethod> appVerificationMethod = verificationMethodService.getVerificationMethodByVerificationMethodName(
                        VerificationMethodEnum.app.name());
                if(appVerificationMethod.isEmpty()){
                    throw new NotFoundException("Unable to read app verification method");
                }
                Optional<VerificationMethod> textVerificationMethod = verificationMethodService.getVerificationMethodByVerificationMethodName(
                        VerificationMethodEnum.text.name());
                if(textVerificationMethod.isEmpty()){
                    throw new NotFoundException("Unable to read text verification method");
                }
                Optional<VerificationMethod> noneVerificationMethod = verificationMethodService.getVerificationMethodByVerificationMethodName(
                        VerificationMethodEnum.none.name());
                if(noneVerificationMethod.isEmpty()){
                    throw new NotFoundException("Unable to read none verification method");
                }

                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                String twoFactorAuthType = "";
                if (user.get().getVerificationMethod().getId().equals(emailVerificationMethod.get().getId())) {
                    twoFactorAuthType = emailVerificationMethod.get().getVerificationMethodName();
                    if(user.get().getIsEmailAddressVerified()) {
                        Optional<VerificationCode> verificationCode = verificationCodeService.getVerificationCodeByUserId(
                                user.get().getId());
                        if(verificationCode.isEmpty()){
                            throw new NotFoundException("Unable to read verification code");
                        }
                        if(!(signInRequest.getVerificationCode().equals(verificationCode.get().getVerificationCode()) &&
                                verificationCode.get().getCurrentAttempt()
                                        .compareTo(verificationCode.get().getMaxAttempts()) <= 0 &&
                                timestamp.compareTo(verificationCode.get().getExpirationDateTime()) <= 0)) {
                            verificationCode.get().setIsVerified(false);
                            verificationCode.get().setCurrentAttempt(
                                    verificationCode.get().getCurrentAttempt() + 1L);
                            verificationCodeService.updateVerificationCode(verificationCode.get());
                            throw new Exception("Incorrect Verification Code");
                        } else {
                            verificationCode.get().setIsVerified(true);
                            verificationCodeService.updateVerificationCode(
                                    verificationCode.get());
                        }
                    }
                } else if (user.get().getVerificationMethod().getId().equals(appVerificationMethod.get().getId())) {
                    Optional<Totp> totp = totpService.getTotpByUserId(user.get().getId());
                    if(totp.isEmpty()){
                        throw new NotFoundException("Unable to read totp");
                    }
                    signInResponse.setAuthAppDataUrl(totp.get().getTotpCode());
                    twoFactorAuthType = appVerificationMethod.get().getVerificationMethodName();
                    if(user.get().getIsAuthAppVerified()) {
                        if(!(TOTPUtils.verifyOTP(totp.get().getTotpCode(), signInRequest.getTotp()))) {
                            throw new Exception("Incorrect TOTP Code");
                        }
                    }
                } else if (user.get().getVerificationMethod().getId().equals(textVerificationMethod.get().getId())) {
                    twoFactorAuthType = textVerificationMethod.get().getVerificationMethodName();
                    Optional<VerificationCode> verificationCode = verificationCodeService.getVerificationCodeByUserId(
                            user.get().getId());
                    if(verificationCode.isEmpty()){
                        throw new NotFoundException("Unable to read verification code");
                    }
                    if(user.get().getIsPhoneNumberVerified()) {
                        if(!(signInRequest.getVerificationCode().equals(verificationCode.get().getVerificationCode()) &&
                                verificationCode.get().getCurrentAttempt()
                                        .compareTo(verificationCode.get().getMaxAttempts()) <= 0 &&
                                timestamp.compareTo(verificationCode.get().getExpirationDateTime()) <= 0)) {
                            verificationCode.get().setIsVerified(false);
                            verificationCode.get().setCurrentAttempt(verificationCode.get().getCurrentAttempt()
                                    + 1L);
                            verificationCodeService.updateVerificationCode(
                                    verificationCode.get());
                            throw new Exception("Incorrect Verification Code");
                        } else {
                            verificationCode.get().setIsVerified(true);
                            verificationCodeService.updateVerificationCode(
                                    verificationCode.get());
                        }
                    }
                } else if (user.get().getVerificationMethod().getId().equals(noneVerificationMethod.get().getId())) {
                    twoFactorAuthType = noneVerificationMethod.get().getVerificationMethodName();
                }
                //create session
                Session session = new Session();
                session.setDevice(device);
                session.setEndDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()).plusMinutes(30)));
                signInResponse.setEndDateTime(session.getEndDateTime().toString());
                session.setStartDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
                session.setMinutesToExpire(30L);
                session.setSessionCode(generateSessionId());
                session.setUser(user.get());
                session.setDidSignOut(false);
                session = sessionService.saveSession(session);

                Cart cart = new Cart();
                cart.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
                cart.setStage(CartStatusEnum.PENDING.name());
                cart.setUser(user.get());
                cart = cartService.saveCart(cart);

                signInResponse.setCart(cart);
                signInResponse.setSessionCode(session.getSessionCode());
                signInResponse.setDateOfBirth(person.get().getDateOfBirth().toString());
                signInResponse.setFirstName(person.get().getFirstName());
                signInResponse.setLastName(person.get().getSurname());
                signInResponse.setPhoneNumber(user.get().getPhoneNumber());
                signInResponse.setEmailAddress(user.get().getEmailAddress());
                signInResponse.setUsername(user.get().getUsername());
                UserAgreement emailConsentUserAgreement = userAgreementService.getUserAgreementByUserIdAndUserAgreementName(
                        user.get().getId(),
                        UserAgreementEnum.emailConsent.toString()).get();
                UserAgreement textConsentUserAgreement = userAgreementService.getUserAgreementByUserIdAndUserAgreementName(
                        user.get().getId(),
                        UserAgreementEnum.textConsent.toString()).get();
                signInResponse.setConsentedToEmails(emailConsentUserAgreement.getDidAgree());
                signInResponse.setConsentedToTexts(textConsentUserAgreement.getDidAgree());
                signInResponse.setTwoFactorAuthType(twoFactorAuthType);
                signInResponse.setUserAgent(userAgent);
                signInResponse.setIsEmailAddressVerified(user.get().getIsEmailAddressVerified());
                signInResponse.setIsPhoneNumberVerified(user.get().getIsPhoneNumberVerified());
                signInResponse.setIsAuthAppVerified(user.get().getIsAuthAppVerified());
                signInResponse.setUser(user.get());
                signInResponse.setBackendAppVersion(applicationVersion);
                Long escrowAccountBalanceId = -1L;
                Long balanceAccountBalanceId = -1L;
                List<AccountBalanceType> accountBalanceTypes = accountBalanceTypeService
                        .getAccountBalanceTypes();
                for (AccountBalanceType accountBalanceType : accountBalanceTypes) {
                    if (accountBalanceType.getAccountBalanceTypeName().equals(AccountBalanceTypeEnum.escrow.name())) {
                        escrowAccountBalanceId = accountBalanceType.getId();
                    } else if (accountBalanceType.getAccountBalanceTypeName().equals(AccountBalanceTypeEnum.balance.name())) {
                        balanceAccountBalanceId = accountBalanceType.getId();
                    }
                }

                Long butterBucksUnitId = -1L;
                Long usdUnitId = -1L;
                List<Unit> units = unitService
                        .getUnits();
                for (Unit unit : units) {
                    if (unit.getUnitName().equals(UnitEnum.butter_bucks.name())) {
                        butterBucksUnitId = unit.getId();
                    } else if (unit.getUnitName().equals(UnitEnum.usd.name())) {
                        usdUnitId = unit.getId();
                    }
                }

                List<AccountBalance> accountBalances = accountBalanceService.getAccountBalancesByUserId(
                        user.get().getId());
                for (AccountBalance accountBalance : accountBalances) {
                    if (accountBalance.getUnit().getId().equals(usdUnitId) &&
                            accountBalance.getAccountBalanceType().getId().equals(balanceAccountBalanceId)) {
                        signInResponse.setRealAccountValue(accountBalance.getAccountBalanceValue());
                    } else if (accountBalance.getUnit().getId().equals(butterBucksUnitId) &&
                            accountBalance.getAccountBalanceType().getId().equals(balanceAccountBalanceId)) {
                        signInResponse.setPlayAccountValue(accountBalance.getAccountBalanceValue());
                    } else if (accountBalance.getUnit().getId().equals(usdUnitId) &&
                            accountBalance.getAccountBalanceType().getId().equals(escrowAccountBalanceId)) {
                        signInResponse.setEscrowRealAmount(accountBalance.getAccountBalanceValue());
                    } else if (accountBalance.getUnit().getId().equals(butterBucksUnitId) &&
                            accountBalance.getAccountBalanceType().getId().equals(escrowAccountBalanceId)) {
                        signInResponse.setEscrowPlayAmount(accountBalance.getAccountBalanceValue());
                    }
                }
                List<UserApplicationRoleRelationship> userApplicationRoleRelationship = userApplicationRoleRelationshipService
                        .getUserApplicationRoleRelationshipsByUserId(
                        user.get().getId());
                ApplicationRole[] applicationRoles = new ApplicationRole[userApplicationRoleRelationship.size()];
                for(int i = 0; i < userApplicationRoleRelationship.size(); i++) {
                    applicationRoles[i] =
                            userApplicationRoleRelationship.get(i).getApplicationRole();
                }
                signInResponse.setApplicationRoles(applicationRoles);
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to sign in");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", signInResponse);
        return signInResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    CreateUserResponse createUser(
            @RequestParam String env,
            @RequestBody CreateUserRequest createUserRequest,
            @RequestAttribute BigInteger currentRequestNumber,
            @RequestAttribute Device device,
            ServletRequest request) throws Exception {
        Connection connection = null;
        CreateUserResponse userResponse = new CreateUserResponse();
        Savepoint savepoint = null;
        try {
            request.setAttribute("requestBody", createUserRequest);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();
            User user1 = new User();
            user1.setEmailAddress(createUserRequest.getEmailAddress());
            user1.setIsPhoneNumberVerified(false);
            user1.setIsEmailAddressVerified(false);
            user1.setIsAuthAppVerified(false);
            user1.setUsername(createUserRequest.getUsername());
            user1.setPhoneNumber(createUserRequest.getPhoneNumber());
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
            user1.setCreatedDateTime(timestamp);
            Optional<VerificationMethod> verificationMethod = Optional.empty();
            if(createUserRequest.getTwoFactorMethod() != null) {
                verificationMethod = verificationMethodService.getVerificationMethodByVerificationMethodName(
                        createUserRequest.getTwoFactorMethod());
                if(verificationMethod.isEmpty()) {
                    throw new NotFoundException("Unable to find verification method");
                }
                user1.setVerificationMethod(verificationMethod.get());
            }

            Person person = new Person();
            person.setDateOfBirth(createUserRequest.getDateOfBirth());
            person.setMiddleName(createUserRequest.getMiddleName());
            person.setSurname(createUserRequest.getSurname());
            person.setFirstName(createUserRequest.getFirstName());
            person.setSsn(createUserRequest.getSsn());

            person = personService.savePerson(person);
            user1.setPerson(person);
            user1 = userService.saveUser(user1);

            Cart cart = new Cart();
            cart.setCreatedDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            cart.setStage(CartStatusEnum.PENDING.name());
            cart.setUser(user1);
            cart = cartService.saveCart(cart);

            userResponse.setCart(cart);
            userResponse.setCreatedDateTime(user1.getCreatedDateTime());
            userResponse.setEmailAddress(user1.getEmailAddress());
            userResponse.setIsEmailAddressVerified(user1.getIsEmailAddressVerified());
            userResponse.setIsPhoneNumberVerified(user1.getIsPhoneNumberVerified());
            userResponse.setPhoneNumber(user1.getPhoneNumber());
            userResponse.setUser(user1);
            userResponse.setUsername(user1.getUsername());
            userResponse.setVerificationMethod(user1.getVerificationMethod());

            UserAgreement consentedToEmailsUserAgreement = new UserAgreement();
            consentedToEmailsUserAgreement.setUpdatedDateTime(timestamp);
            consentedToEmailsUserAgreement.setUserAgreementName(UserAgreementEnum.emailConsent.name());
            consentedToEmailsUserAgreement.setUserAgreementText("");
            consentedToEmailsUserAgreement.setDidAgree(createUserRequest.getConsentedToEmails());
            consentedToEmailsUserAgreement.setUser(user1);

            UserAgreement consentedToTextsUserAgreement = new UserAgreement();
            consentedToTextsUserAgreement.setUpdatedDateTime(timestamp);
            consentedToTextsUserAgreement.setUserAgreementName(UserAgreementEnum.textConsent.name());
            consentedToTextsUserAgreement.setUserAgreementText("");
            consentedToTextsUserAgreement.setDidAgree(createUserRequest.getConsentedToTexts());
            consentedToTextsUserAgreement.setUser(user1);

            UserAgreement consentedToTermsAndConditionsUserAgreement = new UserAgreement();
            consentedToTermsAndConditionsUserAgreement.setUpdatedDateTime(timestamp);
            consentedToTermsAndConditionsUserAgreement.setUserAgreementName(UserAgreementEnum.termsAndCondition.name());
            consentedToTermsAndConditionsUserAgreement.setUserAgreementText("");
            consentedToTermsAndConditionsUserAgreement.setDidAgree(createUserRequest.getConsentedToTermsAndConditions());
            consentedToTermsAndConditionsUserAgreement.setUser(user1);

            consentedToEmailsUserAgreement = userAgreementService.saveUserAgreement(consentedToEmailsUserAgreement);
            consentedToTextsUserAgreement = userAgreementService.saveUserAgreement(consentedToTextsUserAgreement);

            consentedToTermsAndConditionsUserAgreement = userAgreementService.saveUserAgreement(consentedToTermsAndConditionsUserAgreement);
            if (createUserRequest.getConsentedToEmails()) {
                EmailUtil emailUtil = new EmailUtil();
                emailUtil.sendWelcomeEmail(createUserRequest.getEmailAddress(), userService, sentEmailService);
            }
            if (createUserRequest.getConsentedToTexts()) {
                SocketTextHandler socketTextHandler = new SocketTextHandler();
                socketTextHandler.sendWelcomeText(createUserRequest.getEmailAddress(), userService, sentTextService);
            }
            if (verificationMethod.isPresent() &&
                    verificationMethod.get()
                            .getVerificationMethodName().equals(VerificationMethodEnum.app.toString())) {
                Totp totp = new Totp();
                String seedUrl = createSeedUrl();
                seedUrl = seedUrl.replace("{FIRST_NAME}", person.getFirstName());
                seedUrl = seedUrl.replace("{LAST_NAME}", person.getSurname());
                totp.setTotpCode(seedUrl);
                totp.setUser(user1);
                totp.setCreatedDateTime(timestamp);
                totp = totpService.saveTotp(totp);
                userResponse.setAuthAppDataUrl(seedUrl);
            }

            Optional<ApplicationRole> applicationRole = applicationRoleService.getApplicationRoleByApplicationRoleName(
                    ApplicationRoleEnum.customer.name()
            );
            if(applicationRole.isEmpty()) {
                throw new NotFoundException("Unable to find application role");
            }
            ApplicationRole[] applicationRoles = {applicationRole.get()};
            userResponse.setApplicationRoles(applicationRoles);

            UserApplicationRoleRelationship userApplicationRoleRelationship = new UserApplicationRoleRelationship();
            userApplicationRoleRelationship.setUser(user1);
            userApplicationRoleRelationship.setApplicationRole(applicationRole.get());
            userApplicationRoleRelationship = userApplicationRoleRelationshipService.saveUserApplicationRoleRelationship(
                    userApplicationRoleRelationship);

            Password password = new Password();
            password.setUser(user1);
            String salt = Security.getUserSalt();
            password.setHashedPassword(Security.hashedPassword(createUserRequest.getPassword(), salt));
            password.setSalt(salt);
            password.setCreatedDateTime(timestamp);
            password = passwordService.savePassword(password);

            Optional<Unit> usdUnit = unitService.getUnitByUnitName(
                    UnitEnum.usd.name()
            );
            if(usdUnit.isEmpty()) {
                throw new NotFoundException("Unable to find usd unit");
            }
            Optional<Unit> butterBucksUnit = unitService.getUnitByUnitName(
                    UnitEnum.butter_bucks.name()
            );
            if(butterBucksUnit.isEmpty()) {
                throw new NotFoundException("Unable to find butter bucks");
            }

            Optional<AccountBalanceType> balanceAccountBalanceType = accountBalanceTypeService.findAccountBalanceTypeByAccountBalanceTypeName(
                    AccountBalanceTypeEnum.balance.name()
            );
            if(balanceAccountBalanceType.isEmpty()) {
                throw new NotFoundException("Unable to find balance type");
            }
            Optional<AccountBalanceType> escrowAccountBalanceType = accountBalanceTypeService.findAccountBalanceTypeByAccountBalanceTypeName(
                    AccountBalanceTypeEnum.escrow.name()
            );
            if(escrowAccountBalanceType.isEmpty()) {
                throw new NotFoundException("Unable to find escrow type");
            }


            AccountBalance accountBalance = new AccountBalance();
            accountBalance.setAccountBalanceType(balanceAccountBalanceType.get());
            accountBalance.setAccountBalanceValue(0.0);
            accountBalance.setUser(user1);
            accountBalance.setUnit(usdUnit.get());
            accountBalance.setUpdatedDateTime(
                    Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            accountBalance = accountBalanceService.saveAccountBalance(
                    accountBalance);
            userResponse.setUsdAccountBalance(accountBalance);

            AccountBalance escrowAccountBalance = new AccountBalance();
            escrowAccountBalance.setAccountBalanceType(escrowAccountBalanceType.get());
            escrowAccountBalance.setAccountBalanceValue(0.0);
            escrowAccountBalance.setUser(user1);
            escrowAccountBalance.setUnit(usdUnit.get());
            escrowAccountBalance.setUpdatedDateTime(
                    Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            escrowAccountBalance = accountBalanceService.saveAccountBalance(
                    escrowAccountBalance);
            userResponse.setUsdEscrowAccountBalance(escrowAccountBalance);

            AccountBalance freeAccountBalance = new AccountBalance();
            freeAccountBalance.setAccountBalanceType(balanceAccountBalanceType.get());
            freeAccountBalance.setAccountBalanceValue(100.0);
            freeAccountBalance.setUser(user1);
            freeAccountBalance.setUnit(butterBucksUnit.get());
            freeAccountBalance.setUpdatedDateTime(
                    Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            freeAccountBalance = accountBalanceService.saveAccountBalance(
                    freeAccountBalance);
            userResponse.setButterBucksAccountBalance(freeAccountBalance);

            AccountBalance freeEscrowAccountBalance = new AccountBalance();
            freeEscrowAccountBalance.setAccountBalanceType(escrowAccountBalanceType.get());
            freeEscrowAccountBalance.setAccountBalanceValue(0.0);
            freeEscrowAccountBalance.setUser(user1);
            freeEscrowAccountBalance.setUnit(butterBucksUnit.get());
            freeEscrowAccountBalance.setUpdatedDateTime(
                    Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            freeEscrowAccountBalance = accountBalanceService.saveAccountBalance(
                    freeEscrowAccountBalance);
            userResponse.setButterBucksEscrowAccountBalance(freeEscrowAccountBalance);
            Session session = new Session();
            session.setDevice(device);
            session.setEndDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()).plusMinutes(30)));
            session.setStartDateTime(Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())));
            session.setMinutesToExpire(30l);
            userResponse.setEndDateTime(session.getEndDateTime().toString());
            session.setSessionCode(generateSessionId());
            session.setUser(user1);
            session.setDidSignOut(false);
            userResponse.setBackendAppVersion(applicationVersion);
            session = sessionService.saveSession(session);
            userResponse.setSessionCode(session.getSessionCode());
            connection.commit();
        } catch (SQLException e) {
            logger.error("Logging Request Number: {}, message: {}, sqlState: {}", currentRequestNumber,
                    e.getMessage(), e.getSQLState());
            if (connection != null) {
                connection.rollback(savepoint);
            }
            //https://www.postgresql.org/docs/current/errcodes-appendix.html
            if (e.getSQLState().equals(UNIQUE_VIOLATION)) {
                Database.closeConnection(connection);
                throw new ConflictException("user  unique violation");
            } else {
                Database.closeConnection(connection);
                throw new Exception("Unable to create user ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            if (connection != null) {
                connection.rollback(savepoint);
            }
            Database.closeConnection(connection);
            throw new Exception("Unable to create user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", userResponse);
        return userResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = SKIP_VERIFY_PATH)
    @ResponseStatus(HttpStatus.OK)
    SkipVerifyResponse skipVerify(
            @RequestParam String env,
            @RequestBody SkipVerifyRequest skipVerifyRequest,
            @RequestHeader String session,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        SkipVerifyResponse skipVerifyResponse = new SkipVerifyResponse();
        skipVerifyResponse.setIsSuccess(false);

        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            Optional<Session> session1 = sessionService
                    .getSessionBySessionCode(session);
            if(session1.isEmpty()) {
                throw new NotFoundException("Unable to find session");
            }
            Optional<User> user = userService.getUserByUserId(
                    session1.get().getUser().getId());
            if(user.isEmpty()) {
                throw new NotFoundException("Unable to find user");
            }
            Optional<VerificationMethod> verificationMethod = verificationMethodService.getVerificationMethodByVerificationMethodId(
                    user.get().getVerificationMethod().getId()
            );
            if(verificationMethod.isEmpty()) {
                throw new NotFoundException("Unable to find verification method");
            }
            Optional<VerificationMethod> noneVerificationMethod = verificationMethodService.getVerificationMethodByVerificationMethodName(
                    VerificationMethodEnum.none.toString()
            );
            if(noneVerificationMethod.isEmpty()) {
                throw new NotFoundException("Unable to find none verification method");
            }
            String twoFactorType = verificationMethod.get().getVerificationMethodName();
            if(twoFactorType.equals(VerificationMethodEnum.email.toString())) {
                if(user.get().getIsEmailAddressVerified()) {
                    throw new Exception("Email address already verified");
                } else {
                    user.get().setVerificationMethod(noneVerificationMethod.get());
                    userService.updateUser(user.get());
                    skipVerifyResponse.setIsSuccess(true);
                }
            } else if(twoFactorType.equals(VerificationMethodEnum.text.toString())) {
                if(user.get().getIsPhoneNumberVerified()) {
                    throw new Exception("Phone number already verified");
                } else {
                    user.get().setVerificationMethod(noneVerificationMethod.get());
                    userService.updateUser(user.get());
                    skipVerifyResponse.setIsSuccess(true);
                }
            } else if (twoFactorType.equals(VerificationMethodEnum.app.toString())) {
                if(user.get().getIsAuthAppVerified()) {
                    throw new Exception("Auth app already verified");
                } else {
                    user.get().setVerificationMethod(noneVerificationMethod.get());
                    userService.updateUser(user.get());
                    skipVerifyResponse.setIsSuccess(true);
                }
            } else {
                throw new Exception("Two factor already disabled.");
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", skipVerifyResponse);
        return skipVerifyResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = CHECK_TWO_FACTOR_TYPE_PATH)
    @ResponseStatus(HttpStatus.OK)
    CheckUserTwoFactorResponse checkUserTwoFactor(
            @RequestParam String env,
            @RequestBody CheckUserTwoFactorRequest checkUserTwoFactorRequest,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        CheckUserTwoFactorResponse checkUserTwoFactorResponse = new CheckUserTwoFactorResponse();
        String twoFactorType = VerificationMethodEnum.none.name();
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            Optional<User> user = Optional.empty();
            if (checkUserTwoFactorRequest.getUsername() != null) {
                user = userService.getUserByUsername(
                        checkUserTwoFactorRequest.getUsername());

            } else if (checkUserTwoFactorRequest.getPhoneNumber() != null) {
                user = userService.getUserByPhoneNumber(
                        checkUserTwoFactorRequest.getPhoneNumber());

            } else if (checkUserTwoFactorRequest.getEmailAddress() != null) {
                user = userService.getUserByEmailAddress(
                        checkUserTwoFactorRequest.getEmailAddress());
            }
            if (user.isEmpty()) {
                throw new NotFoundException("Unable to read user");
            } else {
                Optional<VerificationMethod> verificationMethod = verificationMethodService
                        .getVerificationMethodByVerificationMethodId(
                        user.get().getVerificationMethod().getId()
                );
                if(verificationMethod.isEmpty()) {
                    throw new NotFoundException("Unable to find verification method");
                }
                twoFactorType = verificationMethod.get().getVerificationMethodName();
                if(twoFactorType.equals(VerificationMethodEnum.email.toString())) {
                    if(user.get().getIsEmailAddressVerified()) {
                        emailUtil.sendVerificationEmail(user.get().getEmailAddress(), connection,
                                verificationMethodService, userService,
                                sentEmailService, verificationCodeService);
                    } else {
                        twoFactorType = VerificationMethodEnum.none.name();
                    }
                } else if(twoFactorType.equals(VerificationMethodEnum.text.toString())) {
                    if(user.get().getIsPhoneNumberVerified()) {
                        SocketTextHandler socketTextHandler = new SocketTextHandler();
                        socketTextHandler.sendVerificationText(user.get().getEmailAddress(),
                                userService, verificationMethodService,
                                sentTextService, verificationCodeService);
                    } else {
                        twoFactorType = VerificationMethodEnum.none.name();
                    }
                }
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } finally {
            Database.closeConnection(connection);
        }
        checkUserTwoFactorResponse
                .setTwoFactorAuthType(twoFactorType);
        request.setAttribute("responseBody", checkUserTwoFactorResponse);
        return checkUserTwoFactorResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = VERIFY_OTP_PATH)
    @ResponseStatus(HttpStatus.OK)
    VerifyOtpResponse verifyOtpEndpoint(
            @RequestBody VerifyOtpRequest verifyOtpRequest,
            @RequestParam String env,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        VerifyOtpResponse verifyOtpResponse = new VerifyOtpResponse();
        verifyOtpResponse.setIsOtpCorrect(false);
        Savepoint savepoint = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (verifyOtpRequest.getUsername() != null) {
                connection.setAutoCommit(false);
                savepoint = connection.setSavepoint();
                Optional<User> user = userService.getUserByUsername(
                        verifyOtpRequest.getUsername());
                if(user.isEmpty()) {
                    throw new NotFoundException("Unable to read user");
                }
                Optional<Totp> totp = totpService.getTotpByUserId(user.get().getId());
                if(totp.isEmpty()) {
                    throw new NotFoundException("Unable to read totp");
                }
                verifyOtpResponse.setIsOtpCorrect(verifyOTP(totp.get().getTotpCode(), verifyOtpRequest.getOtp()));
                user.get().setIsAuthAppVerified(verifyOtpResponse.getIsOtpCorrect());
                userService.updateUser(user.get());
                connection.commit();
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            if (connection != null) {
                connection.rollback(savepoint);
            }
            throw new Exception("Unable to read user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", false);
        return verifyOtpResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = CONFIRM_USER_EMAIL_PATH)
    @ResponseStatus(HttpStatus.OK)
    ConfirmUserEmailResponse confirmUserEmailEndpoint(
            @RequestBody ConfirmUserEmailRequest confirmUserEmailRequest,
            @RequestParam String env,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        ConfirmUserEmailResponse confirmUserEmailResponse = new ConfirmUserEmailResponse();
        confirmUserEmailResponse.setIsSuccess(false);
        Savepoint savepoint = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (confirmUserEmailRequest.getUsername() != null) {
                connection.setAutoCommit(false);
                savepoint = connection.setSavepoint();
                Optional<User> user = userService.getUserByUsername(
                        confirmUserEmailRequest.getUsername());
                if(user.isEmpty()) {
                    throw new NotFoundException("Unable to read user");
                }
                Optional<VerificationCode> verificationCode = verificationCodeService.getVerificationCodeByUserId(
                        user.get().getId());
                if (verificationCode.isEmpty()) {
                    throw new NotFoundException("Unable to read verification code");
                } else {
                    boolean isCorrect = verificationCode.get().getVerificationCode()
                            .equals(confirmUserEmailRequest.getVerificationCode());
                    confirmUserEmailResponse.setIsSuccess(isCorrect);
                    Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                    if(isCorrect &&
                            verificationCode.get().getCurrentAttempt()
                                    .compareTo(verificationCode.get().getMaxAttempts()) <= 0 &&
                            verificationCode.get().getExpirationDateTime().compareTo(timestamp) >= 0
                    ) {
                        user.get().setIsEmailAddressVerified(isCorrect);
                        userService.updateUser(user.get());
                        verificationCode.get().setVerifiedDateTime(timestamp);
                        verificationCode.get().setIsVerified(true);
                        verificationCode.get().setCurrentAttempt(verificationCode.get().getCurrentAttempt() + 1l);
                        verificationCodeService.updateVerificationCode(
                                verificationCode.get());
                        connection.commit();
                    } else {
                        verificationCode.get().setCurrentAttempt(verificationCode.get().getCurrentAttempt() + 1l);
                        verificationCodeService.updateVerificationCode(
                                verificationCode.get());
                        connection.commit();
                    }

                }
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            if (connection != null) {
                connection.rollback(savepoint);
            }
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            if (connection != null) {
                connection.rollback(savepoint);
            }
            throw new Exception("Unable to read user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", false);
        return confirmUserEmailResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = CONFIRM_USER_PHONE_PATH)
    @ResponseStatus(HttpStatus.OK)
    ConfirmUserPhoneResponse confirmUserPhoneEndpoint(
            @RequestBody ConfirmUserPhoneRequest confirmUserPhoneRequest,
            @RequestParam String env,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        ConfirmUserPhoneResponse confirmUserPhoneResponse = new ConfirmUserPhoneResponse();
        confirmUserPhoneResponse.setIsSuccess(false);
        Savepoint savepoint = null;
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (confirmUserPhoneRequest.getUsername() != null) {
                connection.setAutoCommit(false);
                savepoint = connection.setSavepoint();
                User user = userService.getUserByUsername(
                        confirmUserPhoneRequest.getUsername()).get();
                Optional<VerificationCode> verificationCode = verificationCodeService.getVerificationCodeByUserId(
                        user.getId());
                if(verificationCode.isEmpty()) {
                    throw new NotFoundException("Unable to read verification code");
                }
                boolean isCorrect = verificationCode.get().getVerificationCode()
                        .equals(confirmUserPhoneRequest.getVerificationCode());
                confirmUserPhoneResponse.setIsSuccess(isCorrect);
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                if(isCorrect &&
                        verificationCode.get().getCurrentAttempt().compareTo(verificationCode.get().getMaxAttempts()) <= 0 &&
                        verificationCode.get().getExpirationDateTime().compareTo(timestamp) >= 0
                ) {
                    user.setIsPhoneNumberVerified(true);
                    userService.updateUser(user);
                    verificationCode.get().setVerifiedDateTime(timestamp);
                    verificationCode.get().setIsVerified(true);
                    verificationCode.get().setCurrentAttempt(verificationCode.get().getCurrentAttempt() + 1l);
                    verificationCodeService.updateVerificationCode(
                            verificationCode.get());
                    connection.commit();
                } else {
                    verificationCode.get().setCurrentAttempt(verificationCode.get().getCurrentAttempt() + 1l);
                    verificationCodeService.updateVerificationCode(
                            verificationCode.get());
                    connection.commit();
                }
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            if (connection != null) {
                connection.rollback(savepoint);
            }
            throw new Exception("Unable to read user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", false);
        return confirmUserPhoneResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = SEND_EMAIL_VERIFICATION_PATH)
    @ResponseStatus(HttpStatus.OK)
    SendEmailVerificationResponse sendEmailVerificationEndpoint(
            @RequestBody SendEmailVerificationRequest sendEmailVerificationRequest,
            @RequestParam String env,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        SendEmailVerificationResponse sendEmailVerificationResponse = new SendEmailVerificationResponse();
        sendEmailVerificationResponse.setIsSuccess(false);
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (sendEmailVerificationRequest.getUsername() != null) {

                User user = userService.getUserByUsername(
                        sendEmailVerificationRequest.getUsername()).get();
                EmailUtil emailUtil = new EmailUtil();
                boolean success = emailUtil.sendVerificationEmail(user.getEmailAddress(), connection,
                        verificationMethodService, userService,
                        sentEmailService, verificationCodeService);
                sendEmailVerificationResponse.setIsSuccess(success);
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", false);
        return sendEmailVerificationResponse;
    }
    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            path = SEND_PHONE_VERIFICATION_PATH)
    @ResponseStatus(HttpStatus.OK)
    SendPhoneVerificationResponse sendPhoneVerificationEndpoint(
            @RequestBody SendPhoneVerificationRequest sendPhoneVerificationRequest,
            @RequestParam String env,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        SendPhoneVerificationResponse sendPhoneVerificationResponse = new SendPhoneVerificationResponse();
        sendPhoneVerificationResponse.setIsSuccess(false);
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (sendPhoneVerificationRequest.getUsername() != null) {

                Optional<User> user = userService.getUserByUsername(
                        sendPhoneVerificationRequest.getUsername());
                if (user.isEmpty()) {
                    throw new NotFoundException("Unable to read user ");
                } else {
                    SocketTextHandler socketTextHandler = new SocketTextHandler();
                    boolean success = socketTextHandler.sendVerificationText(user.get().getEmailAddress(),
                            userService, verificationMethodService,
                            sentTextService, verificationCodeService);
                    sendPhoneVerificationResponse.setIsSuccess(success);
                }
            }
        } catch(NotFoundException e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to read user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", false);
        return sendPhoneVerificationResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    UpdateUserResponse updateUsers(
            @RequestParam String env,
            @RequestBody UpdateUserRequest updateUserRequest,
            @RequestAttribute BigInteger currentRequestNumber,
            @RequestAttribute String access,
            @RequestHeader String session,
            @RequestAttribute String username,
            ServletRequest request) throws Exception {
        Connection connection = null;
        UpdateUserResponse updateUserResponse = new UpdateUserResponse();
        updateUserResponse.setIsSuccess(false);
        try {
            request.setAttribute("requestBody", updateUserRequest);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (updateUserRequest != null) {
                Optional<User> user = userService.getUserByUsername(
                        username);
                if(user.isEmpty()) {
                    throw new NotFoundException("Unable to read user ");
                }
                //fixme update user  and user agreements
                Optional<UserAgreement> emailUserAgreement = userAgreementService.getUserAgreementByUserIdAndUserAgreementName(
                        user.get().getId(), UserAgreementEnum.emailConsent.name());
                if(emailUserAgreement.isEmpty()) {
                    throw new NotFoundException("Unable to read user agreement");
                }
                Optional<UserAgreement> textUserAgreement = userAgreementService.getUserAgreementByUserIdAndUserAgreementName(
                        user.get().getId(), UserAgreementEnum.textConsent.name());
                if(textUserAgreement.isEmpty()) {
                    throw new NotFoundException("Unable to read user agreement");
                }
                if (updateUserRequest.getEmailAddress().isEmpty() ||
                        updateUserRequest.getPhoneNumber().isEmpty() ||
                        updateUserRequest.getUsername().isEmpty() ||
                        updateUserRequest.getConsentedToEmails() == null ||
                        updateUserRequest.getConsentedToTexts() == null) {
                    throw new Exception("""
                            Unable to update user. consented to emails, consented to texts, username, email address,
                            phone number, and username can't be empty""");
                }
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                if(!emailUserAgreement.get().getDidAgree().equals(updateUserRequest.getConsentedToEmails())) {
                    emailUserAgreement.get().setDidAgree(updateUserRequest.getConsentedToEmails());
                    emailUserAgreement.get().setUpdatedDateTime(timestamp);
                    userAgreementService.updateUserAgreement(emailUserAgreement.get());
                }
                if(!textUserAgreement.get().getDidAgree().equals(updateUserRequest.getConsentedToTexts())) {
                    textUserAgreement.get().setDidAgree(updateUserRequest.getConsentedToTexts());
                    textUserAgreement.get().setUpdatedDateTime(timestamp);
                    userAgreementService.updateUserAgreement(textUserAgreement.get());
                }
                if(!user.get().getEmailAddress().equals(updateUserRequest.getEmailAddress())) {
                    user.get().setEmailAddress(updateUserRequest.getEmailAddress());
                    user.get().setIsEmailAddressVerified(false);
                }
                if(!user.get().getPhoneNumber().equals(updateUserRequest.getPhoneNumber())) {
                    user.get().setPhoneNumber(updateUserRequest.getPhoneNumber());
                    user.get().setIsPhoneNumberVerified(false);
                }
                if(!user.get().getUsername().equals(updateUserRequest.getUsername())) {
                    user.get().setUsername(updateUserRequest.getUsername());
                }
                userService.updateUser(user.get());
                updateUserResponse.setIsSuccess(true);
            } else {
                throw new NotImplementedException("This update query is not implemented user ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", updateUserResponse);
        return updateUserResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = CHANGE_PASSWORD_PATH)
    @ResponseStatus(HttpStatus.OK)
    ChangePasswordResponse changePasswordEndpoint(
            @RequestParam String env,
            @RequestBody ChangePasswordRequest changePasswordRequest,
            @RequestAttribute BigInteger currentRequestNumber,
            @RequestAttribute String access,
            @RequestHeader String session,
            @RequestAttribute String username,
            ServletRequest request) throws Exception {
        Connection connection = null;
        ChangePasswordResponse changePasswordResponse = new ChangePasswordResponse();
        changePasswordResponse.setIsSuccess(false);
        try {
            request.setAttribute("requestBody", changePasswordRequest);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (changePasswordRequest != null) {
                Optional<Session> session1 = sessionService.getSessionBySessionCode(session);
                if(session1.isEmpty()) {
                    throw new NotFoundException("Unable to read session");
                }
                Optional<Password> password = passwordService.getPasswordByUserId(
                        session1.get().getUser().getId());
                if(password.isEmpty()) {
                    throw new NotFoundException("Unable to read password");
                }
                boolean passwordMatch = Security.checkPassword(changePasswordRequest.getExistingPassword(),
                        password.get().getSalt(), password.get().getHashedPassword());
                if (!passwordMatch) {
                    throw new Exception("existing password does not match");
                } else {
                    String hashedPassword = Security.hashedPassword(
                            changePasswordRequest.getNewPassword(), password.get().getSalt());
                    password.get().setHashedPassword(hashedPassword);
                    passwordService.updatePassword(
                            password.get());
                    changePasswordResponse.setIsSuccess(true);
                }
            } else {
                throw new NotImplementedException("This update query is not implemented user ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", changePasswordResponse);
        return changePasswordResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = CHANGE_TWO_FACTOR_AUTH_PATH)
    @ResponseStatus(HttpStatus.OK)
    ChangeTwoFactorAuthResponse changeTwoFactorEndpoint(
            @RequestParam String env,
            @RequestBody ChangeTwoFactorAuthRequest changeTwoFactorAuthRequest,
            @RequestAttribute BigInteger currentRequestNumber,
            @RequestAttribute String access,
            @RequestHeader String session,
            @RequestAttribute String username,
            ServletRequest request) throws Exception {
        Connection connection = null;
        ChangeTwoFactorAuthResponse changeTwoFactorAuthResponse = new ChangeTwoFactorAuthResponse();
        changeTwoFactorAuthResponse.setIsSuccess(false);
        try {
            request.setAttribute("requestBody", changeTwoFactorAuthRequest);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (changeTwoFactorAuthRequest != null) {
                Session session1 = sessionService.getSessionBySessionCode(session).get();
                Optional<Password> password = passwordService.getPasswordByUserId(
                        session1.getUser().getId());
                if(password.isEmpty()) {
                    throw new NotFoundException("Unable to read password");
                }
                boolean passwordMatch = Security.checkPassword(changeTwoFactorAuthRequest.getExistingPassword(),
                        password.get().getSalt(), password.get().getHashedPassword());
                if (!passwordMatch) {
                    throw new Exception("existing password does not match");
                } else {
                    Optional<User> user = userService.getUserByUserId(session1.getUser().getId());
                    if(user.isEmpty()) {
                        throw new NotFoundException("Unable to read user");
                    }
                    Optional<VerificationMethod> verificationMethod =  verificationMethodService.getVerificationMethodByVerificationMethodName(
                            changeTwoFactorAuthRequest.getTwoFactorAuthType());
                    if(verificationMethod.isEmpty()) {
                        throw new NotFoundException("Unable to read verification method");
                    } else {
                        user.get().setVerificationMethod(verificationMethod.get());
                        Optional<Person> person = personService.getPersonById(user.get().getPerson().getId());
                        if(person.isEmpty()) {
                            throw new NotFoundException("Unable to read person");
                        }
                        if(changeTwoFactorAuthRequest.getTwoFactorAuthType().equals(VerificationMethodEnum.app.name())) {
                            Totp totp = new Totp();
                            String seedUrl = createSeedUrl();
                            seedUrl = seedUrl.replace("{FIRST_NAME}", person.get().getFirstName());
                            seedUrl = seedUrl.replace("{LAST_NAME}", person.get().getSurname());
                            totp.setTotpCode(seedUrl);
                            totp.setUser(user.get());
                            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC()));
                            totp.setCreatedDateTime(timestamp);
                            totp = totpService.saveTotp(totp);
                            changeTwoFactorAuthResponse.setAuthAppDataUrl(seedUrl);
                        }
                        userService.updateUser(user.get());
                        changeTwoFactorAuthResponse.setIsSuccess(true);
                    }
                }
            } else {
                throw new NotImplementedException("This update query is not implemented user ");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update user");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", changeTwoFactorAuthResponse);
        return changeTwoFactorAuthResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = RESET_PASSWORD_PATH)
    @ResponseStatus(HttpStatus.OK)
    ResetPasswordResponse resetPasswords(
            @RequestParam String env,
            @RequestBody ResetPasswordRequest resetPasswordRequest,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
        resetPasswordResponse.setIsSuccess(false);
        try {
            request.setAttribute("requestBody", resetPasswordRequest);
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            if (resetPasswordRequest != null) {
                Optional<User> user = userService.getUserByUsername(
                        resetPasswordRequest.getUsername());
                if(user.isEmpty()) {
                    throw new NotFoundException("Unable to read user");
                }
                Optional<Password> password = passwordService.getPasswordByUserId(
                        user.get().getId());
                if(password.isEmpty()) {
                    throw new NotFoundException("Unable to read password");
                }
                String newPassword = generatePassword(10, password.get().getSalt().getBytes(StandardCharsets.UTF_8));
                String newHashedPassword = Security.hashedPassword(newPassword, password.get().getSalt());
                password.get().setHashedPassword(newHashedPassword);
                passwordService.updatePassword(password.get());
                EmailUtil emailUtil = new EmailUtil();
                boolean isSuccess = emailUtil.sendResetPasswordEmail(user.get().getEmailAddress(), newPassword,
                        sentEmailService);
                resetPasswordResponse.setIsSuccess(true);
            } else {
                throw new NotImplementedException("This update query is not implemented user");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to update user ");
        } finally {
            Database.closeConnection(connection);
        }
        request.setAttribute("responseBody", resetPasswordResponse);
        return resetPasswordResponse;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = DELETE_USER_PATH)
    @ResponseStatus(HttpStatus.OK)
    DeleteUserResponse deleteUsers(
            @RequestParam String env,
            @RequestBody DeleteUserRequest deleteUserRequest,
            @RequestAttribute String access,
            @RequestHeader String session,
            @RequestAttribute BigInteger currentRequestNumber,
            ServletRequest request) throws Exception {
        Connection connection = null;
        DeleteUserResponse deleteUserResponse = new DeleteUserResponse();
        deleteUserResponse.setIsSuccess(false);
        try {
            EnvironmentEnum envEnum = EnvironmentEnum.valueOf(env);
            connection = Database.createConnectionWithEnv(envEnum);
            Optional<Session> session1 = sessionService.getSessionBySessionCode(session);
            if(session1.isEmpty()) {
                throw new NotFoundException("Unable to read session");
            }
            Optional<Password> password = passwordService.getPasswordByUserId(
                    session1.get().getUser().getId());
            if(password.isEmpty()) {
                throw new NotFoundException("Unable to read password");
            }
            User user = session1.get().getUser();
            Person person = user.getPerson();
            boolean passwordMatch = Security.checkPassword(deleteUserRequest.getPassword(),
                    password.get().getSalt(), password.get().getHashedPassword());
            if(passwordMatch) {
                sessionService.deleteSessionByUserId(session1.get().getUser().getId());
                accountBalanceService.deleteAccountBalanceByUserId(session1.get().getUser().getId());
                passwordService.deletePasswordByUserId(session1.get().getUser().getId());
                userApplicationRoleRelationshipService.deleteUserApplicationRoleRelationship(user.getId());
                totpService.deleteTotpByUserId(user.getId());
                verificationCodeService.deleteVerificationCodeByUserId(user.getId());
                userAgreementService.deleteUserAgreementByUserId(user.getId());
                reviewService.deleteReviewByUserId(user.getId());
                cartService.deleteCartByUserId(user.getId());
                userService.deleteUser(
                        user.getId());
                personService.deletePerson(
                        person.getId());
                deleteUserResponse.setIsSuccess(true);
            } else {
                throw new NotImplementedException("Incorrect Password");
            }
        } catch (Exception e) {
            logger.error("Logging Request Number: {}, message: {}", currentRequestNumber, e.getMessage());
            throw new Exception("Unable to delete user");
        } finally {
            Database.closeConnection(connection);
        }
        return deleteUserResponse;
    }
}

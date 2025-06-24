/*
 * Copyright (c) 2022-2023  Umbrella Insurance
 */

package com.umbrella.insurance.core.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.umbrella.insurance.core.constants.HttpMethodEnum;
import com.umbrella.insurance.core.models.ads.v1.db.AdPrivilege;
import com.umbrella.insurance.core.models.announcements.v1.db.AnnouncementPrivilege;
import com.umbrella.insurance.core.models.appVersions.v1.db.AppVersionPrivilege;
import com.umbrella.insurance.core.models.applicationPrivileges.v1.db.jpa.ApplicationPrivilegeService;
import com.umbrella.insurance.core.models.applicationRoleApplicationPrivilegeRelationships.v1.db.jpa.ApplicationRoleApplicationPrivilegeRelationshipService;
import com.umbrella.insurance.core.models.applicationRoles.v1.db.jpa.ApplicationRoleService;
import com.umbrella.insurance.core.models.charities.v1.db.CharityPrivilege;
import com.umbrella.insurance.core.models.entities.*;
import com.umbrella.insurance.core.models.equipment.v1.db.EquipmentPrivilege;
import com.umbrella.insurance.core.models.exchangeRates.v1.db.ExchangeRatePrivilege;
import com.umbrella.insurance.core.models.faqs.v1.db.FaqPrivilege;
import com.umbrella.insurance.core.models.gameTypes.v1.db.GameTypePrivilege;
import com.umbrella.insurance.core.models.games.gameStatuses.v1.db.GameStatusPrivilege;
import com.umbrella.insurance.core.models.games.v1.db.GamePrivilege;
import com.umbrella.insurance.core.models.geographies.cities.v1.db.CityPrivilege;
import com.umbrella.insurance.core.models.geographies.continents.v1.db.ContinentPrivilege;
import com.umbrella.insurance.core.models.geographies.countries.v1.db.CountryPrivilege;
import com.umbrella.insurance.core.models.geographies.islands.v1.db.IslandPrivilege;
import com.umbrella.insurance.core.models.geographies.locations.v1.db.LocationPrivilege;
import com.umbrella.insurance.core.models.geographies.states.v1.db.StatePrivilege;
import com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.StreetAddressPrivilege;
import com.umbrella.insurance.core.models.geographies.zipCodes.v1.db.ZipCodePrivilege;
import com.umbrella.insurance.core.models.items.v1.db.ItemPrivilege;
import com.umbrella.insurance.core.models.leagues.conferences.divisions.v1.db.DivisionPrivilege;
import com.umbrella.insurance.core.models.leagues.conferences.v1.db.ConferencePrivilege;
import com.umbrella.insurance.core.models.leagues.v1.db.LeaguePrivilege;
import com.umbrella.insurance.core.models.levelOfCompetitions.v1.db.LevelOfCompetitionPrivilege;
import com.umbrella.insurance.core.models.logging.LoggingPrivilege;
import com.umbrella.insurance.core.models.orderTypes.v1.db.OrderTypePrivilege;
import com.umbrella.insurance.core.models.people.analysts.v1.db.AnalystPrivilege;
import com.umbrella.insurance.core.models.people.coaches.v1.db.CoachPrivilege;
import com.umbrella.insurance.core.models.people.players.v1.db.PlayerPrivilege;
import com.umbrella.insurance.core.models.people.referees.v1.db.RefereePrivilege;
import com.umbrella.insurance.core.models.people.v1.db.PersonPrivilege;
import com.umbrella.insurance.core.models.perils.v1.db.PerilPrivilege;
import com.umbrella.insurance.core.models.periods.periodTypes.v1.db.PeriodTypePrivilege;
import com.umbrella.insurance.core.models.periods.v1.db.PeriodPrivilege;
import com.umbrella.insurance.core.models.promotions.v1.db.PromotionPrivilege;
import com.umbrella.insurance.core.models.records.playerRecords.v1.db.PlayerRecordPrivilege;
import com.umbrella.insurance.core.models.records.teamRecords.v1.db.TeamRecordPrivilege;
import com.umbrella.insurance.core.models.records.v1.db.RecordPrivilege;
import com.umbrella.insurance.core.models.reviews.v1.db.ReviewPrivilege;
import com.umbrella.insurance.core.models.rosters.v1.db.RosterPrivilege;
import com.umbrella.insurance.core.models.schedules.v1.db.SchedulePrivilege;
import com.umbrella.insurance.core.models.seasons.seasonTypes.v1.db.SeasonTypePrivilege;
import com.umbrella.insurance.core.models.seasons.v1.db.SeasonPrivilege;
import com.umbrella.insurance.core.models.stats.playerStats.footballPlayerStats.v1.db.FootballPlayerStatsPrivilege;
import com.umbrella.insurance.core.models.stats.teamStats.footballTeamStats.v1.db.FootballTeamStatsPrivilege;
import com.umbrella.insurance.core.models.stats.v1.db.StatPrivilege;
import com.umbrella.insurance.core.models.teamMemberTypes.v1.db.TeamMemberTypePrivilege;
import com.umbrella.insurance.core.models.teamTransactions.teamTransactionTypes.v1.db.TeamTransactionTypePrivilege;
import com.umbrella.insurance.core.models.teamTransactions.v1.db.TeamTransactionPrivilege;
import com.umbrella.insurance.core.models.teams.teamTypes.v1.db.TeamTypePrivilege;
import com.umbrella.insurance.core.models.teams.v1.db.TeamPrivilege;
import com.umbrella.insurance.core.models.tournaments.tournamentTypes.v1.db.TournamentTypePrivilege;
import com.umbrella.insurance.core.models.tournaments.v1.db.TournamentPrivilege;
import com.umbrella.insurance.core.models.trophies.v1.db.TrophyPrivilege;
import com.umbrella.insurance.core.models.units.v1.db.UnitPrivilege;
import com.umbrella.insurance.core.models.userApplicationRoleRelationships.v1.db.jpa.UserApplicationRoleRelationshipService;
import com.umbrella.insurance.core.models.users.paymentTypes.v1.db.PaymentTypePrivilege;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyStates.v1.db.PendingPolicyStatePrivilege;
import com.umbrella.insurance.core.models.users.policies.pendingPolicies.pendingPolicyTypes.v1.db.PendingPolicyTypePrivilege;
import com.umbrella.insurance.core.models.users.sessions.v1.db.SessionPrivilege;
import com.umbrella.insurance.core.models.users.sessions.v1.db.jpa.SessionService;
import com.umbrella.insurance.core.models.users.v4.db.UserPrivilege;
import com.umbrella.insurance.core.models.users.v4.db.jpa.UserService;
import com.umbrella.insurance.core.models.users.verificationCodes.verificationMethods.v1.db.VerificationMethodPrivilege;
import com.umbrella.insurance.core.models.weeks.v1.db.WeekPrivilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>Security class.</p>
 *
 * @author Marcus Miller
 * @version $Id: $Id
 * @since 1.47
 */
@Service
public class Security {

    private static final Logger logger = LoggerFactory.getLogger(Security.class);
    public static final String WS_PHONE_ENDPOINT = "/phone/";
    public static final String WS_USER_ENDPOINT = "/userWs";
    public static final String PHONE_WS_CONNECTION_PASSWORD = "324gsgdfs44tgsdfgsdfg44fsdg4gsdbcxvbxcv";
    private static final int SALT_BYTE_LENGTH = 16;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserApplicationRoleRelationshipService userApplicationRoleRelationshipService;

    @Autowired
    private ApplicationRoleService applicationRoleService;

    @Autowired
    private ApplicationRoleApplicationPrivilegeRelationshipService applicationRoleApplicationPrivilegeRelationshipService;

    @Autowired
    private ApplicationPrivilegeService applicationPrivilegeService;

    private static String[] NO_SESSION_CODE_TABLES_ARRAY = new String[]{
            "/actuator/health" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/application.ready.time" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/application.started.time" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/disk.free" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/disk.total" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/executor.active" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/executor.completed" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/executor.pool.core" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/executor.pool.max" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/executor.pool.size" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/executor.queue.remaining" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/executor.queued" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/http.server.requests.active" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.buffer.count" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.buffer.memory.used" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.buffer.total.capacity" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.classes.loaded" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.classes.unloaded" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.compilation.time" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.gc.live.data.size" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.gc.max.data.size" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.gc.memory.allocated" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.gc.memory.promoted" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.gc.overhead" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.gc.pause" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.info" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.memory.committed" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.memory.max" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.memory.usage.after.gc" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.memory.used" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.threads.daemon" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.threads.live" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.threads.peak" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.threads.started" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/jvm.threads.states" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/logback.events" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/process.cpu.time" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/process.cpu.usage" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/process.files.max" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/process.files.open" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/process.start.time" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/process.uptime" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/system.cpu.count" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/system.cpu.usage" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/system.load.average.1m" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/tomcat.sessions.active.current" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/tomcat.sessions.active.max" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/tomcat.sessions.alive.max" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/tomcat.sessions.created" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/tomcat.sessions.expired" + ":" + HttpMethodEnum.GET,
            "/actuator/metrics/tomcat.sessions.rejected" + ":" + HttpMethodEnum.GET,


            "/actuator/prometheus" + ":" + HttpMethodEnum.GET,
            "/actuator/info" + ":" + HttpMethodEnum.GET,
            PersonPrivilege.PATH + ":" + HttpMethodEnum.POST,
            UserPrivilege.SOA_PATH + ":" + HttpMethodEnum.POST,
            SessionPrivilege.PATH + ":" + HttpMethodEnum.POST,
            AdPrivilege.PATH + ":" + HttpMethodEnum.GET,
            AnnouncementPrivilege.PATH + ":" + HttpMethodEnum.GET,
            FaqPrivilege.PATH + ":" + HttpMethodEnum.GET,
            CharityPrivilege.PATH + ":" + HttpMethodEnum.GET,
            EquipmentPrivilege.PATH + ":" + HttpMethodEnum.GET,
            ExchangeRatePrivilege.PATH + ":" + HttpMethodEnum.GET,
            GameStatusPrivilege.PATH + ":" + HttpMethodEnum.GET,
            GamePrivilege.PATH + ":" + HttpMethodEnum.GET,
            GameTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            CityPrivilege.PATH + ":" + HttpMethodEnum.GET,
            ContinentPrivilege.PATH + ":" + HttpMethodEnum.GET,
            CountryPrivilege.PATH + ":" + HttpMethodEnum.GET,
            IslandPrivilege.PATH + ":" + HttpMethodEnum.GET,
            LocationPrivilege.PATH + ":" + HttpMethodEnum.GET,
            StatePrivilege.PATH + ":" + HttpMethodEnum.GET,
            StreetAddressPrivilege.PATH + ":" + HttpMethodEnum.GET,
            ZipCodePrivilege.PATH + ":" + HttpMethodEnum.GET,
            LeaguePrivilege.PATH + ":" + HttpMethodEnum.GET,
            ConferencePrivilege.PATH + ":" + HttpMethodEnum.GET,
            DivisionPrivilege.PATH + ":" + HttpMethodEnum.GET,
            LevelOfCompetitionPrivilege.PATH + ":" + HttpMethodEnum.GET,
            OrderTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            AnalystPrivilege.PATH + ":" + HttpMethodEnum.GET,
            CoachPrivilege.PATH + ":" + HttpMethodEnum.GET,
            PlayerPrivilege.PATH + ":" + HttpMethodEnum.GET,
            RefereePrivilege.PATH + ":" + HttpMethodEnum.GET,
            PerilPrivilege.PATH + ":" + HttpMethodEnum.GET,
            PeriodPrivilege.PATH + ":" + HttpMethodEnum.GET,
            PeriodTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            PromotionPrivilege.PATH + ":" + HttpMethodEnum.GET,
            PlayerRecordPrivilege.PATH + ":" + HttpMethodEnum.GET,
            TeamRecordPrivilege.PATH + ":" + HttpMethodEnum.GET,
            RecordPrivilege.PATH + ":" + HttpMethodEnum.GET,
            PendingPolicyTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            PendingPolicyStatePrivilege.PATH + ":" + HttpMethodEnum.GET,
            RosterPrivilege.PATH + ":" + HttpMethodEnum.GET,
            SchedulePrivilege.PATH + ":" + HttpMethodEnum.GET,
            SeasonTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            SeasonPrivilege.PATH + ":" + HttpMethodEnum.GET,
            FootballPlayerStatsPrivilege.PATH + ":" + HttpMethodEnum.GET,
            FootballTeamStatsPrivilege.PATH + ":" + HttpMethodEnum.GET,
            TeamMemberTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            TeamTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            TeamPrivilege.PATH + ":" + HttpMethodEnum.GET,
            TeamTransactionTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            TeamTransactionPrivilege.PATH + ":" + HttpMethodEnum.GET,
            TournamentTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            TournamentPrivilege.PATH + ":" + HttpMethodEnum.GET,
            TrophyPrivilege.PATH + ":" + HttpMethodEnum.GET,
            UnitPrivilege.PATH + ":" + HttpMethodEnum.GET,
            PaymentTypePrivilege.PATH + ":" + HttpMethodEnum.GET,
            VerificationMethodPrivilege.PATH + ":" + HttpMethodEnum.GET,
            WeekPrivilege.PATH + ":" + HttpMethodEnum.GET,
            UserPrivilege.SOA_PATH + UserPrivilege.CHECK_TWO_FACTOR_TYPE_PATH + ":" + HttpMethodEnum.POST,
            WS_PHONE_ENDPOINT + PHONE_WS_CONNECTION_PASSWORD + ":" + HttpMethodEnum.GET,
            LoggingPrivilege.PATH + ":" + HttpMethodEnum.POST,
            WS_USER_ENDPOINT + ":" + HttpMethodEnum.GET,
            UserPrivilege.SOA_PATH + UserPrivilege.VERIFY_OTP_PATH + ":" + HttpMethodEnum.POST,
            UserPrivilege.SOA_PATH + UserPrivilege.SIGN_IN_PATH + ":" + HttpMethodEnum.POST,
            UserPrivilege.SOA_PATH + UserPrivilege.SEND_EMAIL_VERIFICATION_PATH + ":" + HttpMethodEnum.POST,
            UserPrivilege.SOA_PATH + UserPrivilege.SEND_PHONE_VERIFICATION_PATH + ":" + HttpMethodEnum.POST,
            UserPrivilege.SOA_PATH + UserPrivilege.RESET_PASSWORD_PATH + ":" + HttpMethodEnum.PUT,
            StatPrivilege.PATH + ":" + HttpMethodEnum.GET,
            AppVersionPrivilege.PATH + ":" + HttpMethodEnum.GET,
            ReviewPrivilege.PATH + ":" + HttpMethodEnum.GET,
            ItemPrivilege.PATH + ":" + HttpMethodEnum.GET,
    };

    // create a list from the array
    private static ArrayList<String> NO_SESSION_CODE_TABLES_LIST = new ArrayList<String>(Arrays.asList(NO_SESSION_CODE_TABLES_ARRAY));

    public boolean checkPrivilege(
            String sessionCode, ApplicationPrivilege applicationPrivilege) throws SQLException {
        try {
            if(applicationPrivilege.getMethod().equals(HttpMethodEnum.OPTIONS.name())) {
                return true;
            } else if(NO_SESSION_CODE_TABLES_LIST.contains(applicationPrivilege.getPath()
                    + ":" + applicationPrivilege.getMethod())) {
                return true;
            }
            //get user id
            Optional<Session> session = sessionService.getSessionBySessionCode(sessionCode);
            if(session.isEmpty()) {
                logger.info("Session record equals null");
                return false;
            }
            //check if session is expired
            if (Timestamp.valueOf(LocalDateTime.now(Clock.systemUTC())).after(session.get().getEndDateTime())) {
                logger.info("Session expired");
                return false;
            }
            if(session.get().getDidSignOut()) {
                logger.info("Session signed out");
                return false;
            }
            Optional<User> user = userService.getUserByUserId(session.get().getUser().getId());
            if(user.isEmpty()) {
                logger.info("user record equals null");
                return false;
            }
            MDC.put("username", user.get().getUsername());
            MDC.put("email", user.get().getEmailAddress());
            MDC.put("phoneNumber", user.get().getPhoneNumber());
            //check user role
            List<UserApplicationRoleRelationship> userApplicationRoleRelationshipList = userApplicationRoleRelationshipService
                    .getUserApplicationRoleRelationshipsByUserId(session.get().getUser().getId());
            //check role privileges
            for(int i = 0; i < userApplicationRoleRelationshipList.size(); i++) {
                UserApplicationRoleRelationship userApplicationRoleRelationship = userApplicationRoleRelationshipList.get(i);
                Optional<ApplicationRoleApplicationPrivilegeRelationship> applicationRoleApplicationPrivilegeRelationship =
                        applicationRoleApplicationPrivilegeRelationshipService.findApplicationRoleApplicationPrivilegeRelationshipByApplicationRoleIdAndApplicationPrivilegePathAndApplicationPrivilegeMethod(
                                userApplicationRoleRelationship.getApplicationRole().getId(),
                                applicationPrivilege.getPath(),
                                applicationPrivilege.getMethod());

                if (applicationRoleApplicationPrivilegeRelationship.isPresent()) {
                    Optional<ApplicationPrivilege> applicationPrivilegeRecord1 = applicationPrivilegeService.findApplicationPrivilegeById(
                            applicationRoleApplicationPrivilegeRelationship.get().getApplicationPrivilege().getId()
                    );
                    if(applicationPrivilegeRecord1.isPresent()) {
                        MDC.put("access", applicationPrivilegeRecord1.get().getAccess());
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public static String generateSessionId() {
        return (new BigInteger(64, new Random())).toString();
    }

    /**
     * <p>hashedPassword.</p>
     *
     * @param password a {@link java.lang.String} object
     * @param userSalt a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     * @since 1.53
     */
    public static String hashedPassword(String password, String userSalt) {

        if (password == null) {
            return "";
        }
        try {
            MessageDigest md = null;
            BigInteger big = new BigInteger(userSalt, SALT_BYTE_LENGTH);
            byte[] salt = big.toByteArray();
            if (salt[0] == 0) {
                byte[] tmp = new byte[salt.length - 1];
                System.arraycopy(salt, 1, tmp, 0, tmp.length);
                salt = tmp;
            }
            md = MessageDigest.getInstance("SHA3-512");
            md.update(salt);
            StringBuilder sb = new StringBuilder();
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * <p>getUserSalt.</p>
     *
     * @return a {@link java.lang.String} object
     * @since 1.53
     */
    public static String getUserSalt() {
        String userSalt;
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_BYTE_LENGTH];
            random.nextBytes(salt);
            StringBuilder sb = new StringBuilder();
            for (byte b : salt) {
                sb.append(String.format("%02x", b));
            }
            userSalt = sb.toString();
            return userSalt;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * <p>generateKeyPair.</p>
     *
     * @param byteSize a int
     * @return a {@link java.security.KeyPair} object
     * @throws java.security.NoSuchAlgorithmException if any.
     * @since 1.53
     */
    public static KeyPair generateKeyPair(int byteSize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(byteSize);
        KeyPair pair = keyPairGen.generateKeyPair();
        return pair;
    }

    /**
     * <p>getPublicKey.</p>
     *
     * @param keyPair a {@link java.security.KeyPair} object
     * @return a {@link java.lang.String} object
     * @throws java.security.NoSuchAlgorithmException if any.
     * @since 1.53
     */
    public static String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * <p>getPublicKey.</p>
     *
     * @return a {@link java.lang.String} object
     * @throws java.security.NoSuchAlgorithmException if any.
     * @since 1.53
     */
    public static String getPublicKey() throws NoSuchAlgorithmException {
        String publicKeyString = System.getenv().get("publicKey");
        return publicKeyString;
    }

    /**
     * <p>getPrivateKey.</p>
     *
     * @param keyPair a {@link java.security.KeyPair} object
     * @return a {@link java.lang.String} object
     * @throws java.security.NoSuchAlgorithmException if any.
     * @since 1.53
     */
    public static String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * <p>encryptWithPubicKey.</p>
     *
     * @param valueToEncrypt a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     * @throws java.security.NoSuchAlgorithmException     if any.
     * @throws javax.crypto.NoSuchPaddingException        if any.
     * @throws java.security.InvalidKeyException          if any.
     * @throws javax.crypto.IllegalBlockSizeException     if any.
     * @throws javax.crypto.BadPaddingException           if any.
     * @throws java.security.spec.InvalidKeySpecException if any.
     * @throws java.io.UnsupportedEncodingException       if any.
     * @since 1.53
     */
    public static String encryptWithPubicKey(String valueToEncrypt)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException,
        BadPaddingException, InvalidKeySpecException {
        if (valueToEncrypt == null) {
            return "";
        }
        String publicKeyString = System.getenv().get("publicKey");
        byte[] bytes = Base64.getDecoder().decode(publicKeyString);
        byte[] encryptedBytesUtf8 = valueToEncrypt.getBytes(StandardCharsets.UTF_8);
        String string64 = Base64.getEncoder().encodeToString(encryptedBytesUtf8);
        byte[] encryptedBytes = Base64.getDecoder().decode(string64);

        PublicKey publicKey =
            KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = cipher.doFinal(encryptedBytes);
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * <p>encryptWithPubicKey.</p>
     *
     * @param valueToEncryptBoolean a boolean
     * @return a {@link java.lang.String} object
     * @throws java.security.NoSuchAlgorithmException     if any.
     * @throws javax.crypto.NoSuchPaddingException        if any.
     * @throws java.security.InvalidKeyException          if any.
     * @throws javax.crypto.IllegalBlockSizeException     if any.
     * @throws javax.crypto.BadPaddingException           if any.
     * @throws java.security.spec.InvalidKeySpecException if any.
     * @throws java.io.UnsupportedEncodingException       if any.
     * @since 1.53
     */
    public static String encryptWithPubicKey(boolean valueToEncryptBoolean)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException,
        BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException {
        String valueToEncrypt = null;
        if (valueToEncryptBoolean) {
            valueToEncrypt = "true";
        } else {
            valueToEncrypt = "false";
        }
        String publicKeyString = System.getenv().get("publicKey");

        byte[] bytes = Base64.getDecoder().decode(publicKeyString);
        byte[] encryptedBytesUtf8 = valueToEncrypt.getBytes(StandardCharsets.UTF_8);
        String string64 = Base64.getEncoder().encodeToString(encryptedBytesUtf8);
        byte[] encryptedBytes = Base64.getDecoder().decode(string64);
        PublicKey publicKey =
            KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = cipher.doFinal(encryptedBytes);
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * <p>encryptWithPubicKey.</p>
     *
     * @param valueToEncryptDouble a Double
     * @return a {@link java.lang.String} object
     * @throws java.security.NoSuchAlgorithmException     if any.
     * @throws javax.crypto.NoSuchPaddingException        if any.
     * @throws java.security.InvalidKeyException          if any.
     * @throws javax.crypto.IllegalBlockSizeException     if any.
     * @throws javax.crypto.BadPaddingException           if any.
     * @throws java.security.spec.InvalidKeySpecException if any.
     * @throws java.io.UnsupportedEncodingException       if any.
     * @since 1.53
     */
    public static String encryptWithPubicKey(Double valueToEncryptDouble)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException,
        BadPaddingException, InvalidKeySpecException {
        String valueToEncrypt = String.valueOf(valueToEncryptDouble);
        String publicKeyString = System.getenv().get("publicKey");
        byte[] bytes = Base64.getDecoder().decode(publicKeyString);
        byte[] encryptedBytesUtf8 = valueToEncrypt.getBytes(StandardCharsets.UTF_8);
        String string64 = Base64.getEncoder().encodeToString(encryptedBytesUtf8);
        byte[] encryptedBytes = Base64.getDecoder().decode(string64);
        PublicKey publicKey =
            KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(bytes));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = cipher.doFinal(encryptedBytes);
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * <p>decryptWithPrivateKey.</p>
     *
     * @param stringToDecrypt a {@link java.lang.String} object
     * @return a {@link java.lang.String} object
     * @throws java.security.NoSuchAlgorithmException     if any.
     * @throws javax.crypto.NoSuchPaddingException        if any.
     * @throws java.security.InvalidKeyException          if any.
     * @throws javax.crypto.IllegalBlockSizeException     if any.
     * @throws javax.crypto.BadPaddingException           if any.
     * @throws java.security.spec.InvalidKeySpecException if any.
     * @throws java.io.UnsupportedEncodingException       if any.
     * @since 1.53
     */
    public static String decryptWithPrivateKey(String stringToDecrypt)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException,
        BadPaddingException, InvalidKeySpecException {
        if (stringToDecrypt == null) {
            return "";
        }
        String privateKeyString = System.getenv().get("privateKey");
        byte[] bytes = Base64.getDecoder().decode(privateKeyString);
        byte[] stringToDecryptBytes = Base64.getDecoder().decode(stringToDecrypt);
        PrivateKey privateKey =
            KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
        Cipher cipher = Cipher.getInstance("RSA");

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(stringToDecryptBytes);

        return new String(decryptedTextArray, StandardCharsets.UTF_8);
    }

    /**
     * <p>decryptWithPrivateKeyBoolean.</p>
     *
     * @param stringToDecrypt a {@link java.lang.String} object
     * @return a {@link java.lang.Boolean} object
     * @throws java.security.NoSuchAlgorithmException     if any.
     * @throws javax.crypto.NoSuchPaddingException        if any.
     * @throws java.security.InvalidKeyException          if any.
     * @throws javax.crypto.IllegalBlockSizeException     if any.
     * @throws javax.crypto.BadPaddingException           if any.
     * @throws java.security.spec.InvalidKeySpecException if any.
     * @throws java.io.UnsupportedEncodingException       if any.
     * @since 1.53
     */
    public static Boolean decryptWithPrivateKeyBoolean(String stringToDecrypt)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException,
        BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException {
        String privateKeyString = System.getenv().get("privateKey");
        byte[] bytes = Base64.getDecoder().decode(privateKeyString);
        byte[] stringToDecryptBytes = Base64.getDecoder().decode(stringToDecrypt);
        PrivateKey privateKey =
            KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
        Cipher cipher = Cipher.getInstance("RSA");

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(stringToDecryptBytes);

        return Boolean.valueOf(new String(decryptedTextArray, StandardCharsets.UTF_8));
    }

    /**
     * <p>decryptWithPrivateKeyDouble.</p>
     *
     * @param stringToDecrypt a {@link java.lang.String} object
     * @return a {@link java.lang.Double} object
     * @throws java.security.NoSuchAlgorithmException     if any.
     * @throws javax.crypto.NoSuchPaddingException        if any.
     * @throws java.security.InvalidKeyException          if any.
     * @throws javax.crypto.IllegalBlockSizeException     if any.
     * @throws javax.crypto.BadPaddingException           if any.
     * @throws java.security.spec.InvalidKeySpecException if any.
     * @throws java.io.UnsupportedEncodingException       if any.
     * @since 1.53
     */
    public static Double decryptWithPrivateKeyDouble(String stringToDecrypt)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException,
        BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException {
        String privateKeyString = System.getenv().get("privateKey");
        byte[] bytes = Base64.getDecoder().decode(privateKeyString);
        byte[] stringToDecryptBytes = Base64.getDecoder().decode(stringToDecrypt);
        PrivateKey privateKey =
            KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bytes));
        Cipher cipher = Cipher.getInstance("RSA");

        // Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(stringToDecryptBytes);

        return Double.valueOf(new String(decryptedTextArray, StandardCharsets.UTF_8));
    }


    public static boolean checkPassword(String clearPassword, String salt, String hashedPassword) {
        try {
            if (salt == null || clearPassword == null) {
                return false;
            }
            BigInteger big = new BigInteger(salt, SALT_BYTE_LENGTH);
            byte[] saltArray = big.toByteArray();
            if (saltArray[0] == 0) {
                byte[] tmp = new byte[saltArray.length - 1];
                System.arraycopy(saltArray, 1, tmp, 0, tmp.length);
                saltArray = tmp;
            }
            MessageDigest md = MessageDigest.getInstance("SHA3-512");
            md.update(saltArray);
            StringBuilder sb = new StringBuilder();
            byte[] hashedPasswordArray = md.digest(clearPassword.getBytes(StandardCharsets.UTF_8));
            for (byte b : hashedPasswordArray) {
                sb.append(String.format("%02x", b));
            }
            String hashedClearPassword = sb.toString();
            return hashedClearPassword.equals(hashedPassword);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    public static String generatePassword(int length, byte[] seed) {
        // Define characters to use in the password
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+-=[]{};:,./<>?";

        // Create a SecureRandom instance for better randomness
        SecureRandom random = new SecureRandom(seed);

        // Build the password
        StringBuilder passwordBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            passwordBuilder.append(chars.charAt(randomIndex));
        }

        return passwordBuilder.toString();
    }

}

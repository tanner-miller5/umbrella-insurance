import '../../css/user/createUser.css';
import { useNavigate } from "react-router-dom";
import {useEffect } from 'react';

import { useDispatch, useSelector } from 'react-redux';
import { updateApplicationRoles, updateAuthAppDataUrl, updateButterBucksAccountValue, 
    updateButterBucksEscrowAccountValue, updateConfirmPassword, updateConsentedToEmails, 
    updateConsentedToTermsAndConditions, updateConsentedToTexts, updateDateOfBirth, 
    updateDidUserLoad, updateEmailAddress, updateEndDateTime, updateFirstName, 
    updateIsEmailAddressVerified, updateIsPhoneNumberVerified, updateLastName, 
    updatePassword, updatePhoneNumber, updateQrCode, updateSessionCode, 
    updateTwoFactorAuthType, updateUsdAccountValue, updateUsdEscrowAccountValue, 
    updateUserId, updateUsername } from '../../redux/reducers/UserReducer';
import { RootState } from '../../redux/store/Store';
import { CreateUserRequest } from '../../endpoints/soa/user/v1/requests/CreateUserRequest';
import { callCreateLoggingRestEndpoints } from '../../endpoints/rest/logging/v1/LoggingRestEndpoints';
import { LoggingMessage } from '../../models/logging/v1/LoggingMessage';
import { updateBackendAppVersion, updateCurrentPage, updateErrorMessage, updateIsErrorOpen, updateIsHamburgerMenuOpen, updateIsPrivacyPolicyOpen, updateIsTermsAndConditionsOpen } from '../../redux/reducers/AppReducer';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { CreateUserResponse } from '../../endpoints/soa/user/v1/responses/CreateUserResponse';
import { SendEmailVerificationRequest } from '../../endpoints/soa/user/v1/requests/SendEmailVerificationRequest';
import { SendPhoneVerificationRequest } from '../../endpoints/soa/user/v1/requests/SendPhoneVerificationRequest';
import { callCreateUserRestEndpoints, sendPhoneVerification, sendEmailVerification } from '../../endpoints/soa/user/v1/UserSoaEndpoints';

export default function CreateUser() {
    let navigate = useNavigate();
    let dispatch = useDispatch();
    const env = useSelector((state: RootState) => {
        return state.environment.env
    })
    let errorMessage: string = useSelector((state: RootState)=>{
        return state.app.errorMessage;
    }) || "";
    let isErrorOpen: boolean = useSelector((state: RootState)=>{
        return state.app.isErrorOpen;
    });
    let domain: string = useSelector((state: RootState)=>{
        return state.environment.domain;
    });
    let username: string = useSelector((state: RootState)=>{
        return state.user.username;
    }) || "";
    let emailAddress: string = useSelector((state: RootState)=>{
        return state.user.emailAddress;
    }) || "";
    let phone: string = useSelector((state: RootState)=>{
        return state.user.phoneNumber;
    }) || "";
    let firstName: string = useSelector((state: RootState)=>{
        return state.user.firstName;
    }) || "";
    let middleName: string = useSelector((state: RootState)=>{
        return state.user.middleName;
    }) || "";
    let lastName: string = useSelector((state: RootState)=>{
        return state.user.lastName;
    }) || "";
    let dateOfBirth: string = useSelector((state: RootState)=>{
        return state.user.dateOfBirth;
    }) || "";
    let password: string = useSelector((state: RootState)=>{
        return state.user.password;
    }) || "";
    let confirmPassword: string = useSelector((state: RootState)=>{
        return state.user.confirmPassword;
    }) || "";
    let consentedToEmails: boolean = useSelector((state: RootState)=>{
        return state.user.consentedToEmails;
    }) || false;
    let consentedToTexts: boolean = useSelector((state: RootState)=>{
        return state.user.consentedToTexts;
    }) || false;
    let twoFactorAuthType: string = useSelector((state: RootState)=>{
        return state.user.twoFactorAuthType;
    }) || "";
    let consentedToTermsAndConditions: boolean = useSelector((state: RootState)=>{
        return state.user.consentedToTermsAndConditions;
    }) || false;
    let userAgent: string = useSelector((state: RootState)=>{
        return state.user.userAgent;
    }) || "";
    function getRandomInt(max: number) {
        return Math.floor(Math.random() * max);
    }
    useEffect(
        function() {
            dispatch(updateCurrentPage("/createUser"));
        }, []
    )
    //default values
    useEffect(function(){
        dispatch(updateUsername(""));
        dispatch(updateEmailAddress(""));
        dispatch(updatePhoneNumber(""));
        dispatch(updateFirstName(""));
        dispatch(updateLastName(""));
        dispatch(updateDateOfBirth(""));
        dispatch(updatePassword(""));
        dispatch(updateConfirmPassword(""));
        dispatch(updateConsentedToEmails(undefined));
        dispatch(updateConsentedToTexts(undefined));
        dispatch(updateTwoFactorAuthType(""));
        dispatch(updateConsentedToTermsAndConditions(false));
    },[]);
    async function onClickCreateUser(event: any) {
        let createUserRequest: CreateUserRequest = new CreateUserRequest();
        if(password != confirmPassword) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Password and confirm password don't match."));
            return;
        }
        createUserRequest.dateOfBirth = dateOfBirth;
        if(!dateOfBirth) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Enter date of birth."));
            return;
        }
        createUserRequest.emailAddress = emailAddress;
        if(!emailAddress) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Enter email address."));
            return;
        } else {
            if(emailAddress.length < 4) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Invalid email address."));
                return;
            } else if (emailAddress.indexOf("@") < 0) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Invalid email address."));
                return;
            } else if (emailAddress.indexOf(".") < 0) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Invalid email address."));
                return;
            }
        }
        createUserRequest.firstName = firstName;
        if(!firstName) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Enter first name."));
            return;
        }
        createUserRequest.middleName = middleName;
        if(!middleName) {
            //dispatch(updateIsErrorOpen(true));
            //dispatch(updateErrorMessage("Enter middle name."));
            //return;
        }
        createUserRequest.surname = lastName;
        if(!lastName) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Enter surname."));
            return;
        }
        createUserRequest.password = password;
        if(!password) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Enter password."));
            return;
        } else if (password.length < 8) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Password length must be greater than 8 characters."));
            return;
        }
        createUserRequest.phoneNumber = phone;
        if(!phone) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Enter phone."));
            return;
        }
        createUserRequest.ssn = getRandomInt(100000000000).toString();
        if(!createUserRequest.ssn) {
            //dispatch(updateIsErrorOpen(true));
            //dispatch(updateErrorMessage("Enter ssn."));
            //return;
        }
        createUserRequest.username = username;
        if(!username) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Enter username."));
            return;
        }
        createUserRequest.consentedToTexts = consentedToTexts;
        if(!(typeof consentedToTexts === 'boolean')) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Select if you consent to texts."));
            return;
        }
        createUserRequest.consentedToEmails = consentedToEmails;
        if(!(typeof consentedToEmails === 'boolean')) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Select if you consent to emails."));
            return;
        }
        createUserRequest.consentedToTermsAndConditions = consentedToTermsAndConditions;
        if(!consentedToTermsAndConditions) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Consent to terms and conditions to continue."));
            return;
        }

        createUserRequest.twoFactorMethod = twoFactorAuthType;
        if(!twoFactorAuthType) {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Select two factor auth type."));
            return;
        }
        dispatch(updateLoadingState(true));
        let createUserResponse: CreateUserResponse | undefined = undefined;
        try {
            createUserResponse = await callCreateUserRestEndpoints(createUserRequest, env, domain);
            dispatch(updateUsdAccountValue(createUserResponse.usdAccountBalance?.accountBalanceValue));
            dispatch(updateButterBucksAccountValue(createUserResponse.butterBucksAccountBalance?.accountBalanceValue));
            dispatch(updateDidUserLoad(true));
            dispatch(updateUsdEscrowAccountValue(createUserResponse.usdEscrowAccountBalance?.accountBalanceValue));
            dispatch(updateButterBucksEscrowAccountValue(createUserResponse.butterBucksEscrowAccountBalance?.accountBalanceValue));
            dispatch(updateSessionCode(createUserResponse.sessionCode));
            dispatch(updateIsEmailAddressVerified(createUserResponse.isEmailAddressVerified));
            dispatch(updateIsPhoneNumberVerified(createUserResponse.isPhoneNumberVerified));
            dispatch(updateApplicationRoles(createUserResponse.applicationRoles));
            dispatch(updateEndDateTime(createUserResponse.endDateTime));
            dispatch(updateUserId(createUserResponse?.user?.id?.toString()));
            dispatch(updateBackendAppVersion(createUserResponse.backendAppVersion));
        } catch(e:any) {
            let loggingMessage: LoggingMessage = new LoggingMessage();         
            const url = window.location.href;         
            loggingMessage.appName = 'umbrella-insurance-frontend';
            loggingMessage.callingLoggerName = url;         
            loggingMessage.loggingPayload = `ERROR:${e.message}`;         
            loggingMessage.logLevel = "ERROR";         
            callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
            console.error(loggingMessage.loggingPayload);
            dispatch(updateLoadingState(false));
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Create user failed"));
            return;
        }
        dispatch(updateLoadingState(false));

        if (username) {
            if (twoFactorAuthType == 'text') {
                let sentPhoneVerificationRequest: SendPhoneVerificationRequest = new SendPhoneVerificationRequest();
                sentPhoneVerificationRequest.username = username;
                sendPhoneVerification(sentPhoneVerificationRequest, env, domain);
                navigate("/confirmUserPhone")

            } else if (twoFactorAuthType == 'email') {
                let sendEmailVerificationRequest: SendEmailVerificationRequest = new SendEmailVerificationRequest();
                sendEmailVerificationRequest.username = username;
                sendEmailVerification(sendEmailVerificationRequest, env, domain);
                navigate("/confirmUserEmail")
            } else if (twoFactorAuthType == 'app') {
                if(createUserResponse?.authAppDataUrl) {
                    dispatch(updateAuthAppDataUrl(createUserResponse?.authAppDataUrl));
                    const QRCode = require('qrcode');
                    dispatch(updateQrCode(await QRCode.toDataURL(createUserResponse?.authAppDataUrl)));
                    navigate("/verifyAuthApp")
                }
            } else {
                navigate("/");
            }
        } else {
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Unable to create user."));
        }
    }

    function onClickBack() {
        navigate("/signIn");
    }
    return (
        <div className='column2' onClick={()=>dispatch(updateIsHamburgerMenuOpen(false))}>
            <form className="flexContainer" onSubmit={(e)=>{
                            e.preventDefault();
                            onClickCreateUser(e);
                        }}>
                <h2>Create User</h2>
                <div className='flexInner'>
                    <label >Username</label>
                </div>
                <div className='flexInner'>
                    <input id="username" required autoFocus
                        name="username" pattern="[A-Za-z0-9]+" placeholder='JohnDoe99' minLength={8}
                    type="text" value={username} onChange={(event)=>dispatch(updateUsername(event.target.value?.toLowerCase()))}/>
                </div>

                <div className='flexInner'>
                    <label >Email Address</label> 
                </div>
                <div className='flexInner'>
                    <input id="email" name="email" required placeholder='john.doe@widget.com'
                            autoComplete='email'
                            type="email" value={emailAddress} onChange={(event)=>dispatch(updateEmailAddress(event.target.value.toLowerCase()))}/>
                </div>

                <div className='flexInner'>
                    <label >Phone Number</label> 
                </div>
                <div className='flexInner'>
                    <input id="phone" name="phone" required placeholder='1234567890' autoComplete='tel'
                            type="tel" value={phone} onChange={(event)=>dispatch(updatePhoneNumber(event.target.value))}/>
                </div>

                <div className='flexInner'>
                    <label>First Name</label> 
                </div>
                <div className='flexInner'>
                    <input id="firstName" name="firstName"  autoComplete='given-name'
                        pattern="[A-Za-z]{1,50}" placeholder='John'
                        required type="text" value={firstName} onChange={(event)=>dispatch(updateFirstName(event.target.value))}/>
                </div>

                <div className='flexInner'>
                    <label>Last Name</label> 
                </div>
                <div className='flexInner'>
                    <input id="lastName" placeholder='Doe' autoComplete='family-name'
                        name="lastName" pattern="[A-Za-z]{1,50}" 
                        type="text" required value={lastName} onChange={(event)=>dispatch(updateLastName(event.target.value))}/>
                </div>

                <div className='flexInner'>
                    <label >Date of Birth</label>
                </div>
                <div className='flexInner'>
                    <input  id="dateOfBirth" name="dateOfBirth" type="date" value={dateOfBirth}
                            autoComplete='bday'
                            required onChange={(event)=>dispatch(updateDateOfBirth(event.target.value))}/>
                </div>

                <div className='flexInner'>
                    <label >Password</label> 
                </div>
                <div className='flexInner'>
                    <input  id="password" name="password" pattern=".{1,50}" 
                        type="password" value={password} minLength={8} autoComplete='new-password'
                        required onChange={(event)=>dispatch(updatePassword(event.target.value))}/>
                </div>
                <div className='flexInner'>
                    <label >Confirm Password</label> 
                </div>
                <div className='flexInner'>
                    <input  id="confirmPassword" name="confirmPassword" pattern=".{1,50}" 
                        autoComplete='new-password'
                        type="password" value={confirmPassword} minLength={8}
                        required onChange={(event)=>dispatch(updateConfirmPassword(event.target.value))}/>
                </div>

                <div className='flexInner'>
                    <label >Do you consent to emails?</label>
                </div>
                <select id="consentedToEmail" name="consentedToEmail" required
                        defaultValue={""}
                        onChange={(event)=>{dispatch(updateConsentedToEmails(event.target.value == "true"))}}>
                        <option disabled value="">-</option>
                        <option value="true">Yes</option>
                        <option value="false">No</option>
                </select>
                <div className='flexInner'>
                    <label >Do you consent to text messages?</label>
                </div>
                <select id="consentedToText" name="consentedToText" required
                        defaultValue={""}
                        onChange={(event)=>{
                            dispatch(updateConsentedToTexts(event.target.value === "true"))
                        }}>
                        <option disabled value="">-</option>
                        <option value="true">Yes</option>
                        <option value="false">No</option>
                </select>
                <div className='flexInner'>
                    <label >Two-factor authentication type?</label>
                </div>
                <select id="twoFactorSelect" name="twoFactor" required
                        defaultValue={""}
                        onChange={(event)=>{dispatch(updateTwoFactorAuthType(event.target.value))}}>
                        <option disabled value="">-</option>
                        {/*<option value="email">Email</option>
                        <option value="text">Text</option>
                        <option value="app">Authenticator App</option>*/}
                        <option value="none">None</option>
                </select>
                <div className='flexInner'>
                    <button type="button"  onClick={()=>{dispatch(updateIsPrivacyPolicyOpen(true))}}>Privacy Policy</button>
                </div>
                <div className='flexInner'>
                    <button type="button" onClick={()=>{dispatch(updateIsTermsAndConditionsOpen(true))}}>Terms and Conditions</button>
                </div>
                <div className='flexInner'>
                    <label >Do you accept our terms and conditions?</label>
                </div>
                <div className='flexInner'>
                    <select id="consentedToTerms" name="consentedToTerms" required
                        defaultValue={""}
                        onChange={(event)=>{dispatch(updateConsentedToTermsAndConditions(event.target.value === "true"))}}>
                        <option disabled value="">-</option>
                        <option value="true">Yes</option>
                        <option value="false">No</option>

                    </select>
                </div>

                <div className='flexInner'>
                    <button name="action" type="submit" >Submit</button>
                </div>
                <div className='flexInner'>
                    <button name="back" type="button" onClick={onClickBack}>Back</button>
                </div>
            </form>
        </div>);
};

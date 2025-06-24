import '../../css/user/confirmUserEmail.css';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from '../../redux/reducers/AppReducer';
import { SendEmailVerificationRequest } from '../../endpoints/soa/user/v1/requests/SendEmailVerificationRequest';
import { SendEmailVerificationResponse } from '../../endpoints/soa/user/v1/responses/SendEmailVerificationResponse';
import { useNavigate } from 'react-router-dom';
import { RootState } from '../../redux/store/Store';
import { ConfirmUserEmailRequest } from '../../endpoints/soa/user/v1/requests/ConfirmUserEmailRequest';
import { updateConsentedToEmails, updateDidUserLoad, updateIsEmailAddressVerified, updateVerificationCode } from '../../redux/reducers/UserReducer';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { ConfirmUserEmailResponse } from '../../endpoints/soa/user/v1/responses/ConfirmUserEmailResponse';
import { callCreateLoggingRestEndpoints } from '../../endpoints/rest/logging/v1/LoggingRestEndpoints';
import { LoggingMessage } from '../../models/logging/v1/LoggingMessage';
import { SkipVerifyRequest } from '../../endpoints/soa/user/v1/requests/SkipVerifyRequest';
import { SkipVerifyResponse } from '../../endpoints/soa/user/v1/responses/SkipVerifyResponse';
import { sendEmailVerification, callConfirmUserEmailRestEndpoints, callSkipVerifyRestEndpoints } from '../../endpoints/soa/user/v1/UserSoaEndpoints';

export default function ConfirmUserEmail(){
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
    let phoneNumber: string = useSelector((state: RootState)=>{
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
    let sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";
    let verificationCode: string = useSelector((state: RootState)=>{
        return state.user.verificationCode;
    }) || "";
    let consentedToTermsAndConditions: boolean = useSelector((state: RootState)=>{
        return state.user.consentedToTermsAndConditions;
    }) || false;
    let userAgent: string = useSelector((state: RootState)=>{
        return state.user.userAgent;
    }) || "";
    let longitude: number | undefined = useSelector((state: RootState)=>{
        return state.user.longitude;
    });
    let latitude: number | undefined = useSelector((state: RootState)=>{
        return state.user.latitude;
    });
    let accuracy: number | undefined = useSelector((state: RootState)=>{
        return state.user.accuracy;
    });
    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/confirmUserEmail"));
        }, []
    );
    async function onClickResend() {
        try {
            let sendEmailVerificationRequest: SendEmailVerificationRequest = new SendEmailVerificationRequest();
            sendEmailVerificationRequest.username = username;
            let sendEmailVerificationResponse: SendEmailVerificationResponse = await sendEmailVerification(sendEmailVerificationRequest, env, domain);
        } catch(e) {

        }

    }
    async function onClickSubmit() {
        try {
            dispatch(updateLoadingState(true));
            let confirmUserEmailRequest: ConfirmUserEmailRequest = new ConfirmUserEmailRequest();
            confirmUserEmailRequest.username = username;
            confirmUserEmailRequest.verificationCode = verificationCode
            let response: ConfirmUserEmailResponse | undefined = await callConfirmUserEmailRestEndpoints(sessionCode, confirmUserEmailRequest, env, domain);
            if(response?.isSuccess) {
                dispatch(updateIsEmailAddressVerified(true));
                navigate("/");
            } else {
                dispatch(updateErrorMessage("Unable to verify email"));
                dispatch(updateIsErrorOpen(true));
            }
        } catch(e) {
            let loggingMessage: LoggingMessage = new LoggingMessage();
            const url = window.location.href;
            loggingMessage.appName = 'umbrella-insurance-frontend';
            loggingMessage.callingLoggerName = "ConfirmUserEmail.tsx";
            loggingMessage.callingMethod = "ConfirmUserEmail.tsx:onClickSubmit";
            loggingMessage.loggingPayload = `${e}`;
            loggingMessage.logLevel = "INFO";
            loggingMessage.latitude = latitude;
            loggingMessage.longitude = longitude;
            loggingMessage.accuracy = accuracy;
            callCreateLoggingRestEndpoints(loggingMessage, env, domain);
        } finally {
            dispatch(updateLoadingState(false));
        }
    }
    async function onSkipClick() {
        dispatch(updateLoadingState(true));
        let skipVerifyResponse: SkipVerifyResponse | undefined = await callSkipVerifyRestEndpoints(sessionCode, new SkipVerifyRequest(), env, domain);
        dispatch(updateLoadingState(false));
        if(skipVerifyResponse?.isSuccess) {
            dispatch(updateDidUserLoad(true));
            navigate("/")
        } else {
            dispatch(updateErrorMessage("Unable to skip verification"));
            dispatch(updateIsErrorOpen(true));
        }
    }
    return (
        <div className='column2'>
            <form className='flexContainer' onSubmit={(e)=> {
                e.preventDefault();
                onClickSubmit();
            }}>
                <h2 >Confirm Email Verification Code</h2>
                <div className='flexInner'>
                    <input onChange={(e)=> {
                        dispatch(updateVerificationCode(e.target.value));
                    }}
                    id="verificationCode" name="verificationCode" type="text" />
                </div>
                <div className='flexInner'>
                    <button name="action"  type="submit">Submit</button>
                </div>
                <div className='flexInner'>
                    <button name="action" onClick={onClickResend} type="button">Resend</button>
                </div>
                <div className="flexInner">
                    <button onClick={onSkipClick} type="button">Skip</button>
                </div>
            </form>
        </div>);
};

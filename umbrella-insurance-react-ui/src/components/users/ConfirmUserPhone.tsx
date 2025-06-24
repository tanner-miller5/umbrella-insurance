import '../../css/user/confirmUserPhone.css';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from '../../redux/reducers/AppReducer';
import { SkipVerifyRequest } from '../../endpoints/soa/user/v1/requests/SkipVerifyRequest';
import { SkipVerifyResponse } from '../../endpoints/soa/user/v1/responses/SkipVerifyResponse';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { updateConsentedToTexts, updateDidUserLoad, updateIsPhoneNumberVerified, updateVerificationCode } from '../../redux/reducers/UserReducer';
import { useNavigate } from 'react-router-dom';
import { SendPhoneVerificationRequest } from '../../endpoints/soa/user/v1/requests/SendPhoneVerificationRequest';
import { SendPhoneVerificationResponse } from '../../endpoints/soa/user/v1/responses/SendPhoneVerificationResponse';
import { ConfirmUserPhoneRequest } from '../../endpoints/soa/user/v1/requests/ConfirmUserPhoneRequest';
import { ConfirmUserPhoneResponse } from '../../endpoints/soa/user/v1/responses/ConfirmUserPhoneResponse';
import { callSkipVerifyRestEndpoints, sendPhoneVerification, callConfirmUserPhoneRestEndpoint } from '../../endpoints/soa/user/v1/UserSoaEndpoints';

export default function ConfirmUserPhone(){
    const navigate = useNavigate();
    const dispatch = useDispatch();
    let username: string = useSelector((state: RootState)=>{
        return state.user.username;
    }) || "";
    let emailAddress: string = useSelector((state: RootState)=>{
        return state.user.emailAddress;
    }) || "";
    let phoneNumber: string = useSelector((state: RootState)=>{
        return state.user.phoneNumber;
    }) || "";
    let sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";
    let verificationCode: string = useSelector((state: RootState)=>{
        return state.user.verificationCode;
    }) || "";
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const domain = useSelector((state:RootState) => {
        return state.environment.domain;
    });
    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/confirmUserPhone"));
        }, []
    );
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
    async function onClickResend() {
        try {
            dispatch(updateLoadingState(true));
            let sendPhoneVerificationRequest : SendPhoneVerificationRequest = new SendPhoneVerificationRequest();
            sendPhoneVerificationRequest.username = username;
            let sendPhoneVerificationResponse: SendPhoneVerificationResponse | undefined = await sendPhoneVerification(sendPhoneVerificationRequest, env, domain);
            if(!sendPhoneVerificationResponse?.isSuccess) {
                dispatch(updateErrorMessage("Unable to send verifiaction text."));
                dispatch(updateIsErrorOpen(true));
            }
        } catch(e) {
            dispatch(updateErrorMessage("Unable to send verifiaction text."));
            dispatch(updateIsErrorOpen(true));
        } finally {
            dispatch(updateLoadingState(false));
        }
    }

    async function onClickSubmit() {
        try {
            dispatch(updateLoadingState(true));
            let confirmUserPhoneRequest : ConfirmUserPhoneRequest = new ConfirmUserPhoneRequest();
            confirmUserPhoneRequest.username = username;
            confirmUserPhoneRequest.verificationCode = verificationCode;
            let confirmUserPhoneResponse: ConfirmUserPhoneResponse | undefined = await callConfirmUserPhoneRestEndpoint(sessionCode, confirmUserPhoneRequest, env, domain);
            if(confirmUserPhoneResponse?.isSuccess) {
                dispatch(updateIsPhoneNumberVerified(true));
                navigate("/");
            } else {
                dispatch(updateErrorMessage("Unable to verify phone number."));
                dispatch(updateIsErrorOpen(true));
            }
        } catch(e) {
            dispatch(updateErrorMessage("Unable to verify phone number."));
            dispatch(updateIsErrorOpen(true));
        } finally {
            dispatch(updateLoadingState(false));
        }
    }
    return (
        <div className='column2'>
            <form className='flexContainer' onSubmit={(e)=>{
                e.preventDefault();
                onClickSubmit();
            }}>
                <h2>Confirm Phone Verification Code</h2>
                <div className='flexInner'>
                    <input id="verificationCode" name="verificationCode" type="text"
                        onChange={(e)=>dispatch(updateVerificationCode(e.target.value))} />
                </div>
                <div className='flexInner'>
                    <button name="action" type="submit">Submit</button>
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

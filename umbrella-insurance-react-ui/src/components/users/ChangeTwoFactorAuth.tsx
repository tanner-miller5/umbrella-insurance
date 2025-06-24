import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateAuthAppDataUrl, updateConfirmNewPassword, updateExistingPassword, updateNewPassword, updateQrCode, updateTmpTwoFactorAuthType, updateTwoFactorAuthType, updateUsername, updateVerificationCode } from '../../redux/reducers/UserReducer';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { RootState } from '../../redux/store/Store';
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from '../../redux/reducers/AppReducer';
import { useNavigate } from 'react-router-dom';
import { ChangeTwoFactorAuthRequest } from '../../endpoints/soa/user/v1/requests/ChangeTwoFactorAuthRequest';
import { ChangeTwoFactorAuthResponse } from '../../endpoints/soa/user/v1/responses/ChangeTwoFactorAuthResponse';
import { SendEmailVerificationRequest } from '../../endpoints/soa/user/v1/requests/SendEmailVerificationRequest';
import { SendPhoneVerificationRequest } from '../../endpoints/soa/user/v1/requests/SendPhoneVerificationRequest';
import { callChangeTwoFactorAuthTypeRestEndpoint, sendEmailVerification, sendPhoneVerification } from '../../endpoints/soa/user/v1/UserSoaEndpoints';


export default function ChangeTwoFactorAuth(){
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const username: string = useSelector((state: RootState)=>{
        return state.user.username;
    }) || "";
    const email: string = useSelector((state: RootState)=>{
        return state.user.emailAddress;
    }) || "";
    const phone: string = useSelector((state: RootState)=>{
        return state.user.phoneNumber;
    }) || "";
    const env: string = useSelector((state: RootState) => {
        return state.environment.env;
    })
    const domain: string = useSelector((state: RootState) => {
        return state.environment.domain;
    })
    const resetMethod: string = useSelector((state: RootState)=>{
        return state.user.resetMethod;
    }) || "";
    const newPassword: string = useSelector((state: RootState)=>{
        return state.user.newPassword;
    }) || "";
    const existingPassword: string = useSelector((state: RootState)=>{
        return state.user.existingPassword;
    }) || "";
    const confirmNewPassword: string = useSelector((state: RootState)=>{
        return state.user.confirmNewPassword;
    }) || "";
    const twoFactorAuthType: string = useSelector((state: RootState)=>{
        return state.user.twoFactorAuthType;
    }) || "";
    const tmpTwoFactorAuthType: string = useSelector((state: RootState)=>{
        return state.user.tmpTwoFactorAuthType;
    }) || "";
    const password: string = useSelector((state: RootState)=>{
        return state.user.password;
    }) || "";
    const authAppDataUrl: string = useSelector((state: RootState)=>{
        return state.user.authAppDataUrl;
    }) || "";
    const sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";
    const isEmailAddressVerified: boolean | undefined= useSelector((state: RootState)=>{
        return state.user.isEmailAddressVerified;
    });
    const isPhoneNumberVerified: boolean | undefined= useSelector((state: RootState)=>{
        return state.user.isPhoneNumberVerified;
    });
    const isAuthAppVerified: boolean | undefined= useSelector((state: RootState)=>{
        return state.user.isAuthAppVerified;
    });
    
    useEffect(
        function() {
            dispatch(updateCurrentPage("/changeTwoFactorAuthType"));
        }, []
    );

    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateTmpTwoFactorAuthType(twoFactorAuthType));
        }, []
    )
    
    async function changeTwoFactorAuthClick(twoFactorAuthType: string, existingPassword: string) {
        try {
            dispatch(updateLoadingState(true));
            let changeTwoFactorAuthRequest: ChangeTwoFactorAuthRequest = new ChangeTwoFactorAuthRequest();
            changeTwoFactorAuthRequest.existingPassword = existingPassword;
            changeTwoFactorAuthRequest.twoFactorAuthType = tmpTwoFactorAuthType;
            let changeTwoFactorAuthResponse: ChangeTwoFactorAuthResponse = await callChangeTwoFactorAuthTypeRestEndpoint(
                changeTwoFactorAuthRequest, sessionCode, env, domain);
            if(changeTwoFactorAuthResponse.isSuccess) {
                dispatch(updateTwoFactorAuthType(tmpTwoFactorAuthType));
                if(changeTwoFactorAuthResponse.authAppDataUrl) {
                    dispatch(updateAuthAppDataUrl(changeTwoFactorAuthResponse.authAppDataUrl));
                }
                if(twoFactorAuthType === "app") {
                    const QRCode = require('qrcode');
                    dispatch(updateQrCode(await QRCode.toDataURL(changeTwoFactorAuthResponse.authAppDataUrl)));
                    navigate("/verifyAuthApp")
                } else if (twoFactorAuthType === "email") {
                    let sendEmailVerificationRequest: SendEmailVerificationRequest = new SendEmailVerificationRequest();
                    sendEmailVerificationRequest.username = username;
                    sendEmailVerification(sendEmailVerificationRequest, env, domain);
                    navigate("/confirmUserEmail")
                } else if (twoFactorAuthType === "text") {
                    let sentPhoneVerificationRequest: SendPhoneVerificationRequest = new SendPhoneVerificationRequest();
                    sentPhoneVerificationRequest.username = username;
                    sendPhoneVerification(sentPhoneVerificationRequest, env, domain);
                    navigate("/confirmUserPhone")
                } else {
                    navigate("/");
                }
            } else {
                dispatch(updateErrorMessage("Unable to update two factor auth type."));
                dispatch(updateIsErrorOpen(true));
            }
        } catch(e) {
            dispatch(updateErrorMessage("Unable to update two factor auth type."));
            dispatch(updateIsErrorOpen(true));
        } finally {
            dispatch(updateLoadingState(false));
        }
    }

    return (
    <div className="column2">
        <form className='flexContainer' onSubmit={(e)=>{
            e.preventDefault();
            changeTwoFactorAuthClick(tmpTwoFactorAuthType, existingPassword);
        }} >
            <h2>Change Two Factor Auth Type</h2>
            <div className='flexInner'>
                <label hidden>Username</label>
            </div>
            <div className='flexInner' hidden>
                <input id="username" required autoFocus hidden disabled autoComplete="username"
                    name="username" pattern="[A-Za-z0-9]+" placeholder='JohnDoe99' minLength={8}
                type="text" value={username} onChange={(event)=>dispatch(updateUsername(event.target.value?.toLowerCase()))}/>
            </div>
            <div className='flexInner'>
                <label>Two Factor Type</label> 
            </div>
            <div className='flexInner'>
                <select id="twoFactorSelect" name="twoFactor" required
                    defaultValue={twoFactorAuthType ? twoFactorAuthType : ""}
                    onChange={(event)=>{dispatch(updateTmpTwoFactorAuthType(event.target.value))}}>
                    <option disabled value="">-</option>
                    {/* <option value="email">Email</option> 
                    <option value="text">Text</option>
                    <option value="app">Authenticator App</option> */}
                    <option value="none">None</option>
                </select>
            </div>
            <div className='flexInner'>
                <label>Existing Password</label> 
            </div>
            <div className='flexInner'>
                <input
                    id="existingPassword" name="existingPassword" type="password" autoComplete='current-password'
                    onChange={(e)=>dispatch(updateExistingPassword(e.target.value))}/>
            </div>
            <div className='flexInner'>
                <button name="action" type="submit" >Submit</button>
            </div>
        </form>
    </div>);
};
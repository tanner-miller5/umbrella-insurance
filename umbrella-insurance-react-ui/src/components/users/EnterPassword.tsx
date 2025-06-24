import { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { updateBackendAppVersion, updateCurrentPage } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updateApplicationRoles, updateAuthAppDataUrl, updateButterBucksAccountValue, 
    updateButterBucksEscrowAccountValue, updateCart, updateConsentedToEmails, updateConsentedToTexts, 
    updateDateOfBirth, updateDidUserLoad, updateEmailAddress, updateEndDateTime, 
    updateFirstName, updateIsAuthAppVerified, updateIsEmailAddressVerified, 
    updateIsPhoneNumberVerified, updateLastName, updatePassword, updatePhoneNumber, 
    updateSessionCode, updateUsdAccountValue, updateUsdEscrowAccountValue, updateUserId, 
    updateUsername, updateVerificationCode } from "../../redux/reducers/UserReducer";
import { SignInRequest } from "../../endpoints/soa/user/v1/requests/SignInRequest";
import { SignInResponse } from "../../endpoints/soa/user/v1/responses/SignInResponse";
import { useNavigate } from "react-router-dom";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { callSignInRestEndpoints } from "../../endpoints/soa/user/v1/UserSoaEndpoints";
import { Cart } from "../../models/carts/v1/Cart";

export default function EnterPassword(){
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const domain = useSelector((state:RootState) => {
        return state.environment.domain;
    });
    const username = useSelector((state:RootState) => {
        return state.user.username;
    });
    const password = useSelector((state:RootState) => {
        return state.user.password;
    });
    const phoneNumber = useSelector((state:RootState) => {
        return state.user.phoneNumber;
    });
    const verificationCode = useSelector((state:RootState) => {
        return state.user.verificationCode;
    });
    const twoFactorAuthType = useSelector((state:RootState) => {
        return state.user.twoFactorAuthType;
    });
    const applicationRoles = useSelector((state:RootState) => {
        return state.user.applicationRoles;
    })

    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/enterPassword"));
        }, []
    );
    async function onClickSubmit() {
        let signInRequest: SignInRequest = new SignInRequest();
        signInRequest.username = username;
        signInRequest.password = password;
        signInRequest.verificationCode = verificationCode;
        signInRequest.totp = verificationCode;
        dispatch(updateLoadingState(true));
        let signInResponse: SignInResponse | undefined = await callSignInRestEndpoints(signInRequest, env, domain);
        signInResponse = new SignInResponse(signInResponse);
        if(signInResponse) {
            if(signInResponse.sessionCode) {
                if(typeof signInResponse.consentedToEmails === "boolean") {
                    dispatch(updateConsentedToEmails(signInResponse.consentedToEmails));
                }
                if(typeof signInResponse.consentedToTexts === "boolean") {
                    dispatch(updateConsentedToTexts(signInResponse.consentedToTexts));
                }
                if(signInResponse.dateOfBirth) {
                    dispatch(updateDateOfBirth(signInResponse.dateOfBirth));
                }
                if(signInResponse.emailAddress) {
                    dispatch(updateEmailAddress(signInResponse.emailAddress));
                }
                if(signInResponse.escrowPlayAmount) {
                    dispatch(updateButterBucksEscrowAccountValue(signInResponse.escrowPlayAmount));
                }
                if(signInResponse.escrowRealAmount) {
                    dispatch(updateUsdEscrowAccountValue(signInResponse.escrowRealAmount));
                }
                if(signInResponse.firstName) {
                    dispatch(updateFirstName(signInResponse.firstName));
                }
                if(signInResponse.lastName) {
                    dispatch(updateLastName(signInResponse.lastName));
                }
                if(signInResponse.phoneNumber) {
                    dispatch(updatePhoneNumber(signInResponse.phoneNumber));
                }
                if(signInResponse.playAccountValue) {
                    dispatch(updateButterBucksAccountValue(signInResponse.playAccountValue));
                }
                if(signInResponse.realAccountValue) {
                    dispatch(updateUsdAccountValue(signInResponse.realAccountValue));
                }
                if(signInResponse.sessionCode) {
                    dispatch(updateSessionCode(signInResponse.sessionCode));
                }
                if(typeof signInResponse.isAuthAppVerified === "boolean") {
                    dispatch(updateIsAuthAppVerified(signInResponse.isAuthAppVerified));
                }
                if(typeof signInResponse.isEmailAddressVerified === "boolean") {
                    dispatch(updateIsEmailAddressVerified(signInResponse.isEmailAddressVerified));
                }
                if(typeof signInResponse.isPhoneNumberVerified === "boolean") {
                    dispatch(updateIsPhoneNumberVerified(signInResponse.isPhoneNumberVerified));
                }
                if(signInResponse.authAppDataUrl) {
                    dispatch(updateAuthAppDataUrl(signInResponse.authAppDataUrl));
                }
                if(signInResponse.applicationRoles) {
                    dispatch(updateApplicationRoles(signInResponse.applicationRoles));
                }
                if(signInResponse.endDateTime) {
                    dispatch(updateEndDateTime(signInResponse.endDateTime));
                }
                if(signInResponse.user) {
                    dispatch(updateUserId(signInResponse.user.id?.toString()));
                }
                if(signInResponse.backendAppVersion) {
                    dispatch(updateBackendAppVersion(signInResponse.backendAppVersion));
                }
                if(signInResponse.cart) {
                    dispatch(updateCart(signInResponse.cart));
                }
                dispatch(updateLoadingState(false));
                dispatch(updateDidUserLoad(true));
                navigate("/createPolicy");
            }
        }
        dispatch(updateLoadingState(false));
    }
    function onChangePassword(event: any) {
        dispatch(updatePassword(event?.target?.value));
    }
    function onChangeVerificationCode(event: any) {
        dispatch(updateVerificationCode(event?.target?.value));
    }
    return (
        <div className='column2'>
            <form className='flexContainer' onSubmit={(e)=>{
                            e.preventDefault();
                            onClickSubmit();
                        }}>
                
                <div className='flexInner' style={{display:"none"}}>
                    <label htmlFor="username" hidden>Username</label>
                </div>
                <div className='flexInner' hidden>
                    <input id="username" required autoFocus hidden disabled autoComplete="username"
                        name="username" pattern="[A-Za-z0-9]+" placeholder='JohnDoe99' minLength={8}
                    type="text" value={username} onChange={(event)=>dispatch(updateUsername(event.target.value?.toLowerCase()))}/>
                </div>
                <label htmlFor="password">Enter Password</label>
                <div className="flexInner">
                    <input required autoFocus 
                        id="password" name="password" type="password" minLength={8} autoComplete="current-password"
                        onChange={onChangePassword} />
                </div>
                {(twoFactorAuthType === "text" || twoFactorAuthType === "email") && <label >Enter Verifation Code</label>}
                {(twoFactorAuthType === "text" || twoFactorAuthType === "email") && <div className="flexInner">
                    <input required autoComplete="one-time-code"
                        id="verificationCode" name="verificationCode" type="text" minLength={6}
                        onChange={onChangeVerificationCode} />
                </div>}

                {twoFactorAuthType === "app" && <label >Enter Auth App Code</label>}
                {twoFactorAuthType === "app" && <div className="flexInner">
                    <input required autoComplete="one-time-code"
                        id="verificationCode" name="verificationCode" type="text" minLength={6}
                        onChange={onChangeVerificationCode} />
                </div>}

                <div className="flexInner">
                    <button name="action" type="submit">Submit</button>
                </div>
            </form>
        </div>);
};
import '../../css/user/updateUser.css';
import {  useEffect } from 'react';
import { UpdateUserResponse } from '../../endpoints/soa/user/v1/responses/UpdateUserResponse';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { RootState } from '../../redux/store/Store';
import { updateConsentedToEmails, updateConsentedToTexts, updateDateOfBirth, updateEmailAddress, updateFirstName, updateLastName, updatePhoneNumber, updateTmpConsentedToEmails, updateTmpConsentedToTexts, updateTmpEmailAddress, updateTmpPhoneNumber, updateTmpUsername, updateTwoFactorAuthType, updateUsername } from '../../redux/reducers/UserReducer';
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen, updateIsHamburgerMenuOpen } from '../../redux/reducers/AppReducer';
import { UpdateUserRequest } from '../../endpoints/soa/user/v1/requests/UpdateUserRequest';
import { callUpdateUserRestEndpoint } from '../../endpoints/soa/user/v1/UserSoaEndpoints';

export default function UpdateUser(){
    const navigate = useNavigate();
    const env = useSelector((state: RootState) => {
        return state.environment.env
    });
    const domain: string = useSelector((state: RootState)=>{
        return state.environment.domain;
    });
    const username: string | undefined = useSelector((state: RootState)=>{
        return state.user.username;
    });
    const emailAddress: string | undefined = useSelector((state: RootState)=>{
        return state.user.emailAddress;
    });
    const phoneNumber: string | undefined = useSelector((state: RootState)=>{
        return state.user.phoneNumber;
    });
    const tmpUsername: string = useSelector((state: RootState)=>{
        return state.user.tmpUsername;
    }) || username || "";
    const tmpEmailAddress: string = useSelector((state: RootState)=>{
        return state.user.tmpEmailAddress;
    }) || emailAddress || "";
    const tmpPhoneNumber: string = useSelector((state: RootState)=>{
        return state.user.tmpPhoneNumber;
    }) || phoneNumber || "";
    const firstName: string | undefined = useSelector((state: RootState)=>{
        return state.user.firstName;
    });
    const middleName: string | undefined = useSelector((state: RootState)=>{
        return state.user.middleName;
    });
    const lastName: string | undefined = useSelector((state: RootState)=>{
        return state.user.lastName;
    });
    const dateOfBirth: string | undefined = useSelector((state: RootState)=>{
        return state.user.dateOfBirth;
    });
    const password: string | undefined = useSelector((state: RootState)=>{
        return state.user.password;
    });
    const consentedToEmails: boolean | undefined = useSelector((state: RootState)=>{
        return state.user.consentedToEmails;
    });
    const consentedToTexts: boolean | undefined = useSelector((state: RootState)=>{
        return state.user.consentedToTexts;
    });
    const tmpConsentedToEmails: boolean | undefined = useSelector((state: RootState)=>{
        return typeof state.user.tmpConsentedToEmails === "undefined" ? consentedToEmails : state.user.tmpConsentedToEmails;
    });
    const tmpConsentedToTexts: boolean | undefined = useSelector((state: RootState)=>{
        return typeof state.user.tmpConsentedToTexts === "undefined" ? consentedToTexts : state.user.tmpConsentedToTexts;
    });
    const twoFactorAuthType: string | undefined = useSelector((state: RootState)=>{
        return state.user.twoFactorAuthType;
    });
    const consentedToTermsAndConditions: boolean | undefined = useSelector((state: RootState)=>{
        return state.user.consentedToTermsAndConditions;
    });
    const userAgent: string | undefined = useSelector((state: RootState)=>{
        return state.user.userAgent;
    });
    const sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";
    const dispatch = useDispatch();
    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/updateUser"));
        }, []
    );
    async function updateUserClickHandler(event:any) {
        try {
            dispatch(updateLoadingState(true));
            let updateUserRequest : UpdateUserRequest = new UpdateUserRequest();
            updateUserRequest.emailAddress = tmpEmailAddress;
            if(!tmpEmailAddress) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Enter email address."));
                return;
            } else {
                if(tmpEmailAddress.length < 4) {
                    dispatch(updateIsErrorOpen(true));
                    dispatch(updateErrorMessage("Invalid email address."));
                    return;
                } else if (tmpEmailAddress.indexOf("@") < 0) {
                    dispatch(updateIsErrorOpen(true));
                    dispatch(updateErrorMessage("Invalid email address."));
                    return;
                } else if (tmpEmailAddress.indexOf(".") < 0) {
                    dispatch(updateIsErrorOpen(true));
                    dispatch(updateErrorMessage("Invalid email address."));
                    return;
                }
            }
            updateUserRequest.firstName = firstName;
            if(!firstName) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Enter first name."));
                return;
            }
            updateUserRequest.lastName = lastName;
            if(!lastName) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Enter surname."));
                return;
            }
            updateUserRequest.phoneNumber = tmpPhoneNumber;
            if(!tmpPhoneNumber) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Enter phone."));
                return;
            }
            updateUserRequest.username = tmpUsername;
            if(!tmpUsername) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Enter username."));
                return;
            }
            updateUserRequest.consentedToTexts = tmpConsentedToTexts;
            if(!(typeof tmpConsentedToTexts === 'boolean')) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Select if you consent to texts."));
                return;
            }
            updateUserRequest.consentedToEmails = tmpConsentedToEmails;
            if(!(typeof tmpConsentedToEmails === 'boolean')) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Select if you consent to emails."));
                return;
            }
        
            updateUserRequest.twoFactorAuthType = twoFactorAuthType;
            if(!twoFactorAuthType) {
                dispatch(updateIsErrorOpen(true));
                dispatch(updateErrorMessage("Select two factor auth type."));
                return;
            }
            let updateUserResponse: UpdateUserResponse | undefined = await callUpdateUserRestEndpoint(updateUserRequest, sessionCode, env, domain);
            if(updateUserResponse?.isSuccess) {
                dispatch(updateIsHamburgerMenuOpen(false));
                dispatch(updateLoadingState(false));

                dispatch(updateUsername(tmpUsername));
                dispatch(updateEmailAddress(tmpEmailAddress));
                dispatch(updatePhoneNumber(tmpPhoneNumber));
                dispatch(updateConsentedToEmails(tmpConsentedToEmails));
                dispatch(updateConsentedToTexts(tmpConsentedToTexts));

                navigate('/');
            } else {
                dispatch(updateErrorMessage('Unable to update user'));
                dispatch(updateIsErrorOpen(true));
            }
        } catch(e) {
            dispatch(updateErrorMessage('Unable to update user'));
            dispatch(updateIsErrorOpen(true));
        } finally {
            dispatch(updateLoadingState(false));
        }
    }
    async function onClickCancel() {
        navigate('/');
    }
    return <div className='column2'>
        <form className="flexContainer" onSubmit={(e)=>{
                            e.preventDefault();
                            updateUserClickHandler(e);
                        }}>
            <h2>Update User</h2>
            <div className='flexInner'>
                <label >Username</label> 
            </div>
            <div className='flexInner'>
                <input id="username"
                    name="username" pattern="[A-Za-z0-9]+" required type="text"
                    value={tmpUsername} onChange={(event)=>dispatch(updateTmpUsername(event.target.value))}/>
            </div>
            <div className='flexInner'>
                <label>Email</label> 
            </div>
            <div className='flexInner'>
                <input id="email" name="email"
                    required
                    type="email"
                    value={tmpEmailAddress} onChange={(event)=>dispatch(updateTmpEmailAddress(event.target.value))}/>
            </div>
            <div className='flexInner'>
                <label>Phone</label> 
            </div>
            <div className='flexInner'>
                <input id="phone" name="phone"
                    pattern="[0-9]{10}" required 
                    type="tel" value={tmpPhoneNumber}
                     onChange={(event)=>dispatch(updateTmpPhoneNumber(event.target.value))}/>
            </div>
            <div className='flexInner'>
                <label>First Name</label> 
            </div>
            <div className='flexInner'>
                <input id="firstName"
                    name="firstName" pattern="[A-Za-z]{1,50}"
                    required disabled
                    type="text"
                    value={firstName} onChange={(event)=>dispatch(updateFirstName(event.target.value))}/>
            </div>
            <div className='flexInner'>
                <label>Last Name</label> 
            </div>
            <div className='flexInner'>
                <input id="lastName" 
                    name="lastName" pattern="[A-Za-z]{1,50}" disabled
                    required value={lastName} onChange={(event)=>dispatch(updateLastName(event.target.value))}
                    type="text"/>
            </div>
            <div className='flexInner'>
                <label >Date of Birth</label> 
            </div>
            <div className='flexInner'> 
                <input id="dateOfBirth"
                    name="dateOfBirth" required disabled
                    type="date"
                    value={dateOfBirth} onChange={(event)=>dispatch(updateDateOfBirth(event.target.value))}/>
            </div>
            <div className='flexInner'>
                <label >Do you consent to emails?</label>
            </div>
            <select id="consentedToEmail" name="consentedToEmail" required
                defaultValue={typeof tmpConsentedToEmails === 'boolean' ? tmpConsentedToEmails+"" : ""}
                onChange={(event)=>{dispatch(updateTmpConsentedToEmails(event.target.value === "true"))}}>
                <option disabled value="">-</option>
                <option value="true">Yes</option>
                <option value="false">No</option>
            </select>
            <div className='flexInner'>
                <label >Do you consent to text messages?</label>
            </div>
            <select id="consentedToText" name="consentedToText" required
                defaultValue={typeof tmpConsentedToTexts === 'boolean' ? tmpConsentedToTexts+"" : ""}
                onChange={(event)=>{
                    dispatch(updateTmpConsentedToTexts(event.target.value === "true"))
                }}>
                <option disabled value="">-</option>
                <option value="true">Yes</option>
                <option value="false">No</option>
            </select>
            <div className='flexInner'>
                <label >Two-factor authentication type?</label>
            </div>
            <select id="twoFactorSelect" name="twoFactor" required
                defaultValue={twoFactorAuthType ? twoFactorAuthType : ""} disabled
                onChange={(event)=>{dispatch(updateTwoFactorAuthType(event.target.value))}}>
                <option disabled value="">-</option>
                <option value="email">Email</option>
                <option value="text">Text</option>
                <option value="app">Authenticator App</option>
                <option value="none">None</option>
            </select>

            <div className='flexInner'>
                <button name="updateAccount" type="submit" >Update Account</button>
            </div>
            <div className='flexInner'>
                <button name="cancel" type="button" onClick={onClickCancel}>Cancel</button>
            </div>
        </form>
    </div>;
};
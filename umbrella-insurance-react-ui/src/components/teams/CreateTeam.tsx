import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { LoggingMessage } from "../../models/logging/v1/LoggingMessage";
import { updateCurrentPage, updateIsErrorOpen, updateErrorMessage, updateIsHamburgerMenuOpen } from "../../redux/reducers/AppReducer";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { updateUsername, updateEmailAddress, updatePhoneNumber, updateFirstName, updateLastName, updateDateOfBirth, updatePassword, updateConfirmPassword, updateConsentedToEmails, updateConsentedToTexts, updateTwoFactorAuthType, updateConsentedToTermsAndConditions, updateUsdAccountValue, updateButterBucksAccountValue, updateDidUserLoad, updateUsdEscrowAccountValue, updateButterBucksEscrowAccountValue, updateSessionCode, updateIsEmailAddressVerified, updateIsPhoneNumberVerified, updateAuthAppDataUrl, updateQrCode } from "../../redux/reducers/UserReducer";
import { RootState } from "../../redux/store/Store";

export default function CreateTeam() {
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


    return (
        <div className='column2' onClick={()=>dispatch(updateIsHamburgerMenuOpen(false))}>
            <form className="flexContainer" onSubmit={(e)=>{
                            e.preventDefault();

                        }}>
                <h2>Create Team</h2>
                <div className='flexInner'>
                    <label >Username</label>
                </div>
                <div className='flexInner'>
                    <input id="username" required autoFocus
                        name="username" pattern="[A-Za-z0-9]+" placeholder='JohnDoe99' minLength={8}
                    type="text" value={username} onChange={(event)=>dispatch(updateUsername(event.target.value?.toLowerCase()))}/>
                </div>
                <div className='flexInner'>
                    <button name="action" type="submit" >Submit</button>
                </div>

            </form>
        </div>);
};
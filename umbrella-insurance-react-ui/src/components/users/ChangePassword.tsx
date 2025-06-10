import '../../css/user/changePassword.css';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateConfirmNewPassword, updateExistingPassword, updateNewPassword, updateUsername, updateVerificationCode } from '../../redux/reducers/UserReducer';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { RootState } from '../../redux/store/Store';
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from '../../redux/reducers/AppReducer';
import { ChangePasswordResponse } from '../../endpoints/soa/user/v1/responses/ChangePasswordResponse';
import { ChangePasswordRequest } from '../../endpoints/soa/user/v1/requests/ChangePasswordRequest';
import { useNavigate } from 'react-router-dom';
import { callChangePasswordRestEndpoint } from '../../endpoints/soa/user/v1/UserSoaEndpoints';


export default function ChangePasswordPassword(){
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
    const verificationCode: string = useSelector((state: RootState)=>{
        return state.user.verificationCode;
    }) || "";
    const password: string = useSelector((state: RootState)=>{
        return state.user.password;
    }) || "";
    const sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";
    
    useEffect(
        function() {
            dispatch(updateCurrentPage("/changePassword"));
        }, []
    )
    
    async function changePassword(newPassword: string, confirmNewPassword: string, existingPassword: string) {
        try {
            dispatch(updateLoadingState(true));
            let changePasswordRequest: ChangePasswordRequest = new ChangePasswordRequest();
            changePasswordRequest.existingPassword = existingPassword;
            changePasswordRequest.newPassword = newPassword;
            let changePasswordResponse: ChangePasswordResponse = await callChangePasswordRestEndpoint(
                changePasswordRequest, sessionCode, env, domain);
            if(changePasswordResponse.isSuccess) {
                navigate("/");
            } else {
                dispatch(updateErrorMessage("Unable to change password."));
                dispatch(updateIsErrorOpen(true));
            }
        } catch(e) {
            dispatch(updateErrorMessage("Unable to change password."));
            dispatch(updateIsErrorOpen(true));
        } finally {
            dispatch(updateLoadingState(false));
        }
    }

    return (
    <div className="column2">
        <form className='flexContainer' onSubmit={(e)=>{
                e.preventDefault();
                changePassword(newPassword, confirmNewPassword, existingPassword);
        }} >
            <h2>Change Password</h2>
            <div className='flexInner' style={{display:"none"}}>
                <label hidden>Username</label>
            </div>
            <div className='flexInner' hidden>
                <input id="username" required autoFocus hidden disabled autoComplete="username"
                    name="username" pattern="[A-Za-z0-9]+" placeholder='JohnDoe99' minLength={8}
                type="text" value={username} onChange={(event)=>dispatch(updateUsername(event.target.value?.toLowerCase()))}/>
            </div>
            <div className='flexInner'>
                <label>New Password</label> 
            </div>
            <div className='flexInner'>
                <input
                    id="newPassword" name="newPassword" type="password" autoComplete='new-password'
                    onChange={(e)=>dispatch(updateNewPassword(e.target.value))}/>
            </div>
            <div className='flexInner'>
                <label>Confirm New Password</label> 
            </div>
            <div className='flexInner'>
                <input
                    id="confirmNewPassword" name="confirmNewPassword" type="password" autoComplete='new-password'
                    onChange={(e)=>dispatch(updateConfirmNewPassword(e.target.value))}/>
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

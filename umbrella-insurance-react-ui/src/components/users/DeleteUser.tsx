import '../../css/user/deleteUser.css';
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { DeleteUserResponse } from '../../endpoints/soa/user/v1/responses/DeleteUserResponse';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { resetUserReducer, updatePassword, updateUsername } from '../../redux/reducers/UserReducer';
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen, updateIsHamburgerMenuOpen } from '../../redux/reducers/AppReducer';
import { DeleteUserRequest } from '../../endpoints/soa/user/v1/requests/DeleteUserRequest';
import { callDeleteUserRestEndpointsByUserId } from '../../endpoints/soa/user/v1/UserSoaEndpoints';

export default function DeleteUser(){
    const navigate = useNavigate();
    const password: string = useSelector((state: RootState)=>{
        return state.user.password;
    }) || "";
    const sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";
    const env = useSelector((state: RootState) => {
        return state.environment.env
    })
    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    })
    const username: string = useSelector((state: RootState)=>{
        return state.user.username;
    }) || "";
    async function deleteUserClickHandler(event: any) {
        let deleteUserRequest: DeleteUserRequest = new DeleteUserRequest();
        deleteUserRequest.password = password;
        deleteUserRequest.username = username;
        let deleteUserResponse: DeleteUserResponse = await callDeleteUserRestEndpointsByUserId(deleteUserRequest, sessionCode, env, domain);
        if(deleteUserResponse?.isSuccess) {
            dispatch(resetUserReducer(""));
            dispatch(updateIsHamburgerMenuOpen(false));
            navigate('/');
        } else {
            dispatch(updateErrorMessage('Unable to delete user'));
            dispatch(updateIsErrorOpen(true));
        }
    }

    const dispatch = useDispatch();
    useEffect(
        function() {
            dispatch(updateCurrentPage("/deleteUser"));
        }, []
    );

    useEffect(
        function() {
            dispatch(updatePassword(""));
        }, []
    );

    return (
        <div className='column2'>
            <form className="flexContainer" onSubmit={(e)=>{
                e.preventDefault();
                deleteUserClickHandler(e);
            }}>
                <h2>Delete User Page</h2>
                <div className='flexInner' style={{"display":"none"}}>
                    <label hidden>Username</label>
                </div>
                <div className='flexInner' hidden>
                    <input id="username" required autoFocus hidden disabled autoComplete="username"
                        name="username" pattern="[A-Za-z0-9]+" placeholder='JohnDoe99' minLength={8}
                    type="text" value={username} onChange={(event)=>dispatch(updateUsername(event.target.value?.toLowerCase()))}/>
                </div>
                <div className='flexInner'>
                    <label>Password </label> 
                </div>
                <div className='flexInner'>
                    <input id="password"
                            name="password" type="password" value={password} autoComplete='current-password'
                            onChange={(event)=>dispatch(updatePassword(event.target.value))}/>
                </div>
                <div className='flexInner'>
                    <button id="deleteButton" name="action"
                            type="submit" >Delete Account
                    </button>
                </div>
                <div className='flexInner'>
                    <button name="action" type="button" >Back</button>
                </div>
            </form>
        </div>);
};
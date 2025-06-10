import '../../css/user/sendResetPassword.css';
import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { RootState } from '../../redux/store/Store';
import { updateUsername } from '../../redux/reducers/UserReducer';
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from '../../redux/reducers/AppReducer';
import { ResetPasswordRequest } from '../../endpoints/soa/user/v1/requests/ResetPasswordRequest';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { ResetPasswordResponse } from '../../endpoints/soa/user/v1/responses/ResetPasswordResponse';
import { callResetPasswordRestEndpoint } from '../../endpoints/soa/user/v1/UserSoaEndpoints';

export default function ResetPassword(){
    const navigate = useNavigate();
    const username: string = useSelector((state: RootState)=>{
        return state.user.username;
    }) || "";
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const domain = useSelector((state:RootState) => {
        return state.environment.domain;
    });
    const dispatch = useDispatch();
    useEffect(
        function() {
            dispatch(updateCurrentPage("/sendResetPassword"));
        }, []
    );

    async function onClickSubmit() {
        let resetPasswordRequest: ResetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.username = username;
        dispatch(updateLoadingState(true));
        let resetPasswordResponse: ResetPasswordResponse = await callResetPasswordRestEndpoint(resetPasswordRequest, env, domain);
        if(resetPasswordResponse) {
            dispatch(updateLoadingState(false));
            navigate("/signIn");
        } else {
            dispatch(updateErrorMessage("Unable to reset password."));
            dispatch(updateIsErrorOpen(true));
            dispatch(updateLoadingState(false));
        }
    }

    return(
    <div className='column2'>
        <form className="flexContainer" onSubmit={(e)=>{
                    e.preventDefault();
                    onClickSubmit();
                }}>
            <h2>Reset Password</h2>
            <div className='flexInner'>
                <label >Username</label> 
            </div>
            <div className='flexInner'>
                <input  autoComplete="username"
                    id="username"
                    name="username"
                    type="text"
                    onChange={(event)=>dispatch(updateUsername(event.target.value))}
                />
            </div>
            <div className='flexInner'>
                <button name="action" type="submit">Send Reset Password Email
                </button>
            </div>
            <div className='flexInner'>
                <button type='button'
                    onClick={(e)=>{
                        navigate("/");
                    }}>Cancel</button>
            </div>
        </form>
    </div>);
};
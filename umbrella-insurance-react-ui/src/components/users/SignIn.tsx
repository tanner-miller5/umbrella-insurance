import '../../css/user/signin.css';
import '../../css/loadings/loading.css';
import { Link, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { resetUserReducer, updatePassword, updateTwoFactorAuthType, updateUsername } from '../../redux/reducers/UserReducer';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import { useEffect } from 'react';
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from '../../redux/reducers/AppReducer';
import { CheckUserTwoFactorRequest } from '../../endpoints/soa/user/v1/requests/CheckUserTwoFactorRequest';
import { CheckUserTwoFactorResponse } from '../../endpoints/soa/user/v1/responses/CheckUserTwoFactorResponse';
import { callCheckTwoFactorUserRestEndpoints } from '../../endpoints/soa/user/v1/UserSoaEndpoints';

export default function SignIn(){
    const env = useSelector((state: RootState) => {
        return state.environment.env
    })
    const username: string = useSelector((state:RootState)=>{
        return state.user.username;
    }) || "";
    const password: string = useSelector((state:RootState)=>{
        return state.user.password;
    }) || "";
    const domain = useSelector((state:RootState) => {
        return state.environment.domain;
    })
    let navigate = useNavigate();
    let dispatch = useDispatch();
    useEffect(
        function() {
            dispatch(updateCurrentPage("/signIn"));
        }, []
    );
    useEffect(
        function() {
            dispatch(updateUsername(""))
        },[]
    );
    async function loginOnClick(username: string, password: string, env: string, domain: string) {
        let checkUserTwoFactorRequest: CheckUserTwoFactorRequest = new CheckUserTwoFactorRequest();
        checkUserTwoFactorRequest.username = username;
        let checkUserTwoFactorResponse: CheckUserTwoFactorResponse | undefined = undefined;
        dispatch(updateLoadingState(true));
        try {
            checkUserTwoFactorResponse = await callCheckTwoFactorUserRestEndpoints(
                checkUserTwoFactorRequest, env, domain);
            dispatch(updateLoadingState(false));
            dispatch(updateTwoFactorAuthType(checkUserTwoFactorResponse?.twoFactorAuthType));
            switch(checkUserTwoFactorResponse?.twoFactorAuthType) {
                case "none":
                    navigate("/enterPassword")
                    break;
                case "text":
                    navigate("/enterPassword")
                    break;
                case "email":
                    navigate("/enterPassword")
                    break;
                case "app":
                    navigate("/enterPassword")
                    break;
                default:
                    dispatch(updateErrorMessage("User not found"));
                    dispatch(updateIsErrorOpen(true));
            }
        } catch(e) {
            dispatch(updateErrorMessage("User not found"));
            dispatch(updateIsErrorOpen(true));
        } 
    }
    function onClickCreateUser() {
        navigate("/createUser");
    }
    function onClickResetPassword() {
        navigate("/sendResetPassword");
    }
    const isLoadingOpen = useSelector((state: RootState)=>{
        return state.loading.value
    });

    return (
        <div className='column2'>
            <form className='flexContainer' onSubmit={(e)=>{
                    e.preventDefault();
                    loginOnClick(username, password, env, domain);
                }}>
                <h2>Login Page</h2>
                <div className='flexInner'>
                    <label>Username</label> 
                </div>
                <div className='flexInner'>
                    <input minLength={8} autoFocus value={username} autoComplete='username'
                        id="username" name="username" type="text" onChange={(event)=>{
                        dispatch(updateUsername(event.target.value?.toLowerCase()));
                    }}/>
                </div>
                <div className='flexInner'>
                    <button name="action" type="submit" >Login</button>
                </div>
                <div className='flexInner'>
                    <button type="button" onClick={onClickResetPassword}>Reset Password</button>
                </div>
                <div className='flexInner'>
                    <button type="button" onClick={onClickCreateUser}>Create User</button>
                </div>
            </form>
        </div>
    );
};


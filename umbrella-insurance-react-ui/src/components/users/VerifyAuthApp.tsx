import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux/store/Store";
import '../../css/user/authApp.css'
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { updateCantSeeQrCode, updateDidUserLoad, updateQrCode, updateVerificationCode } from "../../redux/reducers/UserReducer";
import { useNavigate } from "react-router-dom";
import { VerifyOtpResponse } from "../../endpoints/soa/user/v1/responses/VerifyOtpResponse";
import { VerifyOtpRequest } from "../../endpoints/soa/user/v1/requests/VerifyOtpRequest";
import { SkipVerifyRequest } from "../../endpoints/soa/user/v1/requests/SkipVerifyRequest";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { SkipVerifyResponse } from "../../endpoints/soa/user/v1/responses/SkipVerifyResponse";
import { callVerifyOtpRestEndpoints, callSkipVerifyRestEndpoints } from "../../endpoints/soa/user/v1/UserSoaEndpoints";

export default function VerifyAuthApp(){
    let navigate = useNavigate();
    const dispatch = useDispatch();
    const qrCode = useSelector((state:RootState)=>{
        return state.user.qrCode;
    });
    const authAppDataUrl: string | undefined = useSelector((state:RootState)=>{
        return state.user.authAppDataUrl;
    });
    let secret: string | undefined = authAppDataUrl?.split("&")[1].split("=")[1];
    const cantSeeQrCode: boolean | undefined = useSelector((state:RootState)=>{
        return state.user.cantSeeQrCode;
    });
    const verificationCode = useSelector((state:RootState)=>{
        return state.user.verificationCode;
    }) || "";
    const username = useSelector((state:RootState)=>{
        return state.user.username;
    }) || "";
    const env = useSelector((state:RootState)=>{
        return state.environment.env;
    });
    const domain = useSelector((state:RootState)=>{
        return state.environment.domain;
    });
    const sessionCode = useSelector((state:RootState)=>{
        return state.user.sessionCode;
    }) || "";

    async function onCantSeeQrCodeClick() {
        dispatch(updateCantSeeQrCode(!cantSeeQrCode))
    }

    async function onOkayClick() {
        let verifyOtpRequest: VerifyOtpRequest = new VerifyOtpRequest();
        verifyOtpRequest.username = username;
        verifyOtpRequest.otp = verificationCode;
        const isCorrectOtp: VerifyOtpResponse | undefined = await callVerifyOtpRestEndpoints(verifyOtpRequest, env, domain);
        console.log(`isCorrectOtp: ${isCorrectOtp}`);
        if(isCorrectOtp) {
            dispatch(updateDidUserLoad(true));
            navigate("/")
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

    function otpInputChange(otp: string) {
        dispatch(updateVerificationCode(otp))
    }

    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/verifyAuthApp"));
        }, []);

    return (
    <div className="column2">
        <form className="flexContainer" onSubmit={(e)=>{
            e.preventDefault();
            onOkayClick();
        }}>
            {!cantSeeQrCode && <h1>Scan QR Code<br/>With Auth App</h1>}
            {!cantSeeQrCode && <img className="qrCodeImage" src={qrCode} alt="QR Code"/>}
            {cantSeeQrCode && <h1>Enter Secret</h1>}
            {cantSeeQrCode && <label>{secret}</label>}
            <div className="flexInner">
                {!cantSeeQrCode && <button onClick={onCantSeeQrCodeClick}>Show Secret</button>}
                {cantSeeQrCode && <button onClick={onCantSeeQrCodeClick}>Show QR Code</button>}
            </div>
            <div className="flexInner">
                <input type="text" placeholder="auth code" onChange={(e)=>otpInputChange(e.target.value)}></input>
            </div>

            <div className="flexInner">
                <button type="submit">Okay</button>
            </div>
            <div className="flexInner">
                <button onClick={onSkipClick} type="button">Skip</button>
            </div>
        
        </form>
    </div>);
}
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { User } from "../../../../models/users/v1/User";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";

import { ChangePasswordRequest } from "./requests/ChangePasswordRequest";
import { ChangeTwoFactorAuthRequest } from "./requests/ChangeTwoFactorAuthRequest";
import { CheckUserTwoFactorRequest } from "./requests/CheckUserTwoFactorRequest";
import { ConfirmUserEmailRequest } from "./requests/ConfirmUserEmailRequest";
import { ConfirmUserPhoneRequest } from "./requests/ConfirmUserPhoneRequest";
import { CreateUserRequest } from "./requests/CreateUserRequest";
import { DeleteUserRequest } from "./requests/DeleteUserRequest";
import { ResetPasswordRequest } from "./requests/ResetPasswordRequest";
import { SendEmailVerificationRequest } from "./requests/SendEmailVerificationRequest";
import { SendPhoneVerificationRequest } from "./requests/SendPhoneVerificationRequest";
import { SignOutRequest } from "./requests/SignOutRequest";
import { UpdateUserRequest } from "./requests/UpdateUserRequest";
import { VerifyOtpRequest } from "./requests/VerifyOtpRequest";
import { ChangePasswordResponse } from "./responses/ChangePasswordResponse";
import { ChangeTwoFactorAuthResponse } from "./responses/ChangeTwoFactorAuthResponse";
import { CheckUserTwoFactorResponse } from "./responses/CheckUserTwoFactorResponse";
import { ConfirmUserEmailResponse } from "./responses/ConfirmUserEmailResponse";
import { ConfirmUserPhoneResponse } from "./responses/ConfirmUserPhoneResponse";
import { CreateUserResponse } from "./responses/CreateUserResponse";
import { DeleteUserResponse } from "./responses/DeleteUserResponse";
import { ResetPasswordResponse } from "./responses/ResetPasswordResponse";
import { SendEmailVerificationResponse } from "./responses/SendEmailVerificationResponse";
import { SendPhoneVerificationResponse } from "./responses/SendPhoneVerificationResponse";
import { SignOutResponse } from "./responses/SignOutResponse";
import { SkipVerifyResponse } from "./responses/SkipVerifyResponse";
import { UpdateUserResponse } from "./responses/UpdateUserResponse";
import { VerifyOtpResponse } from "./responses/VerifyOtpResponse";

import {SignInRequest} from "./requests/SignInRequest";
import {SkipVerifyRequest} from "./requests/SkipVerifyRequest";

import {SignInResponse} from "./responses/SignInResponse";
import { callCreateLoggingRestEndpoints } from "../../../rest/logging/v1/LoggingRestEndpoints";
import { replacer } from "../../../rest/users/sessions/v1/SessionRestEndpoints";


export async function callCreateUserRestEndpoints(
    createUserRequest: CreateUserRequest, env: string, domain: string): Promise<CreateUserResponse>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1?env=${env}`;
    let data = JSON.stringify(createUserRequest as any, replacer);
    let createUserResponse: AxiosResponse<CreateUserResponse> = await axios.post(url, data, config);
    let user1 = new CreateUserResponse(createUserResponse.data);
    return user1;
}

export async function callReadUserRestEndpointsByUserId(
    userId: number, env: string, domain: string): Promise<User[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1?env=${env}&userId=${userId}`;
    let user: any | undefined = undefined;
    try {
        let readUserListResponse: AxiosResponse<User[]> = await axios.get(url, config);
        user = new User(readUserListResponse.data);
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();        
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return user;
}

export async function callReadUserRestEndpointsByUserName(
    userName: string, env: string, domain: string): Promise<User[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1?env=${env}&userName=${userName}`;
    let user: any | undefined = undefined;
    try {
        let readUserListResponse: AxiosResponse<User[]> = await axios.get(url, config);
        let users: User[] = []
        for(let i = 0; i < readUserListResponse.data.length; i++) {
            users[i] = new User(readUserListResponse.data[i]);
        }
        user = users;
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();        
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return user;
}

export async function callSignInRestEndpoints(
    SignInRequest: SignInRequest, env: string, domain: string): Promise<SignInResponse | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1/signIn?env=${env}`;
    let signInResponse: SignInResponse | undefined = undefined;
    try {
        let data = JSON.stringify(SignInRequest as any, replacer);
        let readUserListResponse: AxiosResponse<SignInResponse> = await axios.post(url, data, config);
        signInResponse = new SignInResponse(readUserListResponse.data);
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();        
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return signInResponse;
}

export async function callSignOutRestEndpoints(sessionCode: string,
    signOutRequest: SignOutRequest, env: string, domain: string): Promise<SignOutResponse | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": sessionCode,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1/signOut?env=${env}`;
    let signOutResponse: SignOutResponse | undefined = undefined;
    try {
        let data = JSON.stringify(signOutRequest as any, replacer);
        let readUserListResponse: AxiosResponse<SignOutResponse> = await axios.post(url, data, config);
        signOutResponse = new SignOutResponse(readUserListResponse.data);
    } catch(e:any) {
        signOutResponse = new SignOutResponse({isSuccess:false});
        signOutResponse.isSuccess = false;
        let loggingMessage: LoggingMessage = new LoggingMessage();        
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return signOutResponse;
}

export async function callSkipVerifyRestEndpoints(sessionCode: string,
    SkipVerifyRequest: SkipVerifyRequest, env: string, domain: string): Promise<SkipVerifyResponse | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": sessionCode,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1/skipVerify?env=${env}`;
    let skipVerifyResponse: SkipVerifyResponse | undefined = undefined;
    try {
        let data = JSON.stringify(SkipVerifyRequest as any, replacer);
        let response: AxiosResponse<SkipVerifyResponse> = await axios.post(url, data, config);
        skipVerifyResponse = response.data;
    } catch(e:any) {
        skipVerifyResponse = new SkipVerifyResponse({isSuccess:false});
        let loggingMessage: LoggingMessage = new LoggingMessage();        
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return skipVerifyResponse;
}

export async function callConfirmUserEmailRestEndpoints(sessionCode: string,
    confirmUserEmailRequest: ConfirmUserEmailRequest, env: string, domain: string): Promise<ConfirmUserEmailResponse | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": sessionCode,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1/confirmUserEmail?env=${env}`;
    let confirmUserEmailResponse: ConfirmUserEmailResponse | undefined = undefined;
    try {
        let data = JSON.stringify(confirmUserEmailRequest as any, replacer);
        let response: AxiosResponse<ConfirmUserEmailResponse> = await axios.post(url, data, config);
        confirmUserEmailResponse = new ConfirmUserEmailResponse(response.data);
    } catch(e:any) {
        confirmUserEmailResponse = new ConfirmUserEmailResponse({isSuccess:false});
        let loggingMessage: LoggingMessage = new LoggingMessage();        
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return confirmUserEmailResponse;
}

export async function callConfirmUserPhoneRestEndpoint(sessionCode: string,
    confirmUserPhoneRequest: ConfirmUserPhoneRequest, env: string, domain: string): Promise<ConfirmUserPhoneResponse | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": sessionCode,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1/confirmUserPhone?env=${env}`;
    let confirmUserPhoneResponse: ConfirmUserPhoneResponse | undefined = undefined;
    try {
        let data = JSON.stringify(confirmUserPhoneRequest as any, replacer);
        let response: AxiosResponse<ConfirmUserPhoneResponse> = await axios.post(url, data, config);
        confirmUserPhoneResponse = new ConfirmUserPhoneResponse(response.data);
    } catch(e:any) {
        confirmUserPhoneResponse = new ConfirmUserPhoneResponse({isSuccess:false});
        let loggingMessage: LoggingMessage = new LoggingMessage();        
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return confirmUserPhoneResponse;
}

export async function sendEmailVerification(
        sendEmailVerificationRequest: SendEmailVerificationRequest,
        env: string, domain: string): Promise<SendEmailVerificationResponse> {
    let sendEmailVerificationResponse: SendEmailVerificationResponse = new SendEmailVerificationResponse({isSuccess:false});
    try {
        let config: AxiosRequestConfig = {
            headers: {
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
            }
        }
        let url = `${domain}/soa/user/v1/sendEmailVerification?env=${env}`;
        let result: AxiosResponse<SendEmailVerificationResponse>  = await axios.post(
            url, sendEmailVerificationRequest, config);
        sendEmailVerificationResponse = new SendEmailVerificationResponse(result.data);
    } catch(e: any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();             
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = "UserRestEndpoints.ts/sendEmailVerification";        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return sendEmailVerificationResponse;
  }
  export async function sendPhoneVerification(
    sendPhoneVerificationRequest: SendPhoneVerificationRequest,
    env: string, domain: string): Promise<SendPhoneVerificationResponse> {
    let sendPhoneVerificationResponse: SendPhoneVerificationResponse = new SendPhoneVerificationResponse({isSuccess:false});
    try {
        let config: AxiosRequestConfig = {
            headers: {
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
            }
        }
        let url = `${domain}/soa/user/v1/sendPhoneVerification?env=${env}`;
        
        let result: AxiosResponse<SendPhoneVerificationResponse>  = await axios.post(url, sendPhoneVerificationRequest, config);
        sendPhoneVerificationResponse = new SendPhoneVerificationResponse(result.data);
    } catch(e: any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();             
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = "UserRestEndpoints.ts/sendPhoneVerification";        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }

  return sendPhoneVerificationResponse;
}

export async function callVerifyOtpRestEndpoints(
    verifyOtpRequest: VerifyOtpRequest, env: string, domain: string): Promise<VerifyOtpResponse | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1/verifyOtp?env=${env}`;
    let verifyOtpResponse: VerifyOtpResponse | undefined;
    try {
        let response: AxiosResponse<VerifyOtpResponse> = await axios.post(url, verifyOtpRequest, config);
        verifyOtpResponse = new VerifyOtpResponse(response.data);
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();        
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return verifyOtpResponse;
}

export async function callCheckTwoFactorUserRestEndpoints(
    checkUserTwoFactorRequest: CheckUserTwoFactorRequest, env: string, domain: string): Promise<CheckUserTwoFactorResponse | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1/checkTwoFactor?env=${env}`;
    let checkUserTwoFactorResponse: CheckUserTwoFactorResponse | undefined = undefined;
    try {
        let response: AxiosResponse<CheckUserTwoFactorResponse> = await axios.post(url, checkUserTwoFactorRequest, config);
        checkUserTwoFactorResponse = new CheckUserTwoFactorResponse(response.data);
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();         
        const url = window.location.href;         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = url;        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return checkUserTwoFactorResponse;
}

export async function callResetPasswordRestEndpoint(
    resetPasswordRequest: ResetPasswordRequest,
    env: string, domain: string): Promise<ResetPasswordResponse>  {
    let resetPasswordResponse :ResetPasswordResponse = new ResetPasswordResponse({isSuccess:false});
    try {
        let config: AxiosRequestConfig = {
            headers: {
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
            }
        }
        let url = `${domain}/soa/user/v1/resetPassword?env=${env}`;
        let data = JSON.stringify(resetPasswordRequest as any, replacer);
        let response: AxiosResponse<UpdateUserResponse> = await axios.put(url, data, config);
        resetPasswordResponse = new UpdateUserResponse(response.data);
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = "UserRestEndpoints.ts";        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return resetPasswordResponse;
}

export async function callChangePasswordRestEndpoint(
    changePasswordRequest: ChangePasswordRequest, sessionCode: string,
    env: string, domain: string): Promise<ChangePasswordResponse>  {
    let changePasswordResponse :ResetPasswordResponse = new ChangePasswordResponse({isSuccess:false});
    try {
        let config: AxiosRequestConfig = {
            headers: {
                "session": sessionCode,
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
            }
        }
        let url = `${domain}/soa/user/v1/changePassword?env=${env}`;
        let data = JSON.stringify(changePasswordRequest as any, replacer);
        let response: AxiosResponse<ChangePasswordResponse> = await axios.put(url, data, config);
        changePasswordResponse = new ChangePasswordResponse(response.data);
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = "UserRestEndpoints.ts";        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return changePasswordResponse;
}

export async function callChangeTwoFactorAuthTypeRestEndpoint(
    changeTwoFactorAuthRequest: ChangeTwoFactorAuthRequest, sessionCode: string,
    env: string, domain: string): Promise<ChangeTwoFactorAuthResponse>  {
    let changeTwoFactorAuthResponse :ChangeTwoFactorAuthResponse = new ChangeTwoFactorAuthResponse({isSuccess:false});
    try {
        let config: AxiosRequestConfig = {
            headers: {
                "session": sessionCode,
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
            }
        }
        let url = `${domain}/soa/user/v1/changeTwoFactorAuthType?env=${env}`;
        let data = JSON.stringify(changeTwoFactorAuthRequest as any, replacer);
        let response: AxiosResponse<ChangeTwoFactorAuthResponse> = await axios.put(url, data, config);
        changeTwoFactorAuthResponse = new ChangeTwoFactorAuthResponse(response.data);
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = "UserRestEndpoints.ts";        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return changeTwoFactorAuthResponse;
}

export async function callUpdateUserRestEndpoint(
        updateUserRequest: UpdateUserRequest, sessionCode: string, 
        env: string, domain: string): Promise<UpdateUserResponse>  {
    let updateUserResponse :UpdateUserResponse = new UpdateUserResponse({isSuccess:false});
    try {
        let config: AxiosRequestConfig = {
            headers: {
                "session": sessionCode,
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
            }
        }
        let url = `${domain}/soa/user/v1?env=${env}`;
        let data = JSON.stringify(updateUserRequest as any, replacer);
        let response: AxiosResponse<UpdateUserResponse> = await axios.put(url, data, config);
        updateUserResponse = new UpdateUserResponse(response.data);
    } catch(e:any) {
        let loggingMessage: LoggingMessage = new LoggingMessage();         
        loggingMessage.appName = 'umbrella-insurance-frontend';         
        loggingMessage.callingLoggerName = "UserRestEndpoints.ts";        
        loggingMessage.loggingPayload = `ERROR:${e.message}`;         
        loggingMessage.logLevel = "ERROR";         
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
        console.error(loggingMessage.loggingPayload);
    }
    return updateUserResponse;
}

export async function callUpdateUserRestEndpointsByUserName(
    user: User, userName: string, env: string, domain: string): Promise<User[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1?env=${env}&userName=${userName}`;
    let data = JSON.stringify(user as any, replacer);
    let updateUserListResponse: AxiosResponse<User[]> = await axios.put(url, data, config);
    let users: User[] = []
    for(let i = 0; i < updateUserListResponse.data.length; i++) {
        users[i] = new User(updateUserListResponse.data[i]);
    }
    let userList = users;
    return userList;
}

export async function callDeleteUserRestEndpointsByUserId(
    deleteUserRequest: DeleteUserRequest, sessionCode: string,
    env: string, domain: string): Promise<DeleteUserResponse>  {
    let config = {
        headers: {
            "session": sessionCode,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let data = JSON.stringify(deleteUserRequest as any, replacer);
    let url = `${domain}/soa/user/v1/deleteUser?env=${env}`;
    let deleteUserResponse: AxiosResponse<DeleteUserResponse> = await axios.post(url, data, config);
    return new DeleteUserResponse(deleteUserResponse.data);
}

export async function callDeleteUserRestEndpointsByUserName(
    userName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/soa/user/v1?env=${env}&userName=${userName}` ;
    let deleteUserResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteUserResponse.status == 204;
}
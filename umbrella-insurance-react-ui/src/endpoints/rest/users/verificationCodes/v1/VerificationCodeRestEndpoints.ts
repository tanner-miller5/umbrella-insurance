import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { VerificationCode } from "../../../../../models/users/verificationCodes/v1/VerificationCode";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateVerificationCodeRestEndpoints(
    verificationCode: VerificationCode, env: string, domain: string): Promise<VerificationCode>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationCodes/v1?env=${env}`;
    let data = JSON.stringify(verificationCode as any, replacer);
    let createVerificationCodeResponse: AxiosResponse<VerificationCode> = await axios.post(url, data, config);
    let verificationCode1 = new VerificationCode(createVerificationCodeResponse.data);
    return verificationCode1;
}

export async function callReadVerificationCodeRestEndpointsByVerificationCodeId(
    verificationCodeId: number, env: string, domain: string): Promise<VerificationCode[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationCodes/v1?env=${env}&verificationCodeId=${verificationCodeId}`;
    let verificationCodes: VerificationCode[] | undefined = [];
    try {
        let readVerificationCodeListResponse: AxiosResponse<VerificationCode[]> = await axios.get(url, config);
        let verificationCodeList = readVerificationCodeListResponse.data;
        for(let i = 0; i < verificationCodeList.length; i++) {
            verificationCodes[i] = new VerificationCode(verificationCodeList[i]);
        }
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
    return verificationCodes;
}

export async function callReadVerificationCodeRestEndpointsByVerificationCodeName(
    verificationCodeName: string, env: string, domain: string): Promise<VerificationCode[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationCodes/v1?env=${env}&verificationCodeName=${verificationCodeName}`;
    let verificationCodes: VerificationCode[] | undefined = [];
    try {
        let readVerificationCodeListResponse: AxiosResponse<VerificationCode[]> = await axios.get(url, config);
        let verificationCodeList = readVerificationCodeListResponse.data;
        for(let i = 0; i < verificationCodeList.length; i++) {
            verificationCodes[i] = new VerificationCode(verificationCodeList[i]);
        }
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
    return verificationCodes;
}

export async function callUpdateVerificationCodeRestEndpoints(
    verificationCode: VerificationCode, env: string, domain: string): Promise<VerificationCode[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationCodes/v1?env=${env}`;
    let data = JSON.stringify(verificationCode as any, replacer);
    let updateVerificationCodeListResponse: AxiosResponse<VerificationCode[]> = await axios.put(url, data, config);
    let verificationCodeList = updateVerificationCodeListResponse.data;
    let verificationCodes: VerificationCode[] | undefined = [];
    for(let i = 0; i < verificationCodeList.length; i++) {
        verificationCodes[i] = new VerificationCode(verificationCodeList[i]);
    }
    return verificationCodes;
}

export async function callDeleteVerificationCodeRestEndpointsByVerificationCodeId(
    verificationCodeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationCodes/v1?env=${env}&verificationCodeId=${verificationCodeId}` ;
    let deleteVerificationCodeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteVerificationCodeResponse.status == 204;
}

export async function callDeleteVerificationCodeRestEndpointsByVerificationCodeName(
    verificationCodeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationCodes/v1?env=${env}&verificationCodeName=${verificationCodeName}` ;
    let deleteVerificationCodeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteVerificationCodeResponse.status == 204;
}
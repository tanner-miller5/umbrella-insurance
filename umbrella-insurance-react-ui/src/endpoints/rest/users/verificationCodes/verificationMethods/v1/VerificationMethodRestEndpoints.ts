import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { VerificationMethod } from "../../../../../../models/users/verificationCodes/verificationMethods/v1/VerificationMethod";
import { replacer } from "../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateVerificationMethodRestEndpoints(
    verificationMethod: VerificationMethod, env: string, domain: string): Promise<VerificationMethod>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationMethods/v1?env=${env}`;
    let data = JSON.stringify(verificationMethod as any, replacer);
    let createVerificationMethodResponse: AxiosResponse<VerificationMethod> = await axios.post(url, data, config);
    let verificationMethod1 = new VerificationMethod(createVerificationMethodResponse.data);
    return verificationMethod1;
}

export async function callReadVerificationMethodRestEndpointsByVerificationMethodId(
    verificationMethodId: number, env: string, domain: string): Promise<VerificationMethod[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationMethods/v1?env=${env}&verificationMethodId=${verificationMethodId}`;
    let verificationMethods: VerificationMethod[] | undefined = [];
    try {
        let readVerificationMethodListResponse: AxiosResponse<VerificationMethod[]> = await axios.get(url, config);
        let verificationMethodList = readVerificationMethodListResponse.data;
        for(let i = 0; i < verificationMethodList.length; i++) {
            verificationMethods[i] = new VerificationMethod(verificationMethodList[i]);
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
    return verificationMethods;
}

export async function callReadVerificationMethodRestEndpointsByVerificationMethodName(
    verificationMethodName: string, env: string, domain: string): Promise<VerificationMethod[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationMethods/v1?env=${env}&verificationMethodName=${verificationMethodName}`;
    let verificationMethods: VerificationMethod[] | undefined = [];
    try {
        let readVerificationMethodListResponse: AxiosResponse<VerificationMethod[]> = await axios.get(url, config);
        let verificationMethodList = readVerificationMethodListResponse.data;
        for(let i = 0; i < verificationMethodList.length; i++) {
            verificationMethods[i] = new VerificationMethod(verificationMethodList[i]);
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
    return verificationMethods;
}

export async function callUpdateVerificationMethodRestEndpoints(
    verificationMethod: VerificationMethod, env: string, domain: string): Promise<VerificationMethod[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationMethods/v1?env=${env}`;
    let data = JSON.stringify(verificationMethod as any, replacer);
    let updateVerificationMethodListResponse: AxiosResponse<VerificationMethod[]> = await axios.put(url, data, config);
    let verificationMethodList = updateVerificationMethodListResponse.data;
    let verificationMethods: VerificationMethod[] | undefined = [];
    for(let i = 0; i < verificationMethodList.length; i++) {
        verificationMethods[i] = new VerificationMethod(verificationMethodList[i]);
    }
    return verificationMethods;
}

export async function callDeleteVerificationMethodRestEndpointsByVerificationMethodId(
    verificationMethodId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationMethods/v1?env=${env}&verificationMethodId=${verificationMethodId}` ;
    let deleteVerificationMethodResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteVerificationMethodResponse.status == 204;
}

export async function callDeleteVerificationMethodRestEndpointsByVerificationMethodName(
    verificationMethodName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/verificationMethods/v1?env=${env}&verificationMethodName=${verificationMethodName}` ;
    let deleteVerificationMethodResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteVerificationMethodResponse.status == 204;
}
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { UserAgreement } from "../../../../../models/users/userAgreements/v1/UserAgreement";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateUserAgreementRestEndpoints(
    userAgreement: UserAgreement, env: string, domain: string): Promise<UserAgreement>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userAgreements/v1?env=${env}`;
    let data = JSON.stringify(userAgreement as any, replacer);
    let createUserAgreementResponse: AxiosResponse<UserAgreement> = await axios.post(url, data, config);
    let userAgreement1 = new UserAgreement(createUserAgreementResponse.data);
    return userAgreement1;
}

export async function callReadUserAgreementRestEndpointsByUserAgreementId(
    userAgreementId: number, env: string, domain: string): Promise<UserAgreement[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userAgreements/v1?env=${env}&userAgreementId=${userAgreementId}`;
    let userAgreements: UserAgreement[] | undefined = [];
    try {
        let readUserAgreementListResponse: AxiosResponse<UserAgreement[]> = await axios.get(url, config);
        let userAgreementList = readUserAgreementListResponse.data;
        for(let i = 0; i < userAgreementList.length; i++) {
            userAgreements[i] = new UserAgreement(userAgreementList[i]);
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
    return userAgreements;
}

export async function callReadUserAgreementRestEndpointsByUserAgreementName(
    userAgreementName: string, env: string, domain: string): Promise<UserAgreement[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userAgreements/v1?env=${env}&userAgreementName=${userAgreementName}`;
    let userAgreements: UserAgreement[] | undefined = [];
    try {
        let readUserAgreementListResponse: AxiosResponse<UserAgreement[]> = await axios.get(url, config);
        let userAgreementList = readUserAgreementListResponse.data;
        for(let i = 0; i < userAgreementList.length; i++) {
            userAgreements[i] = new UserAgreement(userAgreementList[i]);
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
    return userAgreements;
}

export async function callUpdateUserAgreementRestEndpoints(
    userAgreement: UserAgreement, env: string, domain: string): Promise<UserAgreement[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userAgreements/v1?env=${env}`;
    let data = JSON.stringify(userAgreement as any, replacer);
    let updateUserAgreementListResponse: AxiosResponse<UserAgreement[]> = await axios.put(url, data, config);
    let userAgreementList = updateUserAgreementListResponse.data;
    let userAgreements: UserAgreement[] | undefined = [];
    for(let i = 0; i < userAgreementList.length; i++) {
        userAgreements[i] = new UserAgreement(userAgreementList[i]);
    }
    return userAgreements;
}

export async function callDeleteUserAgreementRestEndpointsByUserAgreementId(
    userAgreementId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userAgreements/v1?env=${env}&userAgreementId=${userAgreementId}` ;
    let deleteUserAgreementResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteUserAgreementResponse.status == 204;
}

export async function callDeleteUserAgreementRestEndpointsByUserAgreementName(
    userAgreementName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userAgreements/v1?env=${env}&userAgreementName=${userAgreementName}` ;
    let deleteUserAgreementResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteUserAgreementResponse.status == 204;
}
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { EmailAddressHistory } from "../../../../../models/users/emailAddressesHistory/v1/EmailAddressHistory";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";


export async function callCreateEmailAddressHistoryRestEndpoints(
    emailAddressHistory: EmailAddressHistory, env: string, domain: string): Promise<EmailAddressHistory>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/emailAddressHistory/v1?env=${env}`;
    let data = JSON.stringify(emailAddressHistory as any, replacer);
    let createEmailAddressHistoryResponse: AxiosResponse<EmailAddressHistory> = await axios.post(url, data, config);
    let emailAddressHistory1 = new EmailAddressHistory(createEmailAddressHistoryResponse.data);
    return emailAddressHistory1;
}

export async function callReadEmailAddressHistoryRestEndpointsByEmailAddressHistoryId(
    emailAddressHistoryId: number, env: string, domain: string): Promise<EmailAddressHistory[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/emailAddressHistory/v1?env=${env}&emailAddressHistoryId=${emailAddressHistoryId}`;
    let emailAddressHistorys: EmailAddressHistory[] | undefined = [];
    try {
        let readEmailAddressHistoryListResponse: AxiosResponse<EmailAddressHistory[]> = await axios.get(url, config);
        let emailAddressHistoryList = readEmailAddressHistoryListResponse.data;
        for(let i = 0; i < emailAddressHistoryList.length; i++) {
            emailAddressHistorys[i] = new EmailAddressHistory(emailAddressHistoryList[i]);
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
    return emailAddressHistorys;
}

export async function callReadEmailAddressHistoryRestEndpointsByEmailAddressHistoryName(
    emailAddressHistoryName: string, env: string, domain: string): Promise<EmailAddressHistory[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/emailAddressHistory/v1?env=${env}&emailAddressHistoryName=${emailAddressHistoryName}`;
    let emailAddressHistorys: EmailAddressHistory[] | undefined = [];
    try {
        let readEmailAddressHistoryListResponse: AxiosResponse<EmailAddressHistory[]> = await axios.get(url, config);
        let emailAddressHistoryList = readEmailAddressHistoryListResponse.data;
        for(let i = 0; i < emailAddressHistoryList.length; i++) {
            emailAddressHistorys[i] = new EmailAddressHistory(emailAddressHistoryList[i]);
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
    return emailAddressHistorys;
}

export async function callUpdateEmailAddressHistoryRestEndpoints(
    emailAddressHistory: EmailAddressHistory, env: string, domain: string): Promise<EmailAddressHistory[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/emailAddressHistory/v1?env=${env}`;
    let data = JSON.stringify(emailAddressHistory as any, replacer);
    let updateEmailAddressHistoryListResponse: AxiosResponse<EmailAddressHistory[]> = await axios.put(url, data, config);
    let emailAddressHistoryList = updateEmailAddressHistoryListResponse.data;
    let emailAddressHistorys: EmailAddressHistory[] | undefined = [];
    for(let i = 0; i < emailAddressHistoryList.length; i++) {
        emailAddressHistorys[i] = new EmailAddressHistory(emailAddressHistoryList[i]);
    }
    return emailAddressHistorys;
}

export async function callDeleteEmailAddressHistoryRestEndpointsByEmailAddressHistoryId(
    emailAddressHistoryId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/emailAddressHistory/v1?env=${env}&emailAddressHistoryId=${emailAddressHistoryId}` ;
    let deleteEmailAddressHistoryResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEmailAddressHistoryResponse.status == 204;
}

export async function callDeleteEmailAddressHistoryRestEndpointsByEmailAddressHistoryName(
    emailAddressHistoryName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/emailAddressHistory/v1?env=${env}&emailAddressHistoryName=${emailAddressHistoryName}` ;
    let deleteEmailAddressHistoryResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEmailAddressHistoryResponse.status == 204;
}
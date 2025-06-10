import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";
import { UsernameHistory } from "../../../../../models/users/usernamesHistory/v1/UsernameHistory";

export async function callCreateUsernameHistoryRestEndpoints(
    usernameHistory: UsernameHistory, env: string, domain: string): Promise<UsernameHistory>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/usernameHistory/v1?env=${env}`;
    let data = JSON.stringify(usernameHistory as any, replacer);
    let createUsernameHistoryResponse: AxiosResponse<UsernameHistory> = await axios.post(url, data, config);
    let usernameHistory1 = new UsernameHistory(createUsernameHistoryResponse.data);
    return usernameHistory1;
}

export async function callReadUsernameHistoryRestEndpointsByUsernameHistoryId(
    usernameHistoryId: number, env: string, domain: string): Promise<UsernameHistory[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/usernameHistory/v1?env=${env}&usernameHistoryId=${usernameHistoryId}`;
    let usernameHistorys: UsernameHistory[] | undefined = [];
    try {
        let readUsernameHistoryListResponse: AxiosResponse<UsernameHistory[]> = await axios.get(url, config);
        let usernameHistoryList = readUsernameHistoryListResponse.data;
        for(let i = 0; i < usernameHistoryList.length; i++) {
            usernameHistorys[i] = new UsernameHistory(usernameHistoryList[i]);
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
    return usernameHistorys;
}

export async function callReadUsernameHistoryRestEndpointsByUsernameHistoryName(
    usernameHistoryName: string, env: string, domain: string): Promise<UsernameHistory[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/usernameHistory/v1?env=${env}&usernameHistoryName=${usernameHistoryName}`;
    let usernameHistorys: UsernameHistory[] | undefined = [];
    try {
        let readUsernameHistoryListResponse: AxiosResponse<UsernameHistory[]> = await axios.get(url, config);
        let usernameHistoryList = readUsernameHistoryListResponse.data;
        for(let i = 0; i < usernameHistoryList.length; i++) {
            usernameHistorys[i] = new UsernameHistory(usernameHistoryList[i]);
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
    return usernameHistorys;
}

export async function callUpdateUsernameHistoryRestEndpoints(
    usernameHistory: UsernameHistory, env: string, domain: string): Promise<UsernameHistory[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/usernameHistory/v1?env=${env}`;
    let data = JSON.stringify(usernameHistory as any, replacer);
    let updateUsernameHistoryListResponse: AxiosResponse<UsernameHistory[]> = await axios.put(url, data, config);
    let usernameHistoryList = updateUsernameHistoryListResponse.data;
    let usernameHistorys: UsernameHistory[] | undefined = [];
    for(let i = 0; i < usernameHistoryList.length; i++) {
        usernameHistorys[i] = new UsernameHistory(usernameHistoryList[i]);
    }
    return usernameHistorys;
}

export async function callDeleteUsernameHistoryRestEndpointsByUsernameHistoryId(
    usernameHistoryId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/usernameHistory/v1?env=${env}&usernameHistoryId=${usernameHistoryId}` ;
    let deleteUsernameHistoryResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteUsernameHistoryResponse.status == 204;
}

export async function callDeleteUsernameHistoryRestEndpointsByUsernameHistoryName(
    usernameHistoryName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/usernameHistory/v1?env=${env}&usernameHistoryName=${usernameHistoryName}` ;
    let deleteUsernameHistoryResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteUsernameHistoryResponse.status == 204;
}
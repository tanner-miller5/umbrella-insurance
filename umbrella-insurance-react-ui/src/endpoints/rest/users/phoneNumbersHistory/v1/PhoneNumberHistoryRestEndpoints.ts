import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PhoneNumberHistory } from "../../../../../models/users/phoneNumbersHistory/v1/PhoneNumberHistory";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePhoneNumberHistoryRestEndpoints(
    phoneNumberHistory: PhoneNumberHistory, env: string, domain: string): Promise<PhoneNumberHistory>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/phoneNumberHistory/v1?env=${env}`;
    let data = JSON.stringify(phoneNumberHistory as any, replacer);
    let createPhoneNumberHistoryResponse: AxiosResponse<PhoneNumberHistory> = await axios.post(url, data, config);
    let phoneNumberHistory1 = new PhoneNumberHistory(createPhoneNumberHistoryResponse.data);
    return phoneNumberHistory1;
}

export async function callReadPhoneNumberHistoryRestEndpointsByPhoneNumberHistoryId(
    phoneNumberHistoryId: number, env: string, domain: string): Promise<PhoneNumberHistory[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/phoneNumberHistory/v1?env=${env}&phoneNumberHistoryId=${phoneNumberHistoryId}`;
    let phoneNumberHistorys: PhoneNumberHistory[] | undefined = [];
    try {
        let readPhoneNumberHistoryListResponse: AxiosResponse<PhoneNumberHistory[]> = await axios.get(url, config);
        let phoneNumberHistoryList = readPhoneNumberHistoryListResponse.data;
        for(let i = 0; i < phoneNumberHistoryList.length; i++) {
            phoneNumberHistorys[i] = new PhoneNumberHistory(phoneNumberHistoryList[i]);
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
    return phoneNumberHistorys;
}

export async function callReadPhoneNumberHistoryRestEndpointsByPhoneNumberHistoryName(
    phoneNumberHistoryName: string, env: string, domain: string): Promise<PhoneNumberHistory[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/phoneNumberHistory/v1?env=${env}&phoneNumberHistoryName=${phoneNumberHistoryName}`;
    let phoneNumberHistorys: PhoneNumberHistory[] | undefined = [];
    try {
        let readPhoneNumberHistoryListResponse: AxiosResponse<PhoneNumberHistory[]> = await axios.get(url, config);
        let phoneNumberHistoryList = readPhoneNumberHistoryListResponse.data;
        for(let i = 0; i < phoneNumberHistoryList.length; i++) {
            phoneNumberHistorys[i] = new PhoneNumberHistory(phoneNumberHistoryList[i]);
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
    return phoneNumberHistorys;
}

export async function callUpdatePhoneNumberHistoryRestEndpoints(
    phoneNumberHistory: PhoneNumberHistory, env: string, domain: string): Promise<PhoneNumberHistory[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/phoneNumberHistory/v1?env=${env}`;
    let data = JSON.stringify(phoneNumberHistory as any, replacer);
    let phoneNumberHistorys: PhoneNumberHistory[] | undefined = [];
    let updatePhoneNumberHistoryListResponse: AxiosResponse<PhoneNumberHistory[]> = await axios.put(url, data, config);
    let phoneNumberHistoryList = updatePhoneNumberHistoryListResponse.data;
    for(let i = 0; i < phoneNumberHistoryList.length; i++) {
        phoneNumberHistorys[i] = new PhoneNumberHistory(phoneNumberHistoryList[i]);
    }
    return phoneNumberHistorys;
}

export async function callDeletePhoneNumberHistoryRestEndpointsByPhoneNumberHistoryId(
    phoneNumberHistoryId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/phoneNumberHistory/v1?env=${env}&phoneNumberHistoryId=${phoneNumberHistoryId}` ;
    let deletePhoneNumberHistoryResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePhoneNumberHistoryResponse.status == 204;
}

export async function callDeletePhoneNumberHistoryRestEndpointsByPhoneNumberHistoryName(
    phoneNumberHistoryName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/phoneNumberHistory/v1?env=${env}&phoneNumberHistoryName=${phoneNumberHistoryName}` ;
    let deletePhoneNumberHistoryResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePhoneNumberHistoryResponse.status == 204;
}
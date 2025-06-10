import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";
import { TOTP } from "../../../../../models/users/totps/v1/Totp";

export async function callCreateTotpRestEndpoints(
    totp: TOTP, env: string, domain: string): Promise<TOTP>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/totps/v1?env=${env}`;
    let data = JSON.stringify(totp as any, replacer);
    let createTotpResponse: AxiosResponse<TOTP> = await axios.post(url, data, config);
    let totp1 = new TOTP(createTotpResponse.data);
    return totp1;
}

export async function callReadTotpRestEndpointsByTotpId(
    totpId: number, env: string, domain: string): Promise<TOTP[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/totps/v1?env=${env}&totpId=${totpId}`;
    let totps: TOTP[] | undefined = [];
    try {
        let readTotpListResponse: AxiosResponse<TOTP[]> = await axios.get(url, config);
        let totpList = readTotpListResponse.data;
        for(let i = 0; i < totpList.length; i++) {
            totps[i] = new TOTP(totpList[i]);
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
    return totps;
}

export async function callReadTotpRestEndpointsByTotpName(
    totpName: string, env: string, domain: string): Promise<TOTP[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/totps/v1?env=${env}&totpName=${totpName}`;
    let totps: TOTP[] | undefined = [];
    try {
        let readTotpListResponse: AxiosResponse<TOTP[]> = await axios.get(url, config);
        let totpList = readTotpListResponse.data;
        for(let i = 0; i < totpList.length; i++) {
            totps[i] = new TOTP(totpList[i]);
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
    return totps;
}

export async function callUpdateTotpRestEndpoints(
    totp: TOTP, env: string, domain: string): Promise<TOTP[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/totps/v1?env=${env}`;
    let data = JSON.stringify(totp as any, replacer);
    let updateTotpListResponse: AxiosResponse<TOTP[]> = await axios.put(url, data, config);
    let totpList = updateTotpListResponse.data;
    let totps: TOTP[] | undefined = [];
    for(let i = 0; i < totpList.length; i++) {
        totps[i] = new TOTP(totpList[i]);
    }
    return totps;
}

export async function callDeleteTotpRestEndpointsByTotpId(
    totpId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/totps/v1?env=${env}&totpId=${totpId}` ;
    let deleteTotpResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTotpResponse.status == 204;
}

export async function callDeleteTotpRestEndpointsByTotpName(
    totpName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/totps/v1?env=${env}&totpName=${totpName}` ;
    let deleteTotpResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTotpResponse.status == 204;
}
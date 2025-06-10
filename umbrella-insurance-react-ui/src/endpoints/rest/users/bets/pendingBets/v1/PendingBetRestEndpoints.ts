import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PendingBet } from "../../../../../../models/users/bets/pendingBets/v1/PendingBet";
import { replacer } from "../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";


export async function callCreatePendingBetRestEndpoints(
    pendingBet: PendingBet, env: string, domain: string): Promise<PendingBet>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBets/v1?env=${env}`;
    let data = JSON.stringify(pendingBet as any, replacer);
    let createPendingBetResponse: AxiosResponse<PendingBet> = await axios.post(url, data, config);
    let pendingBet1 = new PendingBet(createPendingBetResponse.data);
    return pendingBet1;
}

export async function callReadPendingBetRestEndpointsByPendingBetId(
    pendingBetId: number, env: string, domain: string): Promise<PendingBet[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBets/v1?env=${env}&pendingBetId=${pendingBetId}`;
    let pendingBets: PendingBet[] | undefined = [];
    try {
        let readPendingBetListResponse: AxiosResponse<PendingBet[]> = await axios.get(url, config);
        let pendingBetList = readPendingBetListResponse.data;
        for(let i = 0; i < pendingBetList.length; i++) {
            pendingBets[i] = new PendingBet(pendingBetList[i]);
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
    return pendingBets;
}

export async function callReadPendingBetRestEndpointsByPendingBetName(
    pendingBetName: string, env: string, domain: string): Promise<PendingBet[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBets/v1?env=${env}&pendingBetName=${pendingBetName}`;
    let pendingBets: PendingBet[] | undefined = [];
    try {
        let readPendingBetListResponse: AxiosResponse<PendingBet[]> = await axios.get(url, config);
        let pendingBetList = readPendingBetListResponse.data;
        for(let i = 0; i < pendingBetList.length; i++) {
            pendingBets[i] = new PendingBet(pendingBetList[i]);
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
    return pendingBets;
}

export async function callUpdatePendingBetRestEndpoints(
    pendingBet: PendingBet, env: string, domain: string): Promise<PendingBet[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBets/v1?env=${env}`;
    let data = JSON.stringify(pendingBet as any, replacer);
    let updatePendingBetListResponse: AxiosResponse<PendingBet[]> = await axios.put(url, data, config);
    let pendingBetList = updatePendingBetListResponse.data;
    let pendingBets: PendingBet[] | undefined = [];
    for(let i = 0; i < pendingBetList.length; i++) {
        pendingBets[i] = new PendingBet(pendingBetList[i]);
    }
    return pendingBets;
}

export async function callDeletePendingBetRestEndpointsByPendingBetId(
    pendingBetId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBets/v1?env=${env}&pendingBetId=${pendingBetId}` ;
    let deletePendingBetResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingBetResponse.status == 204;
}

export async function callDeletePendingBetRestEndpointsByPendingBetName(
    pendingBetName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingBets/v1?env=${env}&pendingBetName=${pendingBetName}` ;
    let deletePendingBetResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingBetResponse.status == 204;
}
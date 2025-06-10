import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { BetType } from "../../../../../../models/users/bets/betTypes/v1/BetType";
import { replacer } from "../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";


export async function callCreateBetTypeRestEndpoints(
    betType: BetType, env: string, domain: string): Promise<BetType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/betTypes/v1?env=${env}`;
    let data = JSON.stringify(betType as any, replacer);
    let createBetTypeResponse: AxiosResponse<BetType> = await axios.post(url, data, config);
    let betType1 = new BetType(createBetTypeResponse.data);
    return betType1;
}

export async function callReadBetTypeRestEndpointsByBetTypeId(
    betTypeId: number, env: string, domain: string): Promise<BetType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/betTypes/v1?env=${env}&betTypeId=${betTypeId}`;
    let betTypes: BetType[] | undefined = [];
    try {
        let readBetTypeListResponse: AxiosResponse<BetType[]> = await axios.get(url, config);
        let betTypeList = readBetTypeListResponse.data;
        for(let i = 0; i < betTypeList.length; i++) {
            betTypes[i] = new BetType(betTypeList[i]);
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
    return betTypes;
}

export async function callReadBetTypeRestEndpointsByBetTypeName(
    betTypeName: string, env: string, domain: string): Promise<BetType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/betTypes/v1?env=${env}&betTypeName=${betTypeName}`;
    let betTypes: BetType[] | undefined = [];
    try {
        let readBetTypeListResponse: AxiosResponse<BetType[]> = await axios.get(url, config);
        let betTypeList = readBetTypeListResponse.data;
        for(let i = 0; i < betTypeList.length; i++) {
            betTypes[i] = new BetType(betTypeList[i]);
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
    return betTypes;
}

export async function callUpdateBetTypeRestEndpoints(
    betType: BetType, env: string, domain: string): Promise<BetType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/betTypes/v1?env=${env}`;
    let data = JSON.stringify(betType as any, replacer);
    let updateBetTypeListResponse: AxiosResponse<BetType[]> = await axios.put(url, data, config);
    let betTypeList = updateBetTypeListResponse.data;
    let betTypes: BetType[] | undefined = [];
    for(let i = 0; i < betTypeList.length; i++) {
        betTypes[i] = new BetType(betTypeList[i]);
    }
    return betTypes;
}

export async function callDeleteBetTypeRestEndpointsByBetTypeId(
    betTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/betTypes/v1?env=${env}&betTypeId=${betTypeId}` ;
    let deleteBetTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteBetTypeResponse.status == 204;
}

export async function callDeleteBetTypeRestEndpointsByBetTypeName(
    betTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/betTypes/v1?env=${env}&betTypeName=${betTypeName}` ;
    let deleteBetTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteBetTypeResponse.status == 204;
}
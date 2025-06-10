import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Fee } from "../../../../../models/users/fees/v1/Fee";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateFeeRestEndpoints(
    fee: Fee, env: string, domain: string): Promise<Fee>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/fees/v1?env=${env}`;
    let data = JSON.stringify(fee as any, replacer);
    let createFeeResponse: AxiosResponse<Fee> = await axios.post(url, data, config);
    let fee1 = new Fee(createFeeResponse.data);
    return fee1;
}

export async function callReadFeeRestEndpointsByFeeId(
    feeId: number, env: string, domain: string): Promise<Fee[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/fees/v1?env=${env}&feeId=${feeId}`;
    let fees: Fee[] | undefined = [];
    try {
        let readFeeListResponse: AxiosResponse<Fee[]> = await axios.get(url, config);
        let feeList = readFeeListResponse.data;
        for(let i = 0; i < feeList.length; i++) {
            fees[i] = new Fee(feeList[i]);
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
    return fees;
}

export async function callReadFeeRestEndpointsByFeeName(
    feeName: string, env: string, domain: string): Promise<Fee[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/fees/v1?env=${env}&feeName=${feeName}`;
    let fees: Fee[] | undefined = [];
    try {
        let readFeeListResponse: AxiosResponse<Fee[]> = await axios.get(url, config);
        let feeList = readFeeListResponse.data;
        for(let i = 0; i < feeList.length; i++) {
            fees[i] = new Fee(feeList[i]);
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
    return fees;
}

export async function callUpdateFeeRestEndpoints(
    fee: Fee, env: string, domain: string): Promise<Fee[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/fees/v1?env=${env}`;
    let data = JSON.stringify(fee as any, replacer);
    let updateFeeListResponse: AxiosResponse<Fee[]> = await axios.put(url, data, config);
    let feeList = updateFeeListResponse.data;
    let fees: Fee[] | undefined = [];
    for(let i = 0; i < feeList.length; i++) {
        fees[i] = new Fee(feeList[i]);
    }
    return fees;
}

export async function callDeleteFeeRestEndpointsByFeeId(
    feeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/fees/v1?env=${env}&feeId=${feeId}` ;
    let deleteFeeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteFeeResponse.status == 204;
}

export async function callDeleteFeeRestEndpointsByFeeName(
    feeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/fees/v1?env=${env}&feeName=${feeName}` ;
    let deleteFeeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteFeeResponse.status == 204;
}
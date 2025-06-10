import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PeriodType } from "../../../../../models/periods/periodTypes/v1/PeriodType";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";


export async function callCreatePeriodTypeRestEndpoints(
    periodType: PeriodType, env: string, domain: string): Promise<PeriodType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodTypes/v1?env=${env}`;
    let data = JSON.stringify(periodType as any, replacer);
    let createPeriodTypeResponse: AxiosResponse<PeriodType> = await axios.post(url, data, config);
    let periodType1 = new PeriodType(createPeriodTypeResponse.data);
    return periodType1;
}

export async function callReadPeriodTypeRestEndpointsByPeriodTypeId(
    periodTypeId: number, env: string, domain: string): Promise<PeriodType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodTypes/v1?env=${env}&periodTypeId=${periodTypeId}`;
    let periodTypes: PeriodType[] | undefined = [];
    try {
        let readPeriodTypeListResponse: AxiosResponse<PeriodType[]> = await axios.get(url, config);
        let periodTypeList = readPeriodTypeListResponse.data;
        for(let i = 0; i < periodTypeList.length; i++) {
            periodTypes[i] = new PeriodType(periodTypeList[i]);
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
    return periodTypes;
}

export async function callReadPeriodTypeRestEndpointsByPeriodTypeName(
    periodTypeName: string, env: string, domain: string): Promise<PeriodType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodTypes/v1?env=${env}&periodTypeName=${periodTypeName}`;
    let periodTypes: PeriodType[] | undefined = [];
    try {
        let readPeriodTypeListResponse: AxiosResponse<PeriodType[]> = await axios.get(url, config);
        let periodTypeList = readPeriodTypeListResponse.data;
        for(let i = 0; i < periodTypeList.length; i++) {
            periodTypes[i] = new PeriodType(periodTypeList[i]);
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
    return periodTypes;
}

export async function callUpdatePeriodTypeRestEndpoints(
    periodType: PeriodType, env: string, domain: string): Promise<PeriodType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodTypes/v1?env=${env}`;
    let data = JSON.stringify(periodType as any, replacer);
    let updatePeriodTypeListResponse: AxiosResponse<PeriodType[]> = await axios.put(url, data, config);
    let periodTypes: PeriodType[] | undefined = [];
    let periodTypeList = updatePeriodTypeListResponse.data;
    for(let i = 0; i < periodTypeList.length; i++) {
        periodTypes[i] = new PeriodType(periodTypeList[i]);
    }
    return periodTypes;
}

export async function callDeletePeriodTypeRestEndpointsByPeriodTypeId(
    periodTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodTypes/v1?env=${env}&periodTypeId=${periodTypeId}` ;
    let deletePeriodTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePeriodTypeResponse.status == 204;
}

export async function callDeletePeriodTypeRestEndpointsByPeriodTypeName(
    periodTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodTypes/v1?env=${env}&periodTypeName=${periodTypeName}` ;
    let deletePeriodTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePeriodTypeResponse.status == 204;
}
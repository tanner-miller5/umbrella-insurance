import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PeriodStatus } from "../../../../../models/periods/periodStatuses/v1/PeriodStatus";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePeriodStatusRestEndpoints(
    periodStatus: PeriodStatus, env: string, domain: string): Promise<PeriodStatus>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodStatuses/v1?env=${env}`;
    let data = JSON.stringify(periodStatus as any, replacer);
    let createPeriodStatusResponse: AxiosResponse<PeriodStatus> = await axios.post(url, data, config);
    let periodStatus1 = new PeriodStatus(createPeriodStatusResponse.data);
    return periodStatus1;
}

export async function callReadPeriodStatusRestEndpointsByPeriodStatusId(
    periodStatusId: number, env: string, domain: string): Promise<PeriodStatus[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodStatuses/v1?env=${env}&periodStatusId=${periodStatusId}`;
    let periodStatuss: PeriodStatus[] | undefined = [];
    try {
        let readPeriodStatusListResponse: AxiosResponse<PeriodStatus[]> = await axios.get(url, config);
        let periodStatusList = readPeriodStatusListResponse.data;
        for (let i = 0; i < periodStatusList.length; i++) {
            periodStatuss[i] = new PeriodStatus(periodStatusList[i]);
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
    return periodStatuss;
}

export async function callReadPeriodStatusRestEndpointsByPeriodStatusName(
    periodStatusName: string, env: string, domain: string): Promise<PeriodStatus[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodStatuses/v1?env=${env}&periodStatusName=${periodStatusName}`;
    let periodStatuss: PeriodStatus[] | undefined = [];
    try {
        let readPeriodStatusListResponse: AxiosResponse<PeriodStatus[]> = await axios.get(url, config);
        let periodStatusList = readPeriodStatusListResponse.data;
        for(let i = 0; i < periodStatusList.length; i++) {
            periodStatuss[i] = new PeriodStatus(periodStatusList[i]);
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
    return periodStatuss;
}

export async function callUpdatePeriodStatusRestEndpoints(
    periodStatus: PeriodStatus, env: string, domain: string): Promise<PeriodStatus[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodStatuses/v1?env=${env}`;
    let data = JSON.stringify(periodStatus as any, replacer);
    let updatePeriodStatusListResponse: AxiosResponse<PeriodStatus[]> = await axios.put(url, data, config);
    let periodStatusList = updatePeriodStatusListResponse.data;
    let periodStatuss: PeriodStatus[] | undefined = [];
    for(let i = 0; i < periodStatusList.length; i++) {
        periodStatuss[i] = new PeriodStatus(periodStatusList[i]);
    }
    return periodStatuss;
}

export async function callDeletePeriodStatusRestEndpointsByPeriodStatusId(
    periodStatusId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodStatuses/v1?env=${env}&periodStatusId=${periodStatusId}` ;
    let deletePeriodStatusResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePeriodStatusResponse.status == 204;
}

export async function callDeletePeriodStatusRestEndpointsByPeriodStatusName(
    periodStatusName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periodStatuses/v1?env=${env}&periodStatusName=${periodStatusName}` ;
    let deletePeriodStatusResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePeriodStatusResponse.status == 204;
}
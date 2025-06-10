import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Period } from "../../../../models/periods/v1/Period";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreatePeriodRestEndpoints(
    period: Period, env: string, domain: string): Promise<Period>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periods/v1?env=${env}`;
    let data = JSON.stringify(period as any, replacer);
    let createPeriodResponse: AxiosResponse<Period> = await axios.post(url, data, config);
    let period1 = new Period(createPeriodResponse.data);
    return period1;
}

export async function callReadPeriodRestEndpointsByPeriodId(
    periodId: number, env: string, domain: string): Promise<Period[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periods/v1?env=${env}&periodId=${periodId}`;
    let periods: Period[] | undefined = [];
    try {
        let readPeriodListResponse: AxiosResponse<Period[]> = await axios.get(url, config);
        let periodList = readPeriodListResponse.data;
        for(let i = 0; i < periodList.length; i++) {
            periods[i] = new Period(periodList[i]);
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
    return periods;
}

export async function callReadPeriodRestEndpointsByGameIdAndPeriodNumber(
    gameId: number, periodNumber: number, env: string, domain: string): Promise<Period[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periods/v1?env=${env}&gameId=${gameId}&periodNumber=${periodNumber}`;
    let periods: Period[] | undefined = [];
    try {
        let readPeriodListResponse: AxiosResponse<Period[]> = await axios.get(url, config);
        let periodList = readPeriodListResponse.data;
        for(let i = 0; i < periodList.length; i++) {
            periods[i] = new Period(periodList[i]);
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
    return periods;
}

export async function callUpdatePeriodRestEndpoints(
    period: Period, env: string, domain: string): Promise<Period[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periods/v1?env=${env}`;
    let data = JSON.stringify(period as any, replacer);
    let updatePeriodListResponse: AxiosResponse<Period[]> = await axios.put(url, data, config);
    let periodList = updatePeriodListResponse.data;
    let periods: Period[] | undefined = [];
    for(let i = 0; i < periodList.length; i++) {
        periods[i] = new Period(periodList[i]);
    }
    return periods;
}

export async function callDeletePeriodRestEndpointsByPeriodId(
    periodId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periods/v1?env=${env}&periodId=${periodId}` ;
    let deletePeriodResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePeriodResponse.status == 204;
}

export async function callDeletePeriodRestEndpointsByGameIdAndPeriodNumber(
    gameId: number, periodNumber: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/periods/v1?env=${env}&gameId=${gameId}&periodNumber=${periodNumber}`;
    let deletePeriodResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePeriodResponse.status == 204;
}
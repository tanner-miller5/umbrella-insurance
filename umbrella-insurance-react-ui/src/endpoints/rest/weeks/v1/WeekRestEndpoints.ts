import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Week } from "../../../../models/weeks/v1/Week";
import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateWeekRestEndpoints(
    week: Week, env: string, domain: string): Promise<Week>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/weeks/v1?env=${env}`;
    let data = JSON.stringify(week as any, replacer);
    let createWeekResponse: AxiosResponse<Week> = await axios.post(url, data, config);
    let week1 = new Week(createWeekResponse.data);
    return week1;
}

export async function callReadWeekRestEndpointsByWeekId(
    weekId: number, env: string, domain: string): Promise<Week[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/weeks/v1?env=${env}&weekId=${weekId}`;
    let weeks: Week[] | undefined = [];
    try {
        let readWeekListResponse: AxiosResponse<Week[]> = await axios.get(url, config);
        let weekList = readWeekListResponse.data;
        for(let i = 0; i < weekList.length; i++) {
            weeks[i] = new Week(weekList[i]);
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
    return weeks;
}

export async function callReadWeekRestEndpointsByWeekTitle(
    weekTitle: string, env: string, domain: string): Promise<Week[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/weeks/v1?env=${env}&weekTitle=${weekTitle}`;
    let weeks: Week[] | undefined = [];
    try {
        let readWeekListResponse: AxiosResponse<Week[]> = await axios.get(url, config);
        let weekList = readWeekListResponse.data;
        for(let i = 0; i < weekList.length; i++) {
            weeks[i] = new Week(weekList[i]);
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
    return weeks;
}

export async function callUpdateWeekRestEndpoints(
    week: Week, env: string, domain: string): Promise<Week[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/weeks/v1?env=${env}`;
    let data = JSON.stringify(week as any, replacer);
    let updateWeekListResponse: AxiosResponse<Week[]> = await axios.put(url, data, config);
    let weekList = updateWeekListResponse.data;
    let weeks: Week[] | undefined = [];
    for(let i = 0; i < weekList.length; i++) {
        weeks[i] = new Week(weekList[i]);
    }
    return weeks;
}

export async function callDeleteWeekRestEndpointsByWeekId(
    weekId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/weeks/v1?env=${env}&weekId=${weekId}` ;
    let deleteWeekResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteWeekResponse.status == 204;
}

export async function callDeleteWeekRestEndpointsByWeekTitle(
    weekTitle: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/weeks/v1?env=${env}&weekTitle=${weekTitle}` ;
    let deleteWeekResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteWeekResponse.status == 204;
}
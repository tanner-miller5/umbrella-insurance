import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Schedule } from "../../../../models/schedules/v1/Schedule";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateScheduleRestEndpoints(
    schedule: Schedule, env: string, domain: string): Promise<Schedule>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/schedules/v1?env=${env}`;
    let data = JSON.stringify(schedule as any, replacer);
    let createScheduleResponse: AxiosResponse<Schedule> = await axios.post(url, data, config);
    let schedule1 = new Schedule(createScheduleResponse.data);
    return schedule1;
}

export async function callReadScheduleRestEndpointsByScheduleId(
    scheduleId: number, env: string, domain: string): Promise<Schedule[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/schedules/v1?env=${env}&scheduleId=${scheduleId}`;
    let schedules: Schedule[] | undefined = [];
    try {
        let readScheduleListResponse: AxiosResponse<Schedule[]> = await axios.get(url, config);
        let scheduleList = readScheduleListResponse.data;
        for(let i = 0; i < scheduleList.length; i++) {
            schedules[i] = new Schedule(scheduleList[i]);
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
    return schedules;
}

export async function callReadScheduleRestEndpointsByScheduleName(
    scheduleName: string, env: string, domain: string): Promise<Schedule[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/schedules/v1?env=${env}&scheduleName=${scheduleName}`;
    let schedules: Schedule[] | undefined = [];
    try {
        let readScheduleListResponse: AxiosResponse<Schedule[]> = await axios.get(url, config);
        let scheduleList = readScheduleListResponse.data;
        for(let i = 0; i < scheduleList.length; i++) {
            schedules[i] = new Schedule(scheduleList[i]);
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
    return schedules;
}

export async function callUpdateScheduleRestEndpoints(
    schedule: Schedule, env: string, domain: string): Promise<Schedule[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/schedules/v1?env=${env}`;
    let data = JSON.stringify(schedule as any, replacer);
    let updateScheduleListResponse: AxiosResponse<Schedule[]> = await axios.put(url, data, config);
    let scheduleList = updateScheduleListResponse.data;
    let schedules: Schedule[] | undefined = [];
    for(let i = 0; i < scheduleList.length; i++) {
        schedules[i] = new Schedule(scheduleList[i]);
    }
    return schedules;
}

export async function callDeleteScheduleRestEndpointsByScheduleId(
    scheduleId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/schedules/v1?env=${env}&scheduleId=${scheduleId}` ;
    let deleteScheduleResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteScheduleResponse.status == 204;
}

export async function callDeleteScheduleRestEndpointsByScheduleName(
    scheduleName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/schedules/v1?env=${env}&scheduleName=${scheduleName}` ;
    let deleteScheduleResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteScheduleResponse.status == 204;
}
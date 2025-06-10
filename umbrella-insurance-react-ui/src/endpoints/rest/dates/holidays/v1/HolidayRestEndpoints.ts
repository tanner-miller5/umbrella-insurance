import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { Holiday } from "../../../../../models/dates/holidays/v1/Holiday";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";


export async function callCreateHolidayRestEndpoints(
    holiday: Holiday, env: string, domain: string): Promise<Holiday>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/holidays/v1?env=${env}`;
    let data = JSON.stringify(holiday as any, replacer);
    let createHolidayResponse: AxiosResponse<Holiday> = await axios.post(url, data, config);
    let holiday1 = new Holiday(createHolidayResponse.data);
    return holiday1;
}

export async function callReadHolidayRestEndpointsByHolidayId(
    holidayId: number, env: string, domain: string): Promise<Holiday[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/holidays/v1?env=${env}&holidayId=${holidayId}`;
    let holidays: Holiday[] | undefined = [];
    try {
        let readHolidayListResponse: AxiosResponse<Holiday[]> = await axios.get(url, config);
        let holidayList = readHolidayListResponse.data;
        for(let i = 0; i < holidayList.length; i++) {
            holidays[i] = new Holiday(readHolidayListResponse.data[i]);
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
    return holidays;
}

export async function callReadHolidayRestEndpointsByHolidayName(
    holidayName: string, env: string, domain: string): Promise<Holiday[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/holidays/v1?env=${env}&holidayName=${holidayName}`;
    let holidays: Holiday[] | undefined = [];
    try {
        let readHolidayListResponse: AxiosResponse<Holiday[]> = await axios.get(url, config);
        let holidayList = readHolidayListResponse.data;
        for(let i = 0; i < holidayList.length; i++) {
            holidays[i] = new Holiday(readHolidayListResponse.data[i]);
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
    return holidays;
}

export async function callUpdateHolidayRestEndpoints(
    holiday: Holiday, env: string, domain: string): Promise<Holiday[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/holidays/v1?env=${env}`;
    let data = JSON.stringify(holiday as any, replacer);
    let updateHolidayListResponse: AxiosResponse<Holiday[]> = await axios.put(url, data, config);
    let holidays: Holiday[] | undefined = [];
    let holidayList = updateHolidayListResponse.data;
    for(let i = 0; i < holidayList.length; i++) {
        holidays[i] = new Holiday(updateHolidayListResponse.data[i]);
    }
    return holidays;
}

export async function callDeleteHolidayRestEndpointsByHolidayId(
    holidayId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/holidays/v1?env=${env}&holidayId=${holidayId}` ;
    let deleteHolidayResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteHolidayResponse.status == 204;
}

export async function callDeleteHolidayRestEndpointsByHolidayName(
    holidayName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/holidays/v1?env=${env}&holidayName=${holidayName}` ;
    let deleteHolidayResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteHolidayResponse.status == 204;
}
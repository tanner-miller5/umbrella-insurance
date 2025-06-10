import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { EventType } from "../../../../../../models/users/events/eventTypes/v1/EventType";
import { replacer } from "../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateEventTypeRestEndpoints(
    eventType: EventType, env: string, domain: string): Promise<EventType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/eventTypes/v1?env=${env}`;
    let data = JSON.stringify(eventType as any, replacer);
    let createEventTypeResponse: AxiosResponse<EventType> = await axios.post(url, data, config);
    let eventType1 = createEventTypeResponse.data;
    return eventType1;
}

export async function callReadEventTypeRestEndpointsByEventTypeId(
    eventTypeId: number, env: string, domain: string): Promise<EventType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/eventTypes/v1?env=${env}&eventTypeId=${eventTypeId}`;
    let eventType: any | undefined = undefined;
    try {
        let readEventTypeListResponse: AxiosResponse<EventType[]> = await axios.get(url, config);
        eventType = readEventTypeListResponse.data;
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
    return eventType;
}

export async function callReadEventTypeRestEndpointsByEventTypeName(
    eventTypeName: string, env: string, domain: string): Promise<EventType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/eventTypes/v1?env=${env}&eventTypeName=${eventTypeName}`;
    let eventType: any | undefined = undefined;
    try {
        let readEventTypeListResponse: AxiosResponse<EventType[]> = await axios.get(url, config);
        eventType = readEventTypeListResponse.data;
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
    return eventType;
}

export async function callUpdateEventTypeRestEndpoints(
    eventType: EventType, env: string, domain: string): Promise<EventType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/eventTypes/v1?env=${env}`;
    let data = JSON.stringify(eventType as any, replacer);
    let updateEventTypeListResponse: AxiosResponse<EventType[]> = await axios.put(url, data, config);
    let eventTypeList = updateEventTypeListResponse.data;
    return eventTypeList;
}

export async function callDeleteEventTypeRestEndpointsByEventTypeId(
    eventTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/eventTypes/v1?env=${env}&eventTypeId=${eventTypeId}` ;
    let deleteEventTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEventTypeResponse.status == 204;
}

export async function callDeleteEventTypeRestEndpointsByEventTypeName(
    eventTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/eventTypes/v1?env=${env}&eventTypeName=${eventTypeName}` ;
    let deleteEventTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEventTypeResponse.status == 204;
}
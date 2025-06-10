import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Event } from "../../../../../models/users/events/v1/Event";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateEventRestEndpoints(
    event: Event, env: string, domain: string): Promise<Event>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/events/v1?env=${env}`;
    let data = JSON.stringify(event as any, replacer);
    let createEventResponse: AxiosResponse<Event> = await axios.post(url, data, config);
    let event1 = new Event(createEventResponse.data);
    return event1;
}

export async function callReadEventRestEndpointsByEventId(
    eventId: number, env: string, domain: string): Promise<Event[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/events/v1?env=${env}&eventId=${eventId}`;
    let events: Event[] | undefined = [];
    try {
        let readEventListResponse: AxiosResponse<Event[]> = await axios.get(url, config);
        let eventList = readEventListResponse.data;
        for(let i = 0; i < eventList.length; i++) {
            events[i] = new Event(eventList[i]);
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
    return events;
}

export async function callReadEventRestEndpointsByEventName(
    eventName: string, env: string, domain: string): Promise<Event[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/events/v1?env=${env}&eventName=${eventName}`;
    let events: Event[] | undefined = [];
    try {
        let readEventListResponse: AxiosResponse<Event[]> = await axios.get(url, config);
        let eventList = readEventListResponse.data;
        for(let i = 0; i < eventList.length; i++) {
            events[i] = new Event(eventList[i]);
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
    return events;
}

export async function callUpdateEventRestEndpoints(
    event: Event, env: string, domain: string): Promise<Event[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/events/v1?env=${env}`;
    let data = JSON.stringify(event as any, replacer);
    let updateEventListResponse: AxiosResponse<Event[]> = await axios.put(url, data, config);
    let events: Event[] | undefined = [];
    let eventList = updateEventListResponse.data;
    for(let i = 0; i < eventList.length; i++) {
        events[i] = new Event(eventList[i]);
    }
    return events;
}

export async function callDeleteEventRestEndpointsByEventId(
    eventId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/events/v1?env=${env}&eventId=${eventId}` ;
    let deleteEventResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEventResponse.status == 204;
}

export async function callDeleteEventRestEndpointsByEventName(
    eventName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/events/v1?env=${env}&eventName=${eventName}` ;
    let deleteEventResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEventResponse.status == 204;
}
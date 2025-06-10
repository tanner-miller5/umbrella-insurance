import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Conference } from "../../../../../models/leagues/conferences/v1/Conference";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateConferenceRestEndpoints(
    conference: Conference, env: string, domain: string): Promise<Conference>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/conferences/v1?env=${env}`;
    let data = JSON.stringify(conference as any, replacer);
    let createConferenceResponse: AxiosResponse<Conference> = await axios.post(url, data, config);
    let conference1 = new Conference(createConferenceResponse.data);
    return conference1;
}

export async function callReadConferenceRestEndpointsByConferenceId(
    conferenceId: number, env: string, domain: string): Promise<Conference[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/conferences/v1?env=${env}&conferenceId=${conferenceId}`;
    let conferences: Conference[] = [];
    try {
        let readConferenceListResponse: AxiosResponse<Conference[]> = await axios.get(url, config);
        let conferenceList = readConferenceListResponse.data;
        for(let i = 0; i < conferenceList.length; i++) {
            conferences[i] = new Conference(conferenceList[i]);
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
    return conferences;
}

export async function callReadConferenceRestEndpointsByConferenceName(
    conferenceName: string, env: string, domain: string): Promise<Conference[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/conferences/v1?env=${env}&conferenceName=${conferenceName}`;
    let conferences: Conference[] = [];
    try {
        let readConferenceListResponse: AxiosResponse<Conference[]> = await axios.get(url, config);
        let conferenceList = readConferenceListResponse.data;
        for(let i = 0; i < conferenceList.length; i++) {
            conferences[i] = new Conference(conferenceList[i]);
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
    return conferences;
}

export async function callUpdateConferenceRestEndpoints(
    conference: Conference, env: string, domain: string): Promise<Conference[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/conferences/v1?env=${env}`;
    let data = JSON.stringify(conference as any, replacer);
    let updateConferenceListResponse: AxiosResponse<Conference[]> = await axios.put(url, data, config);
    let conferenceList = updateConferenceListResponse.data;
    let conferences: Conference[] = [];
    for(let i = 0; i < conferenceList.length; i++) {
        conferences[i] = new Conference(conferenceList[i]);
    }
    return conferences;
}

export async function callDeleteConferenceRestEndpointsByConferenceId(
    conferenceId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/conferences/v1?env=${env}&conferenceId=${conferenceId}` ;
    let deleteConferenceResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteConferenceResponse.status == 204;
}

export async function callDeleteConferenceRestEndpointsByConferenceName(
    conferenceName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/conferences/v1?env=${env}&conferenceName=${conferenceName}` ;
    let deleteConferenceResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteConferenceResponse.status == 204;
}
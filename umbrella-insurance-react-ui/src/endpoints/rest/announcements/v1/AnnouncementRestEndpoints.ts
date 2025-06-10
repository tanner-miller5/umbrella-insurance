import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Announcement } from "../../../../models/announcements/v1/Announcement";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateAnnouncementRestEndpoints(
    announcement: Announcement, env: string, domain: string, session: string): Promise<Announcement>  {
    let config:AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/announcements/v1?env=${env}`;
    let data = JSON.stringify(announcement as any, replacer);
    let createAnnouncementResponse: AxiosResponse<Announcement> = await axios.post(url, data, config);
    let announcement1 = new Announcement(createAnnouncementResponse.data);
    return announcement1;
}

export async function callReadAllAnnouncementsRestEndpoint(
    env: string, domain: string): Promise<Announcement[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/announcements/v1?env=${env}`;
    let announcements: Announcement[] | undefined = [];
    try {
        let readFaqListResponse: AxiosResponse<Announcement[]> = await axios.get(url, config);
        for (let i = 0; i < readFaqListResponse.data.length; i++) {
            announcements[i] = new Announcement(readFaqListResponse.data[i]);
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
    return announcements;
}

export async function callReadAnnouncementRestEndpointsByAnnouncementId(
    announcementId: number, env: string, domain: string): Promise<Announcement[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/announcements/v1?env=${env}&announcementId=${announcementId}`;
    let announcements: Announcement[] | undefined = [];
    try {
        let readAnnouncementListResponse: AxiosResponse<Announcement[]> = await axios.get(url, config);
        for (let i = 0; i < readAnnouncementListResponse.data.length; i++) {
            announcements[i] = new Announcement(readAnnouncementListResponse.data[i]);
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
    return announcements;
}

export async function callReadAnnouncementRestEndpointsByAnnouncementName(
    announcementName: string, env: string, domain: string): Promise<Announcement[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/announcements/v1?env=${env}&announcementName=${announcementName}`;
    let announcements: Announcement[] | undefined = [];
    try {
        let readAnnouncementListResponse: AxiosResponse<Announcement[]> = await axios.get(url, config);
        for (let i = 0; i < readAnnouncementListResponse.data.length; i++) {
            announcements[i] = new Announcement(readAnnouncementListResponse.data[i]);
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
    return announcements;
}

export async function callUpdateAnnouncementRestEndpoints(
    announcement: Announcement, env: string, domain: string,
    sessionCode: string): Promise<Announcement[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            session: sessionCode,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/announcements/v1?env=${env}`;
    let announcements: Announcement[] = [];
    let data = JSON.stringify(announcement as any, replacer);
    let updateAnnouncementListResponse: AxiosResponse<Announcement[]> = await axios.put(url, data, config);
    for (let i = 0; i < updateAnnouncementListResponse.data.length; i++) {
        announcements[i] = new Announcement(updateAnnouncementListResponse.data[i]);
    }
    return announcements;
}

export async function callDeleteAnnouncementRestEndpointsByAnnouncementId(
    announcementId: number, env: string, domain: string, sessionCode: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": sessionCode,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/announcements/v1?env=${env}&announcementId=${announcementId}` ;
    let deleteAnnouncementResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAnnouncementResponse.status == 204;
}

export async function callDeleteAnnouncementRestEndpointsByAnnouncementName(
    announcementName: string, env: string, domain: string, sessionCode: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": sessionCode,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/announcements/v1?env=${env}&announcementName=${announcementName}` ;
    let deleteAnnouncementResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAnnouncementResponse.status == 204;
}
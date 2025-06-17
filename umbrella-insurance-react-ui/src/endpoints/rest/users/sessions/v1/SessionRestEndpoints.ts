import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Session } from "../../../../../models/users/sessions/v1/Session";
import { SessionCreateRestRequest } from "./SessionCreateRestRequest";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";


export const replacer = (key: any, value: any) =>
    typeof value === "number" ? value.toString() : value;

export async function callCreateSessionRestEndpoints(
    session: SessionCreateRestRequest, env: string, domain: string): Promise<Session>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sessions/v1?env=${env}`;
    let data = JSON.stringify(session as any, replacer);
    let createSessionResponse: AxiosResponse<Session> = await axios.post(url, data, config);
    let session1: Session = new Session(createSessionResponse.data);
    return session1;
}

export async function callReadSessionRestEndpointsBySessionId(
    sessionId: number, env: string, domain: string): Promise<Session[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sessions/v1?env=${env}&sessionId=${sessionId}`;
    let sessions: Session[] | undefined = [];
    try {
        let readSessionListResponse: AxiosResponse<Session[]> = await axios.get(url, config);
        for (let i = 0; i < readSessionListResponse.data.length; i++) {
            sessions[i] = new Session(readSessionListResponse.data[i]);
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
    return sessions;
}

export async function callReadSessionRestEndpointsBySessionCode(
    sessionCode: string, env: string, domain: string): Promise<Session[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": sessionCode,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sessions/v1?env=${env}&sessionCode=${sessionCode}`;
    let sessions: Session[] = [];
    try {
        let readSessionListResponse: AxiosResponse<Session[]> = await axios.get(url, config);
        for (let i = 0; i < readSessionListResponse.data.length; i++) {
            sessions[i] = new Session(readSessionListResponse.data[i]);
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
    return sessions;
}

export async function callUpdateSessionRestEndpoints(
    session: Session, env: string, domain: string): Promise<Session[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sessions/v1?env=${env}`;
    let updateSessionListResponse: AxiosResponse<Session[]> = await axios.put(url, session, config);
    let sessions: Session[] = [];
    for (let i = 0; i < updateSessionListResponse.data.length; i++) {
        sessions[i] = new Session(updateSessionListResponse.data[i]);
    }
    return sessions;
}

export async function callDeleteSessionRestEndpointsBySessionId(
    sessionId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sessions/v1?env=${env}&sessionId=${sessionId}` ;
    let deleteSessionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return true;
}

export async function callDeleteSessionRestEndpointsByUserId(
    userId: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sessions/v1?env=${env}&userId=${userId}` ;
    let deleteSessionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return true;
}
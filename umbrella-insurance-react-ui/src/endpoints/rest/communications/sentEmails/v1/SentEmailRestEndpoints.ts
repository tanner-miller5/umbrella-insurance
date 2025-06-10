import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { SentEmail } from "../../../../../models/communications/sentEmails/v1/SentEmail";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateSentEmailRestEndpoints(
    sentEmail: SentEmail, env: string, domain: string): Promise<SentEmail>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/sentEmails/v1?env=${env}`;
    let data = JSON.stringify(sentEmail as any, replacer);
    let createSentEmailResponse: AxiosResponse<SentEmail> = await axios.post(url, data, config);
    let sentEmail1: SentEmail = new SentEmail(createSentEmailResponse.data);
    return sentEmail1;
}

export async function callReadSentEmailRestEndpointsBySentEmailId(
    sentEmailId: number, env: string, domain: string): Promise<SentEmail[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/sentEmails/v1?env=${env}&sentEmailId=${sentEmailId}`;
    let sentEmailList: any | undefined = undefined;
    let sentEmails: SentEmail[] = [];
    try {
        let readSentEmailListResponse: AxiosResponse<SentEmail[]> = await axios.get(url, config);
        sentEmailList = readSentEmailListResponse.data;
        for(let i = 0; i < sentEmailList.length; i++) {
            sentEmails[i] = new SentEmail(sentEmailList[i]);
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
    return sentEmails;
}

export async function callReadSentEmailRestEndpointsBySentEmailName(
    sentEmailName: string, env: string, domain: string): Promise<SentEmail[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/sentEmails/v1?env=${env}&sentEmailName=${sentEmailName}`;
    let sentEmailList: any | undefined = undefined;
    let sentEmails: SentEmail[] = [];
    try {
        let readSentEmailListResponse: AxiosResponse<SentEmail[]> = await axios.get(url, config);
        sentEmailList = readSentEmailListResponse.data;
        for(let i = 0; i < sentEmailList.length; i++) {
            sentEmails[i] = new SentEmail(sentEmailList[i]);
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
    return sentEmails;
}

export async function callUpdateSentEmailRestEndpoints(
    sentEmail: SentEmail, env: string, domain: string): Promise<SentEmail[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/sentEmails/v1?env=${env}`;
    let data = JSON.stringify(sentEmail as any, replacer);
    let updateSentEmailListResponse: AxiosResponse<SentEmail[]> = await axios.put(url, data, config);
    let sentEmailList = updateSentEmailListResponse.data;
    let sentEmails: SentEmail[] = [];
    for(let i = 0; i < sentEmailList.length; i++) {
        sentEmails[i] = new SentEmail(sentEmailList[i]);
    }
    return sentEmails;
}

export async function callDeleteSentEmailRestEndpointsBySentEmailId(
    sentEmailId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sentEmails/v1?env=${env}&sentEmailId=${sentEmailId}` ;
    let deleteSentEmailResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSentEmailResponse.status == 204;
}

export async function callDeleteSentEmailRestEndpointsBySentEmailName(
    sentEmailName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sentEmails/v1?env=${env}&sentEmailName=${sentEmailName}` ;
    let deleteSentEmailResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSentEmailResponse.status == 204;
}
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { SentText } from "../../../../../models/communications/sentTexts/v1/SentText";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";



export async function callCreateSentTextRestEndpoints(
    sentText: SentText, env: string, domain: string): Promise<SentText>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sentTexts/v1?env=${env}`;
    let data = JSON.stringify(sentText as any, replacer);
    let createSentTextResponse: AxiosResponse<SentText> = await axios.post(url, data, config);
    let sentText1 = new SentText(createSentTextResponse.data);
    return sentText1;
}

export async function callReadSentTextRestEndpointsBySentTextId(
    sentTextId: number, env: string, domain: string): Promise<SentText[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sentTexts/v1?env=${env}&sentTextId=${sentTextId}`;
    let sentTexts: SentText[] = [];
    try {
        let readSentTextListResponse: AxiosResponse<SentText[]> = await axios.get(url, config);
        let sentTextList = readSentTextListResponse.data;
        for(let i = 0; i < sentTextList.length; i++) {
            sentTexts[i] = new SentText(sentTextList[i]);
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
    return sentTexts;
}

export async function callReadSentTextRestEndpointsBySentTextName(
    sentTextName: string, env: string, domain: string): Promise<SentText[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sentTexts/v1?env=${env}&sentTextName=${sentTextName}`;
    let sentTexts: SentText[] = [];
    try {
        let readSentTextListResponse: AxiosResponse<SentText[]> = await axios.get(url, config);
        let sentTextList = readSentTextListResponse.data;
        for(let i = 0; i < sentTextList.length; i++) {
            sentTexts[i] = new SentText(sentTextList[i]);
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
    return sentTexts;
}

export async function callUpdateSentTextRestEndpoints(
    sentText: SentText, env: string, domain: string): Promise<SentText[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sentTexts/v1?env=${env}`;
    let data = JSON.stringify(sentText as any, replacer);
    let updateSentTextListResponse: AxiosResponse<SentText[]> = await axios.put(url, data, config);
    let sentTextList = updateSentTextListResponse.data;
    let sentTexts: SentText[] = [];
    for(let i = 0; i < sentTextList.length; i++) {
        sentTexts[i] = new SentText(sentTextList[i]);
    }
    return sentTexts;
}

export async function callDeleteSentTextRestEndpointsBySentTextId(
    sentTextId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sentTexts/v1?env=${env}&sentTextId=${sentTextId}` ;
    let deleteSentTextResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSentTextResponse.status == 204;
}

export async function callDeleteSentTextRestEndpointsBySentTextName(
    sentTextName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/sentTexts/v1?env=${env}&sentTextName=${sentTextName}` ;
    let deleteSentTextResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSentTextResponse.status == 204;
}
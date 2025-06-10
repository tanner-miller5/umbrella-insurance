import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";
import { Faq } from "../../../../models/faqs/v1/Faq";

export async function callCreateFaqRestEndpoints(
    faq: Faq, env: string, domain: string, session: string): Promise<Faq>  {
    let config:AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/faqs/v1?env=${env}`;
    let data = JSON.stringify(faq as any, replacer);
    let createFaqResponse: AxiosResponse<Faq> = await axios.post(url, data, config);
    let faq1 = new Faq(createFaqResponse.data);
    return faq1;
}

export async function callReadFaqRestEndpointsByFaqId(
    faqId: number, env: string, domain: string): Promise<Faq[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/faqs/v1?env=${env}&faqId=${faqId}`;
    let faqs: Faq[] | undefined = [];
    try {
        let readFaqListResponse: AxiosResponse<Faq[]> = await axios.get(url, config);
        for (let i = 0; i < readFaqListResponse.data.length; i++) {
            faqs[i] = new Faq(readFaqListResponse.data[i]);
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
    return faqs;
}

export async function callReadAllFaqsRestEndpoint(
    env: string, domain: string): Promise<Faq[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/faqs/v1?env=${env}`;
    let faqs: Faq[] | undefined = [];
    try {
        let readFaqListResponse: AxiosResponse<Faq[]> = await axios.get(url, config);
        for (let i = 0; i < readFaqListResponse.data.length; i++) {
            faqs[i] = new Faq(readFaqListResponse.data[i]);
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
    return faqs;
}

export async function callReadFaqRestEndpointsByFaqName(
    faqName: string, env: string, domain: string): Promise<Faq[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/faqs/v1?env=${env}&faqName=${faqName}`;
    let faqs: Faq[] | undefined = [];
    try {
        let readFaqListResponse: AxiosResponse<Faq[]> = await axios.get(url, config);
        for (let i = 0; i < readFaqListResponse.data.length; i++) {
            faqs[i] = new Faq(readFaqListResponse.data[i]);
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
    return faqs;
}

export async function callUpdateFaqRestEndpoints(
    faq: Faq, env: string, domain: string, session: string): Promise<Faq[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/faqs/v1?env=${env}`;
    let faqs: Faq[] = [];
    let data = JSON.stringify(faq as any, replacer);
    let updateFaqListResponse: AxiosResponse<Faq[]> = await axios.put(url, data, config);
    for (let i = 0; i < updateFaqListResponse.data.length; i++) {
        faqs[i] = new Faq(updateFaqListResponse.data[i]);
    }
    return faqs;
}

export async function callDeleteFaqRestEndpointsByFaqId(
    faqId: number, env: string, domain: string, session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/faqs/v1?env=${env}&faqId=${faqId}` ;
    let deleteFaqResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteFaqResponse.status == 204;
}

export async function callDeleteFaqRestEndpointsByFaqName(
    faqName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/faqs/v1?env=${env}&faqName=${faqName}` ;
    let deleteFaqResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteFaqResponse.status == 204;
}
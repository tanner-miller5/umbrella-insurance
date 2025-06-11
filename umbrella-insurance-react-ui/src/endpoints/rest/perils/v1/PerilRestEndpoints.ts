import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Peril } from "../../../../models/perils/v1/Peril";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreatePerilRestEndpoints(
    peril: Peril, env: string, domain: string): Promise<Peril>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/perils/v1?env=${env}`;
    let data = JSON.stringify(peril as any, replacer);
    let createPerilResponse: AxiosResponse<Peril> = await axios.post(url, data, config);
    let peril1 = new Peril(createPerilResponse.data);
    return peril1;
}

export async function callReadPerilRestEndpointsByPerilId(
    perilId: number, env: string, domain: string): Promise<Peril[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/perils/v1?env=${env}&perilId=${perilId}`;
    let perils: Peril[] | undefined = [];
    try {
        let readPerilListResponse: AxiosResponse<Peril[]> = await axios.get(url, config);
        let perilList = readPerilListResponse.data;
        for(let i = 0; i < perilList.length; i++) {
            perils[i] = new Peril(perilList[i]);
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
    return perils;
}

export async function callReadPerilRestEndpoints(
    env: string, domain: string): Promise<Peril[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/perils/v1?env=${env}`;
    let perils: Peril[] | undefined = [];
    try {
        let readPerilListResponse: AxiosResponse<Peril[]> = await axios.get(url, config);
        let perilList = readPerilListResponse.data;
        for(let i = 0; i < perilList.length; i++) {
            perils[i] = new Peril(perilList[i]);
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
    return perils;
}

export async function callReadPerilRestEndpointsByPerilName(
    perilName: string, env: string, domain: string): Promise<Peril[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/perils/v1?env=${env}&perilName=${perilName}`;
    let perils: Peril[] | undefined = [];
    try {
        let readPerilListResponse: AxiosResponse<Peril[]> = await axios.get(url, config);
        let perilList = readPerilListResponse.data;
        for(let i = 0; i < perilList.length; i++) {
            perils[i] = new Peril(perilList[i]);
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
    return perils;
}

export async function callUpdatePerilRestEndpoints(
    peril: Peril, env: string, domain: string): Promise<Peril[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/perils/v1?env=${env}`;
    let data = JSON.stringify(peril as any, replacer);
    let updatePerilListResponse: AxiosResponse<Peril[]> = await axios.put(url, data, config);
    let perils: Peril[] | undefined = [];
    let perilList = updatePerilListResponse.data;
    for(let i = 0; i < perilList.length; i++) {
        perils[i] = new Peril(perilList[i]);
    }
    return perils;
}

export async function callDeletePerilRestEndpointsByPerilId(
    perilId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/perils/v1?env=${env}&perilId=${perilId}` ;
    let deletePerilResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePerilResponse.status == 204;
}

export async function callDeletePerilRestEndpointsByPerilName(
    perilName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/perils/v1?env=${env}&perilName=${perilName}` ;
    let deletePerilResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePerilResponse.status == 204;
}
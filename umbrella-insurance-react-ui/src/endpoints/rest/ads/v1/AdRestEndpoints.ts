import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Ad } from "../../../../models/ads/v1/Ad";

import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";
import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";

export async function callCreateAdRestEndpoints(
    ad: Ad, env: string, domain: string): Promise<Ad>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/ads/v1?env=${env}`;
    let data = JSON.stringify(ad as any, replacer);
    let createAdResponse: AxiosResponse<Ad> = await axios.post(url, data, config);
    let ad1 = new Ad(createAdResponse.data);
    return ad1;
}

export async function callReadAdRestEndpointsByAdName(
    adName: string, env: string, domain: string): Promise<Ad[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/ads/v1?env=${env}&adName=${adName}`;
    let ads: Ad[] | undefined = [];
    try {
        let readAdListResponse: AxiosResponse<Ad[]> = await axios.get(url, config);
        for(let i = 0; i < readAdListResponse.data.length; i++) {
            ads[i] = new Ad(readAdListResponse.data[i]);
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
    return ads;
}

export async function callReadAdRestEndpointsByAdId(
    adId: number, env: string, domain: string): Promise<Ad[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/ads/v1?env=${env}&adId=${adId}`;
    let ads: Ad[] = [];
    try {
        let readAdListResponse: AxiosResponse<Ad[]> = await axios.get(url, config);
        for(let i = 0; i < readAdListResponse.data.length; i++) {
            ads[i] = new Ad(readAdListResponse.data[i]);
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
    return ads;
}

export async function callUpdateAdRestEndpoints(
    ad: Ad, env: string, domain: string): Promise<Ad[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/ads/v1?env=${env}`;
    let data = JSON.stringify(ad as any, replacer);
    let updateAdListResponse: AxiosResponse<Ad[]> = await axios.put(url, data, config);
    let adList = updateAdListResponse.data;
    let ads: Ad[] = [];
    for(let i = 0; i < adList.length; i++) {
        ads[i] = new Ad(adList[i]);
    }
    return ads;
}

export async function callDeleteAdRestEndpointsByAdId(
    adId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/ads/v1?env=${env}&adId=${adId}` ;
    let deleteAdResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAdResponse.status == 204;
}

export async function callDeleteAdRestEndpointsByAdName(
    adName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/ads/v1?env=${env}&adName=${adName}` ;
    let deleteAdResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAdResponse.status == 204;
}
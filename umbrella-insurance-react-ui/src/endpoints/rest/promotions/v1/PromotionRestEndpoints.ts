import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Promotion } from "../../../../models/promotions/v1/Promotion";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreatePromotionRestEndpoints(
    promotion: Promotion, env: string, domain: string): Promise<Promotion>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/promotions/v1?env=${env}`;
    let data = JSON.stringify(promotion as any, replacer);
    let createPromotionResponse: AxiosResponse<Promotion> = await axios.post(url, data, config);
    let promotion1 = new Promotion(createPromotionResponse.data);
    return promotion1;
}

export async function callReadPromotionRestEndpointsByPromotionId(
    promotionId: number, env: string, domain: string): Promise<Promotion[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/promotions/v1?env=${env}&promotionId=${promotionId}`;
    let promotions: Promotion[] | undefined = [];
    try {
        let readPromotionListResponse: AxiosResponse<Promotion[]> = await axios.get(url, config);
        let promotionList = readPromotionListResponse.data;
        for(let i = 0; i < promotionList.length; i++) {
            promotions[i] = new Promotion(promotionList[i]);
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
    return promotions;
}

export async function callReadPromotionRestEndpointsByPromotionName(
    promotionName: string, env: string, domain: string): Promise<Promotion[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/promotions/v1?env=${env}&promotionName=${promotionName}`;
    let promotions: Promotion[] | undefined = [];
    try {
        let readPromotionListResponse: AxiosResponse<Promotion[]> = await axios.get(url, config);
        let promotionList = readPromotionListResponse.data;
        for(let i = 0; i < promotionList.length; i++) {
            promotions[i] = new Promotion(promotionList[i]);
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
    return promotions;
}

export async function callUpdatePromotionRestEndpoints(
    promotion: Promotion, env: string, domain: string): Promise<Promotion[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/promotions/v1?env=${env}`;
    let data = JSON.stringify(promotion as any, replacer);
    let updatePromotionListResponse: AxiosResponse<Promotion[]> = await axios.put(url, data, config);
    let promotionList = updatePromotionListResponse.data;
    let promotions: Promotion[] | undefined = [];
    for(let i = 0; i < promotionList.length; i++) {
        promotions[i] = new Promotion(promotionList[i]);
    }
    return promotions;
}

export async function callDeletePromotionRestEndpointsByPromotionId(
    promotionId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/promotions/v1?env=${env}&promotionId=${promotionId}` ;
    let deletePromotionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePromotionResponse.status == 204;
}

export async function callDeletePromotionRestEndpointsByPromotionName(
    promotionName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/promotions/v1?env=${env}&promotionName=${promotionName}` ;
    let deletePromotionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePromotionResponse.status == 204;
}
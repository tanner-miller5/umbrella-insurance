import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Bot } from "../../../../../models/users/bots/v1/Bot";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateBotRestEndpoints(
    bot: Bot, env: string, domain: string): Promise<Bot>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bots/v1?env=${env}`;
    let data = JSON.stringify(bot as any, replacer);
    let createBotResponse: AxiosResponse<Bot> = await axios.post(url, data, config);
    let bot1 = new Bot(createBotResponse.data);
    return bot1;
}

export async function callReadBotRestEndpointsByBotId(
    botId: number, env: string, domain: string): Promise<Bot[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bots/v1?env=${env}&botId=${botId}`;
    let bots: Bot[] | undefined = [];
    try {
        let readBotListResponse: AxiosResponse<Bot[]> = await axios.get(url, config);
        let botList = readBotListResponse.data;
        for(let i = 0; i < botList.length; i++) {
            bots[i] = new Bot(botList[i]);
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
    return bots;
}

export async function callReadBotRestEndpointsByBotName(
    botName: string, env: string, domain: string): Promise<Bot[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bots/v1?env=${env}&botName=${botName}`;
    let bots: Bot[] | undefined = [];
    try {
        let readBotListResponse: AxiosResponse<Bot[]> = await axios.get(url, config);
        let botList = readBotListResponse.data;
        for(let i = 0; i < botList.length; i++) {
            bots[i] = new Bot(botList[i]);
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
    return bots;
}

export async function callUpdateBotRestEndpoints(
    bot: Bot, env: string, domain: string): Promise<Bot[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bots/v1?env=${env}`;
    let data = JSON.stringify(bot as any, replacer);
    let updateBotListResponse: AxiosResponse<Bot[]> = await axios.put(url, data, config);
    let bots: Bot[] | undefined = [];
    let botList = updateBotListResponse.data;
    for(let i = 0; i < botList.length; i++) {
        bots[i] = new Bot(botList[i]);
    }
    return bots;
}

export async function callDeleteBotRestEndpointsByBotId(
    botId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bots/v1?env=${env}&botId=${botId}` ;
    let deleteBotResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteBotResponse.status == 204;
}

export async function callDeleteBotRestEndpointsByBotName(
    botName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bots/v1?env=${env}&botName=${botName}` ;
    let deleteBotResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteBotResponse.status == 204;
}
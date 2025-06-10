import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { GameType } from "../../../../models/gameTypes/v1/GameType";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateGameTypeRestEndpoints(
    gameType: GameType, env: string, domain: string): Promise<GameType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameTypes/v1?env=${env}`;
    let data = JSON.stringify(gameType as any, replacer);
    let createGameTypeResponse: AxiosResponse<GameType> = await axios.post(url, data, config);
    let gameType1 = new GameType(createGameTypeResponse.data);
    return gameType1;
}

export async function callReadGameTypeRestEndpointsByGameTypeId(
    gameTypeId: number, env: string, domain: string): Promise<GameType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameTypes/v1?env=${env}&gameTypeId=${gameTypeId}`;
    let gameTypes: GameType[] = [];
    try {
        let readGameTypeListResponse: AxiosResponse<GameType[]> = await axios.get(url, config);
        let gameTypeList = readGameTypeListResponse.data;
        for(let i=0; i < gameTypeList.length; i++) {
            gameTypes[i] = new GameType(gameTypeList[i]);
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
    return gameTypes;
}

export async function callReadGameTypeRestEndpointsByGameTypeName(
    gameTypeName: string, env: string, domain: string): Promise<GameType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameTypes/v1?env=${env}&gameTypeName=${gameTypeName}`;
    let gameTypes: GameType[] = [];
    try {
        let readGameTypeListResponse: AxiosResponse<GameType[]> = await axios.get(url, config);
        let gameTypeList = readGameTypeListResponse.data;
        for(let i=0; i < gameTypeList.length; i++) {
            gameTypes[i] = new GameType(gameTypeList[i]);
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
    return gameTypes;
}

export async function callUpdateGameTypeRestEndpoints(
    gameType: GameType, env: string, domain: string): Promise<GameType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameTypes/v1?env=${env}`;
    let data = JSON.stringify(gameType as any, replacer);
    let updateGameTypeListResponse: AxiosResponse<GameType[]> = await axios.put(url, data, config);
    let gameTypes: GameType[] = [];
    let gameTypeList = updateGameTypeListResponse.data;
    for(let i=0; i < gameTypeList.length; i++) {
        gameTypes[i] = new GameType(gameTypeList[i]);
    }
    return gameTypes;
}

export async function callDeleteGameTypeRestEndpointsByGameTypeId(
    gameTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameTypes/v1?env=${env}&gameTypeId=${gameTypeId}` ;
    let deleteGameTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGameTypeResponse.status == 204;
}

export async function callDeleteGameTypeRestEndpointsByGameTypeName(
    gameTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameTypes/v1?env=${env}&gameTypeName=${gameTypeName}` ;
    let deleteGameTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGameTypeResponse.status == 204;
}
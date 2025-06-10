import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { GameStatus } from "../../../../../models/games/gameStatuses/v1/GameStatus";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateGameStatusRestEndpoints(
    gameStatus: GameStatus, env: string, domain: string): Promise<GameStatus>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameStatuses/v1?env=${env}`;
    let data = JSON.stringify(gameStatus as any, replacer);
    let createGameStatusResponse: AxiosResponse<GameStatus> = await axios.post(url, data, config);
    let gameStatus1 = new GameStatus(createGameStatusResponse.data);
    return gameStatus1;
}

export async function callReadGameStatusRestEndpointsByGameStatusId(
    gameStatusId: number, env: string, domain: string): Promise<GameStatus[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameStatuses/v1?env=${env}&gameStatusId=${gameStatusId}`;
    let gameStatuss: GameStatus[] | undefined = [];
    try {
        let readGameStatusListResponse: AxiosResponse<GameStatus[]> = await axios.get(url, config);
        let gameStatussList = readGameStatusListResponse.data;
        for(let i = 0; i < gameStatussList.length; i++) {
            gameStatuss[i] = new GameStatus(gameStatussList[i]);
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
    return gameStatuss;
}

export async function callReadGameStatusRestEndpointsByGameStatusName(
    gameStatusName: string, env: string, domain: string): Promise<GameStatus[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameStatuses/v1?env=${env}&gameStatusName=${gameStatusName}`;
    let gameStatuss: GameStatus[] | undefined = [];
    try {
        let readGameStatusListResponse: AxiosResponse<GameStatus[]> = await axios.get(url, config);
        let gameStatussList = readGameStatusListResponse.data;
        for(let i = 0; i < gameStatussList.length; i++) {
            gameStatuss[i] = new GameStatus(gameStatussList[i]);
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
    return gameStatuss;
}

export async function callUpdateGameStatusRestEndpoints(
    gameStatus: GameStatus, env: string, domain: string): Promise<GameStatus[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameStatuses/v1?env=${env}`;
    let data = JSON.stringify(gameStatus as any, replacer);
    let updateGameStatusListResponse: AxiosResponse<GameStatus[]> = await axios.put(url, data, config);
    let gameStatussList = updateGameStatusListResponse.data;
    let gameStatuss: GameStatus[] | undefined = [];
    for(let i = 0; i < gameStatussList.length; i++) {
        gameStatuss[i] = new GameStatus(gameStatussList[i]);
    }
    return gameStatuss;
}

export async function callDeleteGameStatusRestEndpointsByGameStatusId(
    gameStatusId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameStatuses/v1?env=${env}&gameStatusId=${gameStatusId}` ;
    let deleteGameStatusResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGameStatusResponse.status == 204;
}

export async function callDeleteGameStatusRestEndpointsByGameStatusName(
    gameStatusName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/gameStatuses/v1?env=${env}&gameStatusName=${gameStatusName}` ;
    let deleteGameStatusResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGameStatusResponse.status == 204;
}
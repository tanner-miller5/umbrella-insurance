import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Game } from "../../../../models/games/v1/Game";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateGameRestEndpoints(
    game: Game, env: string, domain: string): Promise<Game>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/games/v1?env=${env}`;
    let data = JSON.stringify(game as any, replacer);
    let createGameResponse: AxiosResponse<Game> = await axios.post(url, data, config);
    let game1 = new Game(createGameResponse.data);
    return game1;
}

export async function callReadGameRestEndpointsByGameId(
    gameId: number, env: string, domain: string): Promise<Game[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/games/v1?env=${env}&gameId=${gameId}`;
    let games: Game[] = [];
    try {
        let readGameListResponse: AxiosResponse<Game[]> = await axios.get(url, config);
        let gameList = readGameListResponse.data;
        let games: Game[] = [];
        for (let i = 0; i < gameList.length; i++) {
            games[i] = new Game(gameList[i]);
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
    return games;
}

export async function callReadGameRestEndpointsByGameName(
    gameName: string, env: string, domain: string): Promise<Game[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/games/v1?env=${env}&gameName=${gameName}`;
    let games: Game[] = [];
    try {
        let readGameListResponse: AxiosResponse<Game[]> = await axios.get(url, config);
        let gameList = readGameListResponse.data;
        let games: Game[] = [];
        for (let i = 0; i < gameList.length; i++) {
            games[i] = new Game(gameList[i]);
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
    return games;
}

export async function callUpdateGameRestEndpoints(
    game: Game, env: string, domain: string): Promise<Game[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/games/v1?env=${env}`;
    let data = JSON.stringify(game as any, replacer);
    let updateGameListResponse: AxiosResponse<Game[]> = await axios.put(url, data, config);
    let gameList = updateGameListResponse.data;
    let games: Game[] = [];
    for (let i = 0; i < gameList.length; i++) {
        games[i] = new Game(gameList[i]);
    }
    return games;
}

export async function callDeleteGameRestEndpointsByGameId(
    gameId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/games/v1?env=${env}&gameId=${gameId}` ;
    let deleteGameResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGameResponse.status == 204;
}

export async function callDeleteGameRestEndpointsByGameName(
    gameName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/games/v1?env=${env}&gameName=${gameName}` ;
    let deleteGameResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGameResponse.status == 204;
}
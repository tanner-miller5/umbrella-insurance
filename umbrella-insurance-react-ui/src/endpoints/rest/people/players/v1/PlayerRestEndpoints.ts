import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Player } from "../../../../../models/people/players/v1/Player";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePlayerRestEndpoints(
    player: Player, env: string, domain: string): Promise<Player>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/players/v1?env=${env}`;
    let data = JSON.stringify(player as any, replacer);
    let createPlayerResponse: AxiosResponse<Player> = await axios.post(url, data, config);
    let player1 = new Player(createPlayerResponse.data);
    return player1;
}

export async function callReadPlayerRestEndpointsByPlayerId(
    playerId: number, env: string, domain: string): Promise<Player[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/players/v1?env=${env}&playerId=${playerId}`;
    let players: Player[] | undefined = [];
    try {
        let readPlayerListResponse: AxiosResponse<Player[]> = await axios.get(url, config);
        let playerList = readPlayerListResponse.data;
        for(let i = 0; i < playerList.length; i++) {
            players[i] = new Player(playerList[i]);
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
    return players;
}

export async function callReadPlayerRestEndpointsByPlayerName(
    playerName: string, env: string, domain: string): Promise<Player[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/players/v1?env=${env}&playerName=${playerName}`;
    let players: Player[] | undefined = [];
    try {
        let readPlayerListResponse: AxiosResponse<Player[]> = await axios.get(url, config);
        let playerList = readPlayerListResponse.data;
        for(let i = 0; i < playerList.length; i++) {
            players[i] = new Player(playerList[i]);
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
    return players;
}

export async function callUpdatePlayerRestEndpoints(
    player: Player, env: string, domain: string): Promise<Player[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/players/v1?env=${env}`;
    let data = JSON.stringify(player as any, replacer);
    let updatePlayerListResponse: AxiosResponse<Player[]> = await axios.put(url, data, config);
    let playerList = updatePlayerListResponse.data;
    let players: Player[] | undefined = [];
    for(let i = 0; i < playerList.length; i++) {
        players[i] = new Player(playerList[i]);
    }
    return players;
}

export async function callDeletePlayerRestEndpointsByPlayerId(
    playerId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/players/v1?env=${env}&playerId=${playerId}` ;
    let deletePlayerResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePlayerResponse.status == 204;
}

export async function callDeletePlayerRestEndpointsByPlayerName(
    playerName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/players/v1?env=${env}&playerName=${playerName}` ;
    let deletePlayerResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePlayerResponse.status == 204;
}
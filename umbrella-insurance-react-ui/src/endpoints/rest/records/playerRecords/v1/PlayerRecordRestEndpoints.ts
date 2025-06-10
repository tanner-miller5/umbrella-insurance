import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PlayerRecord } from "../../../../../models/records/playerRecords/v1/PlayerRecord";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePlayerRecordRestEndpoints(
    playerRecord: PlayerRecord, env: string, domain: string): Promise<PlayerRecord>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/playerRecords/v1?env=${env}`;
    let data = JSON.stringify(playerRecord as any, replacer);
    let createPlayerRecordResponse: AxiosResponse<PlayerRecord> = await axios.post(url, data, config);
    let playerRecord1 = new PlayerRecord(createPlayerRecordResponse.data);
    return playerRecord1;
}

export async function callReadPlayerRecordRestEndpointsByPlayerRecordId(
    playerRecordId: number, env: string, domain: string): Promise<PlayerRecord[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/playerRecords/v1?env=${env}&playerRecordId=${playerRecordId}`;
    let playerRecords: PlayerRecord[] | undefined = [];
    try {
        let readPlayerRecordListResponse: AxiosResponse<PlayerRecord[]> = await axios.get(url, config);
        let playerRecordList = readPlayerRecordListResponse.data;
        for(let i = 0; i < playerRecordList.length; i++) {
            playerRecords[i] = new PlayerRecord(playerRecordList[i]);
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
    return playerRecords;
}

export async function callReadPlayerRecordRestEndpointsByPlayerIdAndAndSeasonId(
    playerId: number, seasonId: number, env: string, domain: string): Promise<PlayerRecord[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/playerRecords/v1?env=${env}&playerId=${playerId}&seasonId=${seasonId}`;
    let playerRecords: PlayerRecord[] | undefined = [];
    try {
        let readPlayerRecordListResponse: AxiosResponse<PlayerRecord[]> = await axios.get(url, config);
        let playerRecordList = readPlayerRecordListResponse.data;
        for(let i = 0; i < playerRecordList.length; i++) {
            playerRecords[i] = new PlayerRecord(playerRecordList[i]);
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
    return playerRecords;
}

export async function callUpdatePlayerRecordRestEndpoints(
    playerRecord: PlayerRecord, env: string, domain: string): Promise<PlayerRecord[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/playerRecords/v1?env=${env}`;
    let data = JSON.stringify(playerRecord as any, replacer);
    let updatePlayerRecordListResponse: AxiosResponse<PlayerRecord[]> = await axios.put(url, data, config);
    let playerRecordList = updatePlayerRecordListResponse.data;
    let playerRecords: PlayerRecord[] | undefined = [];
    for(let i = 0; i < playerRecordList.length; i++) {
        playerRecords[i] = new PlayerRecord(playerRecordList[i]);
    }
    return playerRecords;
}

export async function callDeletePlayerRecordRestEndpointsByPlayerRecordId(
    playerRecordId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/players/v1?env=${env}&playerRecordId=${playerRecordId}` ;
    let deletePlayerRecordResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePlayerRecordResponse.status == 204;
}

export async function callDeletePlayerRecordRestEndpointsByPlayerIdAndAndSeasonId(
    playerId: number, seasonId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/playerRecords/v1?env=${env}&playerId=${playerId}&seasonId=${seasonId}` ;
    let deletePlayerRecordResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePlayerRecordResponse.status == 204;
}
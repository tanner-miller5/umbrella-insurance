import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { TournamentType } from "../../../../../models/tournaments/tournamentTypes/v1/TournamentType";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateTournamentTypeRestEndpoints(
    tournamentType: TournamentType, env: string, domain: string): Promise<TournamentType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournamentTypes/v1?env=${env}`;
    let data = JSON.stringify(tournamentType as any, replacer);
    let createTournamentTypeResponse: AxiosResponse<TournamentType> = await axios.post(url, data, config);
    let tournamentType1 = new TournamentType(createTournamentTypeResponse.data);
    return tournamentType1;
}

export async function callReadTournamentTypeRestEndpointsByTournamentTypeId(
    tournamentTypeId: number, env: string, domain: string): Promise<TournamentType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournamentTypes/v1?env=${env}&tournamentTypeId=${tournamentTypeId}`;
    let tournamentTypes: TournamentType[] | undefined = [];
    try {
        let readTournamentTypeListResponse: AxiosResponse<TournamentType[]> = await axios.get(url, config);
        let tournamentTypeList = readTournamentTypeListResponse.data;
        for(let i = 0; i < tournamentTypeList.length; i++) {
            tournamentTypes[i] = new TournamentType(tournamentTypeList[i]);
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
    return tournamentTypes;
}

export async function callReadTournamentTypeRestEndpointsByTournamentTypeName(
    tournamentTypeName: string, env: string, domain: string): Promise<TournamentType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournamentTypes/v1?env=${env}&tournamentTypeName=${tournamentTypeName}`;
    let tournamentTypes: TournamentType[] | undefined = [];
    try {
        let readTournamentTypeListResponse: AxiosResponse<TournamentType[]> = await axios.get(url, config);
        let tournamentTypeList = readTournamentTypeListResponse.data;
        for(let i = 0; i < tournamentTypeList.length; i++) {
            tournamentTypes[i] = new TournamentType(tournamentTypeList[i]);
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
    return tournamentTypes;
}

export async function callUpdateTournamentTypeRestEndpoints(
    tournamentType: TournamentType, env: string, domain: string): Promise<TournamentType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournamentTypes/v1?env=${env}`;
    let data = JSON.stringify(tournamentType as any, replacer);
    let updateTournamentTypeListResponse: AxiosResponse<TournamentType[]> = await axios.put(url, data, config);
    let tournamentTypeList = updateTournamentTypeListResponse.data;
    let tournamentTypes: TournamentType[] | undefined = [];
    for(let i = 0; i < tournamentTypeList.length; i++) {
        tournamentTypes[i] = new TournamentType(tournamentTypeList[i]);
    }
    return tournamentTypes;
}

export async function callDeleteTournamentTypeRestEndpointsByTournamentTypeId(
    tournamentTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournamentTypes/v1?env=${env}&tournamentTypeId=${tournamentTypeId}` ;
    let deleteTournamentTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTournamentTypeResponse.status == 204;
}

export async function callDeleteTournamentTypeRestEndpointsByTournamentTypeName(
    tournamentTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournamentTypes/v1?env=${env}&tournamentTypeName=${tournamentTypeName}` ;
    let deleteTournamentTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTournamentTypeResponse.status == 204;
}
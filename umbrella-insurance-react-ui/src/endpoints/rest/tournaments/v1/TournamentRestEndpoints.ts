import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Tournament } from "../../../../models/tournaments/v1/Tournament";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateTournamentRestEndpoints(
    tournament: Tournament, env: string, domain: string): Promise<Tournament>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournaments/v1?env=${env}`;
    let data = JSON.stringify(tournament as any, replacer);
    let createTournamentResponse: AxiosResponse<Tournament> = await axios.post(url, data, config);
    let tournament1 = new Tournament(createTournamentResponse.data);
    return tournament1;
}

export async function callReadTournamentRestEndpointsByTournamentId(
    tournamentId: number, env: string, domain: string): Promise<Tournament[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournaments/v1?env=${env}&tournamentId=${tournamentId}`;
    let tournaments: Tournament[] | undefined = [];
    try {
        let readTournamentListResponse: AxiosResponse<Tournament[]> = await axios.get(url, config);
        let tournamentList = readTournamentListResponse.data;
        for(let i = 0; i < tournamentList.length; i++) {
            tournaments[i] = new Tournament(tournamentList[i]);
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
    return tournaments;
}

export async function callReadTournamentRestEndpointsByTournamentName(
    tournamentName: string, env: string, domain: string): Promise<Tournament[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournaments/v1?env=${env}&tournamentName=${tournamentName}`;
    let tournaments: Tournament[] | undefined = [];
    try {
        let readTournamentListResponse: AxiosResponse<Tournament[]> = await axios.get(url, config);
        let tournamentList = readTournamentListResponse.data;
        for(let i = 0; i < tournamentList.length; i++) {
            tournaments[i] = new Tournament(tournamentList[i]);
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
    return tournaments;
}

export async function callUpdateTournamentRestEndpoints(
    tournament: Tournament, env: string, domain: string): Promise<Tournament[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournaments/v1?env=${env}`;
    let data = JSON.stringify(tournament as any, replacer);
    let updateTournamentListResponse: AxiosResponse<Tournament[]> = await axios.put(url, data, config);
    let tournamentList = updateTournamentListResponse.data;
    let tournaments: Tournament[] | undefined = [];
    for(let i = 0; i < tournamentList.length; i++) {
        tournaments[i] = new Tournament(tournamentList[i]);
    }
    return tournaments;
}

export async function callDeleteTournamentRestEndpointsByTournamentId(
    tournamentId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournaments/v1?env=${env}&tournamentId=${tournamentId}`;
    let deleteTournamentResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTournamentResponse.status == 204;
}

export async function callDeleteTournamentRestEndpointsByTournamentName(
    tournamentName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/tournaments/v1?env=${env}&tournamentName=${tournamentName}`;
    let deleteTournamentResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTournamentResponse.status == 204;
}
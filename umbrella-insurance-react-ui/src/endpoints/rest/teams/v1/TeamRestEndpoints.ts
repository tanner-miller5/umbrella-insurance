import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Team } from "../../../../models/teams/v1/Team";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateTeamRestEndpoints(
    team: Team, env: string, domain: string): Promise<Team>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teams/v1?env=${env}`;
    let data = JSON.stringify(team as any, replacer);
    let createTeamResponse: AxiosResponse<Team> = await axios.post(url, data, config);
    let team1 = new Team(createTeamResponse.data);
    return team1;
}

export async function callReadTeamRestEndpointsByTeamId(
    teamId: number, env: string, domain: string): Promise<Team[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teams/v1?env=${env}&teamId=${teamId}`;
    let teams: Team[] | undefined = [];
    try {
        let readTeamListResponse: AxiosResponse<Team[]> = await axios.get(url, config);
        let teamList = readTeamListResponse.data;
        for(let i = 0; i < teamList.length; i++) {
            teams[i] = new Team(teamList[i]);
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
    return teams;
}

export async function callReadTeamRestEndpointsByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
    teamName: string, levelOfCompetitionId: number, gameTypeId: number, env: string, domain: string): Promise<Team[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teams/v1?env=${env}&teamName=${teamName}&levelOfCompetitionId=${levelOfCompetitionId}&gameTypeId=${gameTypeId}`;
    let teams: Team[] | undefined = [];
    try {
        let readTeamListResponse: AxiosResponse<Team[]> = await axios.get(url, config);
        let teamList = readTeamListResponse.data;
        for(let i = 0; i < teamList.length; i++) {
            teams[i] = new Team(teamList[i]);
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
    return teams;
}

export async function callUpdateTeamRestEndpoints(
    team: Team, env: string, domain: string): Promise<Team[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teams/v1?env=${env}`;
    let data = JSON.stringify(team as any, replacer);
    let updateTeamListResponse: AxiosResponse<Team[]> = await axios.put(url, data, config);
    let teamList = updateTeamListResponse.data;
    let teams: Team[] | undefined = [];
    for(let i = 0; i < teamList.length; i++) {
        teams[i] = new Team(teamList[i]);
    }
    return teams;
}

export async function callDeleteTeamRestEndpointsByTeamId(
    teamId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teams/v1?env=${env}&teamId=${teamId}` ;
    let deleteTeamResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamResponse.status == 204;
}

export async function callDeleteTeamRestEndpointsByTeamNameAndLevelOfCompetitionIdAndGameTypeId(
    teamName: string, levelOfCompetitionId: number, gameTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teams/v1?env=${env}&teamName=${teamName}&levelOfCompetitionId=${levelOfCompetitionId}&gameTypeId=${gameTypeId}`;
    let deleteTeamResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamResponse.status == 204;
}
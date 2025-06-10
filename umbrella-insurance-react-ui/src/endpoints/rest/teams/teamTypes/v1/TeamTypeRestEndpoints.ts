import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { TeamType } from "../../../../../models/teams/teamTypes/v1/TeamType";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateTeamTypeRestEndpoints(
    teamType: TeamType, env: string, domain: string): Promise<TeamType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTypes/v1?env=${env}`;
    let data = JSON.stringify(teamType as any, replacer);
    let createTeamTypeResponse: AxiosResponse<TeamType> = await axios.post(url, data, config);
    let teamType1 = new TeamType(createTeamTypeResponse.data);
    return teamType1;
}

export async function callReadTeamTypeRestEndpointsByTeamTypeId(
    teamTypeId: number, env: string, domain: string): Promise<TeamType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTypes/v1?env=${env}&teamTypeId=${teamTypeId}`;
    let teamTypes: TeamType[] | undefined = [];
    try {
        let readTeamTypeListResponse: AxiosResponse<TeamType[]> = await axios.get(url, config);
        let teamTypeList = readTeamTypeListResponse.data;
        for(let i = 0; i < teamTypeList.length; i++) {
            teamTypes[i] = new TeamType(teamTypeList[i]);
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
    return teamTypes;
}

export async function callReadTeamTypeRestEndpointsByTeamTypeName(
    teamTypeName: string, env: string, domain: string): Promise<TeamType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTypes/v1?env=${env}&teamTypeName=${teamTypeName}`;
    let teamTypes: TeamType[] | undefined = [];
    try {
        let readTeamTypeListResponse: AxiosResponse<TeamType[]> = await axios.get(url, config);
        let teamTypeList = readTeamTypeListResponse.data;
        for(let i = 0; i < teamTypeList.length; i++) {
            teamTypes[i] = new TeamType(teamTypeList[i]);
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
    return teamTypes;
}

export async function callUpdateTeamTypeRestEndpoints(
    teamType: TeamType, env: string, domain: string): Promise<TeamType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTypes/v1?env=${env}`;
    let data = JSON.stringify(teamType as any, replacer);
    let updateTeamTypeListResponse: AxiosResponse<TeamType[]> = await axios.put(url, data, config);
    let teamTypes: TeamType[] | undefined = [];
    let teamTypeList = updateTeamTypeListResponse.data;
    for(let i = 0; i < teamTypeList.length; i++) {
        teamTypes[i] = new TeamType(teamTypeList[i]);
    }
    return teamTypes;
}

export async function callDeleteTeamTypeRestEndpointsByTeamTypeId(
    teamTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTypes/v1?env=${env}&teamTypeId=${teamTypeId}` ;
    let deleteTeamTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamTypeResponse.status == 204;
}

export async function callDeleteTeamTypeRestEndpointsByTeamTypeName(
    teamTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTypes/v1?env=${env}&teamTypeName=${teamTypeName}` ;
    let deleteTeamTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamTypeResponse.status == 204;
}
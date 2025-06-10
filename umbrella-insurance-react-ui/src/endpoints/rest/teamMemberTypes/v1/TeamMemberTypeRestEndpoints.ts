import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { TeamMemberType } from "../../../../models/teamMemberTypes/v1/TeamMemberType";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateTeamMemberTypeRestEndpoints(
    teamMemberType: TeamMemberType, env: string, domain: string): Promise<TeamMemberType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamMemberTypes/v1?env=${env}`;
    let data = JSON.stringify(teamMemberType as any, replacer);
    let createTeamMemberTypeResponse: AxiosResponse<TeamMemberType> = await axios.post(url, data, config);
    let teamMemberType1 = new TeamMemberType(createTeamMemberTypeResponse.data);
    return teamMemberType1;
}

export async function callReadTeamMemberTypeRestEndpointsByTeamMemberTypeId(
    teamMemberTypeId: number, env: string, domain: string): Promise<TeamMemberType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamMemberTypes/v1?env=${env}&teamMemberTypeId=${teamMemberTypeId}`;
    let teamMemberTypes: TeamMemberType[] | undefined = [];
    try {
        let readTeamMemberTypeListResponse: AxiosResponse<TeamMemberType[]> = await axios.get(url, config);
        let teamMemberTypeList = readTeamMemberTypeListResponse.data;
        for(let i = 0; i < teamMemberTypeList.length; i++) {
            teamMemberTypes[i] = new TeamMemberType(teamMemberTypeList[i]);
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
    return teamMemberTypes;
}

export async function callReadTeamMemberTypeRestEndpointsByTeamMemberTypeName(
    teamMemberTypeName: string, env: string, domain: string): Promise<TeamMemberType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamMemberTypes/v1?env=${env}&teamMemberTypeName=${teamMemberTypeName}`;
    let teamMemberTypes: TeamMemberType[] | undefined = [];
    try {
        let readTeamMemberTypeListResponse: AxiosResponse<TeamMemberType[]> = await axios.get(url, config);
        let teamMemberTypeList = readTeamMemberTypeListResponse.data;
        for(let i = 0; i < teamMemberTypeList.length; i++) {
            teamMemberTypes[i] = new TeamMemberType(teamMemberTypeList[i]);
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
    return teamMemberTypes;
}

export async function callUpdateTeamMemberTypeRestEndpoints(
    teamMemberType: TeamMemberType, env: string, domain: string): Promise<TeamMemberType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamMemberTypes/v1?env=${env}`;
    let data = JSON.stringify(teamMemberType as any, replacer);
    let updateTeamMemberTypeListResponse: AxiosResponse<TeamMemberType[]> = await axios.put(url, data, config);
    let teamMemberTypeList = updateTeamMemberTypeListResponse.data;
    let teamMemberTypes: TeamMemberType[] | undefined = [];
    for(let i = 0; i < teamMemberTypeList.length; i++) {
        teamMemberTypes[i] = new TeamMemberType(teamMemberTypeList[i]);
    }
    return teamMemberTypes;
}

export async function callDeleteTeamMemberTypeRestEndpointsByTeamMemberTypeId(
    teamMemberTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamMemberTypes/v1?env=${env}&teamMemberTypeId=${teamMemberTypeId}` ;
    let deleteTeamMemberTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamMemberTypeResponse.status == 204;
}

export async function callDeleteTeamMemberTypeRestEndpointsByTeamMemberTypeName(
    teamMemberTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamMemberTypes/v1?env=${env}&teamMemberTypeName=${teamMemberTypeName}` ;
    let deleteTeamMemberTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamMemberTypeResponse.status == 204;
}
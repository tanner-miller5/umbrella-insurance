import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { TeamTransactionType } from "../../../../../models/teamTransactions/teamTransactionTypes/v1/TeamTransactionType";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateTeamTransactionTypeRestEndpoints(
    teamTransactionType: TeamTransactionType, env: string, domain: string): Promise<TeamTransactionType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactionTypes/v1?env=${env}`;
    let data = JSON.stringify(teamTransactionType as any, replacer);
    let createTeamTransactionTypeResponse: AxiosResponse<TeamTransactionType> = await axios.post(url, data, config);
    let teamTransactionType1 = new TeamTransactionType(createTeamTransactionTypeResponse.data);
    return teamTransactionType1;
}

export async function callReadTeamTransactionTypeRestEndpointsByTeamTransactionTypeId(
    teamTransactionTypeId: number, env: string, domain: string): Promise<TeamTransactionType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactionTypes/v1?env=${env}&teamTransactionTypeId=${teamTransactionTypeId}`;
    let teamTransactionTypes: TeamTransactionType[] | undefined = [];
    try {
        let readTeamTransactionTypeListResponse: AxiosResponse<TeamTransactionType[]> = await axios.get(url, config);
        let teamTransactionTypeList = readTeamTransactionTypeListResponse.data;
        for(let i = 0; i < teamTransactionTypeList.length; i++) {
            teamTransactionTypes[i] = new TeamTransactionType(teamTransactionTypeList[i]);
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
    return teamTransactionTypes;
}

export async function callReadTeamTransactionTypeRestEndpointsByTeamTransactionTypeName(
    teamTransactionTypeName: string, env: string, domain: string): Promise<TeamTransactionType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactionTypes/v1?env=${env}&teamTransactionTypeName=${teamTransactionTypeName}`;
    let teamTransactionTypes: TeamTransactionType[] | undefined = [];
    try {
        let readTeamTransactionTypeListResponse: AxiosResponse<TeamTransactionType[]> = await axios.get(url, config);
        let teamTransactionTypeList = readTeamTransactionTypeListResponse.data;
        for(let i = 0; i < teamTransactionTypeList.length; i++) {
            teamTransactionTypes[i] = new TeamTransactionType(teamTransactionTypeList[i]);
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
    return teamTransactionTypes;
}

export async function callUpdateTeamTransactionTypeRestEndpoints(
    teamTransactionType: TeamTransactionType, env: string, domain: string): Promise<TeamTransactionType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactionTypes/v1?env=${env}`;
    let data = JSON.stringify(teamTransactionType as any, replacer);
    let updateTeamTransactionTypeListResponse: AxiosResponse<TeamTransactionType[]> = await axios.put(url, data, config);
    let teamTransactionTypeList = updateTeamTransactionTypeListResponse.data;
    let teamTransactionTypes: TeamTransactionType[] | undefined = [];
    for(let i = 0; i < teamTransactionTypeList.length; i++) {
        teamTransactionTypes[i] = new TeamTransactionType(teamTransactionTypeList[i]);
    }
    return teamTransactionTypes;
}

export async function callDeleteTeamTransactionTypeRestEndpointsByTeamTransactionTypeId(
    teamTransactionTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactionTypes/v1?env=${env}&teamTransactionTypeId=${teamTransactionTypeId}` ;
    let deleteTeamTransactionTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamTransactionTypeResponse.status == 204;
}

export async function callDeleteTeamTransactionTypeRestEndpointsByTeamTransactionTypeName(
    teamTransactionTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactionTypes/v1?env=${env}&teamTransactionTypeName=${teamTransactionTypeName}` ;
    let deleteTeamTransactionTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamTransactionTypeResponse.status == 204;
}
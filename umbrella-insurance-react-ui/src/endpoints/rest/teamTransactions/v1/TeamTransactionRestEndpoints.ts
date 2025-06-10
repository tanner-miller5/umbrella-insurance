import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Team } from "../../../../models/teams/v1/Team";

import { TeamTransaction } from "../../../../models/teamTransactions/v1/TeamTransaction";
import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateTeamTransactionRestEndpoints(
    teamTransaction: TeamTransaction, env: string, domain: string): Promise<TeamTransaction>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactions/v1?env=${env}`;
    let data = JSON.stringify(teamTransaction as any, replacer);
    let createTeamTransactionResponse: AxiosResponse<TeamTransaction> = await axios.post(url, data, config);
    let teamTransaction1 = new TeamTransaction(createTeamTransactionResponse.data);
    return teamTransaction1;
}

export async function callReadTeamTransactionRestEndpointsByTeamTransactionId(
    teamTransactionId: number, env: string, domain: string): Promise<TeamTransaction[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactions/v1?env=${env}&teamTransactionId=${teamTransactionId}`;
    let teamTransactions: TeamTransaction[] | undefined = [];
    try {
        let readTeamTransactionListResponse: AxiosResponse<TeamTransaction[]> = await axios.get(url, config);
        let teamTransactionList = readTeamTransactionListResponse.data;
        for(let i = 0; i < teamTransactionList.length; i++) {
            teamTransactions[i] = new TeamTransaction(teamTransactionList[i]);
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
    return teamTransactions;
}

export async function callReadTeamTransactionRestEndpointsByTeamTransactionName(
    teamTransactionName: string, env: string, domain: string): Promise<TeamTransaction[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactions/v1?env=${env}&teamTransactionName=${teamTransactionName}`;
    let teamTransactions: TeamTransaction[] | undefined = [];
    try {
        let readTeamTransactionListResponse: AxiosResponse<Team[]> = await axios.get(url, config);
        let teamTransactionList = readTeamTransactionListResponse.data;
        for(let i = 0; i < teamTransactionList.length; i++) {
            teamTransactions[i] = new TeamTransaction(teamTransactionList[i]);
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
    return teamTransactions;
}

export async function callUpdateTeamTransactionRestEndpoints(
    teamTransaction: TeamTransaction, env: string, domain: string): Promise<TeamTransaction[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactions/v1?env=${env}`;
    let data = JSON.stringify(teamTransaction as any, replacer);
    let updateTeamTransactionListResponse: AxiosResponse<TeamTransaction[]> = await axios.put(url, data, config);
    let teamTransactionList = updateTeamTransactionListResponse.data;
    let teamTransactions: TeamTransaction[] | undefined = [];
    for(let i = 0; i < teamTransactionList.length; i++) {
        teamTransactions[i] = new TeamTransaction(teamTransactionList[i]);
    }
    return teamTransactions;
}

export async function callDeleteTeamTransactionRestEndpointsByTeamTransactionId(
    teamTransactionId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactions/v1?env=${env}&teamTransactionId=${teamTransactionId}` ;
    let deleteTeamTransactionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamTransactionResponse.status == 204;
}

export async function callDeleteTeamTransactionRestEndpointsByTeamTransactionName(
    teamTransactionName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamTransactions/v1?env=${env}&teamTransactionName=${teamTransactionName}` ;
    let deleteTeamTransactionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamTransactionResponse.status == 204;
}
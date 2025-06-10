import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { TeamRecord } from "../../../../../models/records/teamRecords/v1/TeamRecord";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";


export async function callCreateTeamRecordRestEndpoints(
    teamRecord: TeamRecord, env: string, domain: string): Promise<TeamRecord>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamRecords/v1?env=${env}`;
    let data = JSON.stringify(teamRecord as any, replacer);
    let createTeamRecordResponse: AxiosResponse<TeamRecord> = await axios.post(url, data, config);
    let teamRecord1 = new TeamRecord(createTeamRecordResponse.data);
    return teamRecord1;
}

export async function callReadTeamRecordRestEndpointsByTeamRecordId(
    teamRecordId: number, env: string, domain: string): Promise<TeamRecord[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamRecords/v1?env=${env}&teamRecordId=${teamRecordId}`;
    let teamRecords: TeamRecord[] | undefined = [];
    try {
        let readTeamRecordListResponse: AxiosResponse<TeamRecord[]> = await axios.get(url, config);
        let teamRecordList = readTeamRecordListResponse.data;
        for (let i = 0; i < teamRecordList.length; i++) {
            teamRecords[i] = new TeamRecord(teamRecordList[i]);
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
    return teamRecords;
}

export async function callReadTeamRecordRestEndpointsByTeamIdAndSeasonId(
    teamId: number, seasonId: number, env: string, domain: string): Promise<TeamRecord[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamRecords/v1?env=${env}&teamId=${teamId}&seasonId=${seasonId}`;
    let teamRecords: TeamRecord[] | undefined = [];
    try {
        let readTeamRecordListResponse: AxiosResponse<TeamRecord[]> = await axios.get(url, config);
        let teamRecordList = readTeamRecordListResponse.data;
        for (let i = 0; i < teamRecordList.length; i++) {
            teamRecords[i] = new TeamRecord(teamRecordList[i]);
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
    return teamRecords;
}

export async function callUpdateTeamRecordRestEndpoints(
    teamRecord: TeamRecord, env: string, domain: string): Promise<TeamRecord[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamRecords/v1?env=${env}`;
    let data = JSON.stringify(teamRecord as any, replacer);
    let updateTeamRecordListResponse: AxiosResponse<TeamRecord[]> = await axios.put(url, data, config);
    let teamRecordList = updateTeamRecordListResponse.data;
    let teamRecords: TeamRecord[] | undefined = [];
    for (let i = 0; i < teamRecordList.length; i++) {
        teamRecords[i] = new TeamRecord(teamRecordList[i]);
    }
    return teamRecords;
}

export async function callDeleteTeamRecordRestEndpointsByTeamRecordId(
    teamRecordId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamRecords/v1?env=${env}&teamRecordId=${teamRecordId}`;
    let deleteTeamRecordResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamRecordResponse.status == 204;
}

export async function callDeleteTeamRecordRestEndpointsByTeamIdAndSeasonId(
    teamId: number, seasonId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/teamRecords/v1?env=${env}&teamId=${teamId}&seasonId=${seasonId}`;
    let deleteTeamRecordResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTeamRecordResponse.status == 204;
}
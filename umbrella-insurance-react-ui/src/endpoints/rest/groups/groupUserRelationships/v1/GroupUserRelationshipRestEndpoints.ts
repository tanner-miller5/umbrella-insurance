import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { GroupUserRelationship } from "../../../../../models/groups/groupUserRelationships/v1/GroupUserRelationship";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateGroupUserRelationshipRestEndpoints(
    groupUserRelationship: GroupUserRelationship, env: string, domain: string): Promise<GroupUserRelationship>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groupUserRelationships/v1?env=${env}`;
    let data = JSON.stringify(groupUserRelationship as any, replacer);
    let createGroupUserRelationshipResponse: AxiosResponse<GroupUserRelationship> = await axios.post(url, data, config);
    let groupUserRelationship1 = new GroupUserRelationship(createGroupUserRelationshipResponse.data);
    return groupUserRelationship1;
}

export async function callReadGroupUserRelationshipRestEndpointsByGroupUserRelationshipId(
    groupUserRelationshipId: number, env: string, domain: string): Promise<GroupUserRelationship[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groupUserRelationships/v1?env=${env}&groupUserRelationshipId=${groupUserRelationshipId}`;
    let groupUserRelationships: GroupUserRelationship[] | undefined = [];
    try {
        let readGroupUserRelationshipListResponse: AxiosResponse<GroupUserRelationship[]> = await axios.get(url, config);
        let groupUserRelationshipsList = readGroupUserRelationshipListResponse.data;
        for(let i = 0; i < groupUserRelationshipsList.length; i++) {
            groupUserRelationships[i] = new GroupUserRelationship(groupUserRelationshipsList[i]);
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
    return groupUserRelationships;
}

export async function callReadGroupUserRelationshipRestEndpointsByGroupUserRelationshipName(
    groupUserRelationshipName: string, env: string, domain: string): Promise<GroupUserRelationship[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groupUserRelationships/v1?env=${env}&groupUserRelationshipName=${groupUserRelationshipName}`;
    let groupUserRelationships: GroupUserRelationship[] | undefined = [];
    try {
        let readGroupUserRelationshipListResponse: AxiosResponse<GroupUserRelationship[]> = await axios.get(url, config);
        let groupUserRelationshipsList = readGroupUserRelationshipListResponse.data;
        for(let i = 0; i < groupUserRelationshipsList.length; i++) {
            groupUserRelationships[i] = new GroupUserRelationship(groupUserRelationshipsList[i]);
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
    return groupUserRelationships;
}

export async function callUpdateGroupUserRelationshipRestEndpoints(
    groupUserRelationship: GroupUserRelationship, env: string, domain: string): Promise<GroupUserRelationship[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groupUserRelationships/v1?env=${env}`;
    let data = JSON.stringify(groupUserRelationship as any, replacer);
    let updateGroupUserRelationshipListResponse: AxiosResponse<GroupUserRelationship[]> = await axios.put(url, data, config);
    let groupUserRelationshipsList = updateGroupUserRelationshipListResponse.data;
    let groupUserRelationships: GroupUserRelationship[] | undefined = [];
    for(let i = 0; i < groupUserRelationshipsList.length; i++) {
        groupUserRelationships[i] = new GroupUserRelationship(groupUserRelationshipsList[i]);
    }
    return groupUserRelationships;
}

export async function callDeleteGroupUserRelationshipRestEndpointsByGroupUserRelationshipId(
    groupUserRelationshipId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groupUserRelationships/v1?env=${env}&groupUserRelationshipId=${groupUserRelationshipId}` ;
    let deleteGroupUserRelationshipResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGroupUserRelationshipResponse.status == 204;
}

export async function callDeleteGroupUserRelationshipRestEndpointsByGroupUserRelationshipName(
    groupUserRelationshipName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groupUserRelationships/v1?env=${env}&groupUserRelationshipName=${groupUserRelationshipName}` ;
    let deleteGroupUserRelationshipResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGroupUserRelationshipResponse.status == 204;
}
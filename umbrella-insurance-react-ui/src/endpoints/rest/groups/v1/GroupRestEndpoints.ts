import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Group } from "../../../../models/groups/v1/Group";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateGroupRestEndpoints(
    group: Group, env: string, domain: string): Promise<Group>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groups/v1?env=${env}`;
    let data = JSON.stringify(group as any, replacer);
    let createGroupResponse: AxiosResponse<Group> = await axios.post(url, data, config);
    let group1 = new Group(createGroupResponse.data);
    return group1;
}

export async function callReadGroupRestEndpointsByGroupId(
    groupId: number, env: string, domain: string): Promise<Group[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groups/v1?env=${env}&groupId=${groupId}`;
    let groups: Group[] | undefined = [];
    try {
        let readGroupListResponse: AxiosResponse<Group[]> = await axios.get(url, config);
        let groupsList = readGroupListResponse.data;
        for(let i = 0; i < groupsList.length; i++) {
            groups[i] = new Group(groupsList[i]);
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
    return groups;
}

export async function callReadGroupRestEndpointsByGroupName(
    groupName: string, env: string, domain: string): Promise<Group[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groups/v1?env=${env}&groupName=${groupName}`;
    let groups: Group[] | undefined = [];
    try {
        let readGroupListResponse: AxiosResponse<Group[]> = await axios.get(url, config);
        let groupsList = readGroupListResponse.data;
        for(let i = 0; i < groupsList.length; i++) {
            groups[i] = new Group(groupsList[i]);
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
    return groups;
}

export async function callUpdateGroupRestEndpoints(
    group: Group, env: string, domain: string): Promise<Group[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groups/v1?env=${env}`;
    let data = JSON.stringify(group as any, replacer);
    let updateGroupListResponse: AxiosResponse<Group[]> = await axios.put(url, data, config);
    let groups: Group[] | undefined = [];
    let groupsList = updateGroupListResponse.data;
    for(let i = 0; i < groupsList.length; i++) {
        groups[i] = new Group(groupsList[i]);
    }
    return groups;
}

export async function callDeleteGroupRestEndpointsByGroupId(
    groupId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groups/v1?env=${env}&groupId=${groupId}` ;
    let deleteGroupResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGroupResponse.status == 204;
}

export async function callDeleteGroupRestEndpointsByGroupName(
    groupName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/groups/v1?env=${env}&groupName=${groupName}` ;
    let deleteGroupResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteGroupResponse.status == 204;
}
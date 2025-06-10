import axios, { AxiosRequestConfig, AxiosResponse } from "axios";


import { UserApplicationRoleRelationship } from "../../../../models/userApplicationRoleRelationships/v1/UserApplicationRoleRelationship";
import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateUserApplicationRoleRelationshipRestEndpoints(
    userApplicationRoleRelationship: UserApplicationRoleRelationship, env: string, domain: string): Promise<UserApplicationRoleRelationship>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userApplicationRoleRelationships/v1?env=${env}`;
    let data = JSON.stringify(userApplicationRoleRelationship as any, replacer);
    let createUserApplicationRoleRelationshipResponse: AxiosResponse<UserApplicationRoleRelationship> = await axios.post(url, data, config);
    let userApplicationRoleRelationship1 = new UserApplicationRoleRelationship(createUserApplicationRoleRelationshipResponse.data);
    return userApplicationRoleRelationship1;
}

export async function callReadUserApplicationRoleRelationshipRestEndpointsByUserApplicationRoleRelationshipId(
    userApplicationRoleRelationshipId: number, env: string, domain: string): Promise<UserApplicationRoleRelationship[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userApplicationRoleRelationships/v1?env=${env}&userApplicationRoleRelationshipId=${userApplicationRoleRelationshipId}`;
    let userApplicationRoleRelationships: UserApplicationRoleRelationship[] | undefined = [];
    try {
        let readUserApplicationRoleRelationshipListResponse: AxiosResponse<UserApplicationRoleRelationship[]> = await axios.get(url, config);
        let userApplicationRoleRelationshipList = readUserApplicationRoleRelationshipListResponse.data;
        for(let i = 0; i < userApplicationRoleRelationshipList.length; i++) {
            userApplicationRoleRelationships[i] = new UserApplicationRoleRelationship(userApplicationRoleRelationshipList[i]);
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
    return userApplicationRoleRelationships;
}

export async function callReadUserApplicationRoleRelationshipRestEndpointsByUserApplicationRoleRelationshipName(
    userApplicationRoleRelationshipName: string, env: string, domain: string): Promise<UserApplicationRoleRelationship[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userApplicationRoleRelationships/v1?env=${env}&userApplicationRoleRelationshipName=${userApplicationRoleRelationshipName}`;
    let userApplicationRoleRelationships: UserApplicationRoleRelationship[] | undefined = [];
    try {
        let readUserApplicationRoleRelationshipListResponse: AxiosResponse<UserApplicationRoleRelationship[]> = await axios.get(url, config);
        let userApplicationRoleRelationshipList = readUserApplicationRoleRelationshipListResponse.data;
        for(let i = 0; i < userApplicationRoleRelationshipList.length; i++) {
            userApplicationRoleRelationships[i] = new UserApplicationRoleRelationship(userApplicationRoleRelationshipList[i]);
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
    return userApplicationRoleRelationships;
}

export async function callUpdateUserApplicationRoleRelationshipRestEndpoints(
    userApplicationRoleRelationship: UserApplicationRoleRelationship, env: string, domain: string): Promise<UserApplicationRoleRelationship[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userApplicationRoleRelationships/v1?env=${env}`;
    let data = JSON.stringify(userApplicationRoleRelationship as any, replacer);
    let updateUserApplicationRoleRelationshipListResponse: AxiosResponse<UserApplicationRoleRelationship[]> = await axios.put(url, data, config);
    let userApplicationRoleRelationshipList = updateUserApplicationRoleRelationshipListResponse.data;
    let userApplicationRoleRelationships: UserApplicationRoleRelationship[] | undefined = [];
    for(let i = 0; i < userApplicationRoleRelationshipList.length; i++) {
        userApplicationRoleRelationships[i] = new UserApplicationRoleRelationship(userApplicationRoleRelationshipList[i]);
    }
    return userApplicationRoleRelationships;
}

export async function callDeleteUserApplicationRoleRelationshipRestEndpointsByUserApplicationRoleRelationshipId(
    userApplicationRoleRelationshipId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userApplicationRoleRelationships/v1?env=${env}&userApplicationRoleRelationshipId=${userApplicationRoleRelationshipId}` ;
    let deleteUserApplicationRoleRelationshipResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteUserApplicationRoleRelationshipResponse.status == 204;
}

export async function callDeleteUserApplicationRoleRelationshipRestEndpointsByUserApplicationRoleRelationshipName(
    userApplicationRoleRelationshipName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/userApplicationRoleRelationships/v1?env=${env}&userApplicationRoleRelationshipName=${userApplicationRoleRelationshipName}` ;
    let deleteUserApplicationRoleRelationshipResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteUserApplicationRoleRelationshipResponse.status == 204;
}
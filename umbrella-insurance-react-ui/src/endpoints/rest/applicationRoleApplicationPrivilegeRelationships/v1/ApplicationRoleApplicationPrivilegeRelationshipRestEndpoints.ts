import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { ApplicationRoleApplicationPrivilegeRelationship } from "../../../../models/applicationRoleApplicationPrivilegeRelationships/v1/ApplicationRoleApplicationPrivilegeRelationship";
import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateApplicationRoleApplicationPrivilegeRelationshipRestEndpoints(
    applicationRoleApplicationPrivilegeRelationship: ApplicationRoleApplicationPrivilegeRelationship, 
    env: string, domain: string): Promise<ApplicationRoleApplicationPrivilegeRelationship>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/applicationRoleApplicationPrivilegeRelationships/v1?env=${env}`;
    let data = JSON.stringify(applicationRoleApplicationPrivilegeRelationship as any, replacer);
    let createApplicationRoleApplicationPrivilegeRelationshipResponse: AxiosResponse<ApplicationRoleApplicationPrivilegeRelationship> = await axios.post(url, data, config);
    let applicationRoleApplicationPrivilegeRelationship1 = new ApplicationRoleApplicationPrivilegeRelationship(createApplicationRoleApplicationPrivilegeRelationshipResponse.data);
    return applicationRoleApplicationPrivilegeRelationship1;
}

export async function callReadApplicationRoleApplicationPrivilegeRelationshipRestEndpointsByApplicationRoleApplicationPrivilegeRelationshipId(
    applicationRoleApplicationPrivilegeRelationshipId: number, 
    env: string, domain: string): Promise<ApplicationRoleApplicationPrivilegeRelationship[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoleApplicationPrivilegeRelationships/v1?env=${env}&applicationRoleApplicationPrivilegeRelationshipId=${applicationRoleApplicationPrivilegeRelationshipId}`;
    let applicationRoleApplicationPrivilegeRelationships: ApplicationRoleApplicationPrivilegeRelationship[] | undefined = [];
    try {
        let readApplicationRoleApplicationPrivilegeRelationshipListResponse: AxiosResponse<ApplicationRoleApplicationPrivilegeRelationship[]> = await axios.get(url, config);
        for(let i = 0; i < readApplicationRoleApplicationPrivilegeRelationshipListResponse.data.length; i++) {
            applicationRoleApplicationPrivilegeRelationships[i] = readApplicationRoleApplicationPrivilegeRelationshipListResponse.data[i];
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
    return applicationRoleApplicationPrivilegeRelationships;
}

export async function callReadApplicationRoleApplicationPrivilegeRelationshipRestEndpointsByApplicationRoleIdAndApplicationPrivilegeId(
    applicationRoleId: number, applicationPrivilegeId: number,
     env: string, domain: string): Promise<ApplicationRoleApplicationPrivilegeRelationship[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoleApplicationPrivilegeRelationships/v1?env=${env}&applicationRoleId=${applicationRoleId}&applicationPrivilegeId=${applicationPrivilegeId}`;
    let applicationRoleApplicationPrivilegeRelationships: ApplicationRoleApplicationPrivilegeRelationship[] | undefined = [];
    try {
        let readApplicationRoleApplicationPrivilegeRelationshipListResponse: AxiosResponse<ApplicationRoleApplicationPrivilegeRelationship[]> = await axios.get(url, config);
        for (let i = 0; i < readApplicationRoleApplicationPrivilegeRelationshipListResponse.data.length; i++) {
            applicationRoleApplicationPrivilegeRelationships[i] = readApplicationRoleApplicationPrivilegeRelationshipListResponse.data[i];
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
    return applicationRoleApplicationPrivilegeRelationships;
}

export async function callUpdateApplicationRoleApplicationPrivilegeRelationshipRestEndpoints(
    applicationRoleApplicationPrivilegeRelationship: ApplicationRoleApplicationPrivilegeRelationship,
    env: string, domain: string): Promise<ApplicationRoleApplicationPrivilegeRelationship[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoleApplicationPrivilegeRelationships/v1?env=${env}`;
    let data = JSON.stringify(applicationRoleApplicationPrivilegeRelationship as any, replacer);
    let updateApplicationRoleApplicationPrivilegeRelationshipListResponse: AxiosResponse<ApplicationRoleApplicationPrivilegeRelationship[]> = await axios.put(url, data, config);
    let applicationRoleApplicationPrivilegeRelationships: ApplicationRoleApplicationPrivilegeRelationship[] | undefined = [];
    for (let i = 0; i < updateApplicationRoleApplicationPrivilegeRelationshipListResponse.data.length; i++) {
        applicationRoleApplicationPrivilegeRelationships[i] = updateApplicationRoleApplicationPrivilegeRelationshipListResponse.data[i];
    }
    return applicationRoleApplicationPrivilegeRelationships;
}

export async function callDeleteApplicationRoleApplicationPrivilegeRelationshipRestEndpointsByApplicationRoleApplicationPrivilegeRelationshipId(
    applicationRoleApplicationPrivilegeRelationshipId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoleApplicationPrivilegeRelationships/v1?env=${env}&applicationRoleApplicationPrivilegeRelationshipId=${applicationRoleApplicationPrivilegeRelationshipId}` ;
    let deleteApplicationRoleApplicationPrivilegeRelationshipResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteApplicationRoleApplicationPrivilegeRelationshipResponse.status == 204;
}

export async function callDeleteApplicationRoleApplicationPrivilegeRelationshipRestEndpointsByApplicationRoleIdAndApplicationPrivilegeId(
    applicationRoleId: number, applicationPrivilegeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoleApplicationPrivilegeRelationships/v1?env=${env}&applicationRoleId=${applicationRoleId}&applicationPrivilegeId=${applicationPrivilegeId}` ;
    let deleteApplicationRoleApplicationPrivilegeRelationshipResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteApplicationRoleApplicationPrivilegeRelationshipResponse.status == 204;
}
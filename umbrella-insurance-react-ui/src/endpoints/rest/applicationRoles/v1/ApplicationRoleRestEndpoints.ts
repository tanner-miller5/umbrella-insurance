import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { ApplicationRole } from "../../../../models/applicationRoles/v1/ApplicationRole";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";


export async function callCreateApplicationRoleRestEndpoints(
    applicationRole: ApplicationRole, env: string, domain: string): Promise<ApplicationRole>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/applicationRoles/v1?env=${env}`;
    let createApplicationRoleResponse: AxiosResponse<ApplicationRole> = await axios.post(url, applicationRole, config);
    let applicationRole1 = new ApplicationRole(createApplicationRoleResponse.data);
    return applicationRole1;
}

export async function callReadApplicationRoleRestEndpointsByApplicationRoleId(
    applicationRoleId: number, env: string, domain: string): Promise<ApplicationRole[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoles/v1?env=${env}&applicationRoleId=${applicationRoleId}`;
    let applicationRoles: ApplicationRole[] = [];
    try {
        let readApplicationRoleListResponse: AxiosResponse<ApplicationRole[]> = await axios.get(url, config);
        for(let i = 0; i < readApplicationRoleListResponse.data.length; i++) {
            applicationRoles[i] = new ApplicationRole(readApplicationRoleListResponse.data[i]);
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
    return applicationRoles;
}

export async function callReadApplicationRoleRestEndpointsByApplicationRoleName(
    applicationRoleName: string, env: string, domain: string): Promise<ApplicationRole[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoles/v1?env=${env}&applicationRoleName=${applicationRoleName}`;
    let applicationRoles: ApplicationRole[] = [];
    try {
        let readApplicationRoleListResponse: AxiosResponse<ApplicationRole[]> = await axios.get(url, config);
        for(let i = 0; i < readApplicationRoleListResponse.data.length; i++) {
            applicationRoles[i] = new ApplicationRole(readApplicationRoleListResponse.data[i]);
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
    return applicationRoles;
}

export async function callUpdateApplicationRoleRestEndpoints(
    applicationRole: ApplicationRole, env: string, domain: string): Promise<ApplicationRole[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoles/v1?env=${env}`;
    let updateApplicationRoleListResponse: AxiosResponse<ApplicationRole[]> = await axios.put(url, applicationRole, config);
    let applicationRoles: ApplicationRole[] = [];
    for(let i = 0; i < updateApplicationRoleListResponse.data.length; i++) {
        applicationRoles[i] = new ApplicationRole(updateApplicationRoleListResponse.data[i]);
    }
    return applicationRoles;
}

export async function callDeleteApplicationRoleRestEndpointsByApplicationRoleId(
    applicationRoleId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoles/v1?env=${env}&applicationRoleId=${applicationRoleId}` ;
    let deleteApplicationRoleResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteApplicationRoleResponse.status == 204;
}

export async function callDeleteApplicationRoleRestEndpointsByApplicationRoleName(
    applicationRoleName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationRoles/v1?env=${env}&applicationRoleName=${applicationRoleName}` ;
    let deleteApplicationRoleResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteApplicationRoleResponse.status == 204;
}
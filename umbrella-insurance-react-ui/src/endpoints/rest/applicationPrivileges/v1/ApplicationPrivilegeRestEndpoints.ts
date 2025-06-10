import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { ApplicationPrivilege } from "../../../../models/applicationPrivileges/v1/ApplicationPrivilege";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";


export async function callCreateApplicationPrivilegeRestEndpoints(
    applicationPrivilege: ApplicationPrivilege, env: string, domain: string): Promise<ApplicationPrivilege>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/applicationPrivileges/v1?env=${env}`;
    let createApplicationPrivilegeResponse: AxiosResponse<ApplicationPrivilege> = await axios.post(url, applicationPrivilege, config);
    let applicationPrivilege1 = new ApplicationPrivilege(createApplicationPrivilegeResponse.data);
    return applicationPrivilege1;
}

export async function callReadApplicationPrivilegeRestEndpointsByApplicationPrivilegeId(
    applicationPrivilegeId: number, env: string, domain: string): Promise<ApplicationPrivilege[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationPrivileges/v1?env=${env}&applicationPrivilegeId=${applicationPrivilegeId}`;
    let applicationPrivileges: ApplicationPrivilege[] | undefined = [];
    try {
        let readApplicationPrivilegeListResponse: AxiosResponse<ApplicationPrivilege[]> = await axios.get(url, config);
        for (let i = 0; i < readApplicationPrivilegeListResponse.data.length; i++) {
            applicationPrivileges[i] = new ApplicationPrivilege(readApplicationPrivilegeListResponse.data[i]);
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
    return applicationPrivileges;
}

export async function callReadApplicationPrivilegeRestEndpointsByApplicationPrivilegeName(
    applicationPrivilegeName: string, env: string, domain: string): Promise<ApplicationPrivilege[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationPrivileges/v1?env=${env}&applicationPrivilegeName=${applicationPrivilegeName}`;
    let applicationPrivileges: ApplicationPrivilege[] | undefined = [];
    try {
        let readApplicationPrivilegeListResponse: AxiosResponse<ApplicationPrivilege[]> = await axios.get(url, config);
        for( let i = 0; i < readApplicationPrivilegeListResponse.data.length; i++) {
            applicationPrivileges[i] = new ApplicationPrivilege(readApplicationPrivilegeListResponse.data[i]);
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
    return applicationPrivileges;
}

export async function callUpdateApplicationPrivilegeRestEndpoints(
    applicationPrivilege: ApplicationPrivilege, 
    env: string, domain: string): Promise<ApplicationPrivilege[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationPrivileges/v1?env=${env}`;
    let updateApplicationPrivilegeListResponse: AxiosResponse<ApplicationPrivilege[]> = await axios.put(url, applicationPrivilege, config);
    let applicationPrivileges: ApplicationPrivilege[] = [];
    for( let i = 0; i < updateApplicationPrivilegeListResponse.data.length; i++) {
        applicationPrivileges[i] = new ApplicationPrivilege(updateApplicationPrivilegeListResponse.data[i]);
    }
    return applicationPrivileges;
}

export async function callDeleteApplicationPrivilegeRestEndpointsByApplicationPrivilegeId(
    applicationPrivilegeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationPrivileges/v1?env=${env}&applicationPrivilegeId=${applicationPrivilegeId}` ;
    let deleteApplicationPrivilegeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteApplicationPrivilegeResponse.status == 204;
}

export async function callDeleteApplicationPrivilegeRestEndpointsByApplicationPrivilegeName(
    applicationPrivilegeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/applicationPrivileges/v1?env=${env}&applicationPrivilegeName=${applicationPrivilegeName}` ;
    let deleteApplicationPrivilegeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteApplicationPrivilegeResponse.status == 204;
}
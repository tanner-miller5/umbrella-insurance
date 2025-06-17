import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PendingPolicyType } from "../../../../../../../models/users/policies/pendingPolicies/pendingPolicyTypes/v1/PendingPolicyType";
import { replacer } from "../../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePendingPolicyTypeRestEndpoints(
    pendingPolicyType: PendingPolicyType, env: string, domain: string): Promise<PendingPolicyType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyTypes/v1?env=${env}`;
    let data = JSON.stringify(pendingPolicyType as any, replacer);
    let createPendingPolicyTypeResponse: AxiosResponse<PendingPolicyType> = await axios.post(url, data, config);
    let pendingPolicyType1 = new PendingPolicyType(createPendingPolicyTypeResponse.data);
    return pendingPolicyType1;
}

export async function callReadPendingPolicyTypeRestEndpointsByPendingPolicyTypeId(
    pendingPolicyTypeId: number, env: string, domain: string): Promise<PendingPolicyType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyTypes/v1?env=${env}&pendingPolicyTypeId=${pendingPolicyTypeId}`;
    let pendingPolicyTypes: PendingPolicyType[] | undefined = [];
    try {
        let readPendingPolicyTypeListResponse: AxiosResponse<PendingPolicyType[]> = await axios.get(url, config);
        let pendingPolicyTypeList = readPendingPolicyTypeListResponse.data;
        for(let i = 0; i < pendingPolicyTypeList.length; i++) {
            pendingPolicyTypes[i] = new PendingPolicyType(pendingPolicyTypeList[i]);
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
    return pendingPolicyTypes;
}

export async function callReadPendingPolicyTypeRestEndpoints(
    env: string, domain: string): Promise<PendingPolicyType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyTypes/v1?env=${env}`;
    let pendingPolicyTypes: PendingPolicyType[] | undefined = [];
    try {
        let readPendingPolicyTypeListResponse: AxiosResponse<PendingPolicyType[]> = await axios.get(url, config);
        let pendingPolicyTypeList = readPendingPolicyTypeListResponse.data;
        for(let i = 0; i < pendingPolicyTypeList.length; i++) {
            pendingPolicyTypes[i] = new PendingPolicyType(pendingPolicyTypeList[i]);
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
    return pendingPolicyTypes;
}

export async function callReadPendingPolicyTypeRestEndpointsByPendingPolicyTypeName(
    pendingPolicyTypeName: string, env: string, domain: string): Promise<PendingPolicyType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyTypes/v1?env=${env}&pendingPolicyTypeName=${pendingPolicyTypeName}`;
    let pendingPolicyTypes: PendingPolicyType[] | undefined = [];
    try {
        let readPendingPolicyTypeListResponse: AxiosResponse<PendingPolicyType[]> = await axios.get(url, config);
        let pendingPolicyTypeList = readPendingPolicyTypeListResponse.data;
        for(let i = 0; i < pendingPolicyTypeList.length; i++) {
            pendingPolicyTypes[i] = new PendingPolicyType(pendingPolicyTypeList[i]);
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
    return pendingPolicyTypes;
}

export async function callUpdatePendingPolicyTypeRestEndpoints(
    pendingPolicyType: PendingPolicyType, env: string, domain: string): Promise<PendingPolicyType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyTypes/v1?env=${env}`;
    let data = JSON.stringify(pendingPolicyType as any, replacer);
    let updatePendingPolicyTypeListResponse: AxiosResponse<PendingPolicyType[]> = await axios.put(url, data, config);
    let pendingPolicyTypeList = updatePendingPolicyTypeListResponse.data;
    let pendingPolicyTypes: PendingPolicyType[] | undefined = [];
    for(let i = 0; i < pendingPolicyTypeList.length; i++) {
        pendingPolicyTypes[i] = new PendingPolicyType(pendingPolicyTypeList[i]);
    }
    return pendingPolicyTypes;
}

export async function callDeletePendingPolicyTypeRestEndpointsByPendingPolicyTypeId(
    pendingPolicyTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyTypes/v1?env=${env}&pendingPolicyTypeId=${pendingPolicyTypeId}` ;
    let deletePendingPolicyTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingPolicyTypeResponse.status == 204;
}

export async function callDeletePendingPolicyTypeRestEndpointsByPendingPolicyTypeName(
    pendingPolicyTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyTypes/v1?env=${env}&pendingPolicyTypeName=${pendingPolicyTypeName}` ;
    let deletePendingPolicyTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingPolicyTypeResponse.status == 204;
}
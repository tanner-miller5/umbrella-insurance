import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PendingPolicyState } from "../../../../../../../models/users/policies/pendingPolicies/pendingPolicyStates/v1/PendingPolicyState";
import { replacer } from "../../../../sessions/v1/SessionRestEndpoints";
import { callCreateLoggingRestEndpoints } from "../../../../../logging/v1/LoggingRestEndpoints";
import { LoggingMessage } from "../../../../../../../models/logging/v1/LoggingMessage";

export async function callCreatePendingPolicyStateRestEndpoints(
    pendingPolicyState: PendingPolicyState, env: string, domain: string): Promise<PendingPolicyState>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyStates/v1?env=${env}`;
    let data = JSON.stringify(pendingPolicyState as any, replacer);
    let createPendingPolicyStateResponse: AxiosResponse<PendingPolicyState> = await axios.post(url, data, config);
    let pendingPolicyState1 = new PendingPolicyState(createPendingPolicyStateResponse.data);
    return pendingPolicyState1;
}

export async function callReadPendingPolicyStateRestEndpointsByPendingPolicyStateId(
    pendingPolicyStateId: number, env: string, domain: string): Promise<PendingPolicyState[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyStates/v1?env=${env}&pendingPolicyStateId=${pendingPolicyStateId}`;
    let pendingPolicyStates: PendingPolicyState[] | undefined = [];
    try {
        let readPendingPolicyStateListResponse: AxiosResponse<PendingPolicyState[]> = await axios.get(url, config);
        let pendingPolicyStateList = readPendingPolicyStateListResponse.data;
        for(let i = 0; i < pendingPolicyStateList.length; i++) {
            pendingPolicyStates[i] = new PendingPolicyState(pendingPolicyStateList[i]);
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
    return pendingPolicyStates;
}

export async function callReadPendingPolicyStateRestEndpointsByPendingPolicyStateName(
    pendingPolicyStateName: string, env: string, domain: string): Promise<PendingPolicyState[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyStates/v1?env=${env}&pendingPolicyStateName=${pendingPolicyStateName}`;
    let pendingPolicyStates: PendingPolicyState[] | undefined = [];
    try {
        let readPendingPolicyStateListResponse: AxiosResponse<PendingPolicyState[]> = await axios.get(url, config);
        let pendingPolicyStateList = readPendingPolicyStateListResponse.data;
        for(let i = 0; i < pendingPolicyStateList.length; i++) {
            pendingPolicyStates[i] = new PendingPolicyState(pendingPolicyStateList[i]);
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
    return pendingPolicyStates;
}

export async function callReadPendingPolicyStateRestEndpoints(
    env: string, domain: string): Promise<PendingPolicyState[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyStates/v1?env=${env}`;
    let pendingPolicyStates: PendingPolicyState[] | undefined = [];
    try {
        let readPendingPolicyStateListResponse: AxiosResponse<PendingPolicyState[]> = await axios.get(url, config);
        let pendingPolicyStateList = readPendingPolicyStateListResponse.data;
        for(let i = 0; i < pendingPolicyStateList.length; i++) {
            pendingPolicyStates[i] = new PendingPolicyState(pendingPolicyStateList[i]);
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
    return pendingPolicyStates;
}

export async function callUpdatePendingPolicyStateRestEndpoints(
    pendingPolicyState: PendingPolicyState, env: string, domain: string): Promise<PendingPolicyState[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyStates/v1?env=${env}`;
    let data = JSON.stringify(pendingPolicyState as any, replacer);
    let updatePendingPolicyStateListResponse: AxiosResponse<PendingPolicyState[]> = await axios.put(url, data, config);
    let pendingPolicyStateList = updatePendingPolicyStateListResponse.data;
    let pendingPolicyStates: PendingPolicyState[] | undefined = [];
    for(let i = 0; i < pendingPolicyStateList.length; i++) {
        pendingPolicyStates[i] = new PendingPolicyState(pendingPolicyStateList[i]);
    }
    return pendingPolicyStates;
}

export async function callDeletePendingPolicyStateRestEndpointsByPendingPolicyStateId(
    pendingPolicyStateId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyStates/v1?env=${env}&pendingPolicyStateId=${pendingPolicyStateId}` ;
    let deletePendingPolicyStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingPolicyStateResponse.status == 204;
}

export async function callDeletePendingPolicyStateRestEndpointsByPendingPolicyStateName(
    pendingPolicyStateName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicyStates/v1?env=${env}&pendingPolicyStateName=${pendingPolicyStateName}` ;
    let deletePendingPolicyStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingPolicyStateResponse.status == 204;
}
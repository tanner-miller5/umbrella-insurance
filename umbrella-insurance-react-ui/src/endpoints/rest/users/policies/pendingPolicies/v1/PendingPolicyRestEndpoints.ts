import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PendingPolicy } from "../../../../../../models/users/policies/pendingPolicies/v1/PendingPolicy";
import { replacer } from "../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePendingPolicyRestEndpoints(
    pendingPolicy: PendingPolicy, env: string, domain: string): Promise<PendingPolicy>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicies/v1?env=${env}`;
    let data = JSON.stringify(pendingPolicy as any, replacer);
    let createPendingPolicyResponse: AxiosResponse<PendingPolicy> = await axios.post(url, data, config);
    let pendingPolicy1 = new PendingPolicy(createPendingPolicyResponse.data);
    return pendingPolicy1;
}

export async function callReadPendingPolicyRestEndpointsByPendingPolicyId(
    pendingPolicyId: number, env: string, domain: string): Promise<PendingPolicy[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicies/v1?env=${env}&pendingPolicyId=${pendingPolicyId}`;
    let pendingPolicys: PendingPolicy[] | undefined = [];
    try {
        let readPendingPolicyListResponse: AxiosResponse<PendingPolicy[]> = await axios.get(url, config);
        let pendingPolicyList = readPendingPolicyListResponse.data;
        for(let i = 0; i < pendingPolicyList.length; i++) {
            pendingPolicys[i] = new PendingPolicy(pendingPolicyList[i]);
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
    return pendingPolicys;
}

export async function callReadPendingPolicyRestEndpointsByPendingPolicyName(
    pendingPolicyName: string, env: string, domain: string): Promise<PendingPolicy[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicies/v1?env=${env}&pendingPolicyName=${pendingPolicyName}`;
    let pendingPolicys: PendingPolicy[] | undefined = [];
    try {
        let readPendingPolicyListResponse: AxiosResponse<PendingPolicy[]> = await axios.get(url, config);
        let pendingPolicyList = readPendingPolicyListResponse.data;
        for(let i = 0; i < pendingPolicyList.length; i++) {
            pendingPolicys[i] = new PendingPolicy(pendingPolicyList[i]);
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
    return pendingPolicys;
}

export async function callUpdatePendingPolicyRestEndpoints(
    pendingPolicy: PendingPolicy, env: string, domain: string): Promise<PendingPolicy[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicies/v1?env=${env}`;
    let data = JSON.stringify(pendingPolicy as any, replacer);
    let updatePendingPolicyListResponse: AxiosResponse<PendingPolicy[]> = await axios.put(url, data, config);
    let pendingPolicyList = updatePendingPolicyListResponse.data;
    let pendingPolicys: PendingPolicy[] | undefined = [];
    for(let i = 0; i < pendingPolicyList.length; i++) {
        pendingPolicys[i] = new PendingPolicy(pendingPolicyList[i]);
    }
    return pendingPolicys;
}

export async function callDeletePendingPolicyRestEndpointsByPendingPolicyId(
    pendingPolicyId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicies/v1?env=${env}&pendingPolicyId=${pendingPolicyId}` ;
    let deletePendingPolicyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingPolicyResponse.status == 204;
}

export async function callDeletePendingPolicyRestEndpointsByPendingPolicyName(
    pendingPolicyName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingPolicies/v1?env=${env}&pendingPolicyName=${pendingPolicyName}` ;
    let deletePendingPolicyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePendingPolicyResponse.status == 204;
}
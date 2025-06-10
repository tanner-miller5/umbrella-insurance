import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { MatchedPolicy } from "../../../../../../models/users/policies/matchedPolicies/v1/MatchedPolicy";
import { replacer } from "../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateMatchedPolicyRestEndpoints(
    matchedPolicy: MatchedPolicy, env: string, domain: string): Promise<MatchedPolicy>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicies/v1?env=${env}`;
    let data = JSON.stringify(matchedPolicy as any, replacer);
    let createMatchedPolicyResponse: AxiosResponse<MatchedPolicy> = await axios.post(url, data, config);
    let matchedPolicy1 = new MatchedPolicy(createMatchedPolicyResponse.data);
    return matchedPolicy1;
}

export async function callReadMatchedPolicyRestEndpointsByMatchedPolicyId(
    matchedPolicyId: number, env: string, domain: string): Promise<MatchedPolicy[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicies/v1?env=${env}&matchedPolicyId=${matchedPolicyId}`;
    let matchedPolicys: MatchedPolicy[] | undefined = [];
    try {
        let readMatchedPolicyListResponse: AxiosResponse<MatchedPolicy[]> = await axios.get(url, config);
        let matchedPolicyList = readMatchedPolicyListResponse.data;
        for(let i = 0; i < matchedPolicyList.length; i++) {
            matchedPolicys[i] = new MatchedPolicy(matchedPolicyList[i]);
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
    return matchedPolicys;
}

export async function callReadMatchedPolicyRestEndpointsByMatchedPolicyName(
    matchedPolicyName: string, env: string, domain: string): Promise<MatchedPolicy[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicies/v1?env=${env}&matchedPolicyName=${matchedPolicyName}`;
    let matchedPolicys: MatchedPolicy[] | undefined = [];
    try {
        let readMatchedPolicyListResponse: AxiosResponse<MatchedPolicy[]> = await axios.get(url, config);
        let matchedPolicyList = readMatchedPolicyListResponse.data;
        for(let i = 0; i < matchedPolicyList.length; i++) {
            matchedPolicys[i] = new MatchedPolicy(matchedPolicyList[i]);
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
    return matchedPolicys;
}

export async function callUpdateMatchedPolicyRestEndpoints(
    matchedPolicy: MatchedPolicy, env: string, domain: string): Promise<MatchedPolicy[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicies/v1?env=${env}`;
    let data = JSON.stringify(matchedPolicy as any, replacer);
    let updateMatchedPolicyListResponse: AxiosResponse<MatchedPolicy[]> = await axios.put(url, data, config);
    let matchedPolicyList = updateMatchedPolicyListResponse.data;
    let matchedPolicys: MatchedPolicy[] | undefined = [];
    for(let i = 0; i < matchedPolicyList.length; i++) {
        matchedPolicys[i] = new MatchedPolicy(matchedPolicyList[i]);
    }
    return matchedPolicys;
}

export async function callDeleteMatchedPolicyRestEndpointsByMatchedPolicyId(
    matchedPolicyId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicies/v1?env=${env}&matchedPolicyId=${matchedPolicyId}` ;
    let deleteMatchedPolicyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteMatchedPolicyResponse.status == 204;
}

export async function callDeleteMatchedPolicyRestEndpointsByMatchedPolicyName(
    matchedPolicyName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedPolicies/v1?env=${env}&matchedPolicyName=${matchedPolicyName}` ;
    let deleteMatchedPolicyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteMatchedPolicyResponse.status == 204;
}
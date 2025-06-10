import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Reward } from "../../../../models/rewards/v1/Reward";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateRewardRestEndpoints(
    reward: Reward, env: string, domain: string): Promise<Reward>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rewards/v1?env=${env}`;
    let data = JSON.stringify(reward as any, replacer);
    let createRewardResponse: AxiosResponse<Reward> = await axios.post(url, data, config);
    let reward1 = new Reward(createRewardResponse.data);
    return reward1;
}

export async function callReadRewardRestEndpointsByRewardId(
    rewardId: number, env: string, domain: string): Promise<Reward[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rewards/v1?env=${env}&rewardId=${rewardId}`;
    let rewards: Reward[] | undefined = [];
    try {
        let readRewardListResponse: AxiosResponse<Reward[]> = await axios.get(url, config);
        let rewardList = readRewardListResponse.data;
        for(let i = 0; i < rewardList.length; i++) {
            rewards[i] = new Reward(rewardList[i]);
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
    return rewards;
}

export async function callReadRewardRestEndpointsByRewardName(
    rewardName: string, env: string, domain: string): Promise<Reward[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rewards/v1?env=${env}&rewardName=${rewardName}`;
    let rewards: Reward[] | undefined = [];
    try {
        let readRewardListResponse: AxiosResponse<Reward[]> = await axios.get(url, config);
        let rewardList = readRewardListResponse.data;
        for(let i = 0; i < rewardList.length; i++) {
            rewards[i] = new Reward(rewardList[i]);
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
    return rewards;
}

export async function callUpdateRewardRestEndpoints(
    reward: Reward, env: string, domain: string): Promise<Reward[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rewards/v1?env=${env}`;
    let data = JSON.stringify(reward as any, replacer);
    let updateRewardListResponse: AxiosResponse<Reward[]> = await axios.put(url, data, config);
    let rewardList = updateRewardListResponse.data;
    let rewards: Reward[] | undefined = [];
    for(let i = 0; i < rewardList.length; i++) {
        rewards[i] = new Reward(rewardList[i]);
    }
    return rewards;
}

export async function callDeleteRewardRestEndpointsByRewardId(
    rewardId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rewards/v1?env=${env}&rewardId=${rewardId}` ;
    let deleteRewardResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteRewardResponse.status == 204;
}

export async function callDeleteRewardRestEndpointsByRewardName(
    rewardName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/rewards/v1?env=${env}&rewardName=${rewardName}` ;
    let deleteRewardResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteRewardResponse.status == 204;
}
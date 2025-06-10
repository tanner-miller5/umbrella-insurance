import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Referral } from "../../../../models/referrals/v1/Referral";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateReferralRestEndpoints(
    referral: Referral, env: string, domain: string): Promise<Referral>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referrals/v1?env=${env}`;
    let data = JSON.stringify(referral as any, replacer);
    let createReferralResponse: AxiosResponse<Referral> = await axios.post(url, data, config);
    let referral1 = new Referral(createReferralResponse.data);
    return referral1;
}

export async function callReadReferralRestEndpointsByReferralId(
    referralId: number, env: string, domain: string): Promise<Referral[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referrals/v1?env=${env}&referralId=${referralId}`;
    let referrals: Referral[] | undefined = [];
    try {
        let readReferralListResponse: AxiosResponse<Referral[]> = await axios.get(url, config);
        let referralList = readReferralListResponse.data;
        for(let i = 0; i < referralList.length; i++) {
            referrals[i] = new Referral(referralList[i]);
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
    return referrals;
}

export async function callReadReferralRestEndpointsByReferralName(
    referralName: string, env: string, domain: string): Promise<Referral[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referrals/v1?env=${env}&referralName=${referralName}`;
    let referrals: Referral[] | undefined = [];
    try {
        let readReferralListResponse: AxiosResponse<Referral[]> = await axios.get(url, config);
        let referralList = readReferralListResponse.data;
        for(let i = 0; i < referralList.length; i++) {
            referrals[i] = new Referral(referralList[i]);
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
    return referrals;
}

export async function callUpdateReferralRestEndpoints(
    referral: Referral, env: string, domain: string): Promise<Referral[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referrals/v1?env=${env}`;
    let data = JSON.stringify(referral as any, replacer);
    let updateReferralListResponse: AxiosResponse<Referral[]> = await axios.put(url, data, config);
    let referralList = updateReferralListResponse.data;
    let referrals: Referral[] | undefined = [];
    for(let i = 0; i < referralList.length; i++) {
        referrals[i] = new Referral(referralList[i]);
    }
    return referrals;
}

export async function callDeleteReferralRestEndpointsByReferralId(
    referralId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referrals/v1?env=${env}&referralId=${referralId}` ;
    let deleteReferralResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteReferralResponse.status == 204;
}

export async function callDeleteReferralRestEndpointsByReferralName(
    referralName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/referrals/v1?env=${env}&referralName=${referralName}` ;
    let deleteReferralResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteReferralResponse.status == 204;
}
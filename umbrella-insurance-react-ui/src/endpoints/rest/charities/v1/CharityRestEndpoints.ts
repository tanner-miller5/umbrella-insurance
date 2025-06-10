import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Charity } from "../../../../models/charities/v1/Charity";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";


export async function callCreateCharityRestEndpoints(
    charity: Charity, env: string, domain: string): Promise<Charity>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/charities/v1?env=${env}`;
    let createCharityResponse: AxiosResponse<Charity> = await axios.post(url, charity, config);
    let charity1 = createCharityResponse.data;
    return charity1;
}

export async function callReadCharityRestEndpointsByCharityId(
    charityId: number, env: string, domain: string): Promise<Charity[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/charities/v1?env=${env}&charityId=${charityId}`;

    let charitys: Charity[] = [];
    try {
        let readCharityListResponse: AxiosResponse<Charity[]> = await axios.get(url, config);
        let charityList = readCharityListResponse.data;
        for(let i = 0; i < charityList.length; i++) {
            charitys[i] = new Charity(charityList[i]);
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
    return charitys;
}

export async function callReadCharityRestEndpointsByCharityName(
    charityName: string, env: string, domain: string): Promise<Charity[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/charities/v1?env=${env}&charityName=${charityName}`;
    let charityList: any | undefined = undefined;
    let charitys: Charity[] = [];
    try {
        let readCharityListResponse: AxiosResponse<Charity[]> = await axios.get(url, config);
        charityList = readCharityListResponse.data;
        for(let i = 0; i < charityList.length; i++) {
            charitys[i] = new Charity(charityList[i]);
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
    return charitys;
}

export async function callUpdateCharityRestEndpoints(
    charity: Charity, env: string, domain: string): Promise<Charity[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/charities/v1?env=${env}`;
    let updateCharityListResponse: AxiosResponse<Charity[]> = await axios.put(url, charity, config);
    let charityList = updateCharityListResponse.data;
    let charitys: Charity[] = [];
    for(let i = 0; i < charityList.length; i++) {
        charitys[i] = new Charity(charityList[i]);
    }
    return charitys;
}

export async function callDeleteCharityRestEndpointsByCharityId(
    charityId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/charities/v1?env=${env}&charityId=${charityId}` ;
    let deleteCharityResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCharityResponse.status == 204;
}

export async function callDeleteCharityRestEndpointsByCharityName(
    charityName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/charities/v1?env=${env}&charityName=${charityName}` ;
    let deleteCharityResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCharityResponse.status == 204;
}
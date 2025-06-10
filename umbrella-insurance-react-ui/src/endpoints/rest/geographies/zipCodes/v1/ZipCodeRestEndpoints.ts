import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { ZipCode } from "../../../../../models/geographies/zipCodes/v1/ZipCode";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateZipCodeRestEndpoints(
    zipCode: ZipCode, env: string, domain: string): Promise<ZipCode>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/zipCodes/v1?env=${env}`;
    let data = JSON.stringify(zipCode as any, replacer);
    let createZipCodeResponse: AxiosResponse<ZipCode> = await axios.post(url, data, config);
    let zipCode1 = new ZipCode(createZipCodeResponse.data);
    return zipCode1;
}

export async function callReadZipCodeRestEndpointsByZipCodeId(
    zipCodeId: number, env: string, domain: string): Promise<ZipCode[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/zipCodes/v1?env=${env}&zipCodeId=${zipCodeId}`;
    let zipCodes: ZipCode[] | undefined = [];
    try {
        let readZipCodeListResponse: AxiosResponse<ZipCode[]> = await axios.get(url, config);
        let zipCodesList = readZipCodeListResponse.data;
        for(let i = 0; i < zipCodesList.length; i++) {
            zipCodes[i] = new ZipCode(zipCodesList[i]);
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
    return zipCodes;
}

export async function callReadZipCodeRestEndpointsByZipCodeName(
    zipCodeName: string, env: string, domain: string): Promise<ZipCode[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/zipCodes/v1?env=${env}&zipCodeName=${zipCodeName}`;
    let zipCodes: ZipCode[] | undefined = [];
    try {
        let readZipCodeListResponse: AxiosResponse<ZipCode[]> = await axios.get(url, config);
        let zipCodesList = readZipCodeListResponse.data;
        for(let i = 0; i < zipCodesList.length; i++) {
            zipCodes[i] = new ZipCode(zipCodesList[i]);
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
    return zipCodes;
}

export async function callUpdateZipCodeRestEndpoints(
    zipCode: ZipCode, env: string, domain: string): Promise<ZipCode[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/zipCodes/v1?env=${env}`;
    let data = JSON.stringify(zipCode as any, replacer);
    let updateZipCodeListResponse: AxiosResponse<ZipCode[]> = await axios.put(url, data, config);
    let zipCodesList = updateZipCodeListResponse.data;
    let zipCodes: ZipCode[] | undefined = [];
    for(let i = 0; i < zipCodesList.length; i++) {
        zipCodes[i] = new ZipCode(zipCodesList[i]);
    }
    return zipCodes;
}

export async function callDeleteZipCodeRestEndpointsByZipCodeId(
    zipCodeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/zipCodes/v1?env=${env}&zipCodeId=${zipCodeId}` ;
    let deleteZipCodeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteZipCodeResponse.status == 204;
}

export async function callDeleteZipCodeRestEndpointsByZipCodeName(
    zipCodeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/zipCodes/v1?env=${env}&zipCodeName=${zipCodeName}` ;
    let deleteZipCodeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteZipCodeResponse.status == 204;
}
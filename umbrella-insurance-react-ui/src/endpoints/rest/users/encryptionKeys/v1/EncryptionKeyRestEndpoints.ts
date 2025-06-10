import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { EncryptionKey } from "../../../../../models/users/encryptedKeys/v1/EncryptionKey";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateEncryptionKeyRestEndpoints(
    encryptionKey: EncryptionKey, env: string, domain: string): Promise<EncryptionKey>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/encryptionKeys/v1?env=${env}`;
    let data = JSON.stringify(encryptionKey as any, replacer);
    let createEncryptionKeyResponse: AxiosResponse<EncryptionKey> = await axios.post(url, data, config);
    let encryptionKey1 = new EncryptionKey(createEncryptionKeyResponse.data);
    return encryptionKey1;
}

export async function callReadEncryptionKeyRestEndpointsByEncryptionKeyId(
    encryptionKeyId: number, env: string, domain: string): Promise<EncryptionKey[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/encryptionKeys/v1?env=${env}&encryptionKeyId=${encryptionKeyId}`;
    let encryptionKeys: EncryptionKey[] | undefined = [];
    try {
        let readEncryptionKeyListResponse: AxiosResponse<EncryptionKey[]> = await axios.get(url, config);
        let encryptionKeyList = readEncryptionKeyListResponse.data;
        for(let i = 0; i < encryptionKeyList.length; i++) {
            encryptionKeys[i] = new EncryptionKey(encryptionKeyList[i]);
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
    return encryptionKeys;
}

export async function callReadEncryptionKeyRestEndpointsByEncryptionKeyName(
    encryptionKeyName: string, env: string, domain: string): Promise<EncryptionKey[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/encryptionKeys/v1?env=${env}&encryptionKeyName=${encryptionKeyName}`;
    let encryptionKeys: EncryptionKey[] | undefined = [];
    try {
        let readEncryptionKeyListResponse: AxiosResponse<EncryptionKey[]> = await axios.get(url, config);
        let encryptionKeyList = readEncryptionKeyListResponse.data;
        for(let i = 0; i < encryptionKeyList.length; i++) {
            encryptionKeys[i] = new EncryptionKey(encryptionKeyList[i]);
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
    return encryptionKeys;
}

export async function callUpdateEncryptionKeyRestEndpoints(
    encryptionKey: EncryptionKey, env: string, domain: string): Promise<EncryptionKey[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/encryptionKeys/v1?env=${env}`;
    let data = JSON.stringify(encryptionKey as any, replacer);
    let updateEncryptionKeyListResponse: AxiosResponse<EncryptionKey[]> = await axios.put(url, data, config);
    let encryptionKeys: EncryptionKey[] | undefined = [];
    let encryptionKeyList = updateEncryptionKeyListResponse.data;
    for(let i = 0; i < encryptionKeyList.length; i++) {
        encryptionKeys[i] = new EncryptionKey(encryptionKeyList[i]);
    }
    return encryptionKeys;
}

export async function callDeleteEncryptionKeyRestEndpointsByEncryptionKeyId(
    encryptionKeyId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/encryptionKeys/v1?env=${env}&encryptionKeyId=${encryptionKeyId}` ;
    let deleteEncryptionKeyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEncryptionKeyResponse.status == 204;
}

export async function callDeleteEncryptionKeyRestEndpointsByEncryptionKeyName(
    encryptionKeyName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/encryptionKeys/v1?env=${env}&encryptionKeyName=${encryptionKeyName}` ;
    let deleteEncryptionKeyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEncryptionKeyResponse.status == 204;
}
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Password } from "../../../../../models/users/passwords/v1/Password";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePasswordRestEndpoints(
    password: Password, env: string, domain: string): Promise<Password>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/passwords/v1?env=${env}`;
    let data = JSON.stringify(password as any, replacer);
    let createPasswordResponse: AxiosResponse<Password> = await axios.post(url, data, config);
    let password1 = new Password(createPasswordResponse.data);
    return password1;
}

export async function callReadPasswordRestEndpointsByPasswordId(
    passwordId: number, env: string, domain: string): Promise<Password[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/passwords/v1?env=${env}&passwordId=${passwordId}`;
    let passwords: Password[] | undefined = [];
    try {
        let readPasswordListResponse: AxiosResponse<Password[]> = await axios.get(url, config);
        let passwordList = readPasswordListResponse.data;
        for(let i = 0; i < passwordList.length; i++) {
            passwords[i] = new Password(passwordList[i]);
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
    return passwords;
}

export async function callReadPasswordRestEndpointsByPasswordName(
    passwordName: string, env: string, domain: string): Promise<Password[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/passwords/v1?env=${env}&passwordName=${passwordName}`;
    let passwords: Password[] | undefined = [];
    try {
        let readPasswordListResponse: AxiosResponse<Password[]> = await axios.get(url, config);
        let passwordList = readPasswordListResponse.data;
        for(let i = 0; i < passwordList.length; i++) {
            passwords[i] = new Password(passwordList[i]);
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
    return passwords;
}

export async function callUpdatePasswordRestEndpoints(
    password: Password, env: string, domain: string): Promise<Password[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/passwords/v1?env=${env}`;
    let data = JSON.stringify(password as any, replacer);
    let updatePasswordListResponse: AxiosResponse<Password[]> = await axios.put(url, data, config);
    let passwordList = updatePasswordListResponse.data;
    let passwords: Password[] | undefined = [];
    for(let i = 0; i < passwordList.length; i++) {
        passwords[i] = new Password(passwordList[i]);
    }
    return passwords;
}

export async function callDeletePasswordRestEndpointsByPasswordId(
    passwordId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/passwords/v1?env=${env}&passwordId=${passwordId}` ;
    let deletePasswordResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePasswordResponse.status == 204;
}

export async function callDeletePasswordRestEndpointsByPasswordName(
    passwordName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/passwords/v1?env=${env}&passwordName=${passwordName}` ;
    let deletePasswordResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePasswordResponse.status == 204;
}
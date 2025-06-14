import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { State } from "../../../../../models/geographies/states/v1/State";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateStateRestEndpoints(
    state: State, env: string, domain: string): Promise<State>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/states/v1?env=${env}`;
    let data = JSON.stringify(state as any, replacer);
    let createStateResponse: AxiosResponse<State> = await axios.post(url, data, config);
    let state1 = new State(createStateResponse.data);
    return state1;
}

export async function callReadStateRestEndpoints(
    env: string, domain: string): Promise<State[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/states/v1?env=${env}`;
    let states: State[] | undefined = [];
    try {
        let readStateListResponse: AxiosResponse<State[]> = await axios.get(url, config);
        let statesList = readStateListResponse.data;
        for (let i = 0; i < statesList.length; i++) {
            states[i] = new State(statesList[i]);
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
    return states;
}

export async function callReadStateRestEndpointsByStateId(
    stateId: number, env: string, domain: string): Promise<State[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/states/v1?env=${env}&stateId=${stateId}`;
    let states: State[] | undefined = [];
    try {
        let readStateListResponse: AxiosResponse<State[]> = await axios.get(url, config);
        let statesList = readStateListResponse.data;
        for (let i = 0; i < statesList.length; i++) {
            states[i] = new State(statesList[i]);
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
    return states;
}

export async function callReadStateRestEndpointsByStateName(
    stateName: string, env: string, domain: string): Promise<State[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/states/v1?env=${env}&stateName=${stateName}`;
    let states: State[] | undefined = [];
    try {
        let readStateListResponse: AxiosResponse<State[]> = await axios.get(url, config);
        let statesList = readStateListResponse.data;
        for (let i = 0; i < statesList.length; i++) {
            states[i] = new State(statesList[i]);
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
    return states;
}

export async function callUpdateStateRestEndpoints(
    state: State, env: string, domain: string): Promise<State[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/states/v1?env=${env}`;
    let data = JSON.stringify(state as any, replacer);
    let updateStateListResponse: AxiosResponse<State[]> = await axios.put(url, data, config);
    let statesList = updateStateListResponse.data;
    let states: State[] | undefined = [];
    for (let i = 0; i < statesList.length; i++) {
        states[i] = new State(statesList[i]);
    }
    return states;
}

export async function callDeleteStateRestEndpointsByStateId(
    stateId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/states/v1?env=${env}&stateId=${stateId}` ;
    let deleteStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteStateResponse.status == 204;
}

export async function callDeleteStateRestEndpointsByStateName(
    stateName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/states/v1?env=${env}&stateName=${stateName}` ;
    let deleteStateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteStateResponse.status == 204;
}
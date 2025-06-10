import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Unit } from "../../../../models/units/v1/Unit";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateUnitRestEndpoints(
    unit: Unit, env: string, domain: string): Promise<Unit>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/units/v1?env=${env}`;
    let data = JSON.stringify(unit as any, replacer);
    let createUnitResponse: AxiosResponse<Unit> = await axios.post(url, data, config);
    let unit1 = new Unit(createUnitResponse.data);
    return unit1;
}

export async function callReadUnitsRestEndpoint(
    env: string, domain: string): Promise<Unit[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/units/v1?env=${env}`;
    let units: Unit[] | undefined = [];
    try {
        let readUnitListResponse: AxiosResponse<Unit[]> = await axios.get(url, config);
        let unitsList = readUnitListResponse.data;
        for (let i = 0; i < unitsList.length; i++) {
            units[i] = new Unit(unitsList[i]);
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
    return units;
}

export async function callReadUnitRestEndpointsByUnitId(
    unitId: number, env: string, domain: string): Promise<Unit[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/units/v1?env=${env}&unitId=${unitId}`;
    let units: Unit[] | undefined = [];
    try {
        let readUnitListResponse: AxiosResponse<Unit[]> = await axios.get(url, config);
        let unitsList = readUnitListResponse.data;
        for (let i = 0; i < unitsList.length; i++) {
            units[i] = new Unit(unitsList[i]);
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
    return units;
}

export async function callReadUnitRestEndpointsByUnitName(
    unitName: string, env: string, domain: string): Promise<Unit[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/units/v1?env=${env}&unitName=${unitName}`;
    let units: Unit[] | undefined = [];
    try {
        let readUnitListResponse: AxiosResponse<Unit[]> = await axios.get(url, config);
        let unitsList = readUnitListResponse.data;
        for (let i = 0; i < unitsList.length; i++) {
            units[i] = new Unit(unitsList[i]);
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
    return units;
}

export async function callUpdateUnitRestEndpoints(
    unit: Unit, env: string, domain: string): Promise<Unit[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/units/v1?env=${env}`;
    let data = JSON.stringify(unit as any, replacer);
    let updateUnitListResponse: AxiosResponse<Unit[]> = await axios.put(url, data, config);
    let units: Unit[] | undefined = [];
    let unitsList = updateUnitListResponse.data;
    for (let i = 0; i < unitsList.length; i++) {
        units[i] = new Unit(unitsList[i]);
    }
    return units;
}

export async function callDeleteUnitRestEndpointsByUnitId(
    unitId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/units/v1?env=${env}&unitId=${unitId}` ;
    let deleteUnitResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteUnitResponse.status == 204;
}

export async function callDeleteUnitRestEndpointsByUnitName(
    unitName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/units/v1?env=${env}&unitName=${unitName}` ;
    let deleteUnitResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteUnitResponse.status == 204;
}
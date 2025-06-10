import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Specialty } from "../../../../../../models/people/analysts/specialties/v1/Specialty";
import { replacer } from "../../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateSpecialtyRestEndpoints(
    specialty: Specialty, env: string, domain: string): Promise<Specialty>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/specialties/v1?env=${env}`;
    let data = JSON.stringify(specialty as any, replacer);
    let createSpecialtyResponse: AxiosResponse<Specialty> = await axios.post(url, data, config);
    let specialty1 = new Specialty(createSpecialtyResponse.data);
    return specialty1;
}

export async function callReadSpecialtyRestEndpointsBySpecialtyId(
    specialtyId: number, env: string, domain: string): Promise<Specialty[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/specialties/v1?env=${env}&specialtyId=${specialtyId}`;
    let specialtys: Specialty[] | undefined = [];
    try {
        let readSpecialtyListResponse: AxiosResponse<Specialty[]> = await axios.get(url, config);
        let specialtyList = readSpecialtyListResponse.data;
        for(let i = 0; i < specialtyList.length; i++) {
            specialtys[i] = new Specialty(specialtyList[i]);
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
    return specialtys;
}

export async function callReadSpecialtyRestEndpointsBySpecialtyName(
    specialtyName: string, env: string, domain: string): Promise<Specialty[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/specialties/v1?env=${env}&specialtyName=${specialtyName}`;
    let specialtys: Specialty[] | undefined = [];
    try {
        let readSpecialtyListResponse: AxiosResponse<Specialty[]> = await axios.get(url, config);
        let specialtyList = readSpecialtyListResponse.data;
        for(let i = 0; i < specialtyList.length; i++) {
            specialtys[i] = new Specialty(specialtyList[i]);
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
    return specialtys;
}

export async function callUpdateSpecialtyRestEndpoints(
    specialty: Specialty, env: string, domain: string): Promise<Specialty[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/specialties/v1?env=${env}`;
    let data = JSON.stringify(specialty as any, replacer);
    let updateSpecialtyListResponse: AxiosResponse<Specialty[]> = await axios.put(url, data, config);
    let specialtyList = updateSpecialtyListResponse.data;
    let specialtys: Specialty[] | undefined = [];
    for(let i = 0; i < specialtyList.length; i++) {
        specialtys[i] = new Specialty(specialtyList[i]);
    }
    return specialtys;
}

export async function callDeleteSpecialtyRestEndpointsBySpecialtyId(
    specialtyId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/specialties/v1?env=${env}&specialtyId=${specialtyId}` ;
    let deleteSpecialtyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSpecialtyResponse.status == 204;
}

export async function callDeleteSpecialtyRestEndpointsBySpecialtyName(
    specialtyName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/specialties/v1?env=${env}&specialtyName=${specialtyName}` ;
    let deleteSpecialtyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteSpecialtyResponse.status == 204;
}
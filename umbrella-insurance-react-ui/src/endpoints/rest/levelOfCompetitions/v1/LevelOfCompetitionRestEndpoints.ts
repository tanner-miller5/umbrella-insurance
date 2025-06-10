import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { LevelOfCompetition } from "../../../../models/levelOfCompetitions/v1/LevelOfCompetition";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateLevelOfCompetitionRestEndpoints(
    levelOfCompetition: LevelOfCompetition, env: string, domain: string): Promise<LevelOfCompetition>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/levelOfCompetitions/v1?env=${env}`;
    let createLevelOfCompetitionResponse: AxiosResponse<LevelOfCompetition> = await axios.post(url, levelOfCompetition, config);
    let levelOfCompetition1 = createLevelOfCompetitionResponse.data;
    return levelOfCompetition1;
}

export async function callReadLevelOfCompetitionRestEndpointsByLevelOfCompetitionId(
    levelOfCompetitionId: number, env: string, domain: string): Promise<LevelOfCompetition[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/levelOfCompetitions/v1?env=${env}&levelOfCompetitionId=${levelOfCompetitionId}`;
    let levelOfCompetition: any | undefined = undefined;
    try {
        let readLevelOfCompetitionListResponse: AxiosResponse<LevelOfCompetition[]> = await axios.get(url, config);
        levelOfCompetition = readLevelOfCompetitionListResponse.data;
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
    return levelOfCompetition;
}

export async function callReadLevelOfCompetitionRestEndpointsByLevelOfCompetitionName(
    levelOfCompetitionName: string, env: string, domain: string): Promise<LevelOfCompetition[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/levelOfCompetitions/v1?env=${env}&levelOfCompetitionName=${levelOfCompetitionName}`;
    let levelOfCompetition: any | undefined = undefined;
    try {
        let readLevelOfCompetitionListResponse: AxiosResponse<LevelOfCompetition[]> = await axios.get(url, config);
        levelOfCompetition = readLevelOfCompetitionListResponse.data;
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
    return levelOfCompetition;
}

export async function callUpdateLevelOfCompetitionRestEndpoints(
    levelOfCompetition: LevelOfCompetition, env: string, domain: string): Promise<LevelOfCompetition[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/levelOfCompetitions/v1?env=${env}`;
    let updateLevelOfCompetitionListResponse: AxiosResponse<LevelOfCompetition[]> = await axios.put(url, levelOfCompetition, config);
    let levelOfCompetitionList = updateLevelOfCompetitionListResponse.data;
    return levelOfCompetitionList;
}

export async function callDeleteLevelOfCompetitionRestEndpointsByLevelOfCompetitionId(
    levelOfCompetitionId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/levelOfCompetitions/v1?env=${env}&levelOfCompetitionId=${levelOfCompetitionId}` ;
    let deleteLevelOfCompetitionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return true;
}

export async function callDeleteLevelOfCompetitionRestEndpointsByLevelOfCompetitionName(
    levelOfCompetitionName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/levelOfCompetitions/v1?env=${env}&levelOfCompetitionName=${levelOfCompetitionName}` ;
    let deleteLevelOfCompetitionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return true;
}
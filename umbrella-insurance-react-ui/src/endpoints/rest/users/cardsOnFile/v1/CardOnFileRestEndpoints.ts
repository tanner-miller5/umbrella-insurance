import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { CardOnFile } from "../../../../../models/users/cardsOnFile/v1/CardOnFile";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateCardOnFileRestEndpoints(
    cardOnFile: CardOnFile, env: string, domain: string): Promise<CardOnFile>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardOnFiles/v1?env=${env}`;
    let data = JSON.stringify(cardOnFile as any, replacer);
    let createCardOnFileResponse: AxiosResponse<CardOnFile> = await axios.post(url, data, config);
    let cardOnFile1 = new CardOnFile(createCardOnFileResponse.data);
    return cardOnFile1;
}

export async function callReadCardOnFileRestEndpointsByCardOnFileId(
    cardOnFileId: number, env: string, domain: string): Promise<CardOnFile[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardOnFiles/v1?env=${env}&cardOnFileId=${cardOnFileId}`;
    let cardOnFiles: CardOnFile[] | undefined = [];
    try {
        let readCardOnFileListResponse: AxiosResponse<CardOnFile[]> = await axios.get(url, config);
        let cardOnFileList = readCardOnFileListResponse.data;
        for(let i = 0; i < cardOnFileList.length; i++) {
            cardOnFiles[i] = new CardOnFile(cardOnFileList[i]);
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
    return cardOnFiles;
}

export async function callReadCardOnFileRestEndpointsByCardOnFileName(
    cardOnFileName: string, env: string, domain: string): Promise<CardOnFile[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardOnFiles/v1?env=${env}&cardOnFileName=${cardOnFileName}`;
    let cardOnFiles: CardOnFile[] | undefined = [];
    try {
        let readCardOnFileListResponse: AxiosResponse<CardOnFile[]> = await axios.get(url, config);
        let cardOnFileList = readCardOnFileListResponse.data;
        for(let i = 0; i < cardOnFileList.length; i++) {
            cardOnFiles[i] = new CardOnFile(cardOnFileList[i]);
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
    return cardOnFiles;
}

export async function callUpdateCardOnFileRestEndpoints(
    cardOnFile: CardOnFile, env: string, domain: string): Promise<CardOnFile[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardOnFiles/v1?env=${env}`;
    let data = JSON.stringify(cardOnFile as any, replacer);
    let updateCardOnFileListResponse: AxiosResponse<CardOnFile[]> = await axios.put(url, data, config);
    let cardOnFileList = updateCardOnFileListResponse.data;
    let cardOnFiles: CardOnFile[] | undefined = [];
    for(let i = 0; i < cardOnFileList.length; i++) {
        cardOnFiles[i] = new CardOnFile(cardOnFileList[i]);
    }
    return cardOnFiles;
}

export async function callDeleteCardOnFileRestEndpointsByCardOnFileId(
    cardOnFileId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardOnFiles/v1?env=${env}&cardOnFileId=${cardOnFileId}` ;
    let deleteCardOnFileResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCardOnFileResponse.status == 204;
}

export async function callDeleteCardOnFileRestEndpointsByCardOnFileName(
    cardOnFileName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardOnFiles/v1?env=${env}&cardOnFileName=${cardOnFileName}` ;
    let deleteCardOnFileResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCardOnFileResponse.status == 204;
}
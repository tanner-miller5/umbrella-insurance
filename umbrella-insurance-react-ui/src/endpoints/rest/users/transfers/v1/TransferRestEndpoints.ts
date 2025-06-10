import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Transfer } from "../../../../../models/users/transfers/v1/Transfer";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateTransferRestEndpoints(
    transfer: Transfer, env: string, domain: string): Promise<Transfer>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/transfers/v1?env=${env}`;
    let data = JSON.stringify(transfer as any, replacer);
    let createTransferResponse: AxiosResponse<Transfer> = await axios.post(url, data, config);
    let transfer1 = new Transfer(createTransferResponse.data);
    return transfer1;
}

export async function callReadTransferRestEndpointsByTransferId(
    transferId: number, env: string, domain: string): Promise<Transfer[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/transfers/v1?env=${env}&transferId=${transferId}`;
    let transfers: Transfer[] | undefined = [];
    try {
        let readTransferListResponse: AxiosResponse<Transfer[]> = await axios.get(url, config);
        let transferList = readTransferListResponse.data;
        for(let i = 0; i < transferList.length; i++) {
            transfers[i] = new Transfer(transferList[i]);
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
    return transfers;
}

export async function callReadTransferRestEndpointsByTransferName(
    transferName: string, env: string, domain: string): Promise<Transfer[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/transfers/v1?env=${env}&transferName=${transferName}`;
    let transfers: Transfer[] | undefined = [];
    try {
        let readTransferListResponse: AxiosResponse<Transfer[]> = await axios.get(url, config);
        let transferList = readTransferListResponse.data;
        for(let i = 0; i < transferList.length; i++) {
            transfers[i] = new Transfer(transferList[i]);
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
    return transfers;
}

export async function callUpdateTransferRestEndpoints(
    transfer: Transfer, env: string, domain: string): Promise<Transfer[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/transfers/v1?env=${env}`;
    let data = JSON.stringify(transfer as any, replacer);
    let updateTransferListResponse: AxiosResponse<Transfer[]> = await axios.put(url, data, config);
    let transferList = updateTransferListResponse.data;
    let transfers: Transfer[] | undefined = [];
    for(let i = 0; i < transferList.length; i++) {
        transfers[i] = new Transfer(transferList[i]);
    }
    return transfers;
}

export async function callDeleteTransferRestEndpointsByTransferId(
    transferId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/transfers/v1?env=${env}&transferId=${transferId}` ;
    let deleteTransferResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTransferResponse.status == 204;
}

export async function callDeleteTransferRestEndpointsByTransferName(
    transferName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/transfers/v1?env=${env}&transferName=${transferName}` ;
    let deleteTransferResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteTransferResponse.status == 204;
}
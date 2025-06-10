import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { WireTransfer } from "../../../../../../models/users/transfers/wireTransfers/v1/WireTransfer";
import { replacer } from "../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";


export async function callCreateWireTransferRestEndpoints(
    wireTransfer: WireTransfer, env: string, domain: string): Promise<WireTransfer>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/wireTransfers/v1?env=${env}`;
    let data = JSON.stringify(wireTransfer as any, replacer);
    let createWireTransferResponse: AxiosResponse<WireTransfer> = await axios.post(url, data, config);
    let wireTransfer1 = new WireTransfer(createWireTransferResponse.data);
    return wireTransfer1;
}

export async function callReadWireTransferRestEndpointsByWireTransferId(
    wireTransferId: number, env: string, domain: string): Promise<WireTransfer[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/wireTransfers/v1?env=${env}&wireTransferId=${wireTransferId}`;
    let wireTransfers: WireTransfer[] | undefined = [];
    try {
        let readWireTransferListResponse: AxiosResponse<WireTransfer[]> = await axios.get(url, config);
        let wireTransferList = readWireTransferListResponse.data;
        for(let i = 0; i < wireTransferList.length; i++) {
            wireTransfers[i] = new WireTransfer(wireTransferList[i]);
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
    return wireTransfers;
}

export async function callReadWireTransferRestEndpointsByWireTransferName(
    wireTransferName: string, env: string, domain: string): Promise<WireTransfer[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/wireTransfers/v1?env=${env}&wireTransferName=${wireTransferName}`;
    let wireTransfers: WireTransfer[] | undefined = [];
    try {
        let readWireTransferListResponse: AxiosResponse<WireTransfer[]> = await axios.get(url, config);
        let wireTransferList = readWireTransferListResponse.data;
        for(let i = 0; i < wireTransferList.length; i++) {
            wireTransfers[i] = new WireTransfer(wireTransferList[i]);
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
    return wireTransfers;
}

export async function callUpdateWireTransferRestEndpoints(
    wireTransfer: WireTransfer, env: string, domain: string): Promise<WireTransfer[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/wireTransfers/v1?env=${env}`;
    let data = JSON.stringify(wireTransfer as any, replacer);
    let updateWireTransferListResponse: AxiosResponse<WireTransfer[]> = await axios.put(url, data, config);
    let wireTransferList = updateWireTransferListResponse.data;
    let wireTransfers: WireTransfer[] | undefined = [];
    for(let i = 0; i < wireTransferList.length; i++) {
        wireTransfers[i] = new WireTransfer(wireTransferList[i]);
    }
    return wireTransfers;
}

export async function callDeleteWireTransferRestEndpointsByWireTransferId(
    wireTransferId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/wireTransfers/v1?env=${env}&wireTransferId=${wireTransferId}` ;
    let deleteWireTransferResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteWireTransferResponse.status == 204;
}

export async function callDeleteWireTransferRestEndpointsByWireTransferName(
    wireTransferName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/wireTransfers/v1?env=${env}&wireTransferName=${wireTransferName}` ;
    let deleteWireTransferResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteWireTransferResponse.status == 204;
}
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { CardTransfer } from "../../../../../models/users/transfers/cardTransfers/v1/CardTransfer";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateCardTransferRestEndpoints(
    cardTransfer: CardTransfer, env: string, domain: string): Promise<CardTransfer>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardTransfers/v1?env=${env}`;
    let data = JSON.stringify(cardTransfer as any, replacer);
    let createCardTransferResponse: AxiosResponse<CardTransfer> = await axios.post(url, data, config);
    let cardTransfer1 = new CardTransfer(createCardTransferResponse.data);
    return cardTransfer1;
}

export async function callReadCardTransferRestEndpointsByCardTransferId(
    cardTransferId: number, env: string, domain: string): Promise<CardTransfer[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardTransfers/v1?env=${env}&cardTransferId=${cardTransferId}`;
    let cardTransfers: CardTransfer[] | undefined = [];
    try {
        let readCardTransferListResponse: AxiosResponse<CardTransfer[]> = await axios.get(url, config);
        let cardTransferList = readCardTransferListResponse.data;
        for(let i = 0; i < cardTransferList.length; i++) {
            cardTransfers[i] = new CardTransfer(cardTransferList[i]);
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
    return cardTransfers;
}

export async function callReadCardTransferRestEndpointsByCardTransferName(
    cardTransferName: string, env: string, domain: string): Promise<CardTransfer[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardTransfers/v1?env=${env}&cardTransferName=${cardTransferName}`;
    let cardTransfers: CardTransfer[] | undefined = [];
    try {
        let readCardTransferListResponse: AxiosResponse<CardTransfer[]> = await axios.get(url, config);
        let cardTransferList = readCardTransferListResponse.data;
        for(let i = 0; i < cardTransferList.length; i++) {
            cardTransfers[i] = new CardTransfer(cardTransferList[i]);
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
    return cardTransfers;
}

export async function callUpdateCardTransferRestEndpoints(
    cardTransfer: CardTransfer, env: string, domain: string): Promise<CardTransfer[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardTransfers/v1?env=${env}`;
    let data = JSON.stringify(cardTransfer as any, replacer);
    let updateCardTransferListResponse: AxiosResponse<CardTransfer[]> = await axios.put(url, data, config);
    let cardTransferList = updateCardTransferListResponse.data;
    let cardTransfers: CardTransfer[] | undefined = [];
    for(let i = 0; i < cardTransferList.length; i++) {
        cardTransfers[i] = new CardTransfer(cardTransferList[i]);
    }
    return cardTransfers;
}

export async function callDeleteCardTransferRestEndpointsByCardTransferId(
    cardTransferId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardTransfers/v1?env=${env}&cardTransferId=${cardTransferId}` ;
    let deleteCardTransferResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCardTransferResponse.status == 204;
}

export async function callDeleteCardTransferRestEndpointsByCardTransferName(
    cardTransferName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cardTransfers/v1?env=${env}&cardTransferName=${cardTransferName}` ;
    let deleteCardTransferResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCardTransferResponse.status == 204;
}
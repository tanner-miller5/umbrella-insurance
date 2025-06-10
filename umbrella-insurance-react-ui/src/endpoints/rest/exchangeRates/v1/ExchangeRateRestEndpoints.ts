import axios, { AxiosRequestConfig, AxiosResponse } from "axios";


import { ExchangeRate } from "../../../../models/exchangeRates/ExchangeRate";
import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateExchangeRateRestEndpoints(
    exchangeRate: ExchangeRate, env: string, domain: string): Promise<ExchangeRate>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/exchangeRates/v1?env=${env}`;
    let data = JSON.stringify(exchangeRate as any, replacer);
    let createExchangeRateResponse: AxiosResponse<ExchangeRate> = await axios.post(url, data, config);
    let exchangeRate1 = new ExchangeRate(createExchangeRateResponse.data);
    return exchangeRate1;
}

export async function callReadExchangeRateRestEndpointsByExchangeRateId(
    exchangeRateId: number, env: string, domain: string): Promise<ExchangeRate[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/exchangeRates/v1?env=${env}&exchangeRateId=${exchangeRateId}`;
    let exchangeRates: ExchangeRate[] | undefined = [];
    try {
        let readExchangeRateListResponse: AxiosResponse<ExchangeRate[]> = await axios.get(url, config);
        let exchangeRatesList = readExchangeRateListResponse.data;
        for (let i = 0; i < exchangeRatesList.length; i++) {
            exchangeRates[i] = new ExchangeRate(readExchangeRateListResponse.data[i]);
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
    return exchangeRates;
}

export async function callReadExchangeRateRestEndpointsByExchangeRateName(
    exchangeRateName: string, env: string, domain: string): Promise<ExchangeRate[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/exchangeRates/v1?env=${env}&exchangeRateName=${exchangeRateName}`;
    let exchangeRates: ExchangeRate[] | undefined = [];
    try {
        let readExchangeRateListResponse: AxiosResponse<ExchangeRate[]> = await axios.get(url, config);
        let exchangeRatesList = readExchangeRateListResponse.data;
        for (let i = 0; i < exchangeRatesList.length; i++) {
            exchangeRates[i] = new ExchangeRate(readExchangeRateListResponse.data[i]);
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
    return exchangeRates;
}

export async function callUpdateExchangeRateRestEndpoints(
    exchangeRate: ExchangeRate, env: string, domain: string): Promise<ExchangeRate[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/exchangeRates/v1?env=${env}`;
    let data = JSON.stringify(exchangeRate as any, replacer);
    let updateExchangeRateListResponse: AxiosResponse<ExchangeRate[]> = await axios.put(url, data, config);
    let exchangeRates: ExchangeRate[] | undefined = [];
    let exchangeRatesList = updateExchangeRateListResponse.data;
    for (let i = 0; i < exchangeRatesList.length; i++) {
        exchangeRates[i] = new ExchangeRate(updateExchangeRateListResponse.data[i]);
    }
    return exchangeRates;
}

export async function callDeleteExchangeRateRestEndpointsByExchangeRateId(
    exchangeRateId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/exchangeRates/v1?env=${env}&exchangeRateId=${exchangeRateId}` ;
    let deleteExchangeRateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteExchangeRateResponse.status == 204;
}

export async function callDeleteExchangeRateRestEndpointsByExchangeRateName(
    exchangeRateName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/exchangeRates/v1?env=${env}&exchangeRateName=${exchangeRateName}` ;
    let deleteExchangeRateResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteExchangeRateResponse.status == 204;
}
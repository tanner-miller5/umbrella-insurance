import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Country } from "../../../../../models/geographies/countries/v1/Country";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateCountryRestEndpoints(
    country: Country, env: string, domain: string): Promise<Country>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/countries/v1?env=${env}`;
    let data = JSON.stringify(country as any, replacer);
    let createCountryResponse: AxiosResponse<Country> = await axios.post(url, data, config);
    let country1 = new Country(createCountryResponse.data);
    return country1;
}

export async function callReadCountryRestEndpointsByCountryId(
    countryId: number, env: string, domain: string): Promise<Country[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/countries/v1?env=${env}&countryId=${countryId}`;
    let countrys: Country[] | undefined = [];
    try {
        let readCountryListResponse: AxiosResponse<Country[]> = await axios.get(url, config);
        let countrysList = readCountryListResponse.data;
        for (let i = 0; i < countrysList.length; i++) {
            countrys[i] = new Country(countrysList[i]);
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
    return countrys;
}

export async function callReadCountryRestEndpointsByCountryName(
    countryName: string, env: string, domain: string): Promise<Country[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/countries/v1?env=${env}&countryName=${countryName}`;
    let countrys: Country[] | undefined = [];
    try {
        let readCountryListResponse: AxiosResponse<Country[]> = await axios.get(url, config);
        let countrysList = readCountryListResponse.data;
        for (let i = 0; i < countrysList.length; i++) {
            countrys[i] = new Country(countrysList[i]);
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
    return countrys;
}

export async function callUpdateCountryRestEndpoints(
    country: Country, env: string, domain: string): Promise<Country[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/countries/v1?env=${env}`;
    let data = JSON.stringify(country as any, replacer);
    let updateCountryListResponse: AxiosResponse<Country[]> = await axios.put(url, data, config);
    let countrysList = updateCountryListResponse.data;
    let countrys: Country[] | undefined = [];
    for (let i = 0; i < countrysList.length; i++) {
        countrys[i] = new Country(countrysList[i]);
    }
    return countrys;
}

export async function callDeleteCountryRestEndpointsByCountryId(
    countryId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/countries/v1?env=${env}&countryId=${countryId}` ;
    let deleteCountryResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCountryResponse.status == 204;
}

export async function callDeleteCountryRestEndpointsByCountryName(
    countryName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/countries/v1?env=${env}&countryName=${countryName}` ;
    let deleteCountryResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCountryResponse.status == 204;
}
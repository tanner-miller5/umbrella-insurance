import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { City } from "../../../../../models/geographies/cities/v1/City";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";


export async function callCreateCityRestEndpoints(
    city: City, env: string, domain: string): Promise<City>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cities/v1?env=${env}`;
    let data = JSON.stringify(city as any, replacer);
    let createCityResponse: AxiosResponse<City> = await axios.post(url, data, config);
    let city1 = createCityResponse.data;
    return city1;
}

export async function callReadCityRestEndpointsByCityId(
    cityId: number, env: string, domain: string): Promise<City[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cities/v1?env=${env}&cityId=${cityId}`;
    let citys: City[] | undefined = [];
    try {
        let readCityListResponse: AxiosResponse<City[]> = await axios.get(url, config);
        let citysList = readCityListResponse.data;
        for (let i = 0; i < citysList.length; i++) {
            citys[i] = new City(citysList[i]);
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
    return citys;
}

export async function callReadCityRestEndpointsByCityName(
    cityName: string, env: string, domain: string): Promise<City[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cities/v1?env=${env}&cityName=${cityName}`;
    let citys: City[] | undefined = [];
    try {
        let readCityListResponse: AxiosResponse<City[]> = await axios.get(url, config);
        let citysList = readCityListResponse.data;
        for (let i = 0; i < citysList.length; i++) {
            citys[i] = new City(citysList[i]);
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
    return citys;
}

export async function callUpdateCityRestEndpoints(
    city: City, env: string, domain: string): Promise<City[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cities/v1?env=${env}`;
    let data = JSON.stringify(city as any, replacer);
    let updateCityListResponse: AxiosResponse<City[]> = await axios.put(url, data, config);
    let citys: City[] | undefined = [];
    let citysList = updateCityListResponse.data;
    for (let i = 0; i < citysList.length; i++) {
        citys[i] = new City(citysList[i]);
    }
    return citys;
}

export async function callDeleteCityRestEndpointsByCityId(
    cityId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cities/v1?env=${env}&cityId=${cityId}` ;
    let deleteCityResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCityResponse.status == 204;
}

export async function callDeleteCityRestEndpointsByCityName(
    cityName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cities/v1?env=${env}&cityName=${cityName}` ;
    let deleteCityResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCityResponse.status == 204;
}
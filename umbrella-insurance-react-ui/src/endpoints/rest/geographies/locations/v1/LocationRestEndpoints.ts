import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Location } from "../../../../../models/geographies/locations/v1/Location";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateLocationRestEndpoints(
    location: Location, env: string, domain: string): Promise<Location>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/locations/v1?env=${env}`;
    let data = JSON.stringify(location as any, replacer);
    let createLocationResponse: AxiosResponse<Location> = await axios.post(url, data, config);
    let location1 = new Location(createLocationResponse.data);
    return location1;
}

export async function callReadLocationRestEndpointsByLocationId(
    locationId: number, env: string, domain: string): Promise<Location[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/locations/v1?env=${env}&locationId=${locationId}`;
    let locations: Location[] = [];
    try {
        let readLocationListResponse: AxiosResponse<Location[]> = await axios.get(url, config);
        let locationList = readLocationListResponse.data;
        for(let i = 0; i < locationList.length; i++) {
            locations[i] = new Location(locationList[i]);
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
    return locations;
}

export async function callReadLocationRestEndpointsByLocationName(
    locationName: string, env: string, domain: string): Promise<Location[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/locations/v1?env=${env}&locationName=${locationName}`;
    let locations: Location[] = [];
    try {
        let readLocationListResponse: AxiosResponse<Location[]> = await axios.get(url, config);
        let locationList = readLocationListResponse.data;
        for(let i = 0; i < locationList.length; i++) {
            locations[i] = new Location(locationList[i]);
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
    return locations;
}

export async function callReadLocationRestEndpointsByStateName(
    stateName: string, env: string, domain: string): Promise<Location[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/locations/v1?env=${env}&stateName=${stateName}`;
    let locations: Location[] = [];
    try {
        let readLocationListResponse: AxiosResponse<Location[]> = await axios.get(url, config);
        let locationList = readLocationListResponse.data;
        for(let i = 0; i < locationList.length; i++) {
            locations[i] = new Location(locationList[i]);
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
    return locations;
}

export async function callUpdateLocationRestEndpoints(
    location: Location, env: string, domain: string): Promise<Location[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/locations/v1?env=${env}`;
    let data = JSON.stringify(location as any, replacer);
    let updateLocationListResponse: AxiosResponse<Location[]> = await axios.put(url, data, config);
    let locationList = updateLocationListResponse.data;
    let locations: Location[] = [];
    for(let i = 0; i < locationList.length; i++) {
        locations[i] = new Location(locationList[i]);
    } 
    return locations;
}

export async function callDeleteLocationRestEndpointsByLocationId(
    locationId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/locations/v1?env=${env}&locationId=${locationId}` ;
    let deleteLocationResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteLocationResponse.status == 204;
}

export async function callDeleteLocationRestEndpointsByLocationName(
    locationName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/locations/v1?env=${env}&locationName=${locationName}` ;
    let deleteLocationResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteLocationResponse.status == 204;
}
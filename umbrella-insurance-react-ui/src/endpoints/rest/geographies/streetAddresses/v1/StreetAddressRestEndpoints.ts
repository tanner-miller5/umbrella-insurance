import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { StreetAddress } from "../../../../../models/geographies/streetAddresses/v1/StreetAddress";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateStreetAddressRestEndpoints(
    streetAddress: StreetAddress, env: string, domain: string): Promise<StreetAddress>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/streetAddresses/v1?env=${env}`;
    let data = JSON.stringify(streetAddress as any, replacer);
    let createStreetAddressResponse: AxiosResponse<StreetAddress> = await axios.post(url, data, config);
    let streetAddress1 = new StreetAddress(createStreetAddressResponse.data);
    return streetAddress1;
}

export async function callReadStreetAddressRestEndpointsByStreetAddressId(
    streetAddressId: number, env: string, domain: string): Promise<StreetAddress[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/streetAddresses/v1?env=${env}&streetAddressId=${streetAddressId}`;
    let streetAddresss: StreetAddress[] | undefined = [];
    try {
        let readStreetAddressListResponse: AxiosResponse<StreetAddress[]> = await axios.get(url, config);
        let streetAddressList = readStreetAddressListResponse.data;
        for(let i = 0; i < streetAddressList.length; i++) {
            streetAddresss[i] = new StreetAddress(streetAddressList[i]);
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
    return streetAddresss;
}

export async function callReadStreetAddressRestEndpointsByStreetAddressName(
    streetAddressName: string, env: string, domain: string): Promise<StreetAddress[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/streetAddresses/v1?env=${env}&streetAddressName=${streetAddressName}`;
    let streetAddresss: StreetAddress[] | undefined = [];
    try {
        let readStreetAddressListResponse: AxiosResponse<StreetAddress[]> = await axios.get(url, config);
        let streetAddressList = readStreetAddressListResponse.data;
        for(let i = 0; i < streetAddressList.length; i++) {
            streetAddresss[i] = new StreetAddress(streetAddressList[i]);
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
    return streetAddresss;
}

export async function callUpdateStreetAddressRestEndpoints(
    streetAddress: StreetAddress, env: string, domain: string): Promise<StreetAddress[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/streetAddresses/v1?env=${env}`;
    let data = JSON.stringify(streetAddress as any, replacer);
    let updateStreetAddressListResponse: AxiosResponse<StreetAddress[]> = await axios.put(url, data, config);
    let streetAddressList = updateStreetAddressListResponse.data;
    let streetAddresss: StreetAddress[] | undefined = [];
    for(let i = 0; i < streetAddressList.length; i++) {
        streetAddresss[i] = new StreetAddress(streetAddressList[i]);
    }
    return streetAddresss;
}

export async function callDeleteStreetAddressRestEndpointsByStreetAddressId(
    streetAddressId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/streetAddresses/v1?env=${env}&streetAddressId=${streetAddressId}` ;
    let deleteStreetAddressResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteStreetAddressResponse.status == 204;
}

export async function callDeleteStreetAddressRestEndpointsByStreetAddressName(
    streetAddressName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/streetAddresses/v1?env=${env}&streetAddressName=${streetAddressName}` ;
    let deleteStreetAddressResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteStreetAddressResponse.status == 204;
}
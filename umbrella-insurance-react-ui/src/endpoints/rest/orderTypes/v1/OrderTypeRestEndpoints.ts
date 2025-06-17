import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { OrderType } from "../../../../models/orderTypes/v1/OrderType";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateOrderTypeRestEndpoints(
    orderType: OrderType, env: string, domain: string): Promise<OrderType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/orderTypes/v1?env=${env}`;
    let data = JSON.stringify(orderType as any, replacer);
    let createOrderTypeResponse: AxiosResponse<OrderType> = await axios.post(url, data, config);
    let orderType1 = new OrderType(createOrderTypeResponse.data);
    return orderType1;
}

export async function callReadOrderTypeRestEndpointsByOrderTypeId(
    orderTypeId: number, env: string, domain: string): Promise<OrderType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/orderTypes/v1?env=${env}&orderTypeId=${orderTypeId}`;
    let orderTypes: OrderType[] | undefined = [];
    try {
        let readOrderTypeListResponse: AxiosResponse<OrderType[]> = await axios.get(url, config);
        let orderTypeList = readOrderTypeListResponse.data;
        for(let i = 0; i < orderTypeList.length; i++) {
            orderTypes[i] = new OrderType(orderTypeList[i]);
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
    return orderTypes;
}

export async function callReadOrderTypeRestEndpointsByOrderTypeName(
    orderTypeName: string, env: string, domain: string): Promise<OrderType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/orderTypes/v1?env=${env}&orderTypeName=${orderTypeName}`;
    let orderTypes: OrderType[] | undefined = [];
    try {
        let readOrderTypeListResponse: AxiosResponse<OrderType[]> = await axios.get(url, config);
        let orderTypeList = readOrderTypeListResponse.data;
        for(let i = 0; i < orderTypeList.length; i++) {
            orderTypes[i] = new OrderType(orderTypeList[i]);
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
    return orderTypes;
}

export async function callReadOrderTypeRestEndpoints(
    env: string, domain: string): Promise<OrderType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/orderTypes/v1?env=${env}`;
    let orderTypes: OrderType[] | undefined = [];
    try {
        let readOrderTypeListResponse: AxiosResponse<OrderType[]> = await axios.get(url, config);
        let orderTypeList = readOrderTypeListResponse.data;
        for(let i = 0; i < orderTypeList.length; i++) {
            orderTypes[i] = new OrderType(orderTypeList[i]);
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
    return orderTypes;
}

export async function callUpdateOrderTypeRestEndpoints(
    orderType: OrderType, env: string, domain: string): Promise<OrderType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/orderTypes/v1?env=${env}`;
    let data = JSON.stringify(orderType as any, replacer);
    let updateOrderTypeListResponse: AxiosResponse<OrderType[]> = await axios.put(url, data, config);
    let orderTypeList = updateOrderTypeListResponse.data;
    let orderTypes: OrderType[] | undefined = [];
    for(let i = 0; i < orderTypeList.length; i++) {
        orderTypes[i] = new OrderType(orderTypeList[i]);
    }
    return orderTypes;
}

export async function callDeleteOrderTypeRestEndpointsByOrderTypeId(
    orderTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/orderTypes/v1?env=${env}&orderTypeId=${orderTypeId}` ;
    let deleteOrderTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteOrderTypeResponse.status == 204;
}

export async function callDeleteOrderTypeRestEndpointsByOrderTypeName(
    orderTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/orderTypes/v1?env=${env}&orderTypeName=${orderTypeName}` ;
    let deleteOrderTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteOrderTypeResponse.status == 204;
}
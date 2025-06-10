import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { Item } from "../../../../models/items/v1/Item";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateItemRestEndpoints(
    item: Item, env: string, domain: string, session: string): Promise<Item>  {
    let config:AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/items/v1?env=${env}`;
    let createItemResponse: AxiosResponse<Item> = await axios.post(url, item, config);
    let item1 = createItemResponse.data;
    return item1;
}

export async function callReadItemRestEndpoints(
    env: string, domain: string, session: string): Promise<Item[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/items/v1?env=${env}`;

    let items: Item[] = [];
    try {
        let readItemListResponse: AxiosResponse<Item[]> = await axios.get(url, config);
        let itemList = readItemListResponse.data;
        for(let i = 0; i < itemList.length; i++) {
            items[i] = new Item(itemList[i]);
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
    return items;
}

export async function callReadItemRestEndpointsByItemId(
    itemId: number, env: string, domain: string, session: string): Promise<Item[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/items/v1?env=${env}&itemId=${itemId}`;

    let items: Item[] = [];
    try {
        let readItemListResponse: AxiosResponse<Item[]> = await axios.get(url, config);
        let itemList = readItemListResponse.data;
        for(let i = 0; i < itemList.length; i++) {
            items[i] = new Item(itemList[i]);
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
    return items;
}

export async function callReadItemRestEndpointsByItemName(
    itemName: string, env: string, domain: string, session: string): Promise<Item[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/items/v1?env=${env}&itemName=${itemName}`;
    let itemList: any | undefined = undefined;
    let items: Item[] = [];
    try {
        let readItemListResponse: AxiosResponse<Item[]> = await axios.get(url, config);
        itemList = readItemListResponse.data;
        for(let i = 0; i < itemList.length; i++) {
            items[i] = new Item(itemList[i]);
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
    return items;
}

export async function callUpdateItemRestEndpoints(
    item: Item, env: string, domain: string, session: string): Promise<Item[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/items/v1?env=${env}`;
    let updateItemListResponse: AxiosResponse<Item[]> = await axios.put(url, item, config);
    let itemList = updateItemListResponse.data;
    let items: Item[] = [];
    for(let i = 0; i < itemList.length; i++) {
        items[i] = new Item(itemList[i]);
    }
    return items;
}

export async function callDeleteItemRestEndpointsByItemId(
    itemId: number, env: string, domain: string, session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/items/v1?env=${env}&itemId=${itemId}` ;
    let deleteItemResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteItemResponse.status == 204;
}

export async function callDeleteItemRestEndpointsByItemName(
    itemName: string, env: string, domain: string, session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/items/v1?env=${env}&itemName=${itemName}` ;
    let deleteItemResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteItemResponse.status == 204;
}
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { CartItemRelationship } from "../../../../models/cartItemRelationships/v1/CartItemRelationship";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateCartItemRelationshipRestEndpoints(
    cartItemRelationship: CartItemRelationship, env: string, 
    domain: string, session: string): Promise<CartItemRelationship>  {
    let config:AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/cartItemRelationships/v1?env=${env}`;
    let createCartItemRelationshipResponse: AxiosResponse<CartItemRelationship> = await axios.post(url, cartItemRelationship, config);
    let cartItemRelationship1 = createCartItemRelationshipResponse.data;
    return cartItemRelationship1;
}

export async function callReadCartItemRelationshipRestEndpointsByCartItemRelationshipId(
    cartItemRelationshipId: number, env: string, domain: string, 
    session: string): Promise<CartItemRelationship[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cartItemRelationships/v1?env=${env}&cartItemRelationshipId=${cartItemRelationshipId}`;

    let cartItemRelationships: CartItemRelationship[] = [];
    try {
        let readCartItemRelationshipListResponse: AxiosResponse<CartItemRelationship[]> = await axios.get(url, config);
        let cartItemRelationshipList = readCartItemRelationshipListResponse.data;
        for(let i = 0; i < cartItemRelationshipList.length; i++) {
            cartItemRelationships[i] = new CartItemRelationship(cartItemRelationshipList[i]);
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
    return cartItemRelationships;
}

export async function callReadCartItemRelationshipRestEndpointsByCartIdAndItemId(
    cartId: number, itemId: number, env: string, 
    domain: string, session: string): Promise<CartItemRelationship[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cartItemRelationships/v1?env=${env}&cartId=${cartId}&itemId=${itemId}`;
    let cartItemRelationshipList: any | undefined = undefined;
    let cartItemRelationships: CartItemRelationship[] = [];
    try {
        let readCartItemRelationshipListResponse: AxiosResponse<CartItemRelationship[]> = await axios.get(url, config);
        cartItemRelationshipList = readCartItemRelationshipListResponse.data;
        for(let i = 0; i < cartItemRelationshipList.length; i++) {
            cartItemRelationships[i] = new CartItemRelationship(cartItemRelationshipList[i]);
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
    return cartItemRelationships;
}

export async function callUpdateCartItemRelationshipRestEndpoints(
    cartItemRelationship: CartItemRelationship, env: string, domain: string,
    session: string): Promise<CartItemRelationship[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cartItemRelationships/v1?env=${env}`;
    let updateCartItemRelationshipListResponse: AxiosResponse<CartItemRelationship[]> = await axios.put(url, cartItemRelationship, config);
    let cartItemRelationshipList = updateCartItemRelationshipListResponse.data;
    let cartItemRelationships: CartItemRelationship[] = [];
    for(let i = 0; i < cartItemRelationshipList.length; i++) {
        cartItemRelationships[i] = new CartItemRelationship(cartItemRelationshipList[i]);
    }
    return cartItemRelationships;
}

export async function callDeleteCartItemRelationshipRestEndpointsByCartItemRelationshipId(
    cartItemRelationshipId: number, env: string, domain: string,
    session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cartItemRelationships/v1?env=${env}&cartItemRelationshipId=${cartItemRelationshipId}` ;
    let deleteCartItemRelationshipResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCartItemRelationshipResponse.status == 204;
}

export async function callDeleteCartItemRelationshipRestEndpointsByCartIdAndItemId(
    cartId: number, itemId: number, env: string, domain: string,
    session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/cartItemRelationships/v1?env=${env}&cartId=${cartId}&itemId=${itemId}`;
    let deleteCartItemRelationshipResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCartItemRelationshipResponse.status == 204;
}
import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { Cart } from "../../../../models/carts/v1/Cart";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateCartRestEndpoints(
    cart: Cart, env: string, domain: string, session: string): Promise<Cart>  {
    let config:AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }
    let url = `${domain}/rest/carts/v1?env=${env}`;
    let createCartResponse: AxiosResponse<Cart> = await axios.post(url, cart, config);
    let cart1 = createCartResponse.data;
    return cart1;
}

export async function callReadCartRestEndpointsByCartId(
    cartId: number, env: string, domain: string, session: string): Promise<Cart[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/carts/v1?env=${env}&cartId=${cartId}`;

    let carts: Cart[] = [];
    try {
        let readCartListResponse: AxiosResponse<Cart[]> = await axios.get(url, config);
        let cartList = readCartListResponse.data;
        for(let i = 0; i < cartList.length; i++) {
            carts[i] = new Cart(cartList[i]);
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
    return carts;
}

export async function callReadCartRestEndpointsByCartName(
    cartName: string, env: string, domain: string, session: string): Promise<Cart[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/carts/v1?env=${env}&cartName=${cartName}`;
    let cartList: any | undefined = undefined;
    let carts: Cart[] = [];
    try {
        let readCartListResponse: AxiosResponse<Cart[]> = await axios.get(url, config);
        cartList = readCartListResponse.data;
        for(let i = 0; i < cartList.length; i++) {
            carts[i] = new Cart(cartList[i]);
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
    return carts;
}

export async function callUpdateCartRestEndpoints(
    cart: Cart, env: string, domain: string, session: string): Promise<Cart[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/carts/v1?env=${env}`;
    let updateCartListResponse: AxiosResponse<Cart[]> = await axios.put(url, cart, config);
    let cartList = updateCartListResponse.data;
    let carts: Cart[] = [];
    for(let i = 0; i < cartList.length; i++) {
        carts[i] = new Cart(cartList[i]);
    }
    return carts;
}

export async function callDeleteCartRestEndpointsByCartId(
    cartId: number, env: string, domain: string, session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/carts/v1?env=${env}&cartId=${cartId}` ;
    let deleteCartResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCartResponse.status == 204;
}

export async function callDeleteCartRestEndpointsByUserId(
    userId: number, env: string, domain: string, session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/carts/v1?env=${env}&userId=${userId}` ;
    let deleteCartResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCartResponse.status == 204;
}

export async function callDeleteCartRestEndpointsByCartName(
    cartName: string, env: string, domain: string, session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/carts/v1?env=${env}&cartName=${cartName}`;
    let deleteCartResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCartResponse.status == 204;
}
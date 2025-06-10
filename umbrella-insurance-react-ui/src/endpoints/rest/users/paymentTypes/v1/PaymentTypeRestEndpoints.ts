import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PaymentType } from "../../../../../models/users/paymentTypes/v1/PaymentType";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePaymentTypeRestEndpoints(
    paymentType: PaymentType, env: string, domain: string): Promise<PaymentType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/paymentTypes/v1?env=${env}`;
    let data = JSON.stringify(paymentType as any, replacer);
    let createPaymentTypeResponse: AxiosResponse<PaymentType> = await axios.post(url, data, config);
    let paymentType1 = new PaymentType(createPaymentTypeResponse.data);
    return paymentType1;
}

export async function callReadPaymentTypeRestEndpointsByPaymentTypeId(
    paymentTypeId: number, env: string, domain: string): Promise<PaymentType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/paymentTypes/v1?env=${env}&paymentTypeId=${paymentTypeId}`;
    let paymentTypes: PaymentType[] | undefined = [];
    try {
        let readPaymentTypeListResponse: AxiosResponse<PaymentType[]> = await axios.get(url, config);
        let paymentTypeList = readPaymentTypeListResponse.data;
        for(let i = 0; i < paymentTypeList.length; i++) {
            paymentTypes[i] = new PaymentType(paymentTypeList[i]);
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
    return paymentTypes;
}

export async function callReadPaymentTypeRestEndpointsByPaymentTypeName(
    paymentTypeName: string, env: string, domain: string): Promise<PaymentType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/paymentTypes/v1?env=${env}&paymentTypeName=${paymentTypeName}`;
    let paymentTypes: PaymentType[] | undefined = [];
    try {
        let readPaymentTypeListResponse: AxiosResponse<PaymentType[]> = await axios.get(url, config);
        let paymentTypeList = readPaymentTypeListResponse.data;
        for(let i = 0; i < paymentTypeList.length; i++) {
            paymentTypes[i] = new PaymentType(paymentTypeList[i]);
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
    return paymentTypes;
}

export async function callUpdatePaymentTypeRestEndpoints(
    paymentType: PaymentType, env: string, domain: string): Promise<PaymentType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/paymentTypes/v1?env=${env}`;
    let data = JSON.stringify(paymentType as any, replacer);
    let updatePaymentTypeListResponse: AxiosResponse<PaymentType[]> = await axios.put(url, data, config);
    let paymentTypeList = updatePaymentTypeListResponse.data;
    let paymentTypes: PaymentType[] | undefined = [];
    for(let i = 0; i < paymentTypeList.length; i++) {
        paymentTypes[i] = new PaymentType(paymentTypeList[i]);
    }
    return paymentTypes;
}

export async function callDeletePaymentTypeRestEndpointsByPaymentTypeId(
    paymentTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/paymentTypes/v1?env=${env}&paymentTypeId=${paymentTypeId}` ;
    let deletePaymentTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePaymentTypeResponse.status == 204;
}

export async function callDeletePaymentTypeRestEndpointsByPaymentTypeName(
    paymentTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/paymentTypes/v1?env=${env}&paymentTypeName=${paymentTypeName}` ;
    let deletePaymentTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deletePaymentTypeResponse.status == 204;
}
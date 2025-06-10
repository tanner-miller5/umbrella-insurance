import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { LoanPayment } from "../../../../../models/users/loanPayments/v1/LoanPayment";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateLoanPaymentRestEndpoints(
    loanPayment: LoanPayment, env: string, domain: string): Promise<LoanPayment>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/loanPayments/v1?env=${env}`;
    let data = JSON.stringify(loanPayment as any, replacer);
    let createLoanPaymentResponse: AxiosResponse<LoanPayment> = await axios.post(url, data, config);
    let loanPayment1 = new LoanPayment(createLoanPaymentResponse.data);
    return loanPayment1;
}

export async function callReadLoanPaymentRestEndpointsByLoanPaymentId(
    loanPaymentId: number, env: string, domain: string): Promise<LoanPayment[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/loanPayments/v1?env=${env}&loanPaymentId=${loanPaymentId}`;
    let loanPayments: LoanPayment[] | undefined = [];
    try {
        let readLoanPaymentListResponse: AxiosResponse<LoanPayment[]> = await axios.get(url, config);
        let loanPaymentList = readLoanPaymentListResponse.data;
        for(let i = 0; i < loanPaymentList.length; i++) {
            loanPayments[i] = new LoanPayment(loanPaymentList[i]);
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
    return loanPayments;
}

export async function callReadLoanPaymentRestEndpointsByLoanPaymentName(
    loanPaymentName: string, env: string, domain: string): Promise<LoanPayment[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/loanPayments/v1?env=${env}&loanPaymentName=${loanPaymentName}`;
    let loanPayments: LoanPayment[] | undefined = [];
    try {
        let readLoanPaymentListResponse: AxiosResponse<LoanPayment[]> = await axios.get(url, config);
        let loanPaymentList = readLoanPaymentListResponse.data;
        for(let i = 0; i < loanPaymentList.length; i++) {
            loanPayments[i] = new LoanPayment(loanPaymentList[i]);
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
    return loanPayments;
}

export async function callUpdateLoanPaymentRestEndpoints(
    loanPayment: LoanPayment, env: string, domain: string): Promise<LoanPayment[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/loanPayments/v1?env=${env}`;
    let data = JSON.stringify(loanPayment as any, replacer);
    let updateLoanPaymentListResponse: AxiosResponse<LoanPayment[]> = await axios.put(url, data, config);
    let loanPaymentList = updateLoanPaymentListResponse.data;
    let loanPayments: LoanPayment[] | undefined = [];
    for(let i = 0; i < loanPaymentList.length; i++) {
        loanPayments[i] = new LoanPayment(loanPaymentList[i]);
    }
    return loanPayments;
}

export async function callDeleteLoanPaymentRestEndpointsByLoanPaymentId(
    loanPaymentId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/loanPayments/v1?env=${env}&loanPaymentId=${loanPaymentId}` ;
    let deleteLoanPaymentResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteLoanPaymentResponse.status == 204;
}

export async function callDeleteLoanPaymentRestEndpointsByLoanPaymentName(
    loanPaymentName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/loanPayments/v1?env=${env}&loanPaymentName=${loanPaymentName}` ;
    let deleteLoanPaymentResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteLoanPaymentResponse.status == 204;
}
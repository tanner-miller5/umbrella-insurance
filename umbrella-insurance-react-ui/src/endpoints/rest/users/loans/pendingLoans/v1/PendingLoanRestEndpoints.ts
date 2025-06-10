import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { PendingLoan } from "../../../../../../models/users/loans/pendingLoans/v1/PendingLoan";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";

export async function callCreatePendingLoanRestEndpoints(
    pendingLoan: PendingLoan, env: string, domain: string): Promise<PendingLoan>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingLoans/v1?env=${env}`;
    let createPendingLoanResponse: AxiosResponse<PendingLoan> = await axios.post(url, pendingLoan, config);
    let pendingLoan1 = createPendingLoanResponse.data;
    return pendingLoan1;
}

export async function callReadPendingLoanRestEndpointsByPendingLoanId(
    pendingLoanId: number, env: string, domain: string): Promise<PendingLoan[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingLoans/v1?env=${env}&pendingLoanId=${pendingLoanId}`;
    let pendingLoan: any | undefined = undefined;
    try {
        let readPendingLoanListResponse: AxiosResponse<PendingLoan[]> = await axios.get(url, config);
        pendingLoan = readPendingLoanListResponse.data;
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
    return pendingLoan;
}

export async function callReadPendingLoanRestEndpointsByPendingLoanName(
    pendingLoanName: string, env: string, domain: string): Promise<PendingLoan[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingLoans/v1?env=${env}&pendingLoanName=${pendingLoanName}`;
    let pendingLoan: any | undefined = undefined;
    try {
        let readPendingLoanListResponse: AxiosResponse<PendingLoan[]> = await axios.get(url, config);
        pendingLoan = readPendingLoanListResponse.data;
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
    return pendingLoan;
}

export async function callUpdatePendingLoanRestEndpoints(
    pendingLoan: PendingLoan, env: string, domain: string): Promise<PendingLoan[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingLoans/v1?env=${env}`;
    let updatePendingLoanListResponse: AxiosResponse<PendingLoan[]> = await axios.put(url, pendingLoan, config);
    let pendingLoanList = updatePendingLoanListResponse.data;
    return pendingLoanList;
}

export async function callDeletePendingLoanRestEndpointsByPendingLoanId(
    pendingLoanId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingLoans/v1?env=${env}&pendingLoanId=${pendingLoanId}` ;
    let deletePendingLoanResponse: AxiosResponse<any> = await axios.delete(url, config);
    return true;
}

export async function callDeletePendingLoanRestEndpointsByPendingLoanName(
    pendingLoanName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/pendingLoans/v1?env=${env}&pendingLoanName=${pendingLoanName}` ;
    let deletePendingLoanResponse: AxiosResponse<any> = await axios.delete(url, config);
    return true;
}
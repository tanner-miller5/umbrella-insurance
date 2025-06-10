import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { MatchedLoan } from "../../../../../../models/users/loans/matchedLoans/v1/MatchedLoan";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";


export async function callCreateMatchedLoanRestEndpoints(
    matchedLoan: MatchedLoan, env: string, domain: string): Promise<MatchedLoan>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedLoans/v1?env=${env}`;
    let createMatchedLoanResponse: AxiosResponse<MatchedLoan> = await axios.post(url, matchedLoan, config);
    let matchedLoan1 = createMatchedLoanResponse.data;
    return matchedLoan1;
}

export async function callReadMatchedLoanRestEndpointsByMatchedLoanId(
    matchedLoanId: number, env: string, domain: string): Promise<MatchedLoan[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedLoans/v1?env=${env}&matchedLoanId=${matchedLoanId}`;
    let matchedLoan: any | undefined = undefined;
    try {
        let readMatchedLoanListResponse: AxiosResponse<MatchedLoan[]> = await axios.get(url, config);
        matchedLoan = readMatchedLoanListResponse.data;
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
    return matchedLoan;
}

export async function callReadMatchedLoanRestEndpointsByMatchedLoanName(
    matchedLoanName: string, env: string, domain: string): Promise<MatchedLoan[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedLoans/v1?env=${env}&matchedLoanName=${matchedLoanName}`;
    let matchedLoan: any | undefined = undefined;
    try {
        let readMatchedLoanListResponse: AxiosResponse<MatchedLoan[]> = await axios.get(url, config);
        matchedLoan = readMatchedLoanListResponse.data;
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
    return matchedLoan;
}

export async function callUpdateMatchedLoanRestEndpoints(
    matchedLoan: MatchedLoan, matchedLoanId: number, env: string, domain: string): Promise<MatchedLoan[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedLoans/v1?env=${env}&matchedLoanId=${matchedLoanId}`;
    let updateMatchedLoanListResponse: AxiosResponse<MatchedLoan[]> = await axios.put(url, matchedLoan, config);
    let matchedLoanList = updateMatchedLoanListResponse.data;
    return matchedLoanList;
}

export async function callDeleteMatchedLoanRestEndpointsByMatchedLoanId(
    matchedLoanId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedLoans/v1?env=${env}&matchedLoanId=${matchedLoanId}` ;
    let deleteMatchedLoanResponse: AxiosResponse<any> = await axios.delete(url, config);
    return true;
}

export async function callDeleteMatchedLoanRestEndpointsByMatchedLoanName(
    matchedLoanName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/matchedLoans/v1?env=${env}&matchedLoanName=${matchedLoanName}` ;
    let deleteMatchedLoanResponse: AxiosResponse<any> = await axios.delete(url, config);
    return true;
}
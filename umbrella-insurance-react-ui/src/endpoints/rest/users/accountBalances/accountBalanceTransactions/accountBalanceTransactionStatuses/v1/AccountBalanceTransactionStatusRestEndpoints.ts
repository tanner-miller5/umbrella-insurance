import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { AccountBalanceTransactionStatus } from "../../../../../../../models/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionStatuses/v1/AccountBalanceTransactionStatus";
import { replacer } from "../../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../../logging/v1/LoggingRestEndpoints";

export async function callCreateAccountBalanceTransactionStatusRestEndpoints(
    accountBalanceTransactionStatus: AccountBalanceTransactionStatus, env: string, domain: string): Promise<AccountBalanceTransactionStatus>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionStatuses/v1?env=${env}`;
    let data = JSON.stringify(accountBalanceTransactionStatus as any, replacer);
    let createAccountBalanceTransactionStatusResponse: AxiosResponse<AccountBalanceTransactionStatus> = await axios.post(url, data, config);
    let accountBalanceTransactionStatus1 = new AccountBalanceTransactionStatus(createAccountBalanceTransactionStatusResponse.data);
    return accountBalanceTransactionStatus1;
}

export async function callReadAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusId(
    accountBalanceTransactionStatusId: number, env: string, domain: string): Promise<AccountBalanceTransactionStatus[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionStatuses/v1?env=${env}&accountBalanceTransactionStatusId=${accountBalanceTransactionStatusId}`;
    let accountBalanceTransactionStatuss: AccountBalanceTransactionStatus[] | undefined = [];
    try {
        let readAccountBalanceTransactionStatusListResponse: AxiosResponse<AccountBalanceTransactionStatus[]> = await axios.get(url, config);
        let accountBalanceTransactionStatusList = readAccountBalanceTransactionStatusListResponse.data;
        for(let i = 0; i < accountBalanceTransactionStatusList.length; i++) {
            accountBalanceTransactionStatuss[i] = new AccountBalanceTransactionStatus(accountBalanceTransactionStatusList[i]);
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
    return accountBalanceTransactionStatuss;
}

export async function callReadAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusName(
    accountBalanceTransactionStatusName: string, env: string, domain: string): Promise<AccountBalanceTransactionStatus[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionStatuses/v1?env=${env}&accountBalanceTransactionStatusName=${accountBalanceTransactionStatusName}`;
    let accountBalanceTransactionStatuss: AccountBalanceTransactionStatus[] | undefined = [];
    try {
        let readAccountBalanceTransactionStatusListResponse: AxiosResponse<AccountBalanceTransactionStatus[]> = await axios.get(url, config);
        let accountBalanceTransactionStatusList = readAccountBalanceTransactionStatusListResponse.data;
        for(let i = 0; i < accountBalanceTransactionStatusList.length; i++) {
            accountBalanceTransactionStatuss[i] = new AccountBalanceTransactionStatus(accountBalanceTransactionStatusList[i]);
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
    return accountBalanceTransactionStatuss;
}

export async function callUpdateAccountBalanceTransactionStatusRestEndpoints(
    accountBalanceTransactionStatus: AccountBalanceTransactionStatus, 
    env: string, domain: string): Promise<AccountBalanceTransactionStatus[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionStatuses/v1?env=${env}`;
    let data = JSON.stringify(accountBalanceTransactionStatus as any, replacer);
    let updateAccountBalanceTransactionStatusListResponse: AxiosResponse<AccountBalanceTransactionStatus[]> = await axios.put(url, data, config);
    let accountBalanceTransactionStatusList = updateAccountBalanceTransactionStatusListResponse.data;
    let accountBalanceTransactionStatuss: AccountBalanceTransactionStatus[] | undefined = [];
    for(let i = 0; i < accountBalanceTransactionStatusList.length; i++) {
        accountBalanceTransactionStatuss[i] = new AccountBalanceTransactionStatus(accountBalanceTransactionStatusList[i]);
    }
    return accountBalanceTransactionStatuss;
}

export async function callDeleteAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusId(
    accountBalanceTransactionStatusId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionStatuses/v1?env=${env}&accountBalanceTransactionStatusId=${accountBalanceTransactionStatusId}` ;
    let deleteAccountBalanceTransactionStatusResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceTransactionStatusResponse.status == 204;
}

export async function callDeleteAccountBalanceTransactionStatusRestEndpointsByAccountBalanceTransactionStatusName(
    accountBalanceTransactionStatusName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionStatuses/v1?env=${env}&accountBalanceTransactionStatusName=${accountBalanceTransactionStatusName}` ;
    let deleteAccountBalanceTransactionStatusResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceTransactionStatusResponse.status == 204;
}
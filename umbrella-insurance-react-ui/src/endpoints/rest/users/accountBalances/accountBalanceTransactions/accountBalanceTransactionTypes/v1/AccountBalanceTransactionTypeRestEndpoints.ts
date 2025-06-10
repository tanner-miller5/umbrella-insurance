import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { AccountBalanceTransactionType } from "../../../../../../../models/users/accountBalances/accountBalanceTransactions/accountBalanceTransactionTypes/v1/AccountBalanceTransactionType";
import { replacer } from "../../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../../logging/v1/LoggingRestEndpoints";


export async function callCreateAccountBalanceTransactionTypeRestEndpoints(
    accountBalanceTransactionType: AccountBalanceTransactionType, env: string, domain: string): Promise<AccountBalanceTransactionType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionTypes/v1?env=${env}`;
    let data = JSON.stringify(accountBalanceTransactionType as any, replacer);
    let createAccountBalanceTransactionTypeResponse: AxiosResponse<AccountBalanceTransactionType> = await axios.post(url, data, config);
    let accountBalanceTransactionType1 = new AccountBalanceTransactionType(createAccountBalanceTransactionTypeResponse.data);
    return accountBalanceTransactionType1;
}

export async function callReadAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeId(
    accountBalanceTransactionTypeId: number, env: string, domain: string): Promise<AccountBalanceTransactionType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionTypes/v1?env=${env}&accountBalanceTransactionTypeId=${accountBalanceTransactionTypeId}`;
    let accountBalanceTransactionTypes: AccountBalanceTransactionType[] | undefined = [];
    try {
        let readAccountBalanceTransactionTypeListResponse: AxiosResponse<AccountBalanceTransactionType[]> = await axios.get(url, config);
        let accountBalanceTransactionTypeList = readAccountBalanceTransactionTypeListResponse.data;
        for(let i = 0; i < accountBalanceTransactionTypeList.length; i++) {
            accountBalanceTransactionTypes[i] = new AccountBalanceTransactionType(accountBalanceTransactionTypeList[i]);
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
    return accountBalanceTransactionTypes;
}

export async function callReadAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeName(
    accountBalanceTransactionTypeName: string, env: string, domain: string): Promise<AccountBalanceTransactionType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionTypes/v1?env=${env}&accountBalanceTransactionTypeName=${accountBalanceTransactionTypeName}`;
    let accountBalanceTransactionTypes: AccountBalanceTransactionType[] | undefined = [];
    try {
        let readAccountBalanceTransactionTypeListResponse: AxiosResponse<AccountBalanceTransactionType[]> = await axios.get(url, config);
        let accountBalanceTransactionTypeList = readAccountBalanceTransactionTypeListResponse.data;
        for(let i = 0; i < accountBalanceTransactionTypeList.length; i++) {
            accountBalanceTransactionTypes[i] = new AccountBalanceTransactionType(accountBalanceTransactionTypeList[i]);
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
    return accountBalanceTransactionTypes;
}

export async function callUpdateAccountBalanceTransactionTypeRestEndpoints(
    accountBalanceTransactionType: AccountBalanceTransactionType, 
    env: string, domain: string): Promise<AccountBalanceTransactionType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionTypes/v1?env=${env}`;
    let data = JSON.stringify(accountBalanceTransactionType as any, replacer);
    let updateAccountBalanceTransactionTypeListResponse: AxiosResponse<AccountBalanceTransactionType[]> = await axios.put(url, data, config);
    let accountBalanceTransactionTypeList = updateAccountBalanceTransactionTypeListResponse.data;
    let accountBalanceTransactionTypes: AccountBalanceTransactionType[] | undefined = [];
    for(let i = 0; i < accountBalanceTransactionTypeList.length; i++) {
        accountBalanceTransactionTypes[i] = new AccountBalanceTransactionType(accountBalanceTransactionTypeList[i]);
    }
    return accountBalanceTransactionTypes;
}

export async function callDeleteAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeId(
    accountBalanceTransactionTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionTypes/v1?env=${env}&accountBalanceTransactionTypeId=${accountBalanceTransactionTypeId}` ;
    let deleteAccountBalanceTransactionTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceTransactionTypeResponse.status == 204;
}

export async function callDeleteAccountBalanceTransactionTypeRestEndpointsByAccountBalanceTransactionTypeName(
    accountBalanceTransactionTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactionTypes/v1?env=${env}&accountBalanceTransactionTypeName=${accountBalanceTransactionTypeName}` ;
    let deleteAccountBalanceTransactionTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceTransactionTypeResponse.status == 204;
}
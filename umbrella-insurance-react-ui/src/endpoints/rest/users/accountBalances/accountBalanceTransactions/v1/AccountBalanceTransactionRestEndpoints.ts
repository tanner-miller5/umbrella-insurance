import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { AccountBalanceTransaction } from "../../../../../../models/users/accountBalances/accountBalanceTransactions/v1/AccountBalanceTransaction";
import { replacer } from "../../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../../logging/v1/LoggingRestEndpoints";


export async function callCreateAccountBalanceTransactionRestEndpoints(
    session: string,
    accountBalanceTransaction: AccountBalanceTransaction, 
    env: string, domain: string): Promise<AccountBalanceTransaction>  {
    let config:AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactions/v1?env=${env}`;
    let data = JSON.stringify(accountBalanceTransaction as any, replacer);
    let createAccountBalanceTransactionResponse: AxiosResponse<AccountBalanceTransaction> = await axios.post(url, data, config);
    let accountBalanceTransaction1 = new AccountBalanceTransaction(createAccountBalanceTransactionResponse.data);
    return accountBalanceTransaction1;
}

export async function callReadAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionId(
    session: string,
    accountBalanceTransactionId: number, env: string, domain: string): Promise<AccountBalanceTransaction[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactions/v1?env=${env}&accountBalanceTransactionId=${accountBalanceTransactionId}`;
    let accountBalanceTransactions: AccountBalanceTransaction[] | undefined = [];
    try {
        let readAccountBalanceTransactionListResponse: AxiosResponse<AccountBalanceTransaction[]> = await axios.get(url, config);
        let accountBalanceTransactionList = readAccountBalanceTransactionListResponse.data;
        for(let i = 0; i < accountBalanceTransactionList.length; i++) {
            accountBalanceTransactions[i] = new AccountBalanceTransaction(accountBalanceTransactionList[i]);
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
    return accountBalanceTransactions;
}

export async function callReadAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionName(
    session: string,
    accountBalanceTransactionName: string, env: string, domain: string): Promise<AccountBalanceTransaction[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactions/v1?env=${env}&accountBalanceTransactionName=${accountBalanceTransactionName}`;
    let accountBalanceTransactions: AccountBalanceTransaction[] | undefined = [];
    try {
        let readAccountBalanceTransactionListResponse: AxiosResponse<AccountBalanceTransaction[]> = await axios.get(url, config);
        let accountBalanceTransactionList = readAccountBalanceTransactionListResponse.data;
        for(let i = 0; i < accountBalanceTransactionList.length; i++) {
            accountBalanceTransactions[i] = new AccountBalanceTransaction(accountBalanceTransactionList[i]);
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
    return accountBalanceTransactions;
}

export async function callUpdateAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionId(
    accountBalanceTransaction: AccountBalanceTransaction, accountBalanceTransactionId: number, env: string, domain: string): Promise<AccountBalanceTransaction[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactions/v1?env=${env}&accountBalanceTransactionId=${accountBalanceTransactionId}`;
    let data = JSON.stringify(accountBalanceTransaction as any, replacer);
    let updateAccountBalanceTransactionListResponse: AxiosResponse<AccountBalanceTransaction[]> = await axios.put(url, data, config);
    let accountBalanceTransactionList = updateAccountBalanceTransactionListResponse.data;
    let accountBalanceTransactions: AccountBalanceTransaction[] | undefined = [];
    for(let i = 0; i < accountBalanceTransactionList.length; i++) {
        accountBalanceTransactions[i] = new AccountBalanceTransaction(accountBalanceTransactionList[i]);
    }
    return accountBalanceTransactions;
}

export async function callUpdateAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionName(
    accountBalanceTransaction: AccountBalanceTransaction, accountBalanceTransactionName: string, env: string, domain: string): Promise<AccountBalanceTransaction[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactions/v1?env=${env}&accountBalanceTransactionName=${accountBalanceTransactionName}`;
    let data = JSON.stringify(accountBalanceTransaction as any, replacer);
    let updateAccountBalanceTransactionListResponse: AxiosResponse<AccountBalanceTransaction[]> = await axios.put(url, data, config);
    let accountBalanceTransactionList = updateAccountBalanceTransactionListResponse.data;
    let accountBalanceTransactions: AccountBalanceTransaction[] | undefined = [];
    for(let i = 0; i < accountBalanceTransactionList.length; i++) {
        accountBalanceTransactions[i] = new AccountBalanceTransaction(accountBalanceTransactionList[i]);
    }
    return accountBalanceTransactions;
}

export async function callDeleteAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionId(
    accountBalanceTransactionId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactions/v1?env=${env}&accountBalanceTransactionId=${accountBalanceTransactionId}` ;
    let deleteAccountBalanceTransactionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceTransactionResponse.status == 204;
}

export async function callDeleteAccountBalanceTransactionRestEndpointsByAccountBalanceTransactionName(
    accountBalanceTransactionName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTransactions/v1?env=${env}&accountBalanceTransactionName=${accountBalanceTransactionName}` ;
    let deleteAccountBalanceTransactionResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceTransactionResponse.status == 204;
}
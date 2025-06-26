import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { AccountBalance } from "../../../../../models/users/accountBalances/v1/AccountBalance";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";


export async function callCreateAccountBalanceRestEndpoints(
    accountBalance: AccountBalance, env: string, domain: string): Promise<AccountBalance>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalances/v1?env=${env}`;
    let data = JSON.stringify(accountBalance as any, replacer);
    let createAccountBalanceResponse: AxiosResponse<AccountBalance> = await axios.post(url, data, config);
    let accountBalance1 = new AccountBalance(createAccountBalanceResponse.data);
    return accountBalance1;
}

export async function callReadAccountBalanceRestEndpointsByAccountBalanceId(
    session: string,
    accountBalanceId: number, env: string, domain: string): Promise<AccountBalance[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalances/v1?env=${env}&accountBalanceId=${accountBalanceId}`;
    let accountBalances: AccountBalance[] | undefined = [];
    try {
        let readAccountBalanceListResponse: AxiosResponse<AccountBalance[]> = await axios.get(url, config);
        let accountBalanceList = readAccountBalanceListResponse.data;
        for(let i = 0; i < accountBalanceList.length; i++) {
            accountBalances[i] = new AccountBalance(accountBalanceList[i]);
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
    return accountBalances;
}

export async function callReadAccountBalanceRestEndpointsByUserId(
    session: string,
    userId: number, env: string, domain: string): Promise<AccountBalance[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalances/v1?env=${env}&userId=${userId}`;
    let accountBalances: AccountBalance[] | undefined = [];
    try {
        let readAccountBalanceListResponse: AxiosResponse<AccountBalance[]> = await axios.get(url, config);
        let accountBalanceList = readAccountBalanceListResponse.data;
        for(let i = 0; i < accountBalanceList.length; i++) {
            accountBalances[i] = new AccountBalance(accountBalanceList[i]);
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
    return accountBalances;
}

export async function callReadAccountBalanceRestEndpointsByAccountBalanceName(
    session: string,
    accountBalanceName: string, env: string, domain: string): Promise<AccountBalance[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalances/v1?env=${env}&accountBalanceName=${accountBalanceName}`;
    let accountBalances: AccountBalance[] | undefined = [];
    try {
        let readAccountBalanceListResponse: AxiosResponse<AccountBalance[]> = await axios.get(url, config);
        let accountBalanceList = readAccountBalanceListResponse.data;
        for(let i = 0; i < accountBalanceList.length; i++) {
            accountBalances[i] = new AccountBalance(accountBalanceList[i]);
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
    return accountBalances;
}

export async function callUpdateAccountBalanceRestEndpoints(
    accountBalance: AccountBalance, env: string, domain: string): Promise<AccountBalance[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalances/v1?env=${env}`;
    let data = JSON.stringify(accountBalance as any, replacer);
    let updateAccountBalanceListResponse: AxiosResponse<AccountBalance[]> = await axios.put(url, data, config);
    let accountBalanceList = updateAccountBalanceListResponse.data;
    let accountBalances: AccountBalance[] | undefined = [];
    for(let i = 0; i < accountBalanceList.length; i++) {
        accountBalances[i] = new AccountBalance(accountBalanceList[i]);
    }
    return accountBalances;
}

export async function callDeleteAccountBalanceRestEndpointsByAccountBalanceId(
    accountBalanceId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalances/v1?env=${env}&accountBalanceId=${accountBalanceId}` ;
    let deleteAccountBalanceResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceResponse.status == 204;
}

export async function callDeleteAccountBalanceRestEndpointsByAccountBalanceName(
    accountBalanceName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalances/v1?env=${env}&accountBalanceName=${accountBalanceName}` ;
    let deleteAccountBalanceResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceResponse.status == 204;
}
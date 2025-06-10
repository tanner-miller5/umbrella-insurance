import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { AccountBalanceType } from "../../../../../models/users/accountBalances/accountBalanceTypes/v1/AccountBalanceType";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateAccountBalanceTypeRestEndpoints(
    accountBalanceType: AccountBalanceType, env: string, domain: string): Promise<AccountBalanceType>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTypes/v1?env=${env}`;
    let data = JSON.stringify(accountBalanceType as any, replacer);
    let createAccountBalanceTypeResponse: AxiosResponse<AccountBalanceType> = await axios.post(url, data, config);
    let accountBalanceType1 = new AccountBalanceType(createAccountBalanceTypeResponse.data);
    return accountBalanceType1;
}

export async function callReadAccountBalanceTypeRestEndpointsByAccountBalanceTypeId(
    accountBalanceTypeId: number, env: string, domain: string): Promise<AccountBalanceType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTypes/v1?env=${env}&accountBalanceTypeId=${accountBalanceTypeId}`;
    let accountBalanceTypes: AccountBalanceType[] | undefined = [];
    try {
        let readAccountBalanceTypeListResponse: AxiosResponse<AccountBalanceType[]> = await axios.get(url, config);
        let accountBalanceTypeList = readAccountBalanceTypeListResponse.data;
        for(let i = 0; i < accountBalanceTypeList.length; i++) {
            accountBalanceTypes[i] = new AccountBalanceType(accountBalanceTypeList[i]);
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
    return accountBalanceTypes;
}

export async function callReadAccountBalanceTypeRestEndpointsByAccountBalanceTypeName(
    accountBalanceTypeName: string, env: string, domain: string): Promise<AccountBalanceType[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTypes/v1?env=${env}&accountBalanceTypeName=${accountBalanceTypeName}`;
    let accountBalanceTypes: AccountBalanceType[] | undefined = [];
    try {
        let readAccountBalanceTypeListResponse: AxiosResponse<AccountBalanceType[]> = await axios.get(url, config);
        let accountBalanceTypeList = readAccountBalanceTypeListResponse.data;
        for(let i = 0; i < accountBalanceTypeList.length; i++) {
            accountBalanceTypes[i] = new AccountBalanceType(accountBalanceTypeList[i]);
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
    return accountBalanceTypes;
}

export async function callUpdateAccountBalanceTypeRestEndpoints(
    accountBalanceType: AccountBalanceType, env: string, domain: string): Promise<AccountBalanceType[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTypes/v1?env=${env}`;
    let data = JSON.stringify(accountBalanceType as any, replacer);
    let updateAccountBalanceTypeListResponse: AxiosResponse<AccountBalanceType[]> = await axios.put(url, data, config);
    let accountBalanceTypeList = updateAccountBalanceTypeListResponse.data;
    let accountBalanceTypes: AccountBalanceType[] | undefined = [];
    for(let i = 0; i < accountBalanceTypeList.length; i++) {
        accountBalanceTypes[i] = new AccountBalanceType(accountBalanceTypeList[i]);
    }
    return accountBalanceTypes;
}

export async function callDeleteAccountBalanceTypeRestEndpointsByAccountBalanceTypeId(
    accountBalanceTypeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTypes/v1?env=${env}&accountBalanceTypeId=${accountBalanceTypeId}` ;
    let deleteAccountBalanceTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceTypeResponse.status == 204;
}

export async function callDeleteAccountBalanceTypeRestEndpointsByAccountBalanceTypeName(
    accountBalanceTypeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/accountBalanceTypes/v1?env=${env}&accountBalanceTypeName=${accountBalanceTypeName}` ;
    let deleteAccountBalanceTypeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteAccountBalanceTypeResponse.status == 204;
}
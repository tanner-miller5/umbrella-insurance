import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { BankAccount } from "../../../../../models/users/bankAccounts/v1/BankAccount";
import { replacer } from "../../sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";


export async function callCreateBankAccountRestEndpoints(
    bankAccount: BankAccount, env: string, domain: string): Promise<BankAccount>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bankAccounts/v1?env=${env}`;
    let data = JSON.stringify(bankAccount as any, replacer);
    let createBankAccountResponse: AxiosResponse<BankAccount> = await axios.post(url, data, config);
    let bankAccount1 = new BankAccount(createBankAccountResponse.data);
    return bankAccount1;
}

export async function callReadBankAccountRestEndpointsByBankAccountId(
    bankAccountId: number, env: string, domain: string): Promise<BankAccount[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bankAccounts/v1?env=${env}&bankAccountId=${bankAccountId}`;
    let bankAccounts: BankAccount[] | undefined = [];
    try {
        let readBankAccountListResponse: AxiosResponse<BankAccount[]> = await axios.get(url, config);
        let bankAccountList = readBankAccountListResponse.data;
        for(let i = 0; i < bankAccountList.length; i++) {
            bankAccounts[i] = new BankAccount(bankAccountList[i]);
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
    return bankAccounts;
}

export async function callReadBankAccountRestEndpointsByBankAccountName(
    bankAccountName: string, env: string, domain: string): Promise<BankAccount[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bankAccounts/v1?env=${env}&bankAccountName=${bankAccountName}`;
    let bankAccounts: BankAccount[] | undefined = [];
    try {
        let readBankAccountListResponse: AxiosResponse<BankAccount[]> = await axios.get(url, config);
        let bankAccountList = readBankAccountListResponse.data;
        for(let i = 0; i < bankAccountList.length; i++) {
            bankAccounts[i] = new BankAccount(bankAccountList[i]);
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
    return bankAccounts;
}

export async function callUpdateBankAccountRestEndpoints(
    bankAccount: BankAccount, env: string, domain: string): Promise<BankAccount[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bankAccounts/v1?env=${env}`;
    let data = JSON.stringify(bankAccount as any, replacer);
    let updateBankAccountListResponse: AxiosResponse<BankAccount[]> = await axios.put(url, data, config);
    let bankAccountList = updateBankAccountListResponse.data;
    let bankAccounts: BankAccount[] | undefined = [];
    for(let i = 0; i < bankAccountList.length; i++) {
        bankAccounts[i] = new BankAccount(bankAccountList[i]);
    }
    return bankAccounts;
}

export async function callDeleteBankAccountRestEndpointsByBankAccountId(
    bankAccountId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bankAccounts/v1?env=${env}&bankAccountId=${bankAccountId}` ;
    let deleteBankAccountResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteBankAccountResponse.status == 204;
}

export async function callDeleteBankAccountRestEndpointsByBankAccountName(
    bankAccountName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/bankAccounts/v1?env=${env}&bankAccountName=${bankAccountName}` ;
    let deleteBankAccountResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteBankAccountResponse.status == 204;
}
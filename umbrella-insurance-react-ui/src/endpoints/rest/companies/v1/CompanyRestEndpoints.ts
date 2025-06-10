import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Company } from "../../../../models/companies/v1/Company";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateCompanyRestEndpoints(
    company: Company, env: string, domain: string): Promise<Company>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/companies/v1?env=${env}`;
    let data = JSON.stringify(company as any, replacer);
    let createCompanyResponse: AxiosResponse<Company> = await axios.post(url, data, config);
    let company1 = new Company(createCompanyResponse.data);
    return company1;
}

export async function callReadCompanyRestEndpointsByCompanyId(
    companyId: number, env: string, domain: string): Promise<Company[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/companies/v1?env=${env}&companyId=${companyId}`;
    let companys: Company[] | undefined = [];
    try {
        let readCompanyListResponse: AxiosResponse<Company[]> = await axios.get(url, config);
        let companyList = readCompanyListResponse.data;
        for(let i = 0; i < companyList.length; i++) {
            companys[i] = new Company(companyList[i]);
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
    return companys;
}

export async function callReadCompanyRestEndpointsByCompanyName(
    companyName: string, env: string, domain: string): Promise<Company[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/companies/v1?env=${env}&companyName=${companyName}`;
    let companys: Company[] | undefined = [];
    try {
        let readCompanyListResponse: AxiosResponse<Company[]> = await axios.get(url, config);
        let companyList = readCompanyListResponse.data;
        for(let i = 0; i < companyList.length; i++) {
            companys[i] = new Company(companyList[i]);
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
    return companys;
}

export async function callUpdateCompanyRestEndpoints(
    company: Company, env: string, domain: string): Promise<Company[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/companies/v1?env=${env}`;
    let data = JSON.stringify(company as any, replacer);
    let updateCompanyListResponse: AxiosResponse<Company[]> = await axios.put(url, data, config);
    let companys: Company[] | undefined = [];
    let companyList = updateCompanyListResponse.data;
    for(let i = 0; i < companyList.length; i++) {
        companys[i] = new Company(companyList[i]);
    }
    return companys;
}

export async function callDeleteCompanyRestEndpointsByCompanyId(
    companyId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/companies/v1?env=${env}&companyId=${companyId}` ;
    let deleteCompanyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCompanyResponse.status == 204;
}

export async function callDeleteCompanyRestEndpointsByCompanyName(
    companyName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/companies/v1?env=${env}&companyName=${companyName}` ;
    let deleteCompanyResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteCompanyResponse.status == 204;
}
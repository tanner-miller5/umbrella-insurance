import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Employee } from "../../../../../models/people/employees/v1/Employee";
import { replacer } from "../../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../../logging/v1/LoggingRestEndpoints";

export async function callCreateEmployeeRestEndpoints(
    employee: Employee, env: string, domain: string): Promise<Employee>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/employees/v1?env=${env}`;
    let data = JSON.stringify(employee as any, replacer);
    let createEmployeeResponse: AxiosResponse<Employee> = await axios.post(url, data, config);
    let employee1 = new Employee(createEmployeeResponse.data);
    return employee1;
}

export async function callReadEmployeeRestEndpointsByEmployeeId(
    employeeId: number, env: string, domain: string): Promise<Employee[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/employees/v1?env=${env}&employeeId=${employeeId}`;
    let employees: any | undefined = [];
    try {
        let readEmployeeListResponse: AxiosResponse<Employee[]> = await axios.get(url, config);
        let employeesList = readEmployeeListResponse.data;
        for (let i = 0; i < employeesList.length; i++) {
            employees[i] = new Employee(employeesList[i]);
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
    return employees;
}

export async function callReadEmployeeRestEndpointsByEmployeeName(
    employeeName: string, env: string, domain: string): Promise<Employee[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/employees/v1?env=${env}&employeeName=${employeeName}`;
    let employees: Employee[] | undefined = [];
    try {
        let readEmployeeListResponse: AxiosResponse<Employee[]> = await axios.get(url, config);
        let employeesList = readEmployeeListResponse.data;
        for (let i = 0; i < employeesList.length; i++) {
            employees[i] = new Employee(employeesList[i]);
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
    return employees;
}

export async function callUpdateEmployeeRestEndpoints(
    employee: Employee, env: string, domain: string): Promise<Employee[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/employees/v1?env=${env}`;
    let data = JSON.stringify(employee as any, replacer);
    let updateEmployeeListResponse: AxiosResponse<Employee[]> = await axios.put(url, data, config);
    let employeesList = updateEmployeeListResponse.data;
    let employees: Employee[] | undefined = [];
    for (let i = 0; i < employeesList.length; i++) {
        employees[i] = new Employee(employeesList[i]);
    }
    return employees;
}

export async function callDeleteEmployeeRestEndpointsByEmployeeId(
    employeeId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/employees/v1?env=${env}&employeeId=${employeeId}` ;
    let deleteEmployeeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEmployeeResponse.status == 204;
}

export async function callDeleteEmployeeRestEndpointsByEmployeeName(
    employeeName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/employees/v1?env=${env}&employeeName=${employeeName}` ;
    let deleteEmployeeResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEmployeeResponse.status == 204;
}
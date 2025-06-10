import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Device } from "../../../../models/devices/v1/Device";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateDeviceRestEndpoints(
    device: Device, env: string, domain: string): Promise<Device>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/devices/v1?env=${env}`;
    let data = JSON.stringify(device as any, replacer);
    let createDeviceResponse: AxiosResponse<Device> = await axios.post(url, data, config);
    let device1 = new Device(createDeviceResponse.data);
    return device1;
}

export async function callReadDeviceRestEndpointsByDeviceId(
    deviceId: number, env: string, domain: string): Promise<Device[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/devices/v1?env=${env}&deviceId=${deviceId}`;
    let devicesList: any | undefined = undefined;
    let devices: Device[] = [];
    try {
        let readDeviceListResponse: AxiosResponse<Device[]> = await axios.get(url, config);
        devicesList = readDeviceListResponse.data;
        for(let i = 0; i < devicesList.length; i++) {
            devices[i] = new Device(devicesList[i]);
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
    return devices;
}

export async function callReadDeviceRestEndpointsByDeviceName(
    deviceName: string, env: string, domain: string): Promise<Device[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/devices/v1?env=${env}&deviceName=${deviceName}`;
    let devicesList: any | undefined = undefined;
    let devices: Device[] = [];
    try {
        let readDeviceListResponse: AxiosResponse<Device[]> = await axios.get(url, config);
        devicesList = readDeviceListResponse.data;
        for(let i = 0; i < devicesList.length; i++) {
            devices[i] = new Device(devicesList[i]);
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
    return devices;
}

export async function callUpdateDeviceRestEndpoints(
    device: Device, env: string, domain: string): Promise<Device[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/devices/v1?env=${env}`;
    let data = JSON.stringify(device as any, replacer);
    let updateDeviceListResponse: AxiosResponse<Device[]> = await axios.put(url, data, config);
    let devicesList = updateDeviceListResponse.data;
    let devices: Device[] = [];
    for(let i = 0; i < devicesList.length; i++) {
        devices[i] = new Device(devicesList[i]);
    }
    return devices;
}

export async function callDeleteDeviceRestEndpointsByDeviceId(
    deviceId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/devices/v1?env=${env}&deviceId=${deviceId}` ;
    let deleteDeviceResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteDeviceResponse.status == 204;
}

export async function callDeleteDeviceRestEndpointsByDeviceName(
    deviceName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/devices/v1?env=${env}&deviceName=${deviceName}` ;
    let deleteDeviceResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteDeviceResponse.status == 204;
}
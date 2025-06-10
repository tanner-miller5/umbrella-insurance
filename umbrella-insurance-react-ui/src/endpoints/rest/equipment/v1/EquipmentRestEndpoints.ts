import axios, { AxiosRequestConfig, AxiosResponse } from "axios";


import { Equipment } from "../../../../models/equipment/v1/Equipment";
import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateEquipmentRestEndpoints(
    equipment: Equipment, env: string, domain: string): Promise<Equipment>  {
    let config:AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/equipment/v1?env=${env}`;
    let data = JSON.stringify(equipment as any, replacer);
    let createEquipmentResponse: AxiosResponse<Equipment> = await axios.post(url, data, config);
    let equipment1 = new Equipment(createEquipmentResponse.data);
    return equipment1;
}

export async function callReadEquipmentRestEndpointsByEquipmentId(
    equipmentId: number, env: string, domain: string): Promise<Equipment[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/equipment/v1?env=${env}&equipmentId=${equipmentId}`;
    let equipments: Equipment[] | undefined = [];
    try {
        let readEquipmentListResponse: AxiosResponse<Equipment[]> = await axios.get(url, config);
        let equipmentsList = readEquipmentListResponse.data;
        for(let i = 0; i < equipmentsList.length; i++) {
            equipments[i] = new Equipment(equipmentsList[i]);
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
    return equipments;
}

export async function callReadEquipmentRestEndpointsByEquipmentName(
    equipmentName: string, env: string, domain: string): Promise<Equipment[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/equipment/v1?env=${env}&equipmentName=${equipmentName}`;
    let equipments: Equipment[] | undefined = [];
    try {
        let readEquipmentListResponse: AxiosResponse<Equipment[]> = await axios.get(url, config);
        let equipmentsList = readEquipmentListResponse.data;
        for(let i = 0; i < equipmentsList.length; i++) {
            equipments[i] = new Equipment(equipmentsList[i]);
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
    return equipments;
}

export async function callUpdateEquipmentRestEndpoints(
    equipment: Equipment, env: string, domain: string): Promise<Equipment[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/equipment/v1?env=${env}`;
    let data = JSON.stringify(equipment as any, replacer);
    let updateEquipmentListResponse: AxiosResponse<Equipment[]> = await axios.put(url, data, config);
    let equipmentsList = updateEquipmentListResponse.data;
    let equipments: Equipment[] | undefined = [];
    for(let i = 0; i < equipmentsList.length; i++) {
        equipments[i] = new Equipment(equipmentsList[i]);
    }
    return equipments;
}

export async function callDeleteEquipmentRestEndpointsByEquipmentId(
    equipmentId: number, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/equipment/v1?env=${env}&equipmentId=${equipmentId}` ;
    let deleteEquipmentResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEquipmentResponse.status == 204;
}

export async function callDeleteEquipmentRestEndpointsByEquipmentName(
    equipmentName: string, env: string, domain: string): Promise<boolean>  {
    let config = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/equipment/v1?env=${env}&equipmentName=${equipmentName}` ;
    let deleteEquipmentResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteEquipmentResponse.status == 204;
}
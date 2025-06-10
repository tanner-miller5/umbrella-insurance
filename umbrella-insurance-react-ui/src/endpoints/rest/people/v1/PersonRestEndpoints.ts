import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Person } from "../../../../models/people/v1/Person"
import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";

export async function callCreatePersonRestEndpoints(
    person: Person, env: string, domain: string): Promise<Person>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/people/v1?env=${env}`;
    let data = JSON.stringify(person as any, replacer);
    let createPersonResponse: AxiosResponse<Person> = await axios.post(url, data, config);
    let personResponse = new Person(createPersonResponse.data);
    return personResponse;
}

export async function callReadPersonRestEndpointsByPersonId(
    personId: number, env: string, domain: string): Promise<Person[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/people/v1?env=${env}&personId=${personId}` ;
    let readPersonResponse: AxiosResponse<Person[]> = await axios.get(url, config);
    let peopesList = readPersonResponse.data;
    let peoples: Person[] = [];
    for(let i = 0; i < peopesList.length; i++) {
        peoples[i] = new Person(peopesList[i]);
    }
    return peoples;
}

export async function callReadPersonRestEndpointsBySsn(
    ssn: string, env: string, domain: string): Promise<Person[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/people/v1?env=${env}&ssn=${ssn}` ;
    let readPersonResponse: AxiosResponse<Person[]> = await axios.get(url, config);
    let peopesList = readPersonResponse.data;
    let peoples: Person[] = [];
    for(let i = 0; i < peopesList.length; i++) {
        peoples[i] = new Person(peopesList[i]);
    }
    return peoples;
}

export async function callUpdatePersonRestEndpoints(
    person: Person, env: string, domain: string): Promise<Person[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/people/v1?env=${env}`;
    let data = JSON.stringify(person as any, replacer);
    let createPersonResponse: AxiosResponse<Person[]> = await axios.put(url, data, config);
    let peopesList: any = createPersonResponse.data;
    let peoples: Person[] = [];
    for(let i = 0; i < peopesList.length; i++) {
        peoples[i] = new Person(peopesList[i]);
    }
    return peoples;
}

export async function callDeletePersonRestEndpointsByPersonId(
    personId: number, env: string, domain: string): Promise<boolean>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/people/v1?env=${env}&personId=${personId}` ;
    let deletePersonResponse: AxiosResponse<any> = await axios.delete(url, config);
    let deletePersonResponseStatus = deletePersonResponse.status;
    return deletePersonResponseStatus == 204;
}

export async function callDeletePersonRestEndpointsBySsn(
    ssn: string, env: string, domain: string): Promise<boolean>  {
    let config: AxiosRequestConfig = {
        headers: {
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/people/v1?env=${env}&ssn=${ssn}` ;
    let deletePersonResponse: AxiosResponse<any> = await axios.delete(url, config);
    let deletePersonResponseStatus = deletePersonResponse.status;
    return deletePersonResponseStatus == 204;
}
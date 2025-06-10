import axios, { AxiosRequestConfig, AxiosResponse } from "axios";

import { Review } from "../../../../models/reviews/v1/Review";

import { replacer } from "../../users/sessions/v1/SessionRestEndpoints";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";
import { callCreateLoggingRestEndpoints } from "../../logging/v1/LoggingRestEndpoints";

export async function callCreateReviewRestEndpoints(
    review: Review, env: string, domain: string, session: string): Promise<Review>  {
    let config:AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/reviews/v1?env=${env}`;
    let data = JSON.stringify(review as any, replacer);
    let createReviewResponse: AxiosResponse<Review> = await axios.post(url, data, config);
    let review1 = new Review(createReviewResponse.data);
    return review1;
}

export async function callReadAllReviewsRestEndpoint(
    env: string, domain: string, session: string): Promise<Review[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/reviews/v1?env=${env}`;
    let reviews: Review[] | undefined = [];
    try {
        let readReviewListResponse: AxiosResponse<Review[]> = await axios.get(url, config);
        let reviewList = readReviewListResponse.data;
        for(let i = 0; i < reviewList.length; i++) {
            reviews[i] = new Review(reviewList[i]);
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
    return reviews;
}

export async function callReadAllReviewsRestEndpointByFrontendAppVersionAndBackendAppVersion(
    env: string, domain: string, session: string, frontendAppVersion: string,
    backendAppVersion: string): Promise<Review[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/reviews/v1?env=${env}&frontendAppVersion=${frontendAppVersion}&backendAppVersion=${backendAppVersion}`;
    let reviews: Review[] | undefined = [];
    try {
        let readReviewListResponse: AxiosResponse<Review[]> = await axios.get(url, config);
        let reviewList = readReviewListResponse.data;
        for(let i = 0; i < reviewList.length; i++) {
            reviews[i] = new Review(reviewList[i]);
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
    return reviews;
}

export async function callReadReviewRestEndpointsByReviewId(
    reviewId: number, env: string, domain: string, session: string): Promise<Review[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/reviews/v1?env=${env}&reviewId=${reviewId}`;
    let reviews: Review[] | undefined = [];
    try {
        let readReviewListResponse: AxiosResponse<Review[]> = await axios.get(url, config);
        let reviewList = readReviewListResponse.data;
        for(let i = 0; i < reviewList.length; i++) {
            reviews[i] = new Review(reviewList[i]);
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
    return reviews;
}

export async function callReadReviewRestEndpointsByUserIdAndFrontendAppVersionAndBackendAppVersion(
    userId: number, frontendAppVersion: string, backendAppVersion: string, 
    env: string, domain: string, session: string): Promise<Review[] | undefined> {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/reviews/v1?env=${env}&userId=${userId}&frontendAppVersion=${frontendAppVersion}&backendAppVersion=${backendAppVersion}`;
    let reviews: Review[] | undefined = [];
    try {
        let readReviewListResponse: AxiosResponse<Review[]> = await axios.get(url, config);
        let reviewList = readReviewListResponse.data;
        for(let i = 0; i < reviewList.length; i++) {
            reviews[i] = new Review(reviewList[i]);
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
    return reviews;
}

export async function callUpdateReviewRestEndpoints(
    review: Review, env: string, domain: string,
    session: string): Promise<Review[]>  {
    let config: AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/reviews/v1?env=${env}`;
    let data = JSON.stringify(review as any, replacer);
    let updateReviewListResponse: AxiosResponse<Review[]> = await axios.put(url, data, config);
    let reviewList = updateReviewListResponse.data;
    let reviews: Review[] | undefined = [];
    for(let i = 0; i < reviewList.length; i++) {
        reviews[i] = new Review(reviewList[i]);
    }
    return reviews;
}

export async function callDeleteReviewRestEndpointsByReviewId(
    reviewId: number, env: string, domain: string, session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/reviews/v1?env=${env}&reviewId=${reviewId}` ;
    let deleteReviewResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteReviewResponse.status == 204;
}

export async function callDeleteReviewRestEndpointsByUserIdAndFrontendAppVersionAndBackendAppVersion(
    userId: number, frontendAppVersion: string, backendAppVersion: string, 
    env: string, domain: string, session: string): Promise<boolean>  {
    let config = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/rest/reviews/v1?env=${env}&userId=${userId}&frontendAppVersion=${frontendAppVersion}&backendAppVersion=${backendAppVersion}` ;
    let deleteReviewResponse: AxiosResponse<any> = await axios.delete(url, config);
    return deleteReviewResponse.status == 204;
}
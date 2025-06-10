import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { LoggingMessage } from "../../../../models/logging/v1/LoggingMessage";


export async function callCreateLoggingRestEndpoints(
    LoggingMessage: LoggingMessage, env: string, domain: string): Promise<LoggingMessage | undefined>  {
    let createLoggingMessageResponse: LoggingMessage;
    try {
        let config:AxiosRequestConfig = {
            headers: {
                "Access-Control-Allow-Origin": "*",
                'Content-Type': 'application/json',
            }
        }
        let url = `${domain}/rest/logging/v1?env=${env}`;
        let createLoggingMessageResponseWrapper: AxiosResponse<LoggingMessage> = await axios.post(url, LoggingMessage, config);
        createLoggingMessageResponse = createLoggingMessageResponseWrapper.data;
        return createLoggingMessageResponse;
    } catch(e: any) {

    }


}
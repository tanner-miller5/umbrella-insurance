import axios, { AxiosRequestConfig, AxiosResponse } from "axios";
import { PendingPolicy } from "../../../../models/users/policies/pendingPolicies/v1/PendingPolicy";
import { replacer } from "../../../rest/users/sessions/v1/SessionRestEndpoints";

export async function callCreatePendingPolicySoaEndpoints(
    session: string, pendingPolicy: PendingPolicy, 
    env: string, domain: string): Promise<PendingPolicy>  {
    let config:AxiosRequestConfig = {
        headers: {
            "session": session,
            "Access-Control-Allow-Origin": "*",
            'Content-Type': 'application/json',
        }
    }

    let url = `${domain}/soa/pendingPolicies/v1?env=${env}`;
    let data = JSON.stringify(pendingPolicy as any, replacer);
    let createPendingPolicyResponse: AxiosResponse<PendingPolicy> = await axios.post(url, data, config);
    let pendingPolicy1 = new PendingPolicy(createPendingPolicyResponse.data);
    return pendingPolicy1;
}
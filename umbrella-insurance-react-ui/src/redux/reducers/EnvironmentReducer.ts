import { createSlice } from '@reduxjs/toolkit'

export class EnvironmentState {
  env: string;
  domain: string;
  wsProtocol: string;
  wsDomain: string;
  isWsConnected: boolean;
  constructor() {
    const queryParameters = new URLSearchParams(window.location.search);
    const envQueryParam: string | null = queryParameters.get("env");
    const domainQueryParam: string | undefined = process.env.REACT_APP_DOMAIN;
    this.env = envQueryParam ? envQueryParam : "PROD"
    this.domain = domainQueryParam ? domainQueryParam : "http://localhost:8080";
    this.wsProtocol = process.env.REACT_APP_WS_PROTOCOL ? process.env.REACT_APP_WS_PROTOCOL : "ws";
    this.wsDomain = process.env.REACT_APP_WS_DOMAIN ? process.env.REACT_APP_WS_DOMAIN : "localhost:8080";
    this.isWsConnected = false;
  }
}

export const environmentSlice = createSlice({
  name: 'environment',
  initialState: new EnvironmentState(),
  reducers: {
    updateEnv: (state, action) => {
      return {
        ...state,
        env: action.payload
      }
    },
    updateDomain: (state, action) => {
      return {
        ...state,
        domain: action.payload
      }
    },
    updateWsProtocol: (state, action) => {
      return {
        ...state,
        wsProtocol: action.payload
      }
    },
    updateWsDomain: (state, action) => {
      return {
        ...state,
        wsDomain: action.payload
      }
    },
    updateIsWsConnected: (state, action) => {
      return {
        ...state,
        isWsConnected: action.payload
      }
    }
  }
})

// Action creators are generated for each case reducer function
export const { updateEnv, updateDomain, updateWsProtocol, updateWsDomain, updateIsWsConnected } = environmentSlice.actions

export default environmentSlice.reducer
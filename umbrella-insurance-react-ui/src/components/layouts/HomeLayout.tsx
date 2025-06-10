import { Outlet } from "react-router-dom";
import Header from "../headers/Header";
import { callCreateLoggingRestEndpoints } from "../../endpoints/rest/logging/v1/LoggingRestEndpoints";
import { LoggingMessage } from "../../models/logging/v1/LoggingMessage";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../redux/store/Store";
import { useEffect } from "react";
import { updateAccuracy, updateLatitude, updateLongitude } from "../../redux/reducers/UserReducer";

export default function Layout() {
    const dispatch = useDispatch();
    const isHamburgerMenuOpen : boolean = useSelector((state:RootState)=>{
        return state.app.isHamburgerMenuOpen;
    });
    function success(pos:GeolocationPosition){
        const crd = pos.coords;

        console.log("Your current position is:");
        console.log(`Latitude : ${crd.latitude}`);
        console.log(`Longitude: ${crd.longitude}`);
        console.log(`More or less ${crd.accuracy} meters.`);
        let loggingMessage: LoggingMessage = new LoggingMessage();
        const url = window.location.href;
        loggingMessage.appName = 'umbrella-insurance-frontend';
        loggingMessage.callingLoggerName = "HomeLayout.tsx";
        loggingMessage.callingMethod = "HomeLayout.tsx:Layout()";
        dispatch(updateLatitude(crd.latitude));
        dispatch(updateLongitude(crd.longitude));
        dispatch(updateAccuracy(crd.accuracy));
        loggingMessage.loggingPayload = `Latitude : ${crd.latitude}, Longitude: ${crd.longitude}, More or less ${crd.accuracy} meters.`;
        loggingMessage.logLevel = "INFO";
        loggingMessage.latitude = crd.latitude;
        loggingMessage.longitude = crd.longitude;
        loggingMessage.accuracy = crd.accuracy;
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);
    }
    function error(err: GeolocationPositionError){
        let loggingMessage: LoggingMessage = new LoggingMessage();
        const url = window.location.href;
        loggingMessage.appName = 'umbrella-insurance-frontend';
        loggingMessage.callingLoggerName = "HomeLayout.tsx";
        loggingMessage.callingMethod = "HomeLayout.tsx:error()";
        loggingMessage.loggingPayload = `ERROR(${err.code}): ${err.message}`
        loggingMessage.logLevel = "ERROR";
        callCreateLoggingRestEndpoints(loggingMessage, env, domain);
        console.warn(loggingMessage.loggingPayload);
    }
    const latitude: number | undefined = useSelector((state: RootState) => {
        return state.user.latitude
    })
    const longitude: number | undefined = useSelector((state: RootState)=>{
        return state.user.longitude;
    });
    useEffect( function() {
        if(!(latitude && longitude) && isHamburgerMenuOpen) {
            navigator?.geolocation?.getCurrentPosition(success, error);
        }
    }, [isHamburgerMenuOpen]);

    const env = useSelector((state: RootState) => {
        return state.environment.env
    })
    let domain: string = useSelector((state: RootState)=>{
        return state.environment.domain;
    });
    return (
        <>
            <Header></Header>
            <Outlet />
        </>
    );
};
import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { callReadAllStatsRestEndpoint } from "../../endpoints/rest/stats/v1/StatRestEndpoints";
import { Stat } from "../../models/stats/v1/Stat";

export default function CreateBettingEvent(){
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const username: string = useSelector((state: RootState)=>{
        return state.user.username;
    }) || "";
    const email: string = useSelector((state: RootState)=>{
        return state.user.emailAddress;
    }) || "";
    const phone: string = useSelector((state: RootState)=>{
        return state.user.phoneNumber;
    }) || "";
    const env: string = useSelector((state: RootState) => {
        return state.environment.env;
    })
    const domain: string = useSelector((state: RootState) => {
        return state.environment.domain;
    })
    const resetMethod: string = useSelector((state: RootState)=>{
        return state.user.resetMethod;
    }) || "";
    const newPassword: string = useSelector((state: RootState)=>{
        return state.user.newPassword;
    }) || "";
    const existingPassword: string = useSelector((state: RootState)=>{
        return state.user.existingPassword;
    }) || "";
    const confirmNewPassword: string = useSelector((state: RootState)=>{
        return state.user.confirmNewPassword;
    }) || "";
    const verificationCode: string = useSelector((state: RootState)=>{
        return state.user.verificationCode;
    }) || "";
    const password: string = useSelector((state: RootState)=>{
        return state.user.password;
    }) || "";
    const sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";
    
    useEffect(
        function() {
            dispatch(updateCurrentPage("/createBettingEvent"));
        }, []
    )

    useEffect(function() {
        async function callGetStats() {
            let stats: Stat[] | undefined = await callReadAllStatsRestEndpoint(env, domain);
        }
        callGetStats();
    }, []);

    async function onSelectStat(e:any ) {
        console.log(typeof e)
        console.log(e.target);
        console.log(e.target[0].selected);
        console.log(e.target[1].selected);
        console.log(e.target[2].selected);
        console.log(e.target[3].selected);
    }
    

    return (
    <div className="column2">
        <form className='flexContainer' onSubmit={(e)=>{
                e.preventDefault();

        }} >
            <h2>Create Betting Event</h2>
            <div className='flexInner'>
                <label>Event Name</label> 
            </div>
            <div className='flexInner'>
                <input
                    id="eventName" name="eventName" type="text" 
                    onChange={(e)=>2}/>
            </div>
            <div className='flexInner'>
                <label>Stats</label> 
            </div>
            <div className='flexInner'>
                <select name="cars" id="cars" multiple onChange={onSelectStat}>
                    <option value="volvo">Volvo</option>
                    <option value="saab">Saab</option>
                    <option value="opel">Opel</option>
                    <option value="audi">Audi</option>
                </select>
            </div>
            <div className='flexInner'>
                <label>Date / Time</label> 
            </div>
            <div className='flexInner'>
                <input
                    id="existingPassword" name="existingPassword" type="datetime-local" 
                    onChange={(e)=>1}/>
            </div>
            <div className='flexInner'>
                <button name="action" type="submit" >Submit</button>
            </div>
        </form>
    </div>);
};
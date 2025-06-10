import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { callReadAllStatsRestEndpoint } from "../../endpoints/rest/stats/v1/StatRestEndpoints";
import { Stat } from "../../models/stats/v1/Stat";

export default function CreateFootballEvent(){
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
            <h2>Create Football Event</h2>
            <div className='flexInner'>
                <label>Event Name</label> 
            </div>
            <div className='flexInner'>
                <input
                    id="eventName" name="eventName" type="text" 
                    onChange={(e)=>2}/>
            </div>
            <div className='flexInner'>
                <label>Home Team</label> 
            </div>
            <div className='flexInner'>
                <select>
                    <option></option>
                </select>
            </div>
            <div className='flexInner'>
                <label>Away Team</label> 
            </div>
            <div className='flexInner'>
                <select>
                    <option></option>
                </select>
            </div>
            <div className='flexInner'>
                <label>Location</label> 
            </div>
            <div className='flexInner'>
                <input type="text"></input>
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
                <label>Home Passing</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>C/ATT</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>TD</th>
                        <th>INT</th>
                        <th>SACKS</th>
                        <th>RTG</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td> 
                        <td><input type="text"></input></td> 
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td> 
                        <td><input type="text"></input></td> 
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Passing</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>C/ATT</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>TD</th>
                        <th>INT</th>
                        <th>SACKS</th>
                        <th>RTG</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td> 
                        <td><input type="text"></input></td> 
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td> 
                        <td><input type="text"></input></td> 
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Rushing</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>CAR</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>TD</th>
                        <th>LONG</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Home Rushing</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>CAR</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>TD</th>
                        <th>LONG</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Home Receiving</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>REC</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>TD</th>
                        <th>LONG</th>
                        <th>TGTS</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Receiving</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>REC</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>TD</th>
                        <th>LONG</th>
                        <th>TGTS</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Fumbles</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>FUM</th>
                        <th>LOST</th>
                        <th>REC</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Home Fumbles</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>FUM</th>
                        <th>LOST</th>
                        <th>REC</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Home Defense</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>TOT</th>
                        <th>SOLO</th>
                        <th>SACKS</th>
                        <th>TFL</th>
                        <th>PD</th>
                        <th>QB HTS</th>
                        <th>TD</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Defense</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>TOT</th>
                        <th>SOLO</th>
                        <th>SACKS</th>
                        <th>TFL</th>
                        <th>PD</th>
                        <th>QB HTS</th>
                        <th>TD</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Interceptions</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>INT</th>
                        <th>YDS</th>
                        <th>TD</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Home Interceptions</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>INT</th>
                        <th>YDS</th>
                        <th>TD</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Home Kick Returns</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>NO</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>LONG</th>
                        <th>TD</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Kick Returns</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>NO</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>LONG</th>
                        <th>TD</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Punt Returns</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>NO</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>LONG</th>
                        <th>TD</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Home Punt Returns</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>NO</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>LONG</th>
                        <th>TD</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Home Kicking</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>FG</th>
                        <th>PCT</th>
                        <th>LONG</th>
                        <th>XP</th>
                        <th>PTS</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Kicking</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>FG</th>
                        <th>PCT</th>
                        <th>LONG</th>
                        <th>XP</th>
                        <th>PTS</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Away Punting</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>NO</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>TB</th>
                        <th>IN 20</th>
                        <th>LONG</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>
            <div className='flexInner'>
                <label>Home Punting</label> 
            </div>
            <div className='flexInner'>
                <table>
                    <tr>
                        <th></th>
                        <th>NO</th>
                        <th>YDS</th>
                        <th>AVG</th>
                        <th>TB</th>
                        <th>IN 20</th>
                        <th>LONG</th>
                    </tr>
                    <tr>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                    <tr>
                        <td>TEAM</td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                        <td><input type="text"></input></td>
                    </tr>
                </table> 
            </div>

            <div className='flexInner'>
                <button name="action" type="submit" >Submit</button>
            </div>
        </form>
    </div>);
};
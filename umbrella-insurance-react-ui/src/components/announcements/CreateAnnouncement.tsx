import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen, updateIsHamburgerMenuOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { Announcement } from "../../models/announcements/v1/Announcement";
import { updateAnnouncement } from "../../redux/reducers/AnnouncementReducer";
import { callCreateAnnouncementRestEndpoints } from "../../endpoints/rest/announcements/v1/AnnouncementRestEndpoints";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { callCreateLoggingRestEndpoints } from "../../endpoints/rest/logging/v1/LoggingRestEndpoints";
import { LoggingMessage } from "../../models/logging/v1/LoggingMessage";

export default function CreateAnnouncement() {
    let navigate = useNavigate();
    let dispatch = useDispatch();
    const env = useSelector((state: RootState) => {
        return state.environment.env
    })
    let errorMessage: string = useSelector((state: RootState)=>{
        return state.app.errorMessage;
    }) || "";
    let isErrorOpen: boolean = useSelector((state: RootState)=>{
        return state.app.isErrorOpen;
    });
    let domain: string = useSelector((state: RootState)=>{
        return state.environment.domain;
    });
    let announcement: Announcement = useSelector((state: RootState)=>{
        return state.announcement.announcement;
    }) || new Announcement();
    let sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";

    let userAgent: string = useSelector((state: RootState)=>{
        return state.user.userAgent;
    }) || "";

    async function createAnnouncementClicked() {
        try {
            dispatch(updateLoadingState(true));
            announcement.createdDateTime = new Date().toISOString();
            let response: Announcement = await callCreateAnnouncementRestEndpoints(
                announcement, env, domain, sessionCode);
        } catch(e: any) {
            let loggingMessage: LoggingMessage = new LoggingMessage();         
            const url = window.location.href;         
            loggingMessage.appName = 'umbrella-insurance-frontend';
            loggingMessage.callingLoggerName = url;         
            loggingMessage.loggingPayload = `ERROR:${e.message}`;         
            loggingMessage.logLevel = "ERROR";         
            callCreateLoggingRestEndpoints(loggingMessage, env, domain);         
            console.error(loggingMessage.loggingPayload);
            dispatch(updateLoadingState(false));
            dispatch(updateIsErrorOpen(true));
            dispatch(updateErrorMessage("Create announcement failed"));
            return 
        } finally {
            dispatch(updateLoadingState(false));
        }
        navigate("/announcements");
    }

    useEffect(
        function() {
            dispatch(updateCurrentPage("/createAnnouncement"));
        }, []
    )
    //default values
    useEffect(function(){
        dispatch(updateAnnouncement(new Announcement()));
    },[]);

    function onClickBack() {
        navigate("/");
    }
    return (
        <div className='column2' onClick={()=>dispatch(updateIsHamburgerMenuOpen(false))}>
            <form className="flexContainer" onSubmit={(e)=>{
                            e.preventDefault();
                            createAnnouncementClicked();
                        }}>
                <h2>Create Announcement</h2>
                <div className='flexInner'>
                    <label>Announcement Name</label> 
                </div>
                <div className='flexInner'>
                    <input id="announcementName" name="announcementName" 
                        placeholder='New Feature X'
                        required type="text" value={announcement?.announcementName || ""} onChange={(event)=> {
                            
                            let newAnnouncement: Announcement = new Announcement();
                            newAnnouncement.message = announcement.message;
                            newAnnouncement.id = announcement.id;
                            newAnnouncement.announcementName = event.target.value;
                            newAnnouncement.createdDateTime = announcement.createdDateTime;
                            newAnnouncement.session = announcement.session;
                            dispatch(updateAnnouncement(newAnnouncement))}
                    }/>
                </div>
                <div className='flexInner'>
                    <label>Announcement Message</label> 
                </div>
                <div className='flexInner'>
                    <input id="message" placeholder='This feature does ...' autoComplete='family-name'
                        name="message" 
                        type="text" required value={announcement.message || ""} onChange={(event)=> {
                            let newAnnouncement: Announcement = new Announcement();
                            newAnnouncement.message = event.target.value;
                            newAnnouncement.id = announcement.id;
                            newAnnouncement.announcementName = announcement.announcementName;
                            newAnnouncement.createdDateTime = announcement.createdDateTime;
                            newAnnouncement.session = announcement.session;
                            dispatch(updateAnnouncement(newAnnouncement))}
                    }/>
                </div>
                <div className='flexInner'>
                    <button name="action" type="submit" >Submit</button>
                </div>
                <div className='flexInner'>
                    <button name="back" type="button" onClick={onClickBack}>Back</button>
                </div>
            </form>
        </div>);
};
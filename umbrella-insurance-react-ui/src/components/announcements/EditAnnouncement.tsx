import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { callCreateAnnouncementRestEndpoints, callDeleteAnnouncementRestEndpointsByAnnouncementId, callDeleteAnnouncementRestEndpointsByAnnouncementName, callReadAllAnnouncementsRestEndpoint, callUpdateAnnouncementRestEndpoints } from "../../endpoints/rest/announcements/v1/AnnouncementRestEndpoints";
import { callCreateLoggingRestEndpoints } from "../../endpoints/rest/logging/v1/LoggingRestEndpoints";
import { Announcement } from "../../models/announcements/v1/Announcement";
import { LoggingMessage } from "../../models/logging/v1/LoggingMessage";
import { updateAnnouncement } from "../../redux/reducers/AnnouncementReducer";
import { updateIsErrorOpen, updateErrorMessage, updateCurrentPage, updateIsHamburgerMenuOpen, updateAnnouncements } from "../../redux/reducers/AppReducer";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { RootState } from "../../redux/store/Store";
import { toObject } from "../../utils/Parser";

export default function EditAnnouncement() {
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

    let announcements: Announcement[] = useSelector((state: RootState) => {
        return state.app.announcements;
    })

    useEffect(
        function() {
            async function getAnnouncements() {
                const announcements = await callReadAllAnnouncementsRestEndpoint(env, domain);
                if(announcements) {
                    dispatch(updateAnnouncements(toObject(announcements)));
                }
            };
            getAnnouncements();
        }, []
    );

    async function editAnnouncementClicked() {
        try {
            dispatch(updateLoadingState(true));
            announcement.createdDateTime = new Date().toISOString();
            let response: Announcement[] = await callUpdateAnnouncementRestEndpoints(
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
            dispatch(updateErrorMessage("Edit announcement failed"));
            return 
        } finally {
            dispatch(updateLoadingState(false));
        }
        navigate("/announcements");
    }

    useEffect(
        function() {
            dispatch(updateCurrentPage("/editAnnouncement"));
        }, []
    )
    //default values
    useEffect(function(){
        let newAnnoucement = new Announcement();
        newAnnoucement.announcementName = "";
        newAnnoucement.message = "";
        dispatch(updateAnnouncement(newAnnoucement));
    },[]);

    function onClickBack() {
        navigate("/");
    }

    function onSelectChange(event: any) {
        for(let i = 0; i < announcements.length ; i++) {
            if(announcements[i].announcementName === event.target.value) {
                dispatch(updateAnnouncement(announcements[i]));
            }
        }

    }
    
    async function deleteAnnouncementClicked() {
        try {
            dispatch(updateLoadingState(true));
            let response: boolean = await callDeleteAnnouncementRestEndpointsByAnnouncementId(
                announcement.id || 1, env, domain, sessionCode);
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
            dispatch(updateErrorMessage("Delete announcement failed"));
            return 
        } finally {
            dispatch(updateLoadingState(false));
        }
        navigate("/announcements");
    }

    let announcementOptions = new Array();
    announcementOptions.push(<option key="-" disabled value="">-</option>);
    for(let i = 0; i < announcements.length ; i++) {
        announcementOptions.push(<option key={announcements[i].announcementName} value={announcements[i].announcementName}>{announcements[i].announcementName}</option>);

    }

    return (
        <div className='column2' onClick={()=>dispatch(updateIsHamburgerMenuOpen(false))}>
            <form className="flexContainer" onSubmit={(e)=>{
                            e.preventDefault();
                            editAnnouncementClicked();
                        }}>
                <h2>Edit Announcement</h2>
                <div className='flexInner'>
                    <label>Announcement Name to Edit</label> 
                </div>
                <select onChange={onSelectChange} defaultValue={""}>
                    {announcementOptions}
                </select>
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
                    <textarea id="message" placeholder='This feature does ...' autoComplete='family-name'
                        name="message" 
                        required value={announcement?.message || ""} onChange={(event)=> {
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
                    <button id="deleteButton" name="action" onClick={deleteAnnouncementClicked}
                            type="button" >Delete Announcement
                    </button>
                </div>
                <div className='flexInner'>
                    <button name="back" type="button" onClick={onClickBack}>Back</button>
                </div>
            </form>
        </div>);
};
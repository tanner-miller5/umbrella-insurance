import { useEffect } from 'react';
import '../../css/announcements/announcements.css';
import { updateAnnouncements, updateCurrentPage, updateFaqs } from '../../redux/reducers/AppReducer';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { toObject } from '../../utils/Parser';
import AnnouncementRow from './AnnouncementRow';
import { callReadAllAnnouncementsRestEndpoint } from '../../endpoints/rest/announcements/v1/AnnouncementRestEndpoints';

export default function Announcements(){
    const dispatch = useDispatch();
    const announcements = useSelector((state: RootState) => {
        return state.app.announcements;
    });
    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    useEffect(
        function() {
            dispatch(updateCurrentPage("/announcements"));
        }, []
    );
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
    const rows = [];
    for (let i = 0; i < announcements.length; i++) {
        // note: we are adding a key prop here to allow react to uniquely identify each
        // element in this array. see: https://reactjs.org/docs/lists-and-keys.html
        rows.push(<AnnouncementRow key={i} message={announcements[i].message || ""} createdDateTime={announcements[i].createdDateTime || ""} />);
    }
    return (    
            <div className='column2'>
                <h1>Announcements</h1>
                {rows}
            </div> 
    );
};

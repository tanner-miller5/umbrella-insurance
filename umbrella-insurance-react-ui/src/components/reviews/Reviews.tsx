import { useEffect } from 'react';
import '../../css/reviews/reviews.css';
import { updateReviews, updateCurrentPage } from '../../redux/reducers/AppReducer';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { toObject } from '../../utils/Parser';
import { callReadAllReviewsRestEndpoint, callReadAllReviewsRestEndpointByFrontendAppVersionAndBackendAppVersion, callReadReviewRestEndpointsByReviewId } from '../../endpoints/rest/reviews/v1/ReviewRestEndpoints';
import ReviewRow from './ReviewRow';

export default function Reviews(){
    const dispatch = useDispatch();
    const reviews = useSelector((state: RootState) => {
        return state.app.reviews;
    });
    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const sessionCode = useSelector((state: RootState) => {
        return state.user.sessionCode;
    }) || "";
    const frontendAppVersion = useSelector((state: RootState) => {
        return state.app.frontendAppVersion;
    }) || "";
    const backendAppVersion = useSelector((state: RootState) => {
        return state.app.backendAppVersion;
    }) || "";
    useEffect(
        function() {
            dispatch(updateCurrentPage("/reviews"));
        }, []
    );
    useEffect(
        function() {
            async function getReviews() {
                const reviews = await callReadAllReviewsRestEndpointByFrontendAppVersionAndBackendAppVersion(env, domain,
                    sessionCode, frontendAppVersion, backendAppVersion
                );
                if(reviews) {
                    dispatch(updateReviews(toObject(reviews)));
                }
            };
            getReviews();
        }, []
    );
    let totalRatings = 0;
    const rows = [];
    let averageRating = "NA";
    for (let i = 0; i < reviews.length; i++) {
        // note: we are adding a key prop here to allow react to uniquely identify each
        // element in this array. see: https://reactjs.org/docs/lists-and-keys.html
        rows.push(<ReviewRow key={i} subject={reviews[i].subject || ""} 
            comment={reviews[i].comment || ""} rating={reviews[i].rating || 3} 
            date={reviews[i].createdDateTime || ""} />);
        totalRatings += reviews[i].rating || 3
    }
    averageRating = reviews.length > 0 ? (totalRatings/reviews.length).toString() : "NA";
    return (    
            <div className='column2'>
                <h1>Reviews</h1>
                <p>App version: {frontendAppVersion}:{backendAppVersion}</p>
                <p>average rating: {averageRating}</p>
                {rows}
            </div> 
    );
};

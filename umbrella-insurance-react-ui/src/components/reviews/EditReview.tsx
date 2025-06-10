import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { callCreateLoggingRestEndpoints } from "../../endpoints/rest/logging/v1/LoggingRestEndpoints";
import { LoggingMessage } from "../../models/logging/v1/LoggingMessage";
import { updateIsErrorOpen, updateErrorMessage, updateCurrentPage, updateIsHamburgerMenuOpen, updateReviews } from "../../redux/reducers/AppReducer";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { RootState } from "../../redux/store/Store";
import { toObject } from "../../utils/Parser";
import { Review } from "../../models/reviews/v1/Review";
import { updateReview } from "../../redux/reducers/ReviewsReducer";
import { callDeleteReviewRestEndpointsByReviewId, callReadAllReviewsRestEndpoint, callReadReviewRestEndpointsByUserIdAndFrontendAppVersionAndBackendAppVersion, callUpdateReviewRestEndpoints } from "../../endpoints/rest/reviews/v1/ReviewRestEndpoints";
import { User } from "../../models/users/v1/User";

export default function EditReview() {
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
    let review: Review = useSelector((state: RootState)=>{
        return state.review.review;
    }) || new Review();
    let sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";

    let userAgent: string = useSelector((state: RootState)=>{
        return state.user.userAgent;
    }) || "";

    let user: User = useSelector((state: RootState)=>{
        return state.user;
    });

    let reviews: Review[] = useSelector((state: RootState) => {
        return state.app.reviews;
    });

    let frontendAppVersion: string = useSelector((state: RootState)=>{
        return state.app.frontendAppVersion;
    }) || "";

    let backendAppVersion: string = useSelector((state: RootState)=>{
        return state.app.backendAppVersion;
    }) || "";


    useEffect(
        function() {
            async function getReviews() {
                const reviews = await callReadReviewRestEndpointsByUserIdAndFrontendAppVersionAndBackendAppVersion(
                    user.id || 1, frontendAppVersion, backendAppVersion, env, domain, sessionCode);
                if(reviews) {
                    dispatch(updateReviews(toObject(reviews)));
                }
            };
            getReviews();
        }, []
    );

    async function editReviewClicked() {
        try {
            dispatch(updateLoadingState(true));
            review.createdDateTime = new Date().toISOString();
            let response: Review[] = await callUpdateReviewRestEndpoints(
                review, env, domain, sessionCode);
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
            dispatch(updateErrorMessage("Edit Review failed"));
            return 
        } finally {
            dispatch(updateLoadingState(false));
        }
        navigate("/");
    }

    useEffect(
        function() {
            dispatch(updateCurrentPage("/editReview"));
        }, []
    )
    //default values
    useEffect(function(){
        let newReview = new Review();
        newReview.subject = "";
        newReview.comment = "";
        newReview.backendAppVersion = backendAppVersion;
        newReview.frontendAppVersion = frontendAppVersion;
        newReview.user = new User();
        dispatch(updateReview(newReview));
    },[]);

    function onClickBack() {
        navigate("/");
    }

    function onSelectChange(event: any) {
        for(let i = 0; i < reviews.length ; i++) {
            if(reviews[i].subject === event.target.value) {
                dispatch(updateReview(reviews[i]));
            }
        }

    }
    
    async function deleteReviewClicked() {
        try {
            dispatch(updateLoadingState(true));
            let response: boolean = await callDeleteReviewRestEndpointsByReviewId(
                review.id || 1, env, domain, sessionCode);
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
            dispatch(updateErrorMessage("Delete Review failed"));
            return 
        } finally {
            dispatch(updateLoadingState(false));
        }
        navigate("/");
    }

    let reviewOptions = new Array();
    reviewOptions.push(<option key="-" disabled value="">-</option>);
    for(let i = 0; i < reviews.length ; i++) {
        reviewOptions.push(<option key={reviews[i].subject} value={reviews[i].subject}>{reviews[i].subject}</option>);

    }

    return (
        <div className='column2' onClick={()=>dispatch(updateIsHamburgerMenuOpen(false))}>
            <form className="flexContainer" onSubmit={(e)=>{
                            e.preventDefault();
                            editReviewClicked();
                        }}>
                <h2>Edit Review</h2>
                <div className='flexInner'>
                    <label>Review Subject to Edit</label> 
                </div>
                <select onChange={onSelectChange} defaultValue={""}>
                    {reviewOptions}
                </select>
                <div className='flexInner'>
                    <label>Review Subject</label> 
                </div>
                <div className='flexInner'>
                    <input id="reviewSubject" name="reviewSubject" 
                        placeholder='Subject x?'
                        required type="text" value={review?.subject || ""} onChange={(event)=> {
                            
                            let newReview: Review = new Review();
                            newReview.subject = event.target.value;
                            newReview.comment = review.comment;
                            newReview.id = review.id;
                            newReview.rating = review.rating;
                            newReview.createdDateTime = review.createdDateTime;
                            newReview.session = review.session;
                            newReview.backendAppVersion = review.backendAppVersion;
                            newReview.frontendAppVersion = review.frontendAppVersion;
                            newReview.user = review.user;
                            dispatch(updateReview(newReview))}
                    }/>
                </div>
                <div className='flexInner'>
                    <label>Review Comment</label> 
                </div>
                <div className='flexInner'>
                    <textarea id="comment" placeholder='I think...' autoComplete='family-name'
                        name="comment" 
                        required value={review?.comment || ""} onChange={(event)=> {
                            let newReview: Review = new Review();
                            newReview.comment = event.target.value;
                            newReview.subject = review.subject;
                            newReview.id = review.id;
                            newReview.rating = review.rating;
                            newReview.createdDateTime = review.createdDateTime;
                            newReview.session = review.session;
                            newReview.backendAppVersion = review.backendAppVersion;
                            newReview.frontendAppVersion = review.frontendAppVersion;
                            newReview.user = review.user;
                            dispatch(updateReview(newReview))}
                    }/>
                </div>
                <div className='flexInner'>
                    <label>Review Rating</label> 
                </div>
                <div className='flexInner'>
                    <select id="rating" name="rating" required
                        defaultValue={""}
                        onChange={(event)=> {
                            let newReview: Review = new Review();
                            newReview.subject = review.subject;
                            newReview.rating = Number(event.target.value);
                            newReview.id = review.id;
                            newReview.comment = review.comment;
                            newReview.createdDateTime = review.createdDateTime;
                            newReview.session = review.session;
                            newReview.backendAppVersion = review.backendAppVersion;
                            newReview.frontendAppVersion = review.frontendAppVersion;
                            newReview.user = review.user;
                            dispatch(updateReview(newReview))}
                    }>
                        <option disabled value="">-</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                </div>
                <div className='flexInner'>
                    <button name="action" type="submit" 
                    disabled={reviewOptions.length === 1 || review.subject === ""}>Submit</button>
                </div>
                <div className='flexInner'>
                    <button id="deleteButton" name="action" onClick={deleteReviewClicked}
                            type="button" 
                            disabled={reviewOptions.length === 1 || review.subject === ""}>Delete Review
                    </button>
                </div>
                <div className='flexInner'>
                    <button name="back" type="button" onClick={onClickBack}>Back</button>
                </div>
            </form>
        </div>);
};
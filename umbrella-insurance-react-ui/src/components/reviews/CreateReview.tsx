import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen, updateIsHamburgerMenuOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { callCreateLoggingRestEndpoints } from "../../endpoints/rest/logging/v1/LoggingRestEndpoints";
import { LoggingMessage } from "../../models/logging/v1/LoggingMessage";
import { callCreateReviewRestEndpoints } from "../../endpoints/rest/reviews/v1/ReviewRestEndpoints";
import { Review } from "../../models/reviews/v1/Review";
import { updateReview } from "../../redux/reducers/ReviewsReducer";
import { User } from "../../models/users/v1/User";

export default function CreateReview() {
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
    let frontendAppVersion: string = useSelector((state: RootState)=>{
        return state.app.frontendAppVersion;
    }) || "";
    let backendAppVersion: string = useSelector((state: RootState)=>{
        return state.app.backendAppVersion;
    }) || "";
    let user: User = useSelector((state: RootState)=>{
        return state.user;
    }) || BigInt(-1);
    let rating: number = useSelector((state: RootState)=>{
        return state.review.review?.rating;
    }) || 1;

    async function createReviewClicked() {
        try {
            dispatch(updateLoadingState(true));
            review.createdDateTime = new Date().toISOString();
            review.backendAppVersion = backendAppVersion;
            review.frontendAppVersion = frontendAppVersion;
            review.user = user;
            review.rating = rating;
            let response: Review = await callCreateReviewRestEndpoints(
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
            dispatch(updateErrorMessage("Create review failed"));
            return 
        } finally {
            dispatch(updateLoadingState(false));
        }
        navigate("/reviews");
    }

    useEffect(
        function() {
            dispatch(updateCurrentPage("/createReview"));
        }, []
    )
    //default values
    useEffect(function(){
        dispatch(updateReview(new Review()));
    },[]);

    function onClickBack() {
        navigate("/");
    }
    return (
        <div className='column2' onClick={()=>dispatch(updateIsHamburgerMenuOpen(false))}>
            <form className="flexContainer" onSubmit={(e)=>{
                            e.preventDefault();
                            createReviewClicked();
                        }}>
                <h2>Create Review</h2>
                <div className='flexInner'>
                    <label>Review Subject</label> 
                </div>
                <div className='flexInner'>
                    <input id="reviewSubject" name="reviewSubject" 
                        placeholder='Slow'
                        required type="text" value={review?.subject || ""} onChange={(event)=> {
                            
                            let newReview: Review = new Review();
                            newReview.subject = event.target.value;
                            newReview.comment = review.comment;
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
                    <input id="comment" placeholder='Clicking on...' autoComplete='family-name'
                        name="comment" 
                        type="text" required value={review.comment || ""} onChange={(event)=> {
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
                    <button name="action" type="submit" >Submit</button>
                </div>
                <div className='flexInner'>
                    <button name="back" type="button" onClick={onClickBack}>Back</button>
                </div>
            </form>
        </div>);
};
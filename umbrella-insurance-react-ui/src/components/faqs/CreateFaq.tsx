import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { updateCurrentPage, updateErrorMessage, updateIsErrorOpen, updateIsHamburgerMenuOpen } from "../../redux/reducers/AppReducer";
import { RootState } from "../../redux/store/Store";
import { Faq } from "../../models/faqs/v1/Faq";
import { callCreateFaqRestEndpoints } from "../../endpoints/rest/faqs/v1/FaqRestEndpoints";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { callCreateLoggingRestEndpoints } from "../../endpoints/rest/logging/v1/LoggingRestEndpoints";
import { LoggingMessage } from "../../models/logging/v1/LoggingMessage";
import { updateFaq } from "../../redux/reducers/FaqReducer";

export default function CreateFaq() {
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
    let faq: Faq = useSelector((state: RootState)=>{
        return state.faq.faq;
    }) || new Faq();
    let sessionCode: string = useSelector((state: RootState)=>{
        return state.user.sessionCode;
    }) || "";

    let userAgent: string = useSelector((state: RootState)=>{
        return state.user.userAgent;
    }) || "";

    async function createFaqClicked() {
        try {
            dispatch(updateLoadingState(true));
            faq.createdDateTime = new Date().toISOString();
            let response: Faq = await callCreateFaqRestEndpoints(
                faq, env, domain, sessionCode);
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
            dispatch(updateErrorMessage("Create faq failed"));
            return 
        } finally {
            dispatch(updateLoadingState(false));
        }
        navigate("/faqs");
    }

    useEffect(
        function() {
            dispatch(updateCurrentPage("/createFaq"));
        }, []
    )
    //default values
    useEffect(function(){
        dispatch(updateFaq(new Faq()));
    },[]);

    function onClickBack() {
        navigate("/");
    }
    return (
        <div className='column2' onClick={()=>dispatch(updateIsHamburgerMenuOpen(false))}>
            <form className="flexContainer" onSubmit={(e)=>{
                            e.preventDefault();
                            createFaqClicked();
                        }}>
                <h2>Create FAQ</h2>
                <div className='flexInner'>
                    <label>FAQ Name</label> 
                </div>
                <div className='flexInner'>
                    <input id="faqName" name="faqName" 
                        placeholder='New Feature X'
                        required type="text" value={faq?.faqName || ""} onChange={(event)=> {
                            
                            let newFaq: Faq = new Faq();
                            newFaq.question = faq.question;
                            newFaq.answer = faq.answer;
                            newFaq.id = faq.id;
                            newFaq.faqName = event.target.value;
                            newFaq.createdDateTime = faq.createdDateTime;
                            newFaq.session = faq.session;
                            dispatch(updateFaq(newFaq))}
                    }/>
                </div>
                <div className='flexInner'>
                    <label>FAQ Question</label> 
                </div>
                <div className='flexInner'>
                    <input id="question" placeholder='What is ...' autoComplete='family-name'
                        name="question" 
                        type="text" required value={faq.question || ""} onChange={(event)=> {
                            let newFaq: Faq = new Faq();
                            newFaq.question = event.target.value;
                            newFaq.answer = faq.answer;
                            newFaq.id = faq.id;
                            newFaq.faqName = faq.faqName;
                            newFaq.createdDateTime = faq.createdDateTime;
                            newFaq.session = faq.session;
                            dispatch(updateFaq(newFaq))}
                    }/>
                </div>
                <div className='flexInner'>
                    <label>FAQ Answer</label> 
                </div>
                <div className='flexInner'>
                    <input id="answer" placeholder='We do ...' autoComplete='family-name'
                        name="answer" 
                        type="text" required value={faq.answer || ""} onChange={(event)=> {
                            let newFaq: Faq = new Faq();
                            newFaq.question = faq.question;
                            newFaq.answer = event.target.value;
                            newFaq.id = faq.id;
                            newFaq.faqName = faq.faqName;
                            newFaq.createdDateTime = faq.createdDateTime;
                            newFaq.session = faq.session;
                            dispatch(updateFaq(newFaq))}
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
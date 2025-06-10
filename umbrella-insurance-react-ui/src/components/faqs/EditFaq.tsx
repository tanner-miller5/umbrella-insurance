import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { callDeleteFaqRestEndpointsByFaqId, callReadAllFaqsRestEndpoint, 
    callUpdateFaqRestEndpoints } from "../../endpoints/rest/faqs/v1/FaqRestEndpoints";
import { callCreateLoggingRestEndpoints } from "../../endpoints/rest/logging/v1/LoggingRestEndpoints";
import { Faq } from "../../models/faqs/v1/Faq";
import { LoggingMessage } from "../../models/logging/v1/LoggingMessage";
import { updateFaq } from "../../redux/reducers/FaqReducer";
import { updateIsErrorOpen, updateErrorMessage, updateCurrentPage, updateIsHamburgerMenuOpen, updateFaqs } from "../../redux/reducers/AppReducer";
import { updateLoadingState } from "../../redux/reducers/LoadingReducer";
import { RootState } from "../../redux/store/Store";
import { toObject } from "../../utils/Parser";

export default function EditFaq() {
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

    let faqs: Faq[] = useSelector((state: RootState) => {
        return state.app.faqs;
    })

    useEffect(
        function() {
            async function getFaqs() {
                const faqs = await callReadAllFaqsRestEndpoint(env, domain);
                if(faqs) {
                    dispatch(updateFaqs(toObject(faqs)));
                }
            };
            getFaqs();
        }, []
    );

    async function editFaqClicked() {
        try {
            dispatch(updateLoadingState(true));
            faq.createdDateTime = new Date().toISOString();
            let response: Faq[] = await callUpdateFaqRestEndpoints(
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
            dispatch(updateErrorMessage("Edit FAQ failed"));
            return 
        } finally {
            dispatch(updateLoadingState(false));
        }
        navigate("/faqs");
    }

    useEffect(
        function() {
            dispatch(updateCurrentPage("/editFaq"));
        }, []
    )
    //default values
    useEffect(function(){
        let newFaq = new Faq();
        newFaq.faqName = "";
        newFaq.question = "";
        newFaq.answer = "";
        dispatch(updateFaq(newFaq));
    },[]);

    function onClickBack() {
        navigate("/");
    }

    function onSelectChange(event: any) {
        for(let i = 0; i < faqs.length ; i++) {
            if(faqs[i].faqName === event.target.value) {
                dispatch(updateFaq(faqs[i]));
            }
        }

    }
    
    async function deleteFaqClicked() {
        try {
            dispatch(updateLoadingState(true));
            let response: boolean = await callDeleteFaqRestEndpointsByFaqId(
                faq.id || 1, env, domain, sessionCode);
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
            dispatch(updateErrorMessage("Delete FAQ failed"));
            return 
        } finally {
            dispatch(updateLoadingState(false));
        }
        navigate("/faqs");
    }

    let faqOptions = new Array();
    faqOptions.push(<option key="-" disabled value="">-</option>);
    for(let i = 0; i < faqs.length ; i++) {
        faqOptions.push(<option key={faqs[i].faqName} value={faqs[i].faqName}>{faqs[i].faqName}</option>);

    }

    return (
        <div className='column2' onClick={()=>dispatch(updateIsHamburgerMenuOpen(false))}>
            <form className="flexContainer" onSubmit={(e)=>{
                            e.preventDefault();
                            editFaqClicked();
                        }}>
                <h2>Edit FAQ</h2>
                <div className='flexInner'>
                    <label>FAQ Name to Edit</label> 
                </div>
                <select onChange={onSelectChange} defaultValue={""}>
                    {faqOptions}
                </select>
                <div className='flexInner'>
                    <label>FAQ Name</label> 
                </div>
                <div className='flexInner'>
                    <input id="faqName" name="faqName" 
                        placeholder='Feature x?'
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
                    <textarea id="question" placeholder='What is feature ...' autoComplete='family-name'
                        name="question" 
                        required value={faq?.question || ""} onChange={(event)=> {
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
                    <textarea id="answer" placeholder='Feature x does ...' autoComplete='family-name'
                        name="answer" 
                        required value={faq?.answer || ""} onChange={(event)=> {
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
                    <button id="deleteButton" name="action" onClick={deleteFaqClicked}
                            type="button" >Delete FAQ
                    </button>
                </div>
                <div className='flexInner'>
                    <button name="back" type="button" onClick={onClickBack}>Back</button>
                </div>
            </form>
        </div>);
};
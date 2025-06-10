import '../../css/faqs/faq.css';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateCurrentPage, updateFaqs } from '../../redux/reducers/AppReducer';
import { callReadAllFaqsRestEndpoint } from '../../endpoints/rest/faqs/v1/FaqRestEndpoints';
import { RootState } from '../../redux/store/Store';
import { toObject } from '../../utils/Parser';
import FaqRow from './FaqRow';

export default function Faq(){
    const dispatch = useDispatch();
    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const faqs = useSelector((state: RootState) => {
        return state.app.faqs;
    });
    useEffect(
        function() {
            dispatch(updateCurrentPage("/faq"));
        }, []
    )
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
    )
    const rows = [];
    for (let i = 0; i < faqs.length; i++) {
        // note: we are adding a key prop here to allow react to uniquely identify each
        // element in this array. see: https://reactjs.org/docs/lists-and-keys.html
        rows.push(<FaqRow key={i} question={faqs[i].question || ""} answer={faqs[i].answer || ""} />);
    }
    return (
        <div className='column2'>
            <h1 >FAQ</h1>
            {rows}
            <h1 >Contact Information</h1>
            <address >help@umbrella-insurance.com</address>
            <address >
                <br/> 
                <br/> 
            </address>
        </div>);
};
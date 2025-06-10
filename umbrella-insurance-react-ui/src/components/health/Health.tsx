import '../../css/faqs/faq.css';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { updateCurrentPage } from '../../redux/reducers/AppReducer';
import { RootState } from '../../redux/store/Store';

export default function Faq(){
    const dispatch = useDispatch();
    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    useEffect(
        function() {
            dispatch(updateCurrentPage("/health"));
        }, []
    )

    return (
        <div className='column2'>
            <h1 >UP</h1>
        </div>);
};
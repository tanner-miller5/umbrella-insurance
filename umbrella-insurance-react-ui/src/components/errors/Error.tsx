import '../../css/errors/error.css';
import React, { useEffect } from 'react';
import { updateCurrentPage, updateIsErrorOpen } from '../../redux/reducers/AppReducer';
import { useDispatch } from 'react-redux';
interface ErrorProps {
    message: string;
    isOpen: boolean;
}
export default function Error({message, isOpen}:ErrorProps){
    const dispatch = useDispatch();
    useEffect(
        function() {
            dispatch(updateCurrentPage("/error"));
        }, []
    )
    function closeError(){
        dispatch(updateIsErrorOpen(false));
    }
    return (
    <div className='overlay' hidden={!isOpen}>
        <div className='errorBox'>
            <h2>Error</h2>
            <p>{message}</p>
            <button onClick={closeError}>ok</button>
        </div>
    </div>);
};
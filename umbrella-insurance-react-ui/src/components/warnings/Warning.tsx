import '../../css/warnings/warning.css';
import React, { useEffect } from 'react';
import { updateCurrentPage, updateIsWarningOpen } from '../../redux/reducers/AppReducer';
import { useDispatch } from 'react-redux';
interface WarningProps {
    message: string;
    isOpen: boolean;
}
export default function Warning({message, isOpen}:WarningProps){
    const dispatch = useDispatch();
    useEffect(
        function() {
            dispatch(updateCurrentPage("/warning"));
        }, []
    )
    function closeWarning(){
        dispatch(updateIsWarningOpen(false));
    }
    return (
    <div className='overlay' hidden={!isOpen}>
        <div className='warningBox'>
            <h2>Warning</h2>
            <p>{message}</p>
            <button onClick={closeWarning}>ok</button>
        </div>
    </div>);
};
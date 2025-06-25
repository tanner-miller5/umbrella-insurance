import '../../css/loadings/loading.css';
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store/Store";
import React from 'react';
export default function Loading() {
    const isLoadingOpen = useSelector((state: RootState)=>{
        return state.loading.value
    });
    return (
        <div className="spinner-container">
            <div className="spinner"></div>
        </div>
    );
}
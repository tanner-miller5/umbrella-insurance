import { useEffect } from 'react';
import { updateCurrentPage, updatePerils } from '../../redux/reducers/AppReducer';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { toObject } from '../../utils/Parser';
import { callReadPerilRestEndpoints } from '../../endpoints/rest/perils/v1/PerilRestEndpoints';
import { useNavigate } from 'react-router-dom';
import { updateLoadingState } from '../../redux/reducers/LoadingReducer';
import SelectPerilRow from './SelectPerilRow';
import Loading from '../loadings/Loading';

export default function SelectPeril(){
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    const perils = useSelector((state: RootState) => {
        return state.app.perils;
    });
    let isLoadingOpen: boolean = useSelector((state: RootState)=>{
        return state.loading.value;
    });
    useEffect(
        function() {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            dispatch(updateCurrentPage("/selectPeril"));
        }, []
    );

    useEffect(
        function() {
            async function getPerils() {
                dispatch(updateLoadingState(true));
                if(perils.length === 0) {
                    const perilsResponse = await callReadPerilRestEndpoints(env, domain);
                    if(perilsResponse) {
                        dispatch(updatePerils(toObject(perilsResponse)));
                    }
                }
                dispatch(updateLoadingState(false));
            };
            getPerils();
        }, []
    )
    const rows = [];
    for (let i = 0; i < perils.length; i++) {
        // note: we are adding a key prop here to allow react to uniquely identify each
        // element in this array. see: https://reactjs.org/docs/lists-and-keys.html
        rows.push(<SelectPerilRow key={i} 
            perilName={perils[i].perilName || ""} 
            description={perils[i].description || ""} 
            scaleName={perils[i].scaleName || ""} 
            minMagnitude={perils[i].minMagnitude || 0} 
            maxMagnitude={perils[i].maxMagnitude || 0} 
            />);
    }
    function onClickBack() {
        navigate("/selectInsurerOrInsured");
    }
    if(isLoadingOpen) {
        return (
            <Loading />
        );
    }
    return (    
        <div className='column2'>
            <h1>Select a Peril Type</h1>
            {rows}
            <button onClick={onClickBack} type="button">Back</button>
        </div> 
    );
};
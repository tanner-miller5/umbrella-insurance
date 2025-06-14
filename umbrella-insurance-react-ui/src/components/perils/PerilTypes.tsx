import { useEffect } from 'react';
import { updateCurrentPage, updatePerils } from '../../redux/reducers/AppReducer';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { toObject } from '../../utils/Parser';
import { callReadPerilRestEndpoints } from '../../endpoints/rest/perils/v1/PerilRestEndpoints';
import SelectPerilRow from './SelectPerilRow';
import PerilTypeRow from './PerilTypeRow';

export default function PerilTypes(){
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
    useEffect(
        function() {
            dispatch(updateCurrentPage("/perilTypes"));
        }, []
    );

    useEffect(
        function() {
            async function getPerils() {
                const perils = await callReadPerilRestEndpoints(env, domain);
                if(perils) {
                    dispatch(updatePerils(toObject(perils)));
                }
            };
            getPerils();
        }, []
    )
    const rows = [];
    for (let i = 0; i < perils.length; i++) {
        // note: we are adding a key prop here to allow react to uniquely identify each
        // element in this array. see: https://reactjs.org/docs/lists-and-keys.html
        rows.push(<PerilTypeRow key={i} 
            perilName={perils[i].perilName || ""} 
            description={perils[i].description || ""} 
            scaleName={perils[i].scaleName || ""} 
            minMagnitude={perils[i].minMagnitude || 0} 
            maxMagnitude={perils[i].maxMagnitude || 0} 
            />);
    }
    return (    
            <div className='column2'>
                <h1>Peril Types</h1>
                {rows}
            </div> 
    );
};
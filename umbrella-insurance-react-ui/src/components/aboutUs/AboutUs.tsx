import { useEffect } from 'react';
import { updateCurrentPage } from '../../redux/reducers/AppReducer';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../../redux/store/Store';
import { toObject } from '../../utils/Parser';

export default function AboutUs(){
    const dispatch = useDispatch();

    const domain = useSelector((state: RootState) => {
        return state.environment.domain;
    });
    const env = useSelector((state: RootState) => {
        return state.environment.env;
    });
    useEffect(
        function() {
            dispatch(updateCurrentPage("/aboutUs"));
        }, []
    );


    return (    
            <div className='column2'>
                <h1>About Us</h1>
                <p>Marcus Miller started the Umbrella Insurance project to help communities that
                    lack traditional insurance coverage options. Marcus enjoys hiking, rock climbing, scuba diving, and golfing. 
                    He currently attends ASU for a Masters in Computer Science. He enjoys walking his dog and hanging out with
                    friends and family. 
                </p>
            </div> 
    );
};
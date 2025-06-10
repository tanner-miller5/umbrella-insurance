import '../../css/loadings/loading.css';
import { useSelector } from "react-redux";
import { RootState } from "../../redux/store/Store";
export default function Loading() {
    const isLoadingOpen = useSelector((state: RootState)=>{
        return state.loading.value
    });
    return (
        <div className="loadingBackground">
            <img src="Logo-02.png" className="loading"></img>
        </div>
    );
}
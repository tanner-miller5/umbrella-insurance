interface PerilTypeRowProps {
    perilName: string;
    description?: string;
    scaleName?: string;
    minMagnitude?: number;
    maxMagnitude?: number;
}
export default function PerilTypeRow({perilName,
    description,
    scaleName,
    minMagnitude,
    maxMagnitude
    }:PerilTypeRowProps){

    return (    
        <div>
            <button style={{cursor: "default"}} >
                {perilName} <br/>
                {description}<br/>
                Scale Name: {scaleName}<br/>
                Min Magnitude: {minMagnitude}<br/>
                Max Magnitude: {maxMagnitude}
            </button>
        </div>);
}
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
            <p >{perilName}: {description}<br/>
            {scaleName}: Min Magnitude = {minMagnitude} - Max Magnitude = {maxMagnitude}</p>
        </div>);
}
interface PerilRowProps {
    perilName: string;
    description?: string;
    scaleName?: string;
    minMagnitude?: number;
    maxMagnitude?: number;
}
export default function PerilRow({perilName,
    description,
    scaleName,
    minMagnitude,
    maxMagnitude
    }:PerilRowProps){

    return (    
        <div>
            <p >{perilName}: {description}<br/>
            {scaleName}: Min Magnitude = {minMagnitude} - Max Magnitude = {maxMagnitude}</p>
        </div>);
}
interface PerilRowProps {
    perilName: string;
}
export default function PerilRow({perilName}:PerilRowProps){

    return (    
    <div>
        <p >{(perilName.charAt(0).toUpperCase() + perilName.slice(1)).replaceAll("_"," ")}</p>
    </div>);
}
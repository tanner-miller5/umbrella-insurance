interface FaqRowProps {
    question: string;
    answer: string;
}
export default function FaqRow({question, answer}:FaqRowProps){

    return (    
    <div>
        <p >{question}<br/>
        {answer}</p>
    </div>);
}
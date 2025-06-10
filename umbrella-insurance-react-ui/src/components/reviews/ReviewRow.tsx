interface ReviewRowProps {
    subject: string;
    comment: string;
    rating: number;
    date: string;
}
export default function ReviewRow({subject, comment, rating, date}:ReviewRowProps){

    return (    
    <div className="outlined">
        <h2>Subject: {subject}</h2>
        <p>Date: {date}</p>
        <p>Comment: {comment}</p>
        <p>Rating: {rating}</p>
    </div>);
}
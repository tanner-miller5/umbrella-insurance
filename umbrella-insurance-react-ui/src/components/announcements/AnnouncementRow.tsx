interface AnnouncementRowProps {
    message: string;
    createdDateTime: string;
}
export default function AnnouncementRow({message, createdDateTime}:AnnouncementRowProps){

    return (    
    <div>
        <p >{createdDateTime.split("T")[0]}<br/>{message}</p>
    </div>);
}
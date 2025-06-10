import moment from "moment";
//yyyy-mm-dd hh:mm:ss
function getCreatedDateTime() {
    return moment().format('YYYY-MM-DD HH:mm:ss');
}
import { City } from "../../cities/v1/City";
import { Country } from "../../countries/v1/Country";
import { State } from "../../states/v1/State";
import { StreetAddress } from "../../streetAddresses/v1/StreetAddress";
import { ZipCode } from "../../zipCodes/v1/ZipCode";

export class Location {
    id?: number;
    streetAddress?: StreetAddress;
    city?: City;
    state?: State;
    zipCode?: ZipCode;
    country?: Country;
    locationName?: string;

    constructor(obj?:any) {
        if(obj) {
            if(obj?.id) {
                this.id = obj.id;
            }
            if(obj?.locationName) {
                this.locationName = obj.locationName;
            }
            if(obj?.streetAddress) {
                this.streetAddress = new StreetAddress(obj.streetAddress);
            }
            if(obj?.city) {
                this.city = new City(obj.city);
            }
            if(obj?.state) {
                this.state = new State(obj.state);
            }
            if(obj?.zipCode) {
                this.zipCode = new ZipCode(obj.zipCode);
            }
            if(obj?.locationName) {
                this.locationName = obj.locationName;
            }
        }
    }
}
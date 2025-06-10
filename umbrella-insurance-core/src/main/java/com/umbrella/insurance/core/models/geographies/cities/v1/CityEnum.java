package com.umbrella.insurance.core.models.geographies.cities.v1;

public enum CityEnum {
    Pittsburgh,
    Paradise,
    Kansas_City,
    Arlington,
    Charlotte,
    New_Orleans,
    Denver,
    Jacksonville,
    Detroit,
    Foxborough,
    Miami_Gardens,
    Orchard_Park,
    Cleveland,
    Green_Bay,
    Santa_Clara,
    Philadelphia,
    Indianapolis,
    Seattle,
    Baltimore,
    Atlanta,
    East_Rutherford,
    Nashville,
    Landover,
    Houston,
    Cincinnati,
    Tampa,
    Inglewood,
    Chicago,
    Glendale,
    Minneapolis;
    public String toString() {
        return this.name().replace("_", " ");
    }

}

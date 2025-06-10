package com.umbrella.insurance.core.models.dates.holidays.v1;

public enum HolidayEnum {
    New_Years_Day,
    Independence_Day,
    Veterans_Day,
    Christmas_Day;
    public String toString() {
        return this.name().replace("_", " ");
    }
}

package com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.StreetAddress;
import java.util.List;
import java.util.Optional;

public interface StreetAddressService {
    StreetAddress saveStreetAddress(StreetAddress streetAddress);
    List<StreetAddress> getStreetAddresses();
    StreetAddress updateStreetAddress(StreetAddress streetAddress);
    void deleteStreetAddress(Long streetAddressId);
    Optional<StreetAddress> getStreetAddressByStreetAddressLine1AndStreetAddressLine2(
            String streetAddressLine1, String streetAddressLine2);
    Optional<StreetAddress> getStreetAddressByStreetAddressId(Long streetAddressId);
    void deleteStreetAddressByStreetAddressLine1AndStreetAddressLine2(
            String streetAddressLine1, String streetAddressLine2);
}

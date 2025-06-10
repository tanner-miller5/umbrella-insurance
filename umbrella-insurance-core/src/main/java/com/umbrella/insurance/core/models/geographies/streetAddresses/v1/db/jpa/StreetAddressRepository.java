package com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.StreetAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreetAddressRepository extends JpaRepository<StreetAddress, Long> {
    Optional<StreetAddress> getStreetAddressByStreetAddressLine1AndStreetAddressLine2(
            String streetAddressLine1, String streetAddressLine2
    );
    void deleteStreetAddressByStreetAddressLine1AndStreetAddressLine2(String streetAddressLine1,
                                                                      String streetAddressLine2);
}

package com.umbrella.insurance.core.models.geographies.streetAddresses.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.StreetAddress;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StreetAddressServiceImpl implements StreetAddressService {
    @Autowired
    StreetAddressRepository streetAddressRepository;

    @Override
    public StreetAddress saveStreetAddress(StreetAddress streetAddress) {
        return streetAddressRepository.save(streetAddress);
    }

    @Override
    public List<StreetAddress> getStreetAddresses() {
        return streetAddressRepository.findAll();
    }

    @Override
    public StreetAddress updateStreetAddress(StreetAddress streetAddress) {
        return streetAddressRepository.save(streetAddress);
    }

    @Override
    public void deleteStreetAddress(Long streetAddressId) {
        streetAddressRepository.deleteById(streetAddressId);
    }

    @Override
    public Optional<StreetAddress> getStreetAddressByStreetAddressLine1AndStreetAddressLine2(
            String streetAddressLine1, String streetAddressLine2) {
        return streetAddressRepository.getStreetAddressByStreetAddressLine1AndStreetAddressLine2(
                streetAddressLine1, streetAddressLine2
        );
    }

    @Override
    public Optional<StreetAddress> getStreetAddressByStreetAddressId(Long streetAddressId) {
        return streetAddressRepository.findById(streetAddressId);
    }

    @Override
    public void deleteStreetAddressByStreetAddressLine1AndStreetAddressLine2(String streetAddressLine1, String streetAddressLine2) {
        streetAddressRepository.deleteStreetAddressByStreetAddressLine1AndStreetAddressLine2(
                streetAddressLine1, streetAddressLine2);
    }
}

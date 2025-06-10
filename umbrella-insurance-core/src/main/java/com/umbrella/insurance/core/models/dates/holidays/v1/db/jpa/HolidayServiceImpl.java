package com.umbrella.insurance.core.models.dates.holidays.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.Holiday;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    HolidayRepository holidayRepository;

    @Override
    public Holiday saveHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    @Override
    public List<Holiday> getHolidays() {
        return holidayRepository.findAll();
    }

    @Override
    public Holiday updateHoliday(Holiday holiday) {
        return holidayRepository.save(holiday);
    }

    @Override
    public void deleteHoliday(Long holidayId) {
        holidayRepository.deleteById(holidayId);
    }

    @Override
    public Optional<Holiday> getHolidayByHolidayName(String holidayName) {
        return holidayRepository.findByHolidayName(holidayName);
    }

    @Override
    public void deleteHolidayByHolidayName(String holidayName) {
        holidayRepository.deleteHolidayByHolidayName(holidayName);
    }

    @Override
    public Optional<Holiday> getHolidayById(Long holidayId) {
        return holidayRepository.findById(holidayId);
    }
}

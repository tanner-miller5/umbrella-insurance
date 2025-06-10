package com.umbrella.insurance.core.models.records.v1.db.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umbrella.insurance.core.models.entities.Record;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RecordServiceImpl implements RecordService  {
    @Autowired
    RecordRepository recordRepository;

    @Override
    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public List<Record> getRecords() {
        return recordRepository.findAll();
    }

    @Override
    public Record updateRecord(Record record) {
        return recordRepository.save(record);
    }

    @Override
    public void deleteRecord(Long recordId) {
        recordRepository.deleteById(recordId);
    }

    @Override
    public Optional<Record> getRecordByRecordName(String recordName) {
        return recordRepository.findByRecordName(recordName);
    }

    @Override
    public Optional<Record> getRecordById(Long recordId) {
        return recordRepository.findById(recordId);
    }

    @Override
    public void deleteRecordByRecordName(String recordName) {
        recordRepository.deleteRecordByRecordName(recordName);
    }
}

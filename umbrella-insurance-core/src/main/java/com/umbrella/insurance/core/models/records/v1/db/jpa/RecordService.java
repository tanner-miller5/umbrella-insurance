package com.umbrella.insurance.core.models.records.v1.db.jpa;

import com.umbrella.insurance.core.models.entities.Record;
import java.util.List;
import java.util.Optional;

public interface RecordService {
    Record saveRecord(Record record);
    List<Record> getRecords();
    Record updateRecord(Record record);
    void deleteRecord(Long recordId);
    Optional<Record> getRecordByRecordName(String recordName);
    Optional<Record> getRecordById(Long recordId);
    void deleteRecordByRecordName(String recordName);
}

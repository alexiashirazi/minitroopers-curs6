package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.ScannerType;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Map;
@ApplicationScoped
public class DiagnosticScanTaskCreator implements MaintenanceTaskCreator {

    @Override
    public TaskType getTaskType() {
        return TaskType.DIAGNOSTIC_SCAN;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        Object codesObj = additionalData.get("errorCodes");
        Object scannerObj = additionalData.get("scannerType");

        if (!(codesObj instanceof List<?> errorCodes)) {
            throw new IllegalArgumentException("DiagnosticScan requires 'errorCodes' in additionalData");
        }
        if (!(scannerObj instanceof ScannerType scannerType)) {
            throw new IllegalArgumentException("DiagnosticScan requires 'scannerType' in additionalData");
        }

        @SuppressWarnings("unchecked")
        List<String> codes = (List<String>) errorCodes;

        return MaintenanceTask.createDiagnosticScan(vin, notes, codes, scannerType);

    }
}

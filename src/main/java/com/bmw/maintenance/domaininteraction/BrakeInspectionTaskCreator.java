package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class BrakeInspectionTaskCreator implements MaintenanceTaskCreator {


    @Override
    public TaskType getTaskType() {
        return TaskType.BRAKE_INSPECTION;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        return MaintenanceTask.createBrakeInspection(vin, notes);
    }
}

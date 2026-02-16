package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;
import com.bmw.maintenance.domain.TirePosition;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;
@ApplicationScoped
public class TireServiceTaskCreator implements MaintenanceTaskCreator {
    @Override
    public TaskType getTaskType() {
        return TaskType.TIRE_SERVICE;
    }

    @Override
    public MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData) {
        Object posObj = additionalData.get("tirePosition");
        if (!(posObj instanceof TirePosition tirePosition)) {
            throw new IllegalArgumentException("TireService requires 'tirePosition' in additionalData");
        }

        return MaintenanceTask.createTireService(vin, notes, tirePosition);

    }
}

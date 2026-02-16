package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskType;

import java.util.Map;

public interface MaintenanceTaskCreator {

    TaskType getTaskType();

    MaintenanceTask create(String vin, String notes, Map<String, Object> additionalData);

}

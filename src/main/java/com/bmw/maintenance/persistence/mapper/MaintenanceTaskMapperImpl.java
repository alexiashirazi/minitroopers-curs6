package com.bmw.maintenance.persistence.mapper;

import jakarta.enterprise.context.ApplicationScoped;

import com.bmw.maintenance.domain.MaintenanceTask;

@ApplicationScoped
public class MaintenanceTaskMapperImpl implements MaintenanceTaskMapper {


    @Override
    public MaintenanceTask toDomain(MaintenanceTaskSchemaVLatest.MaintenanceTask schema) {
        if (schema == null) return null;

        return MaintenanceTask.reconstitute(
                schema.getTaskId(),
                schema.getVin(),
                schema.getType(),
                schema.getStatus(),
                schema.getNotes(),
                schema.getTirePosition(),
                schema.getErrorCodes(),
                schema.getScannerType()
        );
    }

    @Override
    public MaintenanceTaskSchemaVLatest.MaintenanceTask toSchema(MaintenanceTask task) {
        if (task == null) return null;

        MaintenanceTaskSchemaVLatest.MaintenanceTask schema =
                new MaintenanceTaskSchemaVLatest.MaintenanceTask();

        schema.setTaskId(task.getTaskId());
        schema.setVin(task.getVin());
        schema.setType(task.getType());
        schema.setStatus(task.getStatus());
        schema.setNotes(task.getNotes());
        schema.setTirePosition(task.getTirePosition());
        schema.setErrorCodes(task.getErrorCodes());
        schema.setScannerType(task.getScannerType());

        return schema;
    }

}

package com.bmw.maintenance.persistence.mapper;

import com.bmw.maintenance.domain.MaintenanceTask;

/**
 * Maps between {@link MaintenanceTask} domain objects and {@link MaintenanceTask} persistence entities\.
 */
public interface MaintenanceTaskMapper {
    /**
     * Converts a persistence entity to a domain object\.
     *
     * @param schema the persistence entity
     * @return the domain object
     */
    MaintenanceTask toDomain(MaintenanceTaskSchemaVLatest.MaintenanceTask schema);

    /**
     * Converts a domain object to a persistence entity\.
     *
     * @param task the domain object
     * @return the persistence schema
     */
    MaintenanceTaskSchemaVLatest.MaintenanceTask toSchema(MaintenanceTask task);
}

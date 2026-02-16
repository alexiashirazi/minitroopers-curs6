package com.bmw.maintenance.domaininteraction;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.domain.TaskType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

/**
 * Service for creating and managing maintenance tasks.
 */
@ApplicationScoped
public class MaintenanceTaskService {

    private final MaintenanceTasks maintenanceTasks;
    private final Map<TaskType, MaintenanceTaskCreator> creatorMap;

    /**
     * Creates a new service instance.
     *
     * @param maintenanceTasks backing repository
     */
    @Inject
    public MaintenanceTaskService(MaintenanceTasks maintenanceTasks,
                                  Instance<MaintenanceTaskCreator> creators) {
        this.maintenanceTasks = maintenanceTasks;
        this.creatorMap = new HashMap<>();

        creators.forEach(c -> creatorMap.put(c.getTaskType(), c));
    }
    /**
     * Creates a maintenance task for a vehicle.
     *
     * @param vin   vehicle identification number
     * @param type  task type
     * @param notes optional notes
     * @return created task id
     */
    public MaintenanceTask createTask(String vin, TaskType type, String notes, Map<String, Object> additionalData) {
        MaintenanceTaskCreator creator = creatorMap.get(type);

        if (creator == null) {
        throw new IllegalArgumentException("No creator found for task type: " + type);
        }

        MaintenanceTask task = creator.create(vin, notes, additionalData);
        return maintenanceTasks.create(task);

    }



    /**
     * Updates the status of a task.
     *
     * @param taskId    task id
     * @param newStatus new status
     */
    public void updateTaskStatus(String taskId, TaskStatus newStatus) {
        maintenanceTasks.updateStatus(taskId, newStatus);
    }

    /**
     * Adds or updates notes for a task.
     *
     * @param taskId task id
     * @param notes  notes to store
     */
    public void addOrUpdateNotes(String taskId, String notes) {
        maintenanceTasks.upsertNotes(taskId, notes);
    }

    /**
     * Gets a task by id.
     *
     * @param taskId task id
     * @return task or null if not found
     */
    public MaintenanceTask getTaskById(String taskId) {
        return maintenanceTasks.findById(taskId);
    }

    /**
     * Lists tasks for a VIN or all tasks when VIN is blank.
     *
     * @param vin vehicle identification number
     * @return matching tasks
     */
    public List<MaintenanceTask> listTasks(String vin) {
        if (vin != null && !vin.isBlank()) {
            return maintenanceTasks.findByVin(vin);
        }
        return maintenanceTasks.getAllTasks();
    }
}
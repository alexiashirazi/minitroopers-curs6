package com.bmw.maintenance.persistence;

import com.bmw.maintenance.commons.serialization.VersionedSchemaSerDes;
import com.bmw.maintenance.domaininteraction.MaintenanceTasks;
import com.bmw.maintenance.persistence.mapper.MaintenanceTaskMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import com.bmw.maintenance.domain.MaintenanceTask;
import com.bmw.maintenance.domain.TaskStatus;
import com.bmw.maintenance.persistence.mapper.MaintenanceTaskSchemaVLatest;

@ApplicationScoped

public class MaintenanceTaskJpaRepository implements MaintenanceTasks
 {
     @Inject
     MaintenanceTaskPanacheRepository panacheRepository;

     @Inject
     MaintenanceTaskMapper mapper;

     @Inject
     VersionedSchemaSerDes<String> serializer;



     private MaintenanceTaskSchemaVLatest.MaintenanceTask loadSchema(
             MaintenanceTaskEntity entity) {

         return (MaintenanceTaskSchemaVLatest.MaintenanceTask)
                 serializer.deserialize(entity.getAggregate());
     }



     @Override
     @Transactional
     public MaintenanceTask create(MaintenanceTask task) {

         MaintenanceTaskSchemaVLatest.MaintenanceTask schema =
                 mapper.toSchema(task);

         MaintenanceTaskEntity entity = new MaintenanceTaskEntity();
         entity.setCreatedAt(LocalDateTime.now());
         entity.setUpdatedAt(LocalDateTime.now());

         panacheRepository.persist(entity);

         schema.setTaskId(entity.getId());

         String serializedAggregate = serializer.serialize(schema);
         entity.setAggregate(serializedAggregate);

         return mapper.toDomain(schema);
     }

     @Override
     public MaintenanceTask findById(String taskId) {

     Long id = Long.parseLong(taskId);

     MaintenanceTaskEntity entity =
             panacheRepository.findById(id);

     if (entity == null) {
         throw new NotFoundException("Task not found: " + taskId);
     }

         var schema = loadSchema(entity);

         return mapper.toDomain(schema);
 }
     @Override
     @Transactional
     public MaintenanceTask updateStatus(String taskId,
         TaskStatus newStatus) {

     Long id = Long.parseLong(taskId);

     MaintenanceTaskEntity entity =
             panacheRepository.findById(id);

     if (entity == null) {
         throw new NotFoundException("Task not found: " + taskId);
     }

         var schema = loadSchema(entity);

         schema.setStatus(newStatus);

     entity.setAggregate(serializer.serialize(schema));
     entity.setUpdatedAt(LocalDateTime.now());

     return mapper.toDomain(schema);
 }
     @Override
     @Transactional
     public MaintenanceTask upsertNotes(String taskId,
         String notes) {

     Long id = Long.parseLong(taskId);

     MaintenanceTaskEntity entity =
             panacheRepository.findById(id);

     if (entity == null) {
         throw new NotFoundException("Task not found: " + taskId);
     }

         var schema = loadSchema(entity);

         schema.setNotes(notes);

     entity.setAggregate(serializer.serialize(schema));
     entity.setUpdatedAt(LocalDateTime.now());

     return mapper.toDomain(schema);
 }
     @Override
     public List<MaintenanceTask> getAllTasks() {

     return panacheRepository.listAll()
             .stream()
             .map(entity -> {
                 var schema = loadSchema(entity);

                 return mapper.toDomain(schema);
             })
             .toList();
 }
     @Override
     public List<MaintenanceTask> findByVin(String vin) {

     return panacheRepository.listAll()
             .stream()
             .map(entity -> {
                 var schema = loadSchema(entity);

                 return mapper.toDomain(schema);
             })
             .filter(task -> vin.equals(task.getVin()))
             .toList();
 }

 }

package ru.kpfu.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteTargetException;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteTaskException;
import ru.kpfu.itis.exceptions.repository.insert.CannotInsertTaskException;
import ru.kpfu.itis.models.TaskEntity;
import ru.kpfu.itis.repositories.TaskRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=sql
    private static final String SQL_INSERT = "insert into task " +
            "(id, name, description, assigner_id, status) " +
            "values ('%s', '%s', '%s', '%s', '%s')";

    //language=sql
    private static final String SQL_GET_ALL = "select * from task";

    //language=sql
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " where id = '%s'";

    //language=sql
    private static final String SQL_GET_ALL_TASKS_BY_TARGET_ID = "select * from task " +
            "where id in (select task_id from \"target/task\" where target_id = '%s')";

    //language=sql
    private static final String SQL_DELETE_ALL_BY_TARGET_ID = "delete from \"target/task\" where target_id = '%s'";

    //language=sql
    private static final String SQL_DELETE = "delete from task where id = '%s'";

    //language=sql
    private static final String SQL_DELETE_FROM_TARGET = "delete from \"target/task\" where task_id = '%s'";


    private static final RowMapper<TaskEntity> toTask = (rs, rowNum) ->
            TaskEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .name(rs.getString("name"))
                    .description(rs.getString("description"))
                    .dateOfCreating(rs.getDate("date_of_creating"))
                    .dateOfUpdating(rs.getDate("date_of_updating"))
                    .dateOfApproval(rs.getDate("date_of_approval"))
                    .endDate(rs.getDate("end_date"))
                    .status(rs.getString("status"))
                    .build();

    @Override
    public UUID create(TaskEntity model) {
        UUID uuid = UUID.randomUUID();

        int affectedRows = jdbcTemplate.update(SQL_INSERT.formatted(
                uuid,
                model.getName(),
                model.getDescription(),
                model.getAssigner().getId(),
                model.getStatus()
                ));

        if (affectedRows != 1)
            throw new CannotInsertTaskException();

        model.setId(uuid);

        return uuid;
    }

    @Override
    public Optional<TaskEntity> findById(UUID uuid) {
        try (val stream = jdbcTemplate.queryForStream(SQL_GET_BY_ID.formatted(uuid), toTask)) {
            return stream.findAny();
        }
    }

    @Override
    public List<TaskEntity> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, toTask);
    }

    @Override
    public Page<TaskEntity> findAll() {
        return new PageImpl<>(getAll());
    }

    @Override
    public void update(UUID uuid, TaskEntity model) {

    }

    @Override
    public void delete(UUID uuid) {
        deleteFromTarget(uuid);
        int affectedRow = jdbcTemplate.update(SQL_DELETE.formatted(uuid));

        if (affectedRow != 1)
            throw new CannotDeleteTaskException();
    }

    private void deleteFromTarget(UUID uuid) {
        int affectedRows = jdbcTemplate.update(SQL_DELETE_FROM_TARGET.formatted(uuid));

        if (affectedRows != 1)
            throw new CannotDeleteTaskException();
    }

    @Override
    public List<TaskEntity> getAllByTargetId(UUID targetUUID) {
        return jdbcTemplate.query(SQL_GET_ALL_TASKS_BY_TARGET_ID.formatted(targetUUID), toTask);
    }

    @Override
    public void deleteAllByTargetId(UUID targetUUID) throws CannotDeleteTaskException {
        List<UUID> tasksIdThatBelongToTarget = getAllByTargetId(targetUUID).stream()
                .map(TaskEntity::getId)
                .toList();
        tasksIdThatBelongToTarget.forEach(this::delete);
    }

}

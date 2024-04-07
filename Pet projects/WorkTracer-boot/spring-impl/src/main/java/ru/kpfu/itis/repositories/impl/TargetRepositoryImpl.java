package ru.kpfu.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteTargetException;
import ru.kpfu.itis.exceptions.repository.insert.CannotInsertTargetException;
import ru.kpfu.itis.exceptions.repository.insert.CannotInsertTaskException;
import ru.kpfu.itis.models.AccountEntity;
import ru.kpfu.itis.models.AddressEntity;
import ru.kpfu.itis.models.TargetEntity;
import ru.kpfu.itis.models.TaskEntity;
import ru.kpfu.itis.repositories.AccountRepository;
import ru.kpfu.itis.repositories.TargetRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class TargetRepositoryImpl implements TargetRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=sql
    private static final String SQL_INSERT_TARGET = "insert into target " +
            "(id, name, description, approximate_deadline) values " +
            "('%s', '%s', '%s', '%s')";

    //language=sql
    private static final String SQL_INSERT_TASK = "insert into \"target/task\" (task_id, target_id) values ('%s', '%s')";

    //language=sql
    private static final String SQL_INSERT_RESPONSIBLES = "insert into responsible (account_id, target_id) values (?, ?)";

    //language=sql
    private static final String SQL_INSERT_SPECTATORS = "insert into spectator (account_id, target_id) values (?, ?)";

    //language=sql
    private static final String SQL_GET_ALL = "select * from target";

    //language=sql
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " where id = '%s'";

    //language=sql
    private static final String SQL_UPDATE = "";

    //language=sql
    private static final String SQL_DELETE = "delete from target where id = '%s'";

    //language=sql
    private static final String SQL_GET_ALL_BY_ACCOUNT_ID = "select * from target " +
            "where id in " +
            "(select distinct target_id from " +
            "(select target_id from responsible where account_id = '%s' " +
            "union " +
            "select target_id from executor where account_id = '%s' " +
            "union " +
            "select target_id from spectator where account_id = '%s') as target_id)";

    //language=sql
    private static final String SQL_DELETE_ALL_RESPONSIBLES_BY_ACCOUNT_ID = "delete from responsible " +
            "where account_id = '%s'";

    //language=sql
    private static final String SQL_DELETE_ALL_EXECUTORS_BY_ACCOUNT_ID = "delete from executor where account_id = '%s'";

    //language=sql
    private static final String SQL_DELETE_ALL_SPECTATORS_BY_ACCOUNT_ID = "delete from spectator where account_id = '%s'";

    //language=sql
    private static final String SQL_DOES_TARGET_HAVE_PARTICIPANT = "select count() != 0 from " +
            "(select account_id from spectator where target_id = '%s' " +
            "union " +
            "select account_id from responsible where target_id = '%s' " +
            "union " +
            "select account_id from executor where target_id = '%s') as account_id";

    //language=sql
    private static final String SQL_DELETE_ALL_PARTICIPANTS =
            "delete from executor where target_id = '%s';" +
            "delete from spectator where target_id = '%s';" +
            "delete from responsible where target_id = '%s';";

    private static final RowMapper<TargetEntity> toTarget = (rs, rowNum) ->
            TargetEntity.builder()
            .id(UUID.fromString(rs.getString("id")))
            .name(rs.getString("name"))
            .description(rs.getString("description"))
            .dateOfCreating(rs.getDate("date_of_creating"))
            .dateOfUpdating(rs.getDate("date_of_updating"))
            .approximateDeadline(rs.getDate("approximate_deadline"))
            .actualDeadline(rs.getDate("actual_deadline"))
            .build();

    @Override
    public UUID create(TargetEntity model) {
        UUID uuid = UUID.randomUUID();
        int affectedRows = jdbcTemplate.update(SQL_INSERT_TARGET.formatted(
                uuid,
                model.getName(),
                model.getDescription(),
                model.getApproximateDeadline()
        ));

        if (affectedRows != 1)
            throw new CannotInsertTargetException();

        model.setId(uuid);

        batchInsert(SQL_INSERT_RESPONSIBLES, model.getResponsibles(), uuid);
        batchInsert(SQL_INSERT_SPECTATORS, model.getSpectators(), uuid);

        return uuid;
    }

    @Override
    public Optional<TargetEntity> findById(UUID uuid) {
        try (val stream = jdbcTemplate.queryForStream(SQL_GET_BY_ID, toTarget)) {
            return stream.findAny();
        }
    }

    @Override
    public List<TargetEntity> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, toTarget);
    }

    @Override
    public Page<TargetEntity> findAll() {
        return new PageImpl<>(getAll());
    }

    @Override
    public void update(UUID uuid, TargetEntity model) {

    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update(SQL_DELETE_ALL_PARTICIPANTS.replace("%s", uuid.toString()));
        int affectedRows = jdbcTemplate.update(SQL_DELETE.formatted(uuid));

        if (affectedRows != 1)
            throw new CannotDeleteTargetException();
    }


    @Override
    public void deleteAllResponsiblesByAccountId(UUID accountUUID) {
        jdbcTemplate.update(SQL_DELETE_ALL_RESPONSIBLES_BY_ACCOUNT_ID.formatted(accountUUID));
    }

    @Override
    public void deleteAllExecutorsByAccountId(UUID accountUUID) {
        jdbcTemplate.update(SQL_DELETE_ALL_EXECUTORS_BY_ACCOUNT_ID.formatted(accountUUID));
    }

    @Override
    public List<TargetEntity> getAllByAccountId(UUID accountUUID) {
        return jdbcTemplate.query(SQL_GET_ALL_BY_ACCOUNT_ID.replace("%s", accountUUID.toString()), toTarget);
    }

    @Override
    public void deleteAllSpectatorsByAccountId(UUID accountUUID) {
        jdbcTemplate.update(SQL_DELETE_ALL_SPECTATORS_BY_ACCOUNT_ID.formatted(accountUUID));
    }

    @Override
    public boolean doesTargetHaveParticipant(UUID targetUUID) {
        return Boolean.TRUE.equals(
                jdbcTemplate.queryForObject(
                        SQL_DOES_TARGET_HAVE_PARTICIPANT.replace("%s", targetUUID.toString()),
                        Boolean.class
                )
        );
    }

    @Override
    public void createTask(UUID taskUUID, UUID targetUUID) {
        int affectedRows = jdbcTemplate.update(SQL_INSERT_TASK.formatted(taskUUID, taskUUID));

        if (affectedRows != 1)
            throw new CannotInsertTaskException();
    }


    private void batchInsert(String sql, List<AccountEntity> accountEntities, UUID targetUUID) {
        if (accountEntities.isEmpty()) return;
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, accountEntities.get(i).getId().toString());
                ps.setString(2, targetUUID.toString());
            }

            @Override
            public int getBatchSize() {
                return accountEntities.size();
            }
        });
    }
}

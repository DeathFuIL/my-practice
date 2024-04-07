package ru.kpfu.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteDialogException;
import ru.kpfu.itis.exceptions.repository.insert.CannotInsertDialogException;
import ru.kpfu.itis.models.DialogEntity;
import ru.kpfu.itis.repositories.DialogRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DialogRepositoryImpl implements DialogRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=sql
    private static final String SQL_INSERT = "insert into dialog (id) values ('%s')";

    //language=sql
    private static final String SQL_GET_ALL = "select * from dialog";

    //language=sql
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " where id = '%s'";

    //language=sql
    private static final String SQL_GET_BY_ACCOUNT_ID = SQL_GET_ALL + " where id in " +
            "(select account_id from \"account/dialog\" where dialog_id = '%s')";

    //language=sql
    private static final String SQL_DELETE = "delete from dialog where id = '%s'";

    //language=sql
    private static final String SQL = "";

    private static final RowMapper<DialogEntity> toDialog = (rs, rowNum) ->
            DialogEntity.builder()
                    .id(UUID.fromString("id"))
                    .build();

    @Override
    public UUID create(DialogEntity model) {
        UUID uuid = UUID.randomUUID();
        int affectedRows = jdbcTemplate.update(SQL_INSERT.formatted(uuid));

        if (affectedRows != 1)
            throw new CannotInsertDialogException();

        return uuid;
    }

    @Override
    public Optional<DialogEntity> findById(UUID uuid) {
        try (val stream = jdbcTemplate.queryForStream(SQL_GET_BY_ID.formatted(uuid), toDialog)) {
            return stream.findAny();
        }
    }

    @Override
    public List<DialogEntity> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, toDialog);
    }

    @Override
    public Page<DialogEntity> findAll() {
        return null;
    }

    @Override
    public void update(UUID uuid, DialogEntity model) {
        throw new UnsupportedOperationException("This method must not be used in this class");
    }

    @Override
    public void delete(UUID uuid) {
        int affectedRows = jdbcTemplate.update(SQL_DELETE.formatted(uuid));

        if (affectedRows != 1)
            throw new CannotDeleteDialogException();
    }
}

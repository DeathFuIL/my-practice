package ru.kpfu.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteMessageException;
import ru.kpfu.itis.exceptions.repository.insert.CannotInsertMessageException;
import ru.kpfu.itis.exceptions.repository.update.CannotUpdateMessageException;
import ru.kpfu.itis.models.MessageEntity;
import ru.kpfu.itis.repositories.MessageRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=sql
    private static final String SQL_INSERT = "insert into message (id, text) " +
            "values ('%s', '%s')";

    //language=sql
    private static final String SQL_GET_ALL = "select * from message left join reply r on r.message_id = message.id";

    //language=sql
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " where id = '%s'";

    //language=sql
    private static final String SQL_UPDATE = "update message set text = '%s' where id = '%s'";

    //language=sql
    private static final String SQL_DELETE = "delete from message where id = '%s'";

    //language=sql
    private static final String SQL_GET_ALL_BY_DIALOG_ID = SQL_GET_ALL + " where message.id in " +
            "(select message_id from \"dialog/message\" where dialog_id = '%s')";

    //language=sql
    private static final String SQL_DELETE_FROM_DIALOG = "delete from \"dialog/message\" where message_id = '%s'";

    //language=sql
    private static final String SQL_SET_MESSAGE = "insert into \"account/message\" (account_id, message_id) values ('%s', '%s')";

    private static final RowMapper<MessageEntity> toMessage = (rs, rowNum) ->
            MessageEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .text(rs.getString("text"))
                    .replyTo(UUID.fromString(rs.getString("replies_to")))
                    .dateOfCreating(rs.getDate("date_of_creating"))
                    .dateOfUpdating(rs.getDate("date_of_updating"))
                    .build();
    /*todo сделать сервис для диалога и сообщений, после получения диалога
    * вставить участников в сервисе
    */

    @Override
    public UUID create(MessageEntity model) {
        UUID uuid = UUID.randomUUID();
        int affectedRows = jdbcTemplate.update(SQL_INSERT.formatted(uuid, model.getText()));

        if (affectedRows != 1)
            throw new CannotInsertMessageException();

        return uuid;
    }

    @Override
    public Optional<MessageEntity> findById(UUID uuid) {
       try (val stream = jdbcTemplate.queryForStream(SQL_GET_BY_ID.formatted(uuid), toMessage)) {
           return stream.findAny();
       }
    }

    @Override
    public List<MessageEntity> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, toMessage);
    }

    @Override
    public Page<MessageEntity> findAll() {
        return null;
    }

    @Override
    public void update(UUID uuid, MessageEntity model) {
        int affectedRows = jdbcTemplate.update(SQL_UPDATE.formatted(model.getText(), uuid));

        if (affectedRows != 1)
            throw new CannotUpdateMessageException();
    }

    @Override
    public void delete(UUID uuid) {
        deleteFromDialog(uuid);
        int affectedRows = jdbcTemplate.update(SQL_DELETE);

        if (affectedRows != 1)
            throw new CannotDeleteMessageException();
    }

    private void deleteFromDialog(UUID uuid) {
        int affectedRows = jdbcTemplate.update(SQL_DELETE_FROM_DIALOG.formatted(uuid));

        if (affectedRows != 1)
            throw new CannotDeleteMessageException();
    }


    @Override
    public void deleteAllByDialogId(UUID dialogUUID) {
        List<UUID> messagesIdThatBelongToDialog = getAllByDialogId(dialogUUID).stream()
                .map(MessageEntity::getId)
                .toList();
        messagesIdThatBelongToDialog.forEach(this::delete);
    }

    @Override
    public List<MessageEntity> getAllByDialogId(UUID dialogUUID) {
        return jdbcTemplate.query(SQL_GET_ALL_BY_DIALOG_ID.formatted(dialogUUID), toMessage);
    }

    @Override
    public void setMessageTo(UUID messageUUID, UUID accountUUID) {
        int affectedRows = jdbcTemplate.update(SQL_SET_MESSAGE.formatted(accountUUID, messageUUID));

        if (affectedRows != 1)
            throw new CannotInsertMessageException();
    }
}

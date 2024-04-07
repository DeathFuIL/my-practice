package ru.kpfu.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteAccountException;
import ru.kpfu.itis.exceptions.repository.insert.CannotInsertAccountException;
import ru.kpfu.itis.exceptions.repository.update.CannotUpdateAccountException;
import ru.kpfu.itis.models.AccountEntity;
import ru.kpfu.itis.models.AddressEntity;
import ru.kpfu.itis.repositories.AccountRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=sql
    private static final String SQL_GET_BY_ID = "select * from account " +
            "left join address on account.address_id = address.id " +
            "left join public.\"account/company\" \"a/c\" on account.id = \"a/c\".account_id " +
            "where account.id = '%s'";

    //language=sql
    private static final String SQL_GET_ALL = "select * from account " +
            "left join address on account.address_id = address.id " +
            "left join public.\"account/company\" \"a/c\" on account.id = \"a/c\".account_id";

    //language=sql
    private static final String SQL_INSERT = "insert into account " +
            "(id, name, surname, birthday, email, address_id, profile_photo, phone_number, hashed_password) values " +
            "('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')";

    //language=sql
    private static final String SQL_UPDATE = "update account set " +
            "name = '%s', surname = '%s', email = '%s', address_id = '%s', profile_photo = '%s', phone_number = '%s', hashed_password = '%s' " +
            "where id = '%s'";

    //language=sql
    private static final String SQL_DELETE = "delete from account where id = '%s'";

    //language=sql
    private static final String SQL_GET_EXECUTORS_BY_TARGET_ID = SQL_GET_ALL +
            " where account.id in (select account_id from executor where target_id = '%s')";

    //language=sql
    private static final String SQL_GET_RESPONSIBLES_BY_TARGET_ID = SQL_GET_ALL +
            " where account.id in (select account_id from responsible where target_id = '%s')";;

    //language=sql
    private static final String SQL_GET_SPECTATORS_BY_TARGET_ID = SQL_GET_ALL +
            " where account.id in (select account_id from spectator where target_id = '%s')";

    //language=sql
    private static final String SQL_GET_EXECUTORS_BY_TASK_ID = SQL_GET_ALL +
            " where account.id in (select account_id from executor where task_id = '%s')";

    //language=sql
    private static final String SQL_GET_ASSIGNER_BY_TASK_ID = SQL_GET_ALL +
            " where account.id in (select t.assigner_id from task t where t.id = '%s')";

    //language=sql
    private static final String SQL_GET_ALL_BY_COMPANY_ID = SQL_GET_ALL +
            " where account.id in (select account_id from \"account/company\" where company_id = '%s')";

    //language=sql
    private static final String SQL_GET_FRIENDS_BY_ACCOUNT_ID = SQL_GET_ALL +
            " where account.id in " +
            "(select distinct friend_id from " +
            "(select account_id_2 as friend_id from friends where account_id_1 = '%s' " +
            "union " +
            "select account_id_1 as friend_id from friends where account_id_2 = '%s') as friend_list)";

    //language=sql
    private static final String SQL_GET_ALL_BY_DIALOG_ID = SQL_GET_ALL + " where account.id in " +
            "(select account_id from \"account/dialog\" where dialog_id = '%s')";

    //language=sql
    private static final String SQL_GET_BY_EMAIL = SQL_GET_ALL + " where email = '%s'";

    private final RowMapper<AccountEntity> toAccount = (rs, rowNum) ->
            AccountEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .email(rs.getString("email"))
                    .birthday(rs.getDate("birthday"))
                    .phoneNumber(rs.getString("phone_number"))
                    .profilePhoto(rs.getString("profile_photo"))
                    .position(rs.getString("position"))
                    .password(rs.getString("hashed_password"))
                    .dateOfCreating(rs.getDate("date_of_creating"))
                    .address(
                            AddressEntity.builder()
                                    .id(UUID.fromString(rs.getString(11)))
                                    .city(rs.getString("city"))
                                    .state(rs.getString("state"))
                                    .country(rs.getString("country"))
                                    .postalCode(rs.getInt("postal_code"))
                                    .build()
                    )
                    .build();

    @Override
    public UUID create(AccountEntity model) {
        UUID uuid = UUID.randomUUID();
        int affectedRows = jdbcTemplate.update(SQL_INSERT.formatted(
                model.getId().toString(),
                model.getName(),
                model.getSurname(),
                model.getBirthday(),
                model.getEmail(),
                model.getAddress().getId().toString(),
                model.getProfilePhoto(),
                model.getPhoneNumber(),
                model.getPassword())
        );

        if (affectedRows != 1)
            throw new CannotInsertAccountException();
        else
            model.setId(uuid);

        return uuid;
    }

    @Override
    public Optional<AccountEntity> findById(UUID uuid) {
        try (val stream = jdbcTemplate.queryForStream(SQL_GET_BY_ID.formatted(uuid), toAccount)) {
            Optional<AccountEntity> optionalAccount = stream.findAny();
            optionalAccount.ifPresent(accountEntity -> accountEntity.setFriends(getFriendsById(uuid)));
            return optionalAccount;
        }
    }

    @Override
    public List<AccountEntity> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, toAccount);
    }

    @Override
    public Page<AccountEntity> findAll() {
        return new PageImpl<>(getAll());
    }

    @Override
    public void update(UUID uuid, AccountEntity model) {
        int affectedRows = jdbcTemplate.update(SQL_UPDATE.formatted(
                model.getName(),
                model.getSurname(),
                model.getEmail(),
                model.getAddress().getId(),
                model.getProfilePhoto(),
                model.getPhoneNumber(),
                model.getPassword(),
                model.getId()
        ));

        if (affectedRows != 1) throw new CannotUpdateAccountException();
    }

    @Override
    public void delete(UUID uuid) {
        int affectedRows = jdbcTemplate.update(SQL_DELETE.formatted(uuid));

        if (affectedRows != 1) throw new CannotDeleteAccountException();
    }

    @Override
    public List<AccountEntity> getFriendsById(UUID uuid) {
        return jdbcTemplate.query(SQL_GET_FRIENDS_BY_ACCOUNT_ID.replace("%s", uuid.toString()), toAccount);
    }

    @Override
    public List<AccountEntity> getAllExecutorsByTargetId(UUID targetUUID) {
        return jdbcTemplate.query(SQL_GET_EXECUTORS_BY_TARGET_ID.formatted(targetUUID), toAccount);
    }

    @Override
    public List<AccountEntity> getAllResponsiblesByTargetId(UUID targetUUID) {
        return jdbcTemplate.query(SQL_GET_RESPONSIBLES_BY_TARGET_ID.formatted(targetUUID), toAccount);
    }

    @Override
    public List<AccountEntity> getAllSpectatorsByTargetId(UUID targetUUID) {
        return jdbcTemplate.query(SQL_GET_SPECTATORS_BY_TARGET_ID.formatted(targetUUID), toAccount);
    }

    @Override
    public List<AccountEntity> getAllExecutorsByTaskId(UUID taskUUID) {
        return jdbcTemplate.query(SQL_GET_EXECUTORS_BY_TASK_ID.formatted(taskUUID), toAccount);
    }

    @Override
    public AccountEntity getAssignerByTaskId(UUID taskUUID) {
        return jdbcTemplate.queryForObject(SQL_GET_ASSIGNER_BY_TASK_ID.formatted(taskUUID), toAccount);
    }

    @Override
    public List<AccountEntity> getAllByCompanyId(UUID companyUUID) {
        return jdbcTemplate.query(SQL_GET_ALL_BY_COMPANY_ID.formatted(companyUUID), toAccount);
    }

    @Override
    public List<AccountEntity> getAllByDialogId(UUID dialogUUID) {
        return jdbcTemplate.query(SQL_GET_ALL_BY_DIALOG_ID.formatted(dialogUUID), toAccount);
    }

    @Override
    public AccountEntity getByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_GET_BY_EMAIL.formatted(email), toAccount);
    }
}

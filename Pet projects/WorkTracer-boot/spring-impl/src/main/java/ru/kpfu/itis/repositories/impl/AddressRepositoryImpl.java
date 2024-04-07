package ru.kpfu.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.exceptions.repository.insert.CannotInsertAddressException;
import ru.kpfu.itis.models.AddressEntity;
import ru.kpfu.itis.repositories.AddressRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=sql
    public static final String SQL_CHECK_IF_EXISTS =
            "select id from address " +
            "where lower(city) = lower('%s') and " +
            "lower(country) = lower('%s') and " +
            "lower(state) = lower('%s') and " +
            "postal_code = %d";

    //language=sql
    public static final String SQL_CREATE =
            "insert into address(id, city, state, country, postal_code) " +
            "values ('%s', '%s', '%s', '%s', '%s')";

    //language=sql
    public static final String SQL_GET_BY_ID = "select * from address where id = '%s'";


    private final RowMapper<AddressEntity> toAddress = (rs, rowNum) ->
            AddressEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .state(rs.getString("state"))
                    .city(rs.getString("city"))
                    .country(rs.getString("country"))
                    .postalCode(rs.getInt("postal_code"))
                    .build();

    @Override
    public UUID create(AddressEntity model) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SQL_CHECK_IF_EXISTS.formatted(model.getCity(),
                model.getCountry(), model.getState(), model.getPostalCode()));
        if (rowSet.next()) {
            return UUID.fromString(rowSet.getString("id"));
        }

        UUID uuid = UUID.randomUUID();
        int affectedRows = jdbcTemplate.update(SQL_CREATE.formatted(uuid, model.getCity(),
                model.getState(), model.getCountry(), model.getPostalCode()));

        if (affectedRows != 1)
            throw new CannotInsertAddressException();
        else
            model.setId(uuid);

        return uuid;
    }

    @Override
    public Optional<AddressEntity> findById(UUID uuid) {
        try (val stream = jdbcTemplate.queryForStream(SQL_GET_BY_ID.formatted(uuid), toAddress)) {
            return stream.findAny();
        }
    }

    @Override
    public List<AddressEntity> getAll() {
        return null;
    }

    @Override
    public Page<AddressEntity> findAll() {
        return new PageImpl<>(getAll());
    }

    @Override
    public void update(UUID uuid, AddressEntity model) {
        throw new UnsupportedOperationException("This method must not be used in this class");
    }

    @Override
    public void delete(UUID uuid) {
        throw new UnsupportedOperationException("This method must not be used in this class");
    }
}

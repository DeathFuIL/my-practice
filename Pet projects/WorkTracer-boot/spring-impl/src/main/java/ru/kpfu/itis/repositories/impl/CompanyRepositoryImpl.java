package ru.kpfu.itis.repositories.impl;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.exceptions.repository.delete.CannotDeleteCompanyException;
import ru.kpfu.itis.exceptions.repository.insert.CannotInsertCompanyException;
import ru.kpfu.itis.exceptions.repository.update.CannotUpdateCompanyException;
import ru.kpfu.itis.models.AddressEntity;
import ru.kpfu.itis.models.CompanyEntity;
import ru.kpfu.itis.repositories.CompanyRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    private final JdbcTemplate jdbcTemplate;

    //language=sql
    private static final String SQL_CREATE = "insert into company " +
            "(id, name, founding_date, address_id, email, industry, phone_number) values " +
            "('%s', '%s', '%s', '%s', '%s', '%s', '%s')";

    //language=sql
    private static final String SQL_GET_ALL = "select * from company " +
            "left join public.address a on a.id = company.address_id";

    //language=sql
    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " where company.id = '%s'";

    //language=sql
    private static final String SQL_UPDATE = "update company set " +
            "name = '%s', address_id = '%s', email = '%s', industry = '%s', phone_number = '%s' " +
            "where id = '%s'";

    //language=sql
    private static final String SQL_DELETE = "delete from company where id = '%s'";

    private static final RowMapper<CompanyEntity> toCompany = (rs, rowNum) ->
            CompanyEntity.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .name(rs.getString("name"))
                    .foundingDate(rs.getDate("founding_date"))
                    .email(rs.getString("email"))
                    .industry(rs.getString("industry"))
                    .phoneNumber(rs.getString("phoneNumber"))
                    .address(
                            AddressEntity.builder()
                                    .id(UUID.fromString(rs.getString(8)))
                                    .city(rs.getString("city"))
                                    .state(rs.getString("state"))
                                    .country(rs.getString("country"))
                                    .postalCode(rs.getInt("postal_code"))
                                    .build()
                    ).build();
    
    @Override
    public UUID create(CompanyEntity model) {
        UUID uuid = UUID.randomUUID();
        int affectedRows = jdbcTemplate.update(SQL_CREATE.formatted(
                uuid.toString(),
                model.getName(),
                model.getFoundingDate(),
                model.getAddress().getId(),
                model.getEmail(),
                model.getIndustry(),
                model.getPhoneNumber()
        ));

        if (affectedRows != 1)
            throw new CannotInsertCompanyException();
        else
            model.setId(uuid);
        
        return uuid;
    }

    @Override
    public Optional<CompanyEntity> findById(UUID uuid) {
        try (val stream = jdbcTemplate.queryForStream(SQL_GET_BY_ID.formatted(uuid), toCompany)) {
            return stream.findAny();
        }
    }

    @Override
    public List<CompanyEntity> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL, toCompany);
    }

    @Override
    public Page<CompanyEntity> findAll() {
        return new PageImpl<>(getAll());
    }

    @Override
    public void update(UUID uuid, CompanyEntity model) {
        int affectedRows = jdbcTemplate.update(SQL_UPDATE.formatted(
                model.getName(),
                model.getAddress().getId(),
                model.getEmail(),
                model.getIndustry(),
                model.getPhoneNumber(),
                model.getId()
        ));

        if (affectedRows != 1) throw new CannotUpdateCompanyException();
    }

    @Override
    public void delete(UUID uuid) {
        int affectedRows = jdbcTemplate.update(SQL_DELETE.formatted(uuid));

        if (affectedRows != 1) throw new CannotDeleteCompanyException();
    }
}

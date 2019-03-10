package se.plilja.example.model;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import se.plilja.example.dbframework.Column;
import se.plilja.example.dbframework.CurrentUserProvider;
import se.plilja.example.dbframework.Dao;

@Repository
public class CountryDao extends Dao<Country, Integer> {

    public static final Column.IntColumn<Country> COLUMN_COUNTRY_ID = new Column.IntColumn<>("country_id", "countryId");

    public static final Column.StringColumn<Country> COLUMN_COUNTRY = new Column.StringColumn<>("country", "country");

    public static final Column.DateTimeColumn<Country> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final List<Column<Country, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_COUNTRY_ID,
            COLUMN_COUNTRY,
            COLUMN_LAST_UPDATE);

    private static final String ALL_COLUMNS = " country_id, country, last_update ";

    private static final RowMapper<Country> ROW_MAPPER = (rs, i) -> {
        Country r = new Country();
        r.setCountryId(rs.getInt("country_id"));
        r.setCountry(rs.getString("country"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        return r;
    };

    @Autowired
    public CountryDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Country o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("country_id", o.getId(), Types.SMALLINT);
        m.addValue("country", o.getCountry(), Types.VARCHAR);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        return m;
    }

    @Override
    protected RowMapper<Country> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM country " +
                "WHERE country_id = :country_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM country " +
                "WHERE country_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM country " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO country (" +
                "country, " +
                "last_update" +
                ") " +
                "VALUES (" +
                ":country, " +
                ":last_update" +
                ")";
    }

    @Override
    protected String getUpdateSql(Country object) {
        return "UPDATE country SET " +
                "country = :country, " +
                "last_update = :last_update " +
                "WHERE country_id = :country_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM country " +
                "WHERE country_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM country";
    }

    @Override
    protected List<Column<Country, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM country %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM country %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM country " +
                "WHERE country_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "country_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

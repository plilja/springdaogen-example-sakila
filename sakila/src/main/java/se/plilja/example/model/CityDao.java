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
public class CityDao extends Dao<City, Integer> {

    public static final Column.IntColumn<City> COLUMN_CITY_ID = new Column.IntColumn<>("city_id", "cityId");

    public static final Column.StringColumn<City> COLUMN_CITY = new Column.StringColumn<>("city", "city");

    public static final Column.IntColumn<City> COLUMN_COUNTRY_ID = new Column.IntColumn<>("country_id", "countryId");

    public static final Column.DateTimeColumn<City> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final List<Column<City, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_CITY_ID,
            COLUMN_CITY,
            COLUMN_COUNTRY_ID,
            COLUMN_LAST_UPDATE);

    private static final String ALL_COLUMNS = " city_id, city, country_id, last_update ";

    private static final RowMapper<City> ROW_MAPPER = (rs, i) -> {
        City r = new City();
        r.setCityId(rs.getInt("city_id"));
        r.setCity(rs.getString("city"));
        r.setCountryId(rs.getInt("country_id"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        return r;
    };

    @Autowired
    public CityDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(City.class, Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(City o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("city_id", o.getId(), Types.SMALLINT);
        m.addValue("city", o.getCity(), Types.VARCHAR);
        m.addValue("country_id", o.getCountryId(), Types.SMALLINT);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        return m;
    }

    @Override
    protected RowMapper<City> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM city " +
                "WHERE city_id = :city_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM city " +
                "WHERE city_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM city " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO city (" +
                "city, " +
                "country_id, " +
                "last_update" +
                ") " +
                "VALUES (" +
                ":city, " +
                ":country_id, " +
                ":last_update" +
                ")";
    }

    @Override
    protected String getUpdateSql(City object) {
        return "UPDATE city SET " +
                "city = :city, " +
                "country_id = :country_id, " +
                "last_update = :last_update " +
                "WHERE city_id = :city_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM city " +
                "WHERE city_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM city";
    }

    @Override
    protected List<Column<City, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM city %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM city %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM city " +
                "WHERE city_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "city_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

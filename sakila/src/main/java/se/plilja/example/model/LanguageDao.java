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
public class LanguageDao extends Dao<Language, Integer> {

    public static final Column.IntColumn<Language> COLUMN_LANGUAGE_ID = new Column.IntColumn<>("language_id", "languageId");

    public static final Column.DateTimeColumn<Language> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column.StringColumn<Language> COLUMN_NAME = new Column.StringColumn<>("name", "name");

    public static final List<Column<Language, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_LANGUAGE_ID,
            COLUMN_LAST_UPDATE,
            COLUMN_NAME);

    private static final String ALL_COLUMNS = " language_id, last_update, name ";

    private static final RowMapper<Language> ROW_MAPPER = (rs, i) -> {
        Language r = new Language();
        r.setLanguageId(rs.getInt("language_id"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        r.setName(rs.getString("name"));
        return r;
    };

    @Autowired
    public LanguageDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Language o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("language_id", o.getId(), Types.TINYINT);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("name", o.getName(), Types.CHAR);
        return m;
    }

    @Override
    protected RowMapper<Language> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM `language` " +
                "WHERE language_id = :language_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM `language` " +
                "WHERE language_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM `language` " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO `language` (" +
                "last_update, " +
                "name" +
                ") " +
                "VALUES (" +
                ":last_update, " +
                ":name" +
                ")";
    }

    @Override
    protected String getUpdateSql(Language object) {
        return "UPDATE `language` SET " +
                "last_update = :last_update, " +
                "name = :name " +
                "WHERE language_id = :language_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM `language` " +
                "WHERE language_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM `language`";
    }

    @Override
    protected List<Column<Language, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM `language` %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM `language` %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM `language` " +
                "WHERE language_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "language_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

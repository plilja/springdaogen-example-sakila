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
public class ActorDao extends Dao<Actor, Integer> {

    public static final Column.IntColumn<Actor> COLUMN_ACTOR_ID = new Column.IntColumn<>("actor_id", "actorId");

    public static final Column.StringColumn<Actor> COLUMN_FIRST_NAME = new Column.StringColumn<>("first_name", "firstName");

    public static final Column.StringColumn<Actor> COLUMN_LAST_NAME = new Column.StringColumn<>("last_name", "lastName");

    public static final Column.DateTimeColumn<Actor> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final List<Column<Actor, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_ACTOR_ID,
            COLUMN_FIRST_NAME,
            COLUMN_LAST_NAME,
            COLUMN_LAST_UPDATE);

    private static final String ALL_COLUMNS = " actor_id, first_name, last_name, last_update ";

    private static final RowMapper<Actor> ROW_MAPPER = (rs, i) -> {
        Actor r = new Actor();
        r.setActorId(rs.getInt("actor_id"));
        r.setFirstName(rs.getString("first_name"));
        r.setLastName(rs.getString("last_name"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        return r;
    };

    @Autowired
    public ActorDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Actor o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("actor_id", o.getId(), Types.SMALLINT);
        m.addValue("first_name", o.getFirstName(), Types.VARCHAR);
        m.addValue("last_name", o.getLastName(), Types.VARCHAR);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        return m;
    }

    @Override
    protected RowMapper<Actor> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM actor " +
                "WHERE actor_id = :actor_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM actor " +
                "WHERE actor_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM actor " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO actor (" +
                "first_name, " +
                "last_name, " +
                "last_update" +
                ") " +
                "VALUES (" +
                ":first_name, " +
                ":last_name, " +
                ":last_update" +
                ")";
    }

    @Override
    protected String getUpdateSql(Actor object) {
        return "UPDATE actor SET " +
                "first_name = :first_name, " +
                "last_name = :last_name, " +
                "last_update = :last_update " +
                "WHERE actor_id = :actor_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM actor " +
                "WHERE actor_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM actor";
    }

    @Override
    protected List<Column<Actor, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM actor %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM actor %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM actor " +
                "WHERE actor_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "actor_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

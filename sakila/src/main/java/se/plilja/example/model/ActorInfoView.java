package se.plilja.example.model;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import se.plilja.example.dbframework.Column;
import se.plilja.example.dbframework.Queryable;

@Repository
public class ActorInfoView extends Queryable<ActorInfo> {

    public static final Column.IntColumn<ActorInfo> COLUMN_ACTOR_ID = new Column.IntColumn<>("actor_id", "actorId");

    public static final Column.StringColumn<ActorInfo> COLUMN_FILM_INFO = new Column.StringColumn<>("film_info", "filmInfo");

    public static final Column.StringColumn<ActorInfo> COLUMN_FIRST_NAME = new Column.StringColumn<>("first_name", "firstName");

    public static final Column.StringColumn<ActorInfo> COLUMN_LAST_NAME = new Column.StringColumn<>("last_name", "lastName");

    public static final List<Column<ActorInfo, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_ACTOR_ID,
            COLUMN_FILM_INFO,
            COLUMN_FIRST_NAME,
            COLUMN_LAST_NAME);

    private static final String ALL_COLUMNS = " actor_id, film_info, first_name, last_name ";

    private static final RowMapper<ActorInfo> ROW_MAPPER = (rs, i) -> {
        ActorInfo r = new ActorInfo();
        r.setActorId(rs.getInt("actor_id"));
        r.setFilmInfo(rs.getString("film_info"));
        r.setFirstName(rs.getString("first_name"));
        r.setLastName(rs.getString("last_name"));
        return r;
    };

    @Autowired
    public ActorInfoView(NamedParameterJdbcTemplate jdbcTemplate) {
        super(ActorInfo.class, jdbcTemplate);
    }

    @Override
    protected RowMapper<ActorInfo> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM actor_info " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM actor_info";
    }

    @Override
    protected List<Column<ActorInfo, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM actor_info %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM actor_info %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

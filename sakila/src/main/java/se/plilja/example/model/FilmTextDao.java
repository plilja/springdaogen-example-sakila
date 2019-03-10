package se.plilja.example.model;

import java.sql.Types;
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
public class FilmTextDao extends Dao<FilmText, Integer> {

    public static final Column.IntColumn<FilmText> COLUMN_FILM_ID = new Column.IntColumn<>("film_id", "filmId");

    public static final Column.StringColumn<FilmText> COLUMN_DESCRIPTION = new Column.StringColumn<>("description", "`description`", "description");

    public static final Column.StringColumn<FilmText> COLUMN_TITLE = new Column.StringColumn<>("title", "title");

    public static final List<Column<FilmText, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_FILM_ID,
            COLUMN_DESCRIPTION,
            COLUMN_TITLE);

    private static final String ALL_COLUMNS = " film_id, `description`, title ";

    private static final RowMapper<FilmText> ROW_MAPPER = (rs, i) -> {
        FilmText r = new FilmText();
        r.setFilmId(rs.getInt("film_id"));
        r.setDescription(rs.getString("description"));
        r.setTitle(rs.getString("title"));
        return r;
    };

    @Autowired
    public FilmTextDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(FilmText.class, Integer.class, false, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(FilmText o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("film_id", o.getId(), Types.SMALLINT);
        m.addValue("description", o.getDescription(), Types.LONGVARCHAR);
        m.addValue("title", o.getTitle(), Types.VARCHAR);
        return m;
    }

    @Override
    protected RowMapper<FilmText> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM film_text " +
                "WHERE film_id = :film_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM film_text " +
                "WHERE film_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM film_text " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO film_text (" +
                "film_id, " +
                "`description`, " +
                "title" +
                ") " +
                "VALUES (" +
                ":film_id, " +
                ":description, " +
                ":title" +
                ")";
    }

    @Override
    protected String getUpdateSql(FilmText object) {
        return "UPDATE film_text SET " +
                "description = :description, " +
                "title = :title " +
                "WHERE film_id = :film_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM film_text " +
                "WHERE film_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM film_text";
    }

    @Override
    protected List<Column<FilmText, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM film_text %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM film_text %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM film_text " +
                "WHERE film_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "film_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

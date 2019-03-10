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
public class CategoryDao extends Dao<Category, Integer> {

    public static final Column.IntColumn<Category> COLUMN_CATEGORY_ID = new Column.IntColumn<>("category_id", "categoryId");

    public static final Column.DateTimeColumn<Category> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column.StringColumn<Category> COLUMN_NAME = new Column.StringColumn<>("name", "name");

    public static final List<Column<Category, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_CATEGORY_ID,
            COLUMN_LAST_UPDATE,
            COLUMN_NAME);

    private static final String ALL_COLUMNS = " category_id, last_update, name ";

    private static final RowMapper<Category> ROW_MAPPER = (rs, i) -> {
        Category r = new Category();
        r.setCategoryId(rs.getInt("category_id"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        r.setName(rs.getString("name"));
        return r;
    };

    @Autowired
    public CategoryDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Category.class, Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Category o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("category_id", o.getId(), Types.TINYINT);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("name", o.getName(), Types.VARCHAR);
        return m;
    }

    @Override
    protected RowMapper<Category> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM category " +
                "WHERE category_id = :category_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM category " +
                "WHERE category_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM category " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO category (" +
                "last_update, " +
                "name" +
                ") " +
                "VALUES (" +
                ":last_update, " +
                ":name" +
                ")";
    }

    @Override
    protected String getUpdateSql(Category object) {
        return "UPDATE category SET " +
                "last_update = :last_update, " +
                "name = :name " +
                "WHERE category_id = :category_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM category " +
                "WHERE category_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM category";
    }

    @Override
    protected List<Column<Category, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM category %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM category %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM category " +
                "WHERE category_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "category_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

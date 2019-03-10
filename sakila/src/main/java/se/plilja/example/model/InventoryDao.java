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
public class InventoryDao extends Dao<Inventory, Integer> {

    public static final Column.IntColumn<Inventory> COLUMN_INVENTORY_ID = new Column.IntColumn<>("inventory_id", "inventoryId");

    public static final Column.IntColumn<Inventory> COLUMN_FILM_ID = new Column.IntColumn<>("film_id", "filmId");

    public static final Column.DateTimeColumn<Inventory> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column.IntColumn<Inventory> COLUMN_STORE_ID = new Column.IntColumn<>("store_id", "storeId");

    public static final List<Column<Inventory, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_INVENTORY_ID,
            COLUMN_FILM_ID,
            COLUMN_LAST_UPDATE,
            COLUMN_STORE_ID);

    private static final String ALL_COLUMNS = " inventory_id, film_id, last_update, store_id ";

    private static final RowMapper<Inventory> ROW_MAPPER = (rs, i) -> {
        Inventory r = new Inventory();
        r.setInventoryId(rs.getInt("inventory_id"));
        r.setFilmId(rs.getInt("film_id"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        r.setStoreId(rs.getInt("store_id"));
        return r;
    };

    @Autowired
    public InventoryDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Inventory.class, Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Inventory o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("inventory_id", o.getId(), Types.INTEGER);
        m.addValue("film_id", o.getFilmId(), Types.SMALLINT);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("store_id", o.getStoreId(), Types.TINYINT);
        return m;
    }

    @Override
    protected RowMapper<Inventory> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM inventory " +
                "WHERE inventory_id = :inventory_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM inventory " +
                "WHERE inventory_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM inventory " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO inventory (" +
                "film_id, " +
                "last_update, " +
                "store_id" +
                ") " +
                "VALUES (" +
                ":film_id, " +
                ":last_update, " +
                ":store_id" +
                ")";
    }

    @Override
    protected String getUpdateSql(Inventory object) {
        return "UPDATE inventory SET " +
                "film_id = :film_id, " +
                "last_update = :last_update, " +
                "store_id = :store_id " +
                "WHERE inventory_id = :inventory_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM inventory " +
                "WHERE inventory_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM inventory";
    }

    @Override
    protected List<Column<Inventory, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM inventory %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM inventory %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM inventory " +
                "WHERE inventory_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "inventory_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

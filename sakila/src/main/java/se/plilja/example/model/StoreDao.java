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
public class StoreDao extends Dao<Store, Integer> {

    public static final Column.IntColumn<Store> COLUMN_STORE_ID = new Column.IntColumn<>("store_id", "storeId");

    public static final Column.IntColumn<Store> COLUMN_ADDRESS_ID = new Column.IntColumn<>("address_id", "addressId");

    public static final Column.DateTimeColumn<Store> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column.IntColumn<Store> COLUMN_MANAGER_STAFF_ID = new Column.IntColumn<>("manager_staff_id", "managerStaffId");

    public static final List<Column<Store, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_STORE_ID,
            COLUMN_ADDRESS_ID,
            COLUMN_LAST_UPDATE,
            COLUMN_MANAGER_STAFF_ID);

    private static final String ALL_COLUMNS = " store_id, address_id, last_update, manager_staff_id ";

    private static final RowMapper<Store> ROW_MAPPER = (rs, i) -> {
        Store r = new Store();
        r.setStoreId(rs.getInt("store_id"));
        r.setAddressId(rs.getInt("address_id"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        r.setManagerStaffId(rs.getInt("manager_staff_id"));
        return r;
    };

    @Autowired
    public StoreDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Store.class, Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Store o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("store_id", o.getId(), Types.TINYINT);
        m.addValue("address_id", o.getAddressId(), Types.SMALLINT);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("manager_staff_id", o.getManagerStaffId(), Types.TINYINT);
        return m;
    }

    @Override
    protected RowMapper<Store> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM store " +
                "WHERE store_id = :store_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM store " +
                "WHERE store_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM store " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO store (" +
                "address_id, " +
                "last_update, " +
                "manager_staff_id" +
                ") " +
                "VALUES (" +
                ":address_id, " +
                ":last_update, " +
                ":manager_staff_id" +
                ")";
    }

    @Override
    protected String getUpdateSql(Store object) {
        return "UPDATE store SET " +
                "address_id = :address_id, " +
                "last_update = :last_update, " +
                "manager_staff_id = :manager_staff_id " +
                "WHERE store_id = :store_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM store " +
                "WHERE store_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM store";
    }

    @Override
    protected List<Column<Store, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM store %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM store %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM store " +
                "WHERE store_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "store_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

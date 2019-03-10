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
public class RentalDao extends Dao<Rental, Integer> {

    public static final Column.IntColumn<Rental> COLUMN_RENTAL_ID = new Column.IntColumn<>("rental_id", "rentalId");

    public static final Column.IntColumn<Rental> COLUMN_CUSTOMER_ID = new Column.IntColumn<>("customer_id", "customerId");

    public static final Column.IntColumn<Rental> COLUMN_INVENTORY_ID = new Column.IntColumn<>("inventory_id", "inventoryId");

    public static final Column.DateTimeColumn<Rental> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column.DateTimeColumn<Rental> COLUMN_RENTAL_DATE = new Column.DateTimeColumn<>("rental_date", "rentalDate");

    public static final Column.DateTimeColumn<Rental> COLUMN_RETURN_DATE = new Column.DateTimeColumn<>("return_date", "returnDate");

    public static final Column.IntColumn<Rental> COLUMN_STAFF_ID = new Column.IntColumn<>("staff_id", "staffId");

    public static final List<Column<Rental, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_RENTAL_ID,
            COLUMN_CUSTOMER_ID,
            COLUMN_INVENTORY_ID,
            COLUMN_LAST_UPDATE,
            COLUMN_RENTAL_DATE,
            COLUMN_RETURN_DATE,
            COLUMN_STAFF_ID);

    private static final String ALL_COLUMNS = " rental_id, customer_id, inventory_id, last_update, rental_date, " +
            " return_date, staff_id ";

    private static final RowMapper<Rental> ROW_MAPPER = (rs, i) -> {
        Rental r = new Rental();
        r.setRentalId(rs.getInt("rental_id"));
        r.setCustomerId(rs.getInt("customer_id"));
        r.setInventoryId(rs.getInt("inventory_id"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        r.setRentalDate(rs.getTimestamp("rental_date").toLocalDateTime());
        r.setReturnDate(rs.getObject("return_date") != null ? rs.getTimestamp("return_date").toLocalDateTime() : null);
        r.setStaffId(rs.getInt("staff_id"));
        return r;
    };

    @Autowired
    public RentalDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Rental o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("rental_id", o.getId(), Types.INTEGER);
        m.addValue("customer_id", o.getCustomerId(), Types.SMALLINT);
        m.addValue("inventory_id", o.getInventoryId(), Types.INTEGER);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("rental_date", o.getRentalDate(), Types.TIMESTAMP);
        m.addValue("return_date", o.getReturnDate(), Types.TIMESTAMP);
        m.addValue("staff_id", o.getStaffId(), Types.TINYINT);
        return m;
    }

    @Override
    protected RowMapper<Rental> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM rental " +
                "WHERE rental_id = :rental_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM rental " +
                "WHERE rental_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM rental " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO rental (" +
                "customer_id, " +
                "inventory_id, " +
                "last_update, " +
                "rental_date, " +
                "return_date, " +
                "staff_id" +
                ") " +
                "VALUES (" +
                ":customer_id, " +
                ":inventory_id, " +
                ":last_update, " +
                ":rental_date, " +
                ":return_date, " +
                ":staff_id" +
                ")";
    }

    @Override
    protected String getUpdateSql(Rental object) {
        return "UPDATE rental SET " +
                "customer_id = :customer_id, " +
                "inventory_id = :inventory_id, " +
                "last_update = :last_update, " +
                "rental_date = :rental_date, " +
                "return_date = :return_date, " +
                "staff_id = :staff_id " +
                "WHERE rental_id = :rental_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM rental " +
                "WHERE rental_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM rental";
    }

    @Override
    protected List<Column<Rental, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM rental %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM rental %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM rental " +
                "WHERE rental_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "rental_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

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
public class CustomerDao extends Dao<Customer, Integer> {

    public static final Column.IntColumn<Customer> COLUMN_CUSTOMER_ID = new Column.IntColumn<>("customer_id", "customerId");

    public static final Column.BooleanColumn<Customer> COLUMN_ACTIVE = new Column.BooleanColumn<>("active", "`active`", "active");

    public static final Column.IntColumn<Customer> COLUMN_ADDRESS_ID = new Column.IntColumn<>("address_id", "addressId");

    public static final Column.DateTimeColumn<Customer> COLUMN_CREATE_DATE = new Column.DateTimeColumn<>("create_date", "createDate");

    public static final Column.StringColumn<Customer> COLUMN_EMAIL = new Column.StringColumn<>("email", "email");

    public static final Column.StringColumn<Customer> COLUMN_FIRST_NAME = new Column.StringColumn<>("first_name", "firstName");

    public static final Column.StringColumn<Customer> COLUMN_LAST_NAME = new Column.StringColumn<>("last_name", "lastName");

    public static final Column.DateTimeColumn<Customer> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column.IntColumn<Customer> COLUMN_STORE_ID = new Column.IntColumn<>("store_id", "storeId");

    public static final List<Column<Customer, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_CUSTOMER_ID,
            COLUMN_ACTIVE,
            COLUMN_ADDRESS_ID,
            COLUMN_CREATE_DATE,
            COLUMN_EMAIL,
            COLUMN_FIRST_NAME,
            COLUMN_LAST_NAME,
            COLUMN_LAST_UPDATE,
            COLUMN_STORE_ID);

    private static final String ALL_COLUMNS = " customer_id, `active`, address_id, create_date, email, " +
            " first_name, last_name, last_update, store_id ";

    private static final RowMapper<Customer> ROW_MAPPER = (rs, i) -> {
        Customer r = new Customer();
        r.setCustomerId(rs.getInt("customer_id"));
        r.setActive(rs.getBoolean("active"));
        r.setAddressId(rs.getInt("address_id"));
        r.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
        r.setEmail(rs.getString("email"));
        r.setFirstName(rs.getString("first_name"));
        r.setLastName(rs.getString("last_name"));
        r.setLastUpdate(rs.getObject("last_update") != null ? rs.getTimestamp("last_update").toLocalDateTime() : null);
        r.setStoreId(rs.getInt("store_id"));
        return r;
    };

    @Autowired
    public CustomerDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Customer o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("customer_id", o.getId(), Types.SMALLINT);
        m.addValue("active", o.getActive(), Types.BIT);
        m.addValue("address_id", o.getAddressId(), Types.SMALLINT);
        m.addValue("create_date", o.getCreateDate(), Types.TIMESTAMP);
        m.addValue("email", o.getEmail(), Types.VARCHAR);
        m.addValue("first_name", o.getFirstName(), Types.VARCHAR);
        m.addValue("last_name", o.getLastName(), Types.VARCHAR);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("store_id", o.getStoreId(), Types.TINYINT);
        return m;
    }

    @Override
    protected RowMapper<Customer> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM customer " +
                "WHERE customer_id = :customer_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM customer " +
                "WHERE customer_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM customer " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO customer (" +
                "`active`, " +
                "address_id, " +
                "create_date, " +
                "email, " +
                "first_name, " +
                "last_name, " +
                "last_update, " +
                "store_id" +
                ") " +
                "VALUES (" +
                ":active, " +
                ":address_id, " +
                ":create_date, " +
                ":email, " +
                ":first_name, " +
                ":last_name, " +
                ":last_update, " +
                ":store_id" +
                ")";
    }

    @Override
    protected String getUpdateSql(Customer object) {
        return "UPDATE customer SET " +
                "active = :active, " +
                "address_id = :address_id, " +
                "email = :email, " +
                "first_name = :first_name, " +
                "last_name = :last_name, " +
                "last_update = :last_update, " +
                "store_id = :store_id " +
                "WHERE customer_id = :customer_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM customer " +
                "WHERE customer_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM customer";
    }

    @Override
    protected List<Column<Customer, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM customer %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM customer %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM customer " +
                "WHERE customer_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "customer_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

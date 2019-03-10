package se.plilja.example.model;

import java.math.BigDecimal;
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
public class PaymentDao extends Dao<Payment, Integer> {

    public static final Column.IntColumn<Payment> COLUMN_PAYMENT_ID = new Column.IntColumn<>("payment_id", "paymentId");

    public static final Column.BigDecimalColumn<Payment> COLUMN_AMOUNT = new Column.BigDecimalColumn<>("amount", "amount");

    public static final Column.IntColumn<Payment> COLUMN_CUSTOMER_ID = new Column.IntColumn<>("customer_id", "customerId");

    public static final Column.DateTimeColumn<Payment> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column.DateTimeColumn<Payment> COLUMN_PAYMENT_DATE = new Column.DateTimeColumn<>("payment_date", "paymentDate");

    public static final Column.IntColumn<Payment> COLUMN_RENTAL_ID = new Column.IntColumn<>("rental_id", "rentalId");

    public static final Column.IntColumn<Payment> COLUMN_STAFF_ID = new Column.IntColumn<>("staff_id", "staffId");

    public static final List<Column<Payment, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_PAYMENT_ID,
            COLUMN_AMOUNT,
            COLUMN_CUSTOMER_ID,
            COLUMN_LAST_UPDATE,
            COLUMN_PAYMENT_DATE,
            COLUMN_RENTAL_ID,
            COLUMN_STAFF_ID);

    private static final String ALL_COLUMNS = " payment_id, amount, customer_id, last_update, payment_date, " +
            " rental_id, staff_id ";

    private static final RowMapper<Payment> ROW_MAPPER = (rs, i) -> {
        Payment r = new Payment();
        r.setPaymentId(rs.getInt("payment_id"));
        r.setAmount(rs.getBigDecimal("amount"));
        r.setCustomerId(rs.getInt("customer_id"));
        r.setLastUpdate(rs.getObject("last_update") != null ? rs.getTimestamp("last_update").toLocalDateTime() : null);
        r.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
        r.setRentalId(rs.getObject("rental_id") != null ? rs.getInt("rental_id") : null);
        r.setStaffId(rs.getInt("staff_id"));
        return r;
    };

    @Autowired
    public PaymentDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Payment.class, Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Payment o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("payment_id", o.getId(), Types.SMALLINT);
        m.addValue("amount", o.getAmount(), Types.NUMERIC);
        m.addValue("customer_id", o.getCustomerId(), Types.SMALLINT);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("payment_date", o.getPaymentDate(), Types.TIMESTAMP);
        m.addValue("rental_id", o.getRentalId(), Types.INTEGER);
        m.addValue("staff_id", o.getStaffId(), Types.TINYINT);
        return m;
    }

    @Override
    protected RowMapper<Payment> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM payment " +
                "WHERE payment_id = :payment_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM payment " +
                "WHERE payment_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM payment " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO payment (" +
                "amount, " +
                "customer_id, " +
                "last_update, " +
                "payment_date, " +
                "rental_id, " +
                "staff_id" +
                ") " +
                "VALUES (" +
                ":amount, " +
                ":customer_id, " +
                ":last_update, " +
                ":payment_date, " +
                ":rental_id, " +
                ":staff_id" +
                ")";
    }

    @Override
    protected String getUpdateSql(Payment object) {
        return "UPDATE payment SET " +
                "amount = :amount, " +
                "customer_id = :customer_id, " +
                "last_update = :last_update, " +
                "payment_date = :payment_date, " +
                "rental_id = :rental_id, " +
                "staff_id = :staff_id " +
                "WHERE payment_id = :payment_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM payment " +
                "WHERE payment_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM payment";
    }

    @Override
    protected List<Column<Payment, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM payment %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM payment %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM payment " +
                "WHERE payment_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "payment_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

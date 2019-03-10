package se.plilja.example.model;

import java.io.IOException;
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
import se.plilja.example.dbframework.DatabaseException;

@Repository
public class StaffDao extends Dao<Staff, Integer> {

    public static final Column.IntColumn<Staff> COLUMN_STAFF_ID = new Column.IntColumn<>("staff_id", "staffId");

    public static final Column.BooleanColumn<Staff> COLUMN_ACTIVE = new Column.BooleanColumn<>("active", "`active`", "active");

    public static final Column.IntColumn<Staff> COLUMN_ADDRESS_ID = new Column.IntColumn<>("address_id", "addressId");

    public static final Column.StringColumn<Staff> COLUMN_EMAIL = new Column.StringColumn<>("email", "email");

    public static final Column.StringColumn<Staff> COLUMN_FIRST_NAME = new Column.StringColumn<>("first_name", "firstName");

    public static final Column.StringColumn<Staff> COLUMN_LAST_NAME = new Column.StringColumn<>("last_name", "lastName");

    public static final Column.DateTimeColumn<Staff> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column.StringColumn<Staff> COLUMN_PASSWORD = new Column.StringColumn<>("password", "`password`", "password");

    public static final Column<Staff, byte[]> COLUMN_PICTURE = new Column<>("picture", "picture", byte[].class);

    public static final Column.IntColumn<Staff> COLUMN_STORE_ID = new Column.IntColumn<>("store_id", "storeId");

    public static final Column.StringColumn<Staff> COLUMN_USERNAME = new Column.StringColumn<>("username", "username");

    public static final List<Column<Staff, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_STAFF_ID,
            COLUMN_ACTIVE,
            COLUMN_ADDRESS_ID,
            COLUMN_EMAIL,
            COLUMN_FIRST_NAME,
            COLUMN_LAST_NAME,
            COLUMN_LAST_UPDATE,
            COLUMN_PASSWORD,
            COLUMN_PICTURE,
            COLUMN_STORE_ID,
            COLUMN_USERNAME);

    private static final String ALL_COLUMNS = " staff_id, `active`, address_id, email, first_name, " +
            " last_name, last_update, `password`, picture, store_id, " +
            " username ";

    private static final RowMapper<Staff> ROW_MAPPER = (rs, i) -> {
        try {
            Staff r = new Staff();
            r.setStaffId(rs.getInt("staff_id"));
            r.setActive(rs.getBoolean("active"));
            r.setAddressId(rs.getInt("address_id"));
            r.setEmail(rs.getString("email"));
            r.setFirstName(rs.getString("first_name"));
            r.setLastName(rs.getString("last_name"));
            r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
            r.setPassword(rs.getString("password"));
            r.setPicture(rs.getObject("picture") != null ? rs.getBlob("picture").getBinaryStream().readAllBytes() : null);
            r.setStoreId(rs.getInt("store_id"));
            r.setUsername(rs.getString("username"));
            return r;
        } catch (IOException ex) {
            throw new DatabaseException("Caught exception while reading row", ex);
        }
    };

    @Autowired
    public StaffDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Staff.class, Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Staff o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("staff_id", o.getId(), Types.TINYINT);
        m.addValue("active", o.getActive(), Types.BIT);
        m.addValue("address_id", o.getAddressId(), Types.SMALLINT);
        m.addValue("email", o.getEmail(), Types.VARCHAR);
        m.addValue("first_name", o.getFirstName(), Types.VARCHAR);
        m.addValue("last_name", o.getLastName(), Types.VARCHAR);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("password", o.getPassword(), Types.VARCHAR);
        m.addValue("picture", o.getPicture(), Types.LONGVARBINARY);
        m.addValue("store_id", o.getStoreId(), Types.TINYINT);
        m.addValue("username", o.getUsername(), Types.VARCHAR);
        return m;
    }

    @Override
    protected RowMapper<Staff> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM staff " +
                "WHERE staff_id = :staff_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM staff " +
                "WHERE staff_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM staff " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO staff (" +
                "`active`, " +
                "address_id, " +
                "email, " +
                "first_name, " +
                "last_name, " +
                "last_update, " +
                "`password`, " +
                "picture, " +
                "store_id, " +
                "username" +
                ") " +
                "VALUES (" +
                ":active, " +
                ":address_id, " +
                ":email, " +
                ":first_name, " +
                ":last_name, " +
                ":last_update, " +
                ":password, " +
                ":picture, " +
                ":store_id, " +
                ":username" +
                ")";
    }

    @Override
    protected String getUpdateSql(Staff object) {
        return "UPDATE staff SET " +
                "active = :active, " +
                "address_id = :address_id, " +
                "email = :email, " +
                "first_name = :first_name, " +
                "last_name = :last_name, " +
                "last_update = :last_update, " +
                "password = :password, " +
                "picture = :picture, " +
                "store_id = :store_id, " +
                "username = :username " +
                "WHERE staff_id = :staff_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM staff " +
                "WHERE staff_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM staff";
    }

    @Override
    protected List<Column<Staff, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM staff %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM staff %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM staff " +
                "WHERE staff_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "staff_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

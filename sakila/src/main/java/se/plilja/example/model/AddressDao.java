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
public class AddressDao extends Dao<Address, Integer> {

    public static final Column.IntColumn<Address> COLUMN_ADDRESS_ID = new Column.IntColumn<>("address_id", "addressId");

    public static final Column.StringColumn<Address> COLUMN_ADDRESS = new Column.StringColumn<>("address", "address");

    public static final Column.StringColumn<Address> COLUMN_ADDRESS2 = new Column.StringColumn<>("address2", "address2");

    public static final Column.IntColumn<Address> COLUMN_CITY_ID = new Column.IntColumn<>("city_id", "cityId");

    public static final Column.StringColumn<Address> COLUMN_DISTRICT = new Column.StringColumn<>("district", "district");

    public static final Column.DateTimeColumn<Address> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column<Address, byte[]> COLUMN_LOCATION = new Column<>("location", "location", byte[].class);

    public static final Column.StringColumn<Address> COLUMN_PHONE = new Column.StringColumn<>("phone", "phone");

    public static final Column.StringColumn<Address> COLUMN_POSTAL_CODE = new Column.StringColumn<>("postal_code", "postalCode");

    public static final List<Column<Address, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_ADDRESS_ID,
            COLUMN_ADDRESS,
            COLUMN_ADDRESS2,
            COLUMN_CITY_ID,
            COLUMN_DISTRICT,
            COLUMN_LAST_UPDATE,
            COLUMN_LOCATION,
            COLUMN_PHONE,
            COLUMN_POSTAL_CODE);

    private static final String ALL_COLUMNS = " address_id, address, address2, city_id, district, " +
            " last_update, location, phone, postal_code ";

    private static final RowMapper<Address> ROW_MAPPER = (rs, i) -> {
        Address r = new Address();
        r.setAddressId(rs.getInt("address_id"));
        r.setAddress(rs.getString("address"));
        r.setAddress2(rs.getString("address2"));
        r.setCityId(rs.getInt("city_id"));
        r.setDistrict(rs.getString("district"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        r.setLocation((byte[]) rs.getObject("location"));
        r.setPhone(rs.getString("phone"));
        r.setPostalCode(rs.getString("postal_code"));
        return r;
    };

    @Autowired
    public AddressDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Address o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("address_id", o.getId(), Types.SMALLINT);
        m.addValue("address", o.getAddress(), Types.VARCHAR);
        m.addValue("address2", o.getAddress2(), Types.VARCHAR);
        m.addValue("city_id", o.getCityId(), Types.SMALLINT);
        m.addValue("district", o.getDistrict(), Types.VARCHAR);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("location", o.getLocation(), Types.BINARY);
        m.addValue("phone", o.getPhone(), Types.VARCHAR);
        m.addValue("postal_code", o.getPostalCode(), Types.VARCHAR);
        return m;
    }

    @Override
    protected RowMapper<Address> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM address " +
                "WHERE address_id = :address_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM address " +
                "WHERE address_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM address " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO address (" +
                "address, " +
                "address2, " +
                "city_id, " +
                "district, " +
                "last_update, " +
                "location, " +
                "phone, " +
                "postal_code" +
                ") " +
                "VALUES (" +
                ":address, " +
                ":address2, " +
                ":city_id, " +
                ":district, " +
                ":last_update, " +
                ":location, " +
                ":phone, " +
                ":postal_code" +
                ")";
    }

    @Override
    protected String getUpdateSql(Address object) {
        return "UPDATE address SET " +
                "address = :address, " +
                "address2 = :address2, " +
                "city_id = :city_id, " +
                "district = :district, " +
                "last_update = :last_update, " +
                "location = :location, " +
                "phone = :phone, " +
                "postal_code = :postal_code " +
                "WHERE address_id = :address_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM address " +
                "WHERE address_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM address";
    }

    @Override
    protected List<Column<Address, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM address %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM address %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM address " +
                "WHERE address_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "address_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

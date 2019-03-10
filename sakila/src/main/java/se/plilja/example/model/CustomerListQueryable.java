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
public class CustomerListQueryable extends Queryable<CustomerList> {

    public static final Column.StringColumn<CustomerList> COLUMN_ADDRESS = new Column.StringColumn<>("address", "address");

    public static final Column.StringColumn<CustomerList> COLUMN_CITY = new Column.StringColumn<>("city", "city");

    public static final Column.StringColumn<CustomerList> COLUMN_COUNTRY = new Column.StringColumn<>("country", "country");

    public static final Column.IntColumn<CustomerList> COLUMN_ID = new Column.IntColumn<>("ID", "id");

    public static final Column.StringColumn<CustomerList> COLUMN_NAME = new Column.StringColumn<>("name", "name");

    public static final Column.StringColumn<CustomerList> COLUMN_NOTES = new Column.StringColumn<>("notes", "notes");

    public static final Column.StringColumn<CustomerList> COLUMN_PHONE = new Column.StringColumn<>("phone", "phone");

    public static final Column.IntColumn<CustomerList> COLUMN_SID = new Column.IntColumn<>("SID", "sid");

    public static final Column.StringColumn<CustomerList> COLUMN_ZIP_CODE = new Column.StringColumn<>("zip code", "`zip code`", "zipCode");

    public static final List<Column<CustomerList, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_ADDRESS,
            COLUMN_CITY,
            COLUMN_COUNTRY,
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_NOTES,
            COLUMN_PHONE,
            COLUMN_SID,
            COLUMN_ZIP_CODE);

    private static final String ALL_COLUMNS = " address, city, country, ID, name, " +
            " notes, phone, SID, `zip code` ";

    private static final RowMapper<CustomerList> ROW_MAPPER = (rs, i) -> {
        CustomerList r = new CustomerList();
        r.setAddress(rs.getString("address"));
        r.setCity(rs.getString("city"));
        r.setCountry(rs.getString("country"));
        r.setId(rs.getInt("ID"));
        r.setName(rs.getString("name"));
        r.setNotes(rs.getString("notes"));
        r.setPhone(rs.getString("phone"));
        r.setSid(rs.getInt("SID"));
        r.setZipCode(rs.getString("zip code"));
        return r;
    };

    @Autowired
    public CustomerListQueryable(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected RowMapper<CustomerList> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM customer_list " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM customer_list";
    }

    @Override
    protected List<Column<CustomerList, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM customer_list %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM customer_list %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

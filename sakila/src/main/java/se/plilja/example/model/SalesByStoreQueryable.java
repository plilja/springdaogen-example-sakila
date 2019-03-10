package se.plilja.example.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import se.plilja.example.dbframework.Column;
import se.plilja.example.dbframework.Queryable;

@Repository
public class SalesByStoreQueryable extends Queryable<SalesByStore> {

    public static final Column.StringColumn<SalesByStore> COLUMN_MANAGER = new Column.StringColumn<>("manager", "manager");

    public static final Column.StringColumn<SalesByStore> COLUMN_STORE = new Column.StringColumn<>("store", "store");

    public static final Column.BigDecimalColumn<SalesByStore> COLUMN_TOTAL_SALES = new Column.BigDecimalColumn<>("total_sales", "totalSales");

    public static final List<Column<SalesByStore, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_MANAGER,
            COLUMN_STORE,
            COLUMN_TOTAL_SALES);

    private static final String ALL_COLUMNS = " manager, store, total_sales ";

    private static final RowMapper<SalesByStore> ROW_MAPPER = (rs, i) -> {
        SalesByStore r = new SalesByStore();
        r.setManager(rs.getString("manager"));
        r.setStore(rs.getString("store"));
        r.setTotalSales(rs.getBigDecimal("total_sales"));
        return r;
    };

    @Autowired
    public SalesByStoreQueryable(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected RowMapper<SalesByStore> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM sales_by_store " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM sales_by_store";
    }

    @Override
    protected List<Column<SalesByStore, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM sales_by_store %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM sales_by_store %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

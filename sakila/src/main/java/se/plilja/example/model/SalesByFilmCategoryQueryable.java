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
public class SalesByFilmCategoryQueryable extends Queryable<SalesByFilmCategory> {

    public static final Column.StringColumn<SalesByFilmCategory> COLUMN_CATEGORY = new Column.StringColumn<>("category", "category");

    public static final Column.BigDecimalColumn<SalesByFilmCategory> COLUMN_TOTAL_SALES = new Column.BigDecimalColumn<>("total_sales", "totalSales");

    public static final List<Column<SalesByFilmCategory, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_CATEGORY,
            COLUMN_TOTAL_SALES);

    private static final String ALL_COLUMNS = " category, total_sales ";

    private static final RowMapper<SalesByFilmCategory> ROW_MAPPER = (rs, i) -> {
        SalesByFilmCategory r = new SalesByFilmCategory();
        r.setCategory(rs.getString("category"));
        r.setTotalSales(rs.getBigDecimal("total_sales"));
        return r;
    };

    @Autowired
    public SalesByFilmCategoryQueryable(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected RowMapper<SalesByFilmCategory> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM sales_by_film_category " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM sales_by_film_category";
    }

    @Override
    protected List<Column<SalesByFilmCategory, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM sales_by_film_category %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM sales_by_film_category %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

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
public class NicerButSlowerFilmListQueryable extends Queryable<NicerButSlowerFilmList> {

    public static final Column.StringColumn<NicerButSlowerFilmList> COLUMN_ACTORS = new Column.StringColumn<>("actors", "actors");

    public static final Column.StringColumn<NicerButSlowerFilmList> COLUMN_CATEGORY = new Column.StringColumn<>("category", "category");

    public static final Column.StringColumn<NicerButSlowerFilmList> COLUMN_DESCRIPTION = new Column.StringColumn<>("description", "`description`", "description");

    public static final Column.IntColumn<NicerButSlowerFilmList> COLUMN_FID = new Column.IntColumn<>("FID", "fid");

    public static final Column.IntColumn<NicerButSlowerFilmList> COLUMN_LENGTH = new Column.IntColumn<>("length", "length");

    public static final Column.BigDecimalColumn<NicerButSlowerFilmList> COLUMN_PRICE = new Column.BigDecimalColumn<>("price", "price");

    public static final Column.StringColumn<NicerButSlowerFilmList> COLUMN_RATING = new Column.StringColumn<>("rating", "rating");

    public static final Column.StringColumn<NicerButSlowerFilmList> COLUMN_TITLE = new Column.StringColumn<>("title", "title");

    public static final List<Column<NicerButSlowerFilmList, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_ACTORS,
            COLUMN_CATEGORY,
            COLUMN_DESCRIPTION,
            COLUMN_FID,
            COLUMN_LENGTH,
            COLUMN_PRICE,
            COLUMN_RATING,
            COLUMN_TITLE);

    private static final String ALL_COLUMNS = " actors, category, `description`, FID, length, " +
            " price, rating, title ";

    private static final RowMapper<NicerButSlowerFilmList> ROW_MAPPER = (rs, i) -> {
        NicerButSlowerFilmList r = new NicerButSlowerFilmList();
        r.setActors(rs.getString("actors"));
        r.setCategory(rs.getString("category"));
        r.setDescription(rs.getString("description"));
        r.setFid(rs.getObject("FID") != null ? rs.getInt("FID") : null);
        r.setLength(rs.getObject("length") != null ? rs.getInt("length") : null);
        r.setPrice(rs.getBigDecimal("price"));
        r.setRating(rs.getString("rating"));
        r.setTitle(rs.getString("title"));
        return r;
    };

    @Autowired
    public NicerButSlowerFilmListQueryable(NamedParameterJdbcTemplate jdbcTemplate) {
        super(NicerButSlowerFilmList.class, jdbcTemplate);
    }

    @Override
    protected RowMapper<NicerButSlowerFilmList> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM nicer_but_slower_film_list " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM nicer_but_slower_film_list";
    }

    @Override
    protected List<Column<NicerButSlowerFilmList, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM nicer_but_slower_film_list %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM nicer_but_slower_film_list %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

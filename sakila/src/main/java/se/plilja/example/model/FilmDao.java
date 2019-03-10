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
public class FilmDao extends Dao<Film, Integer> {

    public static final Column.IntColumn<Film> COLUMN_FILM_ID = new Column.IntColumn<>("film_id", "filmId");

    public static final Column.StringColumn<Film> COLUMN_DESCRIPTION = new Column.StringColumn<>("description", "`description`", "description");

    public static final Column.IntColumn<Film> COLUMN_LANGUAGE_ID = new Column.IntColumn<>("language_id", "languageId");

    public static final Column.DateTimeColumn<Film> COLUMN_LAST_UPDATE = new Column.DateTimeColumn<>("last_update", "lastUpdate");

    public static final Column.IntColumn<Film> COLUMN_LENGTH = new Column.IntColumn<>("length", "length");

    public static final Column.IntColumn<Film> COLUMN_ORIGINAL_LANGUAGE_ID = new Column.IntColumn<>("original_language_id", "originalLanguageId");

    public static final Column.StringColumn<Film> COLUMN_RATING = new Column.StringColumn<>("rating", "rating");

    public static final Column.IntColumn<Film> COLUMN_RELEASE_YEAR = new Column.IntColumn<>("release_year", "releaseYear");

    public static final Column.IntColumn<Film> COLUMN_RENTAL_DURATION = new Column.IntColumn<>("rental_duration", "rentalDuration");

    public static final Column.BigDecimalColumn<Film> COLUMN_RENTAL_RATE = new Column.BigDecimalColumn<>("rental_rate", "rentalRate");

    public static final Column.BigDecimalColumn<Film> COLUMN_REPLACEMENT_COST = new Column.BigDecimalColumn<>("replacement_cost", "replacementCost");

    public static final Column.StringColumn<Film> COLUMN_SPECIAL_FEATURES = new Column.StringColumn<>("special_features", "specialFeatures");

    public static final Column.StringColumn<Film> COLUMN_TITLE = new Column.StringColumn<>("title", "title");

    public static final List<Column<Film, ?>> ALL_COLUMNS_LIST = Arrays.asList(
            COLUMN_FILM_ID,
            COLUMN_DESCRIPTION,
            COLUMN_LANGUAGE_ID,
            COLUMN_LAST_UPDATE,
            COLUMN_LENGTH,
            COLUMN_ORIGINAL_LANGUAGE_ID,
            COLUMN_RATING,
            COLUMN_RELEASE_YEAR,
            COLUMN_RENTAL_DURATION,
            COLUMN_RENTAL_RATE,
            COLUMN_REPLACEMENT_COST,
            COLUMN_SPECIAL_FEATURES,
            COLUMN_TITLE);

    private static final String ALL_COLUMNS = " film_id, `description`, language_id, last_update, length, " +
            " original_language_id, rating, release_year, rental_duration, rental_rate, " +
            " replacement_cost, special_features, title ";

    private static final RowMapper<Film> ROW_MAPPER = (rs, i) -> {
        Film r = new Film();
        r.setFilmId(rs.getInt("film_id"));
        r.setDescription(rs.getString("description"));
        r.setLanguageId(rs.getInt("language_id"));
        r.setLastUpdate(rs.getTimestamp("last_update").toLocalDateTime());
        r.setLength(rs.getObject("length") != null ? rs.getInt("length") : null);
        r.setOriginalLanguageId(rs.getObject("original_language_id") != null ? rs.getInt("original_language_id") : null);
        r.setRating(rs.getString("rating"));
        r.setReleaseYear(rs.getObject("release_year") != null ? rs.getInt("release_year") : null);
        r.setRentalDuration(rs.getInt("rental_duration"));
        r.setRentalRate(rs.getBigDecimal("rental_rate"));
        r.setReplacementCost(rs.getBigDecimal("replacement_cost"));
        r.setSpecialFeatures(rs.getString("special_features"));
        r.setTitle(rs.getString("title"));
        return r;
    };

    @Autowired
    public FilmDao(NamedParameterJdbcTemplate jdbcTemplate, CurrentUserProvider currentUserProvider) {
        super(Film.class, Integer.class, true, jdbcTemplate, currentUserProvider);
    }

    @Override
    protected SqlParameterSource getParams(Film o) {
        MapSqlParameterSource m = new MapSqlParameterSource();
        m.addValue("film_id", o.getId(), Types.SMALLINT);
        m.addValue("description", o.getDescription(), Types.LONGVARCHAR);
        m.addValue("language_id", o.getLanguageId(), Types.TINYINT);
        m.addValue("last_update", o.getLastUpdate(), Types.TIMESTAMP);
        m.addValue("length", o.getLength(), Types.SMALLINT);
        m.addValue("original_language_id", o.getOriginalLanguageId(), Types.TINYINT);
        m.addValue("rating", o.getRating(), Types.VARCHAR);
        m.addValue("release_year", o.getReleaseYear(), Types.INTEGER);
        m.addValue("rental_duration", o.getRentalDuration(), Types.TINYINT);
        m.addValue("rental_rate", o.getRentalRate(), Types.NUMERIC);
        m.addValue("replacement_cost", o.getReplacementCost(), Types.NUMERIC);
        m.addValue("special_features", o.getSpecialFeatures(), Types.VARCHAR);
        m.addValue("title", o.getTitle(), Types.VARCHAR);
        return m;
    }

    @Override
    protected RowMapper<Film> getRowMapper() {
        return ROW_MAPPER;
    }

    @Override
    protected String getExistsByIdSql() {
        return "SELECT " +
                "COUNT(*) " +
                "FROM film " +
                "WHERE film_id = :film_id";
    }

    @Override
    protected String getSelectIdsSql() {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM film " +
                "WHERE film_id IN (:ids)";
    }

    @Override
    protected String getSelectManySql(int maxSelectCount) {
        return String.format("SELECT " +
                ALL_COLUMNS +
                "FROM film " +
                "LIMIT %d", maxSelectCount);
    }

    @Override
    protected String getInsertSql() {
        return "INSERT INTO film (" +
                "`description`, " +
                "language_id, " +
                "last_update, " +
                "length, " +
                "original_language_id, " +
                "rating, " +
                "release_year, " +
                "rental_duration, " +
                "rental_rate, " +
                "replacement_cost, " +
                "special_features, " +
                "title" +
                ") " +
                "VALUES (" +
                ":description, " +
                ":language_id, " +
                ":last_update, " +
                ":length, " +
                ":original_language_id, " +
                ":rating, " +
                ":release_year, " +
                ":rental_duration, " +
                ":rental_rate, " +
                ":replacement_cost, " +
                ":special_features, " +
                ":title" +
                ")";
    }

    @Override
    protected String getUpdateSql(Film object) {
        return "UPDATE film SET " +
                "description = :description, " +
                "language_id = :language_id, " +
                "last_update = :last_update, " +
                "length = :length, " +
                "original_language_id = :original_language_id, " +
                "rating = :rating, " +
                "release_year = :release_year, " +
                "rental_duration = :rental_duration, " +
                "rental_rate = :rental_rate, " +
                "replacement_cost = :replacement_cost, " +
                "special_features = :special_features, " +
                "title = :title " +
                "WHERE film_id = :film_id";
    }

    @Override
    protected String getDeleteSql() {
        return "DELETE FROM film " +
                "WHERE film_id IN (:ids)";
    }

    @Override
    protected String getCountSql() {
        return "SELECT COUNT(*) FROM film";
    }

    @Override
    protected List<Column<Film, ?>> getColumnsList() {
        return ALL_COLUMNS_LIST;
    }

    @Override
    protected String getQueryOrderBySql(int maxAllowedCount, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM film %n" +
                "WHERE 1=1 %s %n" +
                "%s " +
                "LIMIT %d", whereClause, orderBy, maxAllowedCount);
    }

    @Override
    protected String getQueryPageOrderBySql(long start, int pageSize, String whereClause, String orderBy) {
        return String.format("SELECT %n" +
                ALL_COLUMNS +
                "FROM film %n" +
                "WHERE 1=1 %s %n" +
                "%s %n" +
                "LIMIT %d OFFSET %d", whereClause, orderBy, pageSize, start);
    }

    @Override
    protected String getSelectAndLockSql(String databaseProductName) {
        return "SELECT " +
                ALL_COLUMNS +
                "FROM film " +
                "WHERE film_id = :id " +
                "FOR UPDATE";
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return "film_id";
    }

    @Override
    protected int getSelectAllDefaultMaxCount() {
        return 1000;
    }

}

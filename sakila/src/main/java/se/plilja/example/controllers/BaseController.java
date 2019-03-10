package se.plilja.example.controllers;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import se.plilja.example.dbframework.Column;
import se.plilja.example.dbframework.Dao;
import se.plilja.example.dbframework.QueryItem;
import se.plilja.example.dbframework.Queryable;
import se.plilja.example.dbframework.SortOrder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BaseController<T> {
    @Autowired
    private Queryable<T> queryable;

    @SuppressWarnings("unchecked")
    @GetMapping
    public ResponseEntity<List<T>> list(
            @RequestParam(value = "pageStart", required = false) Long pageStart,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @ApiParam("Asc or desc") @RequestParam(value = "sortOrder", required = false) String sortOrder,
            HttpServletRequest request
    ) {
        SortOrder<T> sort = null;
        if (sortBy != null) {
            Column<T, ?> columnOrNull = queryable.getColumnByFieldNameIgnoreCase(sortBy);
            if (columnOrNull != null) {
                if ("desc".equalsIgnoreCase(sortOrder)) {
                    sort = SortOrder.desc(columnOrNull);
                } else {
                    sort = SortOrder.asc(columnOrNull);
                }
            }
        }
        List<QueryItem<T>> queryItems = new ArrayList<>();
        for (String paramName : request.getParameterMap().keySet()) {
            Column<T, ?> columnOrNull = queryable.getColumnByFieldNameIgnoreCase(paramName);
            if (columnOrNull != null) {
                if (columnOrNull instanceof Column.StringColumn<?>) {
                    queryItems.add(QueryItem.eq((Column.StringColumn<T>) columnOrNull, request.getParameter(paramName)));
                } else if (columnOrNull instanceof Column.IntColumn<?>) {
                    queryItems.add(QueryItem.eq((Column.IntColumn<T>) columnOrNull, Integer.parseInt(request.getParameter(paramName))));
                } else if (columnOrNull instanceof Column.LongColumn<?>) {
                    queryItems.add(QueryItem.eq((Column.LongColumn<T>) columnOrNull, Long.parseLong(request.getParameter(paramName))));
                } else if (columnOrNull instanceof Column.DateColumn<?>) {
                    queryItems.add(QueryItem.eq((Column.DateColumn<T>) columnOrNull, LocalDate.parse(request.getParameter(paramName))));
                } else if (columnOrNull instanceof Column.DateTimeColumn<?>) {
                    queryItems.add(QueryItem.eq((Column.DateTimeColumn<T>) columnOrNull, LocalDateTime.parse(request.getParameter(paramName))));
                } else if (columnOrNull instanceof Column.BooleanColumn<?>) {
                    queryItems.add(QueryItem.eq((Column.BooleanColumn<T>) columnOrNull, Boolean.valueOf(request.getParameter(paramName))));
                }
            }
        }
        if (pageStart != null) {
            if (sort != null) {
                return ResponseEntity.ok(queryable.findPageOrderBy(pageStart, pageSize, queryItems, sort));
            } else {
                if (queryable instanceof Dao<?, ?>) {
                    return ResponseEntity.ok(((Dao) queryable).findPage(pageStart, pageSize));
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must provide sort order when querying a view for a page");
                }
            }
        } else {
            if (sort != null) {
                return ResponseEntity.ok(queryable.findAllOrderBy(queryItems, sort));
            } else {
                return ResponseEntity.ok(queryable.findAll(queryItems));
            }
        }
    }

}

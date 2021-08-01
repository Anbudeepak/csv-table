package com.grootan.csvtable.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Deepak on 31/07/21.
 */
@Component
public class CsvTableDao {

    @Value("${csvtable.default.table.name:csvtable}")
    private String tableName;

    @Value("${csvtable.default.table.columns.size:20}")
    private String columnSize;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable(List<String> columns) {
        jdbcTemplate.execute(prepareCreateScript(columns));
    }

    public void insertValues(List<String> columns, List<String> values) {
        jdbcTemplate.execute(prepareInsertScript(columns, values));
    }

    public List<String> findAll() {
        return jdbcTemplate.queryForList("select * from " + tableName)
            .stream()
            .map(row -> row.values().stream().map(String::valueOf).collect(Collectors.joining(",")))
            .collect(Collectors.toList());
    }

    private String prepareCreateScript(List<String> columns) {
        final String columnsScript = columns
            .stream()
            .map(column -> column + " varchar2(" + columnSize + ")")
            .collect(Collectors.joining(","));

        return "create table " + tableName + " ( " + columnsScript + " )";
    }

    private String prepareInsertScript(List<String> columns, List<String> values) {
        final StringBuilder queryBuilder = new StringBuilder("insert into " + tableName);
        queryBuilder.append(" ( ").append(String.join(",", columns)).append(" ) ");
        queryBuilder.append("values( ").append(String.join(",", getSqlValues(values))).append(" ) ");
        return queryBuilder.toString();
    }

    private List<String> getSqlValues(List<String> values) {
        return values
            .stream()
            .map(v -> "'" + v + "'")
            .collect(Collectors.toList());
    }
}

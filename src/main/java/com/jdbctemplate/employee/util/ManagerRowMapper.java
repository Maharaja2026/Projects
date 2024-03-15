package com.jdbctemplate.employee.util;

import com.jdbctemplate.employee.entity.Manager;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerRowMapper implements RowMapper<Manager>
{
    @Override
    public Manager mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Manager manager = new Manager();
        manager.setManagerId(rs.getInt("manager_id"));
        manager.setManagerName(rs.getString("manager_name"));
        manager.setManagerEmail(rs.getString("manager_email"));
        manager.setManagerPassword(rs.getString("manager_password"));
        return manager;
    }
}

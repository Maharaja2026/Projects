package com.jdbctemplate.employee.dao;

import com.jdbctemplate.employee.entity.Manager;
import com.jdbctemplate.employee.model.ManagerModel;
import com.jdbctemplate.employee.util.ManagerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ManagerDao implements ManagerModel
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void saveManager(Manager manager)
    {
        String query = "insert into manager (manager_name,manager_email,manager_password) values (?,?,?)";
        jdbcTemplate.update(query,manager.getManagerName(),manager.getManagerEmail(),manager.getManagerPassword());
    }

    @Override
    public Manager findManagerById(int managerId) {
        String query = "select * from manager where manager_id=?";
        return jdbcTemplate.queryForObject(query,new Object[]{managerId},new ManagerRowMapper());
    }

    @Override
    public List<Manager> findAll() {
        String query = "select * from manager";
        return jdbcTemplate.query(query,new ManagerRowMapper());
    }

    @Override
    public void updateManager(Manager manager, int managerId)
    {
        String query = "update manager set manager_name=?,manager_email=?,manager_password=? where manager_id=?";
        jdbcTemplate.update(query,manager.getManagerName(),manager.getManagerEmail(),manager.getManagerPassword(),managerId);
    }

    @Override
    public void deleteManager(int managerId)
    {
        String query = "delete from manager where manager_id=?";
        jdbcTemplate.update(query,managerId);
    }

    @Override
    public Manager findManagerByEmail(String managerEmail) {
        String query = "select * from manager where manager_email=?";
        return jdbcTemplate.queryForObject(query,new Object[]{managerEmail},new ManagerRowMapper());
    }



}

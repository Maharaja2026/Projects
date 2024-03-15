package com.jdbctemplate.employee.model;

import com.jdbctemplate.employee.entity.Manager;

import java.util.List;

public interface ManagerModel
{
    public void saveManager(Manager manager);

    public Manager findManagerById(int managerId);

    public List<Manager> findAll();

    public void updateManager(Manager manager, int managerId);

    public void deleteManager(int managerId);

    public Manager findManagerByEmail(String managerEmail);
}

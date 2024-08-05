package com.active.dao.repository;


import com.active.dao.enity.Employee;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmployeeRespository implements PanacheRepository<Employee> {

}
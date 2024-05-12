package com.dbh.dao.impl;

import com.dbh.dao.EmployeeDAO;
import com.dbh.entity.Employee;
import com.dbh.utils.JpaConfig;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> findAll(int offset, int recordPerPage) {
        EntityManager em = JpaConfig.getEntityManager();
        String sql = "SELECT e FROM Employee e ORDER BY e.employeeId ASC";  //JPQL
        TypedQuery<Employee> emPQuery = em.createQuery(sql, Employee.class);
        emPQuery.setFirstResult(offset);
        emPQuery.setMaxResults(recordPerPage);
        return emPQuery.getResultList();
    }

    @Override
    public void save(Employee employee) {
        EntityManager em = JpaConfig.getEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void update(Employee employee) {
        EntityManager em = JpaConfig.getEntityManager();
        em.getTransaction().begin();
        em.merge(employee);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaConfig.getEntityManager();
        Employee employee = em.find(Employee.class, id);
        em.getTransaction().begin();
        em.remove(employee);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public Employee findById(int id) {
        EntityManager em = JpaConfig.getEntityManager();
        return em.find(Employee.class, id);
    }

    @Override
    public Integer count() {
        EntityManager em = JpaConfig.getEntityManager();
        String sql = "SELECT COUNT(*) FROM Employee";
        TypedQuery<Long> emPQuery = em.createQuery(sql, Long.class);
        return Math.toIntExact(emPQuery.getSingleResult());
    }
}

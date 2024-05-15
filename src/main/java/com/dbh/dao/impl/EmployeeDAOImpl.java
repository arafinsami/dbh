package com.dbh.dao.impl;

import com.dbh.dao.EmployeeDAO;
import com.dbh.entity.Employee;
import com.dbh.utils.HibernateConfig;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> findAll(int offset, int recordPerPage) {
        Session session = HibernateConfig.getSession();
        String sql = "SELECT e FROM Employee e ORDER BY e.employeeId ASC";  //JPQL
        TypedQuery<Employee> emPQuery = session.createQuery(sql, Employee.class);
        emPQuery.setFirstResult(offset);
        emPQuery.setMaxResults(recordPerPage);
        return emPQuery.getResultList();
    }

    @Override
    public void save(Employee employee) {
        Session session = HibernateConfig.getSession();
        session.getTransaction().begin();
        session.persist(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Employee employee) {
        Session session = HibernateConfig.getSession();
        session.getTransaction().begin();
        session.merge(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(int id) {
        Session session = HibernateConfig.getSession();
        Employee employee = session.find(Employee.class, id);
        session.getTransaction().begin();
        session.remove(employee);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Employee findById(int id) {
        Session session = HibernateConfig.getSession();
        return session.find(Employee.class, id);
    }

    @Override
    public Integer count() {
        Session session = HibernateConfig.getSession();
        String sql = "SELECT COUNT(*) FROM Employee";
        TypedQuery<Long> emPQuery = session.createQuery(sql, Long.class);
        return Math.toIntExact(emPQuery.getSingleResult());
    }
}

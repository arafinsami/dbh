package com.dbh.controller;


import com.dbh.dao.EmployeeDAO;
import com.dbh.dao.impl.EmployeeDAOImpl;
import com.dbh.entity.Employee;
import com.dbh.service.EmployeeService;
import com.dbh.service.impl.EmployeeServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

@WebServlet("/")
public class EmployeeHomeController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private EmployeeService employeeService;

    public EmployeeHomeController() {
    }

    @Override
    public void init() {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        this.employeeService = new EmployeeServiceImpl(employeeDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int recordsPerPage = 5;
        int offset = (page - 1) * recordsPerPage;
        List<Employee> employees = employeeService.findAll(offset, recordsPerPage);
        int totalEmployees = employeeService.count();
        int totalPages = (int) Math.ceil((double) totalEmployees / recordsPerPage);
        request.setAttribute("employees", employees);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        RequestDispatcher view = request.getRequestDispatcher("/pages/home.jsp");
        view.forward(request, response);
    }
}

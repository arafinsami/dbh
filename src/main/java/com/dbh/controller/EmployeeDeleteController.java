package com.dbh.controller;

import com.dbh.dao.EmployeeDAO;
import com.dbh.dao.impl.EmployeeDAOImpl;
import com.dbh.service.EmployeeService;
import com.dbh.service.impl.EmployeeServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/delete")
public class EmployeeDeleteController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private EmployeeService employeeService;

    public EmployeeDeleteController() {
    }

    @Override
    public void init() throws ServletException {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        this.employeeService = new EmployeeServiceImpl(employeeDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int employeeId = Integer.parseInt(request.getParameter("id"));
        employeeService.delete(employeeId);
        response.sendRedirect("/");
    }
}

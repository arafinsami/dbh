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


@WebServlet("/edit")
public class EmployeeEditController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private EmployeeService employeeService;

    public EmployeeEditController() {
    }

    @Override
    public void init() {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        this.employeeService = new EmployeeServiceImpl(employeeDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int employeeId = Integer.parseInt(request.getParameter("id"));
        Employee employee = employeeService.findById(employeeId);
        request.setAttribute("employee", employee);
        RequestDispatcher view = request.getRequestDispatcher("/pages/edit.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Employee employee = Employee.builder()
                .employeeId(Integer.parseInt(request.getParameter("id")))
                .name(request.getParameter("name"))
                .email(request.getParameter("email"))
                .password(request.getParameter("password"))
                .build();
        employeeService.update(employee);
        response.sendRedirect("/");
    }
}

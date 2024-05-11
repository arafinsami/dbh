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
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serial;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@WebServlet(urlPatterns = {"/", "/save", "/delete", "/edit", "/details"})
public class EmployeeController extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;

    private EmployeeService employeeService;

    public EmployeeController() {
    }

    @Override
    public void init() {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        this.employeeService = new EmployeeServiceImpl(employeeDAO);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/":
                showHomePage(request, response);
                break;
            case "/save":
                showSaveForm(request, response);
                break;
            case "/delete":
                deleteEmployee(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/details":
                showDetails(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/save":
                saveEmployee(request, response);
                break;
            case "/edit":
                updateEmployee(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        try {
            int recordsPerPage = 5;
            int offset = (page - 1) * recordsPerPage;
            List<Employee> employees = employeeService.findAll(offset, recordsPerPage);
            int totalEmployees = employeeService.count();
            int totalPages = (int) Math.ceil((double) totalEmployees / recordsPerPage);
            request.setAttribute("employees", employees);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        RequestDispatcher view = request.getRequestDispatcher("/pages/home.jsp");
        view.forward(request, response);
    }

    private void showSaveForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/pages/save.jsp");
        view.forward(request, response);
    }

    private void saveEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Employee employee = Employee.builder()
                    .name(request.getParameter("name"))
                    .email(request.getParameter("email"))
                    .password(request.getParameter("password"))
                    .build();
            employeeService.save(employee);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        response.sendRedirect("/");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("id"));
            employeeService.delete(employeeId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        response.sendRedirect("/");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("id"));
            Employee employee = employeeService.findById(employeeId);
            request.setAttribute("employee", employee);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        RequestDispatcher view = request.getRequestDispatcher("/pages/edit.jsp");
        view.forward(request, response);
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Employee employee = Employee.builder()
                    .employeeId(Integer.parseInt(request.getParameter("id")))
                    .name(request.getParameter("name"))
                    .email(request.getParameter("email"))
                    .password(request.getParameter("password"))
                    .build();
            employeeService.update(employee);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        response.sendRedirect("/");
    }

    private void showDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int employeeId = Integer.parseInt(request.getParameter("id"));
            Employee employee = employeeService.findById(employeeId);
            request.setAttribute("employee", employee);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        RequestDispatcher view = request.getRequestDispatcher("/pages/details.jsp");
        view.forward(request, response);
    }
}

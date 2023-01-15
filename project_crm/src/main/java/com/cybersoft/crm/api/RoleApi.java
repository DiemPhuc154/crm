package com.cybersoft.crm.api;

import com.cybersoft.crm.model.RoleModel;
import com.cybersoft.crm.payload.ResponseData;
import com.cybersoft.crm.service.RoleService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "roleApi",urlPatterns = {"/api/role/add", "/api/role/delete", "/api/role/update"})
public class RoleApi extends HttpServlet {

    private RoleService roleService = new RoleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));
        boolean isSuccess = roleService.deleteRolesById(id);

        ResponseData responseData = new ResponseData();
        responseData.setStatus(200);
        responseData.setSuccess(isSuccess);
        responseData.setDescription(isSuccess ? "Xoá thành công" : "Xoá thất bại");

        Gson gson = new Gson();
        //toJson : biến đối tượng thành chuỗi JSON
        String json = gson.toJson(responseData);

        out.print(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ResponseData responseData = new ResponseData();
        String path = req.getServletPath();
        switch (path){
            case "/api/role/add":
                String nameAdd = req.getParameter("name");
                String descriptionAdd = req.getParameter("description");
                boolean isSuccessAdd = roleService.insertRole(nameAdd,descriptionAdd);
                responseData.setStatus(200);
                responseData.setSuccess(isSuccessAdd);
                responseData.setDescription(isSuccessAdd ?  "Thêm thành công" : "Thêm thất bại");
                break;
            case "/api/role/update":
                int id = Integer.parseInt(req.getParameter("id"));
                String nameUpdate = req.getParameter("name");
                String descriptionUpdate = req.getParameter("description");
                RoleModel roleModel = new RoleModel(id, nameUpdate, descriptionUpdate);
                boolean isSuccessUpdate = roleService.updateRole(roleModel);
                responseData.setStatus(200);
                responseData.setSuccess(isSuccessUpdate);
                responseData.setDescription(isSuccessUpdate ?  "Cập nhật thành công" : "Cập nhật thất bại");
                break;
        }
        Gson gson = new Gson();
        String json = gson.toJson(responseData);
        out.println(json);
        out.flush();
    }
}

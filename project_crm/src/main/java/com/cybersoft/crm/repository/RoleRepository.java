package com.cybersoft.crm.repository;

import com.cybersoft.crm.config.MysqlConnection;
import com.cybersoft.crm.model.RoleModel;
import com.cybersoft.crm.model.UsersModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {

    public List<RoleModel> getRoles(){
        List<RoleModel> list = new ArrayList<>();
        try{
            String query = "select * from roles";
            Connection connection = MysqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                RoleModel roleModel = new RoleModel();
                roleModel.setId(resultSet.getInt("id"));
                roleModel.setName(resultSet.getString("name"));
                roleModel.setDescription(resultSet.getString("description"));

                list.add(roleModel);
            }

            connection.close();

        }catch (Exception e){
            System.out.println("Error getUsersByEmailAndPassword " + e.getMessage());
        }

        return list;
    }

    public int deleteRolesById(int id){
        int result = 0;
        try{
            String query = "delete from roles r where r.id = ?";
            Connection connection = MysqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            result = preparedStatement.executeUpdate();
            connection.close();

        }catch (Exception e){
            System.out.println("Error getUsersByEmailAndPassword " + e.getMessage());
        }

        return result;
    }
    public int insertRole(String name, String description){
        int result = 0;
        try{
            String query = "insert into roles(name, description) values(?,?)";
            Connection connection = MysqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,description);
            result = preparedStatement.executeUpdate();
            connection.close();
        }
        catch(Exception e){
            System.out.println("Error insert role" + e.getMessage());
        }
        return result;
    }
    public int updateRole(RoleModel role) {
        int result = 0;
        try {
            String query = "update roles set name = ?, description = ? where id = ?";
            Connection connection = MysqlConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setString(2, role.getDescription());
            preparedStatement.setInt(3, role.getId());
            result = preparedStatement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error insert role" + e.getMessage());
        }
        return result;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dzerik.menu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dzerik.menu.config.AppDataSource;
import ru.dzerik.menu.models.Dish;

/**
 *
 * @author Strobo
 */
@Component
public class MenuDAO {
    @Autowired
    AppDataSource ds;
    
    private static final String GET_DISH_SQL="SELECT * FROM \"PUBLIC\".DISH;";
    private static final String GET_DISH_BYID_SQL="SELECT * FROM \"PUBLIC\".DISH WHERE ID=?;";
    private static final String GET_NEXT_DISH_ID="select max(id)+1 as id from public.dish;";
    private static final String ADD_NEW_DISH="INSERT INTO DISH (id,productname,weight,fullcost,halfcost,description ) VALUES (?,?,?,?,?,?);";
    
    public List<Dish> getDish() throws Exception{
        List<Dish> list = new ArrayList<Dish>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            statement = conn.prepareStatement(GET_DISH_SQL);
            
            rs = statement.executeQuery();
            
            while (rs.next()){
                Dish dish = new Dish(
                        rs.getInt("ID"),
                        rs.getString("PRODUCTNAME"),
                        rs.getInt("WEIGHT"),
                        rs.getInt("FULLCOST"),
                        rs.getInt("HALFCOST"),
                        rs.getString("DESCRIPTION")
                );
                list.add(dish);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error");
        } finally{
            
                try {
                    if(rs != null) rs.close();
                    if(statement != null) statement.close();
                    if(conn != null) conn.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
        }
           
        return list;
    }
    
    public Dish getDishByID(Integer id) throws Exception{
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Dish dish = null;
        try {
            conn = ds.getConnection();
            statement = conn.prepareStatement(GET_DISH_BYID_SQL);
            statement.setInt(1, id);
            
            rs = statement.executeQuery();
            
            while (rs.next()){
                dish = new Dish(
                        rs.getInt("ID"),
                        rs.getString("PRODUCTNAME"),
                        rs.getInt("WEIGHT"),
                        rs.getInt("FULLCOST"),
                        rs.getInt("HALFCOST"),
                        rs.getString("DESCRIPTION")
                );
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error");
        } finally{
            
                try {
                    if(rs != null) rs.close();
                    if(statement != null) statement.close();
                    if(conn != null) conn.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
        }
        return dish;
    }
    
    public Integer addDish(
            String productname, 
            Integer weight, 
            Integer fullcost,
            Integer halcost,
            String description) throws Exception{
        ResultSet rs_id = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        Dish dish = null;
        Integer id = null;
        try {
            conn = ds.getConnection();
            statement = conn.prepareStatement(GET_NEXT_DISH_ID);
            rs_id = statement.executeQuery();
            
            if (rs_id.next()){
                        id = rs_id.getInt("ID");
                        
            } else {
                throw new Exception("Error, not set ID");
            }
            
            statement=conn.prepareStatement(ADD_NEW_DISH);
            statement.setInt(1, id);
            statement.setString(2, productname);
            statement.setInt(3, weight);
            statement.setInt(4, fullcost);
            statement.setInt(5, halcost);
            statement.setString(6, description);
            statement.execute();
            conn.commit();
//            if(rs.rowInserted()) {
//                return id;
//            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
            conn.rollback();
            throw new Exception("Error");
        } finally{
            
                try {
                    if(rs != null) rs.close();
                    if(statement != null) statement.close();
                    if(conn != null) conn.close();
                    
                } catch (SQLException ex) {
                    Logger.getLogger(MenuDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            
        }
        return id;
    }
}

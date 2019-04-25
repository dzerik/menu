/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dzerik.menu.config;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Strobo
 */
@Component
public class AppDataSource {
    @Autowired
    private DataSource ds;    
    
    public Connection getConnection() throws SQLException {
        Connection conn = ds.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }
    
}

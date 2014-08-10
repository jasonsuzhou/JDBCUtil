package com.mh.demo.h2;

import java.sql.Connection;  
import java.sql.SQLException;  
import javax.naming.Context;  
import javax.naming.InitialContext;  
import javax.naming.NamingException;  
import javax.sql.DataSource;  
import org.h2.jdbcx.JdbcDataSource;  

public class JNDIDataSourceRegistor {  
   
    public static void register(){  
         JdbcDataSource ds = new JdbcDataSource();  
         ds.setURL("jdbc:h2:÷/test");  
         ds.setUser("sa");  
         ds.setPassword("123456");  
         Context ctx;  
         try {  
            ctx = new InitialContext();  
             try {  
                    ctx.bind("jdbc/dsName", ds);  
                } catch (NamingException e) {  
                    e.printStackTrace();  
                }  
        } catch (NamingException e) {  
            e.printStackTrace();  
        }  
    }  
      
    public static Connection getConnection(){  
        Context ctx;  
        try {  
            ctx = new InitialContext();  
             DataSource ds = (DataSource) ctx.lookup("jdbc/dsName");  
             try {  
                Connection conn = ds.getConnection();  
                return conn;  
            } catch (SQLException e) {  
                e.printStackTrace();  
            }  
        } catch (NamingException e) {  
            e.printStackTrace();  
        }  
        return null;  
          
    }  
    public static void main(String[] args) {  
        JNDIDataSourceRegistor.register();  
        System.out.println(JNDIDataSourceRegistor.getConnection());  
    }  
  
}   


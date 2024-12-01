/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import View.FormLogin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ACER
 */
public class connector {
     private static Connection connection;

    public static Connection getConnection() {
        //cek apakah koneksi null
        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/agendaku";
                String user = "root";
                String pass = "";
                DriverManager.registerDriver (new com.mysql.cj.jdbc.Driver());
                connection = DriverManager.getConnection(url, user, pass);
                
               
            } catch (SQLException t) {
                System.out.println("Error Membuat Koneksi");
            }
        }
        return connection;
    }
    
}

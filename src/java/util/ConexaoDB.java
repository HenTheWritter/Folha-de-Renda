package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/bdueg202601";
    private static final String USER = "postgres";
    private static final String PASS = "123456";

    public static Connection conectar() 
    {
        try 
        {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } 
        catch (ClassNotFoundException | SQLException e) 
        {
            throw new RuntimeException("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}
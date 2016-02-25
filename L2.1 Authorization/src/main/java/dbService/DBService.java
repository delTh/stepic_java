package dbService;

import accounts.UserProfile;
import dbService.dao.UsersDAO;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by delth on 1/17/16.
 */
public class DBService {
    private final Connection connection;

    public DBService(){
        this.connection = getH2Connection();

    }

    public void addUser(UserProfile userProfile) throws DBException {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.InsertUser(userProfile);
            connection.commit();

        } catch (SQLException e ) {
            try {
                connection.rollback();
            } catch (SQLException ignore){}
            throw new DBException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {}
        }
    }

    public UserProfile getUser(String login) throws DBException {
        try {
            return (new UsersDAO(connection).get(login));
        } catch (SQLException e) {
            throw new DBException(e);
        }


    }


    private Connection getH2Connection() {
        try{
            String url="jdbc:h2:./h2db";
            String name="tully";
            String pass="tully";
            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            Connection connection = DriverManager.getConnection(url,name,pass);
            return connection;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void printConnectionInfo(){
       try {
           System.out.println("DB name " + connection.getMetaData().getDatabaseProductName());
           System.out.println("DB version " + connection.getMetaData().getDatabaseProductVersion());
           System.out.println("Driver " + connection.getMetaData().getDriverName());
           System.out.println("Autocommit " + connection.getAutoCommit());

       } catch (SQLException e) {
           e.printStackTrace();
       }

    }
}

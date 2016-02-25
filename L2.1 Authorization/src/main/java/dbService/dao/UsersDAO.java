package dbService.dao;

import accounts.UserProfile;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by delth on 1/17/16.
 */
public class UsersDAO {
    private Executor executor;

    public UsersDAO(Connection connection) throws SQLException {
        this.executor = new Executor(connection);
        executor.execUpdate("create table if not exists users (user_name varchar(256), user_password varchar(256)," +
                " user_mail varchar(256), primary key (user_name))");
    }

    public void InsertUser (UserProfile userProfile) throws SQLException{

        try {
            get(userProfile.getLogin()).getLogin();
        } catch (SQLException e){
            executor.execUpdate("insert into users (user_name, user_password, user_mail) values ('" + userProfile.getLogin() +
                    "', '" + userProfile.getPass() + "','" + userProfile.getEmail() + "')");
        }

    }

    public UserProfile get(String login) throws SQLException{
        return executor.execQuery("Select * from users where user_name = '"+login+"'",
                result -> {
                    result.next();
                    return new UserProfile(result.getString(1), result.getString(2), result.getString(3)); } );
    }

}

package dbService.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by delth on 1/17/16.
 */
public class Executor {
    private Connection connection;

    public Executor(Connection connection){
        this.connection = connection;
    }

    public void execUpdate(String str) throws SQLException {
        Statement stm = connection.createStatement();
        stm.execute(str);
        stm.close();
    }

    public <T> T execQuery(String str, ResultHandler<T> handler) throws SQLException {
        Statement stm = connection.createStatement();
        stm.executeQuery(str);
        ResultSet result = stm.getResultSet();
        T value = handler.handle(result);
        result.close();
        stm.close();

        return value;
    }

}

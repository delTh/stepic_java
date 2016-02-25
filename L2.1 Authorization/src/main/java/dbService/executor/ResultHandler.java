package dbService.executor;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by delth on 1/17/16.
 */
public interface ResultHandler <T>  {
    T handle(ResultSet resultSet) throws SQLException;
}

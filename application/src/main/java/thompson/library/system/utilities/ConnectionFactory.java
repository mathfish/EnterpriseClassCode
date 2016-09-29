package thompson.library.system.utilities;

import java.sql.Connection;


public interface ConnectionFactory {

    Connection getConnection();
}

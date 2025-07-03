package cn.addenda.component.mybatis.idfilling.idgenerator.snowflake;

import cn.addenda.component.base.exception.ExceptionUtils;
import cn.addenda.component.base.util.ConnectionUtils;
import cn.addenda.component.mybatis.idfilling.IdFillingException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

/**
 * @author addenda
 * @since 2023/6/4 19:25
 */
public class DbWorkerIdGenerator implements SnowflakeWorkerIdGenerator {

  private final DataSource dataSource;

  private final String appName;

  public DbWorkerIdGenerator(String appName, DataSource dataSource) {
    this.appName = appName;
    this.dataSource = dataSource;
  }

  @Override
  public long workerId() {
    try {
      return doGetWorkerId();
    } catch (Exception e) {
      throw ExceptionUtils.wrapAsRuntimeException(e, IdFillingException.class);
    }
  }

  private long doGetWorkerId() throws SQLException {
    Connection connection = dataSource.getConnection();
    boolean originalAutoCommit = false;
    int originalTransactionIsolation = -1;
    try {
      originalAutoCommit = ConnectionUtils.setAutoCommitFalse(connection);
      originalTransactionIsolation = ConnectionUtils.setTransactionIsolation(connection, TRANSACTION_SERIALIZABLE);

      boolean b = checkAppNameExists(connection);
      if (!b) {
        insertAppName(connection);
      }

      long workerId = queryNextId(connection);
      incrementNextId(connection, workerId);
      connection.commit();
      return (workerId % (1 << SnowflakeIdGenerator.WORKER_ID_BITS));
    } finally {
      ConnectionUtils.setTransactionIsolation(connection, originalTransactionIsolation);
      ConnectionUtils.setAutoCommit(connection, originalAutoCommit);
      ConnectionUtils.close(connection);
    }
  }

  private static final String APP_NAME_COUNT_SQL = "select id as count from t_snow_flake_worker_id where app_name = ? for update";

  private boolean checkAppNameExists(Connection connection) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(APP_NAME_COUNT_SQL);
    try {
      preparedStatement.setString(1, appName);
      ResultSet resultSet = preparedStatement.executeQuery();
      long id = -1;
      while (resultSet.next()) {
        id = resultSet.getLong("id");
      }
      return id == 1;
    } finally {
      ConnectionUtils.close(preparedStatement);
    }
  }

  private static final String INSERT_APP_NAME_SQL = "insert into t_snow_flake_worker_id(next_id, app_name) values(0, ?)";

  private void insertAppName(Connection connection) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(INSERT_APP_NAME_SQL);
    try {
      preparedStatement.setString(1, appName);
      preparedStatement.executeUpdate();
    } finally {
      ConnectionUtils.close(preparedStatement);
    }
  }

  private static final String QUERY_NEXT_ID_SQL = "select next_id from t_snow_flake_worker_id where app_name = ? for update";

  private long queryNextId(Connection connection) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(QUERY_NEXT_ID_SQL);
    try {
      preparedStatement.setString(1, appName);
      ResultSet resultSet = preparedStatement.executeQuery();
      long nextId = -1;
      while (resultSet.next()) {
        nextId = resultSet.getLong("next_id");
      }
      return nextId;
    } finally {
      ConnectionUtils.close(preparedStatement);
    }
  }

  private static final String INCREMENT_NEXT_ID_SQL = "update t_snow_flake_worker_id set next_id = ? where app_name = ?";

  private void incrementNextId(Connection connection, long curId) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(INCREMENT_NEXT_ID_SQL);
    try {
      preparedStatement.setLong(1, curId + 1);
      preparedStatement.setString(2, appName);
      preparedStatement.executeUpdate();
    } finally {
      ConnectionUtils.close(preparedStatement);
    }
  }

}

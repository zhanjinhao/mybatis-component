package cn.addenda.component.mybatis.test.idfilling.idgeneretor;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.junit.Before;

import javax.sql.DataSource;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

public class AbstractIdGeneratorTest {

  protected DataSource dataSource;

  @Before
  public void before() throws Exception {
    String resource = "cn/addenda/component/mybatis/test/db.properties";
    Reader reader = Resources.getResourceAsReader(resource);
    Properties properties = new Properties();
    properties.load(reader);

    HikariDataSource hikariDataSource = new HikariDataSource();
    hikariDataSource.setJdbcUrl(properties.getProperty("db.driver"));
    hikariDataSource.setJdbcUrl(properties.getProperty("db.url"));
    hikariDataSource.setUsername(properties.getProperty("db.username"));
    hikariDataSource.setPassword(properties.getProperty("db.password"));
    hikariDataSource.setMaximumPoolSize(1);

    this.dataSource = hikariDataSource;
  }

  protected void createTable() throws Exception {
    String create_table_ddl =
            "create table t_snow_flake_worker_id\n" +
                    "(\n" +
                    "    id       int auto_increment\n" +
                    "        primary key,\n" +
                    "    next_id  int         not null,\n" +
                    "    app_name varchar(50) not null,\n" +
                    "    constraint t_snow_flake_worker_id_app_name_uindex\n" +
                    "        unique (app_name)\n" +
                    ");\n";
    try (Connection connection = dataSource.getConnection()) {
      Statement statement = connection.createStatement();
      statement.execute(create_table_ddl);
    }
  }


  protected void dropTable() throws Exception {
    String create_table_ddl =
            "drop table t_snow_flake_worker_id";
    try (Connection connection = dataSource.getConnection();) {
      Statement statement = connection.createStatement();
      statement.execute(create_table_ddl);
    }
  }
}

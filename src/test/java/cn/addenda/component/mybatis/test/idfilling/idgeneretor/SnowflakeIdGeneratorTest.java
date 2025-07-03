package cn.addenda.component.mybatis.test.idfilling.idgeneretor;

import cn.addenda.component.mybatis.idfilling.idgenerator.snowflake.DbWorkerIdGenerator;
import cn.addenda.component.mybatis.idfilling.idgenerator.snowflake.SnowflakeIdGenerator;
import org.junit.Assert;
import org.junit.Test;

public class SnowflakeIdGeneratorTest extends AbstractIdGeneratorTest {

  @Test
  public void test() throws Exception {
    createTable();

    DbWorkerIdGenerator dbWorkerIdGenerator = new DbWorkerIdGenerator("SnowflakeIdGeneratorTest", super.dataSource);
    SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(dbWorkerIdGenerator);
    Long l = snowflakeIdGenerator.nextId("SnowflakeIdGeneratorTest");
    Assert.assertNotNull(l);

    dropTable();
  }

}

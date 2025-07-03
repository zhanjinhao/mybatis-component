package cn.addenda.component.mybatis.test.idfilling.idgeneretor;

import cn.addenda.component.mybatis.idfilling.idgenerator.snowflake.DbWorkerIdGenerator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author addenda
 * @since 2023/6/4 19:58
 */
public class DbWorkerIdGeneratorTest extends AbstractIdGeneratorTest {

  @Test
  public void test0() throws Exception {
    createTable();
    DbWorkerIdGenerator dbWorkerIdGenerator = new DbWorkerIdGenerator("dbWorkerTest", super.dataSource);
    for (int i = 0; i < 10000; i++) {
      dbWorkerIdGenerator.workerId();
    }
    dropTable();
  }


  @Test
  public void test1() throws Exception {
    createTable();

    long workerId = -1L;
    DbWorkerIdGenerator dbWorkerIdGenerator = new DbWorkerIdGenerator("dbWorkerTest", super.dataSource);
    for (int i = 0; i < 10000; i++) {
      workerId = dbWorkerIdGenerator.workerId();
    }

    Assert.assertEquals(783L, workerId);
    dropTable();
  }

}

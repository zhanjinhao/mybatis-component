package cn.addenda.component.mybatis.test.idfilling;

import cn.addenda.component.mybatis.helper.BatchDmlHelper;
import cn.addenda.component.mybatis.test.TCourse;
import cn.addenda.component.mybatis.test.TCourseMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author addenda
 * @since 2022/2/3 17:39
 */
public class IdFillingTest {

  private SqlSessionFactory sqlSessionFactory;
  private SqlSession sqlSession;
  private TCourseMapper tCourseMapper;

  @Before
  public void before() {
    String resource = "cn/addenda/component/mybatis/test/mybatis-config.xml";
    Reader reader;
    try {
      reader = Resources.getResourceAsReader(resource);
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
      sqlSession = sqlSessionFactory.openSession();
      tCourseMapper = sqlSession.getMapper(TCourseMapper.class);
      tCourseMapper.createTable();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @After
  public void after() {
    tCourseMapper.dropTable();
    sqlSession.close();
  }

  @Test
  public void testInsert() {
    TCourse tCourse1 = new TCourse("addenda1", "zhanjinhao");
    TCourse tCourse2 = new TCourse(null, "zhanjinhao");
    tCourseMapper.testInsert(tCourse1);
    tCourseMapper.testInsert(tCourse2);

    Assert.assertNull(tCourseMapper.queryByCourseId("addenda1"));
    Assert.assertNotNull(tCourseMapper.queryByCourseId(tCourse1.getCourseId()));
    Assert.assertNotNull(tCourseMapper.queryByCourseId(tCourse2.getCourseId()));
    sqlSession.commit();
  }

  @Test
  public void testInsertBatch() {
    TCourseMapper courseMapper = sqlSession.getMapper(TCourseMapper.class);
    List<TCourse> list = new ArrayList<>();
    list.add(new TCourse(null, "zhanjinhao"));
    list.add(new TCourse(null, "zhanjinhao"));
    courseMapper.testInsertBatch(list);

    Assert.assertNotNull(tCourseMapper.queryByCourseId(list.get(0).getCourseId()));
    Assert.assertNotNull(tCourseMapper.queryByCourseId(list.get(1).getCourseId()));

    sqlSession.commit();
  }

  @Test
  public void testBatchInsert() {
    List<TCourse> list = new ArrayList<>();
    list.add(new TCourse(null, "zhanjinhao"));
    list.add(new TCourse(null, "zhanjinhao"));
    BatchDmlHelper batchDmlHelper = new BatchDmlHelper(sqlSessionFactory);
    batchDmlHelper.setAutoCommit(true);
    batchDmlHelper.batch(TCourseMapper.class, list, TCourseMapper::testInsert);

    Assert.assertNotNull(tCourseMapper.queryByCourseId(list.get(0).getCourseId()));
    Assert.assertNotNull(tCourseMapper.queryByCourseId(list.get(1).getCourseId()));

    sqlSession.commit();
  }

}

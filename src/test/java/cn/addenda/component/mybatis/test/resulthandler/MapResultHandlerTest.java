package cn.addenda.component.mybatis.test.resulthandler;

import cn.addenda.component.mybatis.resulthandler.MapResultHandler;
import cn.addenda.component.mybatis.test.TCourse;
import cn.addenda.component.mybatis.test.TCourseMapper;
import org.apache.ibatis.exceptions.PersistenceException;
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
public class MapResultHandlerTest {

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

      testInsertBatch();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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

  @After
  public void after() {
    tCourseMapper.dropTable();
    sqlSession.close();
  }

  @Test
  public void test1() {
    TCourseMapper courseMapper = sqlSession.getMapper(TCourseMapper.class);
    // 允许重复，先进先出，过滤空
    MapResultHandler<String> resultHelper = new MapResultHandler<>(true, true);
    courseMapper.testStringMapResultHandler(resultHelper);
    System.out.println(resultHelper.getResult());
    Assert.assertEquals(1, resultHelper.getResult().size());
    Assert.assertNotNull(resultHelper.getResult().get("zhanjinhao"));
  }

  @Test
  public void test2() {
    Assert.assertThrows(PersistenceException.class, () -> {
      TCourseMapper courseMapper = sqlSession.getMapper(TCourseMapper.class);
      // 不允许重复，先进先出，过滤空
      MapResultHandler<String> resultHelper = new MapResultHandler<>(true);
      courseMapper.testStringMapResultHandler(resultHelper);
      System.out.println(resultHelper.getResult());
    });
  }

}

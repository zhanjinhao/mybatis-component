package cn.addenda.component.mybatis.test;

import cn.addenda.component.mybatis.idfilling.annotation.IdScopeController;
import cn.addenda.component.mybatis.resulthandler.MapResultHandler;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author addenda
 * @since 2022/2/5 15:42
 */
public interface TCourseMapper {

  void createTable();

  int dropTable();

  @IdScopeController(mode = IdScopeController.FORCE_INJECT)
  void testInsert(@Param("tCourse") TCourse tCourse);

  void testInsertBatch(@Param("tCourses") List<TCourse> tCourseList);

  void testStringMapResultHandler(MapResultHandler<String> resultHelper);

  TCourse queryByCourseId(@Param("courseId") String courseId);
}

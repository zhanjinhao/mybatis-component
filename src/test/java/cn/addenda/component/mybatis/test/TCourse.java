package cn.addenda.component.mybatis.test;

import cn.addenda.component.mybatis.idfilling.annotation.IdScope;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author addenda
 * @since 2022/2/5 15:28
 */
@Setter
@Getter
@ToString
@IdScope(scopeName = "TCourse", idFieldName = "courseId")
public class TCourse {

  private String courseId;

  private String courseName;

  public TCourse() {
  }

  public TCourse(String courseId, String courseName) {
    this.courseId = courseId;
    this.courseName = courseName;
  }

}

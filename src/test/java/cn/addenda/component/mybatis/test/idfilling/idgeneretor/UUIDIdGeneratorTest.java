package cn.addenda.component.mybatis.test.idfilling.idgeneretor;

import cn.addenda.component.mybatis.idfilling.idgenerator.UUIDIdGenerator;

/**
 * @author addenda
 * @since 2022/2/5 15:34
 */
public class UUIDIdGeneratorTest {

  public static void main(String[] args) {
    UUIDIdGenerator idGenerator = new UUIDIdGenerator();
    System.out.println(idGenerator.nextId("tCourse"));
  }

}

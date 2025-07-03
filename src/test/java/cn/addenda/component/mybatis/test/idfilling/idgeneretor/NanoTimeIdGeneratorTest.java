package cn.addenda.component.mybatis.test.idfilling.idgeneretor;

import cn.addenda.component.mybatis.idfilling.idgenerator.IdGenerator;
import cn.addenda.component.mybatis.idfilling.idgenerator.NanoTimeIdGenerator;

/**
 * @author addenda
 * @since 2022/2/5 15:34
 */
public class NanoTimeIdGeneratorTest {

  public static void main(String[] args) {
    IdGenerator idGenerator = new NanoTimeIdGenerator();
    System.out.println(idGenerator.nextId("tCourse"));
  }

}

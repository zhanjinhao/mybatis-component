package cn.addenda.component.mybatis.idfilling;

import cn.addenda.component.mybatis.MybatisException;

/**
 * @author addenda
 * @since 2022/2/5 22:45
 */
public class IdFillingException extends MybatisException {

  public IdFillingException() {
  }

  public IdFillingException(String message) {
    super(message);
  }

  public IdFillingException(String message, Throwable cause) {
    super(message, cause);
  }

  public IdFillingException(Throwable cause) {
    super(cause);
  }

  public IdFillingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  @Override
  public String moduleName() {
    return "mybatis";
  }

  @Override
  public String componentName() {
    return "idgenerator";
  }

}

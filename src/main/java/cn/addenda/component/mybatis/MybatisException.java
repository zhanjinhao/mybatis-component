package cn.addenda.component.mybatis;

import cn.addenda.component.base.BaseException;

/**
 * @author addenda
 * @since 2022/2/5 22:45
 */
public class MybatisException extends BaseException {

  public MybatisException() {
  }

  public MybatisException(String message) {
    super(message);
  }

  public MybatisException(String message, Throwable cause) {
    super(message, cause);
  }

  public MybatisException(Throwable cause) {
    super(cause);
  }

  public MybatisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  @Override
  public String moduleName() {
    return "mybatis";
  }

  @Override
  public String componentName() {
    return "mybatis";
  }

}

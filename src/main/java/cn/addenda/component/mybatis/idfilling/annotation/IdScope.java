package cn.addenda.component.mybatis.idfilling.annotation;

import java.lang.annotation.*;

/**
 * @author addenda
 * @since 2022/2/3 20:34
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IdScope {

  String scopeName();

  String idFieldName() default "id";

}

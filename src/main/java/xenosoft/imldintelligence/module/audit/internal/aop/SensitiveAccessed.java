package xenosoft.imldintelligence.module.audit.internal.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 敏感访问注解，用于声明需要记录敏感数据访问日志的方法。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveAccessed {
    String sensitiveType();

    String resourceType();

    String resourceIdExpression() default "";

    String reasonExpression() default "";
}

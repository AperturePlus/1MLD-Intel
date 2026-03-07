package xenosoft.imldintelligence.module.audit.internal.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 审计操作注解，用于声明需要记录审计日志的业务方法。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuditedOperation {
    String action();

    String resourceType();

    String resourceIdExpression() default "";

    boolean successOnly() default true;
}

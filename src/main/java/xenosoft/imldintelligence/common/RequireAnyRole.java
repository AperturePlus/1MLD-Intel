package xenosoft.imldintelligence.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 任一角色要求注解，用于声明调用方至少具备一个指定角色。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RequireAnyRole {
    /**
     * 声明允许访问该方法的角色编码集合。
     *
     * @return 角色编码数组
     */
    String[] value();

    /**
     * 声明角色校验失败时使用的默认提示语。
     *
     * @return 校验失败提示语
     */
    String message() default "Required role is missing";
}

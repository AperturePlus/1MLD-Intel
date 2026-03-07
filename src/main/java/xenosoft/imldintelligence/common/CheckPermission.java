package xenosoft.imldintelligence.common;

import java.lang.annotation.*;

/**
 * 权限校验注解，用于在方法级声明资源与动作授权要求。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CheckPermission
{
    /**
     * 声明待校验的资源类型或资源标识。
     *
     * @return 资源类型或资源标识
     */
    String resource();

    /**
     * 声明待执行的动作编码。
     *
     * @return 动作编码
     */
    String action();

    /**
     * 声明权限校验失败时使用的默认提示语。
     *
     * @return 校验失败提示语
     */
    String message() default "Permission denied";

}

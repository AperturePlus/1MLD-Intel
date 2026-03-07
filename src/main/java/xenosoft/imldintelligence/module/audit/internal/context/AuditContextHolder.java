package xenosoft.imldintelligence.module.audit.internal.context;

/**
 * 审计上下文持有器，用于在线程范围内存取审计上下文。
 */
public final class AuditContextHolder {
    private static final ThreadLocal<AuditContext> CONTEXT = new ThreadLocal<>();

    private AuditContextHolder() {
    }

    public static void set(AuditContext context) {
        CONTEXT.set(context);
    }

    public static AuditContext get() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}

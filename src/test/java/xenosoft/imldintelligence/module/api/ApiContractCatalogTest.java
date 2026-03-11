package xenosoft.imldintelligence.module.api;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import xenosoft.imldintelligence.module.audit.internal.api.AuditQueryApi;
import xenosoft.imldintelligence.module.audit.internal.api.dto.AuditQueryDtos;
import xenosoft.imldintelligence.module.careplan.internal.api.CarePlanApi;
import xenosoft.imldintelligence.module.careplan.internal.api.dto.CarePlanApiDtos;
import xenosoft.imldintelligence.module.clinical.internal.api.ClinicalApi;
import xenosoft.imldintelligence.module.clinical.internal.api.dto.ClinicalApiDtos;
import xenosoft.imldintelligence.module.diagnoses.internal.api.DiagnosesApi;
import xenosoft.imldintelligence.module.diagnoses.internal.api.dto.DiagnosesApiDtos;
import xenosoft.imldintelligence.module.identity.internal.api.IdentityApi;
import xenosoft.imldintelligence.module.identity.internal.api.dto.IdentityApiDtos;
import xenosoft.imldintelligence.module.integration.internal.api.IntegrationApi;
import xenosoft.imldintelligence.module.integration.internal.api.dto.IntegrationApiDtos;
import xenosoft.imldintelligence.module.license.internal.api.LicenseApi;
import xenosoft.imldintelligence.module.license.internal.api.dto.LicenseApiDtos;
import xenosoft.imldintelligence.module.notify.internal.api.NotifyApi;
import xenosoft.imldintelligence.module.notify.internal.api.dto.NotifyApiDtos;
import xenosoft.imldintelligence.module.payment.internal.api.PaymentApi;
import xenosoft.imldintelligence.module.payment.internal.api.dto.PaymentApiDtos;
import xenosoft.imldintelligence.module.report.internal.api.ReportApi;
import xenosoft.imldintelligence.module.report.internal.api.dto.ReportApiDtos;
import xenosoft.imldintelligence.module.screening.internal.api.ScreeningApi;
import xenosoft.imldintelligence.module.screening.internal.api.dto.ScreeningApiDtos;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 契约设计冒烟测试。
 *
 * <p>当前测试不验证业务行为，只确保所有模块都显式声明了 HTTP 边界与 DTO 分类目录。</p>
 */
class ApiContractCatalogTest {

    @Test
    void everyModuleApiShouldExposeRequestMapping() {
        assertThat(AuditQueryApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(CarePlanApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(ClinicalApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(DiagnosesApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(IdentityApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(IntegrationApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(LicenseApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(NotifyApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(PaymentApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(ReportApi.class.getAnnotation(RequestMapping.class)).isNotNull();
        assertThat(ScreeningApi.class.getAnnotation(RequestMapping.class)).isNotNull();
    }

    @Test
    void everyModuleDtoCatalogShouldExposeExpectedCategories() {
        assertThat(nestedClassNames(AuditQueryDtos.class)).contains("Query");
        assertThat(nestedClassNames(CarePlanApiDtos.class)).contains("Query", "Request", "Response", "Shared");
        assertThat(nestedClassNames(ClinicalApiDtos.class)).contains("Query", "Request", "Response", "Shared");
        assertThat(nestedClassNames(DiagnosesApiDtos.class)).contains("Query", "Request", "Response", "Shared");
        assertThat(nestedClassNames(IdentityApiDtos.class)).contains("Query", "Request", "Response", "Shared");
        assertThat(nestedClassNames(IntegrationApiDtos.class)).contains("Query", "Request", "Response", "Shared");
        assertThat(nestedClassNames(LicenseApiDtos.class)).contains("Query", "Request", "Response");
        assertThat(nestedClassNames(NotifyApiDtos.class)).contains("Query", "Request", "Response", "Shared");
        assertThat(nestedClassNames(PaymentApiDtos.class)).contains("Query", "Request", "Response", "Shared");
        assertThat(nestedClassNames(ReportApiDtos.class)).contains("Query", "Request", "Response", "Shared");
        assertThat(nestedClassNames(ScreeningApiDtos.class)).contains("Query", "Request", "Response", "Shared");
    }

    private Set<String> nestedClassNames(Class<?> type) {
        return Arrays.stream(type.getDeclaredClasses())
                .map(Class::getSimpleName)
                .collect(Collectors.toSet());
    }
}

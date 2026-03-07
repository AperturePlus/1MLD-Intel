package xenosoft.imldintelligence.module.screening.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.screening.internal.repository.QuestionnaireRepository;
import xenosoft.imldintelligence.module.screening.internal.repository.mybatis.QuestionnaireMapper;
import xenosoft.imldintelligence.module.screening.internal.model.Questionnaire;

import java.util.List;
import java.util.Optional;

/**
 * 问卷仓储实现类，基于 MyBatis Mapper 完成问卷的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class QuestionnaireRepositoryImpl implements QuestionnaireRepository {
    private final QuestionnaireMapper questionnaireMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Questionnaire> findById(Long tenantId, Long id) {
        return Optional.ofNullable(questionnaireMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Questionnaire> findByQuestionnaireCodeAndVersionNo(Long tenantId, String questionnaireCode, Integer versionNo) {
        return Optional.ofNullable(questionnaireMapper.findByQuestionnaireCodeAndVersionNo(tenantId, questionnaireCode, versionNo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Questionnaire> listByTenantId(Long tenantId) {
        return questionnaireMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Questionnaire save(Questionnaire questionnaire) {
        questionnaireMapper.insert(questionnaire);
        return questionnaire;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Questionnaire update(Questionnaire questionnaire) {
        questionnaireMapper.update(questionnaire);
        return questionnaire;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return questionnaireMapper.deleteById(tenantId, id) > 0;
    }
}

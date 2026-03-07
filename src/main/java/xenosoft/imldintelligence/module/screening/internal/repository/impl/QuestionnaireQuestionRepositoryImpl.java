package xenosoft.imldintelligence.module.screening.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.screening.internal.repository.QuestionnaireQuestionRepository;
import xenosoft.imldintelligence.module.screening.internal.repository.mybatis.QuestionnaireQuestionMapper;
import xenosoft.imldintelligence.module.screening.internal.model.QuestionnaireQuestion;

import java.util.List;
import java.util.Optional;

/**
 * 问卷题目仓储实现类，基于 MyBatis Mapper 完成问卷题目的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class QuestionnaireQuestionRepositoryImpl implements QuestionnaireQuestionRepository {
    private final QuestionnaireQuestionMapper questionnaireQuestionMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<QuestionnaireQuestion> findById(Long tenantId, Long id) {
        return Optional.ofNullable(questionnaireQuestionMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<QuestionnaireQuestion> findByQuestionnaireIdAndQuestionNo(Long tenantId, Long questionnaireId, String questionNo) {
        return Optional.ofNullable(questionnaireQuestionMapper.findByQuestionnaireIdAndQuestionNo(tenantId, questionnaireId, questionNo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QuestionnaireQuestion> listByTenantId(Long tenantId) {
        return questionnaireQuestionMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QuestionnaireQuestion> listByQuestionnaireId(Long tenantId, Long questionnaireId) {
        return questionnaireQuestionMapper.listByQuestionnaireId(tenantId, questionnaireId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestionnaireQuestion save(QuestionnaireQuestion questionnaireQuestion) {
        questionnaireQuestionMapper.insert(questionnaireQuestion);
        return questionnaireQuestion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestionnaireQuestion update(QuestionnaireQuestion questionnaireQuestion) {
        questionnaireQuestionMapper.update(questionnaireQuestion);
        return questionnaireQuestion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return questionnaireQuestionMapper.deleteById(tenantId, id) > 0;
    }
}

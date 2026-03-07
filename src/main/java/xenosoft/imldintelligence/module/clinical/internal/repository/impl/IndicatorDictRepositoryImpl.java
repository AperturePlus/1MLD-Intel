package xenosoft.imldintelligence.module.clinical.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.clinical.internal.model.IndicatorDict;
import xenosoft.imldintelligence.module.clinical.internal.repository.IndicatorDictRepository;
import xenosoft.imldintelligence.module.clinical.internal.repository.mybatis.IndicatorDictMapper;

import java.util.List;
import java.util.Optional;

/**
 * 指标字典仓储实现类，基于 MyBatis Mapper 完成指标字典的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class IndicatorDictRepositoryImpl implements IndicatorDictRepository {
    private final IndicatorDictMapper indicatorDictMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IndicatorDict> findByCode(String code) {
        return Optional.ofNullable(indicatorDictMapper.findByCode(code));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IndicatorDict> listAll() {
        return indicatorDictMapper.listAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IndicatorDict> listByStatus(String status) {
        return indicatorDictMapper.listByStatus(status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorDict save(IndicatorDict indicatorDict) {
        indicatorDictMapper.insert(indicatorDict);
        return indicatorDict;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IndicatorDict update(IndicatorDict indicatorDict) {
        indicatorDictMapper.update(indicatorDict);
        return indicatorDict;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteByCode(String code) {
        return indicatorDictMapper.deleteByCode(code) > 0;
    }
}

package xenosoft.imldintelligence.module.careplan.internal.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xenosoft.imldintelligence.module.careplan.internal.repository.CarePlanRepository;
import xenosoft.imldintelligence.module.careplan.internal.repository.mybatis.CarePlanMapper;
import xenosoft.imldintelligence.module.careplan.internal.model.CarePlan;

import java.util.List;
import java.util.Optional;

/**
 * 护理计划仓储实现类，基于 MyBatis Mapper 完成护理计划的数据持久化。
 */
@Repository
@RequiredArgsConstructor
public class CarePlanRepositoryImpl implements CarePlanRepository {
    private final CarePlanMapper carePlanMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<CarePlan> findById(Long tenantId, Long id) {
        return Optional.ofNullable(carePlanMapper.findById(tenantId, id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CarePlan> listByTenantId(Long tenantId) {
        return carePlanMapper.listByTenantId(tenantId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CarePlan> listByPatientId(Long tenantId, Long patientId) {
        return carePlanMapper.listByPatientId(tenantId, patientId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarePlan save(CarePlan carePlan) {
        carePlanMapper.insert(carePlan);
        return carePlan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarePlan update(CarePlan carePlan) {
        carePlanMapper.update(carePlan);
        return carePlan;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteById(Long tenantId, Long id) {
        return carePlanMapper.deleteById(tenantId, id) > 0;
    }
}

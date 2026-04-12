package xenosoft.imldintelligence.module.identity.internal.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xenosoft.imldintelligence.module.identity.internal.model.EmailVerificationCode;

@Mapper
public interface EmailVerificationCodeMapper extends BaseMapper<EmailVerificationCode> {
}

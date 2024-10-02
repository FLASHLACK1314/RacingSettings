package com.flashlack.homeofesportsracingsimulatorsettings.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.EmailCodeDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 邮箱验证码Mapper
 *
 * @author FLASHLACK
 */
@Mapper
public interface EmailCodeMapper extends BaseMapper<EmailCodeDO> {
}

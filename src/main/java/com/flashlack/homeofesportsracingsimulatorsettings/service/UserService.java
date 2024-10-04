package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UserInformationVO;

/**
 * 用户服务接口
 * @author FLASHLACK
 */
public interface UserService {
   UserInformationVO getUserInformation(
            String userUuid);
}

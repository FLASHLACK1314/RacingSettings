package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.UserDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.UserDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UserInformationVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @author FLASHLACK
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserLogic implements UserService {
    private final UserDAO userDAO;

    @Override
    public UserInformationVO getUserInformation(String userUuid) {
        UserDO userDO = userDAO.lambdaQuery().eq(UserDO::getUserUuid, userUuid).one();
        UserInformationVO userInformationVO = new UserInformationVO();
        userInformationVO.setUserEmail(userDO.getUserEmail())
                .setNickName(userDO.getNickName());
        return userInformationVO;
    }
}

package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.xlf.utility.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 赛车设置相关控制器
 * @author FLASHLACK
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Controller("v1/setups")
@CrossOrigin(origins = "*")
public class SetupsController {
    /**
     * 添加赛车设置
     * @return 是否添加成功
     */
    @PostMapping(value = "/addSetups", name = "添加赛车设置")
    public ResponseEntity<BaseResponse<Void>> addSetups(
    ) {
        return null;
    }
}

package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 报名记录数据对象
 *
 * 预留给后续报名、审核流程使用
 */
@TableName("settings_match_registration")
@Data
@Accessors(chain = true)
public class SettingsMatchRegistrationDO {
    @TableId
    String registrationUuid;
    String matchUuid;
    String userUuid;
    String registrationStatus;
    Timestamp appliedAt;
    Timestamp reviewedAt;
    String reviewedBy;
    String reviewComment;
}

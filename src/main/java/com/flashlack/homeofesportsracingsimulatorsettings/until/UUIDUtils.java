package com.flashlack.homeofesportsracingsimulatorsettings.until;

import java.util.UUID;

/**
 * UUID工具类
 * @author FLASHLACK
 */
public class UUIDUtils {
    /**
     * 生成UUID
     * @return UUID
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
    /**
     * 生成无横杠UUID
     * @return 无横杠UUID
     */
    public static String generateUuidWithoutHyphens() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

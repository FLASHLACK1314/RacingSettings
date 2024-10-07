package com.flashlack.homeofesportsracingsimulatorsettings.until;

import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * UUID工具类
 *
 * @author FLASHLACK
 */
@Repository
@SuppressWarnings("unused")
@Slf4j
@RequiredArgsConstructor
public class UUIDUtils {
    // 使用 Set 来存储已占用的 UUID
    private static final Set<String> OCCUPIED_UUIDS = new HashSet<>();

    /**
     * 生成 UUID，并检查是否已被占用
     *
     * @return 唯一的 UUID
     */
    public static String generateUuid() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString();
        }
        // 检查 UUID 是否已被占用
        while (OCCUPIED_UUIDS.contains(uuid));
        return uuid;
    }

    /**
     * 生成无横杠的 UUID，并检查是否已被占用
     *
     * @return 无横杠的唯一 UUID
     */
    public static String generateUuidWithoutHyphens() {
        String uuid;
        do {
            uuid = UUID.randomUUID().toString().replace("-", "");
        }
        // 检查 UUID 是否已被占用
        while (OCCUPIED_UUIDS.contains(uuid));
        return uuid;
    }

    /**
     * 手动添加一个已知的占用 UUID 到集合中
     *
     * @param uuid 已占用的 UUID
     */
    public static void addOccupiedUuid(String uuid) {
        OCCUPIED_UUIDS.add(uuid);
    }

    /**
     * 批量添加已知的占用 UUID 到集合中
     *
     * @param uuids 已占用的 UUID 集合
     */
    public static void addOccupiedUuids(Set<String> uuids) {
        OCCUPIED_UUIDS.addAll(uuids);
    }

    /**
     * 检查 UUID 是否已被占用
     *
     * @param uuid UUID 字符串
     * @return 是否已被占用
     */
    public static boolean isOccupied(String uuid) {
        return OCCUPIED_UUIDS.contains(uuid);
    }

    /**
     * 获取所有已占用的 UUID 集合
     *
     * @return 占用的 UUID 集合
     */
    public static Set<String> getOccupiedUuids() {
        return new HashSet<>(OCCUPIED_UUIDS);
    }

    /**
     * 根据请求头获取用户 UUID
     * @param request 请求头
     * @return 用户UUID
     */
    public static String getUuidByRequest(HttpServletRequest request) {
        //获取用户令牌
        String token = request.getHeader("Authorization");
        log.info("Token :{}", token);
        if (token == null) {
            throw new BusinessException("无效的令牌", ErrorCode.HEADER_ERROR);
        }
        JwtUtil jwtUtil = new JwtUtil();
        //解析Token
        String userUuid = jwtUtil.getUserUuidFromToken(token);
        if (userUuid == null) {
            throw new BusinessException("无效的令牌", ErrorCode.HEADER_ERROR);
        }
        return userUuid;
    }
}

package com.smartbird.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;

import java.util.Objects;

/**
 * 分布式ID工具类
 * 底层为hutool雪花算法实现
 *
 * @author xiaoyaoke
 */
public class SnowFlakeUtil {

    /**
     * 获取分布式ID 不重复
     *
     * @return
     */
    public static String getDistributedID() {
        return getDistributedID(null, null);
    }

    /**
     * 获取分布式ID 不重复
     *
     * @return
     */
    public static String getDistributedID(Long workerId) {
        return getDistributedID(workerId, null);
    }

    /**
     * 获取分布式ID 不重复
     *
     * @return
     */
    public static String getDistributedID(Long workerId, Long datacenterId) {
        if (Objects.isNull(workerId) || workerId < 0) {
            workerId = RandomUtil.randomLong(1, 30);
        }
        if (Objects.isNull(datacenterId) || datacenterId < 0) {
            datacenterId = RandomUtil.randomLong(1, 30);
        }
        Snowflake snowFlake = new Snowflake(workerId, datacenterId);
        return snowFlake.nextIdStr();
    }
}

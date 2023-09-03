package org.itxyq.reggie.common;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/2
 * @description 基于ThreadLocal封装工具类 用户保存和获取当前登录用户id
 **/
public class BaseContext {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * @return Long 登录id
     * @description 获取id
     **/
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /**
     * @param id 登录id
     * @description 设置id
     **/
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
}

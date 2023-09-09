package org.itxyq.reggie.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xyq 13127
 * @version 1.0.0
 * @date 2023/9/2
 * @description 通用返回结果, 服务端响应的数据最终都会封装成此对象
 **/
@Data
@ApiModel("返回结果")
public class R<T> implements Serializable {
    private static final Long SERIALIZABLE_UID = 1L;

    @ApiModelProperty(name = "编码", notes = "编码：1成功，0和其它数字为失败")
    private Integer code;

    @ApiModelProperty(name = "错误信息", notes = "错误信息")
    private String msg;

    @ApiModelProperty(name = "数据", notes = "数据")
    private T data;

    @ApiModelProperty(name = "动态数据", notes = "动态数据")
    private Map map = new HashMap();

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 200;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = 500;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}

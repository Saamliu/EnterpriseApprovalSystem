package org.example.response;

import lombok.Data;

@Data
public class Result<T> {
    private String code;
    private String message;
    private T data;

    /**
     * 返回默认的成功
     * @return
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 返回携带数据的成功
     * @param data
     * @return
     */
    public static <T> Result<T> success(T data) {
        Result result = new Result();
        result.setCode(ResultEnum.OK.getCode());
        result.setMessage(ResultEnum.OK.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 返回默认错误
     * @return
     */
    public static Result error() {
        return error(ResultEnum.ERROR.getCode(), ResultEnum.INSERT_ERROR.getMessage());
    }

    /**
     * 用指定的枚举对象返回错误
     * @param resultEnum
     * @return
     */
    public static Result error(ResultEnum resultEnum) {
        return error(resultEnum.getCode(), resultEnum.getMessage());
    }

    /**
     * 自定义返回错误
     * @param code
     * @param message
     * @return
     */
    public static Result error(String code, String message) {
        Result result = new Result();
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }

}

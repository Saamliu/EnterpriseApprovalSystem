package org.example.exception;


import org.example.response.Result;
import org.example.response.ResultEnum;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 捕捉自定义的业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Object handleCustomException(CustomException e) {
        // 异常追踪打印出来给程序员看
        e.printStackTrace();
        // 从异常对象e里面取出异常枚举
        ResultEnum resultEnum = e.getResultEnum();
        // 返回错误给前端
        return Result.error(resultEnum);
    }

    @ExceptionHandler(BindException.class)
    public Object handleBindException(BindException e) {
        // 异常追踪打印出来给程序员看
        e.printStackTrace();
        // 获取异常对象中携带的校验结果对象 (里面有校验失败的字段和消息)
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", ResultEnum.DATA_VALID_ERROR.getCode());
        resultMap.put("message", errorMap);
        // 返回错误给前端
        return resultMap;
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Object handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        return Result.error(ResultEnum.FILE_SIZE_TOO_BIG);
    }

    /**
     * 捕捉除CustomException异常之外的其他所有异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        // 异常追踪打印出来给程序员看
        e.printStackTrace();
        // 返回默认的错误给前端
        return Result.error();
    }
}

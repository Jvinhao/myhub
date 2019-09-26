package org.lf.community.dto;

import lombok.Data;
import org.lf.community.exception.CustomizeCode;
import org.lf.community.exception.CustomizeException;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static  ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeCode errCode) {
        return errorOf(errCode.getCode(),errCode.getMessage());
    }

    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeException e) {
        return ResultDTO.errorOf(e.getCode(),e.getMessage());
    }

    public static <T> ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}

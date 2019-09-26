package org.lf.community.advice;


import com.alibaba.fastjson.JSON;
import org.lf.community.dto.ResultDTO;
import org.lf.community.exception.CustomizeCode;
import org.lf.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response) {
       /* HttpStatus status = getStatus(request);*/
        if("application/json".equals(request.getContentType())) {
            ResultDTO resultDTO = null;
            if(e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else {
                resultDTO = ResultDTO.errorOf(CustomizeCode.SYSTEM_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter out = response.getWriter();
                out.print(JSON.toJSONString(resultDTO));
                out.close();
            } catch (IOException e1) {


            }
            return null;
        }else {
            if(e instanceof CustomizeException) {
                model.addAttribute("message",e.getMessage());
            }else {
                model.addAttribute("message",CustomizeCode.SYSTEM_ERROR.getMessage());
            }
        }


        return new ModelAndView("error");
    }

    /*public HttpStatus getStatus(HttpServletRequest request) {
        Integer status = (int)request.getAttribute("javax.servlet.error.status_code");
        if(status == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(status);

    }*/
}

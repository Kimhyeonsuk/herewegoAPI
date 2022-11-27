package com.herewego.herewegoapi.response;

import com.herewego.herewegoapi.exceptions.ErrorCode;
import com.herewego.herewegoapi.exceptions.ForwardException;
import com.herewego.herewegoapi.model.response.ResponseVO;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class ApiResponse {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiResponse.class);

    public static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        ApiResponse.applicationContext = applicationContext;
    }

    public static ResponseVO ok() { return result("RC2000000", "OK"); }

    public static Object ok(String resultCode, String resultMessage) { return result(resultCode, resultMessage); }

    public static ResponseVO error(HttpServletResponse response, Exception e) throws ForwardException {

        int httpCode;
        String resultCode = null;
        String resultMessage = null;

        if (e instanceof ForwardException) {
            resultCode = ((ForwardException) e).getResponseCode();
            resultMessage = ((ForwardException) e).getResponseMessage();
        }
        else if (e.getCause() instanceof ForwardException) {
            resultCode = ((ForwardException) e.getCause()).getResponseCode();
            resultMessage = ((ForwardException) e.getCause()).getResponseMessage();
        }

        LOGGER.info("resultCode = {}", resultCode);
        if (ObjectUtils.isEmpty(resultCode)  || resultCode.length() != 8) {
            throw new ForwardException(ErrorCode.RC400000);
        }

        LOGGER.info("response = {}", response);
        if (!ObjectUtils.isEmpty(response)) {
            httpCode = Integer.parseInt(resultCode.substring(2, 5));
            response.setStatus(httpCode);
            LOGGER.info("httpCode = {}", httpCode);
        }

        return result(resultCode, resultMessage);
    }

    private static ResponseVO result(String resultCode, String resultMessage) {
        return new ResponseVO(resultCode, resultMessage);
    }
}

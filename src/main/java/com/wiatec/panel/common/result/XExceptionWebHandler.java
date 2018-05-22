//package com.wiatec.panel.common.result;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @author patrick
// */
//@ControllerAdvice(basePackages = {"com.wiatec.panel.web"})
//public class XExceptionWebHandler {
//
//    private Logger logger = LoggerFactory.getLogger(XExceptionWebHandler.class);
//
//    @ExceptionHandler(value = Exception.class)
//    public ResultInfo handleException(Exception e){
//        if(e instanceof XException){
//            XException xException = (XException) e;
//            logger.error("= XException= {}", e);
//            return ResultMaster.error(xException.getCode(), xException.getMessage());
//        }else {
//            logger.error("= Server exception= {}", e);
//            return ResultMaster.error(EnumResult.ERROR_INTERNAL_SERVER);
//        }
//    }
//}

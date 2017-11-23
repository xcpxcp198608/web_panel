package com.wiatec.panel.xutils.result;

import java.util.List;

public class ResultMaster {

    public static ResultInfo success () {
        return success(null);
    }

    public static <T> ResultInfo<T> success (T t) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setCode(EnumResult.SUCCESS.getCode());
        resultInfo.setMessage(EnumResult.SUCCESS.getMessage());
        resultInfo.setData(t);
        return resultInfo;
    }

    public static <T> ResultInfo<T> success (List<T> list) {
        ResultInfo<T> resultInfo = new ResultInfo<>();
        resultInfo.setCode(EnumResult.SUCCESS.getCode());
        resultInfo.setMessage(EnumResult.SUCCESS.getMessage());
        resultInfo.setDataList(list);
        return resultInfo;
    }

    public static ResultInfo error (EnumResult enumResult) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(enumResult.getCode());
        resultInfo.setMessage(enumResult.getMessage());
        return resultInfo;
    }

    public static ResultInfo error (int code, String message) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(code);
        resultInfo.setMessage(message);
        return resultInfo;
    }

    public static ResultInfo error (String code, String message) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(Integer.parseInt(code));
        resultInfo.setMessage(message);
        return resultInfo;
    }
}

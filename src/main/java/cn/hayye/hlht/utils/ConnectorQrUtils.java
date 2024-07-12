package cn.hayye.hlht.utils;


import cn.hayye.hlht.vo.ConnectorQrVo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectorQrUtils {

    /**
     * 创建互联互通二维码链接
     * @param vo
     * @return
     */
    public static String create(ConnectorQrVo vo){
        StringBuilder content = new StringBuilder();
        content.append(vo.getProtocol()).append("://")
                .append(vo.getConnectorId()).append(".")
                .append(vo.getOptCode()).append("/")
                .append(vo.getExtend());
        return content.toString();
    }

    /**
     * 解析互联互通二维码
     * @param content
     * @return
     */
    public static ConnectorQrVo analysis(String content){
        if (!check(content)){
            throw new RuntimeException("解析失败，格式不正确！");
        }
        ConnectorQrVo vo = new ConnectorQrVo();
        String [] a = content.split("://");
        vo.setProtocol(a[0]);
        if (a[1].contains("/")){
            vo.setExtend(a[1].substring(a[1].indexOf("/")));
            String [] b = (a[1].substring(0,a[1].indexOf("/"))).split("\\.");
            vo.setConnectorId(b[0]);
            vo.setOptCode(b[1]);
        }else {
            String [] b = a[1].split("\\.");
            vo.setConnectorId(b[0]);
            vo.setOptCode(b[1]);
        }
        return vo;
    }

    /**
     * 检验是否是设备二维码
     * 二维码格式 hlht://充电接口ID.运营商ID/自定义数据
     * @param content
     * @return
     */
    public static Boolean check(String content){
        String regStr = "^(hlht://)[\\w]+\\.{1}[\\w]{9}\\/?";
        Pattern pattern = Pattern.compile(regStr);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()){
            return true;
        }
        return false;
    }
}

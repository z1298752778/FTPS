//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.datasweep.core;

import com.datasweep.core.eventlog.EventLog;
import java.util.Locale;
import java.util.ResourceBundle;

public class VersionInformation {
    public static String BUILD = "Local";
    public static String VERSION = "V1.0";//TODO 修改国际化
    public static String COPYRIGHT = "Copyright © 2024 上海雷昶科技有限公司";//TODO 修改国际化
    public static String COPYRIGHT1 = "Copyright © 2024";//TODO 修改国际化
    public static String COPYRIGHT2 = "上海雷昶科技有限公司";//TODO 修改国际化
    public static String RESERVEDRIGHT = "版权所有.";//TODO 修改国际化
    public static String TRADEMARK = "雷昶RA制药生产软件和雷昶RA制药生产软件标志是上海雷昶科技有限公司的商标。所有其他品牌或产品名称均为其各自公司的商标或注册商标。请使用您在订单确认时收到的说明，查看下载网站上可用的LC@RA-MPS开源许可协议指南。";//TODO 修改国际化

    public VersionInformation() {
    }

    static {
        try {
            ResourceBundle var0 = ResourceBundle.getBundle("com.datasweep.plantops.client.version.VersionInformation", Locale.getDefault(), VersionInformation.class.getClassLoader());
            //TODO 修改国际化
        } catch (Exception var1) {
            EventLog.logException(var1, VersionInformation.class, "static");
        }

    }
}

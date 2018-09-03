package com.xs.frame.common.code;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2016/3/7.
 */
public  class BusinessSerialValueRange {

    private static Properties properties;

    private static List<String> platformValues = new ArrayList<String>();
    private static List<String> systemNoValues = new ArrayList<String>();
    private static List<String>  TerminalType  = new ArrayList<String>();

    private static String  platform_p2p="1";

    private static String systemNo_web ="1";
    private static String systemNo_manager ="2";
    private static String systemNo_intermediate ="3";
    private static String systemNo_timer ="4";
    private static String systemNo_gateway ="5";

    private static String terminal_pc = "1";
    private static String terminal_ios ="2";
    private static String terminal_android ="3";
    private static String terminal_wechat ="4";
    private static String terminal_wap ="5";

    private static String  platformValuesKey ="platformValues";
    private static String  systemNoValuesKey ="systemNoValues";
    private static String  TerminalTypeKey ="TerminalType";


    public static String  formatStr = "yyMMdd";
    public static int	countForPlatform =1;
    public static int	countForBusinessNo =5;
    public static int	countForSystemNo =1;
    public static int	countForServiceNo =2;
    public static int	countForTerminalType =2;
    public static int	countForSerialNumber =8;
    public static int	countForDetailSerialNumber =5;

    private static String  formatStrKey ="formatStr";
    private static String  countForPlatformKey ="countForPlatform";
    private static String  countForBusinessNoKey ="countForBusinessNo";
    private static String  countForServiceNoKey ="countForServiceNo";
    private static String  countForSystemNoKey ="countForSystemNo";
    private static String  countForTerminalTypeKey ="countForTerminalType";
    private static String  countForSerialNumberKey ="countForSerialNumber";
    private static String  countForDetailSerialNumberKey ="countForDetailSerialNumber";


    static{
       properties = new Properties();
       try {
           properties.load(BusinessSerialValueRange.class.getClassLoader().getResourceAsStream(
                   "serial.properties"));
       } catch (IOException e) {
           e.printStackTrace();
           platformValues.add(platform_p2p);

           systemNoValues.add(systemNo_web);
           systemNoValues.add(systemNo_manager);
           systemNoValues.add(systemNo_intermediate);
           systemNoValues.add(systemNo_timer);
           systemNoValues.add(systemNo_gateway);

           TerminalType.add(terminal_pc);
           TerminalType.add(terminal_ios);
           TerminalType.add(terminal_android);
           TerminalType.add(terminal_wechat);
           TerminalType.add(terminal_wap);
       }

       if(properties ==null || properties.getProperty(platformValuesKey)==null || properties.getProperty(platformValuesKey).isEmpty()
               || properties.getProperty(systemNoValuesKey)==null || properties.getProperty(systemNoValuesKey).isEmpty()
               || properties.getProperty(TerminalTypeKey)==null || properties.getProperty(TerminalTypeKey).isEmpty())
       {
           platformValues.add(platform_p2p);

           systemNoValues.add(systemNo_web);
           systemNoValues.add(systemNo_manager);
           systemNoValues.add(systemNo_intermediate);
           systemNoValues.add(systemNo_timer);
           systemNoValues.add(systemNo_gateway);

           TerminalType.add(terminal_pc);
           TerminalType.add(terminal_ios);
           TerminalType.add(terminal_android);
           TerminalType.add(terminal_wechat);
           TerminalType.add(terminal_wap);
       }else{
           String[] strPlatformValues = properties.getProperty(platformValuesKey).split(",");
           String[] strSystemNoValues = properties.getProperty(systemNoValuesKey).split(",");
           String[] strTerminalType = properties.getProperty(TerminalTypeKey).split(",");

          for(String strOnePlatformValue : strPlatformValues){
              platformValues.add(strOnePlatformValue);
          }
           for(String strOneSystemNoValue : strSystemNoValues){
               systemNoValues.add(strOneSystemNoValue);
           }

           for(String strOneTerminalType : strTerminalType){
               TerminalType.add(strOneTerminalType);
           }

       }

        if(properties !=null){
            if(!(properties.getProperty(formatStrKey)==null || properties.getProperty(formatStrKey).isEmpty())){
                formatStr = properties.getProperty(formatStrKey);
            }

            if(!(properties.getProperty(countForPlatformKey)==null || properties.getProperty(countForPlatformKey).isEmpty())){
                countForPlatform =  Integer.valueOf(properties.getProperty(countForPlatformKey));
            }

            if(!(properties.getProperty(countForBusinessNoKey)==null || properties.getProperty(countForBusinessNoKey).isEmpty())){
                countForBusinessNo =  Integer.valueOf(properties.getProperty(countForBusinessNoKey));
            }

            if(!(properties.getProperty(countForSystemNoKey)==null || properties.getProperty(countForSystemNoKey).isEmpty())){
                countForSystemNo =  Integer.valueOf(properties.getProperty(countForSystemNoKey));
            }

            if(!(properties.getProperty(countForServiceNoKey)==null || properties.getProperty(countForServiceNoKey).isEmpty())){
                countForServiceNo =  Integer.valueOf(properties.getProperty(countForServiceNoKey));
            }

            if(!(properties.getProperty(countForTerminalTypeKey)==null || properties.getProperty(countForTerminalTypeKey).isEmpty())){
                countForTerminalType =  Integer.valueOf(properties.getProperty(countForTerminalTypeKey));
            }

            if(!(properties.getProperty(countForSerialNumberKey)==null || properties.getProperty(countForSerialNumberKey).isEmpty())){
                countForSerialNumber =  Integer.valueOf(properties.getProperty(countForSerialNumberKey));
            }

            if(!(properties.getProperty(countForDetailSerialNumberKey)==null || properties.getProperty(countForDetailSerialNumberKey).isEmpty())){
                countForDetailSerialNumber =  Integer.valueOf(properties.getProperty(countForDetailSerialNumberKey));
            }
        }



    }

    public static boolean isPlatformOutOfRange(String platformNo){
        return platformValues.contains(platformNo);
    }

    public static boolean isSystemNoOutOfRange(String systemNo){
        return systemNoValues.contains(systemNo);
    }

    public static boolean isTerminalOutOfRange(String terminalNo){
        return TerminalType.contains(terminalNo);
    }



}

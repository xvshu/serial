package com.xs.frame.iserial;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/4.
 */

public interface ILogicSerialService {


    /**
     * 获取流水号
     * @param businessSerial
     * @return
     */
    public PublicResult<String> getProfessionSerialNumber(BusinessSerial businessSerial) ;


    /**
     * 批量获取流水号
     * @param businessSerial
     * @param number
     * @return
     */
    public PublicResult<ArrayList<String>> getProfessionSerialNumbers(BusinessSerial businessSerial, Integer number) ;


    /**
     * 获取明细流水号
     * @param businessSerial
     * @return
     */
    public PublicResult<String> getDetailProfessionSerialNumber(BusinessSerial businessSerial) ;


    /**
     * 批量获取明细流水号
     * @param businessSerial
     * @param number
     * @return
     */
    public PublicResult<ArrayList<String>> getDetailProfessionSerialNumbers(BusinessSerial businessSerial, Integer number) ;

}

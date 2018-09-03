package com.xs.frame.serial;

import com.alibaba.fastjson.JSON;
import com.xs.frame.common.code.BusinessSerialError;
import com.xs.frame.common.code.BusinessSerialValueRange;
import com.xs.frame.common.sequence.SequenceHelp;
import com.xs.frame.common.utils.DateUtil;
import com.xs.frame.iserial.BusinessSerial;
import com.xs.frame.iserial.ILogicSerialService;
import com.xs.frame.iserial.PublicResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("logicSerialService")
public class LogicSerialServiceImpl implements ILogicSerialService {
	private final static Logger LOGGER= LoggerFactory.getLogger(LogicSerialServiceImpl.class);

	@Autowired
	private SequenceHelp sequenceHelp;

	@Override
	public PublicResult<String> getProfessionSerialNumber(BusinessSerial businessSerial) {
		PublicResult<String> publicResult = new PublicResult<String>();
		publicResult =checkParameters(publicResult,businessSerial,0,false,false);
		try {
			if (publicResult.isSuccess()==false) {
				return publicResult;
			}
			businessSerial.setBusinessNo(initBusinessNo(businessSerial.getBusinessNo()));
			String now = getNow();
			long professionSerialNumber = sequenceHelp.get(businessSerial.getBusinessNo() +"_"+ now);
			publicResult.setSuccess(true);
			publicResult.setResult(formatStr(false,businessSerial,professionSerialNumber,now) );
		}catch(Exception e){
			publicResult.setErrorCode(e.getMessage());
			publicResult.setErrorMsg(e.getMessage());
			publicResult.setSuccess(false);
		}
		LOGGER.info("<=getProfessionSerialNumber=> input parm:"+ JSON.toJSONString(businessSerial)+" publicResult value:"+JSON.toJSONString(publicResult));
		return publicResult;
	}

	@Override
	public PublicResult<ArrayList<String>> getProfessionSerialNumbers(BusinessSerial businessSerial,Integer number) {
		PublicResult<ArrayList<String>> publicResult = new PublicResult<ArrayList<String>>();
		publicResult =checkParameters(publicResult,businessSerial,number,true,false);
		try {
			if (publicResult.isSuccess() == false) {
				return publicResult;
			}
			businessSerial.setBusinessNo(initBusinessNo(businessSerial.getBusinessNo()));
			String now = getNow();

			List<Long> professionLongSerialNumbers = sequenceHelp.get(businessSerial.getBusinessNo() + "_" + now, number);
			ArrayList<String> professionSerialNumbers = new ArrayList<String>();
			for (Long oneNum : professionLongSerialNumbers) {
				professionSerialNumbers.add(formatStr(false, businessSerial, oneNum, now));
			}

			publicResult.setSuccess(true);
			publicResult.setResult(professionSerialNumbers);
		}catch(Exception e){
			publicResult.setErrorCode(e.getMessage());
			publicResult.setErrorMsg(e.getMessage());
			publicResult.setSuccess(false);
		}
		LOGGER.info("<=getProfessionSerialNumbers=> input parm:"+ JSON.toJSONString(businessSerial)+" publicResult value:"+JSON.toJSONString(publicResult));
		return publicResult;

	}

	@Override
	public PublicResult<String> getDetailProfessionSerialNumber(BusinessSerial businessSerial) {
		PublicResult<String> publicResult = new PublicResult<String>();
		publicResult =checkParameters(publicResult,businessSerial,0,false,true);
		try {
			if (publicResult.isSuccess() == false) {
				return publicResult;
			}
			businessSerial.setBusinessNo(initBusinessNo(businessSerial.getBusinessNo()));
			String now = getNow();
			long professionSerialNumber = sequenceHelp.get(businessSerial.getBusinessNo() + "_" + now + "_" + businessSerial.getSerialNumber());

			publicResult.setSuccess(true);
			publicResult.setResult(formatStr(true, businessSerial, professionSerialNumber, now));
		}catch(Exception e){
			publicResult.setErrorCode(e.getMessage());
			publicResult.setErrorMsg(e.getMessage());
			publicResult.setSuccess(false);
		}
		LOGGER.info("<=getDetailProfessionSerialNumber=> input parm:"+ JSON.toJSONString(businessSerial)+" publicResult value:"+JSON.toJSONString(publicResult));
		return publicResult;
	}

	@Override
	public PublicResult<ArrayList<String>> getDetailProfessionSerialNumbers(BusinessSerial businessSerial,Integer number) {
		PublicResult<ArrayList<String>> publicResult = new PublicResult<ArrayList<String>>();
		publicResult =checkParameters(publicResult,businessSerial,number,true,true);
		try {
			if (publicResult.isSuccess() == false) {
				return publicResult;
			}
			businessSerial.setBusinessNo(initBusinessNo(businessSerial.getBusinessNo()));

			String now = getNow();
			List<Long> professionLongSerialNumbers = sequenceHelp.get(businessSerial.getBusinessNo() + "_" + now + "_" + businessSerial.getSerialNumber(), number);
			ArrayList<String> professionSerialNumbers = new ArrayList<String>();
			for (Long oneNum : professionLongSerialNumbers) {
				professionSerialNumbers.add(formatStr(true, businessSerial, oneNum, now));
			}
			publicResult.setSuccess(true);
			publicResult.setResult(professionSerialNumbers);
		}catch(Exception e){
			publicResult.setErrorCode(e.getMessage());
			publicResult.setErrorMsg(e.getMessage());
			publicResult.setSuccess(false);
		}
		LOGGER.info("<=getDetailProfessionSerialNumbers=> input parm:"+ JSON.toJSONString(businessSerial)+" publicResult value:"+JSON.toJSONString(publicResult));
		return publicResult;
	}

	/**
	 * 验证参数是否为空
	 * @param publicResult
	 * @param businessSerial 传入的参数
	 * @param number 如批量申请流水号，则必须将申请个数传入
	 * @param isNumbers 是否批量申请流水号
	 * @param isdetail 是否为申请明细流水
	 * @return
	 */
	private PublicResult checkParameters(PublicResult publicResult,BusinessSerial businessSerial,Integer number,boolean isNumbers,boolean isdetail){
		publicResult.setSuccess(true);
		if(isNumbers && (number ==null || (number.compareTo(0) <= 0) ) ){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_NUMBER_ISNULL.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_NUMBER_ISNULL.getErrorMsg());
			return publicResult;
		}else if(businessSerial == null ){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_BUSINESSSERIAL_ISNULL.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_BUSINESSSERIAL_ISNULL.getErrorMsg());
		}else if(businessSerial.getPlatform() ==null || businessSerial.getPlatform().trim().isEmpty()){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_PLATFORM_ISNULL.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_PLATFORM_ISNULL.getErrorMsg());
		}else if(businessSerial.getBusinessNo() == null  || businessSerial.getBusinessNo().trim().isEmpty()){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_BUSINESSNO_ISNULL.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_BUSINESSNO_ISNULL.getErrorMsg());
		}else if( businessSerial.getBusinessNo().trim().length()> BusinessSerialValueRange.countForBusinessNo) {
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_BUSINESSNO_SIZE_ERROR.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_BUSINESSNO_SIZE_ERROR.getErrorMsg());
		}else if(businessSerial.getSystemNo() == null || businessSerial.getSystemNo().trim().isEmpty()){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_SYSTEMNO_ISNULL.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_SYSTEMNO_ISNULL.getErrorMsg());
		}else if(businessSerial.getServiceNo() == null || businessSerial.getServiceNo().trim().isEmpty()){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_SERVICENO_ISNULL.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_SERVICENO_ISNULL.getErrorMsg());
		}else if(businessSerial.getTerminalType() ==null || businessSerial.getTerminalType().trim().isEmpty()){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_TERMINALTYPE_ISNULL.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_TERMINALTYPE_ISNULL.getErrorMsg());
		}else if(isdetail && (businessSerial.getSerialNumber() ==null || businessSerial.getSerialNumber().trim().isEmpty()) ){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_SERIALNUMBER_ISNULL.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_SERIALNUMBER_ISNULL.getErrorMsg());
		}


		if(publicResult.isSuccess()){
			publicResult = checkParametersIsOutOfRange(publicResult,businessSerial);
		}
		return publicResult;

	}

	private String initBusinessNo(String oldStr){
		return formatString(oldStr,BusinessSerialValueRange.countForBusinessNo);
	}

	/**
	 * 验证参数值是否超出范围
	 * @param publicResult
	 * @param businessSerial
	 * @return
	 */
	private PublicResult checkParametersIsOutOfRange(PublicResult publicResult,BusinessSerial businessSerial){
		publicResult.setSuccess(true);
		if( !BusinessSerialValueRange.isPlatformOutOfRange(businessSerial.getPlatform()) ) {
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_PLATFORM_OUTOFRANGE.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_PLATFORM_OUTOFRANGE.getErrorMsg());
		}else if( !BusinessSerialValueRange.isSystemNoOutOfRange(businessSerial.getSystemNo()) ){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_SYSTEMNO_OUTOFRANGE.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_SYSTEMNO_OUTOFRANGE.getErrorMsg());
		}else if( !BusinessSerialValueRange.isTerminalOutOfRange(businessSerial.getTerminalType()) ){
			publicResult.setSuccess(false);
			publicResult.setErrorCode(BusinessSerialError.E_BUSINESS_PARAMETER_TERMINALTYPE_OUTOFRANGE.getErrorCode());
			publicResult.setErrorMsg(BusinessSerialError.E_BUSINESS_PARAMETER_TERMINALTYPE_OUTOFRANGE.getErrorMsg());
		}

		return publicResult;

	}


	private String getNow(){
		return DateUtil.format(new Date(),BusinessSerialValueRange.formatStr);
	}

	/**
	 * 格式化并组合流水号
	 * @param isdetail 是否为明细流水号
	 * @param businessSerial 传入参数
	 * @param number 申请到的流水号
	 * @param now 当前时间
	 * @return
	 */
	private String formatStr(boolean isdetail,BusinessSerial businessSerial,long number,String now){

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("JC");
		stringBuffer.append(formatString(businessSerial.getPlatform(), BusinessSerialValueRange.countForPlatform));
		stringBuffer.append(formatString(businessSerial.getBusinessNo(), BusinessSerialValueRange.countForBusinessNo));
		stringBuffer.append(formatString(businessSerial.getSystemNo(), BusinessSerialValueRange.countForSystemNo));

		stringBuffer.append(formatString(businessSerial.getServiceNo(), BusinessSerialValueRange.countForServiceNo));

		stringBuffer.append(formatString(businessSerial.getTerminalType(), BusinessSerialValueRange.countForTerminalType));

		stringBuffer.append(now);

		if(isdetail){
			stringBuffer.append(formatString(businessSerial.getSerialNumber(), BusinessSerialValueRange.countForSerialNumber));
			stringBuffer.append(formatString(number+"", BusinessSerialValueRange.countForDetailSerialNumber));
		}else{
			stringBuffer.append(formatString(number+"", BusinessSerialValueRange.countForSerialNumber));
		}

		return stringBuffer.toString();

	}

	/**
	 * 格式化流水号的各个组成部分为标准长度
	 * 不足将在字符前补0
	 * @param source 需要格式化的字符
	 * @param lengh 格式化后的字符长度
	 * @return
	 */
	private String formatString (String source , int lengh){
		StringBuffer result = new StringBuffer();
		int resultCount = (lengh-source.trim().length());
		if(resultCount>0){
			result.append(String.format("%0"+resultCount+"d", 0));
			result.append(source);
		}else if(resultCount == 0 ){
			result.append(source);
		}else{
			result.append(source.substring(source.length()-lengh,source.length()));
		}
		return result.toString();
	}
}

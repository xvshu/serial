package com.xs.frame;

import com.alibaba.fastjson.JSON;
import com.xs.frame.common.sequence.RedisSequence;
import com.xs.frame.iserial.BusinessSerial;
import com.xs.frame.iserial.ILogicSerialService;
import com.xs.frame.iserial.PublicResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SerialApplicationTests {

	/**
	 * 日志管理
	 */
	private Logger log = LoggerFactory.getLogger(SerialApplicationTests.class);

	@Autowired
	ILogicSerialService logicSerialService;


	@Autowired
	private RedisSequence redisSequence;

	@Test
	public void getRedisSequence_test1 () {
		log.info("==>begin get redis value");
		Long result = redisSequence.getMaxValue("01001_180903");
		System.out.println(String.valueOf(result));
	}



	@Test
	public void getProfessionSerialNumber_test1 () {
		log.info("==>begin getProfessionSerialNumber_test1e");
		Long result = redisSequence.getMaxValue("01001_180903");
		System.out.println("old_redis_value:"+result);
		BusinessSerial businessSerial = new BusinessSerial();
		businessSerial.setBusinessNo("1001");
		businessSerial.setPlatform("1");
		businessSerial.setSystemNo("1");
		businessSerial.setServiceNo("XV");
		businessSerial.setTerminalType("1");
		PublicResult publicResult = logicSerialService.getProfessionSerialNumber(businessSerial);
		if(!publicResult.isSuccess()){
			System.out.println(publicResult.getErrorMsg());
		}else {
			System.out.println(publicResult.getResult());
		}

		result = redisSequence.getMaxValue("01001_180903");
		System.out.println("new_redis_value:"+result);

	}

	@Test
	public void getProfessionSerialNumber_test1_1000 () throws Exception {

		Long now = System.currentTimeMillis();
		for(int i=0;i<100;i++){
			Long result = redisSequence.getMaxValue("01001_180903");
			System.out.println("old_redis_value:"+result);
			BusinessSerial businessSerial = new BusinessSerial();
			businessSerial.setBusinessNo("1001");
			businessSerial.setPlatform("1");
			businessSerial.setSystemNo("1");
			businessSerial.setServiceNo("XV");
			businessSerial.setTerminalType("1");
			PublicResult publicResult = logicSerialService.getProfessionSerialNumbers(businessSerial,1000);
			if(!publicResult.isSuccess()){
				System.out.println(publicResult.getErrorMsg());
			}else {
				System.out.println(publicResult.getResult());
			}

			result = redisSequence.getMaxValue("01001_180903");
			System.out.println("new_redis_value:"+result);
		}

		Long then = System.currentTimeMillis();
		System.out.println("cost:"+(then-now));
	}



	@Test
	public void getJsonObj(){
		BusinessSerial businessSerial = new BusinessSerial();
		businessSerial.setBusinessNo("1001");
		businessSerial.setPlatform("1");
		businessSerial.setSystemNo("1");
		businessSerial.setServiceNo("XV");
		businessSerial.setTerminalType("1");
		System.out.print(JSON.toJSONString(businessSerial));
	}

	@Test
	public void getSerials(){
		long time = System.currentTimeMillis();
		for(int i=1;i<=10 ;i++){
			getProfessionSerialNumbers_test2();
		}
		System.out.println("spen time:["+(System.currentTimeMillis()-time)+"]");
	}


	private void getSerial(){
		BusinessSerial businessSerial = new BusinessSerial();
		businessSerial.setBusinessNo("1001");
		businessSerial.setPlatform("1");
		businessSerial.setSystemNo("1");
		businessSerial.setServiceNo("XV");
		businessSerial.setTerminalType("1");
		PublicResult<String> publicResult = logicSerialService.getProfessionSerialNumber(businessSerial);
	}

	@Test
	public void getProfessionSerialNumbers_test2 () {
		long time = System.currentTimeMillis();
		BusinessSerial businessSerial = new BusinessSerial();
		businessSerial.setBusinessNo("1001");
		businessSerial.setPlatform("1");
		businessSerial.setSystemNo("1");
		businessSerial.setServiceNo("XV");
		businessSerial.setTerminalType("1");
		PublicResult<ArrayList<String>> publicResult = logicSerialService.getProfessionSerialNumbers(businessSerial,1000);
		if(!publicResult.isSuccess()){
			System.out.println(publicResult.getErrorMsg());
		}else {
			System.out.println(publicResult.getResult());
		}

	}


	@Test
	public void getDetailProfessionSerialNumber_test3 () {
		long time = System.currentTimeMillis();
		BusinessSerial businessSerial = new BusinessSerial();
		businessSerial.setBusinessNo("a001a");
		businessSerial.setPlatform("1");
		businessSerial.setSystemNo("1");
		businessSerial.setServiceNo("XV");
		businessSerial.setTerminalType("1");
		businessSerial.setSerialNumber("1111111a222222a");
		PublicResult<String> publicResult = logicSerialService.getDetailProfessionSerialNumber(businessSerial);
		if(!publicResult.isSuccess()){
			System.out.println(publicResult.getErrorMsg());
		}else {
			System.out.println(publicResult.getResult());
		}
		System.out.println("spen time:["+(System.currentTimeMillis()-time)+"]");

	}


	@Test
	public void getDetailProfessionSerialNumbers_test4 ( ) {

		BusinessSerial businessSerial = new BusinessSerial();
		businessSerial.setBusinessNo("a001a");
		businessSerial.setPlatform("1");
		businessSerial.setSystemNo("1");
		businessSerial.setServiceNo("XV");
		businessSerial.setTerminalType("1");
		businessSerial.setSerialNumber("1111111a222222a");

		long time = System.currentTimeMillis();
		PublicResult<ArrayList<String>> publicResult = logicSerialService.getDetailProfessionSerialNumbers(businessSerial,1000);
		if(!publicResult.isSuccess()){
			System.out.println(publicResult.getErrorMsg());
		}else {
			System.out.println(publicResult.getResult());
		}

		System.out.println("result:"+JSON.toJSONString(publicResult.getResult()));


	}



}

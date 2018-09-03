# serial
流水号生成系统，结合mysql与redis做互相校验，保证流水号不重复

# 接入步骤
## sql 创建
地址：
serial\dbfiles\init.sql
文件内容为:
```

SET FOREIGN_KEY_CHECKS=0;
CREATE DATABASE IF NOT EXISTS xsf_serial DEFAULT CHARSET utf8;
use xsf_serial;
-- ----------------------------
-- Table structure for sequence_value
-- ----------------------------
DROP TABLE IF EXISTS `sequence_value`;
CREATE TABLE `sequence_value` (
  `name` varchar(100) NOT NULL COMMENT '表名称',
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '自增加id',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

```

## 修改mysql连接地址
serial\xs-frame-serial\serial-impl\src\main\resources\mysql.properties
文件初始内容为：
```

##默认数据源配置
spring.datasource.url=jdbc:mysql://192.168.2.150:3316/xsf_serial?characterEncoding=utf8&amp;rewriteBatchedStatements=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
##数据库链接池参数配置
#指定使用哪个连接池，默认使用tomcate-jdbc连接池
spring.datasource.type=com.mchange.v2.c3p0.ComboPooledDataSource

```

## 修改redis连接地址
serial\xs-frame-serial\serial-impl\src\main\resources\redis.properties
文件初始内容为：
```

spring.redis.host=192.168.1.111
spring.redis.port=9000

```



# dubbo接口
## pom

```

<dependency>
	<groupId>com.xs.frame</groupId>
	<artifactId>serial-service</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>

```

## dubbo
```

<dubbo:registry protocol="zookeeper" address="yourhost:2181" id="zookeeper" timeout="15000"/>
<dubbo:protocol name="dubbo" port="-1"  />
<!-- 统一服务流水号 -->
<dubbo:reference id="logicSerialService" group="xs-frame-serial" interface="com.xs.frame.iserial.ILogicSerialService" 
	version="1.0.0" timeout="60000" protocol="dubbo" registry="zookeeper" validation="false" lazy="true" retries="0" check="false" />
	
```
		


# 使用示例

```

@Autowired
ILogicSerialService logicSerialService;

@Test
public void getProfessionSerialNumber_test1 () {
	log.info("==>begin getProfessionSerialNumber_test1e");

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

}

```

返回结果
```

{"result":"JC1010011XV0118090300116001","success":true}

```



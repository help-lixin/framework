集成手册:
1. 加入相关依赖( 由于需要从ThredLocal中获得"库前缀"和"表前缀",所以,对MyBatis进行了改动 )
	<dependency>
		<groupId>org.mybatis</groupId>
		<artifactId>mybatis</artifactId>
		<version>3.5.3-SNAPSHOT</version>
		<!--  需要注意:引入的是: 3.5.3-SNAPSHOT  -->
	</dependency>
	<dependency>
		<groupId>help.lixin.framework.mybatis.integration</groupId>
		<artifactId>mybatis-integration</artifactId>
		<version>1.0.0-SNAPSHOT</version>
	</dependency>			
	<dependency>
		<groupId>help.lixin.framework.mybatis.integration</groupId>
		<artifactId>mybatis-integration-context</artifactId>
		<version>1.0.0-SNAPSHOT</version>
	</dependency>  
	<!-- 需要依赖: javassist/ognl -->
	<dependency>
		<groupId>org.javassist</groupId>
		<artifactId>javassist</artifactId>
		<version>3.24.1-GA</version>
		<scope>compile</scope>
		<optional>true</optional>
	</dependency>
	<dependency>
		<groupId>ognl</groupId>
		<artifactId>ognl</artifactId>
		<version>3.2.10</version>
		<scope>compile</scope>
		<optional>true</optional>
	</dependency>	
	
2. 配置mapper文件位置以及要扫描的包
	[mybatis]
		mapperLocations=classpath*:META-INF/mappers/*.xml
		basePackage=help.lixin.framework.test.mapper

3. 在调用MyBatis的Mapper之前手动设置DB信息(可配置AOP根据上下文设置)
	public List<Map> queryAll() {
		// 手动设置DB需要的信息
		new VarContext.Builder()
		.var("_db","fw")
		.var("_tablePrefix","fw_")
		.bind();
		return languageMapper.query();
	}

4.Mapper信息
	<select id="query"  resultType="map">
		SELECT * FROM ${_db}.${_tablePrefix}language 
	</select>


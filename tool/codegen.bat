@echo on
cd %~dp0
java -jar mybatis-generator-core-1.3.1.jar -configfile mybatisConfig.xml -overwrite
pause

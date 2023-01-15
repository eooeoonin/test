项目结构说明:
${project-name}-parent
-------${project-name}
---------------------${project-name}-controller
---------------------${project-name}-dao
---------------------${project-name}-domain
---------------------${project-name}-service
---------------------${project-name}-service.impl
-------${project-name}-resources
##############################################################
${project-name}-controller
交互控制             *****Controller
通讯实体             *****From    
package        ******.request.  入参
               ******.response. 出参
${project-name}-service
接口命名规则      I*****Service  
通讯实体             *****VO 
package        ******.request.  入参
               ******.response. 出参
${project-name}-service.impl
实现命名规则      *****Service

${project-name}-domain
接口命名规则      I*****Domain  
通讯实体model             *****
               
${project-name}-domain.impl
实现命名规则      *****Domain

${project-name}-model
数据实体             *****Po

${project-name}-dao
数据操作接口      I*****Mapper

${project-name}-common
本工程工具类

#=======================指令说明=======================

1.清空环境
mvn clean 

2.建立Eclipse环境
mvn eclipse:eclipse 

3.清空Eclipse环境
mvn eclipse:clean 

4.只编译
mvn compile 

5.只打包
mvn package -Dmaven.test.skip=true

6.只装配
mvn process-resources

7.打包+装配+安装二进制
mvn install -Dmaven.test.skip=true

8.依赖关系查询
mvn dependency:tree
mvn dependency:analyze
mvn dependency:list

运行maven tomcat
tomcat:run-war -Pdev

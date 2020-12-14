FROM openjdk:8-jdk-alpine
# 作者
MAINTAINER huanxi <1355473748@qq.com>
# VOLUME 指定了临时文件目录为/tmp。
# 将jar包添加到容器中并更名为app.jar
ADD target/*.jar /opt/app.jar
# 运行jar包
ENTRYPOINT ["java","-Xmx512m","-jar","/opt/app.jar"]

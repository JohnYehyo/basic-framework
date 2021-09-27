FROM openjdk:8-jdk-alpine

MAINTAINER JohnYehyo <johnyehyo@hotmail.com>

LABEL description="基础框架"

ENV MYPATH /opt/rjsoft

WORKDIR $MYPATH

ADD rjsoft-web.jar rjsoft.jar

VOLUME $MYPATH

EXPOSE 8088

ENTRYPOINT ["java", "-Xms1000m -Xmx1000m", "-jar","/opt/rjsoft/rjsoft.jar"]

FROM openjdk:8-jdk-slim
COPY juddi-distro-3.3.7 /usr/src/myapp
WORKDIR /usr/src/myapp
EXPOSE 8080
ENV JAVA_OPTS -Djavax.xml.accessExternalDTD=all
CMD ["juddi-tomcat-3.3.7/bin/startup.sh", ";", "bash", "-i"]
FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} practicatf.jar

ENTRYPOINT ["java","-jar","practicatf.jar"]
FROM eclipse-temurin:17 as builder
ENV LANG=en_US.UTF-8
WORKDIR application
ARG APPLICATION
ARG JAR_FILE=${APPLICATION}/target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17
ENV LANG=en_US.UTF-8
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

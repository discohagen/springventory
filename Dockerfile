FROM openjdk:23-jdk

WORKDIR /usr/src/app/

COPY ./pom.xml ./pom.xml
COPY ./.mvn ./.mvn
COPY ./mvnw ./mvnw

RUN ./mvnw dependency:go-offline

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

CMD ["java", "-jar", "./target/springventory-0.0.1-SNAPSHOT.jar"]
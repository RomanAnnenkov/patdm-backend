FROM alpine:3.18 AS builder
RUN apk update && apk --no-cache add openjdk17-jdk=17.0.10_p7-r0 maven=3.9.2-r0
COPY . .
RUN mvn package

FROM alpine:3.18
RUN apk update && apk --no-cache add openjdk17-jre=17.0.10_p7-r0 curl
COPY --from=builder /target/*.jar /app/app.jar
WORKDIR /app
RUN mkdir logs storage && chown 1000:1000 logs storage
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

FROM java:8-jre
ADD ./target/customized-libs.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/customized-libs.jar"]
EXPOSE 8080
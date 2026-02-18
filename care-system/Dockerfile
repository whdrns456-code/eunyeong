# 1. 자바 환경 설정
FROM eclipse-temurin:17-jdk-focal

# 2. JAR 파일을 컨테이너 내부로 복사
# build/libs 폴더에 있는 jar 파일 plain : x 
COPY care-system-0.0.1-SNAPSHOT.jar app.jar

# 3. 앱 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
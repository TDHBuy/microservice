# ----- RUN in NODE.js/Express -----------
#FROM node:18
#WORKDIR /app
#COPY package*.json ./
#RUN npm install
#COPY . .
#EXPOSE 3000
#CMD ["node", "app.js"]


# ----- RUN in Spring ------------
FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 3000
ENTRYPOINT ["java", "-jar", "/app.jar"]

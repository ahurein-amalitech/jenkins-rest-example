FROM openjdk:17

EXPOSE 8082
ADD target/devops_doc.jar devops_doc.jar 
ENTRYPOINT [ "java", "-jar", "/devops_doc.jar" ]
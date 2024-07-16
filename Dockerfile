FROM openjdk:8

EXPOSE 8080
ADD target/devops_doc.jar devops_doc.jar 
ENTRYPOINT [ "java", "-jar", "/devops_doc.jar" ]
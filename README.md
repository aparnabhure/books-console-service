# book-console-service
MVC service for books library app

Docker image creation and running in docker container:
1. Install docker from docker.com 
2. Run the docker container to get deploy your service in that container
3. Add DockerFile
   1. FROM - Base Image (OpenJDK as Sprint Book app needs Java to run)
   2. ADD - To add your service's JAR file to get executed
   3. EXPOSE - Expose an PORT to access the Service URIs
   4. ENTRYPOINT - To run the Jar
4. Create Docker Build
   1. docker build -f DockerFile -t book-console-service .
       -f - DockerFile name
       -t - Tag for the image we can use versioning as well
5. Run docker image: > docker run -p 8081:8081 books-console-service
6. You may need to run >docker login to run the img
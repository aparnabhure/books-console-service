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

Monitoring:
https://www.callicoder.com/spring-boot-actuator-metrics-monitoring-dashboard-prometheus-grafana/
- Spring boot actuator will expose actuator endpoints to monitor service health and metrics
- Prometheus is external monitoring system to collect metrics periodically on given interval using micrometer registry micrometer-registry-prometheus
- After adding micrometer-registry-prometheus dependency and prometheus endpoint will get added with actuator
- Download and run prometheus using docker image 
- Download the image > docker pull prom/prometheus
- Add prometheus.yml configuration to allow prometheus server to scrape/pull the metrics data update HOST_IP as network ip where the service is running, localhost will not work, get IPdetails from ipconfig->ipv4 address in windows
- Run the Prometheus server > docker run -d --name=prometheus -p 9090:9090 -v C:\Users\Aparna\Documents\GitHub\books-console-service\prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
- After this we can access the prometheus dashboard with http://localhost:9090/
- Change the management.server.port back to 8080 then only prometheus will fetch the metrics data
- Few queries examples for prometheus:
- http_server_requests_seconds_max{method="GET", uri=~"/books.+"}  //Search all uris starts with /books
- http_server_requests_seconds_max{uri="/home"} for /home exact match
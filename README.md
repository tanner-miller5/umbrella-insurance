# spring-boot modules
mvn clean install -Dmaven.test.skip=true 
cd umbrella-insurance-core
mvn test -Dtest=FlywayTest#migrationSuccessTest -DSPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/umbrella-insurance"

cd umbrella-insurance-backend
mvn spring-boot:start
mvn spring-boot:stop

Java tests
mvn test -DSPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/umbrella-insurance" \
-Dsurefire.failIfNoSpecifiedTests=false -Dtest=\!DatabaseTests,\!TablesTests


Docker builds
docker build -f Dockerfile-umbrella-insurance-backend -t tmillermillert/umbrella-insurance-backend:1.22 .
docker build -f Dockerfile-umbrella-insurance-react-ui -t tmillermillert/umbrella-insurance-react-ui:1.21 .

Local Postgres DB 
docker run --name some-postgres -e POSTGRES_PASSWORD=umbrella-insurance -e POSTGRES_USER=umbrella-insurance -e POSTGRES_DB=umbrella-insurance -p 5432:5432 -d postgres

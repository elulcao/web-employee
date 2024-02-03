package:
	mvn clean package -DskipTests

build:
	mvn clean package

run: package
	java -jar target/Employee-1.0-SNAPSHOT.jar server config.yaml

check:
	java -jar target/Employee-1.0-SNAPSHOT.jar check config.yaml
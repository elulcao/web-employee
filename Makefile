package:
	mvn clean package

run: package
	java -jar target/Employee-1.0-SNAPSHOT.jar server
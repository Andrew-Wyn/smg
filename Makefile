all: run install package

database:
	sudo service mongod start
	mongo

run:
	mvn spring-boot:run

install:
	./check_libannotation.sh

package:
	./check_libannotation.sh
	mvn clean package -DskipTests
	java -Dloader.path=src/main/resources/lib/action_guard/ -jar ./target/smg-0.0.1-SNAPSHOT.jar 
	

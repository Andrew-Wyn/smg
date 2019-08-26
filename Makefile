all: run

database:
	sudo service mongod start
	sleep 2
	mongo

run:
	@echo "========= start CERERE"
	@echo "========= check libannotation"
	./check_libannotation.sh
	@echo "========= DONE"
	@echo "========= check mvn version"
	mvn -v
	@echo "========= DONE"
	@echo "========= build main project"
	mvn clean package -DskipTests
	@echo "========= DONE"
	@echo "========= LAUNCH"
	java -Dloader.path=src/main/resources/lib/action_guard/ -jar ./target/smg-0.0.1-SNAPSHOT.jar 
	
wrapper_run:
	@echo "========= start CERERE (using mvnw)"
	@echo "========= check libannotation"
	./check_libannotation.sh
	@echo "========= DONE"
	@echo "========= build main project"
	./mvn clean package -DskipTests
	@echo "========= DONE"
	@echo "========= LAUNCH"
	java -Dloader.path=src/main/resources/lib/action_guard/ -jar ./target/smg-0.0.1-SNAPSHOT.jar

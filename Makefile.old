all: run

database:
	sudo service mongod start
	sleep 2
	mongo

install:
	@echo "========= force install the libannotation jar"
	mvn install:install-file -Dfile=src/main/resources/lib/annotation/bmeme-lib-libannotation-spring-boot-starter-1.0.0-SNAPSHOT.jar -DgroupId=com.bmeme.lib -DartifactId=bmeme-lib-libannotation-spring-boot-starter -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar
	@echo "\nINSTALLED LIBRARY\n"


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
	#mvn -Dloader.path=src/main/resources/lib/action_guard/ spring-boot:run

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

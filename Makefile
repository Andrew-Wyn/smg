.PHONY: requirements compose destroy start stop clean build create-jar publish-jar run debug test test-debug

MVN=./cmvn
APP=./.bmeme/bin/app

-include Makefile.local
-include .env

all: requirements destroy compose

requirements:
	@echo '============ Checking if docker binaries exist...'
	@docker --version
	@docker-compose --version
	@echo '============ OK!'

compose:
	@echo '============ Creating docker environment...'
	docker-compose build --pull
	docker-compose up -d
	@sleep 10
	docker-compose start mongo-express
	@echo '============ Docker environment for your project successfully created.'

destroy:
	@echo "============ Cleaning up docker environment..."
	docker-compose down -v
	docker-compose kill
	docker-compose rm -vf

start:
	docker-compose start

stop:
	docker-compose stop

clean:
	${MVN} clean

build:
	${MVN} compile

create-jar:
	${MVN} clean package

publish-jar: create-jar
	${MVN} publish


install:
	@echo "========= force install the libannotation jar"
	${MVN} install:install-file -Dfile=src/main/resources/lib/annotation/bmeme-lib-libannotation-spring-boot-starter-1.0.0-SNAPSHOT.jar -DgroupId=com.bmeme.lib -DartifactId=bmeme-lib-libannotation-spring-boot-starter -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar
	@echo "\nINSTALLED LIBRARY\n"
 

app:
	@echo "========= build main project"
	${MVN} clean package -DskipTests
	@echo "========= DONE"
	@echo "========= LAUNCH"
	${APP} java -Dloader.path=src/main/resources/lib/action_guard/ -jar ./target/smg-0.0.1-SNAPSHOT.jar

database:
	@echo "========= MONGO SHELL"
	docker-compose exec database mongo -u ${DB_USER} -p ${DB_PASS}

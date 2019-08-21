all: run database

run: 
	mvn clean spring-boot:run

database:
	sudo service mongod start

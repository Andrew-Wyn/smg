#!/usr/bin/env bash

#create user for cerere database
mongo -u ${MONGO_INITDB_ROOT_USERNAME} -p ${MONGO_INITDB_ROOT_PASSWORD} --eval "db = db.getSiblingDB('admin'); db.createUser({user:\"cerere\", pwd:\"cerere\", roles:[{ role: \"root\", db: \"admin\" }, {role:\"dbOwner\", db:\"cerere\"}, {role: \"readWrite\", db: \"cerere\"}]}); db.auth(\"cerere\", \"cerere\")"
mongo -u ${MONGO_INITDB_ROOT_USERNAME} -p ${MONGO_INITDB_ROOT_PASSWORD} --eval "db = db.getSiblingDB('cerere'); db.createUser({user:\"cerere\", pwd:\"cerere\", roles:[{ role: \"readWrite\", db: \"cerere\" }]});"

# init import for cerere database
mongoimport -u ${MONGO_NON_ROOT_USERNAME} -p ${MONGO_NON_ROOT_PASSWORD} --db ${MONGO_INITDB_DATABASE} --collection fSMConfiguration --jsonArray --file /docker-dumps/fSMConfiguration.json

#!/usr/bin/env bash

mongo -u ${MONGO_INITDB_ROOT_USERNAME} -p ${MONGO_INITDB_ROOT_PASSWORD} --eval "db.createUser({user:\"cerere\", pwd:\"cerere\", roles:[{ role: \"userAdmin\", db: \"cerere\" }]})"


# mongoimport -u ${MONGODB_USERNAME} -p ${MONGODB_PASSWORD} --db ${MONGODB_DATABASE} --collection fSMConfiguration --jsonArray --file /docker-dumps/fSMConfiguration.json
mongoimport -u cerere -p cerere --db ${MONGO_INITDB_DATABASE} --collection fSMConfiguration --jsonArray --file /docker-dumps/fSMConfiguration.json

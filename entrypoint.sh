#!/bin/bash

echo "$ISANTEPLUS_URL"
echo "$ISANTEPLUS_PW"
echo "$TEST_OPTIONS"

sed -e "s@\${ISANTEPLUS_URL}@$ISANTEPLUS_URL@" -e "s@\${ISANTEPLUS_PW}@$ISANTEPLUS_PW@" -e "s@\${ISANTEPLUS_USER}@$ISANTEPLUS_USER@" /lib/src/test/resources/test.properties.template | tee /lib/src/test/resources/test.properties

if [ -z ${TEST_OPTIONS+x} ]; then
  mvn test
else
  mvn test $TEST_OPTIONS
fi

echo "$ISANTEPLUS_URL"
echo "$ISANTEPLUS_PW"

sed -e "s@\${ISANTEPLUS_URL}@$ISANTEPLUS_URL@" -e "s@\${ISANTEPLUS_PW}@$ISANTEPLUS_PW@" -e "s@\${ISANTEPLUS_USER}@$ISANTEPLUS_USER@" /lib/src/test/resources/test.properties.template | tee /lib/src/test/resources/test.properties

mvn test
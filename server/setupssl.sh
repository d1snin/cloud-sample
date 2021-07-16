#!/bin/bash

RES_PATH="src/main/resources"

openssl req -x509 -newkey rsa:4096 -keyout $RES_PATH/key.pem -out $RES_PATH/cert.pem -days 365
openssl pkcs12 -export -in $RES_PATH/cert.pem -inkey $RES_PATH/key.pem -certfile $RES_PATH/cert.pem -out $RES_PATH/keystore.p12
keytool -importkeystore -srckeystore $RES_PATH/keystore.p12 -srcstoretype pkcs12 -destkeystore $RES_PATH/server.jks -deststoretype JKS

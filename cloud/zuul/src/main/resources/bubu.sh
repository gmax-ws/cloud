#!/bin/bash

file="/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/cacerts"
# keytool -list -keystore $file
# openssl pkcs12 -in certificates.p12 -out certificates.crt -nokeys
keytool -import -noprompt -trustcacerts -alias certificates --file certificates.crt -keystore $file -storepass changeit

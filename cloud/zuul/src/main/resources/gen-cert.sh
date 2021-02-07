#!/bin/bash

name="certificates"
keytool -genkeypair -alias $name -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore $name.p12 -validity 3650
#keytool -genkeypair -alias $name -keyalg RSA -keysize 2048 -keystore $name.jks -validity 3650
#keytool -importkeystore -srckeystore $name.jks -destkeystore $name.p12 -deststoretype pkcs12

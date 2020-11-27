#!/usr/bin/env bash

if [ -f $PWD/ear/target/ejbtimer.ear ];then
	echo "deploy ear"
	podman cp  $PWD/ear/target/ejbtimer.ear sharp_pike:/opt/eap/standalone/deployments
else
	echo "deployment not found"
fi

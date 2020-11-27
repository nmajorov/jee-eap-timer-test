#!/usr/bin/env bash

echo $PWD
if [ -f $PWD/ear/target/greeter.ear ];then
	echo "deploy ear"
	podman cp  $PWD/ear/target/greeter.ear sharp_pike:/opt/eap/standalone/deployments
else
	echo "deployment not found"
fi

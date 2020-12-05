#!/usr/bin/env bash

#SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

SCRIPT_DIR=`dirname "$0"`

if [ -f $SCRIPT_DIR/ear/target/greeter.ear ];then
	echo "deploy ear"
	podman cp  $SCRIPT_DIR/ear/target/greeter.ear sharp_pike:/opt/eap/standalone/deployments
else
	echo "deployment not found"
fi

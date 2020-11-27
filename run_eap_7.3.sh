#!/usr/bin/env bash

podman run --name sharp_pike -it --rm -e DISABLE_EMBEDDED_JMS_BROKER=true  registry.redhat.io/jboss-eap-7/eap73-openjdk11-openshift-rhel8  $@

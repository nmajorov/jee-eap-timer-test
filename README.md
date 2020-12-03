#  Test with wildfly/eap dependencies deployment  problems



How-to create image with podman commit from  an RedHat EAP image:


* run official eap image  with script:

        ./run_eap_7.3.sh


ejbtimer ear has dependency on greeter.ear so greeter.ear has to be deployed first !



* build  project greeter with commands

        cd greeter-ear
        mvn clean install


* deploy first dependency greeter.ear with commands:

      cd greeter-ear
      ./deploy.sh


* build  project timer with commands

        cd ejbtimer
        mvn clean install



* deploy timer ear with command:


        cd  ejbtimer
        ./deploy.sh






**important** to deploy first greeter.ear

after deployment success you can see in server logs each 6 sec a message:

            1:40:51,276 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 1) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:40:51,280 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 2) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:40:57,274 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 2) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:40:57,279 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 1) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:41:03,217 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 1) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:41:03,261 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 1) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:41:09,212 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 1) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:41:09,263 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 1) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:41:15,217 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 1) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:41:15,259 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 1) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:41:21,276 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 1) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !
            21:41:21,279 INFO  [ch.basel.eap7.ejb.MyTimer] (EJB default - 2) ********* Injected bean running say something: Salut Geneva Post Tenebras Lux !

login in running container:

            podman exec -it sharp_pike bash

and edit file:


            vi /opt/eap/bin/openshift-launch.sh


find and comment out a "configure_server" directive:


            22 function runServer() {
                23   local instanceDir=$1
                24
                25   # exposed by wildfly-cekit-modules
                26  # configure_server
                27
                28   log_info "Running $JBOSS_IMAGE_NAME image, version $JBOSS_IMAGE_VERSION"


then  commit image with command:

        commit_image.sh


and finally run commited image with script:


        run_commited_image.sh

you should see messages is coming and deployment are fine..



**notice** after commited images is started you may see message:


            21:44:51,505 ERROR [org.jboss.as.controller] (Controller Boot Thread) WFLYCTL0056: Could not rename /opt/eap/standalone/configuration/standalone_xml_history/current to /opt/eap/standalone/
            configuration/standalone_xml_history/20201127-214451503: java.nio.file.DirectoryNotEmptyException: /opt/eap/standalone/configuration/standalone_xml_history/current
                    at java.base/sun.nio.fs.UnixCopyFile.ensureEmptyDir(UnixCopyFile.java:384)                                                                                                          
                    at java.base/sun.nio.fs.UnixCopyFile.move(UnixCopyFile.java:484)
                    at java.base/sun.nio.fs.UnixFileSystemProvider.move(UnixFileSystemProvider.java:267)


it's ok  container is started  but just can't do some folders renaming  due  to immutability...

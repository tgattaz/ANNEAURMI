# ANNEAURMI

EXO 4 rmi : 

rmiregistry
java -cp . anneau.exo4rmi.ServeurCentral 8080 50

ja -cp . -Djava.security.policy=./anneau/exo4rmi/security.policy -Djava.rmi.server.codebase=file:/. anneau.exo4rmi.ProgrammeSite 127.0.0.1 8080 1 2 true
ja -cp . -Djava.security.policy=./anneau/exo4rmi/security.policy -Djava.rmi.server.codebase=file:/. anneau.exo4rmi.ProgrammeSite 127.0.0.1 8080 2 3 false
ja -cp . -Djava.security.policy=./anneau/exo4rmi/security.policy -Djava.rmi.server.codebase=file:/. anneau.exo4rmi.ProgrammeSite 127.0.0.1 8080 3 1 false

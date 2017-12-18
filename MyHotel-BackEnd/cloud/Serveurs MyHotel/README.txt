2 instances EC2 font tourner une image docker d'une application MyHotel et une Telegraf

Voici les fichiers de configuration pour Telegraf

La compilation et l'exécution de l'image docker de MyHotel est faîte à la base du dossier avec ./build.sh.

Le fichier Dockerbase est un dockerfile qui contient quelques opérations qui ne sont pas nécessaires à chaque build docker comme l'installation de maven ou l'ajout de wildfly. 
Il doit être build avant le dockerfile.
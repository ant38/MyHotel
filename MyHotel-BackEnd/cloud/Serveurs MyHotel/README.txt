2 instances EC2 font tourner une image docker d'une application MyHotel et une Telegraf

Voici les fichiers de configuration pour Telegraf

La compilation et l'ex�cution de l'image docker de MyHotel est fa�te � la base du dossier avec ./build.sh.

Le fichier Dockerbase est un dockerfile qui contient quelques op�rations qui ne sont pas n�cessaires � chaque build docker comme l'installation de maven ou l'ajout de wildfly. 
Il doit �tre build avant le dockerfile.
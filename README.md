# A propos
C'est un petit projet pour réaliser des tests avec l'API **Kafka Consumer** depuis un projet Maven classique. (aucun framework utilisé)

L'application permet de consommer des records au **format Avro**.
Un premier plugin Maven est utilisé pour télécharger les schémas Avro depuis la schema-registry.
Un second plugin Maven est utilisé pour produire les classes Java depuis le schéma Avro afin de pouvoir deserializer la donnée.

# Pré-requis

### Environnement Kafka

Un cluster Kafka (brokers, zookeepers, schema-registry ...) est nécessaire, pour mes tests j'ai utilisé une solution basée sur des containers Docker.

Mon docker-compose est disponible sur ce dépôt suivant :

https://github.com/lfriboulet/kafka_cluster


### Télécharger les schémas Avro
Lancer la commande suivante: (et à chaque fois que le schéma Avro a été mise à jour)

    mvn schema-registry:download

# Démarrer l'application

1. Cloner le projet
2. Compiler le projet
   Cette étape est nécessaire si les classes Java pour générer ne sont pas présentes ou si le schéma Avro a été mise à jour.


    mvn clean compile


3. Créer le jar


    mvn package


4. Lancer l'application


    java - jar kafka-consumer-1.0-SNAPSHOT.jar
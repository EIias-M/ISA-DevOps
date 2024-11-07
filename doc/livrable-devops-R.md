# Rapport DevOps Equipe R

- [Rapport DevOps Equipe R](#Rapport-DevOps-Equipe-R)
      - [Notre workflow](#notre-workflow)
        - [1. Une branche `main` stable et livrable à tout moment](#1-une-branche-main-livrable-à-tout-moment)
        - [2. Des pipelines rapides et sécurisées](#2-des-pipelines-rapides-et-sécurisées)
        - [3. Intégration rapide des changements](#3-intégration-rapide-des-changements)
           - [Les accès](#les-accès)
        - [Liens](#liens)
        - [Les credentials](#les-credentials)

## Plan de Reprise de l'activité

[Plan de reprise de l'activité](PRA.md)

## Notre workflow

Nous avons construit notre workflow dans le but de répondre à 4 objectifs principaux :

- Avoir une branche `main` stable et livrable à tout moment
- Avoir des pipelines rapides et sécurisées
- Pouvoir intégrer nos changements rapidement


Pour répondre à ces impératifs, avons choisi de mettre en place une stratégie de branchement de type 

<!-- [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/). -->

### 1. Une branche `main` livrable à tout moment


L'utilisation de Git Flow facilite les tests de bout en bout en les centralisant sur la branche develop après fusion, contrairement à GitHub Flow, où l'absence d'une branche intermédiaire complique la réalisation de ces tests. Effectuer des tests de bout en bout pour chaque Pull Request sur main rendrait cette branche instable pour la livraison continue, surtout que ces tests, prenant environ 5 minutes, ralentiraient l'intégration des changements, particulièrement lors des pics d'activité. 




### 2. Des pipelines rapides et sécurisées

Comme nous l'avons vu, la stratégie Git Flow nous permet de réaliser les tests
de bout en bout uniquement une fois les changements mergés sur la branche `develop`, ce qui nous permet réduire les tests à réaliser sur les Pull-Requests.
Pour continuer à optimiser le temps, nous réalisons uniquement les tests sur les modules qui ont été modifiés, sur les pipelines de nos branches de feature.
Pour implémenter cette stratégie, les pipelines se lancent en fonction du nom de la branche.


### 3. Intégration rapide des changements

L'existance de la branche `develop` à mi-chemin entre les branches de feature et la branche `main` nous permet d'avoir une zone tampon où nous pouvons
intégrer rapidement nos changements sans risque que ceux-ci soient déployés.




**Discussions et Perspectives :**  
Nous réalisons actuellement les mêmes tests de bouts en bouts sur les branches `develop`, `main` et sur les Releases.
En effet, nous réalisons les tests sur `develop` pour avoir un feedback rapide suite au merge. Nous les réalisons également sur `main`, car dans la stratégie Git Flow, les Hotfix ne passent pas par la branche `develop`, il est donc nécessaire de réaliser les tests avant le merge sur `main`. Nous réalisons aussi les tests lors du déploiement pour avoir plus de confiance dans notre livrable. Pour le moment ces tests de bout en bout sont les mêmes, mais les perspectives seraient de réaliser des tests vitaux lors de merge sur `develop`, ainsi que lors du déploiement et des tests de non-régression lors des merge sur `main`.

**En résumé :**

- Notre projet est composé de deux branches principales (`main` et `develop`) ainsi que de branches de features.
- Les pipelines de features réalisent les tests unitaires et d'intégration des modules concernés par les changements.
- La pipeline de la branche `develop`  build et test les nouveaux artefacts et build les images docker.
Elle réalise ensuite les tests de bout en bout.
- La pipeline de la branche `main` réalise les mêmes tests que la pipeline de la branche `develop` et déploie les artefacts sur l'environnement de production.


## Les accès

#### Les credentials

Le mot de passe de la machine virtuelle est resté le même que celui donné sur Slack en début de projet.
Nous possédons également une copie des identifiants externes (Docker Hub) en cas de problème avec la machine virtuelle.

#### Log into Jenkins:
- Username: `admin`
- Password: `admin12345`

####  Log into Artifactory:
- Username: `admin`
- Password: `admin_06`

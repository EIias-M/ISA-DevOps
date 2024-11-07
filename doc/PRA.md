# Plan de Reprise de l'Activité

- [Plan de Reprise de l'Activité](#plan-de-reprise-de-lactivité)
    - [Introduction](#introduction)
    - [Résolution de problèmes déjà rencontrés](#résolution-de-problèmes-déjà-rencontrés)
        - [VM](#vm)
        - [Jenkins](#jenkins)
        - [SonarQube](#sonarqube)
        - [Artifactory](#artifactory)
        - [Smee](#smee)
        - [GitHub](#github)
    - [Procédures de reconstruction](#procédures-de-reconstruction)
        - [Connexion à la VM](#connexion-à-la-vm)
        - [Create Smee payload URL](#create-smee-payload-url)
        - [Connexion Artifactory](#connexion-artifactory)
        - [Connexion Jenkins](#connexion-jenkins)



## Résolution de problèmes déjà rencontrés

### VM

- Problème : plus d'accès à la VM en SSH
- Solution : demander à redémarrer la VM sur Slack

- Problème : perte d'accès a la VM liée à l'utilisation élevée de RAM
- Solution : limitation des ressources allouées aux différents containers dans le fichier docker-compose

- Problème : plus de mémoire disponible
- Solution : suppression des anciennes images et volumes Docker ainsi que des anciennes branches des pipelines Jenkins.

### Jenkins

- Problème : les pipelines ne se lancent pas malgré que Smee fonctionne et que les Webhooks sont reçus voir [logs Jenkins](http://vmpx07.polytech.unice.fr:8000/manage/log/all)
- Solution : réindexer les branches des différentes pipelines

### SonarQube

- Problème : fail de la quality gate SonarQube malgré que les conditions soient respectées
- Solution : contrôler la mémoire disponible sur la VM 

### Artifactory

- Problème : Artifactory ne démarre pas
- Solution : relancer Artifactory

### Smee

- Problème : les pipelines ne sont plus déclenchées
- Solution :
    - vérifier que la session screen est toujours active `screen -ls` et que le smeeClient est toujours lancé `screen -r smeeClient`
    - si ce n'est pas le cas, relancer le Smee Client.
    - vérifier que le site Smee fonctionne. Si ce n'est pas le cas :
        - Créer une instance.
        - Ou utiliser Jenkins en mode polling

### GitHub

- Problème : Jenkins ne me laisse pas merger ma branche malgré que mes tests passent en local
- Solution : Voir les raisons de l'échec de la pipeline sur Jenkins.
  



### Connexion à la VM

Credentiels : 

- Username : ssh wbaratli@vmpx18.polytech.unice.fr
- Password : D@4zh%H99Ga$


### Create Smee payload URL

[Smee.io](https://smee.io) -> Start new Channel -> `Copy Webhook Proxy URL`

[github.com/pns-isa-devops/isa-devops-23-24-team-r-24/settings/hooks](https://github.com/pns-isa-devops/isa-devops-23-24-team-r-24/settings/hooks/) -> Settings -> Webhooks -> Add Webhook'
**Configuration:**

- Payload URL: URL délivrée par Smee
- Content Type: application/json
- Faire cette commande sur la VM :  `smee -u https://smee.io/52WW9dHdGXhkOPbV -t http://vmpx18.polytech.unice.fr:8001/github-webhook/`



### Connexion Artifactory 

- Connection :
    - Username : admin
    - Password : admin_06

    
### Connexion Jenkins

Credentiels : 

- Username : admin
- Password : admin12345


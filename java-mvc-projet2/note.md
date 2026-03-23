# Base de données — Gestion de forage

## Contexte

Une personne fait une demande de forage.  
L’entreprise réalise ensuite une étude et produit un devis d’étude, qui doit être conservé pour les statistiques.  
Le client accepte ou refuse ce devis.  
Si le devis d’étude est accepté, l’entreprise se rend sur le terrain pour vérifier si le forage est possible.  
Si le forage est faisable, l’entreprise produit un devis de forage.  
Le client accepte ou refuse ce second devis.  
Si le devis de forage est accepté, les travaux de forage commencent.

---



### 1. Client

Table qui stocke les informations du client.

#### `client`
- id
- nom
- telephone
- adresse

---

### 2. Demande de forage

C’est le point de départ du processus.

#### `demande_forage`
- id
- client_id
- reference
- date_demande
- lieu_forage
- description_besoin
- statut

### Exemple de statuts
- en_attente
- etude_en_cours
- etude_refusee
- etude_acceptee
- faisabilite_refusee
- devis_forage_refuse
- devis_forage_accepte
- forage_en_cours
- forage_termine
- annule

---

### 3. Étude

L’entreprise analyse la demande.

#### `etude`
- id
- demande_forage_id
- date_etude
- observations
- conclusion

### Exemple de conclusion
- favorable
- defavorable
- en_attente

---

### 4. Devis d’étude

Le devis d’étude doit être conservé pour les statistiques.

#### `devis_etude`
- id
- etude_id
- numero_devis
- date_devis
- montant
- description
- statut_client

### Exemple de `statut_client`
- en_attente
- accepte
- refuse

### Pourquoi séparer cette table
Cela permet de calculer :
- combien de devis d’étude ont été émis
- combien ont été acceptés
- combien ont été refusés
- le total des montants proposés

---

### 5. Visite terrain / faisabilité

Après acceptation du devis d’étude, l’entreprise va sur le terrain.

#### `visite_terrain`
- id
- demande_forage_id
- date_visite
- agent_nom
- observations
- resultat_faisabilite

### Exemple de `resultat_faisabilite`
- possible
- impossible
- a_confirmer

> Cette option est meilleure qu’un simple champ `oui/non`.

---

### 6. Devis de forage

Si le forage est faisable, l’entreprise produit un devis de forage.

#### `devis_forage`
- id
- demande_forage_id
- numero_devis
- date_devis
- montant
- description_travaux
- profondeur_prevue
- statut_client

### Exemple de `statut_client`
- en_attente
- accepte
- refuse

---

### 7. Forage

Le forage ne doit exister que si le devis de forage est accepté.

#### `forage`
- id
- demande_forage_id
- devis_forage_id
- date_debut
- date_fin
- profondeur_reelle
- observation
- statut

### Exemple de `statut`
- non_commence
- en_cours
- suspendu
- termine
- echoue

---

### 8. Étapes du forage

Les étapes doivent être enregistrées dans une table séparée pour garder l’historique.

#### `etape_forage`
- id
- forage_id
- nom_etape
- date_debut
- date_fin
- statut
- observation

### Étapes prévues
- debut
- recuperation_eau
- test_sanitaire_eau

### Exemple de `statut`
- non_commence
- en_cours
- termine
- echoue

### Pourquoi une table séparée
Cela permet de suivre :
- la date de début de chaque étape
- la date de fin
- les observations
- l’état réel de progression

---

## Ordre logique du processus

1. Le client fait une demande de forage
2. L’entreprise réalise une étude
3. L’entreprise émet un devis d’étude
4. Le client accepte ou refuse
5. Si accepté, l’entreprise fait une visite terrain
6. L’entreprise détermine la faisabilité
7. Si faisable, elle émet un devis de forage
8. Le client accepte ou refuse
9. Si accepté, le forage commence
10. Les étapes du forage sont suivies

---

## Résumé du modèle recommandé

### Tables à créer
- client
- demande_forage
- etude
- devis_etude
- visite_terrain
- devis_forage
- forage
- etape_forage

---

## Conclusion

La meilleure structure est de séparer clairement :

- la demande
- l’étude
- le devis d’étude
- la faisabilité
- le devis de forage
- le forage
- les étapes du forage

Cette structure est plus propre, plus facile à exploiter, et meilleure pour les statistiques.



mr
client
    - nom
    - conatacte
    - demande

demande
    - client
    - date_demande
    - description
    - lieux


devis
    - type (etude, forage)
    - date
    - demande_id
    - type_devis_id

typedevis
    - libelle

detail-devis
    - devis
    - libelle
    - montant

statue 
    - libelle

statut_travaux
    - libelle

traveau 
    - demande_id
    - traveau statut

demande statut
    - traveau
    - statut
    - date


fonctionnalite
crud de tout (demande client)
demande statut( avec de statut)


/////////////////////


## Version corrigée

### client
- id
- nom
- contact

### demande
- id
- client_id
- date_demande
- description
- lieu

### type_devis
- id
- libelle

### statut
- id
- libelle

### devis
- id
- demande_id
- type_devis_id
- date_devis
- statut_id

### detail_devis
- id
- devis_id
- libelle
- montant

### statut_travaux
- id
- libelle

### travaux
- id
- demande_id
- statut_travaux_id


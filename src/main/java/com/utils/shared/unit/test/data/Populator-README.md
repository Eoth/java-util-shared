# Populator

La classe `ObjectPopulator` permet de peupler les champs d'un objet avec des valeurs générées aléatoirement ou spécifiées par l'utilisateur. Elle utilise une instance de la classe `Option` pour définir les actions d'options sur les champs.

## Méthodes

### populate(Class<T> clazz, C collection, int size)

Cette méthode permet de peupler une collection avec des objets de la classe spécifiée. Elle prend en paramètres la classe 
des objets à créer, la collection à peupler et la taille désirée de la collection. La méthode renvoie la collection peuplée.

- `clazz` : La classe des objets à peupler la collection.
- `collection` : La collection à peupler.
- `size` : le nombre d'instances à créer, peupler et ajouter dans la collection.
- 
### populate(Class<T> clazz, C collection, int size, Option options)

Cette méthode est similaire à la méthode précédente, mais elle prend également en compte des options supplémentaires 
pour personnaliser le peuplement des objets. Les options sont spécifiées à l'aide d'un objet de la classe Option.

- `clazz` : La classe des objets à peupler la collection.
- `collection` : La collection à peupler.
- `size` : le nombre d'instances à créer, peupler et ajouter dans la collection.
- `options` : Les options d'action sur les champs

### populate(T object, int key, Option options)

Cette méthode statique peuple les champs de l'objet spécifié en utilisant les options fournies.

- `object` : L'objet à peupler.
- `key` : La clé entière utilisée pour générer des valeurs uniques.
- `options` : Les options d'action sur les champs.

### generateUniqueString(String fieldName, int key)

Cette méthode génère une valeur de chaîne unique basée sur le nom du champ et la clé fournie.

- `fieldName` : Le nom du champ.
- `key` : La clé entière utilisée pour générer des valeurs uniques.

### instantiateCollection(Class<?> collectionType)

Cette méthode instancie une collection vide du type spécifié.

- `collectionType` : Le type de la collection à instancier.

### instantiateMap(Class<?> mapType)

Cette méthode instancie une map vide du type spécifié.

- `mapType` : Le type de la map à instancier.

# Option

La classe `Option` permet de définir les actions d'options sur les champs d'un objet.

## Méthodes

### rename(String fieldName, String newFieldName)

Cette méthode spécifie que le champ spécifié doit être renommé avec le nouveau nom spécifié.

- `fieldName` : Le nom du champ à renommer.
- `newFieldName` : Le nouveau nom du champ.

### assignValue(String fieldName, Object value)

Cette méthode spécifie que le champ spécifié doit être assigné à la valeur spécifiée.

- `fieldName` : Le nom du champ à assigner.
- `value` : La valeur à assigner au champ.

### shouldIgnoreField(String fieldName)

Cette méthode indique si le champ spécifié doit être ignoré lors du peuplement.

- `fieldName` : Le nom du champ à vérifier.

### getFieldAction(String fieldName)

Cette méthode retourne l'action d'option associée au champ spécifié.

- `fieldName` : Le nom du champ.

# Utilisation

Voici un exemple d'utilisation de la classe `ObjectPopulator` :

```java
MyObject myObject = new MyObject();
Option options = new Option()
    .rename("field1", "newField1")
    .assignValue("field2", 42)
    .assignValue("field4", (byte) 10)
    .assignValue("field5", (short) 1000);
ObjectPopulator.populate(myObject, 123, options);
```

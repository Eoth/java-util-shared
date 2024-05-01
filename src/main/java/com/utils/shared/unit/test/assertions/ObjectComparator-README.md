# Classe ObjectComparator

Une classe utilitaire pour comparer des objets dans des tests unitaires.

## Méthodes publiques

### `compare(Object expected, Object actual, String... fieldsToExclude): void`

Compare deux objets pour vérifier s'ils sont égaux.

- **Paramètres :**
    - `expected` : l'objet attendu
    - `actual` : l'objet réel
    - `fieldsToExclude` : les champs à exclure de la comparaison
- **Exceptions :**
    - `AssertionError` : si la comparaison échoue

- # Utilisation

Voici un exemple d'utilisation de la classe `ObjectComparator` :

```java
ComplexObject expected = new ComplexObject("John", 25, Arrays.asList("a", "b", "c"), new Double[]{12.5, 25.0});
ComplexObject actual = new ComplexObject("John", 25, Arrays.asList("b", "a", "c"), new Double[]{25.0, 12.5});
ObjectComparator.compare(expected, actual);// la comparaison se passera avec success

ComplexObject expected = new ComplexObject("Johnattan", 25, Arrays.asList("a", "b", "c"), new Double[]{12.5, 25.0});
ComplexObject actual = new ComplexObject("John", 25, Arrays.asList("b", "a", "c"), new Double[]{25.0, 12.5});
ObjectComparator.compare(expected, actual);// la comparaison va lancer une exception AssertionError
```
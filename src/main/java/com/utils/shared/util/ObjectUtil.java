package com.utils.shared.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectUtil {
    private ObjectUtil() {
        // Constructeur privé pour empêcher l'instanciation de la classe utilitaire
    }

    /**
     * Vérifie si un objet est nul.
     *
     * @param obj l'objet à vérifier
     * @return true si l'objet est nul, false sinon
     */
    public static <T> boolean isNull(T obj) {
        return obj == null;
    }

    /**
     * Vérifie si un objet n'est pas nul.
     *
     * @param obj l'objet à vérifier
     * @return true si l'objet n'est pas nul, false sinon
     */
    public static <T> boolean isNotNull(T obj) {
        return obj != null;
    }

    /**
     * Compare deux objets pour déterminer s'ils sont égaux.
     *
     * @param obj1 le premier objet
     * @param obj2 le deuxième objet
     * @return true si les objets sont égaux, false sinon
     */
    public static <T> boolean equals(T obj1, T obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

    /**
     * Effectue une conversion de type sécurisée.
     *
     * @param obj   l'objet à convertir
     * @param clazz la classe cible de la conversion
     * @return l'objet converti ou null si la conversion échoue
     */
    public static <T> T cast(Object obj, Class<T> clazz) {
        try {
            return clazz.cast(obj);
        } catch (ClassCastException e) {
            return null;
        }
    }

    /**
     * Effectue une copie profonde d'un objet.
     *
     * @param obj l'objet à copier
     * @return la copie de l'objet ou null si la copie échoue
     */
    public static <T extends Serializable> T deepCopy(T obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            T copy = (T) ois.readObject();
            ois.close();

            return copy;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}


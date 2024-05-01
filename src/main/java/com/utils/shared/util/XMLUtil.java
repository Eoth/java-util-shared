package com.utils.shared.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe utilitaire pour la manipulation d'objets XML.
 */
public final class XMLUtil {

    private static final Map<Class<?>, JAXBContext> jaxbContexts = new ConcurrentHashMap<>();
    private static final XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();

    private XMLUtil() {
        // Constructeur privé pour empêcher l'instanciation de la classe
    }

    /**
     * Obtient le contexte JAXB pour une classe donnée.
     *
     * @param clazz la classe pour laquelle obtenir le contexte JAXB
     * @return le contexte JAXB associé à la classe
     */
    private static JAXBContext getJAXBContext(Class<?> clazz) {
        return jaxbContexts.computeIfAbsent(clazz, c -> {
            try {
                return createJAXBContext(c);
            } catch (JAXBException e) {
                throw new XMLManipulationException("Erreur lors de la création du contexte JAXB", e);
            }
        });
    }


    /**
     * Crée un nouveau contexte JAXB pour une classe donnée.
     *
     * @param clazz la classe pour laquelle créer le contexte JAXB
     * @return le nouveau contexte JAXB créé
     * @throws JAXBException en cas d'erreur lors de la création du contexte JAXB
     */
    private static JAXBContext createJAXBContext(Class<?> clazz) throws JAXBException {
        return JAXBContext.newInstance(clazz);
    }

    /**
     * Convertit un objet en une chaîne XML.
     *
     * @param obj          l'objet à convertir en XML
     * @param formatOutput indique si la sortie XML doit être formatée avec l'indentation
     * @param <T>          le type de l'objet
     * @return la chaîne XML représentant l'objet donné
     * @throws XMLManipulationException en cas d'erreur lors de la conversion de l'objet en chaîne XML
     */
    @SuppressWarnings("unchecked")
    public static <T> String toStringAsXML(T obj, boolean formatOutput) {
        try {
            Class<T> objClass = (Class<T>) obj.getClass();

            JAXBElement<T> element = new JAXBElement<>(new QName(objClass.getSimpleName()), objClass, obj);

            StringWriter writer = new StringWriter();

            Marshaller marshaller = getJAXBContext(objClass).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatOutput);

            marshaller.marshal(element, writer);

            return writer.toString();
        } catch (JAXBException e) {
            throw new XMLManipulationException("Erreur lors de la conversion d'un objet en chaîne XML", e);
        }
    }

    /**
     * Convertit un objet en une chaîne XML sans formatage.
     *
     * @param obj l'objet à convertir en XML
     * @param <T> le type de l'objet
     * @return la chaîne XML représentant l'objet donné
     * @throws XMLManipulationException en cas d'erreur lors de la conversion de l'objet en chaîne XML
     */
    public static <T> String toStringAsXML(T obj) {
        return toStringAsXML(obj, false);
    }

    /**
     * Convertit une chaîne XML en un objet.
     *
     * @param xml   la chaîne XML à convertir en objet
     * @param clazz la classe de l'objet à créer
     * @param <T>   le type de l'objet
     * @return l'objet créé à partir de la chaîne XML donnée
     * @throws XMLManipulationException en cas d'erreur lors de la conversion de la chaîne XML en objet
     */
    public static <T> T toObject(String xml, Class<T> clazz) {
        try {
            Unmarshaller unmarshaller = getJAXBContext(clazz).createUnmarshaller();

            StringReader reader = new StringReader(xml);
            XMLStreamReader xmlReader = xmlInputFactory.createXMLStreamReader(reader);

            JAXBElement<T> unmarshal = unmarshaller.unmarshal(xmlReader, clazz);

            xmlReader.close();
            reader.close();

            return unmarshal.getValue();
        } catch (JAXBException | XMLStreamException e) {
            throw new XMLManipulationException("Erreur lors de la conversion d'une chaîne XML en objet", e);
        }
    }

    /**
     * Exception personnalisée pour les erreurs XML.
     */
    public static class XMLManipulationException extends RuntimeException {
        public XMLManipulationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

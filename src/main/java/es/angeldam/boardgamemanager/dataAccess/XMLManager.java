package es.angeldam.boardgamemanager.dataAccess;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Class that has the methods for reading, writing, storing in an XML
 */
public class XMLManager {

    /**
     * Method that reads from an XML keeping the structure of the object relative to the java class
     * @param c object for getting the structure of the class when the XML is read
     * @param filename string with the filename that will be read
     * @return the object of the class read with the data on the XML
     * @param <T> a type parameter that marks this method as a generic method
     */
    public static <T> T readXML(T c, String filename) {
        T result = c;
        JAXBContext context;

        try {
            context = JAXBContext.newInstance(c.getClass());
            Unmarshaller um = context.createUnmarshaller();
            result = (T) um.unmarshal(new File(filename));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
}

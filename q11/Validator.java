package q11;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Validator {
    /**
     * @param XMLPath path to the XML file which has to be validated.
     * @param DTDPath path to the DTD file with which XML file has to be validated.
     *                pass null in case DTD is mentioned in the XML file itself.
     * @return true if the XML file is valid against the mentioned DTD file,
     *         otherwise prints the cause of the error and returns false.
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public boolean validateXMLWithDTD(final String XMLPath, final String DTDPath)
            throws ParserConfigurationException, IOException,
            TransformerConfigurationException, TransformerException {

        // Factory API that enables applications to obtain a parser
        // that produces DOM object trees from XML documents.
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

        // this will enable the validation of XML files while parsing
        // this is disabled by default, so we need to manually enable it.
        docBuilderFactory.setValidating(true);
        docBuilderFactory.setCoalescing(false);

        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

        // anonymous implementation of ErrorHandler interface, just provide
        // implementations of the abstract functions mentioned in the interface.
        docBuilder.setErrorHandler(new ErrorHandler() {
            // keep in mind, these handlers will capture the error, so any try catch
            // block won't get the errors unless we intentionally throw them at the
            // end of each handler.

            // we are throwing them all because we are just printing the errors
            // in the try catch block, but we can also handle something here.

            @Override
            public void error(SAXParseException exception) throws SAXException {
                throw exception;
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                throw exception;
            }

            @Override
            public void warning(SAXParseException exception) throws SAXException {
                throw exception;
            }
        });

        File XMLFile = new File(XMLPath);

        // The following section can be used when the XML file itself does not
        // contain the DTD declaration, hence in that case we need to manually
        // transform the DOMSource with DTDPath.

        try {
            if (DTDPath != null) {
                Document doc = docBuilder.parse(XMLFile);
                DOMSource source = new DOMSource(doc);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, DTDPath);

                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);

                transformer.transform(source, result);
                // Finally parse the result which will throw an exception if the doc is invalid
                // There are two cases, DTD mentioned in the XML file which we are validating
                // and DTD not mentioned in that XML file, in which case path to DTD must be
                // supplied using a parameter argument.

                // Use in case DTD is not mentioned in XML itself
                docBuilder.parse(new InputSource(new StringReader(writer.toString())));
            } else {
                // Use in case DTD is mentioned inside the file.
                docBuilder.parse(XMLFile);
            }

            return true;
        } catch (SAXException se) {
            // In case a SAX error is found, then the validation failed.
            // hence print the error and return false
            // Technically all the se.getClass() will return the class of SAXParseException
            // because
            System.out.println(se.getClass() + " :: " + se.getLocalizedMessage());
            return false;

        } catch (IOException ioe) {
            // in case input output exception occurs, not much is in out hand
            // hence throw the error for the user to handle.
            throw ioe;
        }
    }

}

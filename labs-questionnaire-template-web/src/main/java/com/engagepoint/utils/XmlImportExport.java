package com.engagepoint.utils;


import com.engagepoint.model.questionnaire.TemplateBean;
import com.engagepoint.model.questionnaire.Wrapper;
import org.apache.log4j.Logger;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.List;

public class XmlImportExport {
    private static final Logger LOG = Logger.getLogger(XmlImportExport.class);
    private static final String IMPORT_XML_EXCEPTION = "Import XML Exception";
    private static final String EXPORT_XML_EXCEPTION = "Export XML Exception";

    private XmlImportExport() {

    }

    /**
     * Unmarshal XML to Wrapper from file and return List value.
     */
    private static <T> List<T> unmarshal(Unmarshaller unmarshaller,
                                         String xmlLocation) throws JAXBException {
        StreamSource xml = new StreamSource(xmlLocation);
        Wrapper<T> wrapper = (Wrapper<T>) unmarshaller.unmarshal(xml,
                Wrapper.class).getValue();
        return wrapper.getItems();
    }

    /**
     * Unmarshal XML to Wrapper from input stream and return List value.
     */
    private static <T> List<T> unmarshal(Unmarshaller unmarshaller, InputStream inputStream) throws JAXBException {
        StreamSource xml = new StreamSource(inputStream);
        Wrapper<T> wrapper = (Wrapper<T>) unmarshaller.unmarshal(xml,
                Wrapper.class).getValue();
        return wrapper.getItems();
    }

    /**
     * Wrap List in Wrapper, then leverage JAXBElement to supply root element
     * information (using output stream).
     */
    private static void marshal(Marshaller marshaller, List<?> list, String rootElementName, OutputStream os)
            throws JAXBException, IOException {
        QName qName = new QName(rootElementName);
        Wrapper wrapper = new Wrapper(list);
        JAXBElement<Wrapper> jaxbElement = new JAXBElement<Wrapper>(qName,
                Wrapper.class, wrapper);
        marshaller.marshal(jaxbElement, os);
    }

    /**
     * Import XML date into TemplateBean objects using file path.
     *
     * @param filePath absolute path to XML file location
     * @return List of TemplateBean objects.
     */
    public static List<TemplateBean> importXmlTemplate(String filePath) {
        List<TemplateBean> list = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Wrapper.class, TemplateBean.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            list = unmarshal(unmarshaller, filePath);
        } catch (JAXBException e) {
            LOG.error(IMPORT_XML_EXCEPTION, e);
        }
        return list;
    }

    /**
     * Import XML into TemplateBean objects using input stream.
     *
     * @param inputStream inputStream of file
     * @return List of TemplateBean objects.
     */
    public static List<TemplateBean> importXmlTemplate(InputStream inputStream) {
        List<TemplateBean> list = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Wrapper.class, TemplateBean.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            list = unmarshal(unmarshaller, inputStream);
        } catch (JAXBException e) {
            LOG.error(IMPORT_XML_EXCEPTION, e);
        }
        return list;
    }


    /**
     * Export java objects into XML.
     *
     * @param listTemplateBean list of TemplateBean objects
     */
    public static void exportXmlTemplates(List<TemplateBean> listTemplateBean, String path) {
        OutputStream stream = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Wrapper.class, TemplateBean.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            stream = new FileOutputStream(path);
            marshal(marshaller, listTemplateBean, "questionnaire-forms", stream);
        } catch (Exception e) {
            LOG.error(EXPORT_XML_EXCEPTION, e);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                LOG.error(EXPORT_XML_EXCEPTION, e);
            }
        }
    }
}

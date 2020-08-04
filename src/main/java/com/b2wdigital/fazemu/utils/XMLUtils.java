package com.b2wdigital.fazemu.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML Utils.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public class XMLUtils {

    /**
     * show XML
     *
     * @param newDoc
     * @throws TransformerException
     */
    public static void showXML(Document newDoc) throws TransformerException {
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();
        Source src = new DOMSource(newDoc);
        Result dest = new StreamResult(System.out);
        aTransformer.transform(src, dest);
    }

    /**
     * get Stream From File
     *
     * @param pathXml
     * @return
     * @throws IOException
     */
    public static InputStream getStreamFromFile(String pathXml) throws IOException {
        InputStream isFromFile = new FileInputStream(new File(pathXml));
        String fileSTR = IOUtils.toString(isFromFile, StandardCharsets.UTF_8.name());
        return new ByteArrayInputStream(XMLUtils.unPrettyXml(fileSTR).getBytes());
    }

    /**
     * unPretty Xml
     *
     * @param xml
     * @return
     */
    public static String unPrettyXml(final String xml) {

        if (StringUtils.isBlank(xml)) {
            throw new RuntimeException("Xml eh nulo ou branco");
        }

        final StringWriter sw;

        try {
            final OutputFormat format = OutputFormat.createCompactFormat();
            final org.dom4j.Document document = DocumentHelper.parseText(xml);
            sw = new StringWriter();
            final XMLWriter writer = new XMLWriter(sw, format);
            writer.write(document);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar unpretty no xml:\n" + xml, e);
        }
        return sw.toString();
//        

//		String regex, updatedXml;
//
//		// 1. remove all white space preceding a begin element tag:
//		regex = "[\\n\\s]+(\\<[^/])";
//		updatedXml = xml.replaceAll(regex, "$1");
//
//		// 2. remove all white space following an end element tag:
//		regex = "(\\</[a-zA-Z0-9-_\\.:]+\\>)[\\s]+";
//		updatedXml = updatedXml.replaceAll(regex, "$1");
//
//		// 3. remove all white space following an empty element tag
//		// (<some-element xmlns:attr1="some-value".... />):
//		regex = "(/\\>)[\\s]+";
//		updatedXml = updatedXml.replaceAll(regex, "$1");
//        
//		return updatedXml;
    }

    /**
     * cleanNameSpace
     *
     * @param doc
     * @return
     */
    public static Document cleanNameSpace(Document doc) {

        NodeList list = doc.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            removeNameSpace(list.item(i));
        }

        return doc;
    }

    /**
     * removeNameSpace
     *
     * @param node
     */
    private static void removeNameSpace(Node node) {

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Document ownerDoc = node.getOwnerDocument();
            NamedNodeMap map = node.getAttributes();

            //remove namespaces attributes
            for (int i = 0; i < map.getLength(); i++) {
                Node n = map.item(i);
                if (n.getNodeName().equals("xmlns:ns2")
                        || n.getNodeName().equals("xmlns:ns3")
                        || n.getNodeName().equals("xmlns:nfec")
                        || n.getNodeName().equals("xmlns:soap")) {
                    map.removeNamedItemNS(n.getNamespaceURI(), n.getLocalName());
                    i = 0;
                }
            }
            ownerDoc.renameNode(node, node.getNamespaceURI(), node.getLocalName());

            //remove 'ns2' from tag name
            if (node.getNodeName().lastIndexOf("ns2") >= 0) {
                ownerDoc.renameNode(node, node.getNamespaceURI(), node.getLocalName());
            }
            //include namespace signature
            if (node.getNodeName().lastIndexOf("Signature") >= 0) {
                ((Element) node).setAttribute("xmlns", "http://www.w3.org/2000/09/xmldsig#");
            }
        }

        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            removeNameSpace(list.item(i));
        }
    }

    /**
     * @param node
     * @return
     */
    public static Node insertNameSpace(Node node) {

        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);

            if (n.getNodeType() == Node.ELEMENT_NODE) {

                //include namespace nfe
                if (n.getNodeName().lastIndexOf("NFe") >= 0
                        || n.getNodeName().lastIndexOf("enviNFe") >= 0
                        || n.getNodeName().lastIndexOf("evento") >= 0) {
                    ((Element) n).setAttribute("xmlns", "http://www.portalfiscal.inf.br/nfe");
                }
            }

            insertNameSpace(n);
        }

        return node;
    }

    public static String convertDocumentToString(Document doc) {
        return convertDocumentToString(doc, true);
    }

    /**
     * convert Document To String
     *
     * @param doc
     * @param unPretty
     * @return
     */
    public static String convertDocumentToString(Document doc, boolean unPretty) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            if (unPretty) {
                return XMLUtils.unPrettyXml(writer.toString());
            } else {
                return writer.toString();
            }
        } catch (TransformerException ex) {
            return null;
        }
    }

    /**
     * convert String To Document
     *
     * @param xml
     * @return
     * @throws Exception
     */
    public static Document convertStringToDocument(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Create New Document
     *
     * @return
     * @throws ParserConfigurationException
     */
    public static Document createNewDocument() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }

    /**
     * convert GZip To Xml
     *
     * @param conteudo
     * @return
     * @throws IOException
     */
    public static String convertGZipToXml(byte[] conteudo) throws Exception {
        if (conteudo == null || conteudo.length == 0) {
            return "";
        }

        GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(conteudo));
        BufferedReader br = new BufferedReader(new InputStreamReader(gzip, StandardCharsets.UTF_8.name()));
        String output = "";
        String line;
        while ((line = br.readLine()) != null) {
            output += line;
        }

        return output;
    }

    public static String convertGZipToXml(String conteudo) throws Exception {
        byte[] compressed = Base64.getDecoder().decode(conteudo);
        return convertGZipToXml(compressed);
    }

    public static String getXml(ByteArrayInputStream bais) throws Exception {
        Document document = getDocumentFromBais(bais);

        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(document), new StreamResult(sw));
        return sw.toString();
    }

    private static Document getDocumentFromBais(ByteArrayInputStream bais) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        byte[] array = new byte[bais.available()];
        bais.read(array);

        return builder.parse(new ByteArrayInputStream(array));

    }

    public static String getPrettyXmlFormat(String input, int indent) throws Exception {
        Source xmlInput = new StreamSource(new StringReader(input));
        StringWriter stringWriter = new StringWriter();
        StreamResult xmlOutput = new StreamResult(stringWriter);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", indent);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(xmlInput, xmlOutput);
        return xmlOutput.getWriter().toString();

    }

}

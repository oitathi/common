package com.b2wdigital.fazemu.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * @author Rodolpho Picolo
 */
public class NFeSOAPHeaderHandler implements SOAPHandler<SOAPMessageContext> {

    public static final String NOME_ELEMENTO_NFE_CABEC_MSG = "nfeCabecMsg";
    public static final String NOME_ELEMENTO_CODIGO_UF_EMISSOR = "cUF";
    public static final String NOME_ELEMENTO_VERSAO_DADOS = "versaoDados";

    private final String namespaceElementCabecalho;
    private final int codigoUFOrigemEmissor;
    private final String versaoDados;

    private NFeSOAPHeaderHandler(String serviceNamespace, int codigoUFOrigemEmissor, String versaoDados) {
        this.namespaceElementCabecalho = serviceNamespace;
        this.codigoUFOrigemEmissor = codigoUFOrigemEmissor;
        this.versaoDados = versaoDados;
    }

    @SuppressWarnings("rawtypes")
    public static void prepareHeader(BindingProvider port, String namespace, int codigoUFOrigemEmissor, String versaoDados) {
        NFeSOAPHeaderHandler handler = new NFeSOAPHeaderHandler(namespace, codigoUFOrigemEmissor, versaoDados);
        List<Handler> handlersList = new ArrayList<Handler>();
        handlersList.add(handler);
        port.getBinding().setHandlerChain(handlersList);
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        try {
            SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
            SOAPFactory factory = SOAPFactory.newInstance();
            String prefix = "";

            SOAPElement cabecMsg = factory.createElement(NOME_ELEMENTO_NFE_CABEC_MSG, prefix, this.namespaceElementCabecalho);
            SOAPElement cUF = factory.createElement(NOME_ELEMENTO_CODIGO_UF_EMISSOR, prefix, this.namespaceElementCabecalho);
            SOAPElement versaoDados = factory.createElement(NOME_ELEMENTO_VERSAO_DADOS, prefix, this.namespaceElementCabecalho);

            cUF.addTextNode(String.valueOf(this.codigoUFOrigemEmissor));

            versaoDados.addTextNode(this.versaoDados);

            cabecMsg.addChildElement(cUF);
            cabecMsg.addChildElement(versaoDados);
            SOAPHeader header = envelope.getHeader();
            if (header == null) {
                header = envelope.addHeader();
            }
            header.addChildElement(cabecMsg);

            return true;
        } catch (SOAPException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Set<QName> getHeaders() {
        return new TreeSet<QName>();
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return false;
    }

    @Override
    public void close(MessageContext context) {

    }

}

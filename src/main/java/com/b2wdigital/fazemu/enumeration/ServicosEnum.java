package com.b2wdigital.fazemu.enumeration;

import org.apache.commons.lang3.StringUtils;

/**
 * Servicos Enum.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public enum ServicosEnum {

    //NFe
    AUTORIZACAO_NFE("NFeAutorizacao", "Autorização", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeAutorizacao4", "4.00"),
    RETORNO_AUTORIZACAO_NFE("NFeRetAutorizacao", "Retorno Autorização", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeRetAutorizacao4", "4.00"),
    RECEPCAO_EVENTO_CANCELAMENTO("RecepcaoEvento", "Cancelamento", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeRecepcaoEvento4", "1.00"),
    RECEPCAO_EVENTO_CARTA_CORRECAO("RecepcaoEvento", "Carta de Correção", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeRecepcaoEvento4", "1.00"),
    RECEPCAO_EVENTO_MANIFESTACAO("RecepcaoEvento", "Manifestação", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeRecepcaoEvento4", "1.00"),
    RECEPCAO_EVENTO_EPEC("RecepcaoEvento", "Epec", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeRecepcaoEvento4", "1.00"),
    INUTILIZACAO("NfeInutilizacao", "Inutilização", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeInutilizacao4", "4.00"),
    CONSULTA_PROTOCOLO("NfeConsultaProtocolo", "Consulta Protocolo", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeConsultaProtocolo4", "4.00"),
    CONSULTA_STATUS_SERVICO("NfeStatusServico", "Consulta Status Serviço", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeStatusServico4", "4.00"),
    DISTRIBUICAO_DFE("NFeDistribuicaoDFe", "Distribuição Documentos Eletrônicos", "http://www.portalfiscal.inf.br/nfe/wsdl/NFeDistribuicaoDFe", "1.01");

    private final String nome;
    private final String descricao;
    private final String namespace;
    private final String versao;

    ServicosEnum(String nome, String descricao, String namespace, String versao) {
        this.nome = nome;
        this.descricao = descricao;
        this.namespace = namespace;
        this.versao = versao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getVersao() {
        return versao;
    }

    /**
     * get by Name
     *
     * @param name
     * @return
     */
    public static final ServicosEnum getByName(String name) {
        if (name == null) {
            return null;
        }

        for (ServicosEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(name), e.name())) {
                return e;
            }
        }
        return null;
    }

    /**
     * get by Nome
     *
     * @param nome
     * @return
     */
    public static final ServicosEnum getByNome(String nome) {
        if (nome == null) {
            return null;
        }

        for (ServicosEnum e : values()) {
            if (StringUtils.equalsIgnoreCase(StringUtils.trim(nome), e.getNome())) {
                return e;
            }
        }
        return null;
    }

}

package com.b2wdigital.fazemu.domain;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.b2wdigital.fazemu.utils.FazemuUtils;

/**
 *
 * @author dailton.almeida
 */
public class ResumoLote {

    private Long idLote;
    private String tipoDocumentoFiscal; //NFE ou NFSE
    private Long idEmissor;
    private Integer uf;                 //codigo do IBGE
    private Long municipio;             //codigo do IBGE - Municipio
    private Integer tipoEmissao;        //ex: 1 "normal"
    private String versao;              // ex: "v4.00"
    private List<Long> idDocFiscalList;
    private int quantidade = 0;
    private int tamanho = 0;
    private Date dataAbertura;
    private Date dataUltimaAlteracao;
    private String recibo;
    private Date dataUltimaConsultaRecibo;
    private String chaveAcesso;
    private String servico;

    public static ResumoLote build(Long idLote) {
        ResumoLote result = new ResumoLote();
        result.setIdLote(idLote);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o == this) {
            return true;
        } else if (o instanceof ResumoLote) {
            ResumoLote other = (ResumoLote) o;
            return new EqualsBuilder()
                    .append(this.idLote, other.idLote)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idLote);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("idLote", idLote)
                .append("tipoDocumentoFiscal", tipoDocumentoFiscal)
                .append("idEmissor", idEmissor)
                .append("uf", uf)
                .append("municipio", municipio)
                .append("tipoEmissao", tipoEmissao)
                .append("versao", versao)
                .append("idDocFiscalList", idDocFiscalList)
                .append("quantidade", quantidade)
                .append("tamanho", tamanho)
                .append("dataAbertura", dataAbertura)
                .append("dataUltimaAlteracao", dataUltimaAlteracao)
                .append("recibo", recibo)
                .append("dataUltimaConsultaRecibo", dataUltimaConsultaRecibo)
                .append("servico", servico)
                .toString();
    }

    public Long getIdLote() {
        return idLote;
    }

    public void setIdLote(Long idLote) {
        this.idLote = idLote;
    }

    public String getTipoDocumentoFiscal() {
        return tipoDocumentoFiscal;
    }

    public void setTipoDocumentoFiscal(String tipoDocumentoFiscal) {
        this.tipoDocumentoFiscal = tipoDocumentoFiscal;
    }

    public Long getIdEmissor() {
        return idEmissor;
    }

    public void setIdEmissor(Long idEmissor) {
        this.idEmissor = idEmissor;
    }

    public Integer getUf() {
        return uf;
    }

    public void setUf(Integer uf) {
        this.uf = uf;
    }

    public Long getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Long municipio) {
        this.municipio = municipio;
    }

    public Integer getTipoEmissao() {
        return tipoEmissao;
    }

    public void setTipoEmissao(Integer tipoEmissao) {
        this.tipoEmissao = tipoEmissao;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public List<Long> getIdDocFiscalList() {
        return idDocFiscalList;
    }

    public void setIdDocFiscalList(List<Long> idDocFiscalList) {
        this.idDocFiscalList = idDocFiscalList;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public String getRecibo() {
        return recibo;
    }

    public void setRecibo(String recibo) {
        this.recibo = recibo;
    }

    public Date getDataUltimaConsultaRecibo() {
        return dataUltimaConsultaRecibo;
    }

    public void setDataUltimaConsultaRecibo(Date dataUltimaConsultaRecibo) {
        this.dataUltimaConsultaRecibo = dataUltimaConsultaRecibo;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public Lote toLote(String usuario) {
        Date date = new Date();

        Lote lote = new Lote();
        lote.setId(idLote);
        lote.setTipoDocumentoFiscal(tipoDocumentoFiscal);
        lote.setTipoEmissao(tipoEmissao.longValue());
        lote.setIdEmissorRaiz(FazemuUtils.obterRaizCNPJ(idEmissor));
        lote.setVersao(versao);
        lote.setServico(servico);
        lote.setSituacao("A"); //nasce aberto
        lote.setUsuarioReg(usuario);
        lote.setDataHoraReg(date);
        lote.setUsuario(usuario);
        lote.setDataHora(date);
        return lote;
    }

    public static ResumoLote fromLote(Lote lote, List<Long> idDocFiscalList) {
        ResumoLote resumoLote = ResumoLote.build(lote.getId());
        resumoLote.setTipoDocumentoFiscal(lote.getTipoDocumentoFiscal());
        resumoLote.setIdEmissor(1_000_000L * lote.getIdEmissorRaiz()); //base nao tem CNPJ completo; apenas a raiz
        //x.setUf(lote.getIdEstado());
        resumoLote.setTipoEmissao(lote.getTipoEmissao().intValue());
        resumoLote.setVersao(lote.getVersao());
        resumoLote.setIdDocFiscalList(idDocFiscalList);
        resumoLote.setQuantidade(idDocFiscalList.size());
        resumoLote.setTamanho(1_000_000); //LOTE RECONSTRUIDO TEM TAMANHO ARBITRARIAMENTE GRANDE
        resumoLote.setDataAbertura(lote.getDataHoraReg());
        resumoLote.setDataUltimaAlteracao(lote.getDataHora());
        resumoLote.setServico(lote.getServico());

        Long recibo = lote.getReciboAutorizacao();
        if (recibo != null) {
            resumoLote.setRecibo(recibo.toString());
        }

        resumoLote.setDataUltimaConsultaRecibo(lote.getDataUltimaConsulta());
        return resumoLote;
    }
}

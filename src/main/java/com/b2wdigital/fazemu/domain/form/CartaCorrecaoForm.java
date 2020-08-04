package com.b2wdigital.fazemu.domain.form;

/**
 *
 * @author dailton.almeida
 */
public class CartaCorrecaoForm {

    private String chaveAcesso;
    private String xCorrecao;
    private String usuario;
    private String mensagemRetorno;

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public String getxCorrecao() {
        return xCorrecao;
    }

    public void setxCorrecao(String xCorrecao) {
        this.xCorrecao = xCorrecao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensagemRetorno() {
        return mensagemRetorno;
    }

    public void setMensagemRetorno(String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }
}

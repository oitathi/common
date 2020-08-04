package com.b2wdigital.fazemu.domain.form;

public class ImpressaoNFeForm {

    private String chaveAcesso;
    private String impressora;

    public static ImpressaoNFeForm build(String chaveAcesso, String impressora) {
        ImpressaoNFeForm result = new ImpressaoNFeForm();
        result.chaveAcesso = chaveAcesso;
        result.impressora = impressora;
        return result;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public String getImpressora() {
        return impressora;
    }

    public void setImpressora(String impressora) {
        this.impressora = impressora;
    }
}

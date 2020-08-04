package com.b2wdigital.fazemu.domain.form;

import com.b2wdigital.fazemu.enumeration.RecepcaoEventoEnum;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class DocumentoFiscalForm {

    @NotBlank(message = "{chaveDeAcesso.not.empty}")
    private String chaveAcesso;

    @NotNull(message = "{destinatario.not.empty}")
    private Long idDestinatario;

    @NotBlank(message = "{usuario.not.empty}")
    private String usuario;

    private List<String> retorno = new ArrayList<>();

    private Long idDocumentoFiscal;

    private boolean success;

    private Long id;
    private Long idEmissor;
    private Long numeroDocumentoFiscal;
    private Long serieDocumentoFiscal;
    private Long tipoEmissao;
    private String manifestado;
    private String situacaoAutorizador;
    private String situacaoDocumento;
    private String dataHoraRegStr;
    private String dataHoraManifestacaoStr;

    public DocumentoFiscalForm(@NotBlank(message = "{chaveDeAcesso.not.empty}") String chaveDeAcesso,
            @NotNull(message = "{idDestinatario.not.empty}") String idDestinatario,
            @NotBlank(message = "{usuario.not.empty}") String usuario) {

        this.chaveAcesso = chaveDeAcesso;
        this.idDestinatario = Long.parseLong(idDestinatario);
        this.usuario = usuario;
    }

    public DocumentoFiscalForm() {

    }

    public static String[] getDocumentoFiscalHeader() {
        String[] arr = new String[9];
        arr[0] = "#";
        arr[1] = "Emissor";
        arr[2] = "Destinatario";
        arr[3] = "Num. Nota";
        arr[4] = "Serie";
        arr[5] = "Chave Acesso";
        arr[6] = "Tipo Manifestacao";
        arr[7] = "Data Hora Registro";
        arr[8] = "Data Hora Manifestacao";
        return arr;
    }

    public String[] formToArr() {
        String[] arr = new String[9];
        try {
            String tipoEvento = null;
            if ("N".equals(this.getManifestado())) {
                tipoEvento = "Nao Manifestado";
            } else {
                tipoEvento = RecepcaoEventoEnum.getByCodigoEvento(Integer.valueOf(this.getManifestado())).getDescricao();
            }

            arr[0] = "'" + String.valueOf(this.getId());
            arr[1] = "'" + String.valueOf(this.getIdEmissor());
            arr[2] = "'" + String.valueOf(this.getIdDestinatario());
            arr[3] = "'" + String.valueOf(this.getNumeroDocumentoFiscal());
            arr[4] = String.valueOf(this.getSerieDocumentoFiscal());
            arr[5] = "'" + this.getChaveAcesso();
            arr[6] = tipoEvento;
            arr[7] = this.getDataHoraRegStr();
            arr[8] = this.getDataHoraManifestacaoStr();
        } finally {
            return arr;
        }
    }

}

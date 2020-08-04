package com.b2wdigital.fazemu.domain;

import java.util.Date;

import com.b2wdigital.fazemu.utils.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
public class DocumentoEpec {

    private Long idDocumentoFiscal;
    private Long idEstado;
    private String tipoDocumentoFiscal;
    private String situacao;
    private String observacao;
    private String usuarioReg;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHoraReg;
    private Date dataHoraRegFim;
    private String usuario;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date dataHora;

    public static DocumentoEpec build(Long idDocumentoFiscal, Long idEstado, String tipoDocumentoFiscal, String situacao, String observacao, String usuario) {
        DocumentoEpec result = new DocumentoEpec();
        result.setIdDocumentoFiscal(idDocumentoFiscal);
        result.setIdEstado(idEstado);
        result.setTipoDocumentoFiscal(tipoDocumentoFiscal);
        result.setSituacao(situacao);
        result.setObservacao(observacao);
        result.setUsuarioReg(usuario);
        result.setUsuario(usuario);
        return result;
    }
}

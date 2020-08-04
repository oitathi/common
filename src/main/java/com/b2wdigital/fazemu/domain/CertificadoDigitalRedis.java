package com.b2wdigital.fazemu.domain;

import java.io.Serializable;

public class CertificadoDigitalRedis implements Serializable {

    private static final long serialVersionUID = 3052044844694861160L;

    private byte[] certificadoDigitalByte;
    private String senha;

    public static CertificadoDigitalRedis build(byte[] certificadoDigitalByte, String senha) {
        CertificadoDigitalRedis certificadoRedis = new CertificadoDigitalRedis();
        certificadoRedis.setCertificadoDigitalByte(certificadoDigitalByte);
        certificadoRedis.setSenha(senha);
        return certificadoRedis;
    }

    public byte[] getCertificadoDigitalByte() {
        return certificadoDigitalByte;
    }

    public void setCertificadoDigitalByte(byte[] certificadoDigitalByte) {
        this.certificadoDigitalByte = certificadoDigitalByte;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}

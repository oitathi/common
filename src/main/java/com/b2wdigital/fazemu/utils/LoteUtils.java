package com.b2wdigital.fazemu.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LoteUtils {

    public static final String LOTE_LITERAL = "LOTE";
    public static final String CONSULTA_LOTE_LITERAL = "CONSULTA_LOTE";

    public static String getLoteIdRedis(Long idLote) throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName()
                .concat("_")
                .concat(LOTE_LITERAL)
                .concat("_")
                .concat(String.valueOf(idLote));
    }

    public static String getConsultaLoteKey() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName().concat("_").concat(CONSULTA_LOTE_LITERAL);
    }

    public static String getMyHost() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostName();
    }
}

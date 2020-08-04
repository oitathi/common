package com.b2wdigital.fazemu.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.text.MaskFormatter;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import com.b2wdigital.assinatura_digital.AssinaturaDigital;
import com.b2wdigital.assinatura_digital.CertificadoDigital;
import com.b2wdigital.assinatura_digital.exception.AssinaturaDigitalException;
import com.b2wdigital.fazemu.enumeration.ServicosEnum;
import java.text.DecimalFormatSymbols;

/**
 * Fazemu Utils.
 *
 * @author Thiago Di Santi {thiago.santi@b2wdigital.com}
 * @version 1.0
 */
public class FazemuUtils {

    public static final Locale LOCALE = new Locale("pt", "BR");

    public static final String NAMESPACE_URI = "http://www.portalfiscal.inf.br/nfe";
    public static final String TIPO_DOCUMENTO_FISCAL_NFE = "NFe";
    public static final String TIPO_DOCUMENTO_FISCAL_NFSE = "NFSe";

    public static final String APLICACAO = "B2W Fazemu - 1.0";
    public static final String ERROR_CODE = "999";

    public static final String PREFIX = "FZM";

    // NFe
    public static final String APLICACAO_NFE = "B2W Fazemu-NFe - 1.0";
    public static final String CODE_001_NFE_ACCEPTED = "001";
    public static final String MSG_001_NFE_ACCEPTED = "Sucesso - NFe acatada pelo " + APLICACAO_NFE;
    public static final String CODE_002_CANCELAMENTO_ACCEPTED = "002";
    public static final String MSG_002_CANCELAMENTO_ACCEPTED = "Sucesso - Cancelamento de NFe acatado pelo " + APLICACAO_NFE;
    public static final String CODE_003_CARTA_CORRECAO_ACCEPTED = "003";
    public static final String MSG_003_CARTA_CORRECAO_ACCEPTED = "Sucesso - Carta de Correção Enviada";
    public static final String CODE_004_MANIFESTACAO_ACCEPTED = "004";
    public static final String MSG_004_MANIFESTACAO_ACCEPTED = "Sucesso - Manifestação de NFe acatada pelo " + APLICACAO_NFE;
    public static final String CODE_005_EPEC_ACCEPTED = "005";
    public static final String MSG_005_EPEC_ACCEPTED = "Sucesso - Epec de NFe acatada pelo " + APLICACAO_NFE;
    public static final String CODE_006_INUTILIZACAO_ACCEPTED = "006";
    public static final String MSG_006_INUTILIZACAO_ACCEPTED = "Sucesso - Inutilizacao de NFe acatada pelo " + APLICACAO_NFE;

    // NFSe
    public static final String APLICACAO_NFSE = "B2W Fazemu-NFSe - 1.0";
    public static final String CODE_001_EMISSAO_NFSE_ACCEPTED = "001";
    public static final String MSG_001_EMISSAO_NFSE_ACCEPTED = "Sucesso - NFSe acatada pelo " + APLICACAO_NFSE;
    public static final String CODE_002_CANCELAMENTO_NFSE_ACCEPTED = "002";
    public static final String MSG_002_CANCELAMENTO_NFSE_ACCEPTED = "Sucesso - Cancelamento de NFSe acatado pelo " + APLICACAO_NFSE;

    public static final String MASCARA_CEP = "#####-###";
    public static final String MASCARA_TELEFONE = "(##) ####-####";

//    public static final String FAZEMU_STATUS = "FAZEMU_STATUS";
    public static final String FAZEMU_NFE_STATUS = "FAZEMU_NFE_STATUS";
    public static final String FAZEMU_NFSE_STATUS = "FAZEMU_NFSE_STATUS";

    /**
     * Obter Raiz CNPJ
     *
     * @param cnpj
     * @return
     */
    public static long obterRaizCNPJ(Long cnpj) { //12345678000102 -> 12345678
        return cnpj / 1_000_000; //4 + 2 = 6 zeros
    }

    public static long deltaInSeconds(Date d2, Date d1) {
        return (d2.getTime() - d1.getTime()) / 1000L;
    }

    public static String maskNFeNumber(String nnf) {
        String s = StringUtils.leftPad(nnf, 9, '0');
        return String.format("%3s.%3s.%3s", s.substring(0, 3), s.substring(3, 6), s.subSequence(6, 9));
    }

    public static String maskCPF(String cpf) {
        String s = StringUtils.leftPad(cpf, 11, '0');
        return String.format("%3s.%3s.%3s-%2s", s.substring(0, 3), s.substring(3, 6), s.substring(6, 9), s.substring(9, 11));
    }

    public static String maskCNPJ(String cnpj) {
        String s = StringUtils.leftPad(cnpj, 14, '0');
        return String.format("%2s.%3s.%3s/%4s-%2s", s.substring(0, 2), s.substring(2, 5), s.substring(5, 8), s.substring(8, 12), s.substring(12, 14));
    }

    public static String maskCNPJorCPF(String cnpj, String cpf) {
        if (StringUtils.isNotBlank(cnpj)) {
            return FazemuUtils.maskCNPJ(cnpj);
        } else if (StringUtils.isNotBlank(cpf)) {
            return FazemuUtils.maskCPF(cpf);
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * Retira prefixo NFe ou NFE se existir; se nao existir tal prefixo nao
     * altera
     *
     * @param chaveAcesso
     * @return
     */
    public static String normalizarChaveAcesso(String chaveAcesso) {
        return StringUtils.startsWithIgnoreCase(chaveAcesso, FazemuUtils.TIPO_DOCUMENTO_FISCAL_NFE)
                ? chaveAcesso.substring(FazemuUtils.TIPO_DOCUMENTO_FISCAL_NFE.length()) : chaveAcesso;
    }

    /**
     * Assinatura Digital
     *
     * @param document
     * @param certificado
     * @param servico
     * @throws AssinaturaDigitalException
     * @throws TransformerException
     */
    public static void signXml(Document document, CertificadoDigital certificado, ServicosEnum servico) throws AssinaturaDigitalException, TransformerException {

        if (ServicosEnum.AUTORIZACAO_NFE.equals(servico)) {
            AssinaturaDigital.assinarNFe(document, certificado);

        } else if (ServicosEnum.RECEPCAO_EVENTO_CANCELAMENTO.equals(servico)
                || ServicosEnum.RECEPCAO_EVENTO_CARTA_CORRECAO.equals(servico)
                || ServicosEnum.RECEPCAO_EVENTO_MANIFESTACAO.equals(servico)
                || ServicosEnum.RECEPCAO_EVENTO_EPEC.equals(servico)) {
            AssinaturaDigital.assinarRecepcaoEvento(document, certificado);

        } else if (ServicosEnum.INUTILIZACAO.equals(servico)) {
            AssinaturaDigital.assinarInutilizacao(document, certificado);
        }

    }

    public static Integer getFullYearFormat(Integer yearTwoDigits) {
        if (String.valueOf(yearTwoDigits).length() > 2) {
            return null;
        }

        String str = String.valueOf(Calendar.getInstance().get(Calendar.YEAR)).substring(0, 2) + yearTwoDigits;
        return Integer.valueOf(str);
    }

    public static String formatMoney(String valor) {
        try {
            Locale locale = new Locale("pt", "BR");
            String pattern = "###,###.##";

            DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(locale);
            df.setParseBigDecimal(true);
            df.applyPattern(pattern);

            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            return df.format(Double.valueOf(valor));
        } catch (NumberFormatException e) {
            return valor;
        }
    }

    public static String formatString(String valor, String pattern) {
        try {
            MaskFormatter mf = new MaskFormatter(pattern);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(valor);
        } catch (ParseException ex) {
            return valor;
        }
    }

    public static String formatDecimal(String valor) {
        try {

            DecimalFormat df = new DecimalFormat("#.##");
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setDecimalSeparator('.');
            df.setDecimalFormatSymbols(dfs);
            df.setParseBigDecimal(true);

            df.setMaximumFractionDigits(2);
            df.setMinimumFractionDigits(2);
            return df.format(Double.valueOf(valor));
        } catch (NumberFormatException e) {
            return valor;
        }
    }

}

package com.b2wdigital.fazemu.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.b2wdigital.fazemu.domain.EmissorRaizCertificadoDigital;

/**
 * @author dailton.almeida
 */
public class CertificadoUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CertificadoUtils.class);

    public static void main(String[] args) {
        for (int i = 0, n = args.length; i < n; i += 2) {
            try {
//				X509Certificate cert = X509Certificate.getInstance(new FileInputStream(s));
                KeyStore ks = KeyStore.getInstance("PKCS12");
                String filename = args[i];
                char[] password = args[i + 1].toCharArray();
                ks.load(new FileInputStream(filename), password);

//				PrivateKey privateKey = null;
                KeyStore.PrivateKeyEntry privateKeyEntry = null;
                Enumeration<String> aliasesEnum = ks.aliases();
                while (aliasesEnum.hasMoreElements()) {
                    String alias = aliasesEnum.nextElement();
                    if (ks.isKeyEntry(alias)) {
                        privateKeyEntry = (KeyStore.PrivateKeyEntry) ks.getEntry(alias, new KeyStore.PasswordProtection(password));
                        PrivateKey privateKey = privateKeyEntry.getPrivateKey();
                        System.out.println(privateKey);
                        break;
                    }

                    Date certExpiryDate = ((X509Certificate) ks.getCertificate(alias)).getNotAfter();
                    System.out.println(certExpiryDate);
                }

                X509Certificate cert = (X509Certificate) privateKeyEntry.getCertificate();

                cert.checkValidity();
                System.out.println("Arquivo '" + filename + "' ok!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getExpirationDate(EmissorRaizCertificadoDigital emissorRaizCertificadoDigital) {
        try {
            LOGGER.debug("getExpirationDate {} ", emissorRaizCertificadoDigital.getIdEmissorRaiz());

            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(new ByteArrayInputStream(emissorRaizCertificadoDigital.getCertificadoBytes()), emissorRaizCertificadoDigital.getSenha().toCharArray());

            Enumeration<String> aliasesEnum = ks.aliases();
            while (aliasesEnum.hasMoreElements()) {
                String alias = aliasesEnum.nextElement();
                Date certExpiryDate = ((X509Certificate) ks.getCertificate(alias)).getNotAfter();
                return DateUtils.convertDateToString(certExpiryDate);
            }

        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (CertificateException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (KeyStoreException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return null;
    }

}

package com.b2wdigital.fazemu.net;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.SocketFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.b2wdigital.fazemu.exception.InstanciacaoSocketFactoryException;

/**
 *
 * @author Rodolpho Picolo <rodolpho.picolo@b2wdigital.com>
 */
public class CustomCertSSLSocketFactory extends SSLSocketFactory {

    public static enum KeyStoreType {
        PKCS12, JKS;
    }

    private final SSLSocketFactory sslSocketFactory;
    private final String proxyHost;
    private final Integer proxyPort;

    /**
     * O proxy não funciona 100%, mas deixo dessa forma para se ter uma idéia de
     * como implementar quando houver necessidade.
     *
     * @param keyStore
     * @param keyStorePassword
     * @param trustStore
     * @param proxyHost
     * @param proxyPort
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    /*
    private static CustomCertSSLSocketFactory getInstance(KeyStore keyStore, char[] keyStorePassword, KeyStore trustStore, char[] trustStorePassword, String proxyHost, Integer proxyPort) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException{
	SSLSocketFactory socketFactory = createSSLSocketFactory(keyStore, keyStorePassword, trustStore);
	CustomCertSSLSocketFactory customCertSSLSocketFactory = new CustomCertSSLSocketFactory(socketFactory, proxyHost, proxyPort);
	return customCertSSLSocketFactory;
    }*/
    public static CustomCertSSLSocketFactory getInstance(KeyStore keyStore, char[] keyStorePassword, KeyStore trustStore) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        SSLSocketFactory socketFactory = createSSLSocketFactory(keyStore, keyStorePassword, trustStore);
        CustomCertSSLSocketFactory customCertSSLSocketFactory = new CustomCertSSLSocketFactory(socketFactory, null, null);
        return customCertSSLSocketFactory;
    }

    /*
        Does not validate certificate.
     */
    public static CustomCertSSLSocketFactory getNaiveInstance(KeyStore keyStore, char[] keyStorePassword) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        SSLSocketFactory socketFactory = createSSLSocketFactory(keyStore, keyStorePassword);
        CustomCertSSLSocketFactory customCertSSLSocketFactory = new CustomCertSSLSocketFactory(socketFactory, null, null);
        return customCertSSLSocketFactory;
    }

    public static CustomCertSSLSocketFactory getInstance(byte[] keyStoreBytes, char[] keyStorePassword, byte[] trustStoreBytes, char[] trustStorePassword) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException, FileNotFoundException, IOException, CertificateException {
        KeyStore keyStore = createKeyStore(keyStoreBytes, KeyStoreType.PKCS12, keyStorePassword);
        KeyStore trustStore = createKeyStore(trustStoreBytes, KeyStoreType.PKCS12, trustStorePassword);
        return CustomCertSSLSocketFactory.getInstance(keyStore, keyStorePassword, trustStore);
    }

    public static CustomCertSSLSocketFactory getInstance(byte[] keyStoreBytes, char[] keyStorePassword, KeyStore trustStore) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException, FileNotFoundException, IOException, CertificateException {
        KeyStore keyStore = createKeyStore(keyStoreBytes, KeyStoreType.PKCS12, keyStorePassword);
        return CustomCertSSLSocketFactory.getInstance(keyStore, keyStorePassword, trustStore);
    }

    private CustomCertSSLSocketFactory(SSLSocketFactory sslSocketFactory, String proxyHost, Integer proxyPort) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.sslSocketFactory = sslSocketFactory;
    }

    public static KeyStore createKeyStore(byte[] keyStoreBytes, KeyStoreType type, char[] password) throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        InputStream is = new ByteArrayInputStream(keyStoreBytes);
        KeyStore keyStore = createKeyStore(is, type, password);
        return keyStore;
    }

    public static KeyStore createKeyStore(InputStream keyStoreInputStream, KeyStoreType type, char[] password) throws FileNotFoundException, KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        KeyStore keyStore = KeyStore.getInstance((type.toString()));
        try {
            keyStore.load(keyStoreInputStream, password);
        } finally {
            if (keyStoreInputStream != null) {
                keyStoreInputStream.close();
            }
        }
        return keyStore;
    }

    private static SSLSocketFactory createSSLSocketFactory(KeyStore keyStore, char[] keyStorePassword, KeyStore trustStore) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        return createSSLSocketFactory(keyStore, keyStorePassword, trustManagers);
    }

    private static SSLSocketFactory createSSLSocketFactory(KeyStore keyStore, char[] keyStorePassword) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                System.out.println("getAcceptedIssuers");
                return new java.security.cert.X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                System.out.println("checkClientTrusted");
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                System.out.println("checkServerTrusted");
            }
        }
        };

        return createSSLSocketFactory(keyStore, keyStorePassword, trustAllCerts);
    }

    private static SSLSocketFactory createSSLSocketFactory(KeyStore keyStore, char[] keyStorePassword, TrustManager[] trustManagers) throws NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePassword);
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

        SecureRandom secureRandom = new SecureRandom();

        SSLContext sslContext = SSLContext.getInstance("SSL");
        //SSLContext sslContext = SSLContext.getInstance("TLS");

        //sslContext.init(keyManagers, trustManagers, secureRandom);
        sslContext.init(keyManagers, trustManagers, secureRandom);

        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        //System.out.println("SSLContext class: " + sslContext.getClass().getCanonicalName());
        return sslSocketFactory;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return sslSocketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return sslSocketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket() throws IOException {
        /*
        SocketAddress socketAddress = InetSocketAddress.createUnresolved(proxyHost, proxyPort);
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, socketAddress);
        Socket socket = new Socket(proxy);
        return socket;
         */
        return sslSocketFactory.createSocket();
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return this.createSocket(null, host.getHostName(), port, true);
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return this.createSocket(null, host, port, true);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
        return this.createSocket(null, host, port, true);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return this.createSocket(null, address.getHostName(), port, true);
        //return sslSocketFactory.createSocket(address, port, localAddress, localPort);
    }

    @Override
    public Socket createSocket(Socket proxySocket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
        /*
         * TODO: Ainda não consegui fazer isso funcionar. 
         * Só funciona com o proxy especificado no System Properties,
         * que chega aqui através do parâmetro proxySocket.
         *
         * Se o proxy não é informado no System Properties,
         * este método não é chamado, o chamado passa a ser createSocket(), sem parâmetros.
         */

        SocketFactory proxySocketFactory = SocketFactory.getDefault();
        Socket tunnel = proxySocketFactory.createSocket(this.proxyHost, this.proxyPort);
        tunnel = new Socket(this.proxyHost, this.proxyPort);

        Socket sslSocket = sslSocketFactory.createSocket(proxySocket, host, port, autoClose);
        return sslSocket;
    }

    public static SSLSocketFactory getInstance(File keystoreFile, char[] keystorePassword, File truststoreFile, char[] truststorePassword) throws InstanciacaoSocketFactoryException {
        FileInputStream keystoreInputStream = null;
        try {
            keystoreInputStream = new FileInputStream(keystoreFile);
            FileInputStream truststoreInputStream = new FileInputStream(truststoreFile);
            SSLSocketFactory sslSocketFactory = getInstance(keystoreInputStream, keystorePassword, truststoreInputStream, truststorePassword);
            return sslSocketFactory;
        } catch (FileNotFoundException ex) {
            throw new InstanciacaoSocketFactoryException(ex);
        } finally {
            try {
                keystoreInputStream.close();
            } catch (IOException ex) {
                throw new InstanciacaoSocketFactoryException(ex);
            }
        }
    }

    public static SSLSocketFactory getInstance(InputStream keystoreInputStream, char[] keystorePassword, InputStream truststoreInputStream, char[] truststorePassword) throws InstanciacaoSocketFactoryException {
        try {
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(keystoreInputStream, keystorePassword);
            keyManagerFactory.init(keyStore, keystorePassword);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(truststoreInputStream, truststorePassword);
            trustManagerFactory.init(trustStore);

            CustomCertSSLSocketFactory sslSocketFactory = CustomCertSSLSocketFactory.getInstance(keyStore, keystorePassword, trustStore);

            return sslSocketFactory;
        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException | UnrecoverableKeyException | KeyManagementException ex) {
            throw new InstanciacaoSocketFactoryException(ex);
        }
    }

}

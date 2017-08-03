/*
 * Copyright 2017 Samsung Pay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.samsungpay.s2p.common;

import com.samsungpay.s2p.common.model.I18n;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMDecryptorProvider;
import org.bouncycastle.openssl.PEMEncryptedKeyPair;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.openssl.jcajce.JcePEMDecryptorProviderBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.List;

public final class Util {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private Util() {
    }

    public static boolean containsNullValue(List<? extends I18n> i18ns) {
        if (i18ns == null)
            return true;
        for (I18n i18n : i18ns) {
            if (i18n == null)
                return true;
        }
        return false;
    }

    public static String getBase64EncodedHash(String message) {
        byte[] binaryData = DigestUtils.sha256(message);
        return Base64.encodeBase64URLSafeString(binaryData);
    }

    public static RSAPrivateKey readPrivateKeyFromPEM(InputStream in) throws InvalidKeySpecException, IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException {
        PemObject pemObject = new PemReader(new InputStreamReader(in)).readPemObject();
        return readPrivateKeyHelper(pemObject);
    }

    public static RSAPrivateKey readPrivateKeyFromPEM(String filename) throws InvalidKeySpecException, IOException, NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException {
        PemObject pemObject = new PemReader(new FileReader(filename)).readPemObject();
        return readPrivateKeyHelper(pemObject);
    }

    private static RSAPrivateKey readPrivateKeyHelper(PemObject pemObject) throws InvalidKeySpecException, IOException, NoSuchProviderException, NoSuchAlgorithmException {
        KeyFactory factory = KeyFactory.getInstance("RSA", "BC");
        byte[] content = pemObject.getContent();
        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
        return (RSAPrivateKey) factory.generatePrivate(privKeySpec);
    }

    public static RSAPrivateKey readPrivateKeyFromPEM(InputStream in, String passphrase) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        PEMParser pemParser = new PEMParser(new InputStreamReader(in));
        return readPrivateKeyHelper(pemParser, passphrase);
    }

    public static RSAPrivateKey readPrivateKeyFromPEM(String filename, String passphrase) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        PEMParser pemParser = new PEMParser(new FileReader(filename));
        return readPrivateKeyHelper(pemParser, passphrase);
    }

    private static RSAPrivateKey readPrivateKeyHelper(PEMParser pemParser, String passphrase) throws InvalidKeySpecException, IOException, NoSuchProviderException, NoSuchAlgorithmException {
        Object keyObject = pemParser.readObject();

        PrivateKeyInfo keyInfo;
        if (keyObject instanceof PEMEncryptedKeyPair) {
            PEMDecryptorProvider decryptionProv = new JcePEMDecryptorProviderBuilder().build(passphrase.toCharArray());
            PEMKeyPair decryptedKeyPair = ((PEMEncryptedKeyPair) keyObject).decryptKeyPair(decryptionProv);
            keyInfo = decryptedKeyPair.getPrivateKeyInfo();
        } else {
            keyInfo = ((PEMKeyPair) keyObject).getPrivateKeyInfo();
        }
        pemParser.close();

        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        return (RSAPrivateKey) converter.getPrivateKey(keyInfo);
    }
}

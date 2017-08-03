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

package com.samsungpay.s2p.common.jwt.algorithm;

import com.samsungpay.s2p.common.jwt.Algorithm;

import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAKey;

public class RS256Algorithm implements Algorithm {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final String SIG_INSTANCE = "SHA256withRSA";
    private static final String TYP = "RS256";
    private final Signature privateSignature;

    public RS256Algorithm(RSAKey key) throws NoSuchAlgorithmException, InvalidKeyException {
        if (!(key instanceof PrivateKey)) {
            throw new IllegalArgumentException("The given key is not a RSAPrivateKey.");
        }
        privateSignature = Signature.getInstance(SIG_INSTANCE);
        privateSignature.initSign((PrivateKey) key);
    }

    @Override
    public String getType() {
        return TYP;
    }

    @Override
    public byte[] sign(String plainTxt) throws SignatureException {
        privateSignature.update(plainTxt.getBytes(CHARSET));
        return privateSignature.sign();
    }
}

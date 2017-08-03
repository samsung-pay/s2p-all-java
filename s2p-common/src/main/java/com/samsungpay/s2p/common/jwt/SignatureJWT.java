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

package com.samsungpay.s2p.common.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;

import static org.apache.commons.codec.CharEncoding.UTF_8;

public class SignatureJWT implements JWT {
    private static final String DELIMITER = ".";
    private final Header header;
    private final Payload payload;
    private final String encodedSource;

    public SignatureJWT(Header header, Payload payload) throws JsonProcessingException, UnsupportedEncodingException {
        this.header = header;
        this.payload = payload;
        this.encodedSource = generateEncodedSource(this);
    }

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public Payload getPayload() {
        return payload;
    }

    @Override
    public String sign(Algorithm algorithm) throws SignatureException {
        byte[] signature = algorithm.sign(encodedSource);
        return encodedSource + DELIMITER + Base64.encodeBase64URLSafeString(signature);
    }

    /**
     * Base64URI-encoded parts delimited by period ('.') characters
     *
     * @return
     */
    private static String generateEncodedSource(SignatureJWT signatureJWT) throws JsonProcessingException, UnsupportedEncodingException {
        String jsonHeader = asJson(signatureJWT.getHeader());
        String jsonPayload = asJson(signatureJWT.getPayload());

        return Base64.encodeBase64URLSafeString(jsonHeader.getBytes(UTF_8)) + DELIMITER + Base64.encodeBase64URLSafeString(jsonPayload.getBytes(UTF_8));
    }

    private static String asJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}

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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsungpay.s2p.common.jwt.*;
import com.samsungpay.s2p.common.jwt.algorithm.RS256Algorithm;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;

import static org.apache.commons.codec.CharEncoding.UTF_8;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, creatorVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class S2PRequest {
    public enum Request {
        SAVE_TO_SPAY_MEMBERSHIP("POST", "application/json;charset=UTF-8", "/sapi/loyalty/v1/registrations"),
        SAVE_TO_SPAY_GIFTCARD("POST", "application/json;charset=UTF-8", "/sapi/giftcard/v1/registrations");

        private String type;
        private String contentType;
        private String uri;

        Request(String type, String contentType, String uri) {
            this.type = type;
            this.contentType = contentType;
            this.uri = uri;
        }

        public String getEncodedRequestHash(String bodyAsJson) {
            return Util.getBase64EncodedHash(this.type + "\n" + this.contentType + "\n" + this.uri + "\n" + bodyAsJson);
        }
    }

    private static ObjectMapper newObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }

    @JsonProperty("jwt")
    private String jwt;

    @JsonProperty("reg")
    private String reg;

    @JsonProperty("uri")
    private String uri;

    private S2PRequest() {
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String keyID;
        private AbstractRegistration registration;
        private Request request;
        private String keyPEMPath;
        private String passphrase;

        private Builder() {
        }

        public Builder setKeyID(String keyID) {
            this.keyID = keyID;
            return this;
        }

        public Builder setRegistration(AbstractRegistration registration) {
            this.registration = registration;
            return this;
        }

        public Builder setRequest(Request request) {
            this.request = request;
            return this;
        }

        public Builder setKeyPEMPath(String keyPEMPath) {
            this.keyPEMPath = keyPEMPath;
            return this;
        }

        public Builder setPassphrase(String passphrase) {
            this.passphrase = passphrase;
            return this;
        }

        public S2PRequest sign() throws IllegalArgumentException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidKeySpecException, NoSuchProviderException {
            return sign(retrieveKey());
        }

        public S2PRequest sign(RSAPrivateKey privateKey) throws IllegalArgumentException, SignatureException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException, UnsupportedEncodingException {
            if (privateKey == null)
                throw new IllegalArgumentException("RSAPrivateKey is missing");
            if (keyID == null || keyID.isEmpty())
                throw new IllegalArgumentException("S2PRequest keyID is missing");
            if (registration == null)
                throw new IllegalArgumentException("S2PRequest registration object is missing");
            if (request == null)
                throw new IllegalArgumentException("S2PRequest request enum is missing");

            S2PRequest s2PRequest = new S2PRequest();
            s2PRequest.jwt = signJWT(privateKey);
            s2PRequest.reg = registration.toJson();
            s2PRequest.uri = request.uri;
            return s2PRequest;
        }

        private RSAPrivateKey retrieveKey() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchProviderException, IOException {
            if (keyPEMPath == null || keyPEMPath.isEmpty())
                throw new IllegalArgumentException("S2PRequest keyPEMPath is missing");
            return passphrase == null || passphrase.isEmpty() ? Util.readPrivateKeyFromPEM(keyPEMPath) : Util.readPrivateKeyFromPEM(keyPEMPath, passphrase);
        }

        private String signJWT(RSAPrivateKey privateKey) throws InvalidKeyException, NoSuchAlgorithmException, JsonProcessingException, UnsupportedEncodingException, SignatureException {
            Algorithm algorithm = new RS256Algorithm(privateKey);
            Header header = Header.newBuilder()
                    .keyId(keyID)
                    .build();
            Payload payload = Payload.newBuilder()
                    .jwtId(request.getEncodedRequestHash(registration.toJson()))
                    .build();
            JWT jwt = new SignatureJWT(header, payload);
            return jwt.sign(algorithm);
        }
    }

    public String encode() throws JsonProcessingException, UnsupportedEncodingException {
        final String asJson = newObjectMapper().writeValueAsString(this);
        return Base64.encodeBase64URLSafeString(asJson.getBytes(UTF_8));
    }
}

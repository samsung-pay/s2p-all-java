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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class AbstractRegistration {
    public static ObjectMapper newObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper;
    }

    public String toJson() throws JsonProcessingException {
        return newObjectMapper().writeValueAsString(this);
    }

    public <T extends AbstractRegistration> T readJson(String json, Class<T> valueType) throws IOException {
        return newObjectMapper().readValue(json, valueType);
    }

    public String base64Encoded() throws UnsupportedEncodingException, JsonProcessingException {
        String json = toJson();
        return Base64.encodeBase64URLSafeString(json.getBytes("UTF-8"));
    }
}

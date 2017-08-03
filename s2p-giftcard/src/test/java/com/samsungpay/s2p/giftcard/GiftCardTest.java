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

package com.samsungpay.s2p.giftcard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.samsungpay.s2p.common.S2PRequest;
import com.samsungpay.s2p.common.Util;
import com.samsungpay.s2p.giftcard.model.Card;
import com.samsungpay.s2p.giftcard.model.Merchant;
import com.samsungpay.s2p.giftcard.model.Tnc;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class GiftCardTest {
    private static final String PRIVATE_KEY_PEM_PATH = "src/test/resources/secret/dummyKey.pem";
    private static RSAPrivateKey PRIVATE_KEY;

    @Before
    public void setUp() {
        RSAPrivateKey temp = null;
        try {
            temp = Util.readPrivateKeyFromPEM(PRIVATE_KEY_PEM_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PRIVATE_KEY = temp;
    }

    @After
    public void tearDown() {
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void verifyTncAtLeaseOneFieldRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Terms and Conditions' content and url are both missing, at least one of them has to be provided");

        Tnc.newBuilder()
                .build();
    }

    @Test
    public void verifyCardIDRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Card id is missing");

        Tnc tnc = Tnc.newBuilder()
                .url("http://us.playstation.com/")
                .build();

        Card.newBuilder()
                .imageUrl("123")
                .tnc(tnc)
                .build();
    }

    @Test
    public void verifyCardImageUrlRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Card image url is missing");

        Tnc tnc = Tnc.newBuilder()
                .url("http://us.playstation.com/")
                .build();

        Card.newBuilder()
                .id("123")
                .tnc(tnc)
                .build();
    }

    @Test
    public void verifyCardTncRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Card terms and conditions is missing");

        Card.newBuilder()
                .id("123")
                .imageUrl("123")
                .build();
    }

    @Test
    public void verifyMerchantNameRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Merchant name is missing");

        Merchant.newBuilder()
                .logoUrl("123")
                .build();
    }

    @Test
    public void verifyMerchantLogoUrlRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Merchant logo url is missing");

        Merchant.newBuilder()
                .name("123")
                .build();
    }

    @Test
    public void verifyRegistrationCardRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Registration card is missing");

        Merchant merchant = Merchant.newBuilder()
                .name("123")
                .logoUrl("123")
                .build();

        Registration.newBuilder()
                .merchant(merchant)
                .build();
    }

    @Test
    public void verifyRegistrationMerchantRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Registration merchant is missing");

        Tnc tnc = Tnc.newBuilder()
                .url("123")
                .build();

        Card card = Card.newBuilder()
                .id("123")
                .imageUrl("!23")
                .tnc(tnc)
                .build();

        Registration.newBuilder()
                .card(card)
                .build();
    }

    @Test
    public void verifyS2PRequestKeyIDRequired() throws NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, InvalidKeyException, JsonProcessingException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("S2PRequest keyID is missing");

        S2PRequest.newBuilder()
                .setRegistration(constructRegistrationObject())
                .setRequest(S2PRequest.Request.SAVE_TO_SPAY_GIFTCARD)
                .sign(PRIVATE_KEY);
    }

    @Test
    public void verifyS2PRequestRegistrationObjRequired() throws NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, InvalidKeyException, JsonProcessingException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("S2PRequest registration object is missing");

        S2PRequest.newBuilder()
                .setKeyID("keyID")
                .setRequest(S2PRequest.Request.SAVE_TO_SPAY_GIFTCARD)
                .sign(PRIVATE_KEY);
    }

    @Test
    public void readPrivateKeyFromPEM() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchProviderException, IOException {
        RSAPrivateKey privateKey = Util.readPrivateKeyFromPEM(PRIVATE_KEY_PEM_PATH);
        assertNotNull(privateKey);
        assertThat(privateKey, instanceOf(RSAPrivateKey.class));
    }

    @Test
    public void createRegistrationObject() {
        final String cardID = "123456677788";
        final String merchantName = "Sony PlayStation";
        final String merchantLogo = "https://media.playstation.com/is/image/SCEA/nav-icon-lg-store-07jun17?$ExploreNav_VisualRow$";
        final String artUrl = "http://www.gamestop.com/common/images/lbox/550002b1.jpg";
        final String tncLink = "http://us.playstation.com/";

        Tnc tnc = Tnc.newBuilder()
                .url(tncLink)
                .build();

        Card card = Card.newBuilder()
                .id(cardID)
                .imageUrl(artUrl)
                .tnc(tnc)
                .build();

        Merchant merchant = Merchant.newBuilder()
                .name(merchantName)
                .logoUrl(merchantLogo)
                .build();

        Registration registration = Registration.newBuilder()
                .card(card)
                .merchant(merchant)
                .build();

        assertNotNull(registration);
        Assert.assertNotNull(registration.getCard());
        Assert.assertEquals(cardID, registration.getCard().getId());
        Assert.assertEquals(artUrl, registration.getCard().getImageUrl());
        Assert.assertNotNull(registration.getCard().getTnc());
        Assert.assertEquals(tncLink, registration.getCard().getTnc().getUrl());
        Assert.assertNotNull(registration.getMerchant());
        Assert.assertEquals(merchantName, registration.getMerchant().getName());
        Assert.assertEquals(merchantLogo, registration.getMerchant().getLogoUrl());
    }

    @Test
    public void testRS256JWTCreateAndSign() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, ParseException, IOException, InvalidKeySpecException, NoSuchProviderException {
        // Upon successful registration, each merchant will obtain a keyId and appId of registered service
        final String keyID = "1D2BE69FF72F4C25AC89B894689C2F1E";
        final S2PRequest.Request request = S2PRequest.Request.SAVE_TO_SPAY_GIFTCARD;

        // Construct a registration object
        Registration registration = constructRegistrationObject();

        // Create the model that will be placed in view
        // Either provide (RSAPrivateKey) privateKey or file path of the private key PEM to sign the request
        S2PRequest req = S2PRequest.newBuilder()
                .setKeyID(keyID)
                .setRegistration(registration)
                .setRequest(request)
                .setKeyPEMPath(PRIVATE_KEY_PEM_PATH)
                .sign();
    }

    private Registration constructRegistrationObject() {
        Tnc tnc = Tnc.newBuilder()
                .url("http://us.playstation.com/")
                .build();

        Card card = Card.newBuilder()
                .id("11223344556677")
                .imageUrl("http://www.gamestop.com/common/images/lbox/550002b1.jpg")
                .tnc(tnc)
                .build();

        Merchant merchant = Merchant.newBuilder()
                .name("Sony PlayStation")
                .logoUrl("https://media.playstation.com/is/image/SCEA/nav-icon-lg-store-07jun17?$ExploreNav_VisualRow$")
                .build();

        return Registration.newBuilder()
                .card(card)
                .merchant(merchant)
                .build();
    }
}

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

package com.samsungpay.s2p.membership;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.samsungpay.s2p.common.S2PRequest;
import com.samsungpay.s2p.common.Util;
import com.samsungpay.s2p.membership.model.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class MembershipTest {
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
    public void verifyBarcodeSymbologyRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Barcode symbology is missing");

        Barcode.newBuilder()
                .setData("1234567890")
                .build();
    }

    @Test
    public void verifyBarcodeDataRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Barcode data is missing");

        Barcode.newBuilder()
                .setSymbology(Barcode.Symbology.CODE_39)
                .build();
    }

    @Test
    public void verifyTracksAtLeastOneTrackRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Track (track1, track2, track3) is missing");

        Tracks.newBuilder()
                .build();
    }

    @Test
    public void verifyCustomClaimI18nLabelRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("CustomClaim label is missing");

        CustomClaimI18n.newBuilder()
                .setContent("content")
                .build();
    }

    @Test
    public void verifyCustomClaimI18nContentRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("CustomClaim content is missing");

        CustomClaimI18n.newBuilder()
                .setLabel("label")
                .build();
    }

    @Test
    public void verifyCardArtI18nArtUrlRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("CardArtI18n artUrl is missing");

        CardArtI18n.newBuilder()
                .setLanguage("en")
                .build();
    }

    @Test
    public void verifyCardArtValueRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("CardArt i18ns is missing");

        CardArt.newBuilder()
                .setI18ns(null)
                .build();
    }

    @Test
    public void verifyRegistrationCardIDRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Registration card ID is missing");

        Registration.newBuilder()
                .setMembershipId("123")
                .setMembershipIDType(MembershipIDType.CARDNUM)
                .setProgramName("Holly membership program name")
                .setCardStatus(Registration.CardStatus.ACTIVE)
                .build();
    }

    @Test
    public void verifyRegistrationMembershipIDRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Registration membership ID is missing");

        Registration.newBuilder()
                .setCardId("321321")
                .setMembershipIDType(MembershipIDType.CARDNUM)
                .setProgramName("Holly membership program name")
                .setCardStatus(Registration.CardStatus.ACTIVE)
                .build();
    }

    @Test
    public void verifyRegistrationMembershipIDTypeRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Registration membership ID type is missing");

        Registration.newBuilder()
                .setCardId("321321")
                .setMembershipId("123")
                .setProgramName("Program Name")
                .build();
    }

    @Test
    public void verifyRegistrationProgramNameRequired() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Registration programName is missing");

        Registration.newBuilder()
                .setCardId("321231")
                .setMembershipId("123")
                .setMembershipIDType(MembershipIDType.CARDNUM)
                .build();
    }

    @Test
    public void verifyS2PRequestKeyIDRequired() throws NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, InvalidKeyException, JsonProcessingException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("S2PRequest keyID is missing");

        S2PRequest.newBuilder()
                .setRegistration(constructRegistrationObject())
                .setRequest(S2PRequest.Request.SAVE_TO_SPAY_MEMBERSHIP)
                .sign(PRIVATE_KEY);
    }

    @Test
    public void verifyS2PRequestRegistrationObjRequired() throws NoSuchAlgorithmException, SignatureException, UnsupportedEncodingException, InvalidKeyException, JsonProcessingException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("S2PRequest registration object is missing");

        S2PRequest.newBuilder()
                .setKeyID("keyID")
                .setRequest(S2PRequest.Request.SAVE_TO_SPAY_MEMBERSHIP)
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
        // Barcode
        Barcode.Symbology symbology = Barcode.Symbology.CODE_39;
        final String barcodeData = "1234567890";
        Barcode barcode = Barcode.newBuilder()
                .setSymbology(symbology)
                .setData(barcodeData)
                .build();
        assertNotNull(barcode);

        // Tracks
        final String track1 = "track1_format";
        final String track2 = "track2_format";
        final String track3 = "track3_format";
        Tracks tracks = Tracks.newBuilder()
                .setTrack1(track1)
                .setTrack2(track2)
                .setTrack3(track3)
                .build();
        assertNotNull(tracks);

        Registration registration = Registration.newBuilder()
                .setCardId("3213211q2w3e")
                .setMembershipId("321321")
                .setMembershipIDType(MembershipIDType.CARDNUM)
                .setProgramName("Holly membership program name")
                .setBarcode(barcode)
                .setTracks(tracks)
                .setCardStatus(Registration.CardStatus.ACTIVE)
                .build();

        assertNotNull(registration);
        Assert.assertEquals(symbology, registration.getBarcode().getSymbology());
        Assert.assertEquals(track1, registration.getTracks().getTrack1());
        Assert.assertEquals(track2, registration.getTracks().getTrack2());
        Assert.assertEquals(track3, registration.getTracks().getTrack3());
    }

    @Test
    public void testRS256JWTCreateAndSign() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, ParseException, IOException, InvalidKeySpecException, NoSuchProviderException {
        // Upon successful registration, each merchant will obtain a keyId and appId of registered service
        final String keyID = "kid";
        final S2PRequest.Request request = S2PRequest.Request.SAVE_TO_SPAY_MEMBERSHIP;

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
        // Barcode
        Barcode barcode = Barcode.newBuilder()
                .setSymbology(Barcode.Symbology.CODE_39)
                .setData("1234567890")
                .build();

        // Tracks
        Tracks tracks = Tracks.newBuilder()
                .setTrack1("track1_format")
                .setTrack2("track2_format")
                .setTrack3("track3_format")
                .build();

        // CardArt
        CardArtI18n cardArtI18n1_EN = CardArtI18n.newBuilder()
                .setLanguage("en")
                .setArtUrl("123")
                .build();

        CardArt cardArt1 = CardArt.newBuilder()
                .setI18ns(Collections.singletonList(cardArtI18n1_EN))
                .build();

        // CustomClaim
        CustomClaimI18n customClaimI18n1_EN = CustomClaimI18n.newBuilder()
                .setLanguage("en")
                .setLabel("label1_en")
                .setContent("content1_en")
                .build();

        CustomClaimI18n customClaimI18n1_FR = CustomClaimI18n.newBuilder()
                .setLanguage("fr")
                .setLabel("label1_fr")
                .setContent("content1_fr")
                .build();

        CustomClaim customClaim1 = CustomClaim.newBuilder()
                .setI18ns(Arrays.asList(customClaimI18n1_EN, customClaimI18n1_FR))
                .build();

        // UserMessageI18n
        // (startAt and endAt fields using [RFC 3339 Data and Time Format](https://www.ietf.org/rfc/rfc3339.txt))
        UserMessageI18n userMessageI18n1_EN = UserMessageI18n.newBuilder()
                .setLanguage("en")
                .setTitle("title1_en")
                .setBody("body1_en")
                .build();

        UserMessageI18n userMessageI18n1_FR = UserMessageI18n.newBuilder()
                .setLanguage("fr")
                .setTitle("title1_fr")
                .setBody("body1_fr")
                .build();

        UserMessage userMessage1 = UserMessage.newBuilder()
                .setStartAt("2017-05-19T16:38:07-07:00")
                .setEndAt("2017-09-19T16:38:07-07:00")
                .setI18ns(Arrays.asList(userMessageI18n1_EN, userMessageI18n1_FR))
                .build();

        return Registration.newBuilder()
                .setCardId("3213211q2w3e")
                .setMembershipId("1q2w3e")
                .setMembershipIDType(MembershipIDType.CARDNUM)
                .setProgramName("Holly membership program name")
                .setBarcode(barcode)
                .setTracks(tracks)
                .setCardStatus(Registration.CardStatus.ACTIVE)
                .setCardArt(cardArt1)
                .setCustomClaims(Collections.singletonList(customClaim1))
                .setUserMessages(Collections.singletonList(userMessage1))
                .build();
    }
}

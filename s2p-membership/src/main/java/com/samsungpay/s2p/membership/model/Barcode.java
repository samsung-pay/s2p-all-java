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

package com.samsungpay.s2p.membership.model;

public class Barcode {
    public enum Symbology {
        AZTEC,
        CODABAR,
        CODE_39,
        CODE_93,
        CODE_128,
        DATA_MATRIX,
        EAN_8,
        EAN_13,
        ITF,
        MAXICODE,
        PDF_417,
        QR_CODE,
        RSS_14,
        RSS_EXPANDED,
        UPC_A,
        UPC_E,
        UPC_EAN_EXTENSION
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private Symbology symbology;
    private String data;

    private Barcode() {
    }

    public Symbology getSymbology() {
        return symbology;
    }

    public String getData() {
        return data;
    }

    public static class Builder {
        private Symbology symbology;
        private String data;

        private Builder() {
        }

        public Builder setSymbology(Symbology symbology) {
            this.symbology = symbology;
            return this;
        }

        public Builder setData(String data) {
            this.data = data;
            return this;
        }

        public Barcode build() throws IllegalArgumentException {
            if (symbology == null)
                throw new IllegalArgumentException("Barcode symbology is missing");

            if (data == null || data.isEmpty())
                throw new IllegalArgumentException("Barcode data is missing");

            Barcode barcode = new Barcode();
            barcode.symbology = this.symbology;
            barcode.data = this.data;
            return barcode;
        }
    }
}

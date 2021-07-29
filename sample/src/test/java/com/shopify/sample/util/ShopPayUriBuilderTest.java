package com.shopify.sample.util;

import com.shopify.sample.domain.model.Checkout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopPayUriBuilderTest {
    @org.junit.jupiter.api.Test
    void test_getVariantSlug() {
        final Checkout.ShippingRates rates = new Checkout.ShippingRates(true, Arrays.asList());
        final Checkout.ShippingRate singleRate = new Checkout.ShippingRate("", BigDecimal.ONE, "");

        // Starting with one item, one quantity.
        List<Checkout.LineItem> oneItem =
                Arrays.asList(new Checkout.LineItem("Z2lkOi8vc2hvcGlmeS9Qcm9kdWN0VmFyaWFudC8xMjMxNTExNg", "Good stuff", 1, BigDecimal.ONE));
        Checkout oneItemCheckout = new Checkout("id", "https://store.com/checkout/12314151", "USD", true, oneItem, rates, singleRate, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        assertEquals("12315116:1", ShopPayUriBuilder.getVariantSlug(oneItemCheckout));

        // Having more items and more quantities.
        List<Checkout.LineItem> twoItems =
                Arrays.asList(
                        new Checkout.LineItem("Z2lkOi8vc2hvcGlmeS9Qcm9kdWN0VmFyaWFudC8xMjMxNTExNg", "Good stuff", 3, BigDecimal.ONE),
                        new Checkout.LineItem("Z2lkOi8vc2hvcGlmeS9Qcm9kdWN0VmFyaWFudC8xMjM0NTY3Njc", "Good stuff 2", 2, BigDecimal.ONE));
        Checkout twoItemCheckout = new Checkout("id", "https://store.com/checkout/12314151", "USD", true, twoItems, rates, singleRate, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        assertEquals("12315116:3,123456767:2", ShopPayUriBuilder.getVariantSlug(twoItemCheckout));
    }

    @org.junit.jupiter.api.Test
    void test_getDecodedVariantId() {
        assertEquals("12315116", ShopPayUriBuilder.getDecodedVariantId("Z2lkOi8vc2hvcGlmeS9Qcm9kdWN0VmFyaWFudC8xMjMxNTExNg"));
        assertThrows(java.lang.IllegalArgumentException.class, () -> ShopPayUriBuilder.getDecodedVariantId(""));
    }
}
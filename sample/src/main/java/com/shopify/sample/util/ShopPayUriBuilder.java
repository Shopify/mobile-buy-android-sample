package com.shopify.sample.util;

import android.net.Uri;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.shopify.sample.domain.model.Checkout;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ShopPayUriBuilder {
    private static final String SHOP_PAY_PAYMENT_PARAM_VALUE = "shop_pay";
    private static final String PAYMENT_PARAM_KEY = "payment";
    private static final String CART_PATH = "cart";
    private static final String HTTPS_SCHEME = "https";

    /**
     * @param checkout A valid checkout object from which to generate a Shop Pay URI.
     * @return A URI which can be used to invoke Shop Pay with the contents of the checkout.
     */
    public static Uri buildShopPayURI(@NonNull Checkout checkout) {
        String storeAuthority = Uri.parse(checkout.webUrl).getAuthority();
        Uri.Builder shopPayBuilder = new Uri.Builder()
                .scheme(ShopPayUriBuilder.HTTPS_SCHEME)
                .authority(storeAuthority)
                .appendPath(ShopPayUriBuilder.CART_PATH)
                .appendEncodedPath(getVariantSlug(checkout))
                .appendQueryParameter(ShopPayUriBuilder.PAYMENT_PARAM_KEY, ShopPayUriBuilder.SHOP_PAY_PAYMENT_PARAM_VALUE);
        return shopPayBuilder.build();
    }

    protected static String getVariantSlug(@NonNull Checkout checkout) {
        return checkout.lineItems.stream().map(item -> getDecodedVariantId(item.variantId) + ":" + item.quantity).collect(Collectors.joining(","));
    }

    protected static String getDecodedVariantId(@NonNull String variantId) {
        String fullVariantId = new String(Base64.decode(variantId, Base64.DEFAULT), StandardCharsets.UTF_8);
        String[] variantIds = fullVariantId.split("/");
        // The expected format of the decoded input is "gid://shopify/ProductVariant/12315116"
        if (variantIds.length != 5)
            throw new IllegalArgumentException("Invalid format for input");
        return variantIds[variantIds.length - 1];
    }
}


package com.shopify.sample;

import android.text.TextUtils;

import com.apollographql.apollo.ApolloClient;

import com.apollographql.apollo.api.cache.http.HttpCachePolicy;
import com.apollographql.apollo.cache.http.ApolloHttpCache;
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore;
import com.apollographql.apollo.response.CustomTypeAdapter;
import com.apollographql.apollo.response.CustomTypeValue;
import com.shopify.sample.domain.type.CustomType;
import com.shopify.sample.domain.usecases.UseCases;
import com.shopify.sample.domain.usecases.UseCasesImpl;
import com.shopify.sample.util.CallbackExecutors;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class SampleApplication extends BaseApplication {

  private static final String SHOP_PROPERTIES_INSTRUCTION =
    "\n\tAdd your shop credentials to a shop.properties file in the main app folder (e.g. 'app/shop.properties')."
      + "Include these keys:\n" + "\t\tSHOP_DOMAIN=<myshop>.myshopify.com\n"
      + "\t\tAPI_KEY=0123456789abcdefghijklmnopqrstuvw\n";

  private static ApolloClient apolloClient;

  public static ApolloClient apolloClient() {
    return apolloClient;
  }

  @Override
  protected void initialize() {
    initializeApolloClient();
  }

  @Override
  protected UseCases onCreateUseCases() {
    return new UseCasesImpl(CallbackExecutors.createDefault(), apolloClient);
  }

  private void initializeApolloClient() {
    String shopUrl = BuildConfig.SHOP_DOMAIN;
    if (TextUtils.isEmpty(shopUrl)) {
      throw new IllegalArgumentException(SHOP_PROPERTIES_INSTRUCTION + "You must add 'SHOP_DOMAIN' entry in "
        + "app/shop.properties, in the form '<myshop>.myshopify.com'");
    }

    String shopifyApiKey = BuildConfig.API_KEY;
    if (TextUtils.isEmpty(shopifyApiKey)) {
      throw new IllegalArgumentException(SHOP_PROPERTIES_INSTRUCTION + "You must populate the 'API_KEY' entry in "
        + "app/shop.properties");
    }

    OkHttpClient httpClient = new OkHttpClient.Builder()
      .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.OKHTTP_LOG_LEVEL))
      .addInterceptor(chain -> {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder().method(original.method(), original.body());
        builder.header("User-Agent", "Android Apollo Client");
        builder.header("X-Shopify-Storefront-Access-Token", shopifyApiKey);
        return chain.proceed(builder.build());
      })
      .build();

    apolloClient = ApolloClient.builder()
      .okHttpClient(httpClient)
      .serverUrl(HttpUrl.parse("https://" + shopUrl + "/api/2020-01/graphql"))
      .httpCache(new ApolloHttpCache(new DiskLruHttpCacheStore(getCacheDir(), 1000 * 1024), null))
      .defaultHttpCachePolicy(HttpCachePolicy.CACHE_FIRST.expireAfter(20, TimeUnit.MINUTES))
      .addCustomTypeAdapter(CustomType.MONEY, new CustomTypeAdapter<BigDecimal>() {
          @Override public BigDecimal decode(@NonNull CustomTypeValue value) {
              return new BigDecimal(value.value.toString());
          }

          @NonNull @Override public CustomTypeValue encode(@NonNull BigDecimal value) {
              return CustomTypeValue.fromRawValue(value);
          }
      })
      .build();
  }
}

package com.shopify.sample.domain.usecases;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shopify.sample.core.UseCase.Callback1;
import com.shopify.sample.core.UseCase.Cancelable;
import com.shopify.sample.domain.model.Product;

import java.util.List;

public interface FetchProductsUseCase {

  Cancelable execute(@NonNull String collectionId, @Nullable String cursor, int perPage, @NonNull Callback1<List<Product>> callback);
}

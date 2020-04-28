package com.shopify.sample.domain.usecases;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shopify.sample.domain.model.Collection;
import com.shopify.sample.core.UseCase;

import java.util.List;

public interface FetchCollectionsUseCase extends UseCase {

  Cancelable execute(@Nullable String cursor, int perPage, @NonNull Callback1<List<Collection>> callback);
}

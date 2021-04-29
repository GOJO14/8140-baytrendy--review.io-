package com.baytrendy.shopifyapp.ordersection.activities;

import com.baytrendy.shopifyapp.ordersection.adapters.OrderListAdapter;
import com.baytrendy.shopifyapp.utils.ViewModelFactory;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class OrderList_MembersInjector implements MembersInjector<OrderList> {
  private final Provider<ViewModelFactory> factoryProvider;

  private final Provider<OrderListAdapter> adapterProvider;

  public OrderList_MembersInjector(Provider<ViewModelFactory> factoryProvider,
      Provider<OrderListAdapter> adapterProvider) {
    this.factoryProvider = factoryProvider;
    this.adapterProvider = adapterProvider;
  }

  public static MembersInjector<OrderList> create(Provider<ViewModelFactory> factoryProvider,
      Provider<OrderListAdapter> adapterProvider) {
    return new OrderList_MembersInjector(factoryProvider, adapterProvider);}

  @Override
  public void injectMembers(OrderList instance) {
    injectFactory(instance, factoryProvider.get());
    injectAdapter(instance, adapterProvider.get());
  }

  public static void injectFactory(OrderList instance, ViewModelFactory factory) {
    instance.factory = factory;
  }

  public static void injectAdapter(OrderList instance, OrderListAdapter adapter) {
    instance.adapter = adapter;
  }
}

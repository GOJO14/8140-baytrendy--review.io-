package com.baytrendy.shopifyapp.productsection.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0003J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000eH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u000eH\u0016J\u0018\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000eH\u0016J(\u0010\u0018\u001a\u00020\u00122\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005J&\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0004\u001a\u00020\u001a2\u0006\u0010\u0013\u001a\u00020\u00022\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000bH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/shopify/shopifyapp/productsection/adapters/VariantAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/shopify/shopifyapp/productsection/viewholders/VariantItem;", "()V", "data", "Lcom/shopify/shopifyapp/basesection/models/ListData;", "layoutInflater", "Landroid/view/LayoutInflater;", "model", "Lcom/shopify/shopifyapp/productsection/viewmodels/ProductViewModel;", "variants", "", "Lcom/shopify/buy3/Storefront$ProductVariantEdge;", "getItemCount", "", "getItemViewType", "position", "onBindViewHolder", "", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "setData", "setVariants", "Lcom/shopify/shopifyapp/productsection/models/VariantData;", "selectedOptions", "Lcom/shopify/buy3/Storefront$SelectedOption;", "base_debug"})
public final class VariantAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.baytrendy.shopifyapp.productsection.viewholders.VariantItem> {
    private java.util.List<? extends com.shopify.buy3.Storefront.ProductVariantEdge> variants;
    private com.baytrendy.shopifyapp.productsection.viewmodels.ProductViewModel model;
    private com.baytrendy.shopifyapp.basesection.models.ListData data;
    private android.view.LayoutInflater layoutInflater;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.baytrendy.shopifyapp.productsection.viewholders.VariantItem onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.baytrendy.shopifyapp.productsection.viewholders.VariantItem holder, int position) {
    }
    
    private final void setVariants(com.baytrendy.shopifyapp.productsection.models.VariantData data, com.baytrendy.shopifyapp.productsection.viewholders.VariantItem holder, java.util.List<? extends com.shopify.buy3.Storefront.SelectedOption> selectedOptions) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void setData(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.shopify.buy3.Storefront.ProductVariantEdge> variants, @org.jetbrains.annotations.Nullable()
    com.baytrendy.shopifyapp.productsection.viewmodels.ProductViewModel model, @org.jetbrains.annotations.Nullable()
    com.baytrendy.shopifyapp.basesection.models.ListData data) {
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    @javax.inject.Inject()
    public VariantAdapter() {
        super();
    }
}
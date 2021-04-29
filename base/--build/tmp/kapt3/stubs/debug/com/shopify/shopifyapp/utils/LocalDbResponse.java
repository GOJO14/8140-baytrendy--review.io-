package com.baytrendy.shopifyapp.utils;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\t\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\'\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0001\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0001\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\bR\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00078\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0010"}, d2 = {"Lcom/shopify/shopifyapp/utils/LocalDbResponse;", "", "status", "Lcom/shopify/shopifyapp/utils/Status;", "data", "Lcom/shopify/shopifyapp/dbconnection/entities/AppLocalData;", "error", "", "(Lcom/shopify/shopifyapp/utils/Status;Lcom/shopify/shopifyapp/dbconnection/entities/AppLocalData;Ljava/lang/Throwable;)V", "getData", "()Lcom/shopify/shopifyapp/dbconnection/entities/AppLocalData;", "getError", "()Ljava/lang/Throwable;", "getStatus", "()Lcom/shopify/shopifyapp/utils/Status;", "Companion", "base_debug"})
public final class LocalDbResponse {
    @org.jetbrains.annotations.NotNull()
    private final com.baytrendy.shopifyapp.utils.Status status = null;
    @org.jetbrains.annotations.Nullable()
    @io.reactivex.annotations.Nullable()
    private final com.baytrendy.shopifyapp.dbconnection.entities.AppLocalData data = null;
    @org.jetbrains.annotations.Nullable()
    @io.reactivex.annotations.Nullable()
    private final java.lang.Throwable error = null;
    public static final com.baytrendy.shopifyapp.utils.LocalDbResponse.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.baytrendy.shopifyapp.utils.Status getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.baytrendy.shopifyapp.dbconnection.entities.AppLocalData getData() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Throwable getError() {
        return null;
    }
    
    private LocalDbResponse(com.baytrendy.shopifyapp.utils.Status status, @io.reactivex.annotations.Nullable()
    com.baytrendy.shopifyapp.dbconnection.entities.AppLocalData data, @io.reactivex.annotations.Nullable()
    java.lang.Throwable error) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0003\u001a\u00020\u0005J\u0010\u0010\u0006\u001a\u00020\u00042\b\b\u0001\u0010\u0007\u001a\u00020\b\u00a8\u0006\t"}, d2 = {"Lcom/shopify/shopifyapp/utils/LocalDbResponse$Companion;", "", "()V", "error", "Lcom/shopify/shopifyapp/utils/LocalDbResponse;", "", "success", "data", "Lcom/shopify/shopifyapp/dbconnection/entities/AppLocalData;", "base_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.baytrendy.shopifyapp.utils.LocalDbResponse success(@org.jetbrains.annotations.NotNull()
        @io.reactivex.annotations.NonNull()
        com.baytrendy.shopifyapp.dbconnection.entities.AppLocalData data) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.baytrendy.shopifyapp.utils.LocalDbResponse error(@org.jetbrains.annotations.NotNull()
        @io.reactivex.annotations.NonNull()
        java.lang.Throwable error) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}
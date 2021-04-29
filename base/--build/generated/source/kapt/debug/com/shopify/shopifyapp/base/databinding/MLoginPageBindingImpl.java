package com.baytrendy.shopifyapp.databinding;
import com.baytrendy.shopifyapp.R;
import com.baytrendy.shopifyapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class MLoginPageBindingImpl extends MLoginPageBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(3);
        sIncludes.setIncludes(0, 
            new String[] {"m_login", "m_forgotbottomsheet"},
            new int[] {1, 2},
            new int[] {com.baytrendy.shopifyapp.R.layout.m_login,
                com.baytrendy.shopifyapp.R.layout.m_forgotbottomsheet});
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.coordinatorlayout.widget.CoordinatorLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MLoginPageBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private MLoginPageBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 3
            , (com.baytrendy.shopifyapp.databinding.MForgotbottomsheetBinding) bindings[2]
            , (com.baytrendy.shopifyapp.databinding.MLoginBinding) bindings[1]
            );
        this.mboundView0 = (androidx.coordinatorlayout.widget.CoordinatorLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        includedlogin.invalidateAll();
        includedforgot.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (includedlogin.hasPendingBindings()) {
            return true;
        }
        if (includedforgot.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.handlers == variableId) {
            setHandlers((com.baytrendy.shopifyapp.loginsection.activity.LoginActivity.MyClickHandlers) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setHandlers(@Nullable com.baytrendy.shopifyapp.loginsection.activity.LoginActivity.MyClickHandlers Handlers) {
        updateRegistration(2, Handlers);
        this.mHandlers = Handlers;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.handlers);
        super.requestRebind();
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        includedlogin.setLifecycleOwner(lifecycleOwner);
        includedforgot.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeIncludedlogin((com.baytrendy.shopifyapp.databinding.MLoginBinding) object, fieldId);
            case 1 :
                return onChangeIncludedforgot((com.baytrendy.shopifyapp.databinding.MForgotbottomsheetBinding) object, fieldId);
            case 2 :
                return onChangeHandlers((com.baytrendy.shopifyapp.loginsection.activity.LoginActivity.MyClickHandlers) object, fieldId);
        }
        return false;
    }
    private boolean onChangeIncludedlogin(com.baytrendy.shopifyapp.databinding.MLoginBinding Includedlogin, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeIncludedforgot(com.baytrendy.shopifyapp.databinding.MForgotbottomsheetBinding Includedforgot, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeHandlers(com.baytrendy.shopifyapp.loginsection.activity.LoginActivity.MyClickHandlers Handlers, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        com.baytrendy.shopifyapp.loginsection.activity.LoginActivity.MyClickHandlers handlers = mHandlers;

        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            this.includedforgot.setHandlers(handlers);
            this.includedlogin.setHandlers(handlers);
        }
        executeBindingsOn(includedlogin);
        executeBindingsOn(includedforgot);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): includedlogin
        flag 1 (0x2L): includedforgot
        flag 2 (0x3L): handlers
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}
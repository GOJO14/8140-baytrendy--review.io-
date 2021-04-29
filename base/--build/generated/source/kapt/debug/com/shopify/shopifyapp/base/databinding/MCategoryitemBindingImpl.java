package com.baytrendy.shopifyapp.databinding;
import com.baytrendy.shopifyapp.R;
import com.baytrendy.shopifyapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class MCategoryitemBindingImpl extends MCategoryitemBinding implements com.baytrendy.shopifyapp.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback60;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MCategoryitemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds));
    }
    private MCategoryitemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 1
            , (androidx.cardview.widget.CardView) bindings[1]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (com.baytrendy.shopifyapp.customviews.MageNativeTextView) bindings[3]
            );
        this.card.setTag(null);
        this.image.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name.setTag(null);
        setRootTag(root);
        // listeners
        mCallback60 = new com.baytrendy.shopifyapp.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.commondata == variableId) {
            setCommondata((com.baytrendy.shopifyapp.basesection.models.CommanModel) variable);
        }
        else if (BR.categorydata == variableId) {
            setCategorydata((com.baytrendy.shopifyapp.collectionsection.models.Collection) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCommondata(@Nullable com.baytrendy.shopifyapp.basesection.models.CommanModel Commondata) {
        updateRegistration(0, Commondata);
        this.mCommondata = Commondata;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.commondata);
        super.requestRebind();
    }
    public void setCategorydata(@Nullable com.baytrendy.shopifyapp.collectionsection.models.Collection Categorydata) {
        this.mCategorydata = Categorydata;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.categorydata);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeCommondata((com.baytrendy.shopifyapp.basesection.models.CommanModel) object, fieldId);
        }
        return false;
    }
    private boolean onChangeCommondata(com.baytrendy.shopifyapp.basesection.models.CommanModel Commondata, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        else if (fieldId == BR.imageurl) {
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
        com.baytrendy.shopifyapp.basesection.models.CommanModel commondata = mCommondata;
        java.lang.String categorydataCategoryName = null;
        java.lang.String commondataImageurl = null;
        com.baytrendy.shopifyapp.collectionsection.models.Collection categorydata = mCategorydata;

        if ((dirtyFlags & 0xdL) != 0) {



                if (commondata != null) {
                    // read commondata.imageurl
                    commondataImageurl = commondata.getImageurl();
                }
        }
        if ((dirtyFlags & 0xaL) != 0) {



                if (categorydata != null) {
                    // read categorydata.category_name
                    categorydataCategoryName = categorydata.getCategory_name();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x8L) != 0) {
            // api target 1

            this.card.setOnClickListener(mCallback60);
        }
        if ((dirtyFlags & 0xdL) != 0) {
            // api target 1

            com.baytrendy.shopifyapp.basesection.models.CommanModel.loadImage(this.image, commondataImageurl);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.name, categorydataCategoryName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // categorydata
        com.baytrendy.shopifyapp.collectionsection.models.Collection categorydata = mCategorydata;
        // categorydata != null
        boolean categorydataJavaLangObjectNull = false;



        categorydataJavaLangObjectNull = (categorydata) != (null);
        if (categorydataJavaLangObjectNull) {




            categorydata.blockClick(callbackArg_0, categorydata);
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): commondata
        flag 1 (0x2L): categorydata
        flag 2 (0x3L): commondata.imageurl
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}
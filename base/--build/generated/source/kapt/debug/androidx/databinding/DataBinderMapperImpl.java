package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.baytrendy.shopifyapp.DataBinderMapperImpl());
    addMapper("com.baytrendy.shopifyapp");
  }
}

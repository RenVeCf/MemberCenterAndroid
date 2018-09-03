package com.liantong.membercenter.membercenter.api;

import com.liantong.membercenter.membercenter.bean.ModifyPersonalDataBean;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Description ：Retrofit2.0 后台返回没有数据的时候调用
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/9/3.
 */
public class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
        return new Converter<ResponseBody, Object>() {

            @Override
            public Object convert(ResponseBody body) throws IOException {
                if (body.contentLength() == 0) return new ModifyPersonalDataBean();
                return delegate.convert(body);
            }
        };
    }
}

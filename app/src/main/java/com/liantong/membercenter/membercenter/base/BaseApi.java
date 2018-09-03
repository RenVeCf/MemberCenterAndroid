package com.liantong.membercenter.membercenter.base;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.liantong.membercenter.membercenter.api.NullOnEmptyConverterFactory;
import com.liantong.membercenter.membercenter.common.config.IConstants;
import com.liantong.membercenter.membercenter.utils.ApplicationUtil;
import com.liantong.membercenter.membercenter.utils.LogUtils;
import com.liantong.membercenter.membercenter.utils.MD5Utils;
import com.liantong.membercenter.membercenter.utils.NetWorkUtil;
import com.liantong.membercenter.membercenter.utils.SPUtil;
import com.liantong.membercenter.membercenter.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 网络对象层
 */
public class BaseApi {

    //读超时长，单位：毫秒
    public static final int READ_TIME_OUT = 7676;
    //连接时长，单位：毫秒
    public static final int CONNECT_TIME_OUT = 7676;

    /**
     * 无超时及缓存策略的Retrofit
     *
     * @param baseUrl
     * @return retrofit
     */
    public Retrofit getSimpleRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型，一般为String
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配RxJava2.0,
                // RxJava1.x则为RxJavaCallAdapterFactory.create()
                .build();
        return retrofit;
    }

    /**
     * 使用OkHttp配置了超时及缓存策略的Retrofit
     *
     * @param baseUrl
     * @return retrofit
     */
    public Retrofit getRetrofit(String baseUrl) {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //缓存
        File cacheFile = new File(ApplicationUtil.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request();

                String parameter = "";
                FormBody formBody = (FormBody) build.body();
                if (formBody != null) {
                    for (int i = 0; i < formBody.size(); i++) {
                        parameter = parameter + formBody.encodedName(i) + "=" + URLDecoder.decode(formBody.encodedValue(i), "utf-8");
                        if (i < formBody.size() - 1) {
                            parameter = parameter + "&";
                        }
                    }
                }

                String buildUrl = "";
                String jti = "";
                String uidFromBase64 = "";
                String timesTamp = String.format("%010d", System.currentTimeMillis() / 1000);

                if (SPUtil.get(ApplicationUtil.getContext(), IConstants.TOKEN, "").toString().length() > 0) {
                    uidFromBase64 = getUidFromBase64(StringUtils.identical(SPUtil.get(ApplicationUtil.getContext(), IConstants.TOKEN, "").toString(), ".", "."));
                }
                if (uidFromBase64.length() > 0) {
                    JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(uidFromBase64.getBytes())));
                    jsonReader.setLenient(true);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map = new Gson().fromJson(jsonReader, map.getClass());
                    jti = map.get("jti") + "";
                }

                if (build.method().equals("GET") && build.url().toString().substring(build.url().toString().indexOf("/mc/v1")).contains("&"))
                    buildUrl = "|" + jti + "|2010006|GET|" + build.url().toString().substring(build.url().toString().indexOf("/mc/v1"), Integer.valueOf(build.url().toString().lastIndexOf("?"))) + "|" + timesTamp + "|" + build.url().toString().substring(Integer.valueOf(build.url().toString().lastIndexOf("?")) + 1) + "|||";
                else if (build.method().equals("GET"))
                    buildUrl = "|" + jti + "|2010006|GET|" + build.url().toString().substring(build.url().toString().indexOf("/mc/v1")) + "|" + timesTamp + "|" + parameter + "|||";
                else if (build.method().equals("PUT"))
                    buildUrl = "|" + jti + "|2010006|PUT|" + build.url().toString().substring(build.url().toString().indexOf("/mc/v1")) + "|" + timesTamp + "||" + parameter + "||";
                else
                    buildUrl = "|" + jti + "|2010006|POST|" + build.url().toString().substring(build.url().toString().indexOf("/mc/v1")) + "|" + timesTamp + "||" + parameter + "||";

                LogUtils.i("rmy", "parameter = " + parameter + "\nBase64 = " + getUidFromBase64(SPUtil.get(ApplicationUtil.getContext(), IConstants.TOKEN, "").toString()) + "\nAuthorization = " + getToKen() + "\nX-Signature = " + MD5Utils.encodeMD5(buildUrl) + "\nX-TimesTamp = " + timesTamp + "\nbuildUrl = " + buildUrl);
                build = build.newBuilder()
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .addHeader("Authorization", getToKen())
                        .addHeader("X-Signature", MD5Utils.encodeMD5(buildUrl))
                        .addHeader("X-TimesTamp", timesTamp)
                        .build();
                return chain.proceed(build);
            }
        };

        //创建一个OkHttpClient并设置超时时间
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(mRewriteCacheControlInterceptor)//没网的情况下
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)//有网的情况下
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor)
                .cache(cache)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型，一般为String
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                // RxJava1.x则为RxJavaCallAdapterFactory.create()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配RxJava2.0,
                .build();
        return retrofit;
    }

    private String getToKen() {
        if (!SPUtil.get(ApplicationUtil.getContext(), IConstants.TOKEN, "").equals(""))
            return SPUtil.get(ApplicationUtil.getContext(), IConstants.AUTHORIZATION_TYPE, "") + " " + SPUtil.get(ApplicationUtil.getContext(), IConstants.TOKEN, "");
        else
            return "";
    }

    /**
     * base64 解码
     *
     * @param base64Id
     * @return
     */
    public String getUidFromBase64(String base64Id) {
        String result = "";
        if (!TextUtils.isEmpty(base64Id)) {
            if (!TextUtils.isEmpty(base64Id)) {
                try {
                    result = new String(android.util.Base64.decode(base64Id.getBytes(), android.util.Base64.DEFAULT));
                } catch (Exception e) {
                    LogUtils.i("rmy", e.toString());
                }
            }
        }
        return result;
    }

    /**
     * 设缓存有效期为两天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();//拦截器获取请求
            String cacheControl = request.cacheControl().toString();//服务器的缓存策略
            if (!NetWorkUtil.isNetConnected(ApplicationUtil.getContext())) {//断网时配置缓存策略
                request = request.newBuilder()
                        .cacheControl(TextUtils.isEmpty(cacheControl) ?
                                CacheControl.FORCE_NETWORK : CacheControl.FORCE_CACHE)
                        //                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(ApplicationUtil.getContext())) {//在线缓存
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", cacheControl)//应用服务端配置的缓存策略
                        //                        .header("Cache-Control", "public, max-age=" + 60 * 2)//有网的时候连接服务器请求,缓存(时间)
                        .build();
            } else {//离线缓存
                /**
                 * only-if-cached:(仅为请求标头)
                 *　 请求:告知缓存者,我希望内容来自缓存，我并不关心被缓存响应,是否是新鲜的.
                 * max-stale:
                 *　 请求:意思是,我允许缓存者，发送一个,过期不超过指定秒数的,陈旧的缓存.
                 *　 响应:同上.
                 * max-age:
                 *   请求:强制响应缓存者，根据该值,校验新鲜性.即与自身的Age值,与请求时间做比较.如果超出max-age值,
                 *   则强制去服务器端验证.以确保返回一个新鲜的响应.
                 *   响应:同上.
                 */
                //需要服务端配合处理缓存请求头，不然会抛出： HTTP 504 Unsatisfiable Request (only-if-cached)
                //                KLog.e("离线缓存"+CACHE_STALE_SEC+"秒");
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)//CACHE_STALE_SEC
                        .build();
            }
        }
    };
}
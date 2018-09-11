package com.liantong.membercenter.membercenter.base;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.liantong.membercenter.membercenter.bean.ModifyPersonalDataBean;
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
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
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
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Description ：网络对象层
 * Author ： MengYang
 * Email ： 942685687@qq.com
 * Time ： 2018/8/27.
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
                .addConverterFactory(new NullOnEmptyConverterFactory())//Retrofit 后台返回没有数据的时候调用，不是null，是状态为OK，但什么返回数据都没有
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
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);// Level.BODY貌似在上传文件是有bug，改成.Head就OK了
        //缓存
        File cacheFile = new File(ApplicationUtil.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request build = chain.request();

                /**
                 * 本程序“请求头”规则：
                 *  如：Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIyMDEwMDA2IiwiZXhwIjoxNTM4MzIzMTk5LCJqdGkiOiIxODUwMjk5NDA4NyIsImlhdCI6MTUzNjExMjM4OCwiaXNzIjoiU3BhcmtXb3Jrc0BDaGluYVVuaWNvbSIsInN1YiI6Ik1lbWJlclRva2VuIiwibWVtYmVyX2NvZGUiOiI5MTc1OTU2NDg4OTE3Iiwib3BlbmlkIjoib25Icl9qa2VpOXM2dUNqU3VDOUdMLTgwYk9WOCIsImNoYW5uZWwiOiIifQ.XIFRuiz41Wn1sJgakx6l8JEwkB4ERV8stqpsEOJTXEs
                 * 1. Authorization : Token类型 + 空格 + Token
                 * 如：0fb6d49fc5e73f2fb62dc669a2c0a73c
                 * 2.X-Signature : 用 MD5 加密“签名”
                 * 如：1536115275
                 * 3.X-TimesTamp : 10位时间戳
                 *
                 * 本程序“签名”规则：
                 *  如：|18502994087|2010006|GET|/mc/v1/member_center/exp|1536116464|page=2&page_size=20|||
                 *  1. |手机号码|平台标识码|请求方式|域名之后到参数之前的url|10位时间戳|GET参数|POST参数|BODY参数|
                 * 详细介绍：
                 * <1> 手机号码的获取：Token分为三段，之间以"."分隔，取第二段用Base64解码后得到map对象，取key为jti的值做为手机号.
                 * <2> 平台标识码：如Android/IOS/小程序等，每个平台一个独立的flag.
                 * <3> 请求方式：pass ... 这还要问就过分了.
                 * <4> 截取的URL：从域名之后开始（如 https://fx.10010sh.cn）截取到参数开始时（不带问号）.
                 * <5> 时间戳：pass.
                 * <6> 参数：按字母排序参数后，参数之间用“&”连接，key和value之间用“=”连接.
                 */
                //签名中的参数
                String parameter = "";
                //提取网络请求中的请求体对象
                FormBody formBody = (FormBody) build.body();
                if (formBody != null) {
                    //进行规范中要求的参数的拼接
                    for (int i = 0; i < formBody.size(); i++) {
                        // URLDecoder.decode(str, "utf-8") 参数有中文，需要这样转一下，不然中文会转为 6E%T3%O9% 这样的Base码
                        parameter = parameter + formBody.encodedName(i) + "=" + URLDecoder.decode(formBody.encodedValue(i), "utf-8");
                        if (i < formBody.size() - 1) {
                            parameter = parameter + "&";
                        }
                    }
                }

                //签名
                String buildUrl = "";
                //手机号码
                String jti = "";
                //用Base64解码后的字符串
                String uidFromBase64 = "";
                //10位时间戳
                String timesTamp = String.format("%010d", System.currentTimeMillis() / 1000);

                //Token防空
                if (SPUtil.get(ApplicationUtil.getContext(), IConstants.TOKEN, "").toString().length() > 0) {
                    //截取Token的第二段用Base64解码
                    uidFromBase64 = getUidFromBase64(StringUtils.identical(SPUtil.get(ApplicationUtil.getContext(), IConstants.TOKEN, "").toString(), ".", "."));
                }
                if (uidFromBase64.length() > 0) {
                    //把解码后的Base64字符串转map
                    JsonReader jsonReader = new JsonReader(new InputStreamReader(new ByteArrayInputStream(uidFromBase64.getBytes())));
                    jsonReader.setLenient(true);
                    Map<String, Object> map = new HashMap<String, Object>();
                    map = new Gson().fromJson(jsonReader, map.getClass());
                    //提取手机号码
                    jti = map.get("jti") + "";
                }

                //如果是有参的GET方式
                if (build.method().equals("GET") && build.url().toString().substring(build.url().toString().indexOf("/mc/v1")).contains("&"))
                    buildUrl = "|" + jti + "|2010006|GET|" + build.url().toString().substring(build.url().toString().indexOf("/mc/v1"), Integer.valueOf(build.url().toString().lastIndexOf("?"))) + "|" + timesTamp + "|" + URLDecoder.decode(build.url().toString().substring(Integer.valueOf(build.url().toString().lastIndexOf("?")) + 1), "utf-8") + "|||";
                    //无参GET
                else if (build.method().equals("GET"))
                    buildUrl = "|" + jti + "|2010006|GET|" + build.url().toString().substring(build.url().toString().indexOf("/mc/v1")) + "|" + timesTamp + "|" + parameter + "|||";
                    //PUT
                else if (build.method().equals("PUT"))
                    buildUrl = "|" + jti + "|2010006|PUT|" + build.url().toString().substring(build.url().toString().indexOf("/mc/v1")) + "|" + timesTamp + "||" + parameter + "||";
                    //POST
                else
                    buildUrl = "|" + jti + "|2010006|POST|" + build.url().toString().substring(build.url().toString().indexOf("/mc/v1")) + "|" + timesTamp + "||" + parameter + "||";

                LogUtils.i("rmy", "parameter = " + parameter + "\nBase64 = " + getUidFromBase64(SPUtil.get(ApplicationUtil.getContext(), IConstants.TOKEN, "").toString()) + "\nAuthorization = " + getToKen() + "\nX-Signature = " + MD5Utils.encodeMD5(buildUrl) + "\nX-TimesTamp = " + timesTamp + "\nbuildUrl = " + buildUrl);
                //请求头
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
                .addConverterFactory(new NullOnEmptyConverterFactory())//Retrofit 后台返回没有数据的时候调用，不是null，是状态为OK，但什么返回数据都没有
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                // RxJava1.x则为RxJavaCallAdapterFactory.create()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//适配RxJava2.0,
                .build();
        return retrofit;
    }

    /**
     * 拼接Token
     *
     * @return
     */
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
     * Retrofit2.0 后台返回没有数据的时候调用
     *
     * @return
     */
    class NullOnEmptyConverterFactory extends Converter.Factory {
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
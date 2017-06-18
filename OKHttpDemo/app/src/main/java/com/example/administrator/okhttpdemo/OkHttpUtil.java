package com.example.administrator.okhttpdemo;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dhj on 2017/6/1.
 */

public class OkHttpUtil {
	public static final String TAG="OkHttpUtil";
	private static OkHttpClient client;
	private static OkHttpUtil httpUtil;

	private OkHttpUtil(){
		client=new OkHttpClient.Builder()
				.connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10,TimeUnit.SECONDS)
				.build();
	}

//	使用单例模式，保证只有一个OkHttpUtil和OkHttpClient 的实例。
	public static OkHttpUtil getInstance(){
		synchronized (OkHttpUtil.class){
			if(httpUtil==null){
				httpUtil= new OkHttpUtil();
				return httpUtil;
			}
		}
		return httpUtil;
	}


	/**
	 * get异步请求
	 * @param url
	 */
	public static void getData(String url){
		Request request=new Request.Builder().url(url)
				.build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				//字符串类型
				Log.i(TAG, "getData: " + response.body().string());
				//字节数组类型
				Log.i(TAG, "getData: " + response.body().bytes());
				//字节流类型
				Log.i(TAG, "getData: " + response.body().byteStream());
				//字符流类型
				Log.i(TAG, "getData: " + response.body().charStream());
			}
		});
	}

	/**
	 * 带有多个参数的get请求
	 * @param url
	 * @param map
	 */
	public void getParams(String url,Map<String,String> map){
		String url1=url;

		if(map!=null&&map.size()>0){
			for(String key:map.keySet()){
				url1+="&"+key+"="+map.get(key);
			}
		}
		getData(url1);
	}

	/**
	 * 提交单个键值对
	 * @param url
	 * @param key
	 * @param value
	 */
	public void postKeyValuePaire(String url,String key,String value){
		FormBody formBody=new FormBody.Builder()
				.add(key,value)
				.build();
		Request request=new Request.Builder()
				.url(url)
				.post(formBody)
				.build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {

			}
		});
	}


	/**
	 * 提交多个键值对
	 *
	 * @param url 提交的路径
	 * @param map 用来放置键值对,map的key对应键,value对应值
	 */

	public void postMap(String url,Map<String ,String> map){
		FormBody.Builder formBody=new FormBody.Builder();
		if(map!=null){
			for(String key:map.keySet()){
				formBody.add(key,map.get(key));
			}
		}
		Request request=new Request.Builder().url(url)
				.post(formBody.build())
				.build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {

			}
		});
	}



	/**
	 * post提交Json数据
	 * @param url
	 * @param content
	 */
	public void postString(String url,String content){

		MediaType mediaType=MediaType.parse("application/json;  charset=utf-8");
		RequestBody requestBody=RequestBody.create(mediaType,content);
		Request request=new Request.Builder()
				.url(url)
				.post(requestBody)
				.build();
		client.newCall(request)
				.enqueue(new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {

					}

					@Override
					public void onResponse(Call call, Response response) throws IOException {

					}
				});
	}


	/**
	 * 上传单一文件
	 */
	public void uploadFile(String url, File file){
		MediaType type=MediaType.parse(getMediaType(file.getName()));//设置上传文件的类型
		RequestBody requestBody=RequestBody.create(type,file);//创建请求体

		Request request=new Request.Builder().url(url)//创建请求
				.post(requestBody)
				.build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {

			}
		});
	}


	/**
	 * 上传多个文件
	 */

	public void uploadFiles(String url,File[] files){
		MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
		for(int i=0;i<files.length;i++){
			RequestBody fileBody=RequestBody.create(MediaType.parse(getMediaType(files[i].getName())),files[i]);
			builder.addFormDataPart("file",files[i].getName(),fileBody);
		}
		Request request=new Request.Builder()
				.url(url)
				.post(builder.build())
				.build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {

			}
		});
	}


	/**
	 * 上传多个文件和参数
	 */
	public void uplaodMultiFiles(String url,File[] files,Map<String ,String> map){
		MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
		//添加文件
		if(files!=null&&files.length>0){
			for(int i=0;i<files.length;i++){
				builder.addFormDataPart("uploadfile",files[i].getName(),RequestBody.create(MediaType.parse(files[i].getName()),files[i]));
			}
		}
		//添加参数
		if(map!=null&&map.size()>0){
			for(String key:map.keySet()){
				builder.addFormDataPart(key,map.get(key));
			}
		}

		Request request=new Request.Builder().url(url)
				.post(builder.build())
				.build();
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {

			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {

			}
		});

	}



	/**
	 * 根据文件的名称判断文件的MediaType
	 * @param fileName
	 * @return
	 */
	private String getMediaType(String fileName){
		FileNameMap map= URLConnection.getFileNameMap();
		String contentTypeFor=map.getContentTypeFor(fileName);
		if(contentTypeFor==null){
			contentTypeFor="applicationn/octet-stream";
		}
		return contentTypeFor;
	}




	/**
	 * 设置请求头
	 * @param headersParams
	 * @return
	 */
	private Headers SetHeaders(Map<String, String> headersParams){
		Headers headers=null;
		okhttp3.Headers.Builder headersbuilder=new okhttp3.Headers.Builder();

		if(headersParams != null)
		{
			Iterator<String> iterator = headersParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				headersbuilder.add(key, headersParams.get(key));
				Log.d("get http", "get_headers==="+key+"===="+headersParams.get(key));
			}
		}
		headers=headersbuilder.build();

		return headers;
	}


	/**
	 * get方法连接拼加参数
	 * @param mapParams
	 * @return
	 */
	private String setUrlParams( Map<String, String> mapParams){
		String strParams = "";
		if(mapParams != null){
			Iterator<String> iterator = mapParams.keySet().iterator();
			String key = "";
			while (iterator.hasNext()) {
				key = iterator.next().toString();
				strParams += "&"+ key + "=" + mapParams.get(key);
			}
		}

		return strParams;
	}
}

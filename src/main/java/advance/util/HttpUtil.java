package advance.util;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.*;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.net.URLEncoder;
import java.util.*;

/**
 * http工具类
 */
public final class HttpUtil {

	private final static Logger Log = LoggerFactory.getLogger(HttpUtil.class);
	private static final String DEFAULT_CHARSET = "UTF-8";          //设置默认通信报文编码为UTF-8
	private static final int DEFAULT_CONNECTION_TIMEOUT = 1000 * 2; //设置默认连接超时为2s
	private static final int DEFAULT_SO_TIMEOUT = 1000 * 60;        //设置默认读取超时为60s

	private static HttpClient getHttpClient() {
		/* 1. 设置一些基本参数，如Http版本、编码格式和参数设置 */
		HttpParams httpParams = new BasicHttpParams();
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
		HttpProtocolParams.setUseExpectContinue(httpParams, true);
		/* 2. 超时设置 */
		/* 从连接池中取连接的超时时间 */
		ConnManagerParams.setTimeout(httpParams, 20000);
		/* 连接超时 */
		HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
		/* 请求超时 */
		HttpConnectionParams.setSoTimeout(httpParams, 20000);
		/* 3. 设置我们的HttpClient支持HTTP和HTTPS两种模式 */
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		registry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		/* 4. 使用线程安全的连接管理来创建HttpClient */
		ClientConnectionManager manager = new ThreadSafeClientConnManager(
				httpParams, registry);
		return new DefaultHttpClient(manager, httpParams);
	}
	/**
	 * HttpClient Post请求方式
	 */
	public static String onLinkNetPost(String url, Map<String, String> params) {
			/* 1.2获取HttpClient对象，并发送请求，得到响应 */
		HttpClient httpClient = getHttpClient();
		try {
			/* 1.1 创建POST请求，并设置Url地址的名值对及其编码格式，然后设置Entity */
			Log.info("post", url);
			HttpPost httpPost = new HttpPost(url);
			if (null != params) {
				List<NameValuePair> nvp = new ArrayList<>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					nvp.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvp, "utf-8"));
			}
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 60000);
			// 1.3发送请求，获取服务器返回的相应对象
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.info("【onLinkNetPost】请求地址[{}]\t 服务器返回状态[{}]",url+httpPost.getEntity().getContent().toString(),
					httpResponse.getStatusLine().getStatusCode());
			/* 1.4 从响应中获取数据 */
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = (httpEntity == null) ? null : (EntityUtils
					.toString(httpEntity, "UTF-8"));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("when onLinkNetPost,occur ",e);
		}finally {
			httpClient.getConnectionManager().shutdown();
		}
		return  null;
	}
	/**
	 * HttpClient Get请求方式
	 */
	public static String onLinkNetGet(String url, Map<String, String> params) {
			/* 1.2 获取HttpClient对象，并发送请求，得到响应 */
		HttpClient httpClient = getHttpClient();
		try {
			// GET方式参数拼接在URL结尾
			StringBuilder sb = new StringBuilder();
			sb.append(url).append("?");
			if (params != null && params.size() != 0) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					// 如果请求参数中有中文，需要进行URLEncoder编码
					sb.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(), "utf-8"));
					sb.append("&");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			/* 1.1 创建Get请求，并设置Url地址 */
			HttpGet httpGet = new HttpGet(sb.toString());
			// 1.3发送请求，获取服务器返回的相应对象
			HttpResponse httpResponse = httpClient.execute(httpGet);
			Log.info("【onLinkNetGet】请求地址[{}]\t 服务器返回状态[{}]",sb.toString(),
					httpResponse.getStatusLine().getStatusCode());
			/* 1.4 从响应中获取数据 */
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = (httpEntity == null) ? null : (EntityUtils
					.toString(httpEntity, "UTF-8"));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("when onLinkNetGet,occur ",e);
		}finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
	public static String onLinkNetPut(String url, Map<String, String> params) {
			/* 1.2 获取HttpClient对象，并发送请求，得到响应 */
		HttpClient httpClient = getHttpClient();
		try {
			/* 1.1 创建httpPut请求，并设置Url地址 */
			HttpPut httpPut = new HttpPut(url);
			if (null != params) {
				List<NameValuePair> nvp = new ArrayList<>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					nvp.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPut.setEntity(new UrlEncodedFormEntity(nvp, "utf-8"));
			}
			// 1.3发送请求，获取服务器返回的相应对象
			HttpResponse httpResponse = httpClient.execute(httpPut);
			Log.info("【onLinkNetPut】请求地址[{}]\t 服务器返回状态[{}]",url+httpPut.getEntity().getContent().toString(),
					httpResponse.getStatusLine().getStatusCode());
			/* 1.4从响应中获取数据 */
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = (httpEntity == null) ? null : (EntityUtils.toString(httpEntity, "UTF-8"));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("when onLinkNetPut,occur ",e);
		}finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
	public static String onLinkNetDelete(String url, Map<String, String> params) {
			/* 1.2 获取HttpClient对象，并发送请求，得到响应 */
		HttpClient httpClient = getHttpClient();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(url).append("?");
			if (params != null && params.size() != 0) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					// 如果请求参数中有中文，需要进行URLEncoder编码
					sb.append(entry.getKey())
							.append("=")
							.append(URLEncoder.encode(entry.getValue(), "utf-8"));
					sb.append("&");
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			/* 1.1 创建httpPut请求，并设置Url地址 */
			HttpDelete httpDelete = new HttpDelete(sb.toString());
			// 1.3发送请求，获取服务器返回的相应对象
			HttpResponse httpResponse = httpClient.execute(httpDelete);
			Log.info("【onLinkNetDelete】请求地址[{}]\t 服务器返回状态[{}]",sb.toString(),httpResponse.getStatusLine().getStatusCode());
			/* 1.4从响应中获取数据 */
			if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				return null;
			}
			HttpEntity httpEntity = httpResponse.getEntity();
			String result = (httpEntity == null) ? null : (EntityUtils.toString(httpEntity, "UTF-8"));
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("when onLinkNetDelete,occur ",e);
		}finally {
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}


}
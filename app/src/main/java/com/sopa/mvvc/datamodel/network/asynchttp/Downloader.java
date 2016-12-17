package com.sopa.mvvc.datamodel.network.asynchttp;

import com.loopj.android.http.AsyncHttpClient;
import com.sopa.mvvc.datamodel.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class Downloader {

	private final String name = "name";
	private final String image_url = "i_url";
	private final String category = "category";
	private final String description = "description";
	private final String description_ru = "description_ru";
	private final String map_url = "map_url";
	private final String map_url_backup = "map_urlb";
	private final String premium = "p";

	AsyncHttpClient client = new AsyncHttpClient();

	public void loadMaps(final ArrayList<Map> maps, final LoadingListener listener) throws IOException {
	/*	//maps = new ArrayList<>();
		//String url = "http://spreadsheets.google.com/feeds/list/14Xf3BDfHeOWrRnnxab_iYUJIM3uG03jQNJA8Wl5jkwQ/oesiqps/public/values";
		String url = "https://api.backendless.com/v1/data/map";
		//String url = "http://104.236.252.91/newmapsru.json";


		client.addHeader("secret-key", "FE6B9C1F-0F26-A120-FFE5-345D29C27E00");
		client.addHeader("application-id", "5866D097-2401-59A4-FFE4-5A242A79D100");
		client.addHeader("application-type", "REST");
		client.addHeader("Content-type", "application/json");
		//String s ="14Xf3BDfHeOWrRnnxab_iYUJIM3uG03jQNJA8Wl5jkwQ";
		client.get(url, new JsonHttpResponseHandler() {


			@Override
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
				for (int i = 0; i < response.length(); i++) {
					Map map = new Map();


					Type collectionType = new TypeToken<BackendlessResponse<Map>>() {
					}.getType();
					Gson gson = new Gson();

					final BackendlessResponse<Map> resp = gson.fromJson(response.toString(), collectionType);
/*
					try {
						JSONObject obj = response.getJSONObject(i);
						map.setName(obj.getString(name));
						map.setI_url(obj.getString(image_url));
						map.setMap_url(obj.getString(map_url));
						map.setMap_urlb(obj.getString(map_url_backup));
						map.setCategory(obj.getString(category));
						map.setDescription(obj.getString(description));
						map.setDescription_Ru(obj.getString(description_ru));
						map.setP(obj.getInt(premium) == 1);
						map.setLocked(map.isP());
					} catch (JSONException e) {
						listener.onFailure();
						e.printStackTrace();
					}

					// do something with "data" and "value"
					maps.add(map);

					//map.setP(obj.getInt(premium) == 1);
					map.setLocked(map.isP());
					maps.addAll(resp.getCollection());
				}

				listener.loadingFinished();
				super.onSuccess(statusCode, headers, response);
			}

			@Override
			public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONArray errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				listener.onFailure();
			}

			@Override
			public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, Throwable throwable, JSONObject errorResponse) {
				super.onFailure(statusCode, headers, throwable, errorResponse);
				listener.onFailure();
			}

		});
		*/
	}

	private void writeXML() {


	}

	public void setCategory(String category) {

		
	}

	private String getNodeValue(Element e, String tagName) {

		NodeList idList = e.getElementsByTagName(tagName);
		Element firstIdElement = (Element) idList.item(0);
		NodeList textIdList = firstIdElement.getChildNodes();

		if (textIdList.item(0) != null)
			return textIdList.item(0).getNodeValue().trim();
		else return "";

	}
}
/*
	public static Document loadXMLFrom(String xml) throws TransformerException {
		Source source = new StreamSource(new StringReader(xml));
		DOMResult result = new DOMResult();

		TransformerFactory.newInstance().newTransformer().transform(source, result);
		return (Document) result.getNode();
	}

}
*/

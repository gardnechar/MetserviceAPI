package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APITest {

	public static void main(String[] args) {

		try {

			URL url = new URL("http://www.metservice.com/publicData/mainPageW");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			StringBuffer buffer = new StringBuffer();
			String output;
			System.out.println("Output from Server .... \n");

			while ((output = br.readLine()) != null) {
				buffer.append(output);

			}

			conn.disconnect();

			String finalJson = buffer.toString();

			try {
				JSONArray arr = new JSONArray(finalJson);

				String location;
				for (int i = 0; i < arr.length(); i++) {

					JSONObject jsonobject = arr.getJSONObject(i);

					location = jsonobject.getString("location");

					if (location.equals("WELLINGTON")) {

						JSONArray daysarray = (JSONArray) jsonobject.get("days");
						JSONObject city = (JSONObject) daysarray.get(0);
						System.out.println("City: " + location);
						System.out.println("Date: " + city.getString("date"));
						System.out.println("Min: " + city.getString("min"));
						System.out.println("Max: " + city.getString("max"));
						System.out.println("Forecast: " + city.getString("forecastWord"));

					}

				}

			} catch (JSONException e) {

				e.printStackTrace();
			}


		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}

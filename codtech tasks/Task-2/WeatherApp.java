import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApp {

    public static void main(String[] args) {
        // Replace with your OpenWeatherMap API key
        String apiKey = "YOUR_API_KEY";
        // Location for which we want the weather (e.g., "London")
        String city = "London";
        
        // API endpoint for current weather data
        String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try {
            // Make the HTTP request to the API
            String response = sendGetRequest(urlString);
            
            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response);
            
            // Extract and display relevant data from the response
            displayWeatherData(jsonResponse);

        } catch (Exception e) {
            System.err.println("Error fetching or processing weather data: " + e.getMessage());
        }
    }

    /**
     * Sends an HTTP GET request to the provided URL and returns the response as a String.
     *
     */
    public static String sendGetRequest(String urlString) throws Exception {
        // Create a URL object
        URL url = new URL(urlString);
        
        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        
        // Read the response
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        
        // Close the input stream
        in.close();
        
        return response.toString();
    }

    /**
     * Displays the weather data in a structured format.
     */
    public static void displayWeatherData(JSONObject jsonResponse) {
        // Extracting main data
        JSONObject mainData = jsonResponse.getJSONObject("main");
        double temp = mainData.getDouble("temp");
        double pressure = mainData.getDouble("pressure");
        double humidity = mainData.getDouble("humidity");
        
        // Extracting weather data
        JSONObject weatherData = jsonResponse.getJSONArray("weather").getJSONObject(0);
        String description = weatherData.getString("description");
        
        // Extracting location information
        String city = jsonResponse.getString("name");
        String country = jsonResponse.getJSONObject("sys").getString("country");
        
        // Display the data
        System.out.println("Weather Data for " + city + ", " + country);
        System.out.println("------------------------------------------------");
        System.out.println("Temperature: " + temp + "°C");
        System.out.println("Pressure: " + pressure + " hPa");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Weather Description: " + description);
    }
}

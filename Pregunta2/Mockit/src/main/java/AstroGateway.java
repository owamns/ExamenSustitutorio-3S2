import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AstroGateway implements Gateway<AstroResponse> {
    private final String url;

    public AstroGateway(String url) {
        this.url = url;
    }

    @Override
    public AstroResponse getResponse() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            return parseJsonResponse(responseBody);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public AstroResponse parseJsonResponse(String jsonResponse) {

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
        int number = jsonObject.get("number").getAsInt();
        String message = jsonObject.get("message").getAsString();
        JsonArray peopleArray = jsonObject.get("people").getAsJsonArray();

        List<Assignament> peopleList = new ArrayList<>();
        for (JsonElement element : peopleArray) {
            JsonObject personObject = element.getAsJsonObject();
            String name = personObject.get("name").getAsString();
            String craft = personObject.get("craft").getAsString();
            Assignament assignment = new Assignament(name, craft);
            peopleList.add(assignment);
        }

        return new AstroResponse(number, message, peopleList);
    }

    public static void main(String[] args) {
        String apiUrl = "http://api.open-notify.org/astros.json";
        AstroGateway astroGateway = new AstroGateway(apiUrl);
        AstroResponse astroResponse = astroGateway.getResponse();

        if (astroResponse != null) {
            System.out.println("Numero de astronautas: " + astroResponse.getNumber());
            System.out.println("Mensaje: " + astroResponse.getMessage());
            System.out.println("Astronautas:");
            for (Assignament assignament : astroResponse.getPeople()) {
                System.out.println(assignament);
            }
        } else {
            System.out.println("Error al recuperar datos de la API");
        }
    }
}

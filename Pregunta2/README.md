# Pregunta 2

<h1 align="center">
  <img src="https://raw.githubusercontent.com/owamns/ExamenSustitutorio-3S2/main/Pregunta2/files/p1.png">
</h1>

El archivo astros.json contiene informacion de 10 astronautas en la que se indica su nombre y en que nave 
se encuentran.

- `number` Indica la cantidad de elementos.
- `name` Nombre de la persona.
- `craft` Ubicacion de la persona en una nave espacial.
- `message` Un mensaje de texto

Para obtener los datos mencionados se hace uso 2 de clases, `Assignament` y `AstroResponse`:

Assignament:
```
public class Assignament {
    private String name;
    private String craft;

    public Assignament(String name, String craft) {
        this.name = name;
        this.craft = craft;
    }

    public String getCraft() {
        return craft;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Assignament{" +
                "name='" + name + '\'' +
                ", craft='" + craft + '\'' +
                '}';
    }
}

```
AstroResponse:
```
import java.util.List;

public class AstroResponse {
    private final int number;
    private final String message;
    private final List<Assignament> people;

    public AstroResponse(int number, String message, List<Assignament> people) {
        this.number = number;
        this.message = message;
        this.people = people;
    }

    public int getNumber() {
        return number;
    }

    public String getMessage() {
        return message;
    }

    public List<Assignament> getPeople() {
        return people;
    }

    @Override
    public String toString() {
        return "AstroResponse{" +
                "number=" + number +
                ", message='" + message + '\'' +
                ", people=" + people +
                '}';
    }
}
```

Para acceder al servicio web RESTful, se usa el patrón de diseño Gateway.
Por lo que se crea la inferfaz `Gateway` que hara uso la clase `AstroGateway`.

Gateway:
```
public interface Gateway <T>{
    T getResponse ();
}
```

Entonces la clase `AstroGateway` estaria implementado de la siguiente forma:
```
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
}
```

Verificando que la clase `AstroGateway` accede a un servicio web RESTful para recuperar los
datos de los astronautas en formato JSON y luego convierte la estructura JSON en Java POJO la siguiente ejecucion de codigo,
se ejecuta lo siguiente en el metodo `main` en la clase `AstroGateway`:

```
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
```
Lo que nos muestra lo siguiente y se verifica que si cumple con el objetivo establecido:
<h1 align="center">
  <img src="https://raw.githubusercontent.com/owamns/ExamenSustitutorio-3S2/main/Pregunta2/files/r1.png">
</h1>

<h1 align="center">
  <img src="https://raw.githubusercontent.com/owamns/ExamenSustitutorio-3S2/main/Pregunta2/files/p2.png">
</h1>

Implementando la clase `AstroService`:
```
public class AstroService {
public Map<String, Long> getAstroData(List<Assignament> assignments) {
    Map<String, Long> spacecraftAstronautCount = new HashMap<>();

    for (Assignament assignment : assignments) {
        String spacecraft = assignment.getCraft();
        spacecraftAstronautCount.put(spacecraft, spacecraftAstronautCount.getOrDefault(spacecraft, 0L) + 1);
    }

    return spacecraftAstronautCount;
}
```
El uso de esta clase es para procese los POJO de java y devuelva un mapa de cadenas 
a números enteros, donde las claves del mapa son los nombres de las naves espaciales y los
valores del mapa son la cantidad de astronautas a bordo de cada una.

Para verificar su funcionamiento se toma tambien el funcionamiento de la clase `AstroGateway` para la url 
http://api.open-notify.org/astros.json, se ejecuta lo siguiente:
```
public static void main(String[] args) {
    String apiUrl = "http://api.open-notify.org/astros.json";
    AstroGateway astroGateway = new AstroGateway(apiUrl);
    AstroResponse astroResponse = astroGateway.getResponse();

    if (astroResponse != null) {
        AstroService astroService = new AstroService();
        Map<String, Long> spacecraftAstronautCount = astroService.getAstroData(astroResponse.getPeople());

        for (String spacecraft : spacecraftAstronautCount.keySet()) {
            Long astronautCount = spacecraftAstronautCount.get(spacecraft);
            System.out.println(astronautCount + " astronautas a bordo de " + spacecraft);
        }
    } else {
        System.out.println("Error al recuperar datos de la API");
    }
}
```
Al ejecutar se observa que retorna que 3 astronautas estan abordo de Tiangong y 7 abordo de ISS:

<h1 align="center">
  <img src="https://raw.githubusercontent.com/owamns/ExamenSustitutorio-3S2/main/Pregunta2/files/r2.png">
</h1>

<h1 align="center">
  <img src="https://raw.githubusercontent.com/owamns/ExamenSustitutorio-3S2/main/Pregunta2/files/p3.png">
</h1>

Para la prueba de inegracion no necesitamos la url debido que queremos comprobar el funcionamiento de manera interna 
es decir se simula el funcionamiento para evitar errores externos como de conexion.

Se realiza una prueba en la que se crean 10 astronautas y se comprueba que 3 astronautas estan abordo de Tiangong y 7 abordo de ISS.
Usando mockito:
```
@ExtendWith(MockitoExtension.class)
public class AstroServiceTest {
    @InjectMocks
    private AstroService astroService;
    
    @Test
    public void testGetAstroData() {
    
        Assignament astronaut1 = new Assignament("Astronaut 1", "ISS");
        Assignament astronaut2 = new Assignament("Astronaut 2", "ISS");
        Assignament astronaut3 = new Assignament("Astronaut 3", "ISS");
        Assignament astronaut4 = new Assignament("Astronaut 4", "Tiangong");
        Assignament astronaut5 = new Assignament("Astronaut 5", "Tiangong");
        Assignament astronaut6 = new Assignament("Astronaut 6", "Tiangong");
        Assignament astronaut7 = new Assignament("Astronaut 7", "ISS");
        Assignament astronaut8 = new Assignament("Astronaut 8", "ISS");
        Assignament astronaut9 = new Assignament("Astronaut 9", "ISS");
        Assignament astronaut10 = new Assignament("Astronaut 10", "ISS");
    
    
        List<Assignament> astronauts = Arrays.asList(
                astronaut1, astronaut2, astronaut3, astronaut4, astronaut5,
                astronaut6, astronaut7, astronaut8, astronaut9, astronaut10
        );
    
    
        Map<String, Long> spacecraftAstronautCount = astroService.getAstroData(astronauts);
    
    
        assertEquals(3L, spacecraftAstronautCount.get("Tiangong").longValue());
        assertEquals(7L, spacecraftAstronautCount.get("ISS").longValue());
    }
}
```
<h1 align="center">
  <img src="https://raw.githubusercontent.com/owamns/ExamenSustitutorio-3S2/main/Pregunta2/files/r3.png">
</h1>



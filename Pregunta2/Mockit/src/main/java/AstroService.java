import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AstroService {
    public Map<String, Long> getAstroData(List<Assignament> assignments) {
        Map<String, Long> spacecraftAstronautCount = new HashMap<>();

        for (Assignament assignment : assignments) {
            String spacecraft = assignment.getCraft();
            spacecraftAstronautCount.put(spacecraft, spacecraftAstronautCount.getOrDefault(spacecraft, 0L) + 1);
        }

        return spacecraftAstronautCount;
    }

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
}

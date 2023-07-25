import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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

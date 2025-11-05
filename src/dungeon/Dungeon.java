package dungeon;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import entities.Player;

public class Dungeon {
    public static Floor createPrimerPiso() {
        Floor f = new Floor("La Frontera del Silencio", "Un nivel inferior, las paredes rezuman ecos de antiguas plegarias.");

        // Room 1: Entrada
        f.addRoom(new Room(1,
                "Despertar en la Grieta",
                "Te incorporas entre escombros. Una grieta en el cielo filtra una luz fría.",
                Arrays.asList(
                        "Aurelion: ...¿Dónde estoy? La marca en mi mano arde con un leve calor.",
                        "Voz tenue: 'Despierta, Guardián. Los hilos del sello se han aflojado.'",
                        "Aurelion: Debo avanzar. Si recupero la reliquia, quizá todo tenga sentido."
                ),
                RoomType.ENTRANCE));


        // Room 2: Sala de enemigos
        f.addRoom(new Room(2,
                "Sala de la Guardia",
                "Sombras se mueven entre las columnas. Hay presencias que vigilan la entrada.",
                Arrays.asList(
                        "Aurelion: Deberé enfrentarme a los vigilantes si quiero avanzar.",
                        "Voz distante: 'Solo los dignos tomarán lo que yace más allá.'"
                ),
                RoomType.ENEMIES));

        // Room 3: Mercado del Silencio (mercader)
        f.addRoom(new Room(3,
                "Mercado del Silencio",
                "Un mercader paciente atiende entre telarañas. Intercambia objetos por Almas.",
                Arrays.asList(
                        "Mercader: 'Mis bienes no son baratos, pero son eficaces.'",
                        "Aurelion: Tal vez necesite algo antes del combate final."
                ),
                RoomType.MARKET));

        // Room 4: Sala de acertijo (puzzle)
        f.addRoom(new Room(4,
                "Sala del Reto",
                "Una estatua rota guarda un acertijo que resuena al entrar.",
                Arrays.asList(
                        "Aurelion: Algo aquí pide una respuesta. Debo prestar atención."
                ),
                RoomType.PUZZLE));

        // Room 5: Cámara del Martillo Silente (Boss)
        f.addRoom(new Room(5,
                "Cámara del Martillo Silente",
                "Una figura enorme se alza, cubriendo el techo con su silueta. El aire tiembla.",
                Arrays.asList(
                        "Thargron: 'Has llegado lejos, mortal. Muchos han fallado antes que tú.'",
                        "Aurelion: No permitiré que el sello caiga más. Me enfrentaré a ti.'",
                        "Thargron: 'Entonces ven. Que el martillo cante en tu honor o en tu fin.'"
                ),
                RoomType.BOSS));

        return f;
    }

    //Primer piso
    public static boolean playPrimerPiso(BufferedReader br, Player p) throws IOException {
        Floor f = createPrimerPiso();
        return f.play(br, p);
    }
}

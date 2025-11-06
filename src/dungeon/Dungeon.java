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

    // NUEVO: crear segundo piso con textos actualizados
    public static Floor createSegundoPiso() {
        Floor f = new Floor(
                "El Velo de los Juramentos",
                "Un templo distorsionado por pactos rotos y magia quebrada. Aquí se perdió su vínculo con lo arcano."
        );

        // Sala 1 — Atrio del Pacto
        f.addRoom(new Room(1,
                "Atrio del Pacto",
                "El atrio rezuma energía de pactos quebrados; las paredes parecen exudar memoria arcana.",
                Arrays.asList(
                        "Aurelion: “Los muros lloran pactos rotos. Aquí, mi magia fue arrancada… pero no olvidada.”"
                ),
                RoomType.ENTRANCE));

        // Sala 2 — Salón de los Susurros
        f.addRoom(new Room(2,
                "Salón de los Susurros",
                "Ecos quebrados reptan entre inscripciones suspendidas como humo arcano.",
                Arrays.asList(
                        "Susurrador: “…prometiste… no romperías el círculo… traición…”",
                        "Aurelion: “Fui traicionado. Pero mi palabra aún tiene poder.”"
                ),
                RoomType.ENEMIES));

        // Sala 3 — Cámara del Velo (Acertijo)
        f.addRoom(new Room(3,
                "Cámara del Velo",
                "Una inscripción flotante palpita esperando ser comprendida.",
                Arrays.asList(
                        "Inscripción: “Soy invisible, pero puedo romper reinos.",
                        "Soy palabra, pero también prisión. ¿Qué soy?”"
                ),
                RoomType.PUZZLE));

        // Sala 4 — Tejedora de Secretos (Tienda)
        f.addRoom(new Room(4,
                "Tejedora de Secretos",
                "Hilos de luz son tejidos por manos invisibles formando símbolos juramentados.",
                Arrays.asList(
                        "Lira: “Los pactos rotos dejan huecos… y yo los lleno. Magia, memoria, o engaño. Todo tiene precio.”",
                        "Objetos: Velo de Concentración — mejora magia por 3 combates.",
                        "Fragmento de Cántico — revive una vez con mitad de vida.",
                        "Tinta de Juramento — revela secretos en las paredes del templo."
                ),
                RoomType.MARKET));

        // Sala 5 — Umbral de la Voz Encadenada (Boss)
        f.addRoom(new Room(5,
                "Umbral de la Voz Encadenada",
                "Cadenas de luz silenciosa rodean una figura que canta sin sonido.",
                Arrays.asList(
                        "Selmira: “¿Vienes a reclamar lo que abandonaste? La magia no perdona, Aurelion.”",
                        "Aurelion: “No vine a pedir perdón. Vine a recuperar lo que me fue robado.”"
                ),
                RoomType.BOSS));

        return f;
    }

    // NUEVO: jugar segundo piso
    public static boolean playSegundoPiso(BufferedReader br, Player p) throws IOException {
        Floor f = createSegundoPiso();
        return f.play(br, p);
    }

    // NUEVO: crear tercer piso con textos actualizados
    public static Floor createTercerPiso() {
        Floor f = new Floor(
                "El Núcleo del Abismo",
                "El corazón del caos, donde la Grieta respira y la realidad se distorsiona. Aquí se perdió su divinidad."
        );

        // Sala 1 — Umbral de la Grieta (Entrada)
        f.addRoom(new Room(1,
                "Umbral de la Grieta",
                "El aire vibra y se desgarra en hilos de oscuridad latente.",
                Arrays.asList(
                        "Aurelion: “El aire se desgarra. Aquí, la realidad se retuerce. Mi divinidad yace en lo profundo.”"
                ),
                RoomType.ENTRANCE));

        // Sala 2 — Cámara de los Nacidos del Vacío (Enemigos)
        f.addRoom(new Room(2,
                "Cámara de los Nacidos del Vacío",
                "Presencias imposibles reptan por las paredes; el suelo no siempre es suelo.",
                Arrays.asList(
                        "Hijo del Abismo: “Somos el fin… somos el principio… tú eres el error…”",
                        "Aurelion: “Soy el equilibrio. Y he venido a cerrarlos.”"
                ),
                RoomType.ENEMIES));

        // Sala 3 — Cámara del Olvido (Acertijo)
        f.addRoom(new Room(3,
                "Cámara del Olvido",
                "La sombra de la nada se condensa en palabras que no existen.",
                Arrays.asList(
                        "Susurro múltiple: “No tengo forma, pero devoro todo.",
                        "No tengo fin, pero todos caen en mí. ¿Qué soy?”"
                ),
                RoomType.PUZZLE));

        // Sala 4 — Comerciante del Vacío (Tienda)
        f.addRoom(new Room(4,
                "Comerciante del Vacío",
                "Un tendero de ojos huecos exhibe objetos que no deberían existir.",
                Arrays.asList(
                        "Nul: “El abismo consume… pero también escupe. Yo recojo lo que queda. ¿Quieres comprarle al vacío?”",
                        "Objetos: Sangre de Grieta — mejora ataque por 3 combates.",
                        "Esquirla de Realidad — permite evitar un combate.",
                        "Ojo del Retorno — revela la posición del jefe."
                ),
                RoomType.MARKET));

        // Sala 5 — Trono del Portador (Boss)
        f.addRoom(new Room(5,
                "Trono del Portador",
                "El Portador se alza, y con él, la Grieta respira más hondo.",
                Arrays.asList(
                        "Zar’keth: “Tu divinidad fue devorada. Solo queda el vacío. ¿Aún crees que puedes cerrarlo?”",
                        "Aurelion: “Con el Emblema en mi mano y la voluntad intacta… cerraré lo que tú abriste.”"
                ),
                RoomType.BOSS));

        return f;
    }

    // NUEVO: jugar tercer piso
    public static boolean playTercerPiso(BufferedReader br, Player p) throws IOException {
        Floor f = createTercerPiso();
        return f.play(br, p);
    }
}

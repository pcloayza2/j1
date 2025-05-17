import java.util.HashMap;
import java.util.Map;

public class AFDWithTable {
    private enum State { q0, q1, q2 }

    // Tabla de transiciones como un mapa anidado: Estado -> (Símbolo -> Estado)
    private static Map<State, Map<Character, State>> transitionTable;

    // Inicializar la tabla de transiciones
    static {
        transitionTable = new HashMap<>();

        // Transiciones desde q0
        Map<Character, State> q0Transitions = new HashMap<>();
        q0Transitions.put('a', State.q1);
        q0Transitions.put('b', State.q2);
        transitionTable.put(State.q0, q0Transitions);

        // Transiciones desde q1
        Map<Character, State> q1Transitions = new HashMap<>();
        q1Transitions.put('a', State.q0);
        q1Transitions.put('b', State.q0);
        transitionTable.put(State.q1, q1Transitions);

        // Transiciones desde q2
        Map<Character, State> q2Transitions = new HashMap<>();
        q2Transitions.put('a', State.q0);
        q2Transitions.put('b', State.q0);
        transitionTable.put(State.q2, q2Transitions);
    }

    public static boolean procesarCadena(String input) {
        State current = State.q0;
        for (char c : input.toCharArray()) {
            // Verificar si el símbolo está en el alfabeto
            if (c != 'a' && c != 'b') {
                return false; // Símbolo no válido
            }
            // Obtener las transiciones para el estado actual
            Map<Character, State> transitions = transitionTable.get(current);
            // Verificar si existe una transición para el símbolo
            if (!transitions.containsKey(c)) {
                return false; // Transición no definida
            }
            // Aplicar la transición
            current = transitions.get(c);
        }
        // Acepta si termina en q0 (compatible con (ab(a|b))*)
        return current == State.q0;
    }

    // Método para imprimir la tabla de transiciones (para depuración)
    public static void printTransitionTable() {
        System.out.println("Tabla de Transiciones:");
        for (State state : transitionTable.keySet()) {
            Map<Character, State> transitions = transitionTable.get(state);
            for (Character symbol : transitions.keySet()) {
                System.out.println(state + " --" + symbol + "--> " + transitions.get(symbol));
            }
        }
    }

    public static void main(String[] args) {
        // Imprimir la tabla de transiciones
        printTransitionTable();

        // Pruebas
        String[] pruebas = {"", "ab", "aba", "abb", "abab", "ababa", "a", "b", "aa", "ba"};
        System.out.println("\nResultados de las pruebas:");
        for (String prueba : pruebas) {
            boolean aceptada = procesarCadena(prueba);
            System.out.println("Cadena: " + prueba + " -> " + (aceptada ? "Aceptada" : "Rechazada"));
        }
    }
}
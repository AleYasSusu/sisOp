package Software;

import Hardware.Interrupts;

public class InterruptHandling {
    public void handle(Interrupts irpt, int pc) {   // apenas avisa - todas interrupcoes neste momento finalizam o programa
        System.out.println("                                               Interrupcao "+ irpt+ "   pc: "+pc);
    }
}

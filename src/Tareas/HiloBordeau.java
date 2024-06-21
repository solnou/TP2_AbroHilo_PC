package Tareas;

import GestorMonitor.Monitor;
import RedPetri.RdP;

public class HiloBordeau extends Thread {

    private Monitor monitor;

    private final int[] transiciones = {0, 1};

    private final int[] demoras = {0, 100};

    private int clientesIngresados;

    private int totalClientes;

    public HiloBordeau (Monitor monitor, RdP red, int totalClientes) {
        this.monitor = monitor;
        this.setName("Hilo Bordeau");
        this.clientesIngresados = 0;
        this.totalClientes = totalClientes;
    }

    @Override
    public void run() {
        while (clientesIngresados < totalClientes) {
            for (int i = 0; i < transiciones.length; i++) {
                if (monitor.fireTransition(transiciones[i])) {
                    int[] vector_disparo = new int[12];
                    vector_disparo[transiciones[i]] = 1;

                    if (i == 0) {
                        clientesIngresados++;
                    }

                    try {
                        sleep(demoras[i]); // demora de la transicion
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        // Si se llega al total de clientes, se interrumpe el hilo
        System.out.println("NO SE ACEPTA EL INGRESO DE MAS CLIENTES");
    }
}
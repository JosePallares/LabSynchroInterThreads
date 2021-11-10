package labsynchrointerthreads;

// La clase consumidora de franjas
class ConsumidorDeFranjas implements java.lang.Runnable {

    // Una referencia al productor permite
    // acceder a†su mÈtodo de lectura
    private ProductorDeFranjas miReceptorDeFranjas;

    // El constructor sobrecargado permite 
    // indicar el productor
    public ConsumidorDeFranjas(
            ProductorDeFranjas miReceptorDeFranjas){
        this.miReceptorDeFranjas
                = miReceptorDeFranjas;
        this.miReceptorDeFranjas
                .setMiConsumidorDeFranjas(this);
    }


    @Override
    public void run() {
        System.out.println("Inicio deConsumidorDeFranjas");

        // Mientras que "nadie me pare"...
        while (Thread.currentThread().isInterrupted()
                == false )
        {
            try {
                // Llamada de un mÈtodo bloqueante
                // del productor

                int numeroFranja

                        = miReceptorDeFranjas.LeeFranjaRecibida();

                // Tratamiento de la trama recibida
                System.out.println("Franja consumida: "
                        + numeroFranja);

                Thread.sleep(500);

                tramaConsumida();

            } catch (InterruptedException ex) {
                System.out.println(
                        "InterruptedException "
                                + "en ConsumidorDeFranjas");
                break;
            }
        }
        System.out.println("Fin ConsumidorDeFranjas");
    }

    private synchronized void tramaConsumida() {
        notify();
    }

    public synchronized void esperaConsumo()
            throws InterruptedException{
        wait();
    }

}

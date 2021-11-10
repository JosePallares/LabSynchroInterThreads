package labsynchrointerthreads;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// La clase generadora de franjas
class ProductorDeFranjas implements java.lang.Runnable {

    // El objeto de almacenamiento de las "franjas"
    // ES una Queue que se utiliza para
    // un funcionamiento de tipo FIFO
    private Queue<Integer> colaFranjas
            = new LinkedList<Integer>();
    // Todo lo que hace falta para que el consumidor se guarde
    private ConsumidorDeFranjas miConsumidorDeFranjas;

    public void setMiConsumidorDeFranjas(
            ConsumidorDeFranjas miConsumidorDeFranjas) {
        this.miConsumidorDeFranjas = miConsumidorDeFranjas;
    }

    // El mÈtodo principal
    @Override
    public void run() {

        System.out.println("Inicio ProductorDeFranjas");

        // Mientras que "nadie me pare"...
        while (Thread.currentThread().isInterrupted()
                == false ) {

            // SimulaciÛn de una recepciÛn de franja
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ex) {
                System.out.println(
                        "InterruptedException "
                                + "en ProductorDeFranjas");
                break;
            }
            int numeroFranja = DameUnEnteroAleatorio();
            System.out.println("Franja recibida : " + numeroFranja);

            // Encolado de la franja recibida
            colaFranjas.add(numeroFranja);

            // Indica al threadConsumidor
            // que puede leer la franja
            avisarFranjaRecibida();

            // Si la cola est· llena
            // esperar que se vacÌe
            try {
                esperarColaVacia();
            } catch (InterruptedException ex) {
                System.out.println(
                        "InterruptedException "
                                + "en esperarColaVacia");
                break;
            }

        }
        System.out.println("Fin ProductorDeFranjas");
    }

    // Generador aleatorio de n˙meros enteros
    Random rand = new Random();
    private int DameUnEnteroAleatorio() {

        int randomNum = rand.nextInt(101);
        return randomNum;
    }

    // Este mÈtodo interviene en notify y
    // dede ser de tipo synchronized
    private synchronized void avisarFranjaRecibida(){
        notify();
    }

    // Este mÈtodo se llama cuando una nueva franja
    // se encola.
    // Permite comprobar si la cola est· saturada y
    // si es el caso, esperar a que se vacÌe.
    private void esperarColaVacia()
            throws InterruptedException{

        if( colaFranjas.size() >= 10 ){
            System.out.println("Cola saturada");
            while(colaFranjas.size() > 0){
                this.miConsumidorDeFranjas
                        .esperaConsumo();
            }
            System.out.println("Cola vacÌa");
        }
    }

    // Este mÈtodo de tipo public permite desapilar
    // las franjas recibidas. Utiliza wait
    // por lo que debe ser de tipo synchronized
    public synchronized int LeeFranjaRecibida()
            throws InterruptedException{

        // Mientras que hay des franjas a leeer
        // se leen
        if( colaFranjas.size() > 0)
            return colaFranjas.poll();

        // Si no, esperamos a la sigueinte
        System.out.println("Consumidor espera");
        wait(); // La llamada del wait libera el bloqueo
        // del synchronized para permitir la llamada
        // a†avisarFranjaRecibida()
        // En la salida del wait el thread retoma
        // el bloqueo.
        return colaFranjas.poll();
    }
}

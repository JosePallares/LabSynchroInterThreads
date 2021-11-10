/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labsynchrointerthreads;

public class LabSynchroInterThreads {

    public static void main(String[] args) {
        ProductorConsumidor productorConsumidor
                = new ProductorConsumidor();
        productorConsumidor.Action();

    }
}


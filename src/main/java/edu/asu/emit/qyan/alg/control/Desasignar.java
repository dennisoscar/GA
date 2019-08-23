package edu.asu.emit.qyan.alg.control;

public class Desasignar {

    public GrafoMatriz g;

    public Desasignar(GrafoMatriz g) {
        super();
        this.g = g;
    }

    public void restarTiempo() {

        for (int i = 0; i < g.grafo.length; i++) {

            for (int k = 0; k < g.grafo.length; k++) {

                for (int x = 0; x < g.grafo[i][k].listafs.length; x++) {

                    g.grafo[i][k].listafs[x].libreOcupado = 0;

                }

            }

        }


    }


}

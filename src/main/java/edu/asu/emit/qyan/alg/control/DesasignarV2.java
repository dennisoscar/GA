package edu.asu.emit.qyan.alg.control;

public class DesasignarV2 {
    public GrafoMatrizV2 g;

    public DesasignarV2(GrafoMatrizV2 g) {
        super();
        this.g = g;
    }

    public void restarTiempo() {

        for (int i = 0; i < g.grafo.length; i++) {

            for (int k = 0; k < g.grafo.length; k++) {

                for (int x = 0; x < g.grafo[i][k].listafibra.length; x++) {

                    for (int j = 0; j < g.grafo[i][k].listafibra[x].listafs.length; j++) {
                        g.grafo[i][k].listafibra[x].listafs[j].libreOcupado = 0;
                    }

                }

            }

        }
    }
}

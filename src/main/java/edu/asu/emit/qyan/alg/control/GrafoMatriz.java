package edu.asu.emit.qyan.alg.control;

public class GrafoMatriz {

    public Enlace[][] grafo;
    int nodos;
    int[] cadenaVertices;

    GrafoMatriz() {


    }

    public GrafoMatriz(int[] serieNodos) {
        cadenaVertices = new int[serieNodos.length];
        for (int i = 0; i < serieNodos.length; i++) {
            cadenaVertices[i] = serieNodos[i];
        }
        nodos = serieNodos.length;
        //	System.out.println(nodos.length);
        grafo = new Enlace[serieNodos.length][serieNodos.length];
    }

    public void InicializarGrafo(Enlace[][] grafo) {

        for (int x = 0; x < grafo.length; x++) {
            for (int y = 0; y < grafo[x].length; y++) {
                grafo[x][y] = new Enlace(0, 0, 5);

                for (int k = 0; k < grafo[x][y].listafs.length; k++) {

                    grafo[x][y].listafs[k] = new FrecuenciaSlot(0, 0, 0);
                }

            }
        }
    }

    public void agregarRuta(int origen, int destino, int distancia, int cantfs) {
        //	System.out.println(origen);
        //	System.out.println(destino);
        int n1 = posicionNodo(origen);
        //	System.out.print(n1);

        int n2 = posicionNodo(destino);
        grafo[n1][n2].distancia = distancia;
        grafo[n1][n2].cantfs = cantfs;

        grafo[n2][n1].distancia = distancia;
        grafo[n2][n1].cantfs = cantfs;

    }

    public int posicionNodo(int nodo) {
        for (int i = 0; i < cadenaVertices.length; i++) {
            if (cadenaVertices[i] == nodo) return i;
        }
        return -1;
    }

}

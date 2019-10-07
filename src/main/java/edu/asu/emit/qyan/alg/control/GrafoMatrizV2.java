package edu.asu.emit.qyan.alg.control;

import py.una.pol.model.SimuladorRSA;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GrafoMatrizV2 {
    public EnlaceV2[][] grafo;
    int nodos;
    int[] cadenaVertices;

    GrafoMatrizV2() {

    }

    public GrafoMatrizV2(int[] serieNodos) {
        cadenaVertices = new int[serieNodos.length];
        for (int i = 0; i < serieNodos.length; i++) {
            cadenaVertices[i] = serieNodos[i];
        }
        nodos = serieNodos.length;
        //	System.out.println(nodos.length);
        grafo = new EnlaceV2[serieNodos.length][serieNodos.length];
    }

    public void InicializarGrafo(EnlaceV2[][] grafo, int tamanhoSlot) {
        int tamanhoFibra;
        Properties config = new Properties();
        InputStream configInput = null;
        configInput = SimuladorRSA.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            config.load(configInput);
        } catch (IOException e) {
            System.out.println("No se pudo leer el tamaño de la fibra del properties");
        }
//        System.out.println(config.getProperty("cantidadDeCaminos"));
        tamanhoFibra = Integer.parseInt(config.getProperty("tamanhoFibra"));
        for (int x = 0; x < grafo.length; x++) {
            for (int y = 0; y < grafo[x].length; y++) {
                //tam es el tamaño que indica la cantidad de fibra en cada enlace
                grafo[x][y] = new EnlaceV2(0, 0, tamanhoFibra);
                //tam es el tamaño que indica la cantidad de fs en cada enlace
                for (int k = 0; k < grafo[x][y].listafibra.length; k++) {
                    //instanciar el vector de frecuencySlot dentro de la fibra
                    grafo[x][y].listafibra[k] = new FibraOptica(tamanhoSlot);
                    for (int i = 0; i < grafo[x][y].listafibra[k].listafs.length; i++) {
                        grafo[x][y].listafibra[k].listafs[i] = new FrecuenciaSlot(0, 0, 0);
                    }

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

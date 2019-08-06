package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.abstracts.BaseVertex;

import java.util.List;

public class BuscarSlot {

    public GrafoMatriz g;
    public List<Path> caminos;

    BuscarSlot(GrafoMatriz grafomatriz, List<Path> caminos) {
        this.g = grafomatriz;
        this.caminos = caminos;

    }

    public resultadoSlot concatenarCaminos(int fs) {

        resultadoSlot resultfalso = null;
        int contador = 0;
        //	int[] vectorResultado = new int[g.grafo[0][0].listafs.length];
        resultadoSlot respuesta = new resultadoSlot();
        respuesta.vectorAsignacion = new int[g.grafo[0][0].listafs.length];

        int res = 0;
        //	System.out.println(caminos.size());
        for (int a = 0; a < caminos.size(); a++) {
            for (int i = 0; i < respuesta.vectorAsignacion.length; i++) {

                respuesta.vectorAsignacion[i] = 0;
            }

            Path cam = caminos.get(a);

            System.out.println(cam);

            respuesta.camino = cam;

            //	GrafoMatriz posicion = new GrafoMatriz(g.cadenaVertices);

            //se concatena los vectores de los fs de cada enlace del primer camino examinado
            for (int i = 0; i < cam.get_vertex_list().size() - 1; i++) {

                //	   System.out.println(cam.get_vertex_list().size());

                BaseVertex id1 = cam.get_vertex_list().get(i);
                BaseVertex id2 = cam.get_vertex_list().get(i + 1);

                int k = id1.get_id();
                int l = id2.get_id();

                //   System.out.println(k);
                // 	System.out.println(l);
                int n1 = g.posicionNodo(k);
                int n2 = g.posicionNodo(l);
                //	System.out.println(n1);
                //	System.out.println(n2);

                //	g.grafo[n1][n2].listafs[0].libreOcupado = 1;
                //	g.grafo[n1][n2].listafs[1].libreOcupado = 1;
                //	g.grafo[n1][n2].listafs[2].libreOcupado = 1;
                for (int x = 0; x < g.grafo[n1][n2].listafs.length; x++) {

                    if (g.grafo[n1][n2].listafs[x].libreOcupado == 0 && respuesta.vectorAsignacion[x] == 0)

                        respuesta.vectorAsignacion[x] = 0;
                    else {

                        respuesta.vectorAsignacion[x] = 1;
                    }

                }
            }

            // Una vez que tenemos el vector concatenado se recorre para saber si cumple con las condiciones.
            int contadorActual = 0;
            int contadorFinal = 0;
            int indiceActual = 0;
            int indiceFinal = 0;

            for (int i = 0; i < respuesta.vectorAsignacion.length; i++) {

                boolean ban = false;

                if (respuesta.vectorAsignacion[i] == 0) {

                    contadorActual++;
                    indiceActual = i;
                    ban = true;
                }

                if (contadorActual >= fs && contadorActual > contadorFinal) {

                    indiceFinal = indiceActual;
                    contadorFinal = contadorActual;
                }
                if (!ban) {

                    contadorActual = 0;
                }

            }

            if (contadorFinal >= fs) {

                //  indiceFinal = (indiceFinal - (int)(contadorFinal/2));
                respuesta.indice = indiceFinal;
                respuesta.contador = contadorFinal;
                respuesta.cantidadfs = fs;
                res = contadorFinal;
                //  res = true;
                break;
            }


        }

        if (res >= fs)
            return respuesta;

        else {
            return resultfalso;
        }

    }


}

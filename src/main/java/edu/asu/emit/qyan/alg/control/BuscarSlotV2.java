package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.abstracts.BaseVertex;

import java.util.ArrayList;
import java.util.List;

public class BuscarSlotV2 {

    public GrafoMatrizV2 g;
    public List<Path> caminos;

    public BuscarSlotV2(GrafoMatrizV2 grafomatriz, List<Path> caminos) {
        this.g = grafomatriz;
        this.caminos = caminos;

    }

    public ResultadoSlotV2 concatenarCaminos(int fs) {

        ResultadoSlot resultfalso = null;
        ResultadoSlotV2 resultFalsoV2 = null;
        int contador = 0;
        //	int[] vectorResultado = new int[g.grafo[0][0].listafs.length];
        ResultadoSlotV2 respuestaV2 = new ResultadoSlotV2();
        respuestaV2.vectorAsignacion = new int[g.grafo[0][0].listafibra[0].listafs.length];
        respuestaV2.setFibraList(new ArrayList<Integer>());

        ResultadoSlot respuesta = new ResultadoSlot();
        respuesta.vectorAsignacion = new int[g.grafo[0][0].listafibra[0].listafs.length];

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

            //Se inicializa el vector resultadoFibra para cada camino
//            ResultadoFibra[] resultadoFibraList = new ResultadoFibra[cam.get_vertex_list().size() - 1];
//
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

                for (int x = 0; x <= g.grafo[n1][n2].listafibra.length; x++) {
//                    resultadoFibraList[x].setResultadoSlotList(new ArrayList<ResultadoSlot>());

                    for (int j = 0; j < g.grafo[n1][n2].listafibra[x].listafs.length; j++) {
                        //condicion que hace que se cumplan todas las reglas de eon
                        // aca se asegura
                        if (g.grafo[n1][n2].listafibra[x].listafs[j].libreOcupado == 0 && respuestaV2.vectorAsignacion[j] == 0)

                            respuestaV2.vectorAsignacion[x] = 0;
                        else {

                            respuestaV2.vectorAsignacion[x] = 1;
                        }

                    }
                    // Una vez que tenemos el vector concatenado se recorre para saber si cumple con las condiciones.
                    int contadorActual = 0;
                    int contadorFinal = 0;
                    int indiceActual = 0;
                    int indiceFinal = 0;

                    for (int K = 0; K < respuestaV2.vectorAsignacion.length; K++) {

                        boolean ban = false;

                        if (respuestaV2.vectorAsignacion[K] == 0) {

                            contadorActual++;
                            indiceActual = K;
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
//                        respuesta.indice = indiceFinal;
//                        respuesta.contador = contadorFinal;
//                        respuesta.cantidadfs = fs;
//                        res = contadorFinal;
                        //para la version de multifibra
                        respuestaV2.indice = indiceFinal;
                        respuestaV2.contador = contadorFinal;
                        respuestaV2.cantidadfs = fs;
                        respuestaV2.addFibra(x);
                        res = contadorFinal;
//                        resultadoFibraList[x].addResultadoSlotList(respuesta);
//                        resultadoFibraList[x].setIndiceFibra(x);
                        System.out.println(respuestaV2.getFibraList());
                        //  res = true;
                        break;
                    } else {
                        //contador si de bloqueo de fibra, es decir, si en esa fibra no se pudo encontrar l
                        //FS necesarios
                        contador++;
                        System.out.println("hubo un bloqueo en la fibra:  " + x + " " +
                                "cantidad de fibras con bloqueo:   " + contador);
                    }
                }
                //Si para el conjunto de fibras que pertenece a un enlace no se pudo emcontrar un espacio necesario
                //para los FS requeridos para la demanada se hace un break y se pasa al siguiente camino.
                if (contador == g.grafo[n1][n2].listafibra.length) {
                    break;
                }
            }

            // Una vez que tenemos el vector concatenado se recorre para saber si cumple con las condiciones.
            int contadorActual = 0;
            int contadorFinal = 0;
            int indiceActual = 0;
            int indiceFinal = 0;

            for (int i = 0; i < respuestaV2.vectorAsignacion.length; i++) {

                boolean ban = false;

                if (respuestaV2.vectorAsignacion[i] == 0) {

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
                respuestaV2.indice = indiceFinal;
                respuestaV2.contador = contadorFinal;
                respuestaV2.cantidadfs = fs;
                res = contadorFinal;
                //  res = true;
                break;
            }


        }

        if (res >= fs)
            return respuestaV2;

        else {
            return resultFalsoV2;
        }

    }
}

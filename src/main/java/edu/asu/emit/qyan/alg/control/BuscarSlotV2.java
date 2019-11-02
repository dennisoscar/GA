package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.abstracts.BaseVertex;

import java.util.List;

public class BuscarSlotV2 {

    public GrafoMatrizV2 g;
    public List<Path> caminos;
    public int indiceFS = 0;

    public BuscarSlotV2(GrafoMatrizV2 grafomatriz, List<Path> caminos) {
        this.g = grafomatriz;
        this.caminos = caminos;

    }

    public ResultadoSlotV2 concatenarCaminos(int fs) {

        ResultadoSlot resultfalso = null;
        ResultadoSlotV2 resultFalsoV2 = null;
        //	int[] vectorResultado = new int[g.grafo[0][0].listafs.length];
        ResultadoSlotV2 respuestaV2 = new ResultadoSlotV2();
        //se inicializa el vector resultado con la cantidad de frecuncySlot de una fibra del grafo
        //Ahora mismo solo se cuenta con una cantidad fija de FS, no es dinamica
        respuestaV2.auxFSResultado = new int[g.grafo[0][0].listafibra[0].listafs.length];

        //	System.out.println(caminos.size());
        //cargamos en una variable la cantidad de caminos para el primer Request
        int cant_caminos = caminos.size();
        respuestaV2.indiceFibra = new int[g.grafo[0][0].listafibra.length];
        respuestaV2.indiceFS = -1;
        //inicializamos en 0 la matriz que representa los FS de cada lista de Fibra


        for (int cant_caminosCount = 0; cant_caminosCount < cant_caminos; cant_caminosCount++) {

            //limpiamos los array auxiliares por cada camnino.


            Path camino = caminos.get(cant_caminosCount);
            int[][] auxMatrizFSF = new int[g.grafo[0][0].listafibra[0].listafs.length][camino.get_vertex_list().size() - 1];
            auxMatrizFSF = clearMat(auxMatrizFSF);
            System.out.println("Camino nro --> " + cant_caminosCount + "[" + camino + "]");
            respuestaV2.setCamino(camino);

            for (int enlaceCount = 0; enlaceCount < camino.get_vertex_list().size() - 1; enlaceCount++) {
                BaseVertex id1 = camino.get_vertex_list().get(enlaceCount);
                BaseVertex id2 = camino.get_vertex_list().get(enlaceCount + 1);

                int k = id1.get_id();
                int l = id2.get_id();

                //   System.out.println(k);
                // 	System.out.println(l);
                int n1 = g.posicionNodo(k);
                int n2 = g.posicionNodo(l);
                int[][] auxFS = new int[g.grafo[n1][n2].listafibra[0].listafs.length][g.grafo[n1][n2].listafibra.length];
                //recorremos cada lista de fibra que pertenece al enlace
                for (int fibraCount = 0; fibraCount < g.grafo[n1][n2].listafibra.length; fibraCount++) {
                    //recorremos cafa FS que pertenece a la fibra
                    // la variable existFS indica si existe la cantidad de FS que necesita el Request en la fibra
                    boolean existFS = true;
                    int fsCount = 0;
                    for (int frecuencySlotCount = 0; frecuencySlotCount < g.grafo[n1][n2].listafibra[fibraCount].listafs.length; frecuencySlotCount++) {
                        if (existFS) {
                            fsCount = fs;
                        }
                        //0 significa que esta libre el FS de la fibra
                        if (g.grafo[n1][n2].listafibra[fibraCount].listafs[frecuencySlotCount].libreOcupado == 0
                        ) {
                            fsCount--;
                            existFS = false;
                            if (fsCount == 0) {
                                // 1 indica que apartir de este indice se puede asignar la cantidad de FS que necesita el request
                                auxMatrizFSF[frecuencySlotCount - fs + 1][enlaceCount] = 1;
                                // se vuelve atras al indici siguiente del que se guardo anteriormente. para que en la siguuiente iteracion se continue buscando
                                frecuencySlotCount = frecuencySlotCount - fs + 1;
                                existFS = true;
                            }

                        } else {
                            existFS = true;
                        }

                    }
                }


            }
            respuestaV2.auxFSResultado = clearArray(respuestaV2.auxFSResultado, 1);
            for (int FS_AuxMatrizFSF = 0; FS_AuxMatrizFSF < g.grafo[0][0].listafibra[0].listafs.length; FS_AuxMatrizFSF++) {
                for (int Col_AuxMatrizFSF = 0; Col_AuxMatrizFSF < camino.get_vertex_list().size() - 1; Col_AuxMatrizFSF++) {
                    if (auxMatrizFSF[FS_AuxMatrizFSF][Col_AuxMatrizFSF] == 0) {
                        respuestaV2.auxFSResultado[FS_AuxMatrizFSF] = 0;
                        break;
                    }
                }
            }
            // se selecciona el indice de FS por FF.
            for (int fsCount = 0; fsCount < g.grafo[0][0].listafibra[0].listafs.length; fsCount++) {
                if (respuestaV2.auxFSResultado[fsCount] == 1) {
                    respuestaV2.setIndiceFS(fsCount);
                    break;
                }
            }
            respuestaV2.indiceFibra = clearArray(respuestaV2.indiceFibra, -1);
            for (int enlaceCount = 0; enlaceCount < camino.get_vertex_list().size() - 1; enlaceCount++) {
                BaseVertex id1 = camino.get_vertex_list().get(enlaceCount);
                BaseVertex id2 = camino.get_vertex_list().get(enlaceCount + 1);

                int k = id1.get_id();
                int l = id2.get_id();

                //   System.out.println(k);
                // 	System.out.println(l);
                int n1 = g.posicionNodo(k);
                int n2 = g.posicionNodo(l);

                //se selecciona el indice de fibra por FF
                for (int fibraCount = 0; fibraCount < g.grafo[n1][n2].listafibra.length; fibraCount++) {
                    if (g.grafo[n1][n2].listafibra[fibraCount].listafs[respuestaV2.getIndiceFS()].libreOcupado == 0) {
                        respuestaV2.indiceFibra[enlaceCount] = fibraCount;
                        break;
                    }
                }
            }
            if (respuestaV2.getIndiceFS() != -1) {
                System.out.println("Respuesta --> " + respuestaV2.toString());
                return respuestaV2;
            }
        }
        return resultFalsoV2;
    }

    int[][] clearMat(int[][] mat) {
        for (int a = 0; a < mat.length; a++) {
            for (int b = 0; b < mat[a].length; b++) {
                mat[a][b] = 0;
            }
        }
        return mat;
    }

    int[] clearArray(int[] array, int value) {
        for (int a = 0; a < array.length; a++) {
            array[a] = value;
        }
        return array;
    }
}
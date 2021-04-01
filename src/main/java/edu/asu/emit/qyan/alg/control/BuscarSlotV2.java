package edu.asu.emit.qyan.alg.control;


import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.abstracts.BaseVertex;
import py.una.pol.model.Metodo;
import py.una.pol.model.SimuladorRSA;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class BuscarSlotV2 {

    public GrafoMatrizV2 g;
    public List<Path> caminos;
    public int indiceFS = 0;
    Properties config = new Properties();
    InputStream configInput;


    public BuscarSlotV2(GrafoMatrizV2 grafomatriz, List<Path> caminos) {
        this.g = grafomatriz;
        this.caminos = caminos;

    }

    public ResultadoSlotV2  concatenarCaminos(int fs) {
        configInput = SimuladorRSA.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            config.load(configInput);
        } catch (IOException e) {
            System.out.println("No se pudo leer la cantidad de caminos del properties");
        }
        Double alfa = Double.valueOf(config.getProperty("alfa"));
        ResultadoSlot resultfalso = null;
        ResultadoSlotV2 resultFalsoV2 = null;
        //	int[] vectorResultado = new int[g.grafo[0][0].listafs.length];
        ResultadoSlotV2 respuestaV2 = new ResultadoSlotV2();
        //se inicializa el vector resultado con la cantidad de frecuncySlot de una fibra del grafo
        //Ahora mismo solo se cuenta con una cantidad fija de FS, no es dinamica
        respuestaV2.auxFSResultado = new int[g.grafo[0][0].listafibra[0].listafs.length];

        //cargamos en una variable la cantidad de caminos para el primer Request
        int cant_caminos = caminos.size();

        //inicializamos en 0 la matriz que representa los FS de cada lista de Fibra

        for (int cant_caminosCount = 0; cant_caminosCount < cant_caminos; cant_caminosCount++) {

            //limpiamos los array auxiliares por cada camnino.
            respuestaV2.indiceFS = -1;
            Path camino = caminos.get(cant_caminosCount);
            respuestaV2.indiceFibra = new int[camino.get_vertex_list().size() - 1];
            int[][] auxMatrizFSF = new int[g.grafo[0][0].listafibra[0].listafs.length][camino.get_vertex_list().size() - 1];
            auxMatrizFSF = clearMat(auxMatrizFSF);
            System.out.println("Camino nro --> " + cant_caminosCount + "[" + camino + "]");
            respuestaV2.setCamino(camino);
            respuestaV2.setCantidadfs(fs);

            for (int enlaceCount = 0; enlaceCount < camino.get_vertex_list().size() - 1; enlaceCount++) {
                BaseVertex id1 = camino.get_vertex_list().get(enlaceCount);
                BaseVertex id2 = camino.get_vertex_list().get(enlaceCount + 1);

                int k = id1.get_id();
                int l = id2.get_id();

                int n1 = g.posicionNodo(k);
                int n2 = g.posicionNodo(l);
                int[][] auxFS = new int[g.grafo[n1][n2].listafibra[0].listafs.length][g.grafo[n1][n2].listafibra.length];
                //recorremos cada lista de fibra que pertenece al enlace
                for (int fibraCount = 0; fibraCount < g.grafo[n1][n2].listafibra.length ; fibraCount++) {
                    //recorremos cafa FS que pertenece a la fibra
                    // la variable existFS indica si existe la cantidad de FS que necesita el Request en la fibra
                    if( config.getProperty("FF").equals(Metodo.ACTIVO.name())
                            || config.getProperty("MC").equals(Metodo.ACTIVO.name())
                            || config.getProperty("MW").equals(Metodo.ACTIVO.name())
                            || g.grafo[n1][n2].listafibra[fibraCount].getTipoFs() == null
                            || g.grafo[n1][n2].listafibra[fibraCount].getTipoFs().equals(String.valueOf(fs))) {
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
            }
            respuestaV2.auxFSResultado = clearArray(respuestaV2.auxFSResultado, 1);
            for (int FS_AuxMatrizFSF = 0; FS_AuxMatrizFSF < g.grafo[0][0].listafibra[0].listafs.length; FS_AuxMatrizFSF++) {
                for (int Col_AuxMatrizFSF = 0; Col_AuxMatrizFSF < camino.get_vertex_list().size() - 1; Col_AuxMatrizFSF++) {
                    if (auxMatrizFSF[FS_AuxMatrizFSF][Col_AuxMatrizFSF] == 0 ) {
                        respuestaV2.auxFSResultado[FS_AuxMatrizFSF] = 0;
                        break;
                    }
                }
            }
//            // se selecciona el indice de FS por FF.
//            for (int fsCount = 0; fsCount < g.grafo[0][0].listafibra[0].listafs.length; fsCount++) {
//                if (respuestaV2.auxFSResultado[fsCount] == 1) {
//                    respuestaV2.setIndiceFS(fsCount);
//                    break;
//                }
//            }
            double xt_min_full = 0;
            double xt_min_fs_menor = 0;
            double xt_th = 0;
            int fs_solicitado = fs;
            int k;
            int l;
            int n1;
            int n2;
            double xt_min_fs;
            int core_result;

            /**
             * variables para el segundo recorrido
             */
            double xt_min_fulll = 0;
            double xt_min_fs_menorr = 0;
            int kk;
            int ll;
            int nn1;
            int nn2;
            double xt_min_fss;
            int core_resultt;

            boolean band = true;

            respuestaV2.indiceFibra = clearArray(respuestaV2.indiceFibra, -1);
            for( int fs_result = 0; fs_result <= Integer.parseInt(config.getProperty("tamanhoSlot"))-fs; fs_result++){
                if(respuestaV2.auxFSResultado[fs_result] == 1){
                        band = true;
                        xt_min_full = 0;
                        xt_min_fs_menor = 0;
                        xt_th = 0;
                        for (int enlaceCount = 0; enlaceCount < camino.get_vertex_list().size() - 1
                                && band == true; enlaceCount++) {
                            BaseVertex id1 = camino.get_vertex_list().get(enlaceCount);
                            BaseVertex id2 = camino.get_vertex_list().get(enlaceCount + 1);

                            k = id1.get_id();
                            l = id2.get_id();
                            band = true;
                            n1 = g.posicionNodo(k);
                            n2 = g.posicionNodo(l);
                            /*
                             * creamos una lista clonada de las fibras para ordendar de acuerdo a la (prioridad, mc o mw)
                             * el ordenamiento se hace de acuerdo a la seleccion de nucleo elegida en la configuracion
                             */
                            List<FibraOptica> fibrasListPriori;
                            if (config.getProperty("TODE").equals(Metodo.ACTIVO.name())) {
                                FibraOptica[] fibrasPriorizadas = g.grafo[n1][n2].listafibra;
                                fibrasListPriori = new ArrayList<>(Arrays.asList(fibrasPriorizadas));
                                fibrasListPriori.sort(Comparator.comparing(FibraOptica::getPrioridad));
                                g.setTodeMark(false);
                            } else if (config.getProperty("MW").equals(Metodo.ACTIVO.name())) {
                                FibraOptica[] fibrasPriorizadas = g.grafo[n1][n2].listafibra;
                                fibrasListPriori = new ArrayList<>(Arrays.asList(fibrasPriorizadas));
                                fibrasListPriori.sort(Comparator.comparing(FibraOptica::getMw));
                            } else if (config.getProperty("MC").equals(Metodo.ACTIVO.name())) {
                                FibraOptica[] fibrasPriorizadas = g.grafo[n1][n2].listafibra;
                                fibrasListPriori = new ArrayList<>(Arrays.asList(fibrasPriorizadas));
                                fibrasListPriori.sort(Comparator.comparing(FibraOptica::getMc));
                            } else {
                                FibraOptica[] fibrasPriorizadas = g.grafo[n1][n2].listafibra;
                                fibrasListPriori = new ArrayList<>(Arrays.asList(fibrasPriorizadas));
                            }

                            /*
                             * recorremos la lista ordenada y el count empieza por la fibra con prioridad mas baja y vamos
                             * aumentando
                             */
                            xt_min_fs = 0;
                            xt_min_fs_menor = 99999999;
                            boolean existe_menor = false;
                            for (FibraOptica item : fibrasListPriori) {
                                core_result = item.getId();
                                /*
                                 * preguntamos tambien si cumple la condicion de si esa fibra puede almacenar una peticion
                                 * con el FS demandado en el caso de que sea TODE, en otro caso
                                 */
                                if (g.grafo[n1][n2].listafibra[core_result].listafs[fs_result].libreOcupado == 0
                                        && (config.getProperty("MC").equals(Metodo.ACTIVO.name())
                                        || config.getProperty("MW").equals(Metodo.ACTIVO.name())
                                        || config.getProperty("FF").equals(Metodo.ACTIVO.name())
                                        || g.grafo[n1][n2].listafibra[core_result].getTipoFs() == null
                                        || g.grafo[n1][n2].listafibra[core_result].getTipoFs().equals(String.valueOf(fs)
                                ))) {

                                    band = true;
                                    for (int indice_mat_ady = 0; indice_mat_ady < Integer.parseInt(config.getProperty("tamanhoFibra"))
                                            && band == true; indice_mat_ady++) {
                                        if (g.matrizAdyacencia[indice_mat_ady][core_result] == true
                                                && g.grafo[n1][n2].listafibra[core_result].listafs[fs_result].libreOcupado != 0)
                                            xt_min_fs += g.xt;

                                        if (xt_min_fs > xt_th) {
                                            band = false;
                                        }
                                    }

                                    if (xt_min_fs < xt_min_fs_menor && band != false) {
                                        existe_menor = true;
                                        respuestaV2.indiceFibra[enlaceCount] = core_result;
                                        respuestaV2.indiceFS = fs_result;
                                        xt_min_fs_menor = xt_min_fs;
                                    }
                                }
                                if (existe_menor)
                                    xt_min_full += xt_min_fs_menor;
                                if (xt_min_full > xt_th) {
                                    band = false;
                                }
                            }
                        }

                    for( int fs_required = fs_result + 1; fs_required < fs_result + fs_solicitado  && band != false; fs_required++) {
                        for (int enlaceCount = 0; enlaceCount < camino.get_vertex_list().size() - 1
                                && band == true; enlaceCount++) {
                            BaseVertex id1 = camino.get_vertex_list().get(enlaceCount);
                            BaseVertex id2 = camino.get_vertex_list().get(enlaceCount + 1);

                            kk = id1.get_id();
                            ll = id2.get_id();
                            band = true;
                            nn1 = g.posicionNodo(kk);
                            nn2 = g.posicionNodo(ll);

                            /*
                             * recorremos la lista ordenada y el count empieza por la fibra con prioridad mas baja y vamos
                             * aumentando
                             */
                            xt_min_fss = 0;
                            xt_min_fs_menorr = 99999999;
                            boolean existe_menorr = false;

                                core_resultt = respuestaV2.indiceFibra[enlaceCount];
                                /*
                                 * preguntamos tambien si cumple la condicion de si esa fibra puede almacenar una peticion
                                 * con el FS demandado en el caso de que sea TODE, en otro caso
                                 */
                                if (g.grafo[nn1][nn2].listafibra[core_resultt].listafs[respuestaV2.getIndiceFS()].libreOcupado == 0
                                        && (config.getProperty("MC").equals(Metodo.ACTIVO.name())
                                        || config.getProperty("MW").equals(Metodo.ACTIVO.name())
                                        || config.getProperty("FF").equals(Metodo.ACTIVO.name())
                                        || g.grafo[nn1][nn2].listafibra[core_resultt].getTipoFs() == null
                                        || g.grafo[nn1][nn2].listafibra[core_resultt].getTipoFs().equals(String.valueOf(fs)
                                ))) {

                                    band = true;
                                    for (int indice_mat_ady = 0; indice_mat_ady < Integer.parseInt(config.getProperty("tamanhoFibra"))
                                            && band == true; indice_mat_ady++) {
                                        if (g.matrizAdyacencia[indice_mat_ady][core_resultt] == true
                                                && g.grafo[nn1][nn2].listafibra[core_resultt].listafs[respuestaV2.indiceFS].libreOcupado != 0)
                                            xt_min_fss += g.xt;

                                        if (xt_min_fss > xt_th) {
                                            band = false;
                                        }
                                    }

                                    if (xt_min_fss < xt_min_fs_menorr && band != false) {
                                        existe_menorr = true;
                                        xt_min_fs_menorr = xt_min_fss;
                                    }
                                }
                                if (existe_menorr)
                                    xt_min_fulll += xt_min_fs_menorr;
                                if (xt_min_fulll > xt_th) {
                                    band = false;
                                }
                        }
                    }
                }
                if(band == true) {
                    break;
                } else {
                    respuestaV2.indiceFibra = clearArray(respuestaV2.indiceFibra, -1);
                    respuestaV2.indiceFS = -1;
                }
            }
            if (respuestaV2.getIndiceFS() != -1) {
                System.out.println("Respuesta --> " + respuestaV2.toString());
                return respuestaV2;
            }


//            Agregar el proceso de sumatoria de XT por cada fibra en todos los enlaces
//            recorremos los fs y sus adyacentes por el enlace y vamos sumando en un array XTS donde el indice del fs
//            es el indice del  XTS  donde vamos sumando.
//            luego recorremos el array XTS y comparamos el valor del XT con el XTth, si supera, agregamos 0 en las filas del
//          array FS_AuxMatrizFSF que corresponden al indice del array XTS.


            //En caso de no poder selecionar el indice de fs significa que no cumplen las condiciones
            //de contiguidad para ese camino por lo que ya retornamos resultfalso que indica 1 bloqueo
//            if(respuestaV2.getIndiceFS()!=-1) {
//                respuestaV2.indiceFibra = clearArray(respuestaV2.indiceFibra, -1);
////                for (int enlaceCount = 0; enlaceCount < camino.get_vertex_list().size() - 1; enlaceCount++) {
////                    BaseVertex id1 = camino.get_vertex_list().get(enlaceCount);
////                    BaseVertex id2 = camino.get_vertex_list().get(enlaceCount + 1);
////
////                    int k = id1.get_id();
////                    int l = id2.get_id();
////
////                    //   System.out.println(k);
////                    // 	System.out.println(l);
////                    int n1 = g.posicionNodo(k);
////                    int n2 = g.posicionNodo(l);
////
////                    /*
////                     * creamos una lista clonada de las fibras para ordendar de acuerdo a la (prioridad, mc o mw)
////                     * el ordenamiento se hace de acuerdo a la seleccion de nucleo elegida en la configuracion
////                     */
////                    List<FibraOptica> fibrasListPriori;
////                    if(config.getProperty("TODE").equals(Metodo.ACTIVO.name())) {
////                        FibraOptica[] fibrasPriorizadas = g.grafo[n1][n2].listafibra;
////                        fibrasListPriori = new ArrayList<>(Arrays.asList(fibrasPriorizadas));
////                        fibrasListPriori.sort(Comparator.comparing(FibraOptica::getPrioridad));
////                        g.setTodeMark(false);
////                    }else if(config.getProperty("MW").equals(Metodo.ACTIVO.name())) {
////                        FibraOptica[] fibrasPriorizadas = g.grafo[n1][n2].listafibra;
////                        fibrasListPriori = new ArrayList<>(Arrays.asList(fibrasPriorizadas));
////                        fibrasListPriori.sort(Comparator.comparing(FibraOptica::getMw));
////                    }else if(config.getProperty("MC").equals(Metodo.ACTIVO.name())) {
////                        FibraOptica[] fibrasPriorizadas = g.grafo[n1][n2].listafibra;
////                        fibrasListPriori = new ArrayList<>(Arrays.asList(fibrasPriorizadas));
////                        fibrasListPriori.sort(Comparator.comparing(FibraOptica::getMc));
////                    }else {
////                        FibraOptica[] fibrasPriorizadas = g.grafo[n1][n2].listafibra;
////                        fibrasListPriori = new ArrayList<>(Arrays.asList(fibrasPriorizadas));
////                    }
////
////                    //se selecciona el indice de fibra por FF
////                    //Se cambia el guardado del indice de fibra en el indice del enlace
////                    //ya que en caso de que se 1 sola fibra y halla 2 enlaces en el camino
////                    // da un arrayindexof 1 ya que supera la cantidad fibras donde guardar.
////
////
////                    /*
////                     * recorremos la lista ordenada y el count empieza por la fibra con prioridad mas baja y vamos
////                     * aumentando
////                     */
////                    for (FibraOptica item : fibrasListPriori) {
////                        fibraCount = item.getId();
////                        /*
////                         * preguntamos tambien si cumple la condicion de si esa fibra puede almacenar una peticion
////                         * con el FS demandado en el caso de que sea TODE, en otro caso
////                         */
////                        if (g.grafo[n1][n2].listafibra[fibraCount].listafs[respuestaV2.getIndiceFS()].libreOcupado == 0
////                                && ( config.getProperty("MC").equals(Metodo.ACTIVO.name())
////                                || config.getProperty("MW").equals(Metodo.ACTIVO.name())
////                                || config.getProperty("FF").equals(Metodo.ACTIVO.name())
////                                || g.grafo[n1][n2].listafibra[fibraCount].getTipoFs()==null
////                                || g.grafo[n1][n2].listafibra[fibraCount].getTipoFs().equals(String.valueOf(fs)
////                                )) ) {
////                            respuestaV2.indiceFibra[enlaceCount] = fibraCount ;
////                            //se creo esta linea para solucionar un fix que no sabemos si esta bien
//////                        respuestaV2.indiceFibra[fibraCount] = fibraCount;
////                        }
////                    }
////                }
//                if (respuestaV2.getIndiceFS() != -1) {
//                    System.out.println("Respuesta --> " + respuestaV2.toString());
//                    return respuestaV2;
//                }
//            }
        }
        return resultFalsoV2;
    }


    int[][] clearMat(int[][] mat) {
        for (int[] ints : mat) {
            Arrays.fill(ints, 0);
        }
        return mat;
    }

    int[] clearArray(int[] array, int value) {
        Arrays.fill(array, value);
        return array;
    }
}

package py.una.pol.model;

import edu.asu.emit.qyan.alg.control.*;
import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.VariableGraph;
import exception.GetRequestException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public class SimuladorRSAMultifibras {
    private static List<Solicitud> solicitudes;
    private static YenTopKShortestPathsAlg yenAlg;
    private static VariableGraph graph;
    private static int time = 0;
    private static final int[] vertices = {1, 2, 3, 4, 5};
    private static final GrafoMatrizV2 g = new GrafoMatrizV2(vertices);

    private static Solicitud obtenerSolicitud(int cromosomaValor, List<Solicitud> solicitudList) {
        Solicitud solicitud;
        try {
            //se pone el valor del cromosoma proque el cromosoma crea valores enteros a partir de 1
            //y el arraylist empieza en 0 por lo que se coloca -1 para no nos dee error de indice
            solicitud = solicitudList.get(cromosomaValor - 1);
        } catch (Exception e) {
            throw new GetRequestException("Se produjo un error al querer recuperar una solicitud " +
                    "de la lista de Solicitudes");
        }
        return solicitud;
    }

    private static int obtenerMayorindiceGrafoPorSolicitud(EnlaceV2[][] grafo) {
        int mayorIndice = 0;
        for (int x = 0; x < grafo.length; x++) {
            for (int y = 0; y < grafo[x].length; y++) {
                for (int k = 0; k < grafo[x][y].listafibra.length; k++) {
                    for (int j = 0; j < g.grafo[x][y].listafibra[k].listafs.length; j++) {
                        if (grafo[x][y].listafibra[k].listafs[j].getLibreOcupado() == 1) {
//                            System.out.println((grafo[x][y].listafibra[k].listafs[j].getLibreOcupado()));
                            if (j > mayorIndice) {
                                System.out.println("Para fibra: " + (k + 1) + " y enlace: [" + (x + 1) + "," + (y + 1) + "]");
                                System.out.println("valor indice: " + (j + 1) + "  " + "\nvalor aux: " + mayorIndice);
                                mayorIndice = j;
                            }
                        }

                    }

                }
            }
        }
        return mayorIndice + 1;
    }

    public void LoadDataSimulations() throws IOException {
        Properties config = new Properties();
        InputStream configInput;
        configInput = SimuladorRSA.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            config.load(configInput);
        } catch (IOException e) {
            System.out.println("No se pudo leer la cantidad de caminos del properties");
        }
//        System.out.println(config.getProperty("cantidadDeCaminos"));
        int tamanhoSlot = Integer.parseInt(config.getProperty("tamanhoSlot"));
        solicitudes = new ArrayList<>();
        // Matriz que representa la red igual al archivo test_16 que se va a utilar al tener los caminos.
        g.InicializarGrafo(g.grafo, tamanhoSlot);
        g.agregarRuta(2, 4, 1, tamanhoSlot);
        g.agregarRuta(1, 3, 1, tamanhoSlot);
        g.agregarRuta(2, 3, 1, tamanhoSlot);
        g.agregarRuta(3, 4, 1, tamanhoSlot);
        g.agregarRuta(3, 5, 1, tamanhoSlot);
        g.agregarRuta(1, 2, 1, tamanhoSlot);
        g.agregarRuta(4, 5, 1, tamanhoSlot);
        String matrizAdyacenciaString = config.getProperty("matrizAdyacencia");
        if (Boolean.parseBoolean(config.getProperty("isMatrizAdyacencia"))) {
            loadMatrizAdyacencia(matrizAdyacenciaString);
            g.isMatrizAdyacencia = Boolean.parseBoolean(config.getProperty("isMatrizAdyacencia"));
        }

        //priorizacion de core

        for (int x = 0; x < g.grafo.length; x++) {
            int menor = 0;
            int indiceMenor = 0;
            int prioridad = 1;
            for (int y = 0; y < g.grafo[x].length; y++) {
                while (prioridad <= g.grafo[x][y].listafibra.length) {
                    for (int k = g.grafo[x][y].listafibra.length - 1; k >= 0; k--) {
                        if (menor >= g.grafo[x][y].listafibra[k].getCosto()) {
                            indiceMenor = k;
                            menor = g.grafo[x][y].listafibra[k].getCosto();
                        }
                    }

                    g.grafo[x][y].listafibra[indiceMenor].setCosto(99999999);
                    g.grafo[x][y].listafibra[indiceMenor].setPrioridad(prioridad);
                    prioridad++;
                    /*recorremos la matriz de adyacencia para sumar 1 a las fibras adyacentes a la fibra
                    con indice [indiceMenor]*/
                    for (int n = 0; n < g.matrizAdyacencia.length; n++) {
                        if (g.matrizAdyacencia[indiceMenor][n] == true) {
                            g.grafo[x][y].listafibra[n].setCosto(g.grafo[x][y].listafibra[n].getCosto() + 1);
                        }
                    }
                    menor = 999999999;
                }
            }
        }


        System.out.println("Testing top-k shortest paths!");
        graph = new VariableGraph("data/test_16");
        yenAlg = new YenTopKShortestPathsAlg(graph);
        FileReader input = new FileReader("data/conexiones");

        BufferedReader bufRead = new BufferedReader(input);

        String linea = bufRead.readLine();

        int cont = 0;
        while (linea != null) {

            if (linea.trim().equals("")) {
                linea = bufRead.readLine();
                continue;
            }

            String[] str_list = linea.trim().split("\\s*,\\s*");
            Solicitud solicitud = new Solicitud();
            solicitud.setId(cont++);
            solicitud.setOrigen(Integer.parseInt(str_list[0]));
            //	System.out.println("origen:" + str_list[0]);
            solicitud.setDestino(Integer.parseInt(str_list[1]));
            //	System.out.println(str_list[1]);
            solicitud.setFs(Integer.parseInt(str_list[2]));
            time = Integer.parseInt(str_list[3]);
            //	System.out.println(str_list[2]);
            solicitudes.add(solicitud);

//            Collections.sort(solicitudes, getFSComparator());


            linea = bufRead.readLine();
        }

//        System.out.print("solicitudes entrantes:" + solicitudes);
        bufRead.close();

    }

    private void loadMatrizAdyacencia(String adyacenciaString) {
        String[] matAdyacenciaString = adyacenciaString.split("/");
        g.matrizAdyacencia = new boolean[matAdyacenciaString.length][matAdyacenciaString.length];
        for (String s : matAdyacenciaString) {
            String[] rowMatAdyacencia = s.split(",");
            for (int j = 1; j <= (s.length() / 2); j++) {
                g.matrizAdyacencia[Integer.parseInt(rowMatAdyacencia[0]) - 1][Integer.parseInt(rowMatAdyacencia[j]) - 1] = true;
                g.matrizAdyacencia[Integer.parseInt(rowMatAdyacencia[j]) - 1][Integer.parseInt(rowMatAdyacencia[0]) - 1] = true;
            }
        }

    }

    public ParametrosRetornoRsa RunSimuladorRSAMultifibras(int[] cromosoma, Integer individuoId) {
        int indiceSlotMayor;
        int cont = 0;
        int cantidadDeCaminos;
        ParametrosRetornoRsa parametrosRetornoRsa = new ParametrosRetornoRsa();
        DesasignarV2 des = new DesasignarV2(g);
        des.restarTiempo();
        //traer la cantidad de caminos quiero para el disktra
        Properties config = new Properties();
        InputStream configInput;
        configInput = SimuladorRSA.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            config.load(configInput);
        } catch (IOException e) {
            System.out.println("No se pudo leer la cantidad de caminos del properties");
        }
        cantidadDeCaminos = Integer.parseInt(config.getProperty("cantidadDeCaminos"));
        /*
          ordenamos las solicitudes de mayor a menor con respecto al FS de cada solicitud
          Collections.sort
         */
        int[] cromosomaOrdenado = new int[cromosoma.length];
        if (individuoId != null && individuoId.equals(0)) {
            solicitudes.sort(getFSComparator());
            for (int k = 0; k < cromosoma.length; k++) {
                cromosoma[k] = k + 1;
                cromosomaOrdenado[k] = solicitudes.get(k).getId() + 1;
            }
            /**
             *
             * devolvemos el cromosoma ordenado de acuerdo fs (de mayor a menor) de las  solicitudes  en el
             * archivo y cada elemento hace referencia a la fila donde se encuentran las solicitudes en el
             * archivo de conexion
             *
             */
            parametrosRetornoRsa.setCromosomaOrdenado(cromosomaOrdenado);
        }
        //los fs se poenen en idle(se cera el grafo para cada lista de solicitudes)
        for (int i : cromosoma) {
            Solicitud solicitud = obtenerSolicitud(i, solicitudes);
            int inicio = solicitud.getOrigen();
            int fin = solicitud.getDestino();
            int fs = solicitud.getFs();
            List<Path> shortest_paths_list = yenAlg.get_shortest_paths(
                    //graph.get_vertex(1), graph.get_vertex(3), 300);
                    graph.get_vertex(inicio), graph.get_vertex(fin), cantidadDeCaminos);
//                        		System.out.println(":" + shortest_paths_list);
            //instanciar la clase donde se encuentra concatenar caminos para hallar el camino que cumple cn todas las reglas
            BuscarSlotV2 r = new BuscarSlotV2(g, shortest_paths_list);
            //busca un camino posible para una demanda teniendo en cuenta la 3 reglas de eon
            ResultadoSlotV2 res = r.concatenarCaminos(fs);
            if (res != null) {
                System.out.println(res.toString());
                AsignacionV2 asignar = new AsignacionV2(g, res);
                asignar.marcarSlotUtilizados(time);
            } else {
                cont++;
                System.out.println("No se encontró camino posible.");

            }

        }
        indiceSlotMayor = obtenerMayorindiceGrafoPorSolicitud(g.grafo);
//        System.out.println(indiceSlotMayor);

        parametrosRetornoRsa.setMaximoExpectro(indiceSlotMayor);
        parametrosRetornoRsa.setCantidadBloqueos(cont);
        return parametrosRetornoRsa;
    }

    public static Comparator<Solicitud> getFSComparator() {
        return (s1, s2) -> String.valueOf(s2.getFs()).compareTo(String.valueOf(s1.getFs()));
    }


}

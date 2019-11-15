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
import java.util.List;
import java.util.Properties;

public class SimuladorRSAMultifibras {
    private static ArrayList<Solicitud> solicitudes;
    private static YenTopKShortestPathsAlg yenAlg;
    private static VariableGraph graph;
    private static int time = 0;
    private static int[] vertices = {1, 2, 3, 4, 5};
    private static GrafoMatrizV2 g = new GrafoMatrizV2(vertices);

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
                            System.out.println((grafo[x][y].listafibra[k].listafs[j].getLibreOcupado()));
                            if (j > mayorIndice) {
                                System.out.println("Para fibra: " + k);
                                System.out.println("valor indice: " + j + "  " + "\nvalor aux: " + mayorIndice);
                                mayorIndice = j;
                            }
                        }

                    }

                }
            }
        }
        return mayorIndice;
    }

    public void LoadDataSimulations() throws IOException {
        Properties config = new Properties();
        InputStream configInput = null;
        configInput = SimuladorRSA.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            config.load(configInput);
        } catch (IOException e) {
            System.out.println("No se pudo leer la cantidad de caminos del properties");
        }
//        System.out.println(config.getProperty("cantidadDeCaminos"));
        int tamanhoSlot = Integer.parseInt(config.getProperty("tamanhoSlot"));
        solicitudes = new ArrayList<Solicitud>();
        // Matriz que representa la red igual al archivo test_16 que se va a utilar al tener los caminos.
        g.InicializarGrafo(g.grafo, tamanhoSlot);
        g.agregarRuta(2, 4, 1, tamanhoSlot);
        g.agregarRuta(1, 3, 1, tamanhoSlot);
        g.agregarRuta(2, 3, 1, tamanhoSlot);
        g.agregarRuta(3, 4, 1, tamanhoSlot);
        g.agregarRuta(3, 5, 1, tamanhoSlot);
        g.agregarRuta(1, 2, 1, tamanhoSlot);
        g.agregarRuta(4, 5, 1, tamanhoSlot);

        System.out.println("Testing top-k shortest paths!");
        graph = new VariableGraph("data/test_16");
        yenAlg = new YenTopKShortestPathsAlg(graph);
        FileReader input = new FileReader("data/conexiones");

        BufferedReader bufRead = new BufferedReader(input);

        String linea = bufRead.readLine();

        int contlineatxt = 0;
        int cont = 0;
        int indiceSlotMayor = 0;
        while (linea != null) {

            contlineatxt++;

            if (linea.trim().equals("")) {
                linea = bufRead.readLine();
                continue;
            }

            String[] str_list = linea.trim().split("\\s*,\\s*");
            Solicitud solicitud = new Solicitud();

            solicitud.setOrigen(Integer.parseInt(str_list[0]));
            //	System.out.println("origen:" + str_list[0]);
            solicitud.setDestino(Integer.parseInt(str_list[1]));
            //	System.out.println(str_list[1]);
            solicitud.setFs(Integer.parseInt(str_list[2]));
            time = Integer.parseInt(str_list[3]);
            //	System.out.println(str_list[2]);
            solicitudes.add(solicitud);


            linea = bufRead.readLine();
        }

//        System.out.print("solicitudes entrantes:" + solicitudes);
        bufRead.close();

    }

    public ParametrosRetornoRsa SimuladorRSAMultifibras(int[] cromosoma) {
        int indiceSlotMayor = 0;
        int cont = 0;
        int cantidadDeCaminos = 0;
        ParametrosRetornoRsa parametrosRetornoRsa = new ParametrosRetornoRsa();
        DesasignarV2 des = new DesasignarV2(g);
        des.restarTiempo();
        //traer la cantidad de caminos quiero para el disktra
        Properties config = new Properties();
        InputStream configInput = null;
        configInput = SimuladorRSA.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            config.load(configInput);
        } catch (IOException e) {
            System.out.println("No se pudo leer la cantidad de caminos del properties");
        }
//        System.out.println(config.getProperty("cantidadDeCaminos"));
        cantidadDeCaminos = Integer.parseInt(config.getProperty("cantidadDeCaminos"));
        //los fs se poenen en idle(se cera el grafo para cada lista de solicitudes)
        for (int l = 0; l < cromosoma.length; l++) {
            Solicitud solicitud = obtenerSolicitud(cromosoma[l], solicitudes);
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
        System.out.println(indiceSlotMayor);

        parametrosRetornoRsa.setMaximoExpectro(indiceSlotMayor);
        parametrosRetornoRsa.setCantidadBloqueos(cont);
        return parametrosRetornoRsa;
    }
}

package py.una.pol.model;

import edu.asu.emit.qyan.alg.control.*;
import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.VariableGraph;
import exception.GetRequestException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SimuladorRSA {
    private static ArrayList<Solicitud> solicitudes;
    private static YenTopKShortestPathsAlg yenAlg;
    private static VariableGraph graph;
    private static int time = 0;
    private static int[] vertices = {1, 2, 3, 4, 5};
    private static GrafoMatriz g = new GrafoMatriz(vertices);

    private static Solicitud obtenerSolicitud(int cromosomaValor, List<Solicitud> solicitudList) {
        Solicitud solicitud;
        try {
            solicitud = solicitudList.get(cromosomaValor);
        } catch (Exception e) {
            throw new GetRequestException("Se produjo un error al querer recuperar una solicitud " +
                    "de la lista de Solicitudes");
        }
        return solicitud;
    }

    private static int obtenerMayorindiceGrafoPorGeneracion(Enlace[][] grafo) {
        int mayorIndice = 0;
        for (int x = 0; x < grafo.length; x++) {
            for (int y = 0; y < grafo[x].length; y++) {
                for (int k = 0; k < grafo[x][y].listafs.length; k++) {
                    if (grafo[x][y].listafs[k].getLibreOcupado() == 1) {
                        System.out.println(grafo[x][y].listafs[k].getLibreOcupado());
                        if (k > mayorIndice) {
                            mayorIndice = k;
                        }
                    }

                }

            }
        }
        return mayorIndice;
    }

    public void LoadDataSimulations() throws IOException {
        solicitudes = new ArrayList<Solicitud>();
        // Matriz que representa la red igual al archivo test_16 que se va a utilar al tener los caminos.
        g.InicializarGrafo(g.grafo);
        g.agregarRuta(1, 2, 1, 4);
        g.agregarRuta(1, 3, 1, 4);
        g.agregarRuta(2, 3, 1, 4);
        g.agregarRuta(2, 4, 1, 4);
        g.agregarRuta(3, 4, 1, 4);
        g.agregarRuta(3, 5, 1, 4);
        g.agregarRuta(4, 5, 1, 4);

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
            time = 3;
            //	System.out.println(str_list[2]);
            solicitudes.add(solicitud);


            linea = bufRead.readLine();
        }

//        System.out.print("solicitudes entrantes:" + solicitudes);
        bufRead.close();

    }

    public ParametrosRetornoRsa SimulacionRsa(int[] cromosoma) {
        int indiceSlotMayor = 0;
        int cont = 0;
        ParametrosRetornoRsa parametrosRetornoRsa = new ParametrosRetornoRsa();
        //los fs se poenen en idle(se cera el grafo para cada lista de solicitudes)
        for (int l = 0; l < cromosoma.length; l++) {
            Solicitud solicitud = obtenerSolicitud(cromosoma[l], solicitudes);
            int inicio = solicitud.getOrigen();
            int fin = solicitud.getDestino();
            int fs = solicitud.getFs();
            List<Path> shortest_paths_list = yenAlg.get_shortest_paths(
                    //graph.get_vertex(1), graph.get_vertex(3), 300);
                    graph.get_vertex(inicio), graph.get_vertex(fin), 4);
//                        		System.out.println(":" + shortest_paths_list);
            BuscarSlot r = new BuscarSlot(g, shortest_paths_list);
            resultadoSlot res = r.concatenarCaminos(fs);
            if (res != null) {
                System.out.println(res.toString());
                Asignacion asignar = new Asignacion(g, res);
                asignar.marcarSlotUtilizados(time);
            } else {
                cont++;
                System.out.println("No se encontró camino posible.");

            }

        }
        indiceSlotMayor = obtenerMayorindiceGrafoPorGeneracion(g.grafo);
        System.out.println(indiceSlotMayor);

        parametrosRetornoRsa.setMaximoExpectro(indiceSlotMayor);
        parametrosRetornoRsa.setCantidadBloqueos(cont);
        Desasignar des = new Desasignar(g);
        des.restarTiempo();
        return parametrosRetornoRsa;
    }
}

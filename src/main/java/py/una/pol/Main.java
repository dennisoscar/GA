package py.una.pol;

import agapi.*;
import agapi.impl.SelectorPostCruceSoloHijos;
import agapi.impl.SelectorTorneo;
import edu.asu.emit.qyan.alg.control.*;
import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.VariableGraph;
import exception.GetRequestException;
import py.una.pol.model.IndividuoEntero;
import py.una.pol.model.Solicitud;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        // Matriz que representa la red igual al archivo test_16 que se va a utilar al tener los caminos.
        int[] vertices = {1, 2, 3, 4, 5};
        GrafoMatriz g = new GrafoMatriz(vertices);
        g.InicializarGrafo(g.grafo);
        g.agregarRuta(1, 2, 1, 4);
        g.agregarRuta(1, 3, 1, 4);
        g.agregarRuta(2, 3, 1, 4);
        g.agregarRuta(2, 4, 1, 4);
        g.agregarRuta(3, 4, 1, 4);
        g.agregarRuta(3, 5, 1, 4);
        g.agregarRuta(4, 5, 1, 4);

        System.out.println("Testing top-k shortest paths!");
        VariableGraph graph = new VariableGraph("data/test_16");
        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
        FileReader input = new FileReader("data/conexiones");
        BufferedReader bufRead = new BufferedReader(input);

        String linea = bufRead.readLine();

        ArrayList<Solicitud> solicitudes = new ArrayList();

        int contlineatxt = 0;
        int cont = 0;
        while (linea != null) {

            contlineatxt++;

            if (linea.trim().equals("")) {
                linea = bufRead.readLine();
                continue;
            }


//            	Desasignar des = new Desasignar(g);
//            	des.restarTiempo();

            String[] str_list = linea.trim().split("\\s*,\\s*");
            Solicitud solicitud = new Solicitud();

            solicitud.setOrigen(Integer.parseInt(str_list[0]));
            //	System.out.println("origen:" + str_list[0]);
            solicitud.setDestino(Integer.parseInt(str_list[1]));
            //	System.out.println(str_list[1]);
            solicitud.setFs(Integer.parseInt(str_list[2]));
            //	System.out.println(str_list[2]);
            solicitudes.add(solicitud);

            linea = bufRead.readLine();
        }

//        System.out.print("solicitudes entrantes:" + solicitudes);
        bufRead.close();

        IndividuoEntero individuo = new IndividuoEntero();
        //leer de un archivo properties
        Properties config = new Properties();
        InputStream configInput = null;
        //inicializar variables
        int numeroEjecuciones = 1;
        int numeroGeneraciones = 20;
        double probabilidadDeSeleccion = 0.7;
        double probabilidadDeCruce = 0.7;
        double probabilidadDeMutacion = 0.05;
        int tamanhoPoblacion = 50;
        int tamanhoCromosoma = 6;

        try {
            //traer los datos de un archivo .properties
            configInput = IndividuoEntero.class.getClassLoader().getResourceAsStream("config.properties");
            config.load(configInput);
            System.out.println(config.getProperty("numeroEjecuciones"));
            numeroEjecuciones = Integer.parseInt(config.getProperty("numeroEjecuciones"));
            System.out.println(config.getProperty("numeroGeneraciones"));
            numeroGeneraciones = Integer.parseInt(config.getProperty("numeroGeneraciones"));
            System.out.println(config.getProperty("coeficienteDeSeleccion"));
            probabilidadDeSeleccion = Double.parseDouble(config.getProperty("coeficienteDeSeleccion"));
            System.out.println(config.getProperty("probabilidadDeCruce"));
            probabilidadDeCruce = Double.parseDouble(config.getProperty("probabilidadDeCruce"));
            System.out.println(config.getProperty("probabilidadDeMutacion"));
            probabilidadDeMutacion = Double.parseDouble(config.getProperty("probabilidadDeMutacion"));
            System.out.println(config.getProperty("tamanhoPoblacion"));
            tamanhoPoblacion = Integer.parseInt(config.getProperty("tamanhoPoblacion"));
            System.out.println(config.getProperty("tamanhoCromosoma"));
            tamanhoCromosoma = Integer.parseInt(config.getProperty("tamanhoCromosoma"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error cargando configuración\n" +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


        //setear valores para la ejecución
        Configuracion c = new Configuracion();
        c.setNumeroEjecuciones(numeroEjecuciones);
        c.setNumeroGeneraciones(numeroGeneraciones);
//        c.setSelector(new SelectorSUS(new FuncionClasico()));
        c.setSelector(new SelectorTorneo(probabilidadDeSeleccion));
        c.setSelectorPostCruce(new SelectorPostCruceSoloHijos());
        c.setProbabilidadCruce(probabilidadDeCruce);
        c.setProbabilidadMutacion(probabilidadDeMutacion);
        c.setElitismo(true);
        c.setTamanoPoblacion(tamanhoPoblacion);
        c.setTipoIndividuo(new IndividuoEntero());
        c.setTamanoCromosoma(tamanhoCromosoma);
        c.iniciarProceso();
        //calculo auxiliar para saber como se estructura el proyeto
        Proceso proceso = c.getProceso();
        Ejecucion ejecucion[] = proceso.getEjecuciones();
        Generacion generacion[] = ejecucion[0].getGeneraciones();
        Poblacion poblacions = generacion[0].getPoblacion();
        Individuo individuos[] = poblacions.getIndividuos();
//        System.out.println(individuos[0]);

        //pasos para metodo de calculo de fitnes(FA=Función Aptitud)
        for (int i = 0; i < ejecucion.length; i++) {
            Proceso procesoPrueba = c.getProceso();
            Ejecucion ejecucionPrueba[] = procesoPrueba.getEjecuciones();
            Generacion generacionPrueba[] = ejecucionPrueba[i].getGeneraciones();
            for (int j = 0; j < generacionPrueba.length; j++) {
                Poblacion poblacionPrueba = generacion[j].getPoblacion();
                Individuo individuosPrueba[] = poblacionPrueba.getIndividuos();
                for (int k = 0; k < individuosPrueba.length; k++) {
                    Individuo individuoAux = individuosPrueba[k];
                    IndividuoEntero individuoEntero = (IndividuoEntero) individuoAux;
                    int cromosoma[] = individuoEntero.getCromosoma();
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
                        } else {
                            cont++;
                            System.out.println("No se encontró camino posible.");

                        }
//                        System.out.println(cromosoma[l]);
                    }
                }

            }

        }

        System.out.println("#############");
        System.out.println("Cantidad de conexiones entrantes :" + contlineatxt);
        System.out.println("Cantidad de conexiones fallidas :" + cont);
//        System.out.println("La mejor opción la tiene la abeja: " + resultadoFinal);
//        System.out.println(individuos[0]);


        System.out.println(
                c.aTexto(Configuracion.GENERACIONES_SIN_POBLACIONES));
        double tiempo = c.getProceso().getTiempoProceso() / 1000000000.0;
        System.out.println("Tiempo: " + tiempo + " segundos");


    }
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

}


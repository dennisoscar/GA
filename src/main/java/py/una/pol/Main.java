package py.una.pol;

import agapi.Configuracion;
import agapi.impl.SelectorPostCruceSoloHijos;
import agapi.impl.SelectorTorneo;
import edu.asu.emit.qyan.alg.control.GrafoMatriz;
import edu.asu.emit.qyan.alg.control.YenTopKShortestPathsAlg;
import edu.asu.emit.qyan.alg.model.VariableGraph;
import py.una.pol.model.Individuo;
import py.una.pol.model.Solicitud;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

        System.out.print("solicitudes entrantes:" + solicitudes);
        bufRead.close();

        Individuo individuo = new Individuo();
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
            configInput = Individuo.class.getClassLoader().getResourceAsStream("config.properties");
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
        c.setTipoIndividuo(new Individuo());
        c.setTamanoCromosoma(tamanhoCromosoma);
        c.iniciarProceso();
        System.out.println(
                c.aTexto(Configuracion.GENERACIONES_CON_POBLACIONES));
        double tiempo = c.getProceso().getTiempoProceso() / 1000000000.0;
        System.out.println("Tiempo: " + tiempo + " segundos");

    }

}


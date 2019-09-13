package py.una.pol;

import agapi.Configuracion;
import agapi.impl.SelectorPostCruceSoloHijos;
import agapi.impl.SelectorTorneo;
import py.una.pol.model.IndividuoEntero;
import py.una.pol.model.IndividuoEnteroMultifibras;
import py.una.pol.model.SimuladorRSAMultifibras;
import py.una.pol.model.ValoresConfig;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainRsaMultifibras {
    public static void main(String[] args) throws IOException {
        SimuladorRSAMultifibras simuladorRSAMultifibras = new SimuladorRSAMultifibras();
        ValoresConfig valoresConfig = new ValoresConfig();
        //se inicializa los valores del gefo
        //se leen la lista de solicitudes de un archivo
        simuladorRSAMultifibras.LoadDataSimulations();
        //se leen los datos necesarios para inicializar la ejecución del GA
        valoresConfig = loadPropertiesGA();
        //setear valores para la ejecución
        Configuracion c = new Configuracion();
        c.setNumeroEjecuciones(valoresConfig.getNumeroEjecuciones());
        c.setNumeroGeneraciones(valoresConfig.getNumeroGeneraciones());
//        c.setSelector(new SelectorSUS(new FuncionClasico()));
        c.setSelector(new SelectorTorneo(valoresConfig.getProbabilidadDeSeleccion()));
        c.setSelectorPostCruce(new SelectorPostCruceSoloHijos());
        c.setProbabilidadCruce(valoresConfig.getProbabilidadDeCruce());
        c.setProbabilidadMutacion(valoresConfig.getProbabilidadDeMutacion());
        c.setElitismo(true);
        c.setTamanoPoblacion(valoresConfig.getTamanhoPoblacion());
        c.setTipoIndividuo(new IndividuoEnteroMultifibras());
        c.setTamanoCromosoma(valoresConfig.getTamanhoCromosoma());
        c.iniciarProceso();
        System.out.println(
                c.aTexto(Configuracion.GENERACIONES_SIN_POBLACIONES));
        double tiempo = c.getProceso().getTiempoProceso() / 1000000000.0;
        System.out.println("Tiempo: " + tiempo + " segundos");

    }

    private static ValoresConfig loadPropertiesGA() {
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

        ValoresConfig valoresConfig = new ValoresConfig();
        valoresConfig.setNumeroEjecuciones(numeroEjecuciones);
        valoresConfig.setNumeroGeneraciones(numeroGeneraciones);
        valoresConfig.setProbabilidadDeCruce(probabilidadDeCruce);
        valoresConfig.setProbabilidadDeMutacion(probabilidadDeMutacion);
        valoresConfig.setProbabilidadDeSeleccion(probabilidadDeSeleccion);
        valoresConfig.setTamanhoCromosoma(tamanhoCromosoma);
        valoresConfig.setTamanhoPoblacion(tamanhoPoblacion);
        return valoresConfig;
    }
}

package py.una.pol;

/* AGAPI - API para el desarrollo de Algoritmos Geneticos
 * Copyright (C) 2013 Saul Gonzalez
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import agapi.Configuracion;
import agapi.impl.IndividuoCombinatorio;
import agapi.impl.SelectorPostCruceSoloHijos;
import agapi.impl.SelectorTorneo;

import javax.swing.*;
import java.io.InputStream;
import java.util.Properties;

public class Individuo extends IndividuoCombinatorio {


    public static void main(String[] args) {
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

    @Override
    public double calcFA() {
        double fa = 0;
        int[] s = this.getCromosoma();
        for (int i = 0; i < s.length; i++) {
            fa = (double) fa + (i + 1) * s[i];
        }
        return fa;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int[] s = this.getCromosoma();
        for (int i = 0; i < s.length; i++) {
            if (i != s.length - 1) {
                sb.append(s[i] + "-");
            } else {
                sb.append(s[i]);
            }
        }
        sb.append(" FA: " + this.getFA());
        return sb.toString();
    }
}

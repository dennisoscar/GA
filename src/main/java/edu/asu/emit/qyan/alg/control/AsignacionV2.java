package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.abstracts.BaseVertex;
import py.una.pol.model.Metodo;
import py.una.pol.model.SimuladorRSA;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AsignacionV2 {
    GrafoMatrizV2 g;
    ResultadoSlotV2 resultado;
    int p;
    int m;
    Properties config = new Properties();
    InputStream configInput;

    public AsignacionV2(GrafoMatrizV2 g, ResultadoSlotV2 resultado) {

        this.g = g;
        this.resultado = resultado;

    }

    public void marcarSlotUtilizados(int tiempo, Double alfa) {
        configInput = SimuladorRSA.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            config.load(configInput);
        } catch (IOException e) {
            System.out.println("No se pudo leer la cantidad de caminos del properties");
        }
//        System.out.println(resultado);
        int mitad = lugarInicialAsignacion(resultado);

        for (int i = 0; i < resultado.camino.get_vertex_list().size() - 1; i++) {

//            System.out.println("longitud de camino" + resultado.camino.get_vertex_list().size());
            BaseVertex id1 = resultado.camino.get_vertex_list().get(i);
            BaseVertex id2 = resultado.camino.get_vertex_list().get(i + 1);

            int k = id1.get_id();
            int l = id2.get_id();

            int n1 = g.posicionNodo(k);
            int n2 = g.posicionNodo(l);

            p = n1;
            m = n2;
            //Se agrega for de fibra para la asignación ya que al querer asignar en la fibra
            // de indice "enlace" en caso de que exista 1 fibra y halla  enlaces da un array indexof 1
//            for (int fibraCount = 0; fibraCount < g.grafo[n1][n2].listafibra.length; fibraCount++) {
//                for (int indiceFS = resultado.indiceFS; indiceFS < resultado.indiceFS + resultado.cantidadfs; indiceFS++) {
//                    g.grafo[n1][n2].listafibra[fibraCount].listafs[indiceFS].libreOcupado = 1;
//                }
//            }
            //este es siguiendo la logica del profe
            int mcValue = 0;
            for (int indiceFS = resultado.indiceFS; indiceFS < resultado.indiceFS + resultado.cantidadfs;
                 indiceFS++) {
                g.grafo[n1][n2].listafibra[resultado.indiceFibra[i]].listafs[indiceFS].libreOcupado= 1;
                g.grafo[n2][n1].listafibra[resultado.indiceFibra[i]].listafs[indiceFS].libreOcupado= 1;
                if (!g.grafo[n1][n2].listafibra[resultado.indiceFibra[i]].isMlMark()
                        &&( config.getProperty("MC").equals(Metodo.ACTIVO.name())
                        || config.getProperty("MW").equals(Metodo.ACTIVO.name()))) {
                    g.grafo[n1][n2].listafibra[resultado.indiceFibra[i]]
                            .setMl(g.grafo[n1][n2].listafibra[resultado.indiceFibra[i]].getMl()+resultado.cantidadfs);
                    g.grafo[n2][n1].listafibra[resultado.indiceFibra[i]]
                            .setMl(g.grafo[n2][n1].listafibra[resultado.indiceFibra[i]].getMl()+resultado.cantidadfs);
                    g.grafo[n1][n2].listafibra[resultado.indiceFibra[i]].setMlMark(true);
                    g.grafo[n2][n1].listafibra[resultado.indiceFibra[i]].setMlMark(true);
                }
                int mcAux1 =0;
                for (int fibraAdyacente = 0; fibraAdyacente < g.matrizAdyacencia[resultado.indiceFibra[i]].length
                        && g.isMatrizAdyacencia; fibraAdyacente++) {
                    if(g.matrizAdyacencia[fibraAdyacente][resultado.indiceFibra[i]]){
                        if (g.grafo[n1][n2].listafibra[fibraAdyacente].listafs[indiceFS].libreOcupado == 0
                                && (config.getProperty("MC").equals(Metodo.ACTIVO.name())
                                || config.getProperty("MW").equals(Metodo.ACTIVO.name()))) {
                            g.grafo[n1][n2].listafibra[fibraAdyacente].setMl(g.grafo[n1][n2].listafibra[fibraAdyacente].getMl()+1);
                            g.grafo[n2][n1].listafibra[fibraAdyacente].setMl(g.grafo[n2][n1].listafibra[fibraAdyacente].getMl()+1);
                        }
                        g.grafo[n1][n2].listafibra[fibraAdyacente].listafs[indiceFS].libreOcupado=999;
                        g.grafo[n2][n1].listafibra[fibraAdyacente].listafs[indiceFS].libreOcupado=999;
                    }
                }
            }

            for (int j = 0; j < g.grafo[p][m].listafibra.length; j++) {
                int mcAux = 0;
                for (int fibraAdyacente = 0; fibraAdyacente < g.matrizAdyacencia[resultado.indiceFibra[i]].length
                        && g.isMatrizAdyacencia; fibraAdyacente++) {
                    if (g.matrizAdyacencia[fibraAdyacente][j] && (config.getProperty("MC").equals(Metodo.ACTIVO.name())
                            || config.getProperty("MW").equals(Metodo.ACTIVO.name()))) {
                        mcAux += g.grafo[p][m].listafibra[fibraAdyacente].getMl();
                        g.grafo[p][m].listafibra[j].setMc(mcAux);
                        g.grafo[m][p].listafibra[j].setMc(mcAux);
                        g.grafo[n1][n2].listafibra[j].setMw((alfa * g.grafo[n1][n2].listafibra[j].getMl()) +
                                (1-alfa) * g.grafo[n1][n2].listafibra[j].getMc());
                        g.grafo[n2][n1].listafibra[j].setMw((alfa * g.grafo[n1][n2].listafibra[j].getMl()) +
                                (1-alfa) * g.grafo[n1][n2].listafibra[j].getMc());
                    }
                }
                System.out.println("Para la fibra con id= " + (g.grafo[p][m].listafibra[j].getId()) + ", FS= "  +g.grafo[p][m].listafibra[j].getTipoFs()
                        +  "    Enlace " + (p+1) + "---"+ (m+1) + " ML= " +g.grafo[p][m].listafibra[j].getMl()
                        + " MC= " +g.grafo[p][m].listafibra[j].getMc()
                        + " MW= " +g.grafo[p][m].listafibra[j].getMw() );
                for (int x = 0; x <g.grafo[p][m].listafibra[j].listafs.length; x++) {
                    g.grafo[p][m].listafibra[j].setMlMark(false);
                    g.grafo[p][m].listafibra[j].setMcMark(false);
                    System.out.println("Estado del slot ["+(x+1)+"]-->" + g.grafo[p][m].listafibra[j].listafs[x].libreOcupado + "  ");
                   // System.out.println("Tiempo " + g.grafo[p][m].listafibra[j].listafs[x].tiempo);
                }

            }
            System.out.println("\n###ENLACE###\n");
        }
        System.out.println("^^^^^^PETICION^^^^^");
    }

    public int lugarInicialAsignacion(ResultadoSlotV2 resultado) {

        int indiceInicio = (resultado.indice - resultado.contador) + 1;
        //	 System.out.println(indiceInicio);
        return (indiceInicio + resultado.indice) / 2;
        //     System.out.println(mitad);
//        return /mitad;
    }

}

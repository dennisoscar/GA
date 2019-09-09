package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.abstracts.BaseVertex;

public class Asignacion {

    GrafoMatriz g;
    ResultadoSlot resultado;
    int p;
    int m;

    public Asignacion(GrafoMatriz g, ResultadoSlot resultado) {

        this.g = g;
        this.resultado = resultado;

    }

    public void marcarSlotUtilizados(int tiempo) {

        int mitad = lugarInicialAsignacion(resultado);

        for (int i = 0; i < resultado.camino.get_vertex_list().size() - 1; i++) {

            BaseVertex id1 = resultado.camino.get_vertex_list().get(i);
            BaseVertex id2 = resultado.camino.get_vertex_list().get(i + 1);

            int k = id1.get_id();
            int l = id2.get_id();

            int n1 = g.posicionNodo(k);
            int n2 = g.posicionNodo(l);

            p = n1;
            m = n2;

            int mitadderecha = mitad;
            int mitadizquierda = mitad;
            for (int x = 0; x < resultado.cantidadfs; x++) {

                if (x == 0) {
                    g.grafo[n1][n2].listafs[mitad].libreOcupado = 1;
                    g.grafo[n2][n1].listafs[mitad].libreOcupado = 1;
                    g.grafo[n1][n2].listafs[mitad].tiempo = tiempo;
                    g.grafo[n2][n1].listafs[mitad].tiempo = tiempo;
                } else if (x != 0 && (x % 2) == 0) {
                    mitadizquierda--;
                    g.grafo[n1][n2].listafs[mitadizquierda].libreOcupado = 1;
                    g.grafo[n2][n1].listafs[mitadizquierda].libreOcupado = 1;
                    g.grafo[n1][n2].listafs[mitadizquierda].tiempo = tiempo;
                    g.grafo[n2][n1].listafs[mitadizquierda].tiempo = tiempo;
                } else if (x != 0 && (x % 2) != 0) {
                    mitadderecha++;
                    g.grafo[n1][n2].listafs[mitadderecha].libreOcupado = 1;
                    g.grafo[n2][n1].listafs[mitadderecha].libreOcupado = 1;
                    g.grafo[n1][n2].listafs[mitadderecha].tiempo = tiempo;
                    g.grafo[n2][n1].listafs[mitadderecha].tiempo = tiempo;
                }
            }

            for (int x = 0; x < g.grafo[0][0].listafs.length; x++) {


                System.out.print("Estado del slot " + g.grafo[p][m].listafs[x].libreOcupado + "  ");
                System.out.println("Tiempo " + g.grafo[p][m].listafs[x].tiempo);
            }
            System.out.println("######");
        }


    }

    public int lugarInicialAsignacion(ResultadoSlot resultado) {

        int indiceInicio = (resultado.indice - resultado.contador) + 1;
        //	 System.out.println(indiceInicio);
        int mitad = (indiceInicio + resultado.indice) / 2;
        //     System.out.println(mitad);
        return mitad;
    }


}

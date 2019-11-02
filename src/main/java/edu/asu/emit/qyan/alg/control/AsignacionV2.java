package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.abstracts.BaseVertex;

public class AsignacionV2 {
    GrafoMatrizV2 g;
    ResultadoSlotV2 resultado;
    int p;
    int m;

    public AsignacionV2(GrafoMatrizV2 g, ResultadoSlotV2 resultado) {

        this.g = g;
        this.resultado = resultado;

    }

    public void marcarSlotUtilizados(int tiempo) {
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


            for(int indiceFS = resultado.indiceFS ; indiceFS< resultado.indiceFS + resultado.cantidadfs -1 ; indiceFS++ ){
                g.grafo[n1][n2].listafibra[resultado.indiceFibra[i]].listafs[indiceFS].libreOcupado= 1;
            }
























//            int mitadderecha = mitad;
//            int mitadizquierda = mitad;
//            for (int j = 0; j <= resultado.fibraList.size() - 1; j++) {
//                for (int x = 0; x < resultado.cantidadfs - 1; x++) {
//                    if (x == 0) {
////                        System.out.println("fibra: " + (resultado.fibraList.get(j) + "  mitad  " + mitad));
//                        g.grafo[n1][n2].listafibra[resultado.fibraList.get(j)].listafs[mitad].libreOcupado = 1;
//                        g.grafo[n2][n1].listafibra[resultado.fibraList.get(j)].listafs[mitad].libreOcupado = 1;
//                        g.grafo[n1][n2].listafibra[resultado.fibraList.get(j)].listafs[mitad].tiempo = tiempo;
//                        g.grafo[n2][n1].listafibra[resultado.fibraList.get(j)].listafs[mitad].tiempo = tiempo;
//                    } else if (x != 0 && (x % 2) == 0) {
//                        mitadizquierda--;
////                        System.out.println("fibra: " + resultado.fibraList.get(j) + "  mitadIzquierda  " + mitadizquierda);
//                        g.grafo[n1][n2].listafibra[resultado.fibraList.get(j)].listafs[mitadizquierda].libreOcupado = 1;
//                        g.grafo[n2][n1].listafibra[resultado.fibraList.get(j)].listafs[mitadizquierda].libreOcupado = 1;
//                        g.grafo[n1][n2].listafibra[resultado.fibraList.get(j)].listafs[mitadizquierda].tiempo = tiempo;
//                        g.grafo[n2][n1].listafibra[resultado.fibraList.get(j)].listafs[mitadizquierda].tiempo = tiempo;
//                    } else if (x != 0 && (x % 2) != 0) {
//                        mitadderecha++;
////                        System.out.println("fibra: " + resultado.fibraList.get(j) + "  mitarDerecha  " + mitadderecha);
//                        g.grafo[n1][n2].listafibra[resultado.fibraList.get(j)].listafs[mitadderecha].libreOcupado = 1;
//                        g.grafo[n2][n1].listafibra[resultado.fibraList.get(j)].listafs[mitadderecha].libreOcupado = 1;
//                        g.grafo[n1][n2].listafibra[resultado.fibraList.get(j)].listafs[mitadderecha].tiempo = tiempo;
//                        g.grafo[n2][n1].listafibra[resultado.fibraList.get(j)].listafs[mitadderecha].tiempo = tiempo;
//                    }
//                }
//            }
            for (int j = 0; j < g.grafo[0][0].listafibra.length; j++) {
                System.out.println("Para la fibra:  " + j);
                for (int x = 0; x < g.grafo[0][0].listafibra[j].listafs.length; x++) {
                    System.out.print("Estado del slot " + g.grafo[p][m].listafibra[j].listafs[x].libreOcupado + "  ");
                    System.out.println("Tiempo " + g.grafo[p][m].listafibra[j].listafs[x].tiempo);
                }
            }
            System.out.println("######");
        }
    }

    public int lugarInicialAsignacion(ResultadoSlotV2 resultado) {

        int indiceInicio = (resultado.indice - resultado.contador) + 1;
        //	 System.out.println(indiceInicio);
        int mitad = (indiceInicio + resultado.indice) / 2;
        //     System.out.println(mitad);
        return mitad;
    }

}

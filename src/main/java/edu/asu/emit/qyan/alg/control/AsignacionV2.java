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
            //Se agrega for de fibra para la asignación ya que al querer asignar en la fibra
            // de indice "enlace" en caso de que exista 1 fibra y halla  enlaces da un array indexof 1
//            for (int fibraCount = 0; fibraCount < g.grafo[n1][n2].listafibra.length; fibraCount++) {
//                for (int indiceFS = resultado.indiceFS; indiceFS < resultado.indiceFS + resultado.cantidadfs; indiceFS++) {
//                    g.grafo[n1][n2].listafibra[fibraCount].listafs[indiceFS].libreOcupado = 1;
//                }
//            }


            //este es siguiendo la logica del profe
            for (int indiceFS = resultado.indiceFS; indiceFS < resultado.indiceFS + resultado.cantidadfs; indiceFS++) {
                g.grafo[n1][n2].listafibra[resultado.indiceFibra[i]].listafs[indiceFS].libreOcupado= 1;
                g.grafo[n2][n1].listafibra[resultado.indiceFibra[i]].listafs[indiceFS].libreOcupado= 1;
                for (int fibraAdyacente = 0; fibraAdyacente < g.matrizAdyacencia[resultado.indiceFibra[i]].length
                        && g.isMatrizAdyacencia != false; fibraAdyacente++) {
                    if(g.matrizAdyacencia[fibraAdyacente][resultado.indiceFibra[i]]==true){
                        g.grafo[n1][n2].listafibra[fibraAdyacente].listafs[indiceFS].libreOcupado=999;
                        g.grafo[n2][n1].listafibra[fibraAdyacente].listafs[indiceFS].libreOcupado=999;
                    }
                }
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
                System.out.println("Para la fibra: " + (j+1) + "    Enlace " + (p+1) + "---"+ (m+1));
                for (int x = 0; x < g.grafo[0][0].listafibra[j].listafs.length; x++) {
                    System.out.print("Estado del slot ["+(x+1)+"]-->" + g.grafo[p][m].listafibra[j].listafs[x].libreOcupado + "  ");
                    System.out.println("Tiempo " + g.grafo[p][m].listafibra[j].listafs[x].tiempo);
                }
            }
            System.out.println("######");
        }
        System.out.println("######");
    }

    public int lugarInicialAsignacion(ResultadoSlotV2 resultado) {

        int indiceInicio = (resultado.indice - resultado.contador) + 1;
        //	 System.out.println(indiceInicio);
        int mitad = (indiceInicio + resultado.indice) / 2;
        //     System.out.println(mitad);
        return mitad;
    }

}

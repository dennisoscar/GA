package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.Path;
import edu.asu.emit.qyan.alg.model.VariableGraph;
import py.una.pol.model.SimuladorRSA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Aplicacion {

    public static void main(String[] args) throws InterruptedException, IOException {
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
        // Matriz que representa la red igual al archivo test_16 que se va a utilar al tener los caminos.
        int[] vertices = {1, 2, 3, 4, 5};
        GrafoMatriz g = new GrafoMatriz(vertices);
        g.InicializarGrafo(g.grafo, tamanhoSlot);
        g.agregarRuta(1, 2, 1, 4);
        g.agregarRuta(1, 3, 1, 4);
        g.agregarRuta(2, 3, 1, 4);
        g.agregarRuta(2, 4, 1, 4);
        g.agregarRuta(3, 4, 1, 4);
        g.agregarRuta(3, 5, 1, 4);
        g.agregarRuta(4, 5, 1, 4);

        //  int inicio = 1;
        // int fin    = 5;


        System.out.println("Testing top-k shortest paths!");
        VariableGraph graph = new VariableGraph("data/test_16");
        YenTopKShortestPathsAlg yenAlg = new YenTopKShortestPathsAlg(graph);
        FileReader input = new FileReader("data/conexiones");
        BufferedReader bufRead = new BufferedReader(input);

        String linea = bufRead.readLine();

        ArrayList<Request> solicitudes = new ArrayList();

        int contlineatxt = 0;
        int cont = 0;
        while (linea != null) {

            contlineatxt++;

            if (linea.trim().equals("")) {
                linea = bufRead.readLine();
                continue;
            }


            //	Desasignar des = new Desasignar(g);
            //	des.restarTiempo();

            String[] str_list = linea.trim().split("\\s*,\\s*");
            Request solicitud = new Request();

            solicitud.setOrigen(Integer.parseInt(str_list[0]));
            //	System.out.println("origen:" + str_list[0]);
            solicitud.setDestino(Integer.parseInt(str_list[1]));
            //	System.out.println(str_list[1]);
            solicitud.setFs(Integer.parseInt(str_list[2]));
            //	System.out.println(str_list[2]);
            solicitudes.add(solicitud);

            linea = bufRead.readLine();
        }
        //	System.out.print("solicitudes entrantes:" + solicitudes);
        bufRead.close();
        // cantidad de pasos y numeros de demandas a tomar por cada paso.
        int divpasos = 4;
        System.out.println("divpasos:" + divpasos);
        int pasos = solicitudes.size() / divpasos;
        System.out.println("pasos:" + pasos);
        int pasoinicio = 0;
        int pasofinal = divpasos;
        System.out.println("pasofinal:" + divpasos);
        double a1 = 0.5;
        double a2 = 0.5;

		/*	int origen = Integer.parseInt(str_list[0]);
				int destino = Integer.parseInt(str_list[1]);
				int fs = Integer.parseInt(str_list[2]);
				int tiempo = Integer.parseInt(str_list[3]);

				int inicio = origen;
				int fin = destino;


		        System.out.println("nuevo ciclo" );
				System.out.println(inicio);
				System.out.println(fin);
				System.out.println(fs);


			List<Path> shortest_paths_list = yenAlg.get_shortest_paths(
					//graph.get_vertex(1), graph.get_vertex(3), 300);
					graph.get_vertex(inicio), graph.get_vertex(fin), 4);
			System.out.println(":"+shortest_paths_list);
		//	System.out.println(yenAlg.get_result_list().size());	

		    BuscarSlot r = new BuscarSlot(g, shortest_paths_list);
		    resultadoSlot res = r.concatenarCaminos(fs);
		    if (res !=null) {
		    	System.out.println(res.toString());

		      Asignacion asignar = new Asignacion(g, res);
		      asignar.marcarSlotUtilizados(tiempo);

		    }
		    else {
		    	cont++;
		    	System.out.println("No se encontró camino posible.");

		    } 
		 */
        Abeja resultadoFinal = new Abeja();
        for (int z = 0; z < 10; z++) {

            ArrayList<Abeja> listaNuevasAbejas = new ArrayList();


            Collections.sort(solicitudes, new Comparator<Request>() {

                @Override
                public int compare(Request o1, Request o2) {
                    return String.valueOf(o2.getFs()).compareToIgnoreCase(String.valueOf(o1.getFs()));
                }
            });

            //se ordenas las solicitudes de entradas y se le asignan a cada abeja.
            int nroAbeja = 5;
            AsignacionDemanda asig = new AsignacionDemanda(solicitudes, nroAbeja, g);
            List<Abeja> listaAbejas = asig.asignacionAbeja();


            for (int x = 0; x < pasos; x++) {
                ArrayList<Abeja> abejatabla = new ArrayList<Abeja>();
                for (int a = 0; a < listaAbejas.size(); a++) {

                    Abeja abe = listaAbejas.get(a);
                    int b1 = 0;
                    int b2 = 0;
                    int su = 0;
                    double apl = 0;

                    //hallar b1; suma de todos los fs necesitados por todas las demandas
                    //hallar b2; suma de de la longitud de los caminos, suponiendo que se eligieron los más largo.

                    for (int m = 0; m < abe.getDemandas().size(); m++) {
                        b1 = b1 + abe.getDemandas().get(m).getFs();
                    }
                    for (int k = pasoinicio; k < pasofinal; k++) {
                        int inicio = abe.getDemandas().get(k).getOrigen();
                        int fin = abe.getDemandas().get(k).getDestino();
                        List<Path> shortest_paths_list = yenAlg.get_shortest_paths(
                                //graph.get_vertex(1), graph.get_vertex(3), 300);
                                graph.get_vertex(inicio), graph.get_vertex(fin), 4);
                        //		System.out.println(":" + shortest_paths_list);
                        b2 = b2 + (int) shortest_paths_list.get(shortest_paths_list.size() - 1).get_weight();
                        //		System.out.println("camino más largo " + b2);
                    }

                    for (int i = pasoinicio; i < pasofinal; i++) {
                        int inicio = abe.getDemandas().get(i).getOrigen();
                        int fin = abe.getDemandas().get(i).getDestino();
                        int fs = abe.getDemandas().get(i).getFs();
                        List<Path> shortest_paths_list = yenAlg.get_shortest_paths(
                                //graph.get_vertex(1), graph.get_vertex(3), 300);
                                graph.get_vertex(inicio), graph.get_vertex(fin), 4);
                        //	System.out.println(":" + shortest_paths_list);
                        BuscarSlot r = new BuscarSlot(abe.getG(), shortest_paths_list);
                        ResultadoSlot res = r.concatenarCaminos(fs);
                        if (res != null) {
                            //		System.out.println(res.toString());
                            System.out.println("######" + res);
                            Asignacion asignar = new Asignacion(abe.getG(), res);
                            su = su + res.getCantidadfs();
                            apl = apl + res.getCamino().get_weight();
                            abe.setAPL(apl);
                            abe.setSU(su);
                        } else {
                            cont++;
                            System.out.println("No se encontró camino posible.");

                        }
                    }
                    System.out.println("abejaID:" + abe.getId() + "apl:" + abe.getAPL() + "su:" + abe.getSU());
                    double funcionObjetivo = ((a1 * abe.getSU() / b1) + (a2 * abe.getAPL() / b2));
                    funcionObjetivo = Math.round(funcionObjetivo * 100) / 100d;
                    abe.setFuncionObjetivo(funcionObjetivo);
                    abejatabla.add(abe);

                    //	System.out.println("IDABEJA :" + abe.getId() + "###########funcion objetivos" + abe.getFuncionObjetivo() );
                }
                List<Abeja> abejaslist = new ArrayList<Abeja>();
                Tablavalores tabla = new Tablavalores(abejatabla);
                abejaslist = tabla.valoresob();

                ArrayList<Abeja> abejaslistpb = tabla.valorespb(abejaslist);

                for (Abeja ab : abejaslistpb) {

                    System.out.println("lista abejas para trabajar:" + ab + "tamaño:" + ab.getDemandas().size());
                }

                System.out.println();

                int numeroAleatorio = (int) (Math.random() * 9) + 1;
                //	System.out.println("numero aleatorio:" + numeroAleatorio);
                float numeroAleatorio2 = (float) numeroAleatorio / 10;
                numeroAleatorio2 = 0.97f;
                //	System.out.println("numero aleatorio:" + numeroAleatorio2);

                Reclutamiento reclutas = new Reclutamiento(abejaslistpb, numeroAleatorio2, pasofinal);
                listaNuevasAbejas = reclutas.reclutarAbejas();


                for (Abeja ab : listaNuevasAbejas) {

                    System.out.println(ab);
                }

                pasoinicio = pasofinal;
                pasofinal = pasofinal + divpasos;
            }
            double auxiliar = 1000;
            Abeja nuevaPoblacion = new Abeja();
            ArrayList<Request> aux = new ArrayList();
            for (int k = 0; k < listaNuevasAbejas.size(); k++) {
                if (listaNuevasAbejas.get(k).getFuncionObjetivo() < auxiliar) {
                    nuevaPoblacion = listaNuevasAbejas.get(k);
                    auxiliar = listaNuevasAbejas.get(k).getFuncionObjetivo();
                }
            }
            for (int l = 0; l < nuevaPoblacion.getDemandas().size(); l++) {
                aux.add(nuevaPoblacion.getDemandas().get(l));
            }
            solicitudes = aux;
            pasoinicio = 0;
            pasofinal = divpasos;
            resultadoFinal = nuevaPoblacion;
        }
        System.out.println("#############");
        System.out.println("Cantidad de conexiones entrantes :" + contlineatxt);
        System.out.println("Cantidad de conexiones fallidas :" + cont);

        System.out.println("La mejor opción la tiene la abeja: " + resultadoFinal);
    }


    //con los caminos que tenemos en shortest_paths_list se va armar el vector de slot que tenemos en el grafoMatriz g.


}

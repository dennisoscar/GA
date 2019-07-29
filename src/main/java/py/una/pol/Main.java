package py.una.pol;

import agapi.Configuracion;
import agapi.impl.FuncionRanking;
import agapi.impl.SelectorPostCruceTaigeto;
import agapi.impl.SelectorSUS;

public class Main {
    public static void main(String[] args) {
        Configuracion c = new Configuracion();
        c.setNumeroEjecuciones(1);
        c.setNumeroGeneraciones(10);
        c.setSelector(new SelectorSUS(new FuncionRanking(1.1)));
        c.setSelectorPostCruce(new SelectorPostCruceTaigeto());
        c.setProbabilidadCruce(0.7);
        c.setProbabilidadMutacion(0.05);
        c.setElitismo(true);
        c.setTamanoPoblacion(10);
//        c.setTipoIndividuo(individuo);
        c.setTamanoCromosoma(20);
        c.iniciarProceso();
        c.aTexto(Configuracion.GENERACIONES_CON_POBLACIONES);
        double tiempo =
                c.getProceso().getTiempoProceso() / 1000000000.0;
        System.out.println("Tiempo: " + tiempo + " segundos");
    }
}

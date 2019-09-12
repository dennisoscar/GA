package py.una.pol.model;

import agapi.impl.IndividuoCombinatorio;

public class IndividuoEnteroMultifibras extends IndividuoCombinatorio {
    SimuladorRSAMultifibras simuladorRSAMultifibras = new SimuladorRSAMultifibras();
    ParametrosRetornoRsa parametrosRetornoRsa = new ParametrosRetornoRsa();

    @Override
    public double calcFA() {
        double fa = 0;
        double faB = 0;
//        int[] s = this.getCromosoma();
        parametrosRetornoRsa = simuladorRSAMultifibras.SimuladorRSAMultifibras(this.getCromosoma());
        fa = parametrosRetornoRsa.getMaximoExpectro();
        faB = parametrosRetornoRsa.getCantidadBloqueos();
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

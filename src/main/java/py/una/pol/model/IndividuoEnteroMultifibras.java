package py.una.pol.model;

import agapi.impl.IndividuoCombinatorio;

public class IndividuoEnteroMultifibras extends IndividuoCombinatorio {
    SimuladorRSAMultifibras simuladorRSAMultifibras = new SimuladorRSAMultifibras();
    ParametrosRetornoRsa parametrosRetornoRsa = new ParametrosRetornoRsa();

    @Override
    public double calcFAforMultifiber(Integer individuoId) {
        double fa = 0;
        double faB = 0;
//        int[] s = this.getCromosoma();
        parametrosRetornoRsa = simuladorRSAMultifibras.RunSimuladorRSAMultifibras(this.getCromosoma(),individuoId);
        fa = parametrosRetornoRsa.getMaximoExpectro();
        faB = parametrosRetornoRsa.getCantidadBloqueos();
        if(individuoId!= null && individuoId.equals(0)) {
            super.setCromosoma(parametrosRetornoRsa.getCromosomaOrdenado());
        }
        return fa;

    }

    @Override
    public double calcFA() {
        return 0;
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

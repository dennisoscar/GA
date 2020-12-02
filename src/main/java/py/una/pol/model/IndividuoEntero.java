package py.una.pol.model;

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

import agapi.impl.IndividuoCombinatorio;

public class IndividuoEntero extends IndividuoCombinatorio {
    SimuladorRSA simuladorRSA = new SimuladorRSA();
    ParametrosRetornoRsa parametrosRetornoRsa = new ParametrosRetornoRsa();

    @Override
    public double calcFAforMultifiber(Integer individuoId) {
        return 0;
    }

    @Override
    public double calcFA() {
        double fa = 0;
        double faB = 0;
//        int[] s = this.getCromosoma();
        parametrosRetornoRsa = simuladorRSA.SimulacionRsa(this.getCromosoma());
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

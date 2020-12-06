package agapi.impl;

import agapi.Individuo;
import agapi.SelectorPostCruce;

public class SelectorPostCruceCombinado implements SelectorPostCruce {

    /**
     * Este método se encarga de realizar dos procesos de acuerdo al valor que se encuentra
     * en el parametro @activarSelector,
     *
     * @param padres los 2 individuos que se cruzan
     * @param hijos  los 2 individuos productos del cruce
     * @param activarSelector valor boolean que activa el tipo de seleccion
     * @return los 2 individuos seleccionados para la siguiente generación
     */
    @Override
    public Individuo[] seleccionPostCruce(Individuo[] padres, Individuo[] hijos, boolean activarSelector) {
        Individuo[] exitosos = new Individuo[2];
        if(activarSelector){

           Individuo[] a = new Individuo[4];
           a[0] = padres[0];
           a[1] = padres[1];
           a[2] = hijos[0];
           a[3] = hijos[1];
           double mayor = -Double.MAX_VALUE;
           int indice = 0;
           for (int i = 0; i < a.length; i++) {
               if (a[i].getFA() >= mayor) {
                   exitosos[0] = a[i];
                   mayor = a[i].getFA();
                   indice = i;
               }
           }
           double segundoMayor = -Double.MAX_VALUE;
           for (int i = 0; i < a.length; i++) {
               if (i != indice) {
                   if (a[i].getFA() >= segundoMayor) {
                       exitosos[1] = a[i];
                       segundoMayor = a[i].getFA();
                   }
               }
           }
           return exitosos;
       }
       else{
           return hijos;
       }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Solo Hijos");
        return sb.toString();
    }
}

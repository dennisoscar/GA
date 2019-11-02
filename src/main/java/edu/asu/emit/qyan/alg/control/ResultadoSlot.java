package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.Path;

import java.util.Arrays;

public class ResultadoSlot {

    Path camino;
    int[] vectorAsignacion;
    //Se debe cambiar el vector asignación por una matriz
    //con la misma estructura que el enlace fibra/frecuency Slot
    int indice;
    int contador;
    int cantidadfs;


    public ResultadoSlot() {
        super();
    }

    public Path getCamino() {
        return camino;
    }

    public void setCamino(Path camino) {
        this.camino = camino;
    }

    public int[] getVectorAsignacion() {
        return vectorAsignacion;
    }

    public void setVectorAsignacion(int[] vectorAsignacion) {
        this.vectorAsignacion = vectorAsignacion;
    }


    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }


    public int getCantidadfs() {
        return cantidadfs;
    }

    public void setCantidadfs(int cantidadfs) {
        this.cantidadfs = cantidadfs;
    }

    @Override
    public String toString() {
        return "resultadoSlot [camino=" + camino + ", vectorAsignacion=" + Arrays.toString(vectorAsignacion)
                + ", indice=" + indice + ", contador=" + contador + ", cantidadfs=" + cantidadfs + "]";
    }


}

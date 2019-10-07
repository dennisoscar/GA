package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.Path;

import java.util.Arrays;
import java.util.List;

public class ResultadoSlotV2 {
    Path camino;
    int[] vectorAsignacion;
    int indice;
    int contador;
    int cantidadfs;
    List<Integer> fibraList;


    public ResultadoSlotV2() {
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

    public List<Integer> getFibraList() {
        return fibraList;
    }

    public void setFibraList(List<Integer> fibraList) {
        this.fibraList = fibraList;
    }

    public void addFibra(Integer fibra) {
        this.fibraList.add(fibra);
    }

    @Override
    public String toString() {
        return "resultadoSlot [camino=" + camino + ", vectorAsignacion=" + Arrays.toString(vectorAsignacion)
                + ", indice=" + indice + ", contador=" + contador + ", ListaFibra=" + Arrays.toString(new List[]{fibraList}) + ", cantidadfs=" + cantidadfs + "]";
    }
}

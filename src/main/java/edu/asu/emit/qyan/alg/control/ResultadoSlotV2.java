package edu.asu.emit.qyan.alg.control;

import edu.asu.emit.qyan.alg.model.Path;

import java.util.Arrays;

public class ResultadoSlotV2 {
    Path camino;
    int[] auxFSResultado;
    int indice;
    int contador;
    int cantidadfs;
    int[] indiceFibra;
    int indiceFS;


    public ResultadoSlotV2() {
        super();
    }

    public Path getCamino() {
        return camino;
    }

    public void setCamino(Path camino) {
        this.camino = camino;
    }

    public int[] getAuxFSResultado() {
        return auxFSResultado;
    }

    public void setAuxFSResultado(int[] auxFSResultado) {
        this.auxFSResultado = auxFSResultado;
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

    public int[] getIndiceFibra() {
        return indiceFibra;
    }

    public void setIndiceFibra(int[] indiceFibra) {
        this.indiceFibra = indiceFibra;
    }

    public int getIndiceFS() {
        return indiceFS;
    }

    public void setIndiceFS(int indiceFS) {
        this.indiceFS = indiceFS;
    }

    @Override
    public String toString() {
        return "resultadoSlot [camino=" + camino + ", Array de AUXRespuesta de FS=" + Arrays.toString(auxFSResultado)
                + ", indice de fibra " + Arrays.toString(indiceFibra) + ", indiceFS " + indiceFS +
                "]";
    }
}

package edu.asu.emit.qyan.alg.control;

public class EnlaceV2 {
    public int distancia;
    public int cantfs;
    public FibraOptica[] listafibra;

    public EnlaceV2(int distancia, int cantfs, int tam) {
        super();
        this.distancia = distancia;
        this.cantfs = cantfs;
        this.listafibra = new FibraOptica[tam];
        for (int i = 0; i < this.listafibra.length; i++) {
            //inicializar los Fs
            this.listafibra[i].listafs = new FrecuenciaSlot[5];
        }

    }


    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public int getCantfs() {
        return cantfs;
    }

    public void setCantfs(int cantfs) {
        this.cantfs = cantfs;
    }

    public FibraOptica[] getListafs() {
        return listafibra;
    }

    public void setListafs(FibraOptica[] listafs) {
        this.listafibra = listafs;
    }
}

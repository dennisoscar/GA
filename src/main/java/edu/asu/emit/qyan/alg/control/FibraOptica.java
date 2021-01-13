package edu.asu.emit.qyan.alg.control;

public class FibraOptica {
    public int libreOcupado;
    //representa el indice de la fibra a ser utilizada
    public int id;
    public int tiempo;
    public FrecuenciaSlot[] listafs;
    public int prioridad;
    public int costo;
    public String tipoFs;

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }


    public FibraOptica(int tam) {
        super();
        this.listafs = new FrecuenciaSlot[tam];
    }

    public FibraOptica() {
    }

    public String getTipoFs() {
        return tipoFs;
    }

    public void setTipoFs(String tipoFs) {
        this.tipoFs = tipoFs;
    }

    public FrecuenciaSlot[] getListafs() {
        return listafs;
    }

    public void setListafs(FrecuenciaSlot[] listafs) {
        this.listafs = listafs;
    }

    public int getLibreOcupado() {
        return libreOcupado;
    }

    public void setLibreOcupado(int libreOcupado) {
        this.libreOcupado = libreOcupado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }
}

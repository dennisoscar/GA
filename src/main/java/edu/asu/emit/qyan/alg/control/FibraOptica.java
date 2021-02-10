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
    public int ml;
    public int mc;
    public double mw;
    public Boolean mlMark;
    public Boolean mcMark;

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setMl(int ml) {
        this.ml= ml;
    }

    public int getMl() {
        return ml;
    }

    public int getMc() {
        return mc;
    }

    public double getMw() {
        return mw;
    }

    public void setMc(int mc) {
        this.mc= mc;
    }

    public void setMw(double mw) {
        this.mw = mw;
    }

    public boolean isMlMark() {
        return mlMark;
    }

    public void setMlMark(Boolean mlMark) {
        if(this.mlMark == null ) {
            this.mlMark = false;
        } else {
            this.mlMark = mlMark;
        }
    }

    public boolean isMcMark() {
        return mcMark;
    }

    public void setMcMark(Boolean mcMark) {
        if(this.mcMark == null ) {
            this.mcMark = false;
        } else {
            this.mcMark = mcMark;
        }
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

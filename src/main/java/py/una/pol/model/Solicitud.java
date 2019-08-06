package py.una.pol.model;

public class Solicitud {

    private int origen;
    private int destino;
    private int fs;

    public Solicitud() {

    }

    public Solicitud(int origen, int destino, int fs) {
        this.origen = origen;
        this.destino = destino;
        this.fs = fs;
    }


    public int getOrigen() {
        return origen;
    }


    public void setOrigen(int origen) {
        this.origen = origen;
    }


    public int getDestino() {
        return destino;
    }


    public void setDestino(int destino) {
        this.destino = destino;
    }


    public int getFs() {
        return fs;
    }


    public void setFs(int fs) {
        this.fs = fs;
    }


    @Override
    public String toString() {
        return "Request [origen=" + origen + ", destino=" + destino + ", fs=" + fs + "]";
    }


}

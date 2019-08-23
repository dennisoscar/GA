package py.una.pol.model;

public class ParametrosRetornoRsa {
    int maximoExpectro;
    int cantidadBloqueos;

    public ParametrosRetornoRsa(int maximoExpectro, int cantidadBloqueos) {
        this.maximoExpectro = maximoExpectro;
        this.cantidadBloqueos = cantidadBloqueos;
    }

    public ParametrosRetornoRsa() {
    }

    public int getMaximoExpectro() {
        return maximoExpectro;
    }

    public void setMaximoExpectro(int maximoExpectro) {
        this.maximoExpectro = maximoExpectro;
    }

    public int getCantidadBloqueos() {
        return cantidadBloqueos;
    }

    public void setCantidadBloqueos(int cantidadBloqueos) {
        this.cantidadBloqueos = cantidadBloqueos;
    }
}

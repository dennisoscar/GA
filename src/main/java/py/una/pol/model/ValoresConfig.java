package py.una.pol.model;

public class ValoresConfig {
    int numeroEjecuciones;
    int numeroGeneraciones;
    double probabilidadDeSeleccion;
    double probabilidadDeCruce;
    double probabilidadDeMutacion;
    double probabilidadSelectorPostCruce;
    int tamanhoPoblacion;
    int tamanhoCromosoma;

    public ValoresConfig(int numeroEjecuciones, int numeroGeneraciones, double probabilidadDeSeleccion,
                         double probabilidadDeCruce, double probabilidadDeMutacion, int tamanhoPoblacion, int tamanhoCromosoma, double probabilidadSelectorPostCruce) {
        this.numeroEjecuciones = numeroEjecuciones;
        this.numeroGeneraciones = numeroGeneraciones;
        this.probabilidadDeSeleccion = probabilidadDeSeleccion;
        this.probabilidadDeCruce = probabilidadDeCruce;
        this.probabilidadDeMutacion = probabilidadDeMutacion;
        this.probabilidadSelectorPostCruce = probabilidadSelectorPostCruce;
        this.tamanhoPoblacion = tamanhoPoblacion;
        this.tamanhoCromosoma = tamanhoCromosoma;
    }

    public ValoresConfig() {
    }

    public int getNumeroEjecuciones() {
        return numeroEjecuciones;
    }

    public void setNumeroEjecuciones(int numeroEjecuciones) {
        this.numeroEjecuciones = numeroEjecuciones;
    }

    public int getNumeroGeneraciones() {
        return numeroGeneraciones;
    }

    public void setNumeroGeneraciones(int numeroGeneraciones) {
        this.numeroGeneraciones = numeroGeneraciones;
    }

    public double getProbabilidadDeSeleccion() {
        return probabilidadDeSeleccion;
    }

    public void setProbabilidadDeSeleccion(double probabilidadDeSeleccion) {
        this.probabilidadDeSeleccion = probabilidadDeSeleccion;
    }

    public double getProbabilidadSelectorPostCruce() {
        return probabilidadSelectorPostCruce;
    }

    public void setProbabilidadSelectorPostCruce(double probabilidadSelectorPostCruce) {
        this.probabilidadSelectorPostCruce = probabilidadSelectorPostCruce;
    }

    public double getProbabilidadDeCruce() {
        return probabilidadDeCruce;
    }

    public void setProbabilidadDeCruce(double probabilidadDeCruce) {
        this.probabilidadDeCruce = probabilidadDeCruce;
    }

    public double getProbabilidadDeMutacion() {
        return probabilidadDeMutacion;
    }

    public void setProbabilidadDeMutacion(double probabilidadDeMutacion) {
        this.probabilidadDeMutacion = probabilidadDeMutacion;
    }

    public int getTamanhoPoblacion() {
        return tamanhoPoblacion;
    }

    public void setTamanhoPoblacion(int tamanhoPoblacion) {
        this.tamanhoPoblacion = tamanhoPoblacion;
    }

    public int getTamanhoCromosoma() {
        return tamanhoCromosoma;
    }

    public void setTamanhoCromosoma(int tamanhoCromosoma) {
        this.tamanhoCromosoma = tamanhoCromosoma;
    }
}

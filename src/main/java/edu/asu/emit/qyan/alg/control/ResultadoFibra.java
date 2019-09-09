package edu.asu.emit.qyan.alg.control;

import java.util.List;

public class ResultadoFibra {
    public List<ResultadoSlot> resultadoSlotList;
    FibraOptica fibraOptica;
    int indiceFibra;

    public ResultadoFibra() {
    }

    public List<ResultadoSlot> getResultadoSlotList() {
        return resultadoSlotList;
    }

    public void setResultadoSlotList(List<ResultadoSlot> resultadoSlotList) {
        this.resultadoSlotList = resultadoSlotList;
    }

    public void addResultadoSlotList(ResultadoSlot resultadoSlot) {
        this.resultadoSlotList.add(resultadoSlot);
    }

    public FibraOptica getFibraOptica() {
        return fibraOptica;
    }

    public void setFibraOptica(FibraOptica fibraOptica) {
        this.fibraOptica = fibraOptica;
    }

    public int getIndiceFibra() {
        return indiceFibra;
    }

    public void setIndiceFibra(int indiceFibra) {
        this.indiceFibra = indiceFibra;
    }
}

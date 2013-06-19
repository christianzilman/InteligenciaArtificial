/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.siuden.inteligenciaartificial.problemamochila;

import javax.swing.JOptionPane;
import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

/**
 *
 * @author juliozilman
 */
public class FuncionFitnessMochila extends FitnessFunction {

    private static final double MAX_PERMITIDO = 1000000000.0d;
    private double volumenMochila;
    private Mochila modeloMochila;

    public FuncionFitnessMochila() {
    }
    
    public FuncionFitnessMochila(double _volumenMochila, Mochila _modeloMochila) {
        if (_volumenMochila < 1 || _volumenMochila >= MAX_PERMITIDO) {
            throw new IllegalArgumentException(
                    "El volumen de la mochila de estar comprendido de 1 a " + MAX_PERMITIDO + ".");            
        }
        volumenMochila = _volumenMochila;
        modeloMochila = _modeloMochila;
    }

    /**
     * Determinar la aptitud de la instancia del cromosoma dado. Cuanto mayor
     * sea el valor de retorno, más encajará la instancia. Este método siempre
     * debe devolver el mismo valor de aptitud para dos instancias cromosómicas
     * equivalentes.
     *
     * @param ic
     * @return
     */
    @Override
    protected double evaluate(IChromosome ic) {
        // El valor de fitness mide tanto cuán cerca estamos del óptimo, así como del número total
        // de ítems representados en la solución.
        double totalVolumen = getTotalVolumen(ic);
        double diferenciaVolumen = Math.abs(getVolumenMochila() - totalVolumen);
        double fitness = 0.0d;

        // 1) Determinar la distance entre la cantidad representada por la solución y el objetivo
        fitness += bonusDiferenciaVolumen(MAX_PERMITIDO, diferenciaVolumen);
        // Nos aseguramos que el fitness sea siempre positivo
        // -------------------------------------------
        return Math.max(1.0d, fitness);
    }

    /**
     *
     * @param solucionPotencial
     * @return
     */
    public double getTotalVolumen(IChromosome solucionPotencial) {
        double volume = 0.0d;
        for (int i = 0; i < solucionPotencial.size(); i++) {
            volume += getNumeroElementosEnGene(solucionPotencial, i)
                    * getModeloMochila().getVolumenArticulo().get(i);
        }
        return volume;
    }

    /**
     *
     * @param solucionPotencial
     * @param posicion
     * @return el numero de elementos representados por la solución potencial
     * pasada en la posición
     */
    public int getNumeroElementosEnGene(IChromosome solucionPotencial,
            int posicion) {
        Integer numItems =
                (Integer) solucionPotencial.getGene(posicion).getAllele();
        return numItems.intValue();
    }

    /**
     * Calcula el bonus del valor fitness
     *
     * @param maxFitness máximo valor aplicable a la función fitness
     * @param diferenciaVolumen diferencia de volumen entre los elementos
     * @return bonus por la diferecnia de volumen dado
     */
    protected double bonusDiferenciaVolumen(double maxFitness,
            double diferenciaVolumen) {
        if (diferenciaVolumen == 0) {
            return maxFitness;
        } else {
            // Arbitrariamente se trabaja con la mitad del fitnes menos el cuardado de la diferencia de
            // volumen
            return maxFitness / 2 - (diferenciaVolumen * diferenciaVolumen);
        }
    }

    /**
     * @return the volumenMochila
     */
    public double getVolumenMochila() {
        return volumenMochila;
    }

    /**
     * @param volumenMochila the volumenMochila to set
     */
    public void setVolumenMochila(double volumenMochila) {
        this.volumenMochila = volumenMochila;
    }

    /**
     * @return the modeloMochila
     */
    public Mochila getModeloMochila() {
        return modeloMochila;
    }

    /**
     * @param modeloMochila the modeloMochila to set
     */
    public void setModeloMochila(Mochila modeloMochila) {
        this.modeloMochila = modeloMochila;
    }
}

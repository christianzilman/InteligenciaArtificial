/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.siuden.inteligenciaartificial.problemamochila;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.data.DataTreeBuilder;
import org.jgap.data.IDataCreators;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.xml.XMLDocumentBuilder;
import org.jgap.xml.XMLManager;
import org.w3c.dom.Document;

/**
 *
 * @author juliozilman
 */
public class Mochila {

    /**
     * El número total de veces que vamos a dejar que la población evoluciones
     */
    private static final int MAX_PERMITO_EVOLUCIONES = 500;
    /* Volumnes de elemento ingresados */
    private List<Double> volumenArticulo;
    /* nombre de elementos ingresados*/
    private List<String> nombreArticulo;
    private double volumenMochila;

    public Mochila() {
        volumenArticulo = new ArrayList<Double>();
        nombreArticulo = new ArrayList<String>();
    }

    public String buscarElementosMochila() {
        String resultado = "";
        try {
            // Comienza la configuración, con la configuración por defecto que trae las cosas más usadas
            // -------------------------------------------------------------
            Configuration.reset();
            Configuration configuracion;
            configuracion = new DefaultConfiguration();
            configuracion.setPreservFittestIndividual(true);
            // Establecemos la función fitness la cuál usaremos .
            // ---------------------------------------------------------            
            FitnessFunction miFuncionFitness = new FuncionFitnessMochila(volumenMochila, this);


            configuracion.setFitnessFunction(miFuncionFitness);

            /*
             * Ahora se configurará la estructura del cromosoma. Cada gen estará codificado por un número entero que
             * representará la cantidad de un ítem en particular a incorporar a la mochila.
             */
            Gene[] genes = new Gene[volumenArticulo.size()];
            for (int i = 0; i < volumenArticulo.size(); i++) {
                genes[i] = new IntegerGene(configuracion, 0, 1);
            }
            IChromosome chromosome = new Chromosome(configuracion, genes);

            configuracion.setSampleChromosome(chromosome);


            // En cuanto al tamaño de la población, se ajusta a 50 individuos:
            configuracion.setPopulationSize(50);
            // Creará la población incial de los cromosomas aleatoriamente
            // -----------------------------------------------------------------
            Genotype poblacion;
            poblacion = Genotype.randomInitialGenotype(configuracion);

            /*
             * Ahora se procede a la mecánica genética, se hará evolucionar la población una cantidad paramétrica de veces
             * (300 en este caso) dado que no se conoce cuándo se llega al óptimo:
             */
            for (int i = 0; i < MAX_PERMITO_EVOLUCIONES; i++) {
                poblacion.evolve();
            }

            //Guardar el progreso en el archivo. Una nueva ejecución de este ejemplo 
            //será capaz de reanudar donde se detuvo antes!
            // ---------------------------------------------------------------------
            // Reprensamos el Genotipo como un arbol con los elementos de los cromosomas y genes
            // ------------------------------------------------------------
            DataTreeBuilder builder = DataTreeBuilder.getInstance();
            IDataCreators doc2 = builder.representGenotypeAsDocument(poblacion);
            // creamos documento XML del arbol generado
            // ---------------------------------------
            XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
            Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
            XMLManager.writeFile(xmlDoc, new File("problemaMochilaJGAP.xml"));

            // Mostramos la mejor solucion por pantalla gráfica
            // -----------------------------------
            IChromosome mejorSolucionChromosome = poblacion.getFittestChromosome();
            resultado += "La mejor solución tiene un valor fitness de " + mejorSolucionChromosome.getFitnessValue() + "\n";
            resultado += "Contiene la siguiente información:  \n";
            int cantidad;
            double totalVolume = 0.0d;
            for (int i = 0; i < mejorSolucionChromosome.size(); i++) {
                cantidad = ((Integer) mejorSolucionChromosome.getGene(i).getAllele()).intValue();
                if (cantidad > 0) {
                    resultado += "\t " + cantidad + " x " + nombreArticulo.get(i) + " peso: " + volumenArticulo.get(i) + " gr" + "\n";
                    totalVolume += volumenArticulo.get(i) * cantidad;
                }
            }
            resultado += "\n Para un volumen total de la mochila que es " + totalVolume + " gr" + " \n";
            resultado += "El Volumen esperado de la mochila: " + volumenMochila + " gr" + " \n";
            resultado += "Diferencia en el volumen es: " + Math.abs(totalVolume - volumenMochila) + " gr" + " \n";
        } catch (IllegalArgumentException exI) {
            JOptionPane.showMessageDialog(null, "El volumen de la mochila de estar comprendido de 1 a " + 1000000000 + ".", "Validación del sistema", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultado;
    }

    /**
     * @return the volumenArticulo
     */
    public List<Double> getVolumenArticulo() {
        return volumenArticulo;
    }

    /**
     * @param volumenArticulo the volumenArticulo to set
     */
    public void setVolumenArticulo(List<Double> volumenArticulo) {
        this.volumenArticulo = volumenArticulo;
    }

    /**
     * @return the nombreArticulo
     */
    public List<String> getNombreArticulo() {
        return nombreArticulo;
    }

    /**
     * @param nombreArticulo the nombreArticulo to set
     */
    public void setNombreArticulo(List<String> nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
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
}

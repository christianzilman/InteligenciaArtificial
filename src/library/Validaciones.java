package library;

import java.util.*;
import java.text.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

/**
 * Implementa las funciones de validación para los formularios
 *
 * @version 1.0 $date 29/01/03
 */
public final class Validaciones {

    /**
     * Variables y métodos get y set necesarios para realizar el control de los
     * campos del formularios
     */
    /* Caracteres válidos */
    private static String caracteresValidos = "[0-9|A-Z|a-z||-|(|)|:|||1º|ª|ñ|Ñ|á|é|í|ó|ä|ë|ö|ü|ç|à|è|ì|ò|ù]";
    /* Caracteres válidos para control de usuarios*/
    private static String caracteresValidosUsuario = "[A-Z|a-z|0-9|ñ|Ñ|á|é|í|ó|ä|ë|ö|ü|ç|à|è|ì|ò|ù|-|(|)|:|\\|]";
    /* Caracteres inválidos */
    private static String caracteresInvalidos = "[\\\\¨$´¤¾¼½|]";
    /* Caracteres numericos */
    private static String caracteresNumericos = "[0-9]";
    /* Atributos para las validaciones */
    private static Pattern patron = null;
    private static Matcher matcher = null;

    /**
     * Funciones que van a realizar las validaciones de los distintos campos de
     * los formularios.
     *
     */
    /**
     * Función que valida si un campo de texto esta correctamente completado
     *
     * @param nombre nombre de la función get asociada al campo
     * @return boolean En función del resultado, retorna true si se cumple o
     * false si la comprobación es incorrecta
     */
    public static boolean validarString(String nombre) {
        String campo = nombre.trim();
        if (campo.length() <= 0) {
            return false;
        }
        patron = Pattern.compile(caracteresInvalidos);
        matcher = patron.matcher(campo);
        if (matcher.find()) {
            System.out.println("Caracter Invalido" + nombre);
            return false;
        }
        patron = Pattern.compile(caracteresValidos);
        matcher = patron.matcher(campo);
        int encontrados = 0;
        char[] caracteres = campo.toCharArray();
        for (int i = 0; i < caracteres.length; i++) {
            matcher = patron.matcher(Character.toString(caracteres[i]));
            if (matcher.find()) {
                encontrados++;
            }
        }
        if (encontrados < 1) {
            return false;
        }
        return true;
    } // validarString()

    /**
     * Método que valida los campos select.
     *
     * @param nombre valor del campo del formulario
     * @param cadena referencia necesaria para dar mensaje de error
     * @return boolean En función del resultado, retorna true si se cumple o
     * false si la comprobación es incorrecta
     */
    public boolean validaCampoSelect(String nombre, String cadena) {
        //OJO entiendo que en el desplegable, la opción vacío va a venir con 0. sino habría que mirarlo
        if (nombre.trim().compareTo("") == 0) {
            return false;
        }
        return true;
    }// Fin del método validaCampoSelect()

    /**
     * Chequea que el valor que se le pasa sea un número sin signo y entero.
     *
     * @param nombre cadena que va a ser controlada
     * @return boolean En función del resultado, retorna true si se cumple o
     * false si la comprobación es incorrecta
     */
    public static boolean isNotNumero(String nombre) {
        patron = Pattern.compile(caracteresNumericos);
        matcher = patron.matcher(nombre);
        if (matcher.find()) {
            return false;
        } else {
            return true;
        }
    }// Fin del método isNumero()

    /**
     * Valida que los formatos numéricos sean correctos.
     *
     * @param numero valor del campo del formulario
     * @param obligatorio si debe rellenarse o no
     * @param sinNumero si no admite numeros,
     * @param cadena string que se muestra en caso de error
     * @param maximo longitud del campo
     * @param fijo si la longitud debe ser siempre la misma
     * @return boolean En función del resultado, retorna true si se cumple o
     * false si la comprobación es incorrecta
     */
    public static boolean validarEntero(String nombre) {
        String campo = nombre.trim();
        if (campo.length() <= 0) {
            return false;
        }
//        if (campo.length() > 0){
//            if (isNotNumero(campo)) {
//                return false;
//            }
//        }
//        return true;
        try {
            Integer.parseInt(campo);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }// validarEntero()

    public static boolean validarNumerico(String nombre) {
        String campo = nombre.trim();
        if (campo.length() <= 0) {
            return false;
        }
        try {
            Double.parseDouble(campo);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }// validarNumerico()

    /**
     * Valida el campo email de cualquier formulario.
     *
     * @param email valor del campo del formulario,
     * @param cadena string que se muestra en caso de error
     * @return boolean En función del resultado, retorna true si se cumple o
     * false si la comprobación es incorrecta
     */
    public boolean validaEmail(String email, String cadena) {
        String campo = email.trim();
        if (campo.length() > 0) {
            if (campo.length() < 6) {
                return false;
            }
            if (campo.length() > 32) {
                return false;
            }
            if ((campo.indexOf("@") < 1) || (campo.indexOf("@") > (campo.length() - 5))) {
                return false;
            }
            if (campo.indexOf(".") > (campo.length() - 3)) {
                return false;
            }
        }
        return true;
    }//Fin del método validaEmail()

    /**
     * Método que valida el formato de las fechas mm/yyyy
     *
     * @param fecha valor de fecha introducido
     * @param cadena error que envia si no es correcto
     * @param obligatorio indica si el campo es o no obligatorio
     * @return boolean En función del resultado, retorna true si se cumple o
     * false si la comprobación es incorrecta
     */
    public boolean validaFecha_tarjeta(String fecha, String cadena, boolean obligatorio) {
        String campo = fecha.trim();
        if (obligatorio) {
            if (campo.length() <= 0) {
                return false;
            }
        }
        if (campo.length() > 0) {
            if (campo.length() != 7) {
                return false;
            }
            //saca de la fecha mes y año
            String[] fech1 = campo.split("[/]");
            //comprueba que el array tenga dos elementos, lo que significaría que el formato es correcto mm/yyyy
            if (fech1.length < 2) {
                return false;
            }
            //extraemos los valores del array
            int mes = new Integer(fech1[0]).intValue();
            int anio = new Integer(fech1[1]).intValue();

            if (mes < 1 || mes > 12) {
                return false;
            }
            //comprueba que el año este entre 1900 y 2099
            if (anio < 1900 || anio > 2099) {
                return false;
            }

        }
        return true;
    }//Fin del método validaFecha_tarjeta()

    /**
     * Método que valida el formato de las fechas
     *
     * @param fecha valor de fecha introducido
     * @param cadena error que envia si no es correcto
     * @param obligatorio indica si el campo es o no obligatorio
     * @return boolean En función del resultado, retorna true si se cumple o
     * false si la comprobación es incorrecta
     */
    public static boolean validarFecha(String fecha) {
        String campo = fecha.trim();
        if (campo.length() <= 0) {
            return false;
        }
        if (campo.length() > 0) {
            if (campo.length() != 10) {
                return false;
            }
            //saca de la fecha dia, mes y año
            String[] fech1 = campo.split("[/]");
            //comprueba que haya introducido el formato dd/mm/yyyy
            if (fech1.length < 3) {
                return false;
            }
            int dia = new Integer(fech1[0]).intValue();
            int mes = new Integer(fech1[1]).intValue();
            int anio = new Integer(fech1[2]).intValue();
            //el mes debe estar entre 1 y 12
            if (mes < 1 || mes > 12) {
                return false;
            }
            //comprueba que el año este entre 1900 y 2099
            if (anio < 1900 || anio > 2099) {
                return false;
            }
            //comprueba el numero de dias dependiendo del mes
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                if (dia <= 0 || dia > 31) {
                    return false;
                }
            }
            if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                if (dia <= 0 || dia > 30) {
                    return false;
                }
            }
            if (mes == 2) {
                if (anio % 4 > 0) {
                    if (dia > 28) {
                        return false;
                    }
                } else if (anio % 100 == 0 && anio % 400 > 0) {
                    if (dia > 28) {
                        return false;
                    }
                } else {
                    if (dia > 29) {
                        return false;
                    }
                }
            }
        }
        return true;
    }//Fin del método validaFecha()

    /**
     * Método que compara la fecha introducida para la tarjeta con la fecha
     * actual (unica validación posible sobre fecha)
     *
     * @param fechaIntroducida fecha que ha introducido el usuario
     * @return boolean En función del resultado, retorna true si se cumple o
     * false si la comprobación es incorrecta
     */
    public boolean compararFechas(String fechaIntroducida) {
        String fechaSistema = null;

        Timestamp tmstp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaSistema = sdf.format(tmstp);
        Date sistema = null;
        Date caducidad = null;

        try {
            sistema = sdf.parse(fechaSistema);
            caducidad = sdf.parse(fechaIntroducida);
        } catch (ParseException e) {
            e.printStackTrace(System.out);//Escribe en el fichero de log del servicio de aplicaciones
        }

        if (sistema.compareTo(caducidad) >= 0) {
            return false;
        }

        return true;
    }

    /**
     * Compara que la fecha ingresada no sea superior a la actual
     */
    public static boolean compararFechaMenorActual(String fechaIntroducida) {
        String fechaSistema = null;

        Timestamp tmstp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        fechaSistema = sdf.format(tmstp);
        Date sistema = null;
        Date caducidad = null;

        try {
            sistema = sdf.parse(fechaSistema);
            caducidad = sdf.parse(fechaIntroducida);
        } catch (ParseException e) {
            e.printStackTrace(System.out);//Escribe en el fichero de log del servicio de aplicaciones
        }

        if (caducidad.after(sistema)) {
            return false;
        }

        return true;
    }

    /**
     * Método que devuelve la fecha del sistema
     *
     * @return fecha en formato dd/MM/yyyy
     */
    public String getFechaHoraActual() {
        Timestamp tmstp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return (sdf.format(tmstp));
    }
}

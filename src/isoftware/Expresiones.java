/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftware;

import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author alfredo
 */
public class Expresiones {

    final static private Scanner leer = new Scanner(System.in);
    final static private HashMap<Object, Object> tabla = new HashMap<Object, Object>();

    public static String posfijo(String expresion) {
        try {
            String expresionCorrecta = corregirCadena(sinEspacios(expresion));
            StringTokenizer arreglo = new StringTokenizer(expresionCorrecta, " ");
            return hacerPosfijo(arreglo);
        } catch (Exception e) {
            System.out.println(e.getStackTrace() + "; posfijo");
            return null;
        }
    }

    public static String sinEspacios(String cadena) {
        String nueva_cadena = new String();
        for (byte i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) != ' ') {
                nueva_cadena += cadena.charAt(i);
            }
        }
        return nueva_cadena;
    }

    public static String corregirCadena(String expresion) {
        String expresionCorrecta = expresion;
        expresionCorrecta = expresionCorrecta.replace("(", " ( ");
        expresionCorrecta = expresionCorrecta.replace(")", " ) ");
        expresionCorrecta = expresionCorrecta.replace("-", " - ");
        expresionCorrecta = expresionCorrecta.replace("+", " + ");
        expresionCorrecta = expresionCorrecta.replace("/", " / ");
        expresionCorrecta = expresionCorrecta.replace("*", " * ");
        expresionCorrecta = expresionCorrecta.replace("^", " ^ ");
        expresionCorrecta = expresionCorrecta.replace("%", " % ");
        expresionCorrecta = expresionCorrecta.replace("sin", "sin ");
        expresionCorrecta = expresionCorrecta.replace("cos", "cos ");
        expresionCorrecta = expresionCorrecta.replace("tan", "tan ");
        return expresionCorrecta;
    }

    public static String hacerPosfijo(StringTokenizer expresionCorrecta) {
        int tamaño = expresionCorrecta.countTokens();
        String caracter;
        String prefijo = new String();
        Pila pilaTemp = new Pila();
        Pila pilaDef = new Pila();
        for (int i = 0; i < tamaño; i++) {
            caracter = expresionCorrecta.nextToken();
            if (caracter.equals("(")) {
                pilaTemp.PUSH(caracter);
                Object aux = pilaDef.siguientePOP();
                if (aux != null && (aux.toString().equalsIgnoreCase("sin")
                        || aux.toString().equalsIgnoreCase("cos")
                        || aux.toString().equalsIgnoreCase("tan"))) {
                    pilaDef.POP();
                    caracter = aux.toString() + "-";
                    pilaDef.PUSH(caracter);
                }
            } else if (caracter.matches("[+|*|/|^|%|-]")) {
                while (!pilaTemp.estaVacia() && !pilaTemp.siguientePOP().toString().equals("(")) {
                    if (prioridad(caracter) <= prioridad(pilaTemp.siguientePOP().toString())) {
                        pilaDef.PUSH(pilaTemp.POP());
                    } else {
                        break;
                    }
                }
                pilaTemp.PUSH(caracter);
            } else if (caracter.equals(")")) {
                while (pilaTemp.siguientePOP() != null && !pilaTemp.siguientePOP().toString().equals("(")) {
                    pilaDef.PUSH(pilaTemp.POP());
                }
                pilaTemp.POP();
            } else {
                pilaDef.PUSH(caracter);
            }
        }
        while (pilaTemp.siguientePOP() != null) {
            pilaDef.PUSH(pilaTemp.POP());
            pilaTemp.POP();
        }
        while (pilaDef.siguientePOP() != null) {
            prefijo += " " + pilaDef.POP();
        }
        return invertirCadena(prefijo);
    }

    private static String invertirCadena(String cadena) {
        String nuevaCadena = "";
        StringTokenizer arreglo = new StringTokenizer(cadena, " ");
        String elemento = new String();
        while (arreglo.hasMoreTokens()) {
            elemento = arreglo.nextToken();
            nuevaCadena = elemento + " " + nuevaCadena;
        }
        return nuevaCadena;
    }

    public static int prioridad(String elemento) {
        int res = 0;
        if (elemento.matches("[%|^]")) {
            res = 3;
        }
        if (elemento.matches("[*|/]")) {
            res = 2;
        }
        if (elemento.matches("[+|-]")) {
            res = 1;
        }
        return res;
    }

    public static void evaluarExpresion(String posfijo) {
        System.out.println("Expresion a evaluar: " + posfijo);
        StringTokenizer arreglo = new StringTokenizer(posfijo, " ");
        String elemento = new String();
        while (arreglo.hasMoreTokens()) {
            elemento = arreglo.nextToken();
            if (elemento.matches("[a-zA-Z]*")
                    && !elemento.matches("[e|E]")
                    && !elemento.equalsIgnoreCase("sin")
                    && !elemento.equalsIgnoreCase("cos")
                    && !elemento.equalsIgnoreCase("tan")) {
                System.out.println("Dame valor de "
                        + elemento + ": ");
                tabla.put(elemento, leer.nextLine());
            }
            if (elemento.matches("[e|E]")) {
                tabla.put(elemento, Math.E);
            }
        }
        construirArbol(posfijo);
    }
    
    //Estatico
    public static double evaluarExpresionEstatico(String posfijo, String valor) {
        StringTokenizer arreglo = new StringTokenizer(posfijo, " ");
        String elemento = new String();
        while (arreglo.hasMoreTokens()) {
            elemento = arreglo.nextToken();
            if (elemento.matches("[a-zA-Z]*")
                    && !elemento.matches("[e|E]")
                    && !elemento.equalsIgnoreCase("sin")
                    && !elemento.equalsIgnoreCase("cos")
                    && !elemento.equalsIgnoreCase("tan")) {
                tabla.put(elemento, valor);
            }
            if (elemento.matches("[e|E]")) {
                tabla.put(elemento, Math.E);
            }
        }
        double resultado=0;
        return construirArbolEstatico(posfijo, resultado);
    }
    
    public static double construirArbolEstatico(String posfijo, double resultado) {
        Pila pila = new Pila();
        if (posfijo == null) {
            return resultado;
        }
        StringTokenizer arreglo = new StringTokenizer(posfijo, " ");
        String elemento = new String();
        while (arreglo.hasMoreTokens()) {
            elemento = arreglo.nextToken();
            Nodo nodo = new Nodo();
            if (esOperador(elemento)) {
                Nodo aux=new Nodo();
                nodo.setDato(elemento);
                nodo.setAnterior(validaExpresionTrigonometrica(pila, nodo, aux));
                nodo.setSiguiente(validaExpresionTrigonometrica(pila, nodo, aux));
                if(aux.getDato()!=null){
                    Nodo aux2 = (Nodo)aux.getDato();
                    aux2.setAnterior(nodo);
                    nodo = aux2;
                    aux = null;
                }
            } else {
                nodo.setDato(elemento);
            }
            pila.PUSH(nodo);
        }
        //
        Nodo n = (Nodo) pila.POP();
        if ((pila.siguientePOP() != null)&&n.getDato().toString().matches("[+|*|/|^|%|-]")) {
            Nodo aux = (Nodo) pila.POP();
            aux.setAnterior(n);
            n = aux;
        }else{
            if((pila.siguientePOP() != null)&&!n.getDato().toString().matches("[+|*|/|^|%|-]")){
                Nodo aux = (Nodo) pila.POP();                
                n.setDato(aux.getDato() + " " + n.getDato());
            }
        }
        if(n.getAnterior()==null){
            double r = 0.0;
            n.setDato(obtenerValor(n));
            resultado = new Double(n.getDato().toString());
            return resultado;
        }
        return postordenEstatico(n, resultado); //Metodo local
    }

    public static void construirArbol(String posfijo) {
        Pila pila = new Pila();
        if (posfijo == null) {
            return;
        }
        StringTokenizer arreglo = new StringTokenizer(posfijo, " ");
        String elemento = new String();
        while (arreglo.hasMoreTokens()) {
            elemento = arreglo.nextToken();
            Nodo nodo = new Nodo();
            if (esOperador(elemento)) {
                Nodo aux=new Nodo();
                nodo.setDato(elemento);
                nodo.setAnterior(validaExpresionTrigonometrica(pila, nodo, aux));
                nodo.setSiguiente(validaExpresionTrigonometrica(pila, nodo, aux));
                if(aux.getDato()!=null){
                    Nodo aux2 = (Nodo)aux.getDato();
                    aux2.setAnterior(nodo);
                    nodo = aux2;
                    aux = null;
                }
            } else {
                nodo.setDato(elemento);
            }
            pila.PUSH(nodo);
        }
        //
        Nodo n = (Nodo) pila.POP();
        if ((pila.siguientePOP() != null)&&n.getDato().toString().matches("[+|*|/|^|%|-]")) {
            Nodo aux = (Nodo) pila.POP();
            aux.setAnterior(n);
            n = aux;
        }else{
            if((pila.siguientePOP() != null)&&!n.getDato().toString().matches("[+|*|/|^|%|-]")){
                Nodo aux = (Nodo) pila.POP();                
                n.setDato(aux.getDato() + " " + n.getDato());
            }
        }
        if(n.getAnterior()==null){
            double r = 0.0, resultado;
            n.setDato(obtenerValor(n));
            resultado = new Double(n.getDato().toString());
            System.out.println("Operacion: "
                + resultado);
            return;
        }
        postorden(n); //Metodo local
    }

    public static Nodo validaExpresionTrigonometrica(Pila pila, Nodo padre, Nodo auxiliar) {
        Nodo nodo = (Nodo) pila.POP();
        Nodo aux = (Nodo) pila.siguientePOP();
        if ((aux != null && (aux.getDato().toString().equalsIgnoreCase("sin")
                || aux.getDato().toString().equalsIgnoreCase("cos")
                || aux.getDato().toString().equalsIgnoreCase("tan")))
                &&!nodo.getDato().toString().matches("[+|*|/|^|%|-]")) {
            aux = (Nodo) pila.POP();
            nodo.setDato(aux.getDato().toString() + " " + nodo.getDato().toString());
        } else {
            if (aux != null && (aux.getDato().toString().equalsIgnoreCase("sin-")
                    || aux.getDato().toString().equalsIgnoreCase("cos-")
                    || aux.getDato().toString().equalsIgnoreCase("tan-"))) {
                aux.setDato(aux.getDato().toString().replace("-", ""));
            }else{
                if((aux != null && (aux.getDato().toString().equalsIgnoreCase("sin")
                || aux.getDato().toString().equalsIgnoreCase("cos")
                || aux.getDato().toString().equalsIgnoreCase("tan")))
                &&nodo.getDato().toString().matches("[+|*|/|^|%|-]")){
                    aux = (Nodo)pila.POP();
                    aux.setAnterior(nodo);
                    nodo = aux;
                }
            }
        }
        return nodo;
    }

    //Estatico
    public static double postordenEstatico(Nodo r, double resultado) {
        if (r != null) {
            postordenEstatico(r.getAnterior(),resultado);
            postordenEstatico(r.getSiguiente(),resultado);
            // Operaciones
            if (r.getDato().toString().matches("[+|/|*|^|%|-]")) {
                double anterior = 0;
                double siguiente = 0;
                anterior = obtenerValorAnterior(r);
                siguiente = obtenerValorSiguiente(r);
                resultado = realizarOperacionEstatico(r, siguiente, anterior);//Estan volteados los valores en el arbol
                return resultado;
            }
            resultado = realizarOperacionTrigonometricaEstatico(r);
        }
        return resultado;
    }
    
    public static void postorden(Nodo r) {
        if (r != null) {
            postorden(r.getAnterior());
            postorden(r.getSiguiente());
            // Operaciones
            if (r.getDato().toString().matches("[+|/|*|^|%|-]")) {
                double anterior = 0;
                double siguiente = 0;
                anterior = obtenerValorAnterior(r);
                siguiente = obtenerValorSiguiente(r);
                realizarOperacion(r, anterior, siguiente);
                return;
            }
            realizarOperacionTrigonometrica(r);
        }
    }
    
    //Estatico
    public static double realizarOperacionTrigonometricaEstatico(Nodo r) {
        if(r.getDato().toString().contains(" ") || r.getDato().toString().matches("[a-zA-Z]"))
            return 0;
        if (r.getAnterior() != null && r.getDato().toString().equalsIgnoreCase("sin")) {
            r.setDato(Math.sin(new Double(r.getAnterior().getDato().toString())));
        }
        if (r.getAnterior() != null && r.getDato().toString().equalsIgnoreCase("tan")) {
        }
        if (r.getAnterior() != null && r.getDato().toString().equalsIgnoreCase("cos")) {
            r.setDato(Math.cos(new Double(r.getAnterior().getDato().toString())));
        }
        return new Double(r.getDato().toString());
    }

    public static void realizarOperacionTrigonometrica(Nodo r) {
        if(r.getDato().toString().contains(" ") || r.getDato().toString().matches("[a-zA-Z]"))
            return;
        if (r.getAnterior() != null && r.getDato().toString().equalsIgnoreCase("sin")) {
            System.out.println("Operacion: sin("
                    + r.getAnterior().getDato().toString() + ") = "
                    + Math.sin(new Double(r.getAnterior().getDato().toString())));
            r.setDato(Math.sin(new Double(r.getAnterior().getDato().toString())));
        }
        if (r.getAnterior() != null && r.getDato().toString().equalsIgnoreCase("tan")) {
            System.out.println("Operacion: tan("
                    + r.getAnterior().getDato().toString() + ") = "
                    + Math.tan(new Double(r.getAnterior().getDato().toString())));
            r.setDato(Math.tan(new Double(r.getAnterior().getDato().toString())));
        }
        if (r.getAnterior() != null && r.getDato().toString().equalsIgnoreCase("cos")) {
            System.out.println("Operacion: cos("
                    + r.getAnterior().getDato().toString() + ") = "
                    + Math.cos(new Double(r.getAnterior().getDato().toString())));
            r.setDato(Math.cos(new Double(r.getAnterior().getDato().toString())));
        }
        System.out.println("Resultado Trigonométrica: " + r.getDato().toString());
    }

    //Estatico
    public static double realizarOperacionEstatico(Nodo r, double anterior, double siguiente) {
        double resultado = 0.0;
        if (r.getDato().toString().equals("+")) {
            resultado = anterior + siguiente;
        }
        if (r.getDato().toString().equals("-")) {
            resultado = anterior - siguiente;
        }
        if (r.getDato().toString().equals("*")) {
            resultado = anterior * siguiente;
        }
        if (r.getDato().toString().equals("/")) {
            resultado = anterior / siguiente;
        }
        if (r.getDato().toString().equals("%")) {
            resultado = anterior % siguiente;
        }
        if (r.getDato().toString().equals("^")) {
            resultado = Math.pow(anterior, siguiente);
        }
        r.setDato(resultado);
        return new Double(r.getDato().toString());
    }
    
    public static void realizarOperacion(Nodo r, double anterior, double siguiente) {
        double resultado = 0.0;
        if (r.getDato().toString().equals("+")) {
            resultado = anterior + siguiente;
        }
        if (r.getDato().toString().equals("-")) {
            resultado = anterior - siguiente;
        }
        if (r.getDato().toString().equals("*")) {
            resultado = anterior * siguiente;
        }
        if (r.getDato().toString().equals("/")) {
            resultado = anterior / siguiente;
        }
        if (r.getDato().toString().equals("%")) {
            resultado = anterior % siguiente;
        }
        if (r.getDato().toString().equals("^")) {
            resultado = Math.pow(anterior, siguiente);
        }
        System.out.println("Operacion: " + anterior + " "
                + r.getDato().toString() + " " + siguiente + " = "
                + resultado);
        r.setDato(resultado);
    }

    public static double obtenerValorSiguiente(Nodo r) {
        double siguiente;
        if (r.getSiguiente().getDato().toString()
                .matches("[a-zA-Z]")) {
            siguiente = new Double(tabla.get(
                    r.getSiguiente().getDato().toString()).toString());
        } else {
            siguiente = new Double(r.getSiguiente().getDato().toString());
        }
        return siguiente;
    }

    public static double obtenerValorAnterior(Nodo r) {
        double anterior;
        if (r.getAnterior().getDato().toString()
                .matches("[a-zA-Z]")) {
            anterior = new Double(tabla.get(
                    r.getAnterior().getDato().toString()).toString());
        } else if (r.getAnterior().getDato().toString()
                .contains("sin")) {
            String sin[] = r.getAnterior().getDato().toString().split(" ");
            anterior = Math.sin(new Double(tabla.get(sin[1]).toString()));
        } else if (r.getAnterior().getDato().toString()
                .contains("cos")) {
            String cos[] = r.getAnterior().getDato().toString().split(" ");
            anterior = Math.cos(new Double(tabla.get(cos[1]).toString()));
        } else if (r.getAnterior().getDato().toString()
                .contains("tan")) {
            String tan[] = r.getAnterior().getDato().toString().split(" ");
            anterior = Math.tan(new Double(tabla.get(tan[1]).toString()));
        } else {
            anterior = new Double(r.getAnterior().getDato().toString());
        }
        return anterior;
    }

    public static double obtenerValor(Nodo r) {
        double anterior=0.0;
        if (r.getDato().toString()
                .matches("[a-zA-Z]")) {
            anterior = new Double(tabla.get(
                    r.getDato().toString()).toString());
        } else if (r.getDato().toString()
                .contains("sin")) {
            String sin[] = r.getDato().toString().split(" ");
            anterior = Math.sin(new Double(tabla.get(sin[1]).toString()));
        } else if (r.getDato().toString()
                .contains("cos")) {
            String cos[] = r.getDato().toString().split(" ");
            anterior = Math.cos(new Double(tabla.get(cos[1]).toString()));
        } else if (r.getDato().toString()
                .contains("tan")) {
            String tan[] = r.getDato().toString().split(" ");
            anterior = Math.tan(new Double(tabla.get(tan[1]).toString()));
        } else {
            anterior = new Double(r.getDato().toString());
        }
        return anterior;
    }
    
    private static boolean esOperador(String caracter) {
        if (caracter.equalsIgnoreCase("^")
                || caracter.equalsIgnoreCase("%")
                || caracter.equalsIgnoreCase("*")
                || caracter.equalsIgnoreCase("/")
                || caracter.equalsIgnoreCase("+")
                || caracter.equalsIgnoreCase("-")) {
            return true;
        } else {
            return false;
        }
    }
}

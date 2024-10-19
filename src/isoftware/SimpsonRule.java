/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftware;

/**
 *
 * @author alfredo
 */
public class SimpsonRule {

    public static void rule(String funcion, int N, double E, int LI, int LS) {
        double W = 0.0, IA = 0, IB = 0, diferencia = 1;
        funcion = Expresiones.posfijo(funcion);
        W = (double)(LS - LI) / N;
        IA = ((W) * ((Expresiones.evaluarExpresionEstatico(funcion, "" + LI))
                + (4 * sumatoria4(funcion, LI, N)) + (2 * sumatoria2(funcion, LS, N))
                + (Expresiones.evaluarExpresionEstatico(funcion, "" + LS))) / 3);
        while (diferencia > E) {
            N = 2 * N;
            W = (double)(LS - LI) / N;
            IB = ((W) * ((Expresiones.evaluarExpresionEstatico(funcion, "" + LI))
                    + (4 * sumatoria4(funcion, LI, N)) + (2 * sumatoria2(funcion, LS, N))
                    + (Expresiones.evaluarExpresionEstatico(funcion, "" + LS))) / 3);
            diferencia = Math.abs((IA - IB));
            //if(diferencia>E)
            //    IA = IB;
            System.out.println(Math.exp(0));
            System.out.println(Math.pow(Math.E, 0));
            System.out.println("1: " + Expresiones.evaluarExpresionEstatico(funcion, "" + LI));
            System.out.println("2: " + (4 * sumatoria4(funcion, LI, N)));
            System.out.println("3: " + (2 * sumatoria2(funcion, LS, N)));
            System.out.println("4: " + (Expresiones.evaluarExpresionEstatico(funcion, "" + LS)));
            System.out.println("Resultado Diferencia: " + diferencia);
            System.out.println("Resultado IA: " + IA);
            System.out.println("Resultado IB: " + IB);
        }
        System.out.println("Resultado: " + IA);
    }

    public static double sumatoria4(String funcion, int LI, int N) {
        double resultado = 0.0;
        for (int i = 1; i < N; i = i + 2) {
            resultado += Expresiones.evaluarExpresionEstatico(funcion, "" + (i));
        }
        return resultado;
    }

    public static double sumatoria2(String funcion, int LS, int N) {
        double resultado = 0.0;
        for (int i = 2; i < N; i = i + 2) {
            resultado += Expresiones.evaluarExpresionEstatico(funcion, "" + (i));
        }
        return resultado;
    }
}

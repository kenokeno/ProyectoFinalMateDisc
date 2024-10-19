/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftware;

import java.util.Scanner;

/**
 *
 * @author alfredo
 */
public class Main {
    public static void main(String args[]){
        Scanner leer = new Scanner(System.in);
        System.out.println("Funcion a integrar: ");
        String funcion = leer.nextLine();
        System.out.println("Numero de segmentos: ");
        int N = leer.nextInt();
        System.out.println("Error de acpetación: ");
        double E = leer.nextDouble();
        System.out.println("Limite Inferior: ");
        int LI = leer.nextInt();
        System.out.println("Limite Superior: ");
        int LS = leer.nextInt();
        SimpsonRule.rule(funcion, N, E, LI, LS);
    }
}

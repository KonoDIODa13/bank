package application.ui;

import application.CRUD.Banco;
import application.domain.CuentaAhorro;
import application.domain.Persona;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Banco banco = new Banco();
    static boolean control = true;

    public static void main(String[] args) {
        pintaMenu();
    }

    static void pintaMenu() {
        System.out.println("Bienvenido al Gestión Banco");
        do {
            try {
                System.out.println("Eliga una opción\n" +
                        "1 - ABRIR CUENTA\n" +
                        "2 - LISTADO CUENTAS\n" +
                        "3 - INFORMACION CUENTA\n" +
                        "4 - INGRESO CUENTA\n" +
                        "5 - RETIRADA CUENTA\n" +
                        "6 - SALIR");

                int opcion = Integer.parseInt(scanner.next());

                switch (opcion) {
                    case 1:
                        abrirCuenta();
                        break;
                }
            } catch (Exception e) {
                System.out.println("error");
            }
        } while (control);
    }

    public static void abrirCuenta() {
        boolean bool = true;
        String nombre, apellidos, DNI, IBAN;
        double saldo, interes;
        do {
            try {
                System.out.println("Escriba el nombre del titular:");
                nombre = scanner.next();
                System.out.println("Escriba los apellidos del titular:");
                apellidos = scanner.next();
                do {
                    System.out.println("Escriba el DNI del titular:");
                    DNI = scanner.next();
                    if (!validateDNI(DNI)) {
                        System.out.println("Error al insertar DNI, atengase al formato NNNNNNNNL");
                    }
                } while (!validateDNI(DNI));

                do {
                    System.out.println("Escriba el IBAN del titular:");
                    IBAN = scanner.next();
                    if (!validateIBAN(IBAN)) {
                        System.out.println("Error al insertar IBAN, atengase al formato ESNNNNNNNNNNNNNNNNNN");
                    }
                } while (!validateIBAN(IBAN));

                System.out.println("Escriba el saldo inicial de la cuenta");
                saldo = Double.parseDouble(scanner.next());
                Persona titular = new Persona(nombre, apellidos, DNI);
                insertarTipoCuenta(titular, IBAN, saldo);

            } catch (Exception e) {
                System.out.println("error");
            }

        } while (bool);

    }

    public static boolean validateDNI(String DNI) {
        String pattern = "[0-9]{7,8}[A-Z a-z]";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(DNI);
        return matcher.matches();
    }

    public static boolean validateIBAN(String IBAN) {
        String pattern = "^ES\\\\d{20}$]";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(IBAN);
        return matcher.matches();
    }

    public static void insertarTipoCuenta(Persona titular, String IBAN, Double saldo) {
        double interes;
        boolean bool2 = true;
        do {
            try {
                System.out.println("Eliga un Tipo de Cuenta\n" +
                        "1 - CUENTA AHORRO\n" +
                        "2 - CUENTA CORRIENTE PERSONAL\n" +
                        "3 - CUENTA CORRIENTE EMPRESA\n");

                int opcionCuenta = Integer.parseInt(scanner.next());
                switch (opcionCuenta) {
                    case 1:
                        System.out.println("Escriba el interés de la cuenta");
                        interes = Double.parseDouble(scanner.next());
                        CuentaAhorro cuenta = new CuentaAhorro(titular, IBAN, saldo, interes);
                        if (banco.abrirCuenta(cuenta)) {
                            System.out.println("Usuario creado");
                        } else {
                            System.out.println("Error al insertar usuario");
                        }
                        break;
                }
            } catch (Exception e) {
                System.out.println("error");
            }
        } while (bool2);
    }
}
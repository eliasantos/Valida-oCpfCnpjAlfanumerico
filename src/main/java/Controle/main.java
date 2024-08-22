package Controle;

import java.util.Scanner;

public class main {
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CpfCnpjValidator validator = new CpfCnpjValidator();
        
        System.out.println("Você quer validar um CPF ou um CNPJ? (Digite 1 para CPF e 2 para CNPJ)");
        int escolha = sc.nextInt();
        sc.nextLine(); // Consumir a nova linha após a escolha

        if (escolha == 1) {
            System.out.println("Digite o CPF:");
            String cpf = sc.nextLine();

            try {
                if (validator.isCpf(cpf)) {
                    System.out.println("CPF é válido.");
                } else {
                    System.out.println("CPF é inválido.");
                }
                System.out.println(validator.isValid(cpf) + ": É o resultado da validação.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else if (escolha == 2) {
            System.out.println("Digite o CNPJ:");
            String cnpj = sc.nextLine();

            try {
                if (CpfCnpjValidator.isCnpj(cnpj)) {
                    System.out.println("CNPJ é válido.");
                } else {
                    System.out.println("CNPJ é inválido.");
                }
                System.out.println(validator.isValid(cnpj) + ": É o resultado da validação.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Escolha inválida.");
        }

        sc.close();
    }
}

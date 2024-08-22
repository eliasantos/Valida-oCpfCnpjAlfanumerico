package Controle;

import java.util.InputMismatchException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CpfCnpjValidator {
	
	public boolean isValid(String value) {
		return value == null || value.isEmpty() || isCpf(value) || isCnpj(value);
	}

	/**
	 * Realiza a validação do CPF.
	 * 
	 * @param cpf número de CPF a ser validado pode ser passado no formado
	 *            999.999.999-99 ou 99999999999
	 * @return true se o CPF é válido e false se não é válido
	 */
	public boolean isCpf(String cpf) {
		cpf = cpf.replaceAll("\\W", "");

		try {
			Long.parseLong(cpf);
		} catch (NumberFormatException e) {
			return false;
		}

		int d1, d2;
		int digito1, digito2, resto;
		int digitoCPF;
		String nDigResult;

		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;

		for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
			digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

			// multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4
			// e assim por diante.
			d1 = d1 + (11 - nCount) * digitoCPF;

			// para o segundo digito repita o procedimento incluindo o primeiro
			// digito calculado no passo anterior.
			d2 = d2 + (12 - nCount) * digitoCPF;
		}
		;

		// Primeiro resto da divisão por 11.
		resto = (d1 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2)
			digito1 = 0;
		else
			digito1 = 11 - resto;

		d2 += 2 * digito1;

		// Segundo resto da divisão por 11.
		resto = (d2 % 11);

		// Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11
		// menos o resultado anterior.
		if (resto < 2)
			digito2 = 0;
		else
			digito2 = 11 - resto;

		// Digito verificador do CPF que está sendo validado.
		String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

		// Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

		// comparar o digito verificador do cpf com o primeiro resto + o segundo
		// resto.
		return nDigVerific.equals(nDigResult);
	}
	
	
	/**
	 * Verifica se o CNPJ é válido.
	 *
	 * @param cnpj String contendo o CNPJ a ser validado.
	 * @return true se o CNPJ for válido, false caso contrário.
	 */
	public static boolean isCnpj(String cnpj) {
		cnpj = cnpj.replaceAll("\\W", ""); // Remove caracteres não numéricos

		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222")
				|| cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888")
				|| cnpj.equals("99999999999999") || (cnpj.length() != 14))
			return (false);

		try {
			// Cria um array para armazenar os valores numéricos do CNPJ
			int[] valores = new int[14];

			// Preenche o array com os valores numéricos extraídos da string do CNPJ
			for (int i = 0; i < cnpj.length(); i++) {
				valores[i] = Character.getNumericValue(cnpj.charAt(i));
			}
			// Chama o método de validação do CNPJ com os valores numéricos e o CNPJ
			// original
			return validaCNPJValores(valores, cnpj);
		} catch (InputMismatchException e) {
			return false;
		}
	}

	
	
	/**
	 * Valida os valores do CNPJ.
	 * 
	 * @param valores Array de inteiros representando os valores do CNPJ.
	 * @param cnpj    O CNPJ a ser validado.
	 * @return {@code true} se os valores forem válidos, caso contrário
	 *         {@code false}.
	 */
	private static boolean validaCNPJValores(int[] valores, String cnpj) {

		// Declaração e inicialização das variáveis sm1 e sm2
		int sm1 = 0, sm2 = 0;
		// Declaração e inicialização do array de pesos para o primeiro dígito
		// verificador
		int[] peso1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
		// Declaração e inicialização do array de pesos para o segundo dígito
		// verificador
		int[] peso2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

		// Loop para calcular a soma ponderada para o primeiro dígito verificador
		for (int i = 0; i < 12; i++) {
			// Verifica se o valor na posição i é inteiro
			if ((valores[i] % 1) == 0) {
				// Multiplica o valor pelo peso correspondente e soma em sm1
				sm1 += valores[i] * peso1[i];
			} else {
				// Obter referencia da letra na tabela ASCII ec onverte o valor para ajusta para
				// obter o número correspondente
				int valorASCII = (int) valores[i];
				int num = valorASCII - 48;
				// Multiplica o número ajustado pelo peso correspondente e soma em sm1
				sm1 += num * peso1[i];
			}
		}

		// Calcula o resto da divisão de sm1 por 11
		int r1 = sm1 % 11;
		// Determina o primeiro dígito verificador
		char dig13 = (r1 < 2) ? '0' : (char) ((11 - r1) + '0');

		// Loop para calcular a soma ponderada para o segundo dígito verificador
		for (int i = 0; i < 13; i++) {
			// Verifica se o valor na posição i é inteiro
			if ((valores[i] % 1) == 0) {
				// Multiplica o valor pelo peso correspondente e soma em sm2
				sm2 += valores[i] * peso2[i];
			} else {
				// Obter referencia da letra na tabela ASCII ec onverte o valor para ajusta para
				// obter o número correspondente
				int valorASCII = (int) valores[i];
				int num = valorASCII - 48;
				// Multiplica o número ajustado pelo peso correspondente e soma em sm2
				sm2 += num * peso2[i];
			}
		}

		// Calcula o resto da divisão de sm2 por 11
		int r2 = sm2 % 11;
		// Determina o segundo dígito verificador
		char dig14 = (r2 < 2) ? '0' : (char) ((11 - r2) + '0');

		// Verifica se os dígitos verificadores calculados são iguais aos dígitos do
		// CNPJ fornecido
		try {
			if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13))) {
				return true;// Retorna verdadeiro se os dígitos verificadores são iguais
			} else {
				return false;// Retorna falso se os dígitos verificadores são diferentes
			}
		} catch (InputMismatchException erro) {
			return false;// Retorna falso se ocorrer uma exceção de incompatibilidade de entrada
		}
	}
}

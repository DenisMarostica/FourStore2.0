package br.com.foursys.fourcamp.fourstore.service;

import java.util.List;

import br.com.foursys.fourcamp.fourstore.data.TransactionData;
import br.com.foursys.fourcamp.fourstore.enums.PaymentMethod;
import br.com.foursys.fourcamp.fourstore.model.Transaction;

public class TransactionService {

	ProductService productService = new ProductService();
	TransactionData transactionData = new TransactionData();
	
	public boolean sell(Transaction transaction) {
		
		Double  paymentValue = 0.0;

		// multiplica valor do produto pela quantidade desejada
		
		/* transaction.setPaymentValue(productService.getPrice(transaction.getProduct())
		 * transaction.getProduct().getQtt());*/
		 

		System.out.println("Carregando transação... \n");
		System.out.println("Informações das possibilidades de pagamento: \n");

		// Cria o Object "preco" e multiplica valor do produto pela quantidade desejada
		if (transaction.getPaymentMethod().equals(PaymentMethod.MONEY)) {
			/*transaction.setPaymentValue(productService.getPrice(transaction.getProduct())
					* transaction.getProduct().getQtt()
					- ((productService.getPrice(transaction.getProduct()) * transaction.getProduct().getQtt() * 0.10)));*/
			for (int i = 0; i < transaction.getProductList().size(); i++) {
                Double price = productService.getPrice(transaction.getProductList().get(i));
                price = price * transaction.getProductList().get(i).getQtt();
                price = price * 0.9;
                paymentValue += price;
            }
            transaction.setPaymentValue(paymentValue *0.9);
			System.out.println("Valor da transação por DINHEIRO: R$" + transaction.getPaymentValue() + ", (10% de desconto).");
			//System.out.println();
		}
		if (transaction.getPaymentMethod().equals(PaymentMethod.DEBITCARD)) {
			/*transaction.setPaymentValue(productService.getPrice(transaction.getProduct())
					* transaction.getProduct().getQtt()
					- ((productService.getPrice(transaction.getProduct()) * transaction.getProduct().getQtt() * 0.05)));*/
			for (int i = 0; i < transaction.getProductList().size(); i++) {
                Double price = productService.getPrice(transaction.getProductList().get(i));
                price = price * transaction.getProductList().get(i).getQtt();
                price = price * 0.85;
                paymentValue += price;
            }
			System.out.println("Valor da transação por DÉBITO-CARD: R$" + transaction.getPaymentValue() + ", (5% de desconto).");
			
		}
		if (transaction.getPaymentMethod().equals(PaymentMethod.CREDITCARD)) {
			transaction.setPaymentValue(productService.getPrice(transaction.getProduct())
					* transaction.getProduct().getQtt()
					+ ((productService.getPrice(transaction.getProduct()) * transaction.getProduct().getQtt() * 0.10)));
			System.out.println("Valor da transação por CRÉDITO-CARD: R$" + transaction.getPaymentValue() + ", (10% de acréscimo).");
			//System.out.println();
		}
		if (transaction.getPaymentMethod().equals(PaymentMethod.PIX)) {
			transaction.setPaymentValue(productService.getPrice(transaction.getProduct())
					* transaction.getProduct().getQtt()
					+ ((productService.getPrice(transaction.getProduct()) * transaction.getProduct().getQtt() * 0.05)));
			System.out.println("Valor da transação por PIX: R$" + transaction.getPaymentValue() + ", (5% de acréscimo).");
			//System.out.println();
		}
		
		
		
		
		System.out.println("=================================");
		System.out.println("RECIBO DE TRANSAÇÃO");
		
		// verifica se getPrice deu certo
		
		if (transaction.getPaymentValue() == 0.0) {
			return false;
			} else {
			// remove do estoque
			for (int i = 0; i < transaction.getProductList().size(); i++) {
			productService.callRemove(transaction.getProductList().get(i));
			}
			transactionData.save(transaction);
			return true;
			}



	}
	
	public String returnSellHistory() {
		String sellHistory = "Nenhuma venda no histórico ainda.";
		List<Transaction> allSales = transactionData.listAll();

		if (allSales != null) {
			sellHistory = "";
			for (Transaction sale : allSales) {
				sellHistory += sale.toString() + "\n";
			}
		}

		return sellHistory;
	}

	public String saveSell(Transaction transaction) {
		String result = "Erro, item duplicado.";

		if (transactionData.save(transaction)) {
			result = "Adicionado no banco de dados com sucesso";
		}

		return result;
	}
	

}
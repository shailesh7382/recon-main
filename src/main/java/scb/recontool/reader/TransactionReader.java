package scb.recontool.reader;

import scb.recontool.txn.Transaction;

public interface TransactionReader<T extends Transaction> {

	T parse(String line, int lineNumber);

}

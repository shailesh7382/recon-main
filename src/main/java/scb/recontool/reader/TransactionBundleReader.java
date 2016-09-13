package scb.recontool.reader;

import scb.recontool.txn.Transaction;
import scb.recontool.txn.TxnBundle;

public interface TransactionBundleReader<T extends Transaction> {
	
	public TxnBundle<T> readInput();

}

package scb.recontool.txn;

public interface ValidBuilder<T> {
	
	public ValidBuilder<T> addError(TxnError txnError) ;
	public ValidBuilder<T> addWarning(TxnWarning txnWarning);
	public T build();

}

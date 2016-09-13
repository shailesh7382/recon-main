package scb.recontool.txn;

import java.util.ArrayList;
import java.util.List;

import scb.recontool.input.InputSource;


public class TxnBundle<T extends Id> {
	private List<T> txns = new ArrayList<>();
	private List<T> errorTxns = new ArrayList<>();
	private InputSource inputSource;
	
	public TxnBundle(InputSource inputSource) {
		this.inputSource = inputSource;
	}

	public void addTransaction(T txn) {
		if(txn.isError()){
			addErrorTransaction(txn);
		}
		else{
			txns.add(txn);
		}
	}
	
	private void addErrorTransaction(T txn) {
		errorTxns.add(txn);
	}

	public List<T> getTransactions(){
		return new ArrayList<T>(txns);
	}
	
	public InputSource inputSource() {
		return inputSource;
	}

	public List<T> getErrorTransactions() {
		return new ArrayList<T>(errorTxns);
	}
	
	

}

package scb.recontool.txn;

import java.util.List;

public class Transaction implements Id{
	private String transactionId = null;
	private String accountId = null;
	private String postingDateString = null;
	private String amountString = null;
	private TxnMetaData txnMetaData;

	private Transaction(String id, String accountId2, String postingDate, String amount, TxnMetaData txnMetaData) {
		this.transactionId = id;
		this.accountId = accountId2;
		this.postingDateString = postingDate;
		this.amountString = amount;
		this.txnMetaData = txnMetaData;
	}

	@Override
	public String id() {
		return transactionId;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getPostingDate() {
		return postingDateString;
	}

	public String getAmount() {
		return amountString;
	}
	
	public Integer getSequenceNumber() {
		return txnMetaData.getSequenceNumber();
	}


// not including metadata
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((amountString == null) ? 0 : amountString.hashCode());
		result = prime * result + ((postingDateString == null) ? 0 : postingDateString.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		return result;
	}
// not including metadata
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (amountString == null) {
			if (other.amountString != null)
				return false;
		} else if (!amountString.equals(other.amountString))
			return false;
		if (postingDateString == null) {
			if (other.postingDateString != null)
				return false;
		} else if (!postingDateString.equals(other.postingDateString))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		return true;
	}

	public static TransactionBuilder getTransactionBuilder(){
		return new TransactionBuilder();
	}

	public static class TransactionBuilder {

		private String id;
		private String accountId;
		private String postingDate;
		private String amount;
		private TxnMetaData txnMetaData;

		public TransactionBuilder setTransactionId(String id) {
			this.id = id;
			return this;
		}

		public TransactionBuilder setAccountId(String accountId) {
			this.accountId = accountId;
			return this;

		}

		public TransactionBuilder setPostingDate(String postingDate) {
			this.postingDate = postingDate;
			return this;
		}

		public TransactionBuilder setTxnAmount(String amount) {
			this.amount = amount;
			return this;
		}

		public Transaction build(){
			return new Transaction(id, accountId, postingDate, amount, txnMetaData);
		}

		public TransactionBuilder setMetaData(TxnMetaData txnMetaData) {
			this.txnMetaData = txnMetaData;
			return this;
		}
	}

	@Override
	public boolean isError() {
		if(txnMetaData.getErrors().isEmpty())
			return false;
		else 
			return true;
	}

	public boolean isWarning() {
		if(txnMetaData.getWarnings().isEmpty())
			return false;
		else 
			return true;
	}
	
	public List<TxnWarning> getWarnings() {
		return txnMetaData.getWarnings();
	}
	
	public List<TxnError> getErrors() {
		return txnMetaData.getErrors();
	}
	
	
	@Override
	public String toString() {
		String string = id();
		if(isWarning()){
			string = string + "("+getWarnings()+")";
		}
		if(isError()){
			string = string +"("+getErrors()+")";
		}
		return string;
	}
	
}

package scb.recontool.txn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TxnMetaData {
	
	private Integer sequenceNumber;
	private List<TxnError>  errors;
	private List<TxnWarning>  warnings;
	
	
	private TxnMetaData(Integer sequenceNumber2, List<TxnError> errors2, List<TxnWarning> warnings2) {
		this.sequenceNumber = sequenceNumber2;
		this.errors = errors2;
		this.warnings = warnings2;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public List<TxnError> getErrors() {
		return Collections.unmodifiableList(errors);
	}

	public List<TxnWarning> getWarnings() {
		return Collections.unmodifiableList(warnings);
	}


	public static TxnMetaDataBuilder getTxnMetaDataBuilder(){
		return new TxnMetaDataBuilder();
	}

	public static class TxnMetaDataBuilder implements ValidBuilder<TxnMetaData>{
		private Integer sequenceNumber;
		private List<TxnError>  errors = new ArrayList<TxnError>();
		private List<TxnWarning>  warnings = new ArrayList<TxnWarning>();
		
		public TxnMetaDataBuilder setSequenceNumber(Integer num) {
			this.sequenceNumber = num;
			return this;
		}

		@Override
		public TxnMetaDataBuilder addError(TxnError txnError) {
			this.errors.add(txnError);
			return this;

		}

		@Override
		public TxnMetaDataBuilder addWarning(TxnWarning txnWarning) {
			this.warnings.add(txnWarning);
			return this;
		}
		
		@Override
		public TxnMetaData build(){
			return new TxnMetaData(sequenceNumber, errors, warnings);
		}
	}
}

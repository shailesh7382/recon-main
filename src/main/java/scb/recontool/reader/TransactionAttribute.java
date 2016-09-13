package scb.recontool.reader;

import scb.recontool.txn.TxnMetaData;
import scb.recontool.txn.ValidBuilder;
import scb.recontool.txn.validator.AmountValidator;
import scb.recontool.txn.validator.AttributeValidator;
import scb.recontool.txn.validator.DefaultStringValidator;
import scb.recontool.txn.validator.PostingDateValidator;

public enum TransactionAttribute {
	TXN_ID(0, new DefaultStringValidator()),
	ACCOUNT(1, new DefaultStringValidator()),
	POSTING_DATE(2, new PostingDateValidator()),
	AMOUNT(3, new AmountValidator()),
	INVALID(-1, null);
	
	private AttributeValidator<String, ValidBuilder<TxnMetaData>> attributevalidator;
	private int index;

	private TransactionAttribute(int index, AttributeValidator<String, ValidBuilder<TxnMetaData>> attributeValidator) {
		this.attributevalidator = attributeValidator;
		this.index = index;
	}
	
	public AttributeValidator<String, ValidBuilder<TxnMetaData>> getValidator() {
		return attributevalidator;
	}
	
	public TransactionAttribute getAttribute(int index) {
		for (TransactionAttribute attribute : values()) {
			if(attribute.index == index)
			{
				return attribute;
			}
		}
		return TransactionAttribute.INVALID;
	}
}

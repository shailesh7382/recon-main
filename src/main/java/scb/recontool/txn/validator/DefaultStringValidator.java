package scb.recontool.txn.validator;

import scb.recontool.txn.TxnError;
import scb.recontool.txn.TxnMetaData;
import scb.recontool.txn.ValidBuilder;
import scb.recontool.util.StringUtil;

public class DefaultStringValidator implements AttributeValidator<String, ValidBuilder<TxnMetaData>>{
	
	@Override
	public boolean validate(String attribute, ValidBuilder<TxnMetaData> builder) {
		if(StringUtil.empty(attribute)){
			builder.addError(TxnError.TXN_ATTRIBUTE_INCOMPLETE);
			return false;
		}
		return true;
	}

}

package scb.recontool.txn.validator;

import java.time.format.DateTimeParseException;

import scb.recontool.txn.TxnError;
import scb.recontool.txn.TxnMetaData;
import scb.recontool.txn.ValidBuilder;
import scb.recontool.util.DateUtil;

public class PostingDateValidator extends DefaultStringValidator{
	
	@Override
	public boolean validate(String attribute, ValidBuilder<TxnMetaData> builder) {
		boolean toReturn = super.validate(attribute, builder);
		if(toReturn){ // fail fast
			try {
				DateUtil.convertDate(attribute);
				toReturn = true;
			}
			catch (DateTimeParseException e) {
				builder.addError(TxnError.POSTING_DATE_ERROR);
				toReturn = false;
			}
			
		}
		return toReturn;
	}
}

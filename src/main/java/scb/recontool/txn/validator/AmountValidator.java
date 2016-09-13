package scb.recontool.txn.validator;

import scb.recontool.txn.TxnError;
import scb.recontool.txn.TxnMetaData;
import scb.recontool.txn.TxnWarning;
import scb.recontool.txn.ValidBuilder;
import scb.recontool.util.AmountUtil;

public class AmountValidator extends DefaultStringValidator{
	
	@Override
	public boolean validate(String attribute, ValidBuilder<TxnMetaData> builder) {
		boolean toReturn = super.validate(attribute, builder);
		if(toReturn){ // fail fast
			try {
				int scale = AmountUtil.getScale(attribute);
				if(scale > 2){
					builder.addError(TxnError.AMOUNT_MORE_THAN_2_SCALE);
					toReturn = false;
				}
				else if(scale == 2){
					
				}
				else{
					builder.addWarning(TxnWarning.AMOUNT_SCALE_INACCURATE);
				}
			} catch (NumberFormatException e) {
				builder.addError(TxnError.AMOUNT_ATTRIBUTE_INCORRECT);
				toReturn = false;
			}
		}
		return toReturn;
	}

}

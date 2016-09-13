package scb.recontool.txn.validator;

import scb.recontool.txn.ValidBuilder;

@SuppressWarnings("rawtypes")
public interface AttributeValidator<T, B extends ValidBuilder> {
	
	boolean validate(T attribute, B builder);

}

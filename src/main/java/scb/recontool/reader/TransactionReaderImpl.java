package scb.recontool.reader;

import java.util.logging.Level;
import java.util.logging.Logger;

import scb.recontool.txn.Transaction;
import scb.recontool.txn.TxnError;
import scb.recontool.txn.Transaction.TransactionBuilder;
import scb.recontool.txn.TxnMetaData.TxnMetaDataBuilder;
import scb.recontool.util.StringUtil;

public class TransactionReaderImpl implements TransactionReader<Transaction> {
	private static final Logger LOGGER = Logger.getLogger(TransactionReaderImpl.class.getName());
	private static final String DELIMITER = ";";
	private static final Integer ATTRIBUTE_COUNT = 4;

	@Override
	public Transaction parse(String line, int lineNumber) {
		TransactionBuilder builder = new TransactionBuilder();
		TxnMetaDataBuilder metaDataBuilder = new TxnMetaDataBuilder();
		metaDataBuilder.setSequenceNumber(lineNumber);
		try {
			if(StringUtil.empty(line)){
				metaDataBuilder.addError(TxnError.TXN_LINE_EMPTY);
			}
			else{
				String[] txnLine = line.split(DELIMITER);
				if(txnLine == null || txnLine.length != ATTRIBUTE_COUNT){
					metaDataBuilder.addError(TxnError.TXN_ATTRIBUTE_INCOMPLETE);
				}
				else{
					String txnId 		= txnLine[0].trim();
					String accountId 	= txnLine[1].trim();
					String postingDate 	= txnLine[2].trim();
					String amount 		= txnLine[3].trim();

					TransactionAttribute.TXN_ID.getValidator().validate(txnId, metaDataBuilder);
					TransactionAttribute.ACCOUNT.getValidator().validate(accountId, metaDataBuilder);
					TransactionAttribute.POSTING_DATE.getValidator().validate(postingDate, metaDataBuilder);
					TransactionAttribute.AMOUNT.getValidator().validate(amount, metaDataBuilder);

					builder.setTransactionId(txnId)
					.setAccountId(accountId)
					.setPostingDate(postingDate)
					.setTxnAmount(amount);

				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage()
					+ " while parsing line: " + lineNumber + "\n"
					+ e);
			metaDataBuilder.addError(TxnError.GENERIC_ERROR);
		} 
		builder.setMetaData(metaDataBuilder.build());
		return builder.build();
	}
}
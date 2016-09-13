package scb.recontool.input;

import scb.recontool.reader.FileTransactionBundleReader;
import scb.recontool.reader.TransactionBundleReader;
import scb.recontool.reader.TransactionReaderImpl;
import scb.recontool.txn.ReconTxnBundles;
import scb.recontool.txn.Transaction;
import scb.recontool.txn.TxnBundle;

public class ReconInputBundleProcessor implements InputBundleProcessor{
	
	public ReconTxnBundles process(InputBundle inputBundle) {
		validateInputBundle(inputBundle);
		ReconTxnBundles reconTxnBundles = new ReconTxnBundles(); 
		for (InputSource inputSource : inputBundle.getSources()) {
			TransactionBundleReader<Transaction> transactionBundleReader = findTransactionBundleReader(inputSource);
			TxnBundle<Transaction> bundle = transactionBundleReader.readInput();
			reconTxnBundles.add(bundle);
		}
		return reconTxnBundles;
	}

	private TransactionBundleReader<Transaction> findTransactionBundleReader(InputSource inputSource) {
		switch (inputSource.getInputType()) {
		case  FILE_NAME :
			return new FileTransactionBundleReader(inputSource, new TransactionReaderImpl());
		
		default:
			throw new IllegalArgumentException("Input type "+inputSource.getInputType() +" is not implemented yet");
		}
	}

	@Override
	public void validateInputBundle(InputBundle inputBundle) throws InputBundleException{
		if(inputBundle == null || inputBundle.getSources() == null 
				|| inputBundle.getSources().size() != 2){
			throw new InputBundleException("Input for Reconciliation is not appropriate");
		}
		
	}

}

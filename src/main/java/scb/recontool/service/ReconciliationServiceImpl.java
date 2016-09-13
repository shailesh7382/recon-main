package scb.recontool.service;

import java.util.Iterator;
import java.util.List;

import scb.recontool.service.ReconciliationReport.ReportBuilder;
import scb.recontool.txn.ReconTxnBundles;
import scb.recontool.txn.Transaction;
import scb.recontool.txn.TxnBundle;
import scb.recontool.util.TransactionUtil;

public class ReconciliationServiceImpl implements ReconciliationService {

	/* (non-Javadoc)
	 * @see scb.recontool.service.ReconciliationService#reconcile(scb.recontool.txn.ReconTxnBundles)
	 */
	@Override
	public ReconciliationReport<Transaction> reconcile(ReconTxnBundles bundles) {
		ReportBuilder<Transaction> builder = ReconciliationReport.getReportBuilder();
		
		TxnBundle<Transaction> firstBundle = bundles.getBundle(0);
		TxnBundle<Transaction> secondBundle = bundles.getBundle(1);

		List<Transaction> firstTrans = firstBundle.getTransactions();
		List<Transaction> secondTrans = secondBundle.getTransactions();
		builder.firstInputSource(firstBundle.inputSource().inputDetail());
		builder.secondInputSource(secondBundle.inputSource().inputDetail());
		tryMatch(builder,firstTrans, secondTrans);
		
		builder.addErrorLine(firstBundle.getErrorTransactions(), secondBundle.getErrorTransactions() );
		builder.addBreak(firstTrans, secondTrans);
		
		return builder.build();
	}

	

	private void tryMatch(ReportBuilder<Transaction> builder,List<Transaction> firstTrans, List<Transaction> secondTrans) {
		for (Iterator<Transaction> firstIterator = firstTrans.iterator(); firstIterator.hasNext();) {
			Transaction first = (Transaction) firstIterator.next();
			boolean isMatch = false;
			for (Iterator<Transaction> secondIterator = secondTrans.iterator(); secondIterator.hasNext();) {
				Transaction second = (Transaction) secondIterator.next();
				if (isTransactionMatching(builder, first , second) ){
					secondIterator.remove();
					isMatch = true;
					break;
				}
			}
			if(isMatch){
				firstIterator.remove();
				isMatch = false;
			}
		}
	}

	private boolean isTransactionMatching(ReportBuilder<Transaction> builder, Transaction first, Transaction second) {
		MatchType matchType = TransactionUtil.matching(first, second);
		boolean isMatching = matchType != MatchType.DIFFERENT;
		if (isMatching) {
			builder.addReconMatch(first, second, matchType);
		}
		return isMatching;
	}

}

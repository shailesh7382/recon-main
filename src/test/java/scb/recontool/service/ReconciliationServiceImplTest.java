package scb.recontool.service;

import static org.junit.Assert.*;

import org.junit.Test;

import scb.recontool.txn.ReconTxnBundles;
import scb.recontool.txn.Transaction;
import scb.recontool.txn.TxnBundle;
import scb.recontool.txn.TxnError;
import scb.recontool.txn.TxnMetaData;
import scb.recontool.txn.TxnMetaData.TxnMetaDataBuilder;
import scb.recontool.txn.TxnWarning;

public class ReconciliationServiceImplTest {

	@Test
	public void testReconcileEmpty() {
		ReconTxnBundles bundles = new ReconTxnBundles();
		TxnBundle< Transaction> bundle1 = new TxnBundle<>(null);
		TxnBundle< Transaction> bundle2 = new TxnBundle<>(null);
		bundles.add(bundle1);
		bundles.add(bundle2);
		
		ReconciliationService impl = new ReconciliationServiceImpl();
		ReconciliationReport<Transaction> report = impl.reconcile(bundles);
		
		assertNotNull(report);
		assertEquals(0, report.getExactMatchPairs().size());
		assertEquals(0, report.getFirstbreaks().size());
		assertEquals(0, report.getFirstErrorLines().size());
		assertEquals(0, report.getSecondbreaks().size());
		assertEquals(0, report.getSecondErrorLines().size());
		assertEquals(0, report.getWeakMatchPairs().size());
		assertNotNull(report.dump());

	}
	
	@Test
	public void testReconcileExact() {
		ReconTxnBundles bundles = new ReconTxnBundles();
		TxnBundle< Transaction> bundle1 = new TxnBundle<>(null);
		TxnBundle< Transaction> bundle2 = new TxnBundle<>(null);
		
		bundle1.addTransaction(getTxn("qq","16-06-2016", "q1", "121.11", 1, false, false));
		
		bundle2.addTransaction(getTxn("qq","16-06-2016", "p1", "121.11", 1, false, false));
		
		bundles.add(bundle1);
		bundles.add(bundle2);
		
		ReconciliationService impl = new ReconciliationServiceImpl();
		ReconciliationReport<Transaction> report = impl.reconcile(bundles);
		
		assertNotNull(report);
		assertEquals(1, report.getExactMatchPairs().size());
		assertEquals(0, report.getFirstbreaks().size());
		assertEquals(0, report.getFirstErrorLines().size());
		assertEquals(0, report.getSecondbreaks().size());
		assertEquals(0, report.getSecondErrorLines().size());
		assertEquals(0, report.getWeakMatchPairs().size());
		assertNotNull(report.dump());

	}
	
	@Test
	public void testReconcileWeak() {
		ReconTxnBundles bundles = new ReconTxnBundles();
		TxnBundle< Transaction> bundle1 = new TxnBundle<>(null);
		TxnBundle< Transaction> bundle2 = new TxnBundle<>(null);
		
		bundle1.addTransaction(getTxn("qq","16-06-2016", "q1", "121.11", 1, false, false));
		
		bundle2.addTransaction(getTxn("qq","16-06-2016", "p1", "121.10", 1, false, false));
		
		bundles.add(bundle1);
		bundles.add(bundle2);
		
		ReconciliationService impl = new ReconciliationServiceImpl();
		ReconciliationReport<Transaction> report = impl.reconcile(bundles);
		
		assertNotNull(report);
		assertEquals(0, report.getExactMatchPairs().size());
		assertEquals(0, report.getFirstbreaks().size());
		assertEquals(0, report.getFirstErrorLines().size());
		assertEquals(0, report.getSecondbreaks().size());
		assertEquals(0, report.getSecondErrorLines().size());
		assertEquals(1, report.getWeakMatchPairs().size());
		assertNotNull(report.dump());

	}
	
	@Test
	public void testReconcileBreak() {
		ReconTxnBundles bundles = new ReconTxnBundles();
		TxnBundle< Transaction> bundle1 = new TxnBundle<>(null);
		TxnBundle< Transaction> bundle2 = new TxnBundle<>(null);
		
		bundle1.addTransaction(getTxn("qq1","16-06-2016", "q1", "121.11", 1, false, false));
		
		bundle2.addTransaction(getTxn("qq","16-06-2016", "p1", "121.11", 1, false, false));
		
		bundles.add(bundle1);
		bundles.add(bundle2);
		
		ReconciliationService impl = new ReconciliationServiceImpl();
		ReconciliationReport<Transaction> report = impl.reconcile(bundles);
		
		assertNotNull(report);
		assertEquals(0, report.getExactMatchPairs().size());
		assertEquals(1, report.getFirstbreaks().size());
		assertEquals(0, report.getFirstErrorLines().size());
		assertEquals(1, report.getSecondbreaks().size());
		assertEquals(0, report.getSecondErrorLines().size());
		assertEquals(0, report.getWeakMatchPairs().size());
		assertNotNull(report.dump());
	}
	
	@Test
	public void testReconcileBreakError() {
		ReconTxnBundles bundles = new ReconTxnBundles();
		TxnBundle< Transaction> bundle1 = new TxnBundle<>(null);
		TxnBundle< Transaction> bundle2 = new TxnBundle<>(null);
		
		bundle1.addTransaction(getTxn("qq1","16-06-2016", "q1", "121.11", 1, true, false));
		
		bundle2.addTransaction(getTxn("qq","16-06-2016", "p1", "121.11", 1, false, false));
		
		bundles.add(bundle1);
		bundles.add(bundle2);
		
		ReconciliationService impl = new ReconciliationServiceImpl();
		ReconciliationReport<Transaction> report = impl.reconcile(bundles);
		
		assertNotNull(report);
		assertEquals(0, report.getExactMatchPairs().size());
		assertEquals(0, report.getFirstbreaks().size());
		assertEquals(1, report.getFirstErrorLines().size());
		assertEquals(1, report.getSecondbreaks().size());
		assertEquals(0, report.getSecondErrorLines().size());
		assertEquals(0, report.getWeakMatchPairs().size());
		assertNotNull(report.dump());
	}
	
	private Transaction getTxn(String acc, String date, String txnId, String amount, int seqN, boolean isError, boolean isWarn ) {
		TxnMetaDataBuilder txnMetaDataBuilder = TxnMetaData.getTxnMetaDataBuilder().setSequenceNumber(1);
		if(isError)
			txnMetaDataBuilder.addError(TxnError.GENERIC_ERROR);
		if(isWarn)
			txnMetaDataBuilder.addWarning(TxnWarning.AMOUNT_SCALE_INACCURATE);
		Transaction txn1 = Transaction.getTransactionBuilder().setAccountId(acc)
				.setPostingDate(date)
				.setTransactionId(txnId)
				.setTxnAmount(amount)
				.setMetaData(txnMetaDataBuilder.build())
				.build();
		return txn1;
	}

}

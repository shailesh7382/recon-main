package scb.recontool.input;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import scb.recontool.reader.TxnReadException;
import scb.recontool.txn.ReconTxnBundles;

public class ReconInputBundleProcessorTest {
	
	@Test
	public void testProcessXY() {
		MockInputBundle inputBundle = new MockInputBundle();

		ReconInputBundleProcessor bundleProcessor;
		ReconTxnBundles bundles = null;
		try {
			bundleProcessor = new ReconInputBundleProcessor();
			bundles = bundleProcessor.process(inputBundle);
		} catch (Exception e) {
			Assert.fail();
		}
		Assert.assertNotNull(bundles);
		Assert.assertNotNull("First bundle is not null",bundles.getBundle(0));
		Assert.assertNotNull("Second bundle is not null",bundles.getBundle(1));
		Assert.assertEquals("check first x0 txn is not null", "x0",bundles.getBundle(0).getTransactions().get(0).id());
		Assert.assertEquals("check txn bundle size",8, bundles.getBundle(0).getTransactions().size());
		Assert.assertEquals("check txn bundle size",8, bundles.getBundle(1).getTransactions().size());
		
	}
	

	@Test
	public void testProcessNonExisting() {
		InputSource inputSource = new InputSource() {

			@Override
			public String inputDetail() {
				return "abc";
			}

			@Override
			public InputType getInputType() {
				return InputType.FILE_NAME;
			}
		};

		InputSource inputSource2 = new InputSource() {

			@Override
			public String inputDetail() {
				return "";
			}

			@Override
			public InputType getInputType() {
				return InputType.FILE_NAME;
			}
		};

		InputBundle inputBundle = new InputBundle() {

			@Override
			public List<InputSource> getSources() {
				return Arrays.asList(inputSource, inputSource2);
			}
		};

		ReconInputBundleProcessor bundleProcessor;
		ReconTxnBundles bundles = null;
		try {
			bundleProcessor = new ReconInputBundleProcessor();
			bundles = bundleProcessor.process(inputBundle);
		} catch (Exception e) {
			Assert.assertTrue("Input in not existing", e instanceof TxnReadException);
		}
		Assert.assertNull(bundles);
	}

	@Test
	public void testProcessDifferentSource() {
		InputSource inputSource = new InputSource() {

			@Override
			public String inputDetail() {
				return "x.txt";
			}

			@Override
			public InputType getInputType() {
				return InputType.FILE_NAME;
			}
		};

		InputBundle inputBundle = new InputBundle() {

			@Override
			public List<InputSource> getSources() {
				return Arrays.asList(inputSource, inputSource, inputSource);
			}
		};

		ReconInputBundleProcessor bundleProcessor;
		ReconTxnBundles bundles = null;
		try {
			bundleProcessor = new ReconInputBundleProcessor();
			bundles = bundleProcessor.process(inputBundle);
		} catch (Exception e) {
			Assert.assertTrue("Input in not correct", e instanceof InputBundleException);
		}
		Assert.assertNull(bundles);
	}
	
	
	

}

package scb.recontool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import scb.recontool.txn.Transaction;

public class ReconciliationReport<T extends Transaction> {
	private List<TxnPair<T>> exactMatchPairs = new ArrayList<>();
	private List<TxnPair<T>> weakMatchPairs = new ArrayList<>();
	private List<T> firstbreaks = new ArrayList<>();
	private List<T> secondbreaks = new ArrayList<>();

	private List<String> firstErrorLines = new ArrayList<>();
	private List<String> secondErrorLines = new ArrayList<>();
	private String firstInputSource;
	private String secondInputSource;
	
	
	private ReconciliationReport(List<TxnPair<T>> exactMatchPairs2, List<TxnPair<T>> weakMatchPairs2,
			List<T> firstbreaks2, List<T> secondbreaks2, List<String> firstErrorLines2,
			List<String> secondErrorLines2, String firstInputSource, String secondInputSource) {
		this.exactMatchPairs = exactMatchPairs2;
		this.weakMatchPairs = weakMatchPairs2;
		this.firstbreaks = firstbreaks2;
		this.secondbreaks = secondbreaks2;
		this.firstErrorLines = firstErrorLines2;
		this.secondErrorLines = secondErrorLines2;
		this.firstInputSource = firstInputSource;
		this.secondInputSource = secondInputSource;
	}
	
	public List<TxnPair<T>> getExactMatchPairs() {
		return new ArrayList<>(exactMatchPairs);
	}
	
	public List<TxnPair<T>> getWeakMatchPairs() {
		return new ArrayList<>(weakMatchPairs);
	}

	public List<T> getFirstbreaks() {
		return new ArrayList<>(firstbreaks);
	}
	
	public List<T> getSecondbreaks() {
		return new ArrayList<>(secondbreaks);
	}
	
	public List<String> getFirstErrorLines() {
		return new ArrayList<>(firstErrorLines);
	}
	
	public List<String> getSecondErrorLines() {
		return new ArrayList<>(secondErrorLines);
	}
	
	public String getFirstInputSource() {
		return firstInputSource;
	}
	
	public String getSecondInputSource() {
		return secondInputSource;
	}
	
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ReportBuilder<Transaction> getReportBuilder(){
		return new ReportBuilder();
	}

	public static class ReportBuilder<T extends Transaction> {

		private List<TxnPair<T>> exactMatchPairs = new ArrayList<>();
		private List<TxnPair<T>> weakMatchPairs = new ArrayList<>();
		private List<T> firstbreaks = new ArrayList<>();
		private List<T> secondbreaks = new ArrayList<>();

		private List<String> firstErrorLines = new ArrayList<>();
		private List<String> secondErrorLines = new ArrayList<>();
		private String firstInputSource;
		private String secondInputSource;

		

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ReconciliationReport<Transaction> build(){
			return new ReconciliationReport(exactMatchPairs, 
					weakMatchPairs,
					firstbreaks,
					secondbreaks,
					firstErrorLines,
					secondErrorLines, 
					firstInputSource,
					secondInputSource);
		}
		
		public ReportBuilder<T> addReconMatch(T first, T second, MatchType matchType) {
			switch (matchType) {
			case EXACT_MATCH:
				exactMatchPairs.add(new TxnPair<T>(first, second));
				break;
			case WEAK_MATCH:
				weakMatchPairs.add(new TxnPair<T>(first, second));
				break;
			default:

				break;
			}
			return this;
		}

		public ReportBuilder<T> addErrorLine(List<T> errorTransactions, List<T> errorTransactions2) {
			for (T t : errorTransactions) {
				firstErrorLines.add(t.getSequenceNumber() + "(" + t.getErrors() + ")");
			}

			for (T t : errorTransactions2) {
				secondErrorLines.add(t.getSequenceNumber() + "(" + t.getErrors() + ")");
			}
			return this;
		}

		public ReportBuilder<T> addBreak(List<T> firstTrans, List<T> secondTrans) {
			firstbreaks.addAll(firstTrans);
			secondbreaks.addAll(secondTrans);
			return this;
		}

		public void firstInputSource(String firstInputSource) {
			this.firstInputSource = firstInputSource;
		}

		public void secondInputSource(String inputDetail) {
			this.secondInputSource = inputDetail;
		}
	}

	public String dump() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n===========Report Starts("+firstInputSource+"/"+secondInputSource+")===========\n");
		builder.append("\n");
		builder.append(firstInputSource+"/"+secondInputSource+" Exact Matches \n");
		StringJoiner joiner = new StringJoiner(", ");
		for (TxnPair<T> t : exactMatchPairs) {
			joiner.add(t.toString());
		}
		builder.append(joiner.toString());
		builder.append("\n");
		builder.append("\n");
		builder.append(firstInputSource+"/"+secondInputSource+" Weak Matches \n");
		joiner = new StringJoiner(", ");
		for (TxnPair<T> t : weakMatchPairs) {
			joiner.add(t.toString());
		}
		builder.append(joiner.toString());

		builder.append("\n");
		builder.append("\n");
		builder.append(firstInputSource+" Breaks \n");
		joiner = new StringJoiner(", ");
		for (T txn : firstbreaks) {
			joiner.add(txn.toString());
		}
		builder.append(joiner.toString());
		builder.append("\n");
		builder.append("\n");
		builder.append(secondInputSource+ " Breaks \n");
		joiner = new StringJoiner(", ");
		for (T txn : secondbreaks) {
			joiner.add(txn.toString());
		}
		builder.append(joiner.toString());

		builder.append("\n");
		if (!firstErrorLines.isEmpty()) {
			builder.append(firstInputSource+" <LineNumber> Errors\n");
			joiner = new StringJoiner(", ");
			for (String txn : firstErrorLines) {
				joiner.add(txn.toString());
			}
			builder.append(joiner.toString());
		}

		if (!secondErrorLines.isEmpty()) {
			builder.append("\n");
			builder.append("\n");
			builder.append(secondInputSource+" <LineNumber> Errors\n");
			joiner = new StringJoiner(", ");
			for (String txn : secondErrorLines) {
				joiner.add(txn.toString());
			}
			builder.append(joiner.toString());
			
		}
		builder.append("\n");
		builder.append("\n=============Report Ends("+firstInputSource+"/"+secondInputSource+")===========\n");
		System.out.println(builder.toString());
		return builder.toString();
		
	}

	public static class TxnPair<T> {
		private T first;
		private T second;

		public TxnPair(T first, T second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public String toString() {
			return first.toString() + second.toString();
		}
	}

}

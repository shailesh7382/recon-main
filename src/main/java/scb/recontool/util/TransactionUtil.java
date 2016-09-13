package scb.recontool.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import scb.recontool.service.MatchType;
import scb.recontool.txn.Transaction;

public class TransactionUtil {
	

	public static MatchType matching(Transaction first, Transaction second) {
		if(first.isError() || second.isError()){
			return MatchType.DIFFERENT;
		}
		else if(!first.getAccountId().equals(second.getAccountId())){
			return MatchType.DIFFERENT;
		}
		else if(first.getPostingDate().equals(second.getPostingDate()) 
				&& first.getAmount().equals(second.getAmount())){
			return MatchType.EXACT_MATCH;
		}
		else{
			MatchType postingDateMatch = getPostingDateMatch(first, second);
			MatchType amountMatch = getAmountMatch(first, second);
			if(amountMatch.equals(MatchType.DIFFERENT) || postingDateMatch.equals(MatchType.DIFFERENT)){
				return MatchType.DIFFERENT;
			}
			else {
				return MatchType.WEAK_MATCH;
			}
		}
	}
	
	
	private static MatchType getPostingDateMatch(Transaction first, Transaction second) {
		if(first.getPostingDate().equals(second.getPostingDate())){
			return MatchType.EXACT_MATCH;
		}
		else if(DateUtil.isWithinADay(first.getPostingDate(), second.getPostingDate())){
			return MatchType.WEAK_MATCH;
		}
		return MatchType.DIFFERENT;
	}


	private static MatchType getAmountMatch(Transaction first, Transaction second) {
		if(first.getAmount().equals(second.getAmount())){
			return MatchType.EXACT_MATCH;
		}
		else{
			BigDecimal firstBD = AmountUtil.resetScale(first.getAmount());
			BigDecimal secondBD = AmountUtil.resetScale(second.getAmount());
			BigDecimal diff = firstBD.subtract(secondBD);
			if(diff.abs().doubleValue() > 0.01){
				return MatchType.DIFFERENT;
			}
			else{
				return MatchType.WEAK_MATCH;
			}
		}

	}



}

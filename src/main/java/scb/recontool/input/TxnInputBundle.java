package scb.recontool.input;

import java.util.ArrayList;
import java.util.List;

public class TxnInputBundle implements InputBundle{
	List<InputSource> sources = new ArrayList<>();
	
	private TxnInputBundle() {}
	
	@Override
	public List<InputSource> getSources() {
		return sources;
	}
	
	public static TxnInputBundle get(){
		return new TxnInputBundle();
	}
	
	public TxnInputBundle add(InputSource inputSource) {
		sources.add(inputSource);
		return this;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sources == null) ? 0 : sources.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TxnInputBundle other = (TxnInputBundle) obj;
		if (sources == null) {
			if (other.sources != null)
				return false;
		} else if (!sources.equals(other.sources))
			return false;
		return true;
	}

	
}

package Client.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Accounts")
public class AccountsWrapper {
	private List<Account> accounts;
	
	public List<Account> getAcounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts=accounts;
	}
}

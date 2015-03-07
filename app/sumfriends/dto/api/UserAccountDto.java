package sumfriends.dto.api;

import sumfriends.models.UserAccount;

public class UserAccountDto extends AbstractWrappedApiDto {
    public long id;
    public long summonerId;
    public String email;

	public UserAccountDto() {
	}

	public UserAccountDto(UserAccount account) {
	    this.id = account.id;
	    this.summonerId = account.summonerId;
        this.email = account.email;
	}

    @Override
    protected String _getWrapperName() {
        return "account";
    }
}

package sumfriends.dto.api;

public class ErrorDto extends EmberDataSingleElementDto {
	public String msg;
	public int status;

	public ErrorDto() {
		super();
	}

	public ErrorDto(String msg, int status) {
		super();
		this.msg = msg;
		this.status = status;
	}

	@Override
	protected String _getWrapperName() {
		return "error";
	}

}

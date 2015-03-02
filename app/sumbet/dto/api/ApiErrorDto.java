package sumbet.dto.api;

public class ApiErrorDto {
	public ApiErrorInner error;
	
	public ApiErrorDto(ApiErrorInner error) {
		super();
		this.error = error;
	}

	public ApiErrorDto(String msg, int status) {
		this.error = new ApiErrorInner(msg, status);
	}
	
	public static class ApiErrorInner {
		public String msg;
		public int status;
		public ApiErrorInner(String msg, int status) {
			this.msg = msg;
			this.status = status;
		}
	}
}

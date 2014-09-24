package spring.practice.di;

public class HiWorldMessageProvider implements MessageProvider {

	@Override
	public String getMessage() {
		return "Hi World";
	}

}

package spring.practice.di;

public class HelloWorldMessageProvider implements MessageProvider {

	@Override
	public String getMessage() {
		return "HelloWorld";
	}

}

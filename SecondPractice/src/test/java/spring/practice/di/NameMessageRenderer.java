package spring.practice.di;

public class NameMessageRenderer implements MessageRenderer {

	MessageProvider messageProvider;
	String name;
	
	public NameMessageRenderer(String name) {
		this.name = name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void print() {
		System.out.println(name + messageProvider.getMessage());
	}

	@Override
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}

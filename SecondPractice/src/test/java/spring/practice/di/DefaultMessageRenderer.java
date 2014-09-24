package spring.practice.di;

public class DefaultMessageRenderer implements MessageRenderer {

	MessageProvider messageProvider;
	
	@Override
	public void print() {
		System.out.println(this.messageProvider.getMessage());
	}

	@Override
	public void setMessageProvider(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}
}

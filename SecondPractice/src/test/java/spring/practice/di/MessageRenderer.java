package spring.practice.di;

public interface MessageRenderer {
	public void print();
	public void setMessageProvider(MessageProvider messageProvider);
}

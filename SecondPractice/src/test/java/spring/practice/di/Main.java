package spring.practice.di;

public class Main {
	public static void main(String[] args) {
		MessageProvider messageProvider;
		MessageRenderer messageRenderer;
		
		//messageProvider = new HiWorldMessageProvider();
		messageProvider = new HelloWorldMessageProvider();
		
		messageRenderer = new DefaultMessageRenderer();
		messageRenderer = new NameMessageRenderer("Yoonsung");
		
		messageRenderer.setMessageProvider(messageProvider);
		messageRenderer.print();
	}
}

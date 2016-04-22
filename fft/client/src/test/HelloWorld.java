package test;
import javax.jws.WebService;

@WebService(targetNamespace="test")
public interface HelloWorld {
    String sayHi(String text);
}
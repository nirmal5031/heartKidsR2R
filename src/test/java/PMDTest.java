//PMD Eclipse Tutorial
public class PMDTest {
  public static void main(String args[]) {
    PMDTest.callMethod("hello");
    PMDTest.CallHello();
  }

  public static void callMethod(String INPUT_PARAMETER) {
    System.out.println(INPUT_PARAMETER);
  }

  public static void CallHello() {
    System.out.println("Hello PMD World!");
  }
}
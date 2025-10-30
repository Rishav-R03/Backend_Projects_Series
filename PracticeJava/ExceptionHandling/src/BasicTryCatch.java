import java.util.Scanner;

void checkAge(int age){
    if(age < 18){
        System.out.println("Age is below 18");
        throw new ArithmeticException("Access Denied: Age is below authorized age.");
    }else{
        System.out.println("Success");
    }
}
void main() {
    Scanner sc = new Scanner(System.in);
    int age = sc.nextInt();
    try{
        checkAge(age);
    } catch (ArithmeticException e) {
        System.out.println("Caught exception: "+e.getMessage());
    }finally {
        System.out.println("Thank you coming");
    }
}

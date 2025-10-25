class ComplexNumber{
    double real;
    double imaginary;

    public ComplexNumber(real,imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }
    /**
     * Java's alternative to operator overloading.
     * This method is named 'add' and is used to perform custom addition 
     * logic for two ComplexNumber objects.
     * * @param other The ComplexNumber to add to the current object.
     * @return A new ComplexNumber representing the sum.
     */
    public ComplexNumber add(ComplexNumber other){
        double newReal = this.real + other.real;
        double newImaginary = this.imaginary + other.imaginary;
        return new ComplexNumber(newReal,newImaginary);
    }

    // --- Method Overriding Example ---
    /**
     * Method Overriding the standard Object.toString() method 
     * to provide a user-friendly representation of the object.
     * * The @Override annotation is used to ensure the method signature is correct.
     */
    @Override 
    public String toString() {
        if (imaginary == 0) return String.valueOf(real);
        if (real == 0) return imaginary + "i";
        if (imaginary < 0) return real + " - " + (-imaginary) + "i";
        return real + " + " + imaginary + "i";
    }
}

public class OperatorOverloadOverride {
    public static void main(String[] args) {
        System.out.println("--- Java's Alternative to Operator Overloading ---");
        
        // Create two complex number objects
        ComplexNumber num1 = new ComplexNumber(3.0, 4.0);
        ComplexNumber num2 = new ComplexNumber(1.0, 2.5);
        
        System.out.println("Number 1 (a): " + num1); // Uses overridden toString()
        System.out.println("Number 2 (b): " + num2); // Uses overridden toString()
        
        // Use the custom 'add' method to perform addition
        // This is the functional equivalent of operator overloading in Java.
        ComplexNumber sum = num1.add(num2);
        
        System.out.println("\nResult of a.add(b):");
        System.out.println("Sum: " + sum); // Output: 4.0 + 6.5i
        
        System.out.println("\n--- Method Overriding Confirmation ---");
        // The above output was formatted correctly because we OVERRODE the toString() method.
        System.out.println("num1.toString() result: " + num1.toString());
    }
}

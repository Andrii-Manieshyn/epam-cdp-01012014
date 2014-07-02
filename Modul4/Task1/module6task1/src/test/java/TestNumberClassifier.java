import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;

/**
 * @author Andrii_Manieshyn
 */
@RunWith(JUnitParamsRunner.class)
public class TestNumberClassifier {


    private NumberClassifier numberClassifier = new NumberClassifierImpl();

    private Object[] primeNumbersTrue(){
        return $(1,2,3,5,7,11);
    }


    private Object[] primeNumbersFalse(){
        return $(4,6,8,9,10,12);
    }


    @Test
    @Parameters(method = "primeNumbersTrue")
    public void testPrimeNumber_whenPrimeNumberEntered_shouldReturnTrue(int number){
        Assert.assertTrue(numberClassifier.isPrime(number));
    }



    @Test
    @Parameters(method = "primeNumbersFalse")
    public void testPrimeNumber_whenNotPrimeNumberEntered_shouldReturnFalse(int number){
        Assert.assertFalse(numberClassifier.isPrime(number));
    }



    private Object[] perfectNumbersTrue(){
        return $(6,28,496,8128, 33550336);
    }


    private Object[] perfectNumbersFalse(){
        return $(4,5,8,9,10,12);
    }


    @Test
    @Parameters(method = "perfectNumbersTrue")
    public void testPerfectNumber_whenPerfectNumberEntered_shouldReturnTrue(int number){
        Assert.assertTrue(numberClassifier.isPerfect(number));
    }



    @Test
    @Parameters(method = "perfectNumbersFalse")
    public void testPerfectNumber_whenNotPerfectNumberEntered_shouldReturnFalse(int number){
        Assert.assertFalse(numberClassifier.isPerfect(number));
    }


    private Object[] abundantNumbersTrue(){
        return $(12,18,20,24, 30, 36, 40, 42, 48);
    }

    private Object[] abundantNumbersFalse(){
        return $(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    @Parameters(method = "abundantNumbersTrue")
    public void testAbundanttNumber_whenAbundantNumberEntered_shouldReturnTrue(int number){
        Assert.assertTrue(numberClassifier.isAbundant(number));
    }

    @Test
    @Parameters(method = "abundantNumbersFalse")
    public void testAbundanttNumber_whenNotAbundantNumberEntered_shouldReturnFalse(int number){
        Assert.assertFalse(numberClassifier.isAbundant(number));
    }


    private Object[] deficientNumbersTrue(){
        return $(1,2,3,4, 5, 7, 8, 9, 10, 11, 13, 14,15, 16,17,19);
    }

    private Object[] deficientNumbersFalse(){
        return $(6,12,18,20,24,28, 30, 36, 40, 42, 48);
    }

    @Test
    @Parameters(method = "deficientNumbersTrue")
    public void testAbundanttNumber_whenDeficientNumberEntered_shouldReturnTrue(int number){
        Assert.assertTrue(numberClassifier.isDeficient(number));
    }

    @Test
    @Parameters(method = "deficientNumbersFalse")
    public void testAbundanttNumber_whenNotDeficientNumberEntered_shouldReturnFalse(int number){
        Assert.assertFalse(numberClassifier.isDeficient(number));
    }
}

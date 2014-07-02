/**
 * Created by Paladin on 04.06.2014.
 */
public class NumberClassifierImpl implements NumberClassifier {
    @Override
    public boolean isPrime(int number) {
        for (int i = 2 ; i <= number/2; i++){
            if (number % i == 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isPerfect(int number) {
        return (findDivisorsSumm(number) == number);
    }

    @Override
    public boolean isAbundant(int number) {
        return (findDivisorsSumm(number) > number);
    }

    @Override
    public boolean isDeficient(int number) {
        return (findDivisorsSumm(number) < number);
    }

    private int findDivisorsSumm( int number){
        int result = 0;
        for (int i = 1 ; i <= number/2; i++){
            if (number % i == 0){
                result +=i;
            }
        }
        return result;
    }
}

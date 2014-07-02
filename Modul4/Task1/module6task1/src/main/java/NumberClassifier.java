
public interface NumberClassifier {
    /**
     * Check number whether it is prime or not. (http://en.wikipedia.org/wiki/Prime_number)
     *
     * @param number - number to be checked
     * @return true if the number is prime, otherwise - false
     */
    boolean isPrime(int number);

    /**
     * Check number whether it is perfect or not. (http://en.wikipedia.org/wiki/Perfect_number)
     *
     * @param number - number to be checked
     * @return true if the number is perfect, otherwise - false
     */
    boolean isPerfect(int number);

    /**
     * Check number whether it is abundant or not. (http://en.wikipedia.org/wiki/Abundant_number)
     *
     * @param number - number to be checked
     * @return true if the number is abundant, otherwise - false
     */
    boolean isAbundant(int number);

    /**
     * Check number whether it is deficient or not. (http://en.wikipedia.org/wiki/Deficient_number)
     *
     * @param number - number to be checked
     * @return true if the number is deficient, otherwise - false
     */
    boolean isDeficient(int number);
}
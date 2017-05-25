package examples.functional;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public class ExampleApp {
    public String getFullName(final String firstName, final String lastName) {
        return Stream.of(firstName, lastName)
                .filter(Objects::nonNull)
                .filter(s -> s.length() > 0)
                .map(String::trim)
                .collect(Collectors.joining(", "));
    }

    public boolean palindrome(String inputString) {
        return !StringUtils.isEmpty(inputString) &&
                IntStream.range(0, inputString.length() / 2).boxed()
                        .allMatch(i -> inputString.charAt(i) == inputString.charAt(inputString.length() - i - 1));
    }

    public boolean palindrome(final Integer inputValue) {
        return Stream.of(inputValue).filter(Objects::nonNull).noneMatch(n -> n < 0)
                && (inputValue.equals(0)
                || Stream.iterate(inputValue, n -> n / 10)
                .limit(1 + (int) Math.log10(inputValue))
                .map(n -> n % 10)
                .reduce((s, n) -> s * 10 + n).filter(n -> n.equals(inputValue)).isPresent());
    }

    public BigInteger fibonacci(final Integer range) {
        if (Optional.ofNullable(range).filter(r -> r <= 0).isPresent()) {
            return null;
        }
        return Stream.iterate(new BigInteger[]{BigInteger.ZERO, BigInteger.ONE},
                f -> new BigInteger[]{f[1], f[0].add(f[1])})
                .limit(range).map(f -> f[0])
                .reduce((a, b) -> b).orElse(BigInteger.ZERO);
    }

    public static Map<Integer, Long> findDuplicateNumberInList(List<Integer> list){
        return Optional.ofNullable(list).orElse(emptyList())
                .stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream().filter(entrySet -> entrySet.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Integer findSumOfNPrimeNumbers(int range){
        if (Optional.ofNullable(range).filter(r -> r <= 0).isPresent()) {
            return null;
        }
        final Set<Integer> primeNumberTestDivisors = new HashSet<>();
        Stream.of(2,3,5,7).forEach(primeNumberTestDivisors::add);
        final Function<Integer, Boolean> notPrimeFunction = n -> primeNumberTestDivisors.stream()
                                                                                .anyMatch(pd -> n%pd==0);
        return IntStream.rangeClosed(2, range+1).boxed().
                filter(n -> primeNumberTestDivisors.contains(n) || !(notPrimeFunction.apply(n)))
                .collect(Collectors.toList())
                    .stream().reduce(Integer::sum).get();
    }

    static class MyNode<T> {
        private T value;
        private MyNode<T> leftNode;
        private MyNode<T> rightNode;

        public MyNode(T value){
            this.value = value;
            this.leftNode = null;
            this.rightNode = null;
        }

        public MyNode(T value, MyNode<T> leftNode, MyNode<T> rightNode){
            this.value = value;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }

        public T getValue() {
            return value;
        }

        public Stream<MyNode<T>> computeStream(){
            return Stream.concat(Stream.of(this), Stream.of(leftNode, rightNode).filter(Objects::nonNull).flatMap(MyNode::computeStream));
        }
    }

    private static final MyNode<Integer> zeroNode = new MyNode<>(0);

    public static Integer findNodeSum(MyNode<Integer> node){
        return Optional.ofNullable(node).orElse(zeroNode).computeStream().mapToInt(MyNode::getValue).reduce(Integer::sum).getAsInt();
    }
}

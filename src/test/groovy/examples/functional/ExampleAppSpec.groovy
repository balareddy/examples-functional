package examples.functional

import spock.lang.Specification
import spock.lang.Unroll
import examples.functional.ExampleApp.MyNode;

class ExampleAppSpec extends Specification{

    def application

    void setup() {
        application = new ExampleApp()
    }

    @Unroll
    void 'test the fullName formatting'() {

        expect: 'the full Name should be first Name, Last Name'
        application.getFullName(firstName, lastName) == fullName

        where: 'firstName and lastName are as below'
        firstName   | lastName    | fullName
        'Charles'   | 'Dickens'   | 'Charles, Dickens'
        ' Charles ' | ' Dickens ' | 'Charles, Dickens'
        'Charles'   | null        | 'Charles'
        null        | 'Dickens'   | 'Dickens'
        null        | null        | ''
        ''          | ''          | ''
    }

    @Unroll
    void 'check the Palindrome for a string'() {

        expect:'check the palindrome for the string'
        application.palindrome(input) == result

        where: 'string inputs are as below'
        input           | result
        'abba'          | true
        'abcdcba'       | true
        'abcd12321dcba' | true
        'abcd'          | false
        'abcd124'       | false
        ''              | false
        null            | false
    }

    @Unroll
    void 'check the Palindrome for a integer'() {

        expect:'check the palindrome for the integer'
        application.palindrome(input) == result

        where: 'integer inputs are as below'
        input           | result
        -1221           | false
        0               | true
        1221            | true
        1234321         | true
        1234            | false
        1234324         | false
        null            | false
    }


    @Unroll
    void 'test the fibonaci series generation'() {

        expect:'check the fibonaci series for a given range'
        application.fibonacci(range) == result

        where: 'input range are as below'
        range  | result
        -1     | null
        0      | null
        1      | BigInteger.ZERO
        2      | BigInteger.ONE
        3      | BigInteger.ONE
        4      | new BigInteger('2')
        5      | new BigInteger('3')
        6      | new BigInteger('5')

    }

    @Unroll
    void 'test the finding of duplicate numbers in a list'() {

        expect:'find duplicate numbers in the list'
        application.findDuplicateNumberInList(list) == result

        where: 'list input is as below'
        list                    | result
        [1, 2, 3, 4, 5, 2, 1]   | [1:2, 2:2]
        [1, 2, 3, 4]            | [:]
        []                      | [:]
        null                    | [:]

    }

    @Unroll
    void 'test the finding of sum of first N prime numbers'() {

        expect:'find the sum of first N prime numbers'
        application.findSumOfNPrimeNumbers(range) == result

        where: 'list input is as below'
        range                   | result
        1                       | 2
        2                       | 5
        3                       | 5
        100                     | 1161
        0                       | null
        -1                      | null

    }

    @Unroll
    void 'test to find the total sum of tree nodes'() {

        expect: 'find the total sum of tree nodes'
        application.findNodeSum(node) == result

        where: 'tree nodes are as follows'
        node                                                          | result
        new MyNode(20, new MyNode(10), new MyNode(40))                | 70
        new MyNode(20, null, new MyNode(40))                          | 60
        new MyNode(20, new MyNode(10), null)                          | 30
        new MyNode(20, new MyNode(10, new MyNode(5), new MyNode(15)),
                new MyNode(40, new MyNode(30), new MyNode(55)))       | 175
        null                                                          | 0

    }

}

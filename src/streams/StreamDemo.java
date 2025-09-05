package streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args){
        // stream is a feature in java 8, with the help of which we can process collection in a functional and declarative manner

        // simplify Data Processing
        // Embrace Functional Programming
        // Improve Readability and Maintainability
        // Enable Easy Parallelism

        // what is stream ?
        // a sequence of elements supporting various operations

        // How to use streams ?
        // source, intermediate operations & terminal operation


        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        // if we do map we don't eliminate anything we just modify even element, but filter does take what we want
        long y = numbers.stream().filter(x -> x % 2 == 0).count();
        System.out.println(y);

        // now  line by line

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> integerStream = list.stream();

        // List is easy to fetch how about array

        String [] arr = {"a", "b", "c", "d"};

        Stream<String> s1 = Arrays.stream(arr);
        System.out.println(Arrays.stream(list.toArray()).count());
        System.out.println(Arrays.stream(list.toArray()).collect(Collectors.toList()));


        Stream<Integer> s3= Stream.of(1, 2, 3, 4);

        // Infinite Stream
        System.out.println("Infifnite Stream");
      //  Stream.generate(() -> 1).collect(Collectors.toList()); // stores a lsit of infinite 1's
        Stream.generate(() -> 1).limit(10); // a stream of 10 1's
        // convert to list
        Stream.generate(() -> 1).limit(10).collect(Collectors.toList());


        System.out.println("Iterator");
        // takes a unary operator, like a Function takes x of and return a object of same type
      //  List<Integer> l1 = Stream.iterate(1, x -> x).collect(Collectors.toList());
        // this seed is a starting point i.e we start from 1 and increase i.e 1, 2, 3, 4... infinity

        // now modify when counting

        List<Integer> l2 = Stream.iterate(0, x -> x + 10)
                .limit(10) // not more than 10 iteration
                .filter(x -> x % 2 == 0)
                .collect(Collectors.toList());

          // Its a builder pattern so limit can go either way change its postion in intermidiate, but not in terminal or source

        // OR

/*        List<Integer> l2 = Stream.iterate(0, x -> x + 10)
                .filter(x -> x % 2 == 0)
                .limit(10) // not more than 10 iteration
                .collect(Collectors.toList());*/



        System.out.println(l2);

        List<String> names1 = Arrays.asList("sdgsdget", "etdgsdg", "gdfdgett", "srdsr", "ets");

        List<MobilePhone> myphones = names1.stream()
                .map(x -> new MobilePhone(x))
                .filter(x -> x.getName().contains("et"))
                .map(x -> new MobilePhone(x.getName().replace("et", "bt")))
                .collect(Collectors.toList());

        System.out.println(myphones.stream().map(x -> x.getName()).collect(Collectors.toList()));


        List<String> myp2 = names1.stream()
                .map(x -> new MobilePhone(x))
                .filter(x -> x.getName().contains("et"))
                .map(x -> x.getName().replace("et", "bt"))
                .collect(Collectors.toList());


        System.out.println(myp2);


       // sorted is also an intermediate operation

        List<Integer> l4 = list.stream()
                .sorted().toList();

        List<String> l3 = list.stream()
                .map(x -> x.toString())
                .sorted()
                .collect(Collectors.toList());


        List<String> l5 = list.stream()
                .map(x -> x.toString())
                .sorted((a, b) -> {
                    int i = a.length() - b.length();
                    return i;             // OR (a, b) -> a.length - b.length
                })
                .collect(Collectors.toList());


        List<String> l6 = list.stream()
                .map(x -> x.toString())
                .sorted((a, b) -> {
                    return  a.compareTo(b); // i.e a < b if b.compareTo(a) then a > b (greater) and so on
                })
                .collect(Collectors.toList());


        // distinct() -> intermediate

        List<String> l7 = list.stream()
                .filter(x -> x % 2 == 0)
                .map(x -> x.toString())
                .distinct()
                .collect(Collectors.toList());

        System.out.println(l7);

        // here limit is set to 1 so when going through the list we come across 1st element which is 1. and limit is also 1,
        // therefore after than list isn't scanned. and to filter only 1 goes which isn;t filtered so nothing goes to map , nothing to distinct and then to list
        List<String> l8 = list.stream()
                .limit(1)
                .filter(x -> x % 2 == 0)
                .map(x -> x.toString())

                .distinct()
                .collect(Collectors.toList());

        System.out.println(l8);


        // skip

        List<Integer> l9 = Stream.iterate(1, x -> x + 1)
                .skip(10).limit(20).toList();

        System.out.println(l9);


        // terminal ops

        // forEach --> takes a consumer
        list.stream().forEach(x -> System.out.println(x + 1));

        // reduce --> combines elements to produce a single result or accumulator --> scan the list and apply operation.

        Optional<Integer> sum= list.stream().reduce((a, b) -> a + b); // returns an Optional --. sum of all integers
        System.out.println(sum.get());

        System.out.println(list.stream().reduce((a, b) -> a * b).get()); // product of all integers

        // 1:02:47









        int target = 4;

        ArrayList<Integer>nums = new ArrayList<>(Arrays.asList(0 ,1, 2, 3, 4, 5));
        // two sum
        List<List<Integer>>ans = IntStream.range(0, nums.size())     // --> This produces i from 0 to nums.size()-1
                .boxed()
                .flatMap(i ->                    // --> For each i, do the inner stream
                        IntStream.range(i + 1, nums.size())  // --> For each j > i
                                .filter(j -> nums.get(i) + nums.get(j) == target)
                                .mapToObj(j -> Arrays.asList(nums.get(i), nums.get(j)))
                )
                .toList();




        List<List<Integer>>res = IntStream.range(0, nums.size())
                .boxed()
                .flatMap(i ->
                        IntStream.range(i + 1, nums.size())
                                .boxed()
                                .flatMap(j ->
                                        IntStream.range(j + 1, nums.size())
                                                .filter(k -> nums.get(i) + nums.get(j) + nums.get(k) == target)
                                                .mapToObj(k -> Arrays.asList(nums.get(i), nums.get(j), nums.get(k)))
                                )
                ).toList();


        // Second Highest Number

        Optional<Integer> value = nums.stream()
                .sorted(Collections.reverseOrder()) // sort in reverse order
                .limit(2) // get first  two digit in a list
                .findFirst(); // get the first no which is 2nd highest

        // value.get()

        // Longest String in a List

        ArrayList<String> snums = new ArrayList<>(Arrays.asList("apple", "bat", "banana", "dog", "cat", "zebra"));

        Optional<String>myString = snums.stream()
                .sorted(
                        Comparator.comparingInt(String::length).reversed()
                                .thenComparing(Comparator.naturalOrder())
                )
                .limit(1).findFirst();


        Optional<String>myStr = snums.stream()
                .sorted((a, b) ->{
                    // observe java is opposite of cpp
                    if(a.length() > b.length()) return -1; // longer first considirng a is longer
                    else if(a.length() < b.length()) return 1;
                    else{
                        b.compareTo(a); // comapre a and b such the lexicographically greater comes first. for smaller a.compareTo(b)
                    }
                    return 0;
                })
                .limit(1).findFirst();


        System.out.println(myStr.get());





        // return names of all employee having salary  Above Average Salary.

        ArrayList<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee("dsdsd", 2323),
                new Employee("sfasf", 24234),
                new Employee("asfasf", 423555),
                new Employee("dgssedeg", 9999)));


        // append more into the list

        Double average = employees.stream().mapToDouble(e -> e.salary).average().orElse(0.0);

        List<String> employeeList = employees.stream()
                .filter(e -> e.salary >= average)
                .map(e -> e.name)
                .collect(Collectors.toList());

        // Common Elements in Two Lists


        ArrayList<Integer>nums1 = new ArrayList<>(Arrays.asList(0 ,1, 4, 5));

        List<Integer> common = nums.stream().
                filter(x -> nums1.contains(x)).
                collect(Collectors.toList());

        // we dont want to use find

        IntStream.range(0, nums.size())
                .boxed()
                .flatMap(i -> // note i and j are indexes
                        IntStream.range(0, nums1.size()).
                        filter(j -> nums.get(i).equals(nums1.get(j)))
                                .mapToObj(j -> nums.get(i))

                   ).collect(Collectors.toList());





    }


}

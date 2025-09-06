package streams;

import java.util.*;
import java.util.function.Function;
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




        String temp = "cccccdddddnnnnnaaaaarrrrrmmmmm";



/*        Q1. Character Frequency

        Given a string, use streams to find the frequency of each character.*/



        Map<Character, Long>hash = temp.chars() // convert into cahr of streams
                .mapToObj(x -> (char) x) // x is ASCII value, so get char value from it
                .collect(Collectors.groupingBy( // collect by grouping by frequency
                        Function.identity(), // group by char
                        // TreeMap::new, // add this to get natural ordering (alphabetical order -> a, , c, d)
                        Collectors.counting() // count the occurences
                ));

        System.out.println(hash);


        // find max value char and frequency now --> gives us the lexicographically first having max freq


        Optional<Map.Entry<Character, Long>> hash1 = hash.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        hash1.ifPresent(x ->
                System.out.println(x.getKey() + " " + x.getValue())
                );


       // ** A Now I want to get max frequency char given, if there are multiple give me lexicographically smallest


        // getting directly from hash hence we need to sort again by values.


        Optional<Map.Entry<Character, Long>> res1 =  hash.entrySet()
                .stream()
                .sorted(
                        Comparator.comparing(Map.Entry<Character, Long>::getKey).reversed() // sort by key since values are already max
                )
                .findFirst(); // get first element

        res1.ifPresent(
                x ->
                        System.out.println(x.getKey() + " " + x.getValue())
        );


        // if e have map do it directly like sort by values and key  (smallest) ->

        Optional<Map.Entry<Character, Long>> res2 =  hash.entrySet()
                .stream()
                .sorted(
                        Comparator.comparing(Map.Entry<Character, Long>::getValue).reversed() // hig
                                .thenComparing(Map.Entry<Character, Long>::getKey)
                )
                .findFirst();

        res2.ifPresent(
                x ->
                        System.out.println(x.getKey() + " " + x.getValue())
        );



        // for largest

        Optional<Map.Entry<Character, Long>> res3 = hash.entrySet()
                .stream()
                .sorted(
                        Comparator.comparing(Map.Entry<Character, Long>::getValue).reversed() // highest freq first
                                .thenComparing(Map.Entry<Character, Long>::getKey, Comparator.reverseOrder()) // tie → largest char
                )
                .findFirst();

        res3.ifPresent(
                x ->
                        System.out.println(x.getKey() + " " + x.getValue())
        );


        // Now lets do it in a single fun given only a raw string temp



        // get max frequency with max key of char (largst char largest frequency)
        // remember custom sort wont work in here
        Optional<Map.Entry<Character, Long>> entry = temp.chars()
                .mapToObj(c -> (char)c)
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .sorted(
                      Comparator.comparing(Map.Entry<Character, Long>::getValue).reversed()
                              .thenComparing(Map.Entry<Character, Long>::getKey).reversed()
                        // either write  Map.Entry<Character, Long>::getEntry, Comparator.reverseOrder())
                        // or .reversed()

                )
                .findFirst();



        entry.ifPresent(
                x -> System.out.println(x.getKey() + " " + x.getValue())
        );


        // smallest key

        Optional<Map.Entry<Character, Long>> entry1 = temp.chars()
                .mapToObj(c -> (char)c)
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .sorted(
                        Comparator.comparing(Map.Entry<Character, Long>::getValue).reversed()
                                .thenComparing(Map.Entry<Character, Long>::getKey)
                        // either write  Map.Entry<Character, Long>::getEntry, Comparator.reverseOrder())
                        // or .reversed()

                )
                .findFirst();

        entry1.ifPresent(
                x -> System.out.println(x.getKey() + " " + x.getValue())
        );


        // instead of nreturning the entrySet return value or key directly


        Character myKey = temp.chars()
                .mapToObj(c -> (char)c)
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .sorted(
                        Comparator.comparing(Map.Entry<Character, Long>::getValue).reversed()
                                .thenComparing(Map.Entry<Character, Long>::getKey)
                        // either write  Map.Entry<Character, Long>::getEntry, Comparator.reverseOrder())
                        // or .reversed()

                )
                .findFirst().get().getKey();


        // Anagram Check (new topic)
        // Given two strings, use streams to check if they are anagrams (contain the same characters with same frequency).


        String t1 = "aacfgh", t2 = "afhgca";

        Map<Character, Long> map_t1 = t1.chars()
                .mapToObj(c -> (char)c)
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );

        Map<Character, Long> map_t2 = t2
                .chars()
                .mapToObj(c -> (char)c)
                .collect(
                        Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                    )
                );

       // now compare map

        Long count = map_t1
                .entrySet()
                .stream()
                .takeWhile((a ->{
                    if(map_t2.containsKey(a.getKey())) {
                       Long v1 =  map_t2.getOrDefault(a.getKey(), 0L);
                       return v1.equals(a.getValue());
                    }
                    return false;
                })
                ).count();

        if(count == map_t1.size()) System.out.println("An anagram");


        // Any better sol

        if (t1.length() == t2.length()) {

            // Convert each string to sorted char list
            List<Character> sorted1 = t1.chars()
                    .mapToObj(c -> (char) c)
                    .sorted()
                    .collect(Collectors.toList());

            List<Character> sorted2 = t2.chars()
                    .mapToObj(c -> (char) c)
                    .sorted()
                    .collect(Collectors.toList());

            System.out.println(sorted1.equals(sorted2));
        }




        // Nth Highest Salary for employee
        int n = 3;

         List<Long> salaries = employees.
                stream()
                .sorted((a, b) ->{
                    if(a.salary > b.salary) return -1;
                    return 1;
                })
                 .map(e -> e.salary * 1L)
                .limit(n)
                 .collect(Collectors.toList());


         System.out.println(salaries);


         /*

                    else if(a.salary == b.salary){
                        if(a.name.length() > a.name.length()) return -1;
                        else if(a.name.length() > a.name.length()) return 1;
                        return a.name.compareTo(a.name);
                    }
                    return 1;
          */

        // Now get List of Employees

        List<Employee> EmplList = employees.
                stream()
                .sorted((a, b) ->{
                    if(a.salary > b.salary) return -1;
                    else if(a.salary == b.salary){
                        if(a.name.length() > a.name.length()) return -1;
                        else if(a.name.length() > a.name.length()) return 1;
                        return a.name.compareTo(a.name);
                    }
                    return 1;
                })
                .limit(n)
                .collect(Collectors.toList());

        EmplList
                .forEach(e -> System.out.println(e.name + " " + e.salary ));









        // Palindrome Strings

        ArrayList<String>l1 = new ArrayList<>(Arrays.asList("racecar", "madam", "java"));

        // return list of palindromic strings


        List<String>allPaindromes = l1.stream()
                .filter(s -> {
                    int len = s.length();
                    for(int i = 0; i < len/ 2; ++ i){
                        if(s.charAt(i) != s.charAt(len - i - 1)) return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());


        System.out.println(allPaindromes);

        // OR use helper fun.

        allPaindromes = l1.stream()
                .filter(StreamDemo::isPalindrome)
                .collect(Collectors.toList());


        System.out.println(allPaindromes);


/*        Longest Word(s) in a Sentence

        Given a sentence (string with spaces), use streams to return the longest word(s).
        Example: "I love programming in Java" → ["programming"]*/

        String word = "I love programming in Java";

        System.out.println(word);

        List<String>words = new ArrayList<>();
        // we cannot use k as normal String because lamba needs mutable types to  e modified inside it or keep it as final (dont modify).

        StringBuilder sb = new StringBuilder();

        word.chars()
                .mapToObj(c -> (char)c)
                .forEach(c -> {
                     if(!c.equals(' ')) sb.append(c);
                     else{
                         words.add(sb.toString());
                         sb.setLength(0);
                     }
                });

        String longestWord = words.stream()
                .sorted(
                        (a, b) ->{
                            if(a.length() == b.length()) {
                                return b.compareTo(a); // which is greater lexicographically comes first
                                // a.compareTo(b) -> says which is smaller comes first
                            }
                            else if(a.length() > b.length()) return -1;
                            else return 1;
                        }
                )
                .limit(1)
                .findFirst()
                .orElse(" ");

        System.out.println(longestWord);


        // get all max len words

        // split wot words
        String [] sentence = word.split("\\s+");

          // get amx len

        OptionalInt mxLen = Arrays.stream(sentence)
                .mapToInt(s -> s.length())
                .max();

        System.out.println(mxLen.getAsInt());


        List<String>maxLenWords = Arrays.stream(sentence)
                .filter(s -> s.length() == mxLen.getAsInt())
                .collect(Collectors.toList());


        System.out.println(maxLenWords);

        // get one max word lexicographically largest



        String ls = Arrays
                .stream(sentence)
                .max(
                        Comparator.comparingInt(String::length)
                                .thenComparing(Comparator.naturalOrder()).reversed() // revese the natural order
                )
                .orElse("");








/*        Top K Frequent Words

        Given a list of words, return the top 3 most frequent words sorted by frequency, and if frequencies are equal, sort lexicographically.*/

        int k = 3;
      // use tis alwasy for hasing
        Map<String, Long> hashStrings = l1.stream()
                .collect(
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                );

        // and this to sort map

         List<Map.Entry<String, Long>> topK = hashStrings
                .entrySet()
                .stream()
                .sorted(
                        Comparator.comparing(Map.Entry<String, Long>::getValue).reversed()
                                .thenComparing(Map.Entry<String, Long>::getKey).reversed()
                )
                 .limit(k)
                .collect(Collectors.toList());


         topK.forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));





    }


    public static boolean isPalindrome(String s){
        int len = s.length();
        for(int i = 0; i < len/ 2; ++ i){
            if(s.charAt(i) != s.charAt(len - i - 1)) return false;
        }
        return true;
    }


}

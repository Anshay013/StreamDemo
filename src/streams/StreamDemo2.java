package streams;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamDemo2 {

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee("dsdsd", 2323, 1, "local"),
                new Employee("sfasf", 24234, 2, "local"),
                new Employee("asfasf", 423555, 3, "local"),
                new Employee("ryh4y4", 3423423, 5, "general"),
                new Employee("jytjkt", 4234, 6, "general"),
                new Employee("g334gt", 43434, 7, "general"),
                new Employee("g34t", 533, 8, "general"),
                new Employee("t34g", 85678, 9, "exclusive"),
                new Employee("gdfg34", 5231, 10, "exclusive"),
                new Employee("reger", 75674533, 11, "exclusive"),
                new Employee("fdsfdf", 75674533, 12, "exclusive")));




        // * Important

        /*Employees Grouped by Department and Sorted by Salary

        Given a list of Employee(id, name, dept, salary), group employees by department and within each department, sort employees by salary in descending order.*/

         Map<String, List<Employee>> empList = employees
                .stream()
                .collect(
                        Collectors.groupingBy(
                                e -> e.department == null ? "Unknown" : e.department, // key (this == null check is needed it will throw nullptrExp)
                                Collectors.collectingAndThen(
                                        Collectors.toList(), // collec tthe whole list as value
                                        l -> l.stream() // stream it and sort it via salary
                                                .sorted(Comparator.comparingLong((Employee x) -> x.salary).reversed()) // sort it on the basis of salary
                                                .collect(Collectors.toList())

                                )
                        )

                );

         // this is also how we can apply foEach
         empList
                 .forEach((dept, list) -> {
                         System.out.println(dept);
                         list.forEach(e -> System.out.println(e.salary));
                         }
                 );





/*        Distinct Characters Across Words

        Given a list of words, return a set of distinct characters across all words using flatMap.
         Example: ["java", "stream"] → {j, a, v, s, t, r, e, m}*/

        ArrayList<String> myList = new ArrayList<>(Arrays.asList("java", "stream"));

        LinkedHashSet<Character>hash = new LinkedHashSet<>(); // for maintaining current order

        myList .stream() .forEach(x -> {
                                  x.chars() // stream to the strings inside
                                  .mapToObj(c -> (char) c)
                                  .forEach(hash::add);  // a -> hash.add(a)
                            });


        System.out.println(hash);


        // Important


        myList
                .stream()
                .flatMap(x -> x.chars() // flatten all the strings inside into a single string.
                                                   //  used instead of forEach two times which helps to apply .collect.
                        //  flatMap["java", "stream"] -> ["javastream"]
                        .mapToObj(c -> (char) c)
                )
                .collect(
                        Collectors.toCollection(LinkedHashSet::new)
                ).forEach(x -> System.out.println(x));





/*      Nth Highest Salary (without sorting the whole list)

        Given a list of Employee(id, name, dept, salary), find the 2nd highest salary using streams.*/


        Optional<Integer> maxSalary = employees.
                stream()
                .max(Comparator.comparingLong((Employee x) -> x.salary))
                .map(e -> e.salary);

        System.out.println(maxSalary.get());


        // now get 2nd max which is just lower then max but max
        Optional<Integer> res = employees // 2nd max salary
                .stream()
                .filter(e -> e.salary < maxSalary.get())
                .max(Comparator.comparingLong((Employee e) -> e.salary))
                .map(e -> e.salary);


        System.out.println(res.get());

        // How to get count of max salary


         Optional<Map.Entry<Integer, Long>> maxEntry = employees
                .stream()
                .map(e -> e.salary)
                .collect(  // first collect it into a hashmap
                        Collectors.groupingBy(
                                Function.identity(),
                                Collectors.counting()
                        )
                )
                .entrySet() // scan hashmap by entrySet
                .stream()
                .max( Comparator.comparingLong(x -> x.getKey())); // get entrySet with max Key.


        maxEntry.ifPresent(
                x -> System.out.println(x.getKey() + " " + x.getValue())
        );




        // can we do it in a single stream (2nd highest salary) ?


/*        Find Duplicate Elements and Their Count

        From a list of integers, return a map of duplicate elements with their frequency.*/

        //cast it to map adn then get entry sets of those whose count is greater than 1




        // ** Important use of Collectors.toMap

        ArrayList<Integer>elements = new ArrayList<>(Arrays.asList(1 , 2, 1, 1, 2 ,2 ,3 ,4, 5, 6, 6, 7, 7, 8 , 9 ,10, 10));


         Map<Integer,  Long> duplicates= elements
                .stream()
                .collect(
                        Collectors.groupingBy( // used to create a key value by frequency
                                Function.identity(),
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .filter(x -> x.getValue() >= 2)
                 .collect( //  we do this for collection where we already have key value
                         Collectors.toMap(
                         Map.Entry::getKey,
                         Map.Entry::getValue
                 ));





         duplicates
                 .entrySet()
                 .forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));






       /* Given a list of strings, group them by their first character.
         Example: ["apple", "ant", "banana", "ball"] → {a=[apple, ant], b=[banana, ball]}*/



         ArrayList<String> words = new ArrayList<>(Arrays.asList("apple", "ant", "banana", "ball"));


         // Map<Character, List<String>>


         Map<Character, List<String>>groupByFirst = words
                 .stream()
                 .sorted()
                 .collect(
                         Collectors.groupingBy( // group into  a map
                                 x -> x.charAt(0), // key
                                 Collectors.toList() // value, this can be diretly collected as list. or while collecting we can sort it or modify it via map as well
                                 )
                         );

        System.out.println(groupByFirst);


        groupByFirst
                .forEach((key, val) -> {
                    System.out.print(key + " ");
                    val.forEach(x -> System.out.print(x + " "));
                    System.out.println();
                } );



         // If we want to sort it via words inside

        Map<Character, List<String>>gbw = words
                .stream()
                .collect(
                        Collectors.groupingBy( // group into  a map
                                x -> x.charAt(0), // key
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        l -> l
                                                .stream()
                                                .sorted()
                                                .collect(Collectors.toList())
                                )
                        )
                );





    /*     Most Frequent Word in a Paragraph

        Given a paragraph (string), split into words and find the most frequent word.
👉 Example: "Java is fun and Java is powerful" → Java (2)*/


       String para = "Java is fun and Java is powerful";


        // first and foremost always split it into  and array

        String [] sentence = para.split("\\s+");

       Optional<Map.Entry<String, Long>> maxOccur = Arrays
               .stream(sentence)
               .collect(
                       Collectors.groupingBy(
                               x -> x, // key is the whole stirng
                               Collectors.counting() // value how many occurences

                       )
               )
               .entrySet()
               .stream()
               .max(
                       Comparator.comparingLong(x -> x.getValue())
               );

       maxOccur.ifPresent(
               entry -> System.out.println(entry.getKey() + " " + entry.getValue())
       );








/*        Find Common Characters Across Words

        Given a list of words, find characters that appear in all words.
         ["java", "lava", "avocado"] → {a, v}*/

        // Less of stream use more of java use


        words = new ArrayList<>(Arrays.asList("java", "lava", "avocado"));


       Set<Character> common =
               words.get(0)
                       .chars()
                       .mapToObj(c -> (char)c)
                       .collect(Collectors.toSet());


       for(int i = 1; i < words.size(); ++ i){
           common.retainAll(
                   // get common letters in words[i], retainAll does is retain which are common only
           words.get(i)
                   .chars()
                   .mapToObj(c -> (char)c)
                   .collect(Collectors.toSet())
           );

       }

       System.out.println(common);






/*        Partition Employees by Salary Threshold

        Given Employee(id, name, salary), partition employees into high earners (salary > 50k) and low earners (salary <= 50k).*/


        Map<Boolean, List<Employee>> nel = employees
                .stream()
                .collect(
                        Collectors.groupingBy(
                                e -> e.salary >= 50000,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        l -> l
                                                .stream()
                                                .collect(Collectors.toList())
                                )

                        )
                );

        // OR use partitionBy, since its a partiton it does more clarity.

        Map<Boolean, List<Employee>> nel1 = employees
                .stream()
                .collect(
                        Collectors.partitioningBy(
                                e -> e.salary >= 50000,
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        l -> l
                                                .stream()
                                                .collect(Collectors.toList())
                                )

                        )
                );



        System.out.println(nel1);

        // name keys --> more clear ->

        Map<String, List<Employee>> nel3 = employees
                .stream()
                .collect(
                        Collectors.groupingBy(
                                // key
                               e -> {
                                   if(e.salary < 50000) return "0 - 50000";
                                   else if(e.salary >= 50000) return " > 50K";
                                   else return "its much more";
                               },

                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        l -> l
                                                .stream()
                                                .collect(Collectors.toList())
                                )
                        )
                );

        System.out.println(nel3);


        Map<String, List<Long>> nel4 = employees
                .stream()
                .collect(
                        Collectors.groupingBy(
                                // key
                                e -> {
                                    if(e.salary < 50000) return "0 - 50000";
                                    else if(e.salary >= 50000) return " > 50K";
                                    else return "its much more";
                                },

                                // value -> here its the whole employee lets asy we only want salary
                                Collectors.collectingAndThen(
                                        Collectors.toList(),
                                        l -> l
                                                .stream()
                                                .map(x -> x.salary * 1L) // OR .mapToLong(emp -> emp.salary).boxed()
                                                .collect(Collectors.toList())
                                )
                        )
                );





/*        Word Length Frequency

        Given a sentence, return a map where the key is word length and the value is count of words of that length.
         "I love Java streams" → {1=1, 4=2, 6=1}*/


        para = "I love Java streams";


        // first and foremost always split it into  and array

         sentence = para.split("\\s+");

         Arrays.stream(sentence)
                 .collect(
                         Collectors.groupingBy(
                                 // key
                                 x -> x.length(),
                                 Collectors.counting()
                         )
                 )
                 .entrySet()
                 .forEach(x -> System.out.println(x.getKey() + " " + x.getValue()));








        /*Longest Palindrome Word

        Given a list of words, return the longest palindrome using streams.*/




        ArrayList<String>l1 = new ArrayList<>(Arrays.asList("racecar", "madam", "java", "aaaaaaaaa"));

        // return list of palindromic strings



        // OR use helper fun.

        l1.stream()
                .filter(StreamDemo::isPalindrome)
                .max(
                        Comparator.comparingLong(x -> x.length())
                )
                .ifPresent(
                        x -> System.out.println(x)
                );















    }
}

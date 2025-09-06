package streams;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamDemo2 {

    public static void main(String[] args) {
        ArrayList<Employee> employees = new ArrayList<>(Arrays.asList(
                new Employee("dsdsd", 2323, 1, "local"),
                new Employee("sfasf", 24234, 2, "local"),
                new Employee("asfasf", 423555, 3, "local"),
                new Employee("dgssedeg", 3423423, 5, "general"),
                new Employee("dgssedeg", 4234, 6, "general"),
                new Employee("dgssedeg", 43434, 7, "general"),
                new Employee("dgssedeg", 533, 8, "general"),
                new Employee("dgssedeg", 85678, 9, "exclusive"),
                new Employee("dgssedeg", 5231, 10, "exclusive"),
                new Employee("dgssedeg", 75674533, 1, "exclusive")));




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





    }
}

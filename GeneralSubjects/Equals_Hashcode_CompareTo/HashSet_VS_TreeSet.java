package GeneralSubjects.Equals_Hashcode_CompareTo;

import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

public class HashSet_VS_TreeSet {
    public static class Oopie implements Comparable<Oopie> {
        private int _i;
        public Oopie(int i) {
            _i = i;
        }
        public int hashCode() { return 0; }
        public int compareTo(Oopie other) { return 1; }
        public boolean equals(Object other) { return _i % 2 == 0; }
    }
    public static void main(String[] args) {
        Set<Oopie> set1 = new HashSet<>();
        Set<Oopie> set2 = new TreeSet<>();
        for(int i = 0; i < 10; i++) {
            set1.add(new Oopie(i));
            set2.add(new Oopie(i));
        }
        System.out.println("HashSet use hashCode and than equals: " + set1.size());
        System.out.println("TreeSet use compareTo: " + set2.size());
    }
}

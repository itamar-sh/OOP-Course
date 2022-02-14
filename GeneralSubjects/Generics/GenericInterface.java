package GeneralSubjects.Generics;

// simply generic inteface
interface GenericInterface<I>{
    void set(I i);
}
// interface with generic objects that are extends of the GenericInterface2.
interface GenericInterface2<I extends GenericInterface2<I>>{
    void set(I i);
}



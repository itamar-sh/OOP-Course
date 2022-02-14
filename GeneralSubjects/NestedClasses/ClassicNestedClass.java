package GeneralSubjects.NestedClasses;

public class ClassicNestedClass {
    public static void main(String[] args) {
        // ClassicNestedInner o1 = new ClassicNestedInner(2);  cant be refrenced without an object
        // ClassicNestedInner o1 = ClassicNestedClass.ClassicNestedInner(5);  cant be refrenced without an object
        // ClassicNestedInner o1 = new outer.ClassicNestedInner(5);  // cant be refrenced without an object
        ClassicNestedClass outer = new ClassicNestedClass();
        // ClassicNestedClass.ClassicNestedInner o1 = new outer.ClassicNestedInner(5);  //cant be called from static
        // ClassicNestedClass.ClassicNestedInner o1 = new ClassicNestedInner(5);  //cant be called from static

        ClassicNestedInner in1 = outer.new ClassicNestedInner(1); // the only way


    }

    public ClassicNestedInner makeInnerInstance(int num){
        return new ClassicNestedInner(num);
    }

    public class ClassicNestedInner{
        private int fld1;
        public ClassicNestedInner(int num){
            fld1 = num;
        }
        public void method1(){
            System.out.println("method1");
        }
        public int getFld1(){
            return fld1;
        }
    }
}

class PrivateOuter{
    private int fld1 = 0;
    private static int staticFld1 = 1;
    private class Inner{
        private int innerFld1 = 2;
        private static int innerStaticFld1 = 3;
        private int getFld1(){
            return innerFld1;
        }
        private int getSFld1(){
            return innerStaticFld1;
        }
        private int getOutFld1(){
            return fld1;
        }
        private int getOutSFld1(){
            return staticFld1;
        }
    }
    private int getInnerStaticFld1(){
        //return Inner.innerFld1;  //wont compile since we dont have only one instance of InnerClass
        return Inner.innerStaticFld1;
    }

    public static void main(String[] args) {
        PrivateOuter p1 = new PrivateOuter();
        System.out.println(p1.getInnerStaticFld1());
        Inner in = p1.new Inner();
    }
}

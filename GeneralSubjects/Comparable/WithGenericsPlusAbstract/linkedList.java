package GeneralSubjects.Comparable.WithGenericsPlusAbstract;

public class linkedList {
}
class MyLinkedList<T extends Comparable<T>> implements Comparable<MyLinkedList<T>>{
    Node<T> head;
    public MyLinkedList(T data){
        head = new Node<T>(data);
    }


    private static class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
        private Node<T> next = null;
        private T data;
        private Node(T data){
            this.data = data;
        }

        @Override
        public int compareTo(Node<T> other){
            return data.compareTo(other.data);
        }
    }

    public void addFirst(T data){
        Node<T> temp = new Node<T>(data);
        temp.next = head;
        head = temp;
    }

    public T getFirst(){
        return head.data;
    }

    public void addAll (MyLinkedList<? extends T> other){
        Node<T> temp = (Node<T>)other.head;
        while(temp != null){
            if(temp.data == null){
                break;
            }
            this.addFirst(temp.data);
            temp = temp.next;
        }
    }

    @Override
    public int compareTo(MyLinkedList<T> other){
        return head.compareTo(other.head);
    }
}

package rcs.mulder.utils.struct;

import java.util.Iterator;

public class MulderLinkedList<T extends MulderGarbageCollectable> extends MulderCollection<T> {
  
  private final DoublyLinkedList<T> linkedList;
  
  public MulderLinkedList() {
    linkedList = new DoublyLinkedList<T>();
  }

  public void add(T sprite) { 
    linkedList.add(sprite); 
  }

  public void clear() {
    linkedList.clear();
  }

  public int size() {
    return linkedList.size();
  }
  
  public DoublyLinkedList<T> list() {
    return linkedList;
  }

  @Override
  public Iterator<T> iterator() {
    return linkedList.iterator();
  }
}

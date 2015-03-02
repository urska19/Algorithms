/* 
 *  Your class has to (correctly) implement this interface. This is necessary to facilitate automated testing.
 */

public interface Sem2Interface
{
	boolean contains(String s); // returns true if element is in list (set), false if not
    
	boolean add(String s); // if element not in list it adds it and returns true; otherwise returns false

	void removeBetween(String a, String b); // remove all elements between a and b (inclusive) from the list; a and b do not have to be elements from the list
	
	boolean remove(String s); // removes element and returns true, returns false if element not in list
	
	void clear(); // removes all elements from list   
	
	String[] toArray(); // returns all elements from list in a String array (ordered) 
    
	String studentId(); // returns your student ID
}


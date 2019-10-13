/**  
 * Link.java -  Link class with all the getter and setter methods.  
 * @author  Deepak Kumar
 */ 


package com.workspan.assignment.entityClone;

public class Link implements Cloneable{
	private long from;
	private long to;

	public Link() {

	}

	public Link(long from, long to) {
		// TODO Auto-generated constructor stub
		this.from = from;
		this.to = to;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public long getTo() {
		return to;
	}

	public void setTo(long to) {
		this.to = to;
	}

	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
	}
}

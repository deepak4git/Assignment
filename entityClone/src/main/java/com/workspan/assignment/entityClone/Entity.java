package com.workspan.assignment.entityClone;

public class Entity implements Cloneable{
    private long entityID ;
    private String entityName;
    private String entityDesc; 
    public Entity() {
    	
    }
	public Entity(long ID, String name,String desc) {
		// TODO Auto-generated constructor stub
		this.entityID = ID;
		this.entityName = name;
		this.entityDesc = desc;
	}
	public long getEntityID() {
		return entityID;
	}
	public void setEntityID(long entityID) {
		this.entityID = entityID;
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public String getEntityDesc() {
		return entityDesc;
	}
	public void setEntityDesc(String entityDesc) {
		this.entityDesc = entityDesc;
	}
	
	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
		}
}

package com.workspan.assignment.entityClone;

import java.util.Comparator;

public class SortByEntityID implements Comparator<Entity>  {
	public int compare(Entity entity1, Entity entity2) 
    { 
        return (int) (entity1.getEntityID() - entity2.getEntityID()); 
    } 
}

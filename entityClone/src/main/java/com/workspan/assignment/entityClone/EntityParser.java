/**  
 * EntityParser.java -  This is the parser class which has all the static methods for parsing and printing json data.  
 * @author  Deepak Kumar
 */ 


package com.workspan.assignment.entityClone;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class EntityParser {

	/**  
	 * ParseEntityData - Static method for parsing entity data in Json.  
	 */ 
	
	public static ArrayList<Entity> ParseEntityData(String filePath)
	{
		JSONParser parser = new JSONParser();
		ArrayList<Entity> entities = new ArrayList<Entity>();
		try {
			Reader reader = new FileReader(filePath);
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			JSONArray entityList = (JSONArray) jsonObject.get("entities");
			@SuppressWarnings("rawtypes")
			Iterator keyItr = null;
			@SuppressWarnings("rawtypes")
			Iterator valueItr = null;
			for(int i = 0; i < entityList.size() ; i++) {
				JSONObject entityData = (JSONObject) entityList.get(i);
				keyItr = entityData.keySet().iterator();
				valueItr = entityData.values().iterator();
				Entity entity = new Entity();
				while(keyItr.hasNext()) {
					switch(keyItr.next().toString()) {
					case "entity_id" :
						entity.setEntityID((long) valueItr.next());
						break;
					case "name" :
						entity.setEntityName(valueItr.next().toString());
						break;
					case "description" :
						entity.setEntityDesc(valueItr.next().toString());
						break;
					default : 
						System.out.println("Non Matching Keys / Values");
						break;
					}
				}
				entities.add(entity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return entities;	
	}

	/**  
	 * ParseLinkData - Static method for parsing link data in Json.  
	 */ 
	
	public static ArrayList<Link> ParseLinkData(String filePath)
	{
		JSONParser parser = new JSONParser();
		ArrayList<Link> links = new ArrayList<Link>();
		try {
			Reader reader = new FileReader(filePath);
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			JSONArray entityList = (JSONArray) jsonObject.get("links");
			@SuppressWarnings("rawtypes")
			Iterator keyItr = null;
			@SuppressWarnings("rawtypes")
			Iterator valueItr = null;
			for(int i = 0; i < entityList.size() ; i++) {
				JSONObject entityData = (JSONObject) entityList.get(i);
				keyItr = entityData.keySet().iterator();
				valueItr = entityData.values().iterator();
				Link link = new Link();
				while(keyItr.hasNext()) {
					switch(keyItr.next().toString()) {
					case "from" :
						link.setFrom((long) valueItr.next());
						break;
					case "to" :
						link.setTo((long) valueItr.next());
						break;
					default : 
						System.out.println("Non Matching Keys / Values");
						break;
					}
				}
				links.add(link);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return links;	
	}

	/**  
	 * printJsonData - Static method for printing data in Json format.  
	 */ 
	
	@SuppressWarnings("unchecked")
	public static void printJsonData(List<Entity> entity, List<Link> link) {
		JSONObject entityDetailsJson = new JSONObject();
		JSONArray jsonEntityArray = new JSONArray();
		// add Entity data
		for(Entity e : entity) {
			JSONObject jsonEntity = new JSONObject();
			jsonEntity.put("entity_id", e.getEntityID());
			jsonEntity.put("name", e.getEntityName());
			if (e.getEntityDesc() != null) {
				jsonEntity.put("description", e.getEntityDesc());
			}
			jsonEntityArray.add(jsonEntity);
		}
		// add Link data
		JSONArray jsonLinkArray = new JSONArray();
		for(Link ln : link) {
			JSONObject jsonLink = new JSONObject();
			jsonLink.put("from", ln.getFrom());
			jsonLink.put("to", ln.getTo());
			jsonLinkArray.add(jsonLink);
		}

		entityDetailsJson.put("entities",jsonEntityArray );
		entityDetailsJson.put("links",jsonLinkArray );
		// Print Json as json string
		System.out.println(entityDetailsJson.toJSONString());
	}
}

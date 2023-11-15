package com.team.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;


public class StringUtil {
	
	private StringUtil() {}

	
	public static String toString(Object obj)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			if(obj.getClass().equals(ArrayList.class))
			{
				StringBuffer tempBuff = new StringBuffer();
				
				for(int i=0; i < ((List)obj).size(); i++)
				{
					
					String jsonStr = ToStringBuilder.reflectionToString(((List)obj).get(i), ToStringStyle.JSON_STYLE);
					Object jsonType = mapper.readValue(jsonStr.getBytes("UTF-8"), Object.class);
					
					tempBuff.append("\n").append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonType));
				}
				
				return tempBuff.toString();
			}
			else
			{
				String jsonStr = ToStringBuilder.reflectionToString(obj, ToStringStyle.JSON_STYLE);
				Object jsonType = mapper.readValue(jsonStr.getBytes("UTF-8"), Object.class);
				
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonType);
			}
			
		}
		catch(IOException e)
		{
			return "error!!";
		}
	}
}

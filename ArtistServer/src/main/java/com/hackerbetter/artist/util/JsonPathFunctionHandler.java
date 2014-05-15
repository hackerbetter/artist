package com.hackerbetter.artist.util;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Splitter;
import static com.google.common.base.Preconditions.*;
import com.jayway.jsonpath.JsonPath;

public class JsonPathFunctionHandler {
    public static final Pattern pattern = Pattern.compile("jsonpath\\(([^\\)]*)\\)");
    public static String handle(String template,List<String> jsonDataSources){
        Matcher matcher = pattern.matcher(template);
        while(matcher.find()){
            String params = matcher.group(1);
            Iterator<String> paramsIterator = Splitter.on(",").split(params).iterator();
            String jsonPathStr = paramsIterator.next().trim();
            int dataSourcePosition = paramsIterator.hasNext() ? Integer.parseInt(paramsIterator.next().trim()) -1 : jsonDataSources.size() -1;
            
            checkArgument(dataSourcePosition >= 0, "arg 2 must bigger then 0");
            
            String dataSource = jsonDataSources.get(dataSourcePosition);
            JsonPath jsonPath = JsonPath.compile(jsonPathStr);
            Object value = jsonPath.read(dataSource);
            
            template = template.replace(matcher.group(), value.toString());
        }
        return template;
    }
}

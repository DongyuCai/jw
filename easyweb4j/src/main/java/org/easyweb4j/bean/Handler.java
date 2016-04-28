package org.easyweb4j.bean;

import java.lang.reflect.Method;
import java.util.List;

import org.easyweb4j.filter.Filter;

/**
 * 封装 Action 信息
 * Created by CaiDongYu on 2016/4/11.
 */
public class Handler {

	/**
	 * 接受请求的类型
	 */
	private String requestMethod;
	/**
	 * Action rest 串
	 */
	private String mappingPath;
	
    /**
     * Controller 类
     */
    private Class<?> controllerClass;

    /**
     * Action 方法
     */
    private Method actionMethod;
    
    /**
     * Filter 链
     */
    private List<Filter> filterList;

    public Handler(String requestMethod, String mappingPath,Class<?> controllerClass, Method actionMethod, List<Filter> filterList){
    	this.requestMethod = requestMethod;
    	this.mappingPath = mappingPath;
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
        this.filterList = filterList;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
    
    public String getRequestMethod() {
		return requestMethod;
	}
    
    public String getMappingPath() {
		return mappingPath;
	}
    
    public List<Filter> getFilterList() {
		return filterList;
	}
}

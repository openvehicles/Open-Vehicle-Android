/**
 * 
 */
package com.openvehicles.OVMS.utils;

/**
 * @author Andrej
 *
 */

public class MyElement {

	//http://www.nikisoft.ru/blog/java/kak-poluchit-imya-tekushhego-metodaklassa/
	//Get current class name, method name and line number
	public static String getName()
	{
		Throwable t = new Throwable();
		StackTraceElement trace[] = t.getStackTrace();
		 
		// √лубина стэка должна быть больше 1-го, поскольку интересующий
		// нас элемент стэка находитс€ под индексом 1 массива элементов
		// Ёлемент с индексом 0 - это текущий метод, т.е. log
		if (trace.length > 1)
		{
			StackTraceElement element = trace[1];
			return String.format("%s.%s(...), line %d.", element.getClassName(), element.getMethodName(), element.getLineNumber());
		}
		return "[no info]";
	}
}

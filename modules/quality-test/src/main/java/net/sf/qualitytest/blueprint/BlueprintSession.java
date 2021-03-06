/*******************************************************************************
 * Copyright 2013 André Rouél and Dominik Seichter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.qualitytest.blueprint;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

/**
 * A {@code BlueprintSession} holds information acquired while doing a blueprint of a class. This includes cycle
 * detection as well as statistical information.
 * 
 * @author Dominik Seichter
 */
@NotThreadSafe
public final class BlueprintSession {

	private static final String SEPARATOR = "->";

	private final Stack<Class<?>> stack = new Stack<Class<?>>();
	private final Set<Class<?>> classes = new HashSet<Class<?>>();
	private int blueprintCount = 0;

	private String lastAction = "";

	/**
	 * Detect cycles in the blueprinting-graph. A cycle occurs when a class is blueprinted within a scope where the same
	 * class has been blueprinted before.
	 * 
	 * @param clazz
	 *            a class
	 * @return true if a cycle in the blueprinting graph was detected
	 */
	private boolean detectCycles(@Nonnull final Class<?> clazz) {
		return stack.contains(clazz);
	}

	/**
	 * Retrieve all classes that have been blueprinted in the current session.
	 * 
	 * @return a set of classes encountered while creating the blueprint
	 */
	public Set<Class<?>> getBlueprintClasses() {
		return Collections.unmodifiableSet(classes);
	}

	/**
	 * Retrieve the number of objects which have been blueprinted in the current session.
	 * 
	 * @return number of objects that have been blueprinted in the current session
	 */
	public int getBlueprintCount() {
		return blueprintCount;
	}

	/**
	 * Get the current blueprinting context as string. This is useful to describe the context in which blueprinting
	 * errors have occured.
	 * 
	 * @return context as string.
	 */
	public String getContext() {
		final StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < stack.size(); i++) {
			buffer.append(stack.get(i).getName());
			if (i < stack.size() - 1) {
				buffer.append(SEPARATOR);
			}
		}

		if (!lastAction.isEmpty()) {
			buffer.append(" {");
			buffer.append(lastAction);
			buffer.append('}');
		}

		return buffer.toString();
	}

	/**
	 * Call after creating a blueprint of a class.
	 */
	public void pop() {
		stack.pop();

		blueprintCount++;
	}

	/**
	 * Call before creating a blueprint of a class.
	 * 
	 * The internal stack is used to do cycle detection in the blueprinting graph.
	 * 
	 * @param clazz
	 *            the class for which a blueprint is created
	 * 
	 * @return true if a cycle in the blueprinting graph was detected
	 * 
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public boolean push(@Nonnull final Class<?> clazz) {
		Check.notNull(clazz, "clazz");

		final boolean cycle = detectCycles(clazz);

		stack.push(clazz);
		classes.add(clazz);

		return cycle;
	}

	/**
	 * Specify the last action performed on an object. This will be added to the {@link BlueprintSession.getContext()}
	 * to allow for simpler debugging.
	 * 
	 * @param lastAction
	 *            A description of the last action.
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public void setLastAction(@Nonnull final String lastAction) {
		this.lastAction = Check.notEmpty(lastAction, "lastAction");
	}
}

/*******************************************************************************
 * Copyright 2013 Dominik Seichter
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
package net.sf.qualitytest.blueprint.strategy.matching;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.blueprint.MatchingStrategy;

import org.junit.Assert;
import org.junit.Test;

public class CaseInsensitiveValueMatchingStrategyTest {

	@Test
	public void testCaseInsensitiveEquals() {
		final MatchingStrategy strategy1 = new CaseInsensitiveMatchingStrategy("EMail");
		final MatchingStrategy strategy2 = new CaseInsensitiveMatchingStrategy("email");

		Assert.assertEquals(strategy1, strategy2);
	}

	@Test
	public void testCaseInsensitiveHashCodeEquals() {
		final MatchingStrategy strategy1 = new CaseInsensitiveMatchingStrategy("EMail");
		final MatchingStrategy strategy2 = new CaseInsensitiveMatchingStrategy("email");

		Assert.assertEquals(strategy1.hashCode(), strategy2.hashCode());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testConstructWithNullThrows() {
		new CaseInsensitiveMatchingStrategy(null);
	}

	@Test
	public void testEquals() {
		final MatchingStrategy strategy1 = new CaseInsensitiveMatchingStrategy("EMail");
		final MatchingStrategy strategy2 = new CaseInsensitiveMatchingStrategy("EMail");

		Assert.assertEquals(strategy1, strategy2);
	}

	@Test
	public void testHashCodeEquals() {
		final MatchingStrategy strategy1 = new CaseInsensitiveMatchingStrategy("EMail");
		final MatchingStrategy strategy2 = new CaseInsensitiveMatchingStrategy("EMail");

		Assert.assertEquals(strategy1.hashCode(), strategy2.hashCode());
	}

	@Test
	public void testMatchesCaseInsensitveOk() {
		final MatchingStrategy strategy = new CaseInsensitiveMatchingStrategy("EMail");
		Assert.assertTrue(strategy.matches("email"));
	}

	@Test
	public void testMatchesOk() {
		final MatchingStrategy strategy = new CaseInsensitiveMatchingStrategy("EMail");
		Assert.assertTrue(strategy.matches("EMail"));
	}

	@Test
	public void testMatchesSetterOk() {
		final MatchingStrategy strategy = new CaseInsensitiveMatchingStrategy("EMail");
		Assert.assertTrue(strategy.matches("setEmail"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testMatchesWithNullThrows() {
		final MatchingStrategy strategy = new CaseInsensitiveMatchingStrategy("EMail");
		strategy.matches((String) null);
	}

	@Test
	public void testNotEquals() {
		final MatchingStrategy strategy1 = new CaseInsensitiveMatchingStrategy("EMail");
		final MatchingStrategy strategy2 = new CaseInsensitiveMatchingStrategy("EMail12");

		Assert.assertNotEquals(strategy1, strategy2);
	}

	@Test
	public void testNotHashCodeEquals() {
		final MatchingStrategy strategy1 = new CaseInsensitiveMatchingStrategy("EMail");
		final MatchingStrategy strategy2 = new CaseInsensitiveMatchingStrategy("EMail12");

		Assert.assertNotEquals(strategy1.hashCode(), strategy2.hashCode());
	}

	@Test
	public void testNotMatchesType() {
		final MatchingStrategy strategy = new CaseInsensitiveMatchingStrategy("EMail");
		Assert.assertFalse(strategy.matches(String.class));
	}

}

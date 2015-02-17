/*
 * Copyright 2012 demchuck.dima@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package common.hibernate;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author demchuck.dima@gmail.com
 */
public class HQLPartGeneratorTest {

	/**
	 * Test of getValuesList method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetValuesList() {
		String[] colNames = {"field1","field2.comma","field2"};
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getValuesList(colNames, rez);
		assertEquals(rez.toString(), "select field1,field2.comma,field2");
	}

	/**
	 * Test of getValuesListWithAliases method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetValuesListWithAliases() {
		String[] propNames = {"p1","p2","p3"};
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getValuesListWithAliases(propNames, new String[]{"u1","u2","u3"}, rez);
		assertEquals(rez.toString(), "select p1 as u1,p2 as u2,p3 as u3");
		rez = new StringBuilder();
		HQLPartGenerator.getValuesListWithAliases(propNames, null, rez);
		assertEquals(rez.toString(), "");
	}

	/**
	 * Test of getValuesListForUpdate method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetValuesListForUpdate() {
		String[] colNames = {"field1","field2.comma","field2"};
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getValuesListForUpdate(colNames, rez);
		assertEquals(rez.toString(), " set field1 = :prop0,field2.comma = :prop1,field2 = :prop2");
	}

	/**
	 * Test of getValuesListForUpdateNumbers method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetValuesListForUpdateNumbers() {
		String[] colNames = {"field1","field2.comma","field2"};
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getValuesListForUpdateNumbers(colNames, rez);
		assertEquals(rez.toString(), " set field1 = ?,field2.comma = ?,field2 = ?");
	}

	/**
	 * Test of getOrderBy method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetOrderBy() {
		String[] orderBy = {"p1", "p2", "p3"};
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getOrderBy(orderBy, new String[]{"desc","asc","desc"}, rez);
		assertEquals(rez.toString(), " order by p1 desc,p2 asc,p3 desc");
		rez = new StringBuilder();
		HQLPartGenerator.getOrderBy(orderBy, null, rez);
		assertEquals(rez.toString(), " order by p1,p2,p3");
	}

	/**
	 * Test of getWhereManyColumns method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetWhereManyColumns() {
		String[] whereColumns = {"p1","p2","p3"};
		Object[] whereValues = {"v1",null,"v3"};
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getWhereManyColumns(whereColumns, whereValues, rez);
		assertEquals(rez.toString(), " where p1 = :prop0 and p2 is null and p3 = :prop2");
	}

	/**
	 * Test of getWhereColumnValue method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetWhereColumnValue() {
		String whereColumn = "property";
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getWhereColumnValue(whereColumn, "test_value", rez);
		assertEquals(rez.toString(), " where property = :prop");
		rez = new StringBuilder();
		HQLPartGenerator.getWhereColumnValue(whereColumn, null, rez);
		assertEquals(rez.toString(), " where property is null");
	}

	/**
	 * Test of getWhereColumnValueRelation method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetWhereColumnValueRelation() {
		String whereColumn = "p1";
		Object whereValue = "p2";
		String relation = "like";
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getWhereColumnValueRelation(whereColumn, whereValue, relation, rez);
		assertEquals(rez.toString(), " where p1 like :prop");
		rez = new StringBuilder();
		HQLPartGenerator.getWhereColumnValueRelation(whereColumn, null, relation, rez);
		assertEquals(rez.toString(), " where p1 is null");
	}

	/**
	 * Test of getWhereColumnValues method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetWhereColumnValues_3args_1() {
		String whereColumn = "p";
		Object[] whereValues = {"v1", null, "v2"};
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getWhereColumnValues(whereColumn, whereValues, rez);
		assertEquals(rez.toString(), " where (p = :prop0 or p is null or p = :prop2)");
		rez = new StringBuilder();
		HQLPartGenerator.getWhereColumnValues(whereColumn, new Object[]{}, rez);
		assertEquals(rez.toString(), "");
	}

	/**
	 * Test of getWhereColumnValues method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetWhereColumnValues_3args_2() {
		String whereColumn = "p";
		StringBuilder rez = new StringBuilder();
		List<Object> whereValues = java.util.Arrays.<Object>asList("v1", null, "v3");
		HQLPartGenerator.getWhereColumnValues(whereColumn, whereValues, rez);
		assertEquals(rez.toString(), " where (p = :prop0 or p is null or p = :prop2)");
		rez = new StringBuilder();
		HQLPartGenerator.getWhereColumnValues(whereColumn, java.util.Collections.emptyList(), rez);
		assertEquals(rez.toString(), "");
	}

	/**
	 * Test of getWhereManyColumnsRelations method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetWhereManyColumnsRelations() {
		String[] whereColumns = {"p1","p2","p3"};
		Object[] whereValues = {"v1",null,"v2"};
		String[] whereRelations = {null,"=","like"};
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getWhereManyColumnsRelations(whereColumns, whereValues, whereRelations, rez);
		assertEquals(rez.toString(), " where p1= :prop0 and p2 is null and p3 like :prop2");
		rez = new StringBuilder();
		HQLPartGenerator.getWhereManyColumnsRelations(whereColumns, null, whereRelations, rez);
		assertEquals(rez.toString(), "");
	}

	/**
	 * Test of getWhereManyColumnsManyValues method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetWhereManyColumnsManyValues() {
		String[] whereColumns = {"p1","p2","p3"};
		Object[][] whereValues = null;
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getWhereManyColumnsManyValues(whereColumns, whereValues, rez);
		assertEquals(rez.toString(), "");
		rez = new StringBuilder();
		whereValues = new Object[][]{new Object[]{"v11",null,"v12"}, null, new Object[]{}};
		HQLPartGenerator.getWhereManyColumnsManyValues(whereColumns, whereValues, rez);
		assertEquals(rez.toString(), " where p1 in (:prop0) and p2 is null and p3 in (:prop2)");
	}

	/**
	 * Test of getWhereManyColumnsManyValuesRelations method, of class HQLPartGenerator.
	 */
	@Test
	public void testGetWhereManyColumnsManyValuesRelations() {
		String[] whereColumns = {"p1","p2","p3"};
		String[][] whereRelations = {new String[]{"=",null,"like"},new String[]{}, new String[]{}};
		Object[][] whereValues = {new Object[]{null,"v1","v2"},new Object[]{}, new Object[]{}};
		StringBuilder rez = new StringBuilder();
		HQLPartGenerator.getWhereManyColumnsManyValuesRelations(whereColumns, whereRelations, whereValues, rez);
		assertEquals(rez.toString(), " where (p1 is null or p1= :prop0_1 or p1 like :prop0_2)");

		rez = new StringBuilder();
		HQLPartGenerator.getWhereManyColumnsManyValuesRelations(whereColumns, null, whereValues, rez);
		assertEquals(rez.toString(), "");

		whereRelations = new String[][]{new String[]{"=",null,"like"},new String[]{}, new String[]{"like"}};
		whereValues = new Object[][]{new Object[]{null,"v1","v2"},new Object[]{}, new Object[]{"test"}};
		rez = new StringBuilder();
		HQLPartGenerator.getWhereManyColumnsManyValuesRelations(whereColumns, whereRelations, whereValues, rez);
		assertEquals(rez.toString(), " where (p1 is null or p1= :prop0_1 or p1 like :prop0_2)and(p3 like :prop2_0)");
	}

}
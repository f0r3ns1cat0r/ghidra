/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mdemangler;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import generic.test.AbstractGenericTest;
import ghidra.app.util.demangler.DemangledObject;
import mdemangler.naming.MDQualification;
import mdemangler.object.MDObjectCPP;
import mdemangler.typeinfo.MDVxTable;

/**
 * This class performs extra demangler testing for special cases that do not fit
 * the testing pattern found in MDMangBaseTest and its derived test classes.
 */
public class MDMangExtraTest extends AbstractGenericTest {

	@Test
	//This test checks that we can provide a mangled string for a function namespace.
	// The return String from getOriginalMangled() is not null only for this special
	// circumstance.  So, in normal processing, we should check it for non-null to
	// determine that we have a result of this form.
	// The symbol here is from our cn3.cpp source target.
	public void testFunctionNamespace() throws Exception {
		String mangled = "?fn3@?2??Bar3@Foo2b@@SAHXZ@4HA";
		String wholeTruth = "int `public: static int __cdecl Foo2b::Bar3(void)'::`3'::fn3";
		String functionNamespaceMangledTruth = "?Bar3@Foo2b@@SAHXZ";
		String functionNamespaceTruth = "public: static int __cdecl Foo2b::Bar3(void)";

		MDMangGhidra demangler = new MDMangGhidra();
		MDParsableItem item = demangler.demangle(mangled, true, true);

		String demangled = item.toString();
		assertEquals(wholeTruth, demangled);
		DemangledObject obj = demangler.getObject();
		String mangledFunctionNamespace = obj.getNamespace().getNamespace().getMangledString();
		assertEquals(functionNamespaceMangledTruth, mangledFunctionNamespace);

		item = demangler.demangle(mangledFunctionNamespace, true, true);
		demangled = item.toString();
		assertEquals(functionNamespaceTruth, demangled);
	}

	@Test
	public void testVxTableNestedQualifications() throws Exception {
		// Test string taken from MDMangBaseTest
		String mangled = "??_7a@b@@6Bc@d@e@@f@g@h@@i@j@k@@@";
		String truth = "const b::a::`vftable'{for `e::d::c's `h::g::f's `k::j::i'}";

		MDMangGhidra demangler = new MDMangGhidra();
		MDParsableItem item = demangler.demangle(mangled, true, true);

		String demangled = item.toString();
		assertEquals(truth, demangled);

		MDObjectCPP cppItem = (MDObjectCPP) item;
		MDVxTable vxTable = (MDVxTable) cppItem.getTypeInfo();
		List<MDQualification> qualifications = vxTable.getNestedQualifications();
		assertEquals(3, qualifications.size());
		assertEquals("e::d::c", qualifications.get(0).toString());
		assertEquals("h::g::f", qualifications.get(1).toString());
		assertEquals("k::j::i", qualifications.get(2).toString());
	}

	// Need to test the demangleType() method to make sure it does the retry with LLVM mode
	@Test
	public void testDemangleTypeWithRetry() throws Exception {
		// Test string taken from MDMangBaseTest
		String mangled = ".?AW4name0@?name1@name2@@YAX_N@Z@";
		String truth = "enum `void __cdecl name2::name1(bool)'::name0";

		MDMangGhidra demangler = new MDMangGhidra();
		MDParsableItem item = demangler.demangleType(mangled, true); // note demangleType()

		String demangled = item.toString();
		assertEquals(truth, demangled);
	}

}

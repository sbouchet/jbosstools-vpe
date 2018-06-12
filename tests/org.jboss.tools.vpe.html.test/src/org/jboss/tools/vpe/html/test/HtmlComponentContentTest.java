/******************************************************************************* 
 * Copyright (c) 2007 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.vpe.html.test;

import org.jboss.tools.vpe.base.test.ComponentContentTest;
import org.junit.Test;

/**
 * Class for testing all jsf components
 * 
 * @author sdzmitrovich
 * 
 */
public class HtmlComponentContentTest extends ComponentContentTest {

	public HtmlComponentContentTest() {
		setCheckWarning(false);
	}

	/* 
	 * tests for html5 tags
	 */
	@Test
	public void testArticle() throws Throwable {
		performContentTest("components/html5/article.html"); //$NON-NLS-1$
	}

	@Test
	public void testAside() throws Throwable {
		performContentTest("components/html5/aside.html"); //$NON-NLS-1$
	}

	@Test
	public void testAudio() throws Throwable {
		performContentTest("components/html5/audio.html"); //$NON-NLS-1$
	}

	@Test
	public void testBdi() throws Throwable {
		performContentTest("components/html5/bdi.html"); //$NON-NLS-1$
	}

	@Test
	public void testCommand() throws Throwable {
		performContentTest("components/html5/command.html"); //$NON-NLS-1$
	}

	@Test
	public void testDetails() throws Throwable {
		performContentTest("components/html5/details.html"); //$NON-NLS-1$
	}

	@Test
	public void testFigcaption() throws Throwable {
		performContentTest("components/html5/figcaption.html"); //$NON-NLS-1$
	}

	@Test
	public void testFigure() throws Throwable {
		performContentTest("components/html5/figure.html"); //$NON-NLS-1$
	}

	@Test
	public void testFooter() throws Throwable {
		performContentTest("components/html5/footer.html"); //$NON-NLS-1$
	}

	@Test
	public void testHeader() throws Throwable {
		performContentTest("components/html5/header.html"); //$NON-NLS-1$
	}

	@Test
	public void testHgroup() throws Throwable {
		performContentTest("components/html5/hgroup.html"); //$NON-NLS-1$
	}

	@Test
	public void testMark() throws Throwable {
		performContentTest("components/html5/mark.html"); //$NON-NLS-1$
	}

	@Test
	public void testMeter() throws Throwable {
		performContentTest("components/html5/meter.html"); //$NON-NLS-1$
	}

	@Test
	public void testNav() throws Throwable {
		performContentTest("components/html5/nav.html"); //$NON-NLS-1$
	}

	@Test
	public void testOutput() throws Throwable {
		performContentTest("components/html5/output.html"); //$NON-NLS-1$
	}

	@Test
	public void testProgress() throws Throwable {
		performContentTest("components/html5/progress.html"); //$NON-NLS-1$
	}

	@Test
	public void testRp() throws Throwable {
		performContentTest("components/html5/rp.html"); //$NON-NLS-1$
	}

	@Test
	public void testRt() throws Throwable {
		performContentTest("components/html5/rt.html"); //$NON-NLS-1$
	}

	@Test
	public void testRuby() throws Throwable {
		performContentTest("components/html5/ruby.html"); //$NON-NLS-1$
	}

	@Test
	public void testSection() throws Throwable {
		performContentTest("components/html5/section.html"); //$NON-NLS-1$
	}

	@Test
	public void testSummary() throws Throwable {
		performContentTest("components/html5/summary.html"); //$NON-NLS-1$
	}

	@Test
	public void testTime() throws Throwable {
		performContentTest("components/html5/time.html"); //$NON-NLS-1$
	}

	@Test
	public void testVideo() throws Throwable {
		performContentTest("components/html5/video.html"); //$NON-NLS-1$
	}
	
	/*
	 * 
	 * test for block html tags
	 */

	@Test
	public void testDd() throws Throwable {
		performContentTest("components/block/dd.html"); //$NON-NLS-1$
	}

	@Test
	public void testDiv() throws Throwable {
		performContentTest("components/block/div.html"); //$NON-NLS-1$
	}

	@Test
	public void testDl() throws Throwable {
		performContentTest("components/block/dl.html"); //$NON-NLS-1$
	}

	@Test
	public void testDt() throws Throwable {
		performContentTest("components/block/dt.html"); //$NON-NLS-1$
	}

	@Test
	public void testLi() throws Throwable {
		performContentTest("components/block/li.html"); //$NON-NLS-1$
	}

	@Test
	public void testOl() throws Throwable {
		performContentTest("components/block/ol.html"); //$NON-NLS-1$
	}

	@Test
	public void testSpan() throws Throwable {
		performContentTest("components/block/span.html"); //$NON-NLS-1$
	}

	@Test
	public void testUl() throws Throwable {
		performContentTest("components/block/ul.html"); //$NON-NLS-1$
	}

	/*
	 * test for core html tags
	 */

	@Test
	public void testA() throws Throwable {
		performContentTest("components/core/a.html"); //$NON-NLS-1$
	}

	@Test
	public void testAddress() throws Throwable {
		performContentTest("components/core/address.html"); //$NON-NLS-1$
	}

	@Test	
	public void testArea() throws Throwable {
		performInvisibleTagTest("components/core/area.html","area"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testBody() throws Throwable {
		performContentTest("components/core/body.html"); //$NON-NLS-1$
	}

	@Test
	public void testH1() throws Throwable {
		performContentTest("components/core/h1.html"); //$NON-NLS-1$
	}

	@Test
	public void testH2() throws Throwable {
		performContentTest("components/core/h2.html"); //$NON-NLS-1$
	}

	@Test
	public void testH3() throws Throwable {
		performContentTest("components/core/h3.html"); //$NON-NLS-1$
	}

	@Test
	public void testH4() throws Throwable {
		performContentTest("components/core/h4.html"); //$NON-NLS-1$
	}

	@Test
	public void testH5() throws Throwable {
		performContentTest("components/core/h5.html"); //$NON-NLS-1$
	}

	@Test
	public void testH6() throws Throwable {
		performContentTest("components/core/h6.html"); //$NON-NLS-1$
	}

	@Test(expected=RuntimeException.class)
	public void testHead() throws Throwable {
		performContentTest("components/core/head.html"); //$NON-NLS-1$
	}

	@Test
	public void testHtml() throws Throwable {
		performContentTest("components/core/html.html"); //$NON-NLS-1$
	}

	@Test
	public void testImg() throws Throwable {
		performContentTest("components/core/img.html"); //$NON-NLS-1$
	}

	@Test
	public void testLink() throws Throwable {
		performInvisibleTagTest("components/core/link.html", "link"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testMap() throws Throwable {
		performContentTest("components/core/map.html"); //$NON-NLS-1$
	}

	@Test
	public void testObjectEmbed() throws Throwable {
		performContentTest("components/core/object-embed.html"); //$NON-NLS-1$
	}

	@Test
	public void testParam() throws Throwable {
		performInvisibleTagTest("components/core/param.html","param"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testStyle() throws Throwable {
		performInvisibleTagTest("components/core/style.html", "style"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testTitle() throws Throwable {
		performInvisibleTagTest("components/core/title.html","title"); //$NON-NLS-1$
	}

	/*
	 * test for form html tags
	 */

	@Test
	public void testButton() throws Throwable {
		performContentTest("components/form/button.html"); //$NON-NLS-1$
	}

	@Test
	public void testFieldset() throws Throwable {
		performContentTest("components/form/fieldset.html"); //$NON-NLS-1$
	}

	@Test
	public void testForm() throws Throwable {
		performContentTest("components/form/form.html"); //$NON-NLS-1$
	}

	@Test
	public void testInput() throws Throwable {
		performContentTest("components/form/input.html"); //$NON-NLS-1$
	}

	@Test
	public void testLabel() throws Throwable {
		performContentTest("components/form/label.html"); //$NON-NLS-1$
	}

	@Test
	public void testLegend() throws Throwable {
		performContentTest("components/form/legend.html"); //$NON-NLS-1$
	}

	@Test
	public void testOptgroup() throws Throwable {
		performContentTest("components/form/optgroup.html"); //$NON-NLS-1$
	}

	@Test
	public void testOption() throws Throwable {
		performContentTest("components/form/option.html"); //$NON-NLS-1$
	}

	@Test
	public void testSelect() throws Throwable {
		performContentTest("components/form/select.html"); //$NON-NLS-1$
	}

	@Test
	public void testTextArea() throws Throwable {
		performContentTest("components/form/textArea.html"); //$NON-NLS-1$
	}

	/*
	 * test for frames html tags
	 */

	@Test
	public void testFrame() throws Throwable {
		performContentTest("components/frames/frame.html"); //$NON-NLS-1$
	}

	@Test
	public void testFrameset() throws Throwable {
		performContentTest("components/frames/frameset.html"); //$NON-NLS-1$
	}

	@Test
	public void testIframe() throws Throwable {
		performContentTest("components/frames/iframe.html"); //$NON-NLS-1$
	}

	@Test
	public void testNoframes() throws Throwable {
		performContentTest("components/frames/noframes.html"); //$NON-NLS-1$
	}

	/*
	 * test for script html tags
	 */

	@Test
	public void testScript() throws Throwable {
		performInvisibleTagTest("components/scripts/script.html", "script"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testNoscript() throws Throwable {
		performInvisibleTagTest("components/scripts/noscript.html", "noscript"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/*
	 * test for table html tags
	 */

	@Test
	public void testCaption() throws Throwable {
		performContentTest("components/table/caption.html"); //$NON-NLS-1$
	}

	@Test
	public void testCol() throws Throwable {
		performContentTest("components/table/col.html"); //$NON-NLS-1$
	}

	@Test
	public void testColgroup() throws Throwable {
		performContentTest("components/table/colgroup.html"); //$NON-NLS-1$
	}

	@Test
	public void testTable() throws Throwable {
		performContentTest("components/table/table.html"); //$NON-NLS-1$
	}

	@Test
	public void testTbody() throws Throwable {
		performContentTest("components/table/tbody.html"); //$NON-NLS-1$
	}

	@Test
	public void testTd() throws Throwable {
		performContentTest("components/table/td.html"); //$NON-NLS-1$
	}

	@Test
	public void testTfoot() throws Throwable {
		performContentTest("components/table/tfoot.html"); //$NON-NLS-1$
	}

	@Test
	public void testTh() throws Throwable {
		performContentTest("components/table/th.html"); //$NON-NLS-1$
	}

	@Test
	public void testThead() throws Throwable {
		performContentTest("components/table/thead.html"); //$NON-NLS-1$
	}

	@Test
	public void testTr() throws Throwable {
		performContentTest("components/table/tr.html"); //$NON-NLS-1$
	}

	/*
	 * test for text html tags
	 */

	@Test
	public void testAbbr() throws Throwable {
		performContentTest("components/text/abbr.html"); //$NON-NLS-1$
	}

	@Test
	public void testAcronym() throws Throwable {
		performContentTest("components/text/acronym.html"); //$NON-NLS-1$
	}

	@Test
	public void testB() throws Throwable {
		performContentTest("components/text/b.html"); //$NON-NLS-1$
	}

	@Test
	public void testBig() throws Throwable {
		performContentTest("components/text/big.html"); //$NON-NLS-1$
	}

	@Test
	public void testBlockquote() throws Throwable {
		performContentTest("components/text/blockquote.html"); //$NON-NLS-1$
	}

	@Test
	public void testBr() throws Throwable {
		performContentTest("components/text/br.html"); //$NON-NLS-1$
	}

	@Test
	public void testCite() throws Throwable {
		performContentTest("components/text/cite.html"); //$NON-NLS-1$
	}

	@Test
	public void testCode() throws Throwable {
		performContentTest("components/text/code.html"); //$NON-NLS-1$
	}

	@Test
	public void testDel() throws Throwable {
		performContentTest("components/text/del.html"); //$NON-NLS-1$
	}

	@Test
	public void testDfn() throws Throwable {
		performContentTest("components/text/dfn.html"); //$NON-NLS-1$
	}

	@Test
	public void testEm() throws Throwable {
		performContentTest("components/text/em.html"); //$NON-NLS-1$
	}

	@Test
	public void testHr() throws Throwable {
		performContentTest("components/text/hr.html"); //$NON-NLS-1$
	}

	@Test
	public void testI() throws Throwable {
		performContentTest("components/text/i.html"); //$NON-NLS-1$
	}

	@Test
	public void testIns() throws Throwable {
		performContentTest("components/text/ins.html"); //$NON-NLS-1$
	}

	@Test
	public void testKbd() throws Throwable {
		performContentTest("components/text/kbd.html"); //$NON-NLS-1$
	}

	@Test
	public void testP() throws Throwable {
		performContentTest("components/text/p.html"); //$NON-NLS-1$
	}

	@Test
	public void testPre() throws Throwable {
		performContentTest("components/text/pre.html"); //$NON-NLS-1$
	}

	@Test
	public void testQ() throws Throwable {
		performContentTest("components/text/q.html"); //$NON-NLS-1$
	}

	@Test
	public void testSamp() throws Throwable {
		performContentTest("components/text/samp.html"); //$NON-NLS-1$
	}

	@Test
	public void testSmall() throws Throwable {
		performContentTest("components/text/small.html"); //$NON-NLS-1$
	}

	@Test
	public void testStrong() throws Throwable {
		performContentTest("components/text/strong.html"); //$NON-NLS-1$
	}

	@Test
	public void testSub() throws Throwable {
		performContentTest("components/text/sub.html"); //$NON-NLS-1$
	}

	@Test
	public void testSup() throws Throwable {
		performContentTest("components/text/sup.html"); //$NON-NLS-1$
	}

	@Test
	public void testTt() throws Throwable {
		performContentTest("components/text/tt.html"); //$NON-NLS-1$
	}

	@Test
	public void testVar() throws Throwable {
		performContentTest("components/text/var.html"); //$NON-NLS-1$
	}

	/*
	 * test for other html tags
	 */
	
	@Test
	public void testBaseFont() throws Throwable {
		performContentTest("components/other/basefont.html"); //$NON-NLS-1$
	}

	@Test
	public void testBdo() throws Throwable {
		performContentTest("components/other/bdo.html"); //$NON-NLS-1$
	}

	@Test
	public void testBgsound() throws Throwable {
		performContentTest("components/other/bgsound.html"); //$NON-NLS-1$
	}

	@Test
	public void testCenter() throws Throwable {
		performContentTest("components/other/center.html"); //$NON-NLS-1$
	}

	@Test
	public void testFont() throws Throwable {
		performContentTest("components/other/font.html"); //$NON-NLS-1$
	}

	@Test
	public void testMarquee() throws Throwable {
		performContentTest("components/other/marquee.html"); //$NON-NLS-1$
	}

	@Test
	public void testNobr() throws Throwable {
		performContentTest("components/other/nobr.html"); //$NON-NLS-1$
	}

	@Test
	public void testNoembed() throws Throwable {
		performInvisibleTagTest("components/other/noembed.html", "noembed"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testWbr() throws Throwable {
		performContentTest("components/other/wbr.html"); //$NON-NLS-1$
	}

	@Test
	public void testXmp() throws Throwable {
		performContentTest("components/other/xmp.xhtml"); //$NON-NLS-1$
	}

	@Test
	public void testCssUrl() throws Throwable {
		performContentTest("jbide9975( 1 )/CSSUrlQuotes.html"); //$NON-NLS-1$
	}

	@Test
	public void testCssImport() throws Throwable {
		performStyleTest("jbide9975( 1 )/CSSImportConstruction.html"); //$NON-NLS-1$
	}

	@Test
	public void testComplexStyle() throws Throwable {
		performContentTest("jbide10126/complexStyle.html"); //$NON-NLS-1$
	}

	protected String getTestProjectName() {
		return HtmlAllTests.IMPORT_PROJECT_NAME;
	}
}

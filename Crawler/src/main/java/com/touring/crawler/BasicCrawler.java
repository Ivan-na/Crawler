package com.touring.crawler;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class BasicCrawler extends WebCrawler {
	// private static final Pattern IMAGE_EXTENSIONS =
	// Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpg|png|mp3|mp4|zip|gz))$");
	private final static String DOMAIN = "http://beijing.lotour.com";

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();
		// Ignore the url if it has an extension that matches our defined set of
		// image extensions.
		if (FILTERS.matcher(href).matches()) {
			return false;
		}
		//System.out.println(href);
		//System.out.println(href.contains(DOMAIN));
		/*if(!SCAN.matcher(href).matches()){
			return false;
		}*/
		// Only accept the url if it is in the "www.ics.uci.edu" domain and
		// protocol is "http".
		// return href.startsWith("http://www.ics.uci.edu/");
		return href.startsWith(DOMAIN);
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {
		int docid = page.getWebURL().getDocid();
		String url = page.getWebURL().getURL();
		String domain = page.getWebURL().getDomain();
		String path = page.getWebURL().getPath();
		String subDomain = page.getWebURL().getSubDomain();
		String parentUrl = page.getWebURL().getParentUrl();
		String anchor = page.getWebURL().getAnchor();

		//System.out.println("Docid: {}" + docid);
		//System.out.println("URL: {}" + url);
		/*
		 * System.out.println("Domain: '{}'" + domain); System.out.println(
		 * "Sub-domain: '{}'" + subDomain); System.out.println("Path: '{}'" +
		 * path); System.out.println("Parent page: {}" + parentUrl);
		 * System.out.println("Anchor text: {}" + anchor);
		 */

		if (page.getParseData() instanceof HtmlParseData) {
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			// String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();
			// Set<WebURL> links = htmlParseData.getOutgoingUrls();
			// System.out.println("Text length: {}" + text.length());
			// System.out.println("Html length: {}" + html.length());
			// System.out.println("Number of outgoing links: {}" +
			// links.size());
			Document doc = Jsoup.parse(html);
			Element f = doc.select(".lvwen-tit-txt").first();
			Element iaT = doc.select(".ia-title").first();
			//Element iaT2 = doc.select(".ia-subtitle").first();
			Element textzj = doc.select("div.ia-text > p").first();
			Element textzw = doc.select("div.ia-text > p").get(1);
			
			if(null!=f){
				System.out.println("Docid: {}" + docid);
				System.out.println("URL: {}" + url);
				System.out.println(f.text());
				System.out.println(textzj.text());
				System.out.println(textzw.text());
				System.out.println("=============");
			}
			if(null!=iaT){
				System.out.println("Docid: {}" + docid);
				System.out.println("URL: {}" + url);
				System.out.println(iaT.text());
				//System.out.println(iaT2.text());
				System.out.println(textzj.text());
				System.out.println(textzw.text());
				System.out.println("=============");
			}
		}

		/*
		 * Header[] responseHeaders = page.getFetchResponseHeaders(); if
		 * (responseHeaders != null) { System.out.println("Response headers:");
		 * for (Header header : responseHeaders) { System.out.println("\t{}: {}"
		 * + header.getName() + ",,," + header.getValue()); } }
		 */
	}
}

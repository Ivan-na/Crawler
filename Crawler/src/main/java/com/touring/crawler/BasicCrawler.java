package com.touring.crawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import com.csvreader.CsvWriter;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class BasicCrawler extends WebCrawler {
	// private static final Pattern IMAGE_EXTENSIONS =
	// Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpg|png|mp3|mp4|zip|gz))$");
	// private final static String DOMAIN = "http://beijing.lotour.com";
	private final static String DOMAIN = "http://shopping.bcia.com.cn";

	private final static String CSVPATH = "F://test.csv";
	private CsvWriter cw;
	private File csv;
	private String range = "�й���Ʒ����Ʒ����,��������,Hour Passion,������ Bvlgari,ľ��ʮ,OMEGA ,�����ֱ���е�313,˹���� swatch,ʩ�������� Swarovski,�й��ƽ��,�M���ã��M��꣩,������,ͬ����,��Ҷ�궫,��Ҷ����,����Է,��Ľ�Ƶ� Camus,�����ز���,���õ�,����,��������-��Ʒ��,��������-��Ʒ��,������,�����ƿ���,nici��NiCI��ߵ꣩,��ߵ�,Kiehls ,�����㻯��327,ELLE HOMME,������Ľ Ferragamo,304ˮ����,��ζѼ����,��ˮ��վ,�ز���309������309���ز��꣩,�ز���,���ھӱ����꣨���ھӵ꣩,�����ز���,����330������,�ۺ��̵�,�����ز���309��,���Ǳ������۳�340�ŵ�,�����ز����۳�336,�����ز����۳�337,�ۺϵ�315��,�����ز���342,345ȫ�۵�,��ʳ԰,��ɣ��,zipit,�ʹ���� CROWN,����ի-һ�ŵ�,�����鿯,�����鿯1�ŵ�,�����鿯7�ŵ�,�����鿯6�ŵ�,�����鿯8�ŵ�,�����鿯9�ŵ�,�����鿯4�ŵ�,�����鿯5�ŵ�,�����鿯3�ŵ�,�����鿯10�ŵ�,�ز�������332,�ۺϵ�325��,���ϱ�����,������˹,GANT,BALLY,���� PORTS,ʥ٤�� SKAP,��Ľ����,Ͼ��ʿ,JACKWOLF-����320,ŵ�ۿ�-����320,KENT&amp;curwen,CERRUTI 1881,��ľ�� Timberland,���� JEEP,�������,��Ľ,�ε�����,MANGO,�嶼 ASILK,MaxMara,1436,������ Zegna,�Ϻ�̲ ShanghaiTang,���ױ���,��ϲ· Alfred Dunhill,���� LACOSTE,������Ʒ��311,������Ʒ��321";
	public BasicCrawler() throws IOException {
		csv = new File(CSVPATH);
		if (csv.isFile()) {
			csv.delete();
		}
		cw = new CsvWriter(new FileWriter(csv, true), ',');
		cw.write("name");
		cw.write("type");
		cw.write("time");
		cw.write("location");
		cw.write("details");
		cw.write("url");
		cw.endRecord();
		cw.close();
	}

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
		// System.out.println(href);
		// System.out.println(href.contains(DOMAIN));
		/*
		 * if(!SCAN.matcher(href).matches()){ return false; }
		 */
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

		// System.out.println("Docid: {}" + docid);
		// System.out.println("URL: {}" + url);
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
			System.out.println("URL: {}" + url);
			// Element f = doc.select(".storeinstr").first();
			Element store = doc.select(".store").first();
			String store_name = store.nextSibling().toString();
			System.out.println(store_name);
			Element service = doc.select(".service").first();
			String service_type = service.nextSibling().toString();
			System.out.println(service_type);
			Element time = doc.select(".time").first();
			String time_range = time.nextSibling().toString();
			System.out.println(time_range);
			Element map = doc.select(".map").first();
			String map_lo = map.nextSibling().toString();
			System.out.println(map_lo);
			Element info = doc.select("#brandinstrduce").first();
			String info_t = info.text();
			System.out.println(info_t);
			try {
				if (range.contains(store_name.trim())) {
					cw = new CsvWriter(new FileWriter(csv, true), ',');
					cw.write(store_name.toString());
					cw.write(service_type.toString());
					cw.write(time_range.toString());
					cw.write(map_lo.toString());
					cw.write(info_t);
					cw.write(url);
					cw.endRecord();
					cw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			/*
			 * Element f = doc.select(".lvwen-tit-txt").first(); Element iaT =
			 * doc.select(".ia-title").first(); //Element iaT2 =
			 * doc.select(".ia-subtitle").first(); Element textzj = doc.select(
			 * "div.ia-text > p").first(); Element textzw = doc.select(
			 * "div.ia-text > p").get(1);
			 * 
			 * if(null!=f){ System.out.println("Docid: {}" + docid);
			 * System.out.println("URL: {}" + url);
			 * System.out.println(f.text()); System.out.println(textzj.text());
			 * System.out.println(textzw.text());
			 * System.out.println("============="); } if(null!=iaT){
			 * System.out.println("Docid: {}" + docid); System.out.println(
			 * "URL: {}" + url); System.out.println(iaT.text());
			 * //System.out.println(iaT2.text());
			 * System.out.println(textzj.text());
			 * System.out.println(textzw.text());
			 * System.out.println("============="); }
			 */
		}

		/*
		 * Header[] responseHeaders = page.getFetchResponseHeaders(); if
		 * (responseHeaders != null) { System.out.println("Response headers:");
		 * for (Header header : responseHeaders) { System.out.println("\t{}: {}"
		 * + header.getName() + ",,," + header.getValue()); } }
		 */
	}
}

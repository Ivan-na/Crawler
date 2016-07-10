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
	private String range = "中国商品，精品电器,翼蓝数码,Hour Passion,宝格丽 Bvlgari,木九十,OMEGA ,航诚手表店中店313,斯沃琪 swatch,施华洛世奇 Swarovski,中国黄金店,鱉龟堂（鱉龟店）,倍轻松,同仁堂,茶叶店东,茶叶店西,华祥苑,卡慕酒店 Camus,航诚特产店,酷泼德,韵泓,北京礼物-礼品店,北京礼物-礼品店,法蓝瓷,西藏唐卡店,nici（NiCI玩具店）,玩具店,Kiehls ,航诚香化店327,ELLE HOMME,菲拉格慕 Ferragamo,304水果店,绝味鸭脖店,乐水驿站,特产店309（航诚309号特产店）,特产店,好邻居便利店（好邻居店）,航程特产店,航诚330便利店,综合商店,航城特产店309号,航城便利零售车340号店,航诚特产零售车336,航诚特产零售车337,综合店315号,航诚特产店342,345全聚德,御食园,迪桑娜,zipit,皇冠箱包 CROWN,文语斋-一号店,中信书刊,中信书刊1号店,中信书刊7号店,中信书刊6号店,中信书刊8号店,中信书刊9号店,中信书刊4号店,中信书刊5号店,中信书刊3号店,中信书刊10号店,特产便利店332,综合店325号,航诚便利店,鄂尔多斯,GANT,BALLY,宝姿 PORTS,圣伽步 SKAP,爱慕先生,暇步士,JACKWOLF-航程320,诺帝卡-航程320,KENT&amp;curwen,CERRUTI 1881,天木兰 Timberland,吉普 JEEP,宝马服饰,爱慕,嘉德利亚,MANGO,绣都 ASILK,MaxMara,1436,杰尼亚 Zegna,上海滩 ShanghaiTang,哥伦比亚,登喜路 Alfred Dunhill,鳄鱼 LACOSTE,航诚礼品店311,航诚礼品店321";
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

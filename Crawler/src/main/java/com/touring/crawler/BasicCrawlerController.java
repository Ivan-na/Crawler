package com.touring.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class BasicCrawlerController {
	// private static final Logger logger =
	// LoggerFactory.getLogger(BasicCrawlerController.class);

	public static void main(String[] args) throws Exception {
		/*
		 * if (args.length != 2) { System.out.println("Needed parameters: ");
		 * System.out.println(
		 * "\t rootFolder (it will contain intermediate crawl data)");
		 * System.out.println(
		 * "\t numberOfCralwers (number of concurrent threads)"); return; }
		 */
		/*
		 * crawlStorageFolder is a folder where intermediate crawl data is
		 * stored.
		 */
		// String crawlStorageFolder = args[0];
		String crawlStorageFolder = "F://T";
		/*
		 * numberOfCrawlers shows the number of concurrent threads that should
		 * be initiated for crawling.
		 */
		// int numberOfCrawlers = Integer.parseInt(args[1]);
		int numberOfCrawlers = 3;
		CrawlConfig config = new CrawlConfig();

		config.setCrawlStorageFolder(crawlStorageFolder);

		/*
		 * Be polite: Make sure that we don't send more than 1 request per
		 * second (1000 milliseconds between requests).
		 */
		config.setPolitenessDelay(1000);

		/*
		 * You can set the maximum crawl depth here. The default value is -1 for
		 * unlimited depth
		 */
		config.setMaxDepthOfCrawling(2);

		/*
		 * You can set the maximum number of pages to crawl. The default value
		 * is -1 for unlimited number of pages
		 */
		config.setMaxPagesToFetch(1000);

		/**
		 * Do you want crawler4j to crawl also binary data ? example: the
		 * contents of pdf, or the metadata of images etc
		 */
		config.setIncludeBinaryContentInCrawling(false);

		/*
		 * Do you need to set a proxy? If so, you can use:
		 * config.setProxyHost("proxyserver.example.com");
		 * config.setProxyPort(8080);
		 *
		 * If your proxy also needs authentication:
		 * config.setProxyUsername(username); config.getProxyPassword(password);
		 */

		/*
		 * This config parameter can be used to set your crawl to be resumable
		 * (meaning that you can resume the crawl from a previously
		 * interrupted/crashed crawl). Note: if you enable resuming feature and
		 * want to start a fresh crawl, you need to delete the contents of
		 * rootFolder manually.
		 */
		config.setResumableCrawling(false);

		/*
		 * Instantiate the controller for this crawl.
		 */
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		/*
		 * For each crawl, you need to add some seed urls. These are the first
		 * URLs that are fetched and then the crawler starts following links
		 * which are found in these pages
		 */
		// controller.addSeed("http://www.lotour.com");
		// controller.addSeed("http://shopping.bcia.com.cn/app/eshop/index/mall/name/shopping/id/26/left_ref/130/right_ref/219");\

		// controller.addSeed("http://www.qdairport.com/control/setSessionLocale?newLocale=zh_CN");
		// controller.addSeed("http://www.qdairport.com/control/shopping_food?catalogId=head_cygw02");

		// controller.addSeed("http://csa.hnjcjt.com/channels/748.html");

		/*
		 * controller.addSeed("http://www.zzairport.com/c/list.php?tid=63");
		 * controller.addSeed("http://www.zzairport.com/c/list.php?tid=64");
		 * controller.addSeed("http://www.zzairport.com/c/list.php?tid=65");
		 * controller.addSeed("http://www.zzairport.com/c/list.php?tid=66");
		 */
		/*
		 * controller.addSeed("http://www.zzairport.com/c/list.php?tid=75");
		 * controller.addSeed("http://www.zzairport.com/c/list.php?tid=76");
		 * controller.addSeed("http://www.zzairport.com/c/list.php?tid=77");
		 * controller.addSeed("http://www.zzairport.com/c/list.php?tid=78");
		 * controller.addSeed("http://www.zzairport.com/c/list.php?tid=79");
		 */

		/*
		 * for(int i =1;i<=43;i++){ controller.addSeed(
		 * "http://s.visitbeijing.com.cn/index.php?m=content&c=search&catid=7&area=3848&theme2=0&crowd=0&level=0&ticselect=0&page="
		 * +i); }
		 */

		for (int i = 1; i <= 1000; i++) {
			controller.addSeed("http://you.ctrip.com/restaurantlist/beijing1/s0-p" + i + ".html");
		}

		/*
		 * Start the crawl. This is a blocking operation, meaning that your code
		 * will reach the line after this only when crawling is finished.
		 */
		// controller.start(BasicCrawler.class, numberOfCrawlers);
		// controller.start(QdBasicCrawler.class, numberOfCrawlers);
		// controller.start(CsBasicCrawler.class, numberOfCrawlers);
		// controller.start(ZzBasicCrawler.class, numberOfCrawlers);
		controller.start(BjMs.class, numberOfCrawlers);

	}
}

package com.example.PCOnlineShop.service.blog;

import com.example.PCOnlineShop.dto.blog.BlogLinkDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class HacomScraperService {

    private static final String HACOM_TIN_TUC = "https://hacom.vn/tin-tuc";
    private final List<BlogLinkDto> cache = new ArrayList<>();
    private volatile long lastFetch = 0L;
    private static final long CACHE_TTL_MS = 10 * 60 * 1000L; // 10 phút

    public List<BlogLinkDto> getLatest() {
        if (System.currentTimeMillis() - lastFetch > CACHE_TTL_MS || cache.isEmpty()) {
            synchronized (this) {
                if (System.currentTimeMillis() - lastFetch > CACHE_TTL_MS || cache.isEmpty()) {
                    try {
                        List<BlogLinkDto> fresh = fetchFromHacom();
                        cache.clear();
                        cache.addAll(fresh);
                        lastFetch = System.currentTimeMillis();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new ArrayList<>(cache);
    }

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void scheduledRefresh() {
        try {
            List<BlogLinkDto> fresh = fetchFromHacom();
            synchronized (this) {
                cache.clear();
                cache.addAll(fresh);
                lastFetch = System.currentTimeMillis();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<BlogLinkDto> fetchFromHacom() throws Exception {
        List<BlogLinkDto> result = new ArrayList<>();

        Document doc = Jsoup.connect(HACOM_TIN_TUC)
                .userAgent("Mozilla/5.0 (compatible; HacomScraper/1.0)")
                .timeout(10_000)
                .get();

        String[] candidateContainers = {
                "div.blog-list",
                "div.tin-tuc-list",
                "div.news-list",
                "ul.products-list",
                "div.items",
                "article",
                "div.post-item",
                "div.latest-news"
        };

        Elements items = new Elements();
        for (String sel : candidateContainers) {
            Elements found = doc.select(sel + " a");
            if (found != null && !found.isEmpty()) {
                items = doc.select(sel);
                break;
            }
        }

        if (items.isEmpty()) {
            items = doc.select("article, .news-item, .post-item, .tin-tuc-item, .item-news, .blog-card, .col-news");
        }

        if (items.isEmpty()) {
            items = doc.select("a[href*=\"/tin-tuc\"], a[href*=\"/news\"], a[href*=\"/blog\"]");
        }

        int max = 30;
        outer:
        for (Element el : items) {
            if (result.size() >= max) break;
            Element a = null;
            if (el.tagName().equals("a")) {
                a = el;
            } else {
                a = el.selectFirst("a[href]");
                if (a == null) {
                    a = el.selectFirst("a");
                }
            }
            if (a == null) continue;

            String href = a.attr("href").trim();
            if (href.isEmpty()) continue;
            if (href.startsWith("javascript:") || href.startsWith("mailto:")) continue;

            // 🔹 Tạo biến final để dùng trong lambda
            final String finalHref = absolutizeUrl(HACOM_TIN_TUC, href);

            String title = a.text();
            if (title == null || title.isBlank()) {
                Element h = el.selectFirst("h1, h2, h3, .title");
                if (h != null) title = h.text();
            }
            if (title == null || title.isBlank()) continue;

            String img = null;
            Element imgEl = el.selectFirst("img");
            if (imgEl == null) imgEl = a.selectFirst("img");
            if (imgEl != null) {
                img = imgEl.hasAttr("data-src") ? imgEl.attr("data-src") : imgEl.attr("src");
                if (img != null && !img.isBlank()) img = absolutizeUrl(HACOM_TIN_TUC, img);
            }

            String summary = null;
            Element p = el.selectFirst("p, .summary, .desc, .excerpt");
            if (p != null) summary = p.text();

            boolean dup = result.stream().anyMatch(r -> r.getLink().equalsIgnoreCase(finalHref));
            if (dup) continue;

            result.add(new BlogLinkDto(title, finalHref, img, summary));
            if (result.size() >= 10) break outer;
        }

        return result;
    }

    private String absolutizeUrl(String base, String url) {
        try {
            URL baseUrl = new URL(base);
            URL abs = new URL(baseUrl, url);
            return abs.toString();
        } catch (MalformedURLException e) {
            return url;
        }
    }
}

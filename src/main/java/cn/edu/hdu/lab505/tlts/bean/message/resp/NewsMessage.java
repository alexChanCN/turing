package cn.edu.hdu.lab505.tlts.bean.message.resp;

import java.util.List;

/**
 * ͼ����Ϣ
 * 
 * @author Administrator
 * 
 */
public class NewsMessage extends BaseMessage {
	private int ArticleCount;
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}

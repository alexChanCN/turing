package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.common.AbstractHibernateCurdDaoSupport;
import cn.edu.hdu.lab505.tlts.domain.Feedback;
import org.springframework.stereotype.Repository;

/**
 * Created by hhx on 2017/1/12.
 */
@Repository
public class FeedbackDao extends AbstractHibernateCurdDaoSupport<Feedback> implements IFeedbackDao {
}

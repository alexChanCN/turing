package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.common.AbstractCurdServiceSupport;
import cn.edu.hdu.lab505.tlts.common.ICurdDaoSupport;
import cn.edu.hdu.lab505.tlts.dao.IFeedbackDao;
import cn.edu.hdu.lab505.tlts.domain.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by hhx on 2017/1/12.
 */
@Service
public class FeedbackService extends AbstractCurdServiceSupport<Feedback> implements IFeedbackService {
    @Autowired
    private IFeedbackDao feedbackDao;

    @Override
    protected ICurdDaoSupport<Feedback> getCurdDao() {
        return feedbackDao;
    }

    @Override
    @Transactional
    public void insert(Feedback entity) {
        entity.setDate(new Date());
        super.insert(entity);
    }
}

package cn.edu.hdu.lab505.tlts.service;

import cn.edu.hdu.lab505.tlts.domain.Upload;

import java.util.List;

/**
 * Created by ShineChan on 2017/11/24.
 */
public interface IUploadService {
    List<Upload> listAll();

    Long save(Upload upload);
}

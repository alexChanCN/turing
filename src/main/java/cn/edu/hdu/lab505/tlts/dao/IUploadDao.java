package cn.edu.hdu.lab505.tlts.dao;

import cn.edu.hdu.lab505.tlts.domain.Student;
import cn.edu.hdu.lab505.tlts.domain.Upload;

import java.util.List;

/**
 * Created by ShineChan on 2017/11/24.
 */
public interface IUploadDao {

    List<Upload> listAll();

    List<Upload> listAllByStudent(Student student);

    Long save(Upload upload);
}

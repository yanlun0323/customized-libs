package com.customized.libs.springboot.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author yan
 */
@Service
public class FileQueryService implements InitializingBean {

    @Resource(name = "defaultGridFsOperations")
    private GridFsOperations gridFsOperations;

    @Override
    public void afterPropertiesSet() {
        String filename = "5fdbec1bb92b4ce3bc540cbf6d8ae345.png";

        Query query = Query.query(Criteria.where("filename").is(filename));
        GridFSFile one = this.gridFsOperations.findOne(query);
        if (Objects.nonNull(one)) {
            System.out.println("gridFs ==> chunkSize: " + one.getChunkSize() +"\tfilename: " +  one.getFilename());
        } else {
            System.out.println("gridFs ==> empty");
        }
    }
}

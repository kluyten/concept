package eu.concept.repository.concept.service;

import eu.concept.repository.concept.dao.MetadataRepository;
import eu.concept.repository.concept.domain.Component;
import eu.concept.repository.concept.domain.Metadata;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Christos Paraskeva <ch.paraskeva at gmail dot com>
 */
@Service
public class MetadataService {

    @Autowired
    private MetadataRepository metadataRepo;

    public boolean storeMetadata(Metadata metadata) {
        try {

            metadataRepo.save(metadata);
        } catch (Exception ex) {
            Logger.getLogger(MetadataService.class.getName()).severe(ex.getMessage());
            return false;
        }
        return metadata.getId() > 0;
    }

    public boolean deleteMetadata(long metadataID) {
        try {
            metadataRepo.delete(metadataID);
        } catch (Exception ex) {
            Logger.getLogger(MetadataService.class.getName()).severe(ex.getMessage());
            return false;
        }
        return true;
    }

    public Metadata fetchMetadataById(long id) {
        return metadataRepo.findOne(id);
    }

    public Metadata fetchMetadataByCidAndComponent(int cid, String component) {
        Component comp = new Component(component);
        return metadataRepo.findByCidAndComponent(cid, comp);
    }

    public List<String> findAllMetadata() {
        return  metadataRepo.findAllByCustomQueryAndStream().flatMap(metadata -> Stream.of(metadata.getKeywords().split(","))).distinct().collect(Collectors.toList());
    }

}

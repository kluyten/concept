package eu.concept.repository.concept.dao;

import eu.concept.repository.concept.domain.BriefAnalysis;
import eu.concept.repository.concept.domain.FileManagement;
import eu.concept.repository.concept.domain.Likes;
import eu.concept.repository.concept.domain.MindMap;
import eu.concept.repository.concept.domain.Sketch;
import eu.concept.repository.concept.domain.UserCo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Christos Paraskeva <ch.paraskeva at gmail dot com>
 */
public interface LikesRepository extends JpaRepository<Likes, Integer> {

    //Fetch Brief Analysis  Likes for a spercific User
    public Likes findByUidAndBaId(UserCo user, BriefAnalysis ba_id);

    //Fetch Filemanagement  Likes for a spercific User
    public Likes findByUidAndFmId(UserCo user, FileManagement fm_id);

    //Fetch MindMap  Likes for a spercific User
    public Likes findByUidAndMmId(UserCo user, MindMap mm_id);

    //Fetch Sketch  Likes for a spercific User
    public Likes findByUidAndSkId(UserCo user, Sketch sk_id);

}

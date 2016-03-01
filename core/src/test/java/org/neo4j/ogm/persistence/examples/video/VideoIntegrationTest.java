package org.neo4j.ogm.persistence.examples.video;


import org.neo4j.ogm.domain.video.Video;
import org.neo4j.ogm.domain.video.VideoAsset;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.testutil.MultiDriverTestClass;
import org.neo4j.ogm.transaction.Transaction;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertNotNull;

@Test
public class VideoIntegrationTest extends MultiDriverTestClass {

    private final SessionFactory sessionFactory = new SessionFactory("org.neo4j.ogm.domain.video");

    @AfterClass
    public void clear() {
        Session session = sessionFactory.openSession();
        session.purgeDatabase();
    }

    @Test(invocationCount = 1, threadPoolSize = 1)
    public void shouldSaveVideosAndVideoAssetsCorrectlySingleThread() {
        saveVideo();
    }


    /**
     * MappingContext state seems to get polluted resulting in deleting relationships
     *
     * Relationships between Video and VideoAsset gets deleted.
     * Seeing: UNWIND {rows} as row MATCH (startNode) WHERE ID(startNode) = row.startNodeId MATCH (endNode) WHERE ID(endNode) = row.endNodeId MATCH (startNode)-[rel:`VIDEO_DESCRIBES_VIDEO_ASSET`]->(endNode) DELETE rel
     */
    @Test(invocationCount = 800, threadPoolSize = 6)
    public void shouldSaveVideosAndVideoAssetsCorrectly() {
        saveVideo();
    }

    public void saveVideo() {
        Session session = sessionFactory.openSession();
        /*
        Save VideoAsset node only, in one transaction
         */

        Transaction tx = session.beginTransaction();
        // save video asset
        VideoAsset videoAsset = new VideoAsset();
        videoAsset.setTitle("X-Men "+ UniqueIdGenerator.nextId());
        session.save(videoAsset);
        tx.commit();
        tx.close();

        /*
        Save new Video node + relationship to VideoAsset, in second transaction
         */

        Transaction tx2 = session.beginTransaction();
        // retrieve video asset from db
        Map<String, Object> params = new HashMap<>();
        params.put("title", videoAsset.getTitle());
        VideoAsset videoAssetFromDb = session.queryForObject(VideoAsset.class, "MATCH (n:VideoAsset) WHERE n.title={title} RETURN n", params);

        // create new video and link it to saved video asset
        Video video = new Video();
        video.setTitle("meta for :" + videoAssetFromDb.getTitle());
        video.setVideoAsset(videoAssetFromDb);
        // should save the newly created video and relationship to video asset
        session.save(video);
        tx2.commit();
        tx2.close();

        /*
        Make sure the relationship between Video and VideoAsset exists.
         */

        Map<String, Object> params2 = new HashMap<>();
        params2.put("title", video.getTitle());
        // fetch the video asset linked to the video we just saved
        VideoAsset videoAssetLinked = session.queryForObject(VideoAsset.class, "MATCH (f:Video)-[r:VIDEO_DESCRIBES_VIDEO_ASSET]->(t) WHERE f.title={title} RETURN t", params2);


        assertNotNull(videoAssetLinked);
    }


    /**
     * Generates sequential unique IDs starting with 1, 2, 3, and so on.
     * <p>
     * This class is thread-safe.
     * </p>
     */
    public static class UniqueIdGenerator {
        private static final AtomicLong counter = new AtomicLong();

        public static long nextId() {
            return counter.incrementAndGet();
        }
    }

}

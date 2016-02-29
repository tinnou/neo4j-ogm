package org.neo4j.ogm.domain.video;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Video {

    @GraphId
    private Long gid;
    private String title;
    private String caption;
    private String url;
    @Relationship(type = "VIDEO_DESCRIBES_VIDEO_ASSET")
    private VideoAsset videoAsset;

    public VideoAsset getVideoAsset() {
        return videoAsset;
    }

    public Video setVideoAsset(VideoAsset videoAsset) {
        this.videoAsset = videoAsset;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public Video setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public Long getGid() {
        return gid;
    }

    public Video setGid(Long gid) {
        this.gid = gid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Video setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Video setUrl(String url) {
        this.url = url;
        return this;
    }
}

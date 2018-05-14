package org.exoplatform.gamification.service.dto.configuration;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.exoplatform.gamification.entities.domain.configuration.BadgeEntity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BadgeDTO implements Serializable {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    protected Long id;

    protected String title;

    protected String description;

    protected int neededScore;

    //protected String icon;

    private byte[] icon;

    protected String startValidityDate;

    protected String endValidityDate;

    protected boolean isEnabled;

    private String createdBy;

    private String createdDate;

    private String lastModifiedBy;

    private String lastModifiedDate;

    public BadgeDTO() {
    }

    public BadgeDTO(BadgeEntity badgeEntity) {

        this.id = badgeEntity.getId();

        this.title = badgeEntity.getTitle();

        //TODO : save an inputStream
        //this.icon = badgeEntity.getIcon();

        this.neededScore = badgeEntity.getNeededScore();

        this.startValidityDate = formatter.format(badgeEntity.getStartValidityDate());

        this.endValidityDate = formatter.format(badgeEntity.getEndValidityDate());

        this.isEnabled = badgeEntity.isEnabled();

        this.description = badgeEntity.getDescription();

        this.createdBy = badgeEntity.getCreatedBy();

        this.createdDate = formatter.format(badgeEntity.getCreatedDate());

        this.lastModifiedBy = badgeEntity.getLastModifiedBy();

        this.lastModifiedDate = formatter.format(badgeEntity.getLastModifiedDate());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNeededScore() {
        return neededScore;
    }

    public void setNeededScore(int neededScore) {
        this.neededScore = neededScore;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    //TODO : it seems that eXo ws module doesn't supoprt Date's serialization, thus I have to use Tring instead of java.util.Date

    public String getStartValidityDate() {
        return startValidityDate;
    }

    public void setStartValidityDate(String startValidityDate) {
        this.startValidityDate = startValidityDate;
    }

    public String getEndValidityDate() {
        return endValidityDate;
    }

    public void setEndValidityDate(String endValidityDate) {
        this.endValidityDate = endValidityDate;
    }

    /**
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getStartValidityDate() {
        return startValidityDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setStartValidityDate(Date startValidityDate) {
        this.startValidityDate = startValidityDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public Date getEndValidityDate() {
        return endValidityDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public void setEndValidityDate(Date endValidityDate) {
        this.endValidityDate = endValidityDate;
    }
*/
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    //TODO : it seems that eXo ws module doesn't supoprt Date's serialization, thus I have to use Tring instead of java.util.Date
/**
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
*/
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }



    @Override
    public String toString() {
        return "BadgeDTO{" +
                "title='" + title + '\'' +
                ", neededScore='" + neededScore + '\'' +
                ", startValidityDate='" + startValidityDate + '\'' +
                ", endValidityDate='" + endValidityDate + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", isEnabled=" + isEnabled +
                ", createdBy=" + createdBy +
                ", createdDate=" + createdDate +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                "}";
    }
}
